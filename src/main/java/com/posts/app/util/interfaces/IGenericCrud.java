package com.posts.app.util.interfaces;


import com.posts.app.util.ApiResponse;
import com.posts.app.util.Response;

public interface IGenericCrud<T> {


    ApiResponse<Response<T>> add(T t);

    ApiResponse<Response<T>> update(T t);

    ApiResponse<Response<T>> get(String id);

    ApiResponse<Response<T>> delete(String id);
}
