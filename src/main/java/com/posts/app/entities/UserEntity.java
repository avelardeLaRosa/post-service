package com.posts.app.entities;

import com.posts.app.dto.PostDTO;
import com.posts.app.dto.UserDTO;
import com.posts.app.util.IServiceConstants;
import com.posts.app.util.entities.AuditoryEntity;
import com.posts.app.util.interfaces.IDTOManagment;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends AuditoryEntity implements IDTOManagment<UserDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "tx_cellphone")
    private String cellphone;
    @Column(name = "tx_name")
    private String name;
    @Column(name = "tx_last_name")
    private String lastName;
    @Column(name = "tx_email")
    private String email;
    @Column(name = "tx_password")
    private String password;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PostEntity> postEntities = new ArrayList<>();

    @Override
    public UserDTO getDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setCellphone(this.cellphone);
        userDTO.setName(this.name);
        userDTO.setLastName(this.lastName);
        userDTO.setEmail(this.email);
        userDTO.setPassword(this.password);
        userDTO.setPostDTOList(this.postEntities.stream().filter(p -> p.getStatus().equals(IServiceConstants.CREATED)).map(PostEntity::getDTO).collect(Collectors.toList()));

        userDTO.setStatus(getStatus());
        userDTO.setUniqueIdentifier(getUniqueIdentifier());
        userDTO.setCreateUser(getCreateUser());
        userDTO.setUpdateUser(getUpdateUser());
        userDTO.setDeleteUser(getDeleteUser());
        userDTO.setCreateDate(getCreateDate());
        userDTO.setUpdateDate(getUpdateDate());
        userDTO.setDeleteDate(getDeleteDate());

        return userDTO;
    }

    @Override
    public void setEntity(UserDTO userDTO) {

        this.cellphone = userDTO.getCellphone();
        this.name = userDTO.getName();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();

        setStatus(userDTO.getStatus());
        setUniqueIdentifier(userDTO.getUniqueIdentifier());
        setCreateUser(userDTO.getCreateUser());
        setUpdateUser(userDTO.getUpdateUser());
        setDeleteUser(userDTO.getDeleteUser());
        setCreateDate(userDTO.getCreateDate());
        setUpdateDate(userDTO.getUpdateDate());
        setDeleteDate(userDTO.getDeleteDate());

    }

}
