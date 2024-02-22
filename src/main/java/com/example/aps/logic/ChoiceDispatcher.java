package com.example.aps.logic;

import java.util.List;

public class ChoiceDispatcher {
    private Application application;
    private Buffer buffer;
    private DeviceStorage deviceStorage;

    public ChoiceDispatcher(Application application, Buffer buffer, DeviceStorage deviceStorage) {
        this.application = application;
        this.buffer = buffer;
        this.deviceStorage = deviceStorage;
    }


    public Device chooseDevice() {
        for (Device device: deviceStorage.getDevices()) {
            if (device.getDeviceState() == DeviceState.FREE) {
                return device;
            }
        }
        return null;
    }


    public Request chooseRequest() {
        List<Request> requests = buffer.getRequests();
        if (!requests.isEmpty()) {
            int lastIndex = requests.size() - 1;
            return requests.get(lastIndex);
        }
        return null;
    }

    public void startChoiceDispatcher() {
        new Thread(() -> {
            while (true) {
                synchronized (application) {
                    if (!buffer.getRequests().isEmpty()) {
                        ChoiceDispatcher choiceDispatcher = application.getChoiceDispatcher();
                        Device device = choiceDispatcher.chooseDevice();
                        if (device != null) {
                            Request chosenRequest = choiceDispatcher.chooseRequest();
                            if (chosenRequest != null) {
                                device.setProcessingRequest(chosenRequest);
                                application.getBuffer().deleteRequest(chosenRequest);
                                device.processRequest(chosenRequest);
                            }
                        }
                    }
                }
            }
        }).start();
    }
}
