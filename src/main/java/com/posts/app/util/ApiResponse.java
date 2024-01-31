package com.posts.app.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {
    @JsonIgnore
    private HttpStatus code;
    private Boolean exitoso = false;
    private String messages;
    private T data;

    public ApiResponse<T> success(Integer code, String message, T data) {
        this.exitoso = true;
        this.code = this.code(code);
        this.messages = message;
        this.data = data;
        return this;
    }

    public ApiResponse<T> success(Integer code, String message) {
        this.exitoso = true;
        this.code = this.code(code);
        this.messages = message;
        return this;
    }


    public ApiResponse<T> error(Integer code, String message) {
        this.exitoso = false;
        this.code = this.code(code);
        this.messages = message;
        return this;
    }

    public ApiResponse<T> failed(Integer code, String message) {
        this.exitoso = false;
        this.code = this.code(code);
        this.messages = message;
        return this;
    }

    public ApiResponse<T> exists(Integer code, String message) {
        this.exitoso = false;
        this.code = this.code(code);
        this.messages = message;
        return this;
    }


    private HttpStatus code(Integer code) {
        HttpStatus status = null;
        switch (code) {
            case 200:
                status = HttpStatus.OK;
                break;
            case 201:
                status = HttpStatus.CREATED;
                break;
            case 404:
                status = HttpStatus.NOT_FOUND;
                break;
            case 400:
                status = HttpStatus.BAD_REQUEST;
                break;
            case 500:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
        }
        return status;
    }
}
