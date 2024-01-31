package com.posts.app.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pagination<T> {
    private int pageNumber;
    private int pageSize;
    private List<T> list;
    private long totalElements;
    private int totalPages;
    private boolean lastRow;

}