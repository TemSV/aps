package com.example.aps.logic;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
    private List<Request> requests = new ArrayList<>();
    private Integer capacity;
    private Application application;

    public Buffer(Integer capacity, Application application) {
        this.capacity = capacity;
        this.application = application;
    }

    public void saveRequest(Request request) {
        synchronized (application) {
            if (requests.size() < capacity) {
                request.setStartTimeInBuffer(System.currentTimeMillis());
                requests.add(request);
            } else {
                Request oldestRequest = getOldestRequest();
                if (oldestRequest != null) {
                    deleteRequest(oldestRequest);
                    System.out.println("Denied request from source: " + oldestRequest.getSourceId());
                    oldestRequest.countTimeInSystem(System.currentTimeMillis());
                    application.getStatistics().addDeniedRequest(oldestRequest);
                    request.setStartTimeInBuffer(System.currentTimeMillis());
                    requests.add(request);
                    System.out.println("Buffer saved request from source: " + request.getSourceId());
                }
            }
        }
    }

    private Request getOldestRequest() {
        Request oldestRequest = null;
        for (Request r : requests) {
            if (oldestRequest == null || r.getStep() < oldestRequest.getStep()) {
                oldestRequest = r;
            }
        }
        return oldestRequest;
    }

    public void deleteRequest(Request request) {
        request.countTimeInBuffer(System.currentTimeMillis());
        requests.remove(request);
        /*System.out.println(requests);*/
    }

    public List<Request> getRequests() {
        return requests;
    }
}
