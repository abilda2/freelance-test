package com.bezkoder.spring.security.postgresql.models;

public class Response<T> {
    T response;
    public boolean success;
    public String info = "Request was successful";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Response(T response) {
        this.response = response;
    }
    public Response (T response, boolean success) {
        this.response = response;
        this.success = success;
    }
    public Response (T response, boolean success, String info) {
        this.response = response;
        this.success = success;
        this.info = info;
    }
}
