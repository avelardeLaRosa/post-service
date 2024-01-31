package com.posts.app.util.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class AuditoryEntity {

    @Column(name = "tx_status")
    private String status;
    @Column(name = "tx_unique_identifier")
    private String uniqueIdentifier;

    @Column(name = "tx_create_user")
    private String createUser;
    @Column(name = "tx_update_user")
    private String updateUser;
    @Column(name = "tx_delete_user")
    private String deleteUser;
    @Column(name = "tx_create_date")
    private LocalDateTime createDate;
    @Column(name = "tx_update_date")
    private LocalDateTime updateDate;
    @Column(name = "tx_delete_date")
    private LocalDateTime deleteDate;
}
