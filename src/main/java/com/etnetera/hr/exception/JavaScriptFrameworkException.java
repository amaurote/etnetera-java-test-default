package com.etnetera.hr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class JavaScriptFrameworkException extends HttpStatusCodeException {

    public JavaScriptFrameworkException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
