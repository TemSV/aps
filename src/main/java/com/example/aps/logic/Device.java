package com.example.aps.logic;


public class Device {

    private Integer deviceId;
    private DeviceStorage deviceStorage;
    private Integer processingTime;
    private Application application;
    private DeviceState deviceState = DeviceState.FREE;
    private Request processingRequest;
    private long startWorkingTime;
    private long startWaitingTime;
    private long waitingTime = 0;
    private long workingTime = 0;


    public Device(Integer deviceId, DeviceStorage deviceStorage, Integer processingTime, Application application) {
        this.deviceId = deviceId;
        this.deviceStorage = deviceStorage;
        this.processingTime = processingTime;
        this.application = application;
        this.startWaitingTime = System.currentTimeMillis();
    }

    public void processRequest(Request request) {
        new Thread(() -> {
            Integer startStep;
            Integer actualStep;

            synchronized (application) {
                startStep = application.getStep().get();
                actualStep = application.getStep().get();
                startWorkingTime = System.currentTimeMillis();
            }

            while (actualStep <= startStep + processingTime) {
                actualStep = application.getStep().get();
            }

            synchronized (application) {
                deviceState = DeviceState.FREE;
                workingTime += System.currentTimeMillis() - startWorkingTime;
                request.countTimeInSystem(System.currentTimeMillis());
                application.getStatistics().addRequestOnDevice(request, this);
                processingRequest = null;
            }

        }).start();
    }


    public DeviceState getDeviceState() {
        return deviceState;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setProcessingRequest(Request processingRequest) {
        deviceState = DeviceState.BUSY;
        this.processingRequest = processingRequest;
    }

    public long getWorkingTime() {
        return workingTime;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long currentTime) {
        this.waitingTime = currentTime - startWaitingTime - workingTime;
    }

    public Request getProcessingRequest() {
        return processingRequest;
    }
}
