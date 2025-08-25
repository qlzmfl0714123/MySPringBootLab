package com.rookies4.myspringbootlab.exception.advice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ErrorObject {
    private Integer statusCode;
    private String message;
    private String timestamp;

    public ErrorObject() { }

    public Integer getStatusCode() { return statusCode; }
    public void setStatusCode(Integer statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() {
        LocalDateTime ldt = LocalDateTime.now();
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss E a", Locale.KOREA)
                .format(ldt);
    }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}