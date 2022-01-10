package com.example.projectarm.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ExceptionFileNotfound  extends  RuntimeException{

    public ExceptionFileNotfound(String message) {
        super(message);
    }

    public ExceptionFileNotfound(String message, Throwable exception) {
        super(message, exception);
    }
}
