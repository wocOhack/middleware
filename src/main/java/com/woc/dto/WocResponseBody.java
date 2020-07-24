package com.woc.dto;

public class WocResponseBody {

    private Object result;
    private String responseStatus;
    private String detailedMessage;

    public WocResponseBody() {}

    public WocResponseBody(Object result, String responseStatus, String detailedMessage) {
        this.responseStatus = responseStatus;
        this.result = result;
        this.detailedMessage = detailedMessage;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }

    public void setDetailedMessage(String detailedMessage) {
        this.detailedMessage = detailedMessage;
    }
    

}
