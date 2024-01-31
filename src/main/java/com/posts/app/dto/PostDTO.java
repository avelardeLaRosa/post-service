package com.posts.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.posts.app.util.dto.AuditoryDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO extends AuditoryDTO {
    private String text;
}
