package com.posts.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.posts.app.util.dto.AuditoryDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends AuditoryDTO {
    private String cellphone;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private List<PostDTO> postDTOList;
}
