package com.ibt.ava.service.model;


public class Response {
    private boolean status;
    private String message;
    private Object data;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Response(boolean status, String message, Object data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Response() {
        super();
    }

}
