package com.posts.app.util.exception;

import java.io.Serializable;

public class GlobalException extends RuntimeException implements Serializable {
    private final String code;

    public GlobalException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
