package com.posts.app.util;

public final class IServiceConstants {
    private IServiceConstants() {
    }

    public static final String CREATED = "CREATED";
    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
    public static final String CLOSED = "CLOSED";
    public static final String DELETED = "DELETED";
    public static final String USER_FOUND_MESSAGE = "Usuario con email %s encontrado";
    public static final String TASK_FOUND_MESSAGE = "Tarea con Codigo %s encontrado";
    public static final String USER_NOT_FOUND_MESSAGE = "Usuario no encontrado";
    public static final String USER_SAVED_MESSAGE = "Usuario con email %s guardado";
    public static final String USER_UPDATED_MESSAGE = "Usuario con email %s actualizado";
    public static final String USER_DELETED_MESSAGE = "Usuario con DNI %s eliminado";
    public static final String TASK_DELETED_MESSAGE = "Tarea con codigo %s eliminado";
    public static final String TASK_SAVED_MESSAGE = "Tarea con codigo %s guardado";
    public static final String TASK_UPDATED_MESSAGE = "Tarea con codigo %s actualizado";
    public static final String TASK_NOT_FOUND_MESSAGE = "Tarea con no encontrada";

    public static final String POST_DELETED_MESSAGE = "Post del usuerio %s eliminado";
    public static final String POST_SAVED_MESSAGE = "Post guardado";
    public static final String POST_UPDATED_MESSAGE = "Post actualizado";
    public static final String POST_NOT_FOUND_MESSAGE = "Post no encontrado";
    public static final String POST_FOUND_MESSAGE = "Post encontrado";
    public static final String POST_IS_NOT_FROM_USER_MESSAGE = "Post no pertenece al usuario %s";
    public static final String TIME_ZONE_DEFAULT = "America/Lima";
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String FILTER = "filter";
    public static final String EMAIL = "email";
    public static final String F_INICIO = "fechaInicio";
    public static final String F_FIN = "fechaFin";
    public static final String STATUS_OP = "statusOp";
    public static final String CARGO = "cargo";
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "10";
    public static final String ID_NULL = "Identificador nulo para consulta";
    public static final String DTO_NULL = "Request nulo";
    public static final String TASK_PREFIX = "TSK";
    public static final int ENTITY_LENGTH_CODE = 6;


    public static String generateCodeMaintenance(String prefix, long current, int maxLength) {
        return completeZeros(prefix, maxLength - (prefix.length() + String.valueOf(current).length())) +
                current;
    }

    private static String completeZeros(String text, int quantity) {
        return text +
                "0".repeat(Math.max(0, quantity));
    }
}
