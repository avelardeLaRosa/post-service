package com.posts.app.util.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class AuditoryDTO {
    private String status;
    private String uniqueIdentifier;
    private String createUser;
    private String updateUser;
    private String deleteUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updateDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime deleteDate;
}
