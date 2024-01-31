package com.posts.app.service;

import com.posts.app.dto.UserDTO;
import com.posts.app.util.Pagination;
import com.posts.app.util.interfaces.IGenericCrud;

import java.util.Map;

public interface IUserService extends IGenericCrud<UserDTO> {

    Pagination<UserDTO> getPagination(Map<String, Object> parameters);
}
