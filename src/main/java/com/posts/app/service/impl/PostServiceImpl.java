package com.posts.app.service.impl;

import com.posts.app.dto.PostDTO;
import com.posts.app.entities.PostEntity;
import com.posts.app.entities.UserEntity;
import com.posts.app.repository.IPostRepository;
import com.posts.app.repository.IUserRepository;
import com.posts.app.service.IPostService;
import com.posts.app.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;

    @Autowired
    public PostServiceImpl(IPostRepository postRepository, IUserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Pagination<PostDTO> getPagination(Map<String, Object> parameters) {
        Pagination<PostDTO> pagination = new Pagination<>();
        Pageable pageable = PageRequest.of(
                Integer.parseInt(parameters.get(IServiceConstants.PAGE).toString()),
                Integer.parseInt(parameters.get(IServiceConstants.SIZE).toString())
        );
        Page<PostEntity> postEntities = this.postRepository.getPostEntities(
                IServiceConstants.CREATED,
                !StringUtils.isEmpty(parameters.get(IServiceConstants.EMAIL).toString()) ? parameters.get(IServiceConstants.EMAIL).toString() : null,
                pageable
        );

        pagination.setList(postEntities.getContent().stream().map(PostEntity::getDTO).collect(Collectors.toList()));
        pagination.setTotalPages(postEntities.getTotalPages());
        pagination.setPageNumber(postEntities.getNumber());
        pagination.setPageSize(postEntities.getSize());
        pagination.setTotalElements(postEntities.getTotalElements());
        pagination.setLastRow(postEntities.isLast());
        return pagination;
    }

    @Override
    public ApiResponse<Response<PostDTO>> add(PostDTO postDTO, String email) {
        Optional<UserEntity> optionalUser = this.userRepository.findByStatusAndEmail(IServiceConstants.CREATED, email);
        if (optionalUser.isEmpty()) {
            return getErrorApiResponse(400, IServiceConstants.USER_NOT_FOUND_MESSAGE);
        }
        PostEntity postEntity = new PostEntity();
        postEntity.setEntity(postDTO);
        postEntity.setUniqueIdentifier(UUID.randomUUID().toString());
        postEntity.setStatus(IServiceConstants.CREATED);
        postEntity.setCreateDate(Date.getCurrent(IServiceConstants.TIME_ZONE_DEFAULT));
        postEntity.setUserEntity(optionalUser.get());
        return saveEntityAndApiResponse(200, IServiceConstants.POST_SAVED_MESSAGE, postEntity);
    }

    @Override
    public ApiResponse<Response<PostDTO>> update(PostDTO postDTO, String email) {

        Optional<PostEntity> optionalPost = this.postRepository.findByUniqueIdentifierAndStatus(postDTO.getUniqueIdentifier(), IServiceConstants.CREATED);
        if (optionalPost.isEmpty()) {
            return getErrorApiResponse(400, IServiceConstants.POST_NOT_FOUND_MESSAGE);
        }

        Optional<UserEntity> optionalUser = this.userRepository.findByStatusAndEmail(IServiceConstants.CREATED, email);
        if (optionalUser.isEmpty()) {
            return getErrorApiResponse(400, IServiceConstants.USER_NOT_FOUND_MESSAGE);
        }

        Optional<PostEntity> optionalPostByUser = this.postRepository.getPostByUser(IServiceConstants.CREATED, postDTO.getUniqueIdentifier(), email);
        if (optionalPostByUser.isEmpty()) {
            return getErrorApiResponse(400, String.format(IServiceConstants.POST_IS_NOT_FROM_USER_MESSAGE, email));
        }

        PostEntity updatePost = optionalPostByUser.get();
        updatePost.setUpdateDate(Date.getCurrent(IServiceConstants.TIME_ZONE_DEFAULT));
        updatePost.setText(!StringUtils.isEmpty(postDTO.getText()) ? postDTO.getText() : updatePost.getText());
        return saveEntityAndApiResponse(200, IServiceConstants.POST_UPDATED_MESSAGE, updatePost);
    }


    @Override
    public ApiResponse<Response<PostDTO>> get(String id) {
        Optional<PostEntity> optionalPost = this.postRepository.findByUniqueIdentifierAndStatus(id, IServiceConstants.CREATED);
        if (optionalPost.isEmpty()) {
            return getErrorApiResponse(400, IServiceConstants.POST_NOT_FOUND_MESSAGE);
        }
        return getSuccessApiResponse(200, IServiceConstants.POST_FOUND_MESSAGE, optionalPost.get().getDTO());
    }

    @Override
    public ApiResponse<Response<PostDTO>> delete(String id, String email) {

        Optional<PostEntity> optionalPost = this.postRepository.findByUniqueIdentifierAndStatus(id, IServiceConstants.CREATED);
        if (optionalPost.isEmpty()) {
            return getErrorApiResponse(400, IServiceConstants.POST_NOT_FOUND_MESSAGE);
        }

        Optional<UserEntity> optionalUser = this.userRepository.findByStatusAndEmail(IServiceConstants.CREATED, email);
        if (optionalUser.isEmpty()) {
            return getErrorApiResponse(400, IServiceConstants.USER_NOT_FOUND_MESSAGE);
        }

        Optional<PostEntity> optionalPostByUser = this.postRepository.getPostByUser(IServiceConstants.CREATED, id, email);
        if (optionalPostByUser.isEmpty()) {
            return getErrorApiResponse(400, String.format(IServiceConstants.POST_IS_NOT_FROM_USER_MESSAGE, email));
        }

        PostEntity updatePost = optionalPostByUser.get();
        updatePost.setDeleteDate(Date.getCurrent(IServiceConstants.TIME_ZONE_DEFAULT));
        updatePost.setStatus(IServiceConstants.DELETED);
        return saveEntityAndApiResponse(200, String.format(IServiceConstants.POST_DELETED_MESSAGE, email), updatePost);
    }


    @Override
    public ApiResponse<Response<PostDTO>> update(PostDTO postDTO) {
        return null;
    }

    @Override
    public ApiResponse<Response<PostDTO>> add(PostDTO postDTO) {
        return null;
    }

    @Override
    public ApiResponse<Response<PostDTO>> delete(String id) {
        return null;
    }

    public ApiResponse<Response<PostDTO>> saveEntityAndApiResponse(Integer code, String message, PostEntity entity) {
        Response<PostDTO> response = new Response<>();
        response.setT(this.postRepository.save(entity).getDTO());
        return new ApiResponse<Response<PostDTO>>().success(code, message, response);
    }

    public ApiResponse<Response<PostDTO>> getSuccessApiResponse(Integer code, String message, PostDTO dto) {
        Response<PostDTO> response = new Response<>();
        response.setT(dto);
        return new ApiResponse<Response<PostDTO>>().success(code, message, response);
    }

    public ApiResponse<Response<PostDTO>> getErrorApiResponse(Integer code, String message) {
        return new ApiResponse<Response<PostDTO>>().failed(code, message);
    }


}
