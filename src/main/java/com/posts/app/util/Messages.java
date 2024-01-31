package com.posts.app.util;

import lombok.Getter;

@Getter
public enum Messages {
    OK("Proceso exitoso.", 200),
    CREATED("Creacion exitosa", 201),
    UPDATED("Actualizacion exitosa", 200),
    DELETED("Eliminacion exitosa", 200),
    BADREQUEST("Error en su peticion", 400),

    INTERNAL_ERROR("Error en el servicio", 500),
    USER_NOT_FOUND("Usuario no encontrado", 500),
    DISTRICT_NOT_FOUND("Distrito no encontrado", 500),
    CONDITION_NOT_FOUND("Estado no encontrado", 500),
    CURSO_NOT_FOUND("Curso no encontrado", 500),
    USUARIO_NOT_FOUND("Usuario no encontrado", 500),
    CARRERA_NOT_FOUND("Carrera no encontrada", 500),
    PROVINCIA_NOT_FOUND("Provincia no encontrada", 500),
    EMAIL_EXISTS("Correo ya ingresado", 500),
    DISTRIC_EXISTS("Distrito ya ingresado", 500),
    CURSO_EXISTS("Curso ya ingresado", 500),

    ROLE_NOT_FOUND("Rol no encontrado", 500),

    ;

    private String message;
    private int code;

    private Messages(String message, Integer code){
        this.message = message;
        this.code = code;
    }
}
