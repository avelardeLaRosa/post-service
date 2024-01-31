package com.posts.app.entities;

import com.posts.app.dto.PostDTO;
import com.posts.app.dto.UserDTO;
import com.posts.app.util.entities.AuditoryEntity;
import com.posts.app.util.interfaces.IDTOManagment;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "t_post")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity extends AuditoryEntity implements IDTOManagment<PostDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "tx_text")
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private UserEntity userEntity;

    @Override
    public PostDTO getDTO() {
        PostDTO postDTO = new PostDTO();
        postDTO.setText(this.text);

        postDTO.setStatus(getStatus());
        postDTO.setUniqueIdentifier(getUniqueIdentifier());
        postDTO.setCreateUser(getCreateUser());
        postDTO.setUpdateUser(getUpdateUser());
        postDTO.setDeleteUser(getDeleteUser());
        postDTO.setCreateDate(getCreateDate());
        postDTO.setUpdateDate(getUpdateDate());
        postDTO.setDeleteDate(getDeleteDate());

        return postDTO;
    }

    @Override
    public void setEntity(PostDTO postDTO) {

        this.text = postDTO.getText();

        setStatus(postDTO.getStatus());
        setUniqueIdentifier(postDTO.getUniqueIdentifier());
        setCreateUser(postDTO.getCreateUser());
        setUpdateUser(postDTO.getUpdateUser());
        setDeleteUser(postDTO.getDeleteUser());
        setCreateDate(postDTO.getCreateDate());
        setUpdateDate(postDTO.getUpdateDate());
        setDeleteDate(postDTO.getDeleteDate());

    }

}
