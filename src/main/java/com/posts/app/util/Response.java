package com.posts.app.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    Pagination<T> pagination;
    T t;


    public Response<T> pagination(Pagination<T> pagination) {
        this.pagination = pagination;
        return this;
    }

    public Response<T> t(T t) {
        this.t = t;
        return this;
    }

    public Response<T> build() {
        Response<T> nouraResponse = new Response();
        nouraResponse.setPagination(this.pagination);
        nouraResponse.setT(this.t);
        return nouraResponse;
    }

}
