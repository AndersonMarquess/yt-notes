package com.andersonmarques.youtubenotes.models;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiError {

    private Date timestamp;
    private Integer status;
    private String error;
    private List<String> message;
    private String path;

    public ApiError(HttpStatus status, List<String> message, String Path) {
        this.timestamp = new Date();
        this.status = status.value();
        this.error = status.toString();
        this.message = message;
        this.path = Path;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
