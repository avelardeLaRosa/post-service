package com.posts.app.service;

import com.posts.app.dto.UserDTO;
import com.posts.app.entities.UserEntity;
import com.posts.app.repository.IUserRepository;
import com.posts.app.service.impl.UserServiceImpl;
import com.posts.app.util.ApiResponse;
import com.posts.app.util.IServiceConstants;
import com.posts.app.util.Response;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void end() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @DisplayName("TEST GUARDAR SUCCESS")
    @Test
    void saveSucess() {
        UserEntity userEntity = this.getUserEntity();
        userEntity.setCreateDate(formatteDate("2023-12-21 00:00:00"));
        userEntity.setStatus(IServiceConstants.CREATED);
        userEntity.setUniqueIdentifier(UUID.randomUUID().toString());

        given(this.userRepository.findByStatusAndEmail(IServiceConstants.CREATED, userEntity.getEmail()))
                .willReturn(Optional.empty());

        given(this.userRepository.save(any(UserEntity.class)))
                .willReturn(userEntity);

        ApiResponse<Response<UserDTO>> userSaved = this.userService.add(userEntity.getDTO());
        assertThat(userSaved.getExitoso()).isTrue();
    }

    @DisplayName("TEST GUARDAR FAILED")
    @Test
    void saveFailed() {
        UserEntity userEntity = this.getUserEntity();
        userEntity.setCreateDate(formatteDate("2023-12-21 00:00:00"));
        userEntity.setStatus(IServiceConstants.CREATED);
        userEntity.setUniqueIdentifier(UUID.randomUUID().toString());

        given(this.userRepository.findByStatusAndEmail(IServiceConstants.CREATED, userEntity.getEmail()))
                .willReturn(Optional.of(userEntity));

        verify(this.userRepository, never()).save(any(UserEntity.class));

        ApiResponse<Response<UserDTO>> userSaved = this.userService.add(userEntity.getDTO());
        assertThat(userSaved.getExitoso()).isFalse();
        assertThat(userSaved.getCode()).isNotNull();
        assertThat(userSaved.getMessages()).isNotNull();
    }


    @DisplayName("TEST ACTUALIZAR SUCCESS")
    @Test
    void updateSuccess() {
        UserEntity userEntity = this.getUserEntity();
        userEntity.setCreateDate(formatteDate("2023-12-21 00:00:00"));
        userEntity.setStatus(IServiceConstants.CREATED);
        userEntity.setUniqueIdentifier(UUID.randomUUID().toString());


        given(this.userRepository.findByStatusAndEmail(IServiceConstants.CREATED, userEntity.getEmail()))
                .willReturn(Optional.empty());
        given(this.userRepository.save(any(UserEntity.class)))
                .willReturn(userEntity);
        ApiResponse<Response<UserDTO>> userSaved = this.userService.add(userEntity.getDTO());
        assertThat(userSaved.getExitoso()).isTrue();

        log.info("CELULAR GUARDADO: {}", userSaved.getData().getT().getCellphone());
        log.info("NOMBRE GUARDADO: {}", userSaved.getData().getT().getName());


        given(this.userRepository.findByUniqueIdentifierAndStatus(userSaved.getData().getT().getUniqueIdentifier(), IServiceConstants.CREATED))
                .willReturn(Optional.of(userEntity));

        UserEntity userUpdate = getUserEntity();
        userUpdate.setCellphone("12345678");
        userUpdate.setName("alexander");

        given(this.userRepository.save(any(UserEntity.class)))
                .willReturn(userUpdate);

        ApiResponse<Response<UserDTO>> userUpdated = this.userService.update(userSaved.getData().getT());
        log.info("USERUPDATE RESPONSE: {}", userUpdated);
        log.info("CELULAR ACTUALIZADO: {}", userUpdated.getData().getT().getName());
        log.info("NOMBRE ACTUALIZADO: {}", userUpdated.getData().getT().getName());

        assertThat(userUpdated.getExitoso()).isTrue();
        assertThat(userUpdated.getData().getT().getName()).isEqualTo("alexander");
        assertThat(userUpdated.getData().getT().getCellphone()).isEqualTo("12345678");

    }

    @DisplayName("TEST ACTUALIZAR FAILED")
    @Test
    void updateFailed() {
        UserEntity userEntity = this.getUserEntity();
        userEntity.setCreateDate(formatteDate("2023-12-21 00:00:00"));
        userEntity.setStatus(IServiceConstants.CREATED);
        userEntity.setUniqueIdentifier(UUID.randomUUID().toString());

        given(this.userRepository.findByUniqueIdentifierAndStatus(userEntity.getUniqueIdentifier(), IServiceConstants.CREATED))
                .willReturn(Optional.empty());

        verify(this.userRepository, never()).save(any(UserEntity.class));

        ApiResponse<Response<UserDTO>> userUpdated = this.userService.update(userEntity.getDTO());

        assertThat(userUpdated.getExitoso()).isFalse();
        assertThat(userUpdated.getCode()).isNotNull();
        assertThat(userUpdated.getMessages()).isNotNull();

    }

    @DisplayName("TEST OBTENER POR ID SUCCESS")
    @Test
    void getSuccess() {
        UserEntity userEntity = this.getUserEntity();
        userEntity.setCreateDate(formatteDate("2023-12-21 00:00:00"));
        userEntity.setStatus(IServiceConstants.CREATED);
        userEntity.setUniqueIdentifier(UUID.randomUUID().toString());

        given(this.userRepository.findByUniqueIdentifierAndStatus(userEntity.getUniqueIdentifier(), IServiceConstants.CREATED))
                .willReturn(Optional.of(userEntity));

        ApiResponse<Response<UserDTO>> userFound = this.userService.get(userEntity.getUniqueIdentifier());

        assertThat(userFound.getExitoso()).isTrue();

    }

    @DisplayName("TEST OBTENER POR ID FAILED")
    @Test
    void getFailed() {
        UserEntity userEntity = this.getUserEntity();
        userEntity.setCreateDate(formatteDate("2023-12-21 00:00:00"));
        userEntity.setStatus(IServiceConstants.CREATED);
        userEntity.setUniqueIdentifier(UUID.randomUUID().toString());

        given(this.userRepository.findByUniqueIdentifierAndStatus(userEntity.getUniqueIdentifier(), IServiceConstants.CREATED))
                .willReturn(Optional.empty());

        ApiResponse<Response<UserDTO>> userFound = this.userService.get(userEntity.getUniqueIdentifier());

        assertThat(userFound.getExitoso()).isFalse();
    }

    @DisplayName("TEST ELIMINAR SUCCESS")
    @Test
    void deleteSuccess() {
        UserEntity userEntity = this.getUserEntity();
        userEntity.setCreateDate(formatteDate("2023-12-21 00:00:00"));
        userEntity.setStatus(IServiceConstants.CREATED);
        userEntity.setUniqueIdentifier(UUID.randomUUID().toString());

        given(this.userRepository.findByUniqueIdentifierAndStatus(IServiceConstants.CREATED, userEntity.getEmail()))
                .willReturn(Optional.of(userEntity));

        given(this.userRepository.save(any(UserEntity.class)))
                .willReturn(userEntity);

        ApiResponse<Response<UserDTO>> userSaved = this.userService.add(userEntity.getDTO());
        assertThat(userSaved.getExitoso()).isTrue();

        UserEntity newUser = new UserEntity();
        newUser.setEntity(userSaved.getData().getT());
        newUser.setStatus(IServiceConstants.DELETED);

        given(this.userRepository.findByUniqueIdentifierAndStatus(newUser.getUniqueIdentifier(), IServiceConstants.CREATED))
                .willReturn(Optional.of(newUser));

        given(this.userRepository.save(any(UserEntity.class)))
                .willReturn(newUser);

        ApiResponse<Response<UserDTO>> userDeleted = this.userService.delete(newUser.getUniqueIdentifier());

        assertThat(userDeleted.getExitoso()).isTrue();
        assertThat(userDeleted.getData().getT().getStatus()).isEqualTo(IServiceConstants.DELETED);
    }

    UserEntity getUserEntity() {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("JUAN");
        userEntity.setLastName("PEREZ");
        userEntity.setEmail("jperez@gmail.com");
        userEntity.setCellphone("78945788");
        userEntity.setPassword("PEREZ");
        userEntity.setLastName("987511023");
        userEntity.setPostEntities(new ArrayList<>());
        return userEntity;
    }


    private LocalDateTime formatteDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }
}
