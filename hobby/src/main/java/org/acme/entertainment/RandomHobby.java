package org.acme.entertainment;

public class RandomHobby {
    
    private String requestId;
    private String workerId;
    private String cloudId;
    private String activity;

    public RandomHobby() {
    }

    public RandomHobby(String requestId, String workerId, String cloudId, String text) {
        this.requestId = requestId;
        this.workerId = workerId;
        this.cloudId = cloudId;
        this.activity = text;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public String getActivity() {
        return activity;
    }

    public String getCloudId() {
        return cloudId;
    }

    public void setCloudId(String cloudId) {
        this.cloudId = cloudId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    @Override
    public String toString() {
        return String.format("Response{requestId=%s, workerId=%s, cloudId=%s, activity=%s}",
                             requestId, workerId, cloudId, activity);
    }
    
}