package com.posts.app.controller;

import com.posts.app.dto.PostDTO;
import com.posts.app.service.IPostService;
import com.posts.app.util.*;
import com.posts.app.util.exception.GlobalException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostRESTController {
    private final IPostService postService;

    @Autowired
    public PostRESTController(IPostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Response<PostDTO>>> getUsers(
            @RequestParam(value = IServiceConstants.EMAIL, defaultValue = "", required = false) String email,
            @RequestParam(value = IServiceConstants.PAGE, defaultValue = IServiceConstants.PAGE_NUMBER, required = false) String page,
            @RequestParam(value = IServiceConstants.SIZE, defaultValue = IServiceConstants.PAGE_SIZE, required = false) String size
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(IServiceConstants.EMAIL, email);
        parameters.put(IServiceConstants.PAGE, page);
        parameters.put(IServiceConstants.SIZE, size);

        Pagination<PostDTO> pagination = this.postService.getPagination(parameters);
        Response<PostDTO> response = new Response<>();

        response.setPagination(pagination);
        ApiResponse<Response<PostDTO>> apiResponse = new ApiResponse<>();
        apiResponse.success(Messages.OK.getCode(), Messages.OK.getMessage(), response);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Response<PostDTO>>> getPost(@PathVariable("id") String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                throw new GlobalException(String.valueOf(Messages.BADREQUEST.getCode()), IServiceConstants.ID_NULL);
            }
            ApiResponse<Response<PostDTO>> postFound = this.postService.get(id);
            if (!postFound.getExitoso()) {
                throw new GlobalException(String.valueOf(postFound.getCode()), postFound.getMessages());
            }
            return new ResponseEntity<>(new ApiResponse<Response<PostDTO>>().success(Messages.OK.getCode(), Messages.OK.getMessage(), postFound.getData()), HttpStatus.OK);
        } catch (GlobalException g) {
            return new ResponseEntity<>(new ApiResponse<Response<PostDTO>>().failed(400, g.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Response<PostDTO>>> add(
            @RequestBody PostDTO postDTO,
            @RequestParam(value = IServiceConstants.EMAIL) String email
    ) {
        try {
            if (postDTO == null) {
                throw new GlobalException(String.valueOf(Messages.BADREQUEST.getCode()), IServiceConstants.DTO_NULL);
            }
            ApiResponse<Response<PostDTO>> postSaved = this.postService.add(postDTO, email);
            if (!postSaved.getExitoso()) {
                throw new GlobalException(String.valueOf(postSaved.getCode()), postSaved.getMessages());
            }
            return new ResponseEntity<>(new ApiResponse<Response<PostDTO>>().success(Messages.CREATED.getCode(), postSaved.getMessages(), postSaved.getData()), HttpStatus.OK);
        } catch (GlobalException g) {
            return new ResponseEntity<>(new ApiResponse<Response<PostDTO>>().failed(400, g.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Response<PostDTO>>> update(
            @RequestBody PostDTO userDTO,
            @RequestParam(value = IServiceConstants.EMAIL) String email
    ) {
        try {
            if (userDTO == null) {
                throw new GlobalException(String.valueOf(Messages.BADREQUEST.getCode()), IServiceConstants.DTO_NULL);
            }
            ApiResponse<Response<PostDTO>> postUpdated = this.postService.update(userDTO, email);
            if (!postUpdated.getExitoso()) {
                throw new GlobalException(String.valueOf(postUpdated.getCode()), postUpdated.getMessages());
            }
            return new ResponseEntity<>(new ApiResponse<Response<PostDTO>>().success(Messages.OK.getCode(), postUpdated.getMessages(), postUpdated.getData()), HttpStatus.OK);
        } catch (GlobalException g) {
            return new ResponseEntity<>(new ApiResponse<Response<PostDTO>>().failed(400, g.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Response<PostDTO>>> delete(
            @PathVariable("id") String id,
            @RequestParam(value = IServiceConstants.EMAIL) String email
    ) {
        try {
            if (StringUtils.isEmpty(id)) {
                throw new GlobalException(String.valueOf(Messages.BADREQUEST.getCode()), IServiceConstants.ID_NULL);
            }
            ApiResponse<Response<PostDTO>> postDeleted = this.postService.delete(id, email);
            if (!postDeleted.getExitoso()) {
                throw new GlobalException(String.valueOf(postDeleted.getCode()), postDeleted.getMessages());
            }
            return new ResponseEntity<>(new ApiResponse<Response<PostDTO>>().success(Messages.OK.getCode(), postDeleted.getMessages(), postDeleted.getData()), HttpStatus.OK);
        } catch (GlobalException g) {
            return new ResponseEntity<>(new ApiResponse<Response<PostDTO>>().failed(400, g.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
