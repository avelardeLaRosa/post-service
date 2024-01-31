package com.posts.app.service;

import com.posts.app.dto.PostDTO;
import com.posts.app.dto.UserDTO;
import com.posts.app.util.ApiResponse;
import com.posts.app.util.Pagination;
import com.posts.app.util.Response;
import com.posts.app.util.interfaces.IGenericCrud;

import java.util.Map;

public interface IPostService extends IGenericCrud<PostDTO> {

    Pagination<PostDTO> getPagination(Map<String, Object> parameters);

    ApiResponse<Response<PostDTO>> add(PostDTO postDTO, String email);
    ApiResponse<Response<PostDTO>> update(PostDTO postDTO, String email);
    ApiResponse<Response<PostDTO>> delete(String id, String email);
}
