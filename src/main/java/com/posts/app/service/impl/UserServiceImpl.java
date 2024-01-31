package com.posts.app.service.impl;

import com.posts.app.dto.UserDTO;
import com.posts.app.entities.UserEntity;
import com.posts.app.repository.IUserRepository;
import com.posts.app.service.IUserService;
import com.posts.app.util.*;
import com.posts.app.util.exception.GlobalException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Pagination<UserDTO> getPagination(Map<String, Object> parameters) {
        Pagination<UserDTO> pagination = new Pagination<>();
        Pageable pageable = PageRequest.of(
                Integer.parseInt(parameters.get(IServiceConstants.PAGE).toString()),
                Integer.parseInt(parameters.get(IServiceConstants.SIZE).toString())
        );
        Page<UserEntity> userEntities = this.userRepository.getUserEntities(
                IServiceConstants.CREATED,
                parameters.get(IServiceConstants.FILTER).toString(),
                pageable
        );

        pagination.setList(userEntities.getContent().stream().map(UserEntity::getDTO).collect(Collectors.toList()));
        pagination.setTotalPages(userEntities.getTotalPages());
        pagination.setPageNumber(userEntities.getNumber());
        pagination.setPageSize(userEntities.getSize());
        pagination.setTotalElements(userEntities.getTotalElements());
        pagination.setLastRow(userEntities.isLast());
        return pagination;
    }

    @Transactional(rollbackFor = {
            GlobalException.class,
            RuntimeException.class
    })
    @Override
    public ApiResponse<Response<UserDTO>> add(UserDTO userDTO) {
        Optional<UserEntity> optionalUser = this.userRepository.findByStatusAndEmail(IServiceConstants.CREATED, userDTO.getEmail());
        if (optionalUser.isPresent()) {
            return getErrorApiResponse(400, String.format(IServiceConstants.USER_FOUND_MESSAGE, userDTO.getEmail()));
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setEntity(userDTO);
        userEntity.setUniqueIdentifier(UUID.randomUUID().toString());
        userEntity.setStatus(IServiceConstants.CREATED);
        userEntity.setCreateDate(Date.getCurrent(IServiceConstants.TIME_ZONE_DEFAULT));
        return saveEntityAndApiResponse(200, String.format(IServiceConstants.USER_SAVED_MESSAGE, userDTO.getEmail()), userEntity);
    }

    @Transactional(rollbackFor = {
            GlobalException.class,
            RuntimeException.class
    })
    @Override
    public ApiResponse<Response<UserDTO>> update(UserDTO userDTO) {
        Optional<UserEntity> optionalUser = this.userRepository.findByUniqueIdentifierAndStatus(userDTO.getUniqueIdentifier(), IServiceConstants.CREATED);
        if (optionalUser.isEmpty()) {
            return getErrorApiResponse(400, IServiceConstants.USER_NOT_FOUND_MESSAGE);
        }
        UserEntity userUpdate = optionalUser.get();
        userUpdate.setUpdateDate(Date.getCurrent(IServiceConstants.TIME_ZONE_DEFAULT));
        userUpdate.setCellphone(!StringUtils.isEmpty(userDTO.getCellphone()) ? userDTO.getCellphone() : userUpdate.getCellphone());
        userUpdate.setName(!StringUtils.isEmpty(userDTO.getName()) ? userDTO.getName() : userUpdate.getName());
        userUpdate.setLastName(!StringUtils.isEmpty(userDTO.getLastName()) ? userDTO.getLastName() : userUpdate.getLastName());
        userUpdate.setEmail(!StringUtils.isEmpty(userDTO.getEmail()) ? userDTO.getEmail() : userUpdate.getEmail());
        userUpdate.setPassword(!StringUtils.isEmpty(userDTO.getPassword()) ? userDTO.getPassword() : userUpdate.getPassword());
        return saveEntityAndApiResponse(200, String.format(IServiceConstants.USER_UPDATED_MESSAGE, userDTO.getEmail()), userUpdate);
    }

    @Transactional(readOnly = true)
    @Override
    public ApiResponse<Response<UserDTO>> get(String id) {
        Optional<UserEntity> optionalUser = this.userRepository.findByUniqueIdentifierAndStatus(id, IServiceConstants.CREATED);
        if (optionalUser.isEmpty()) {
            return getErrorApiResponse(400, IServiceConstants.USER_NOT_FOUND_MESSAGE);
        }
        return getSuccessApiResponse(200, String.format(IServiceConstants.USER_FOUND_MESSAGE, optionalUser.get().getEmail()), optionalUser.get().getDTO());
    }

    @Transactional(rollbackFor = {
            GlobalException.class,
            RuntimeException.class
    })
    @Override
    public ApiResponse<Response<UserDTO>> delete(String id) {
        Optional<UserEntity> optionalUser = this.userRepository.findByUniqueIdentifierAndStatus(id, IServiceConstants.CREATED);
        if (optionalUser.isEmpty()) {
            return getErrorApiResponse(400, IServiceConstants.USER_NOT_FOUND_MESSAGE);
        }
        UserEntity userEntity = optionalUser.get();
        userEntity.setStatus(IServiceConstants.DELETED);
        userEntity.setDeleteDate(Date.getCurrent(IServiceConstants.TIME_ZONE_DEFAULT));
        return saveEntityAndApiResponse(200, String.format(IServiceConstants.USER_FOUND_MESSAGE, userEntity.getEmail()), userEntity);
    }

    public ApiResponse<Response<UserDTO>> saveEntityAndApiResponse(Integer code, String message, UserEntity entity) {
        Response<UserDTO> response = new Response<>();
        response.setT(this.userRepository.save(entity).getDTO());
        return new ApiResponse<Response<UserDTO>>().success(code, message, response);
    }

    public ApiResponse<Response<UserDTO>> getSuccessApiResponse(Integer code, String message, UserDTO dto) {
        Response<UserDTO> response = new Response<>();
        response.setT(dto);
        return new ApiResponse<Response<UserDTO>>().success(code, message, response);
    }

    public ApiResponse<Response<UserDTO>> getErrorApiResponse(Integer code, String message) {
        return new ApiResponse<Response<UserDTO>>().failed(code, message);
    }


}
