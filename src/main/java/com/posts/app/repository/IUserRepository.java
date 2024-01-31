package com.posts.app.repository;

import com.posts.app.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByStatusAndEmail(String status, String email);

    Optional<UserEntity> findByUniqueIdentifierAndStatus(String uniqueIdentifier, String status);

    @Query(
            value = "SELECT u FROM UserEntity  u " +
                    "where u.status = :status " +
                    "and ( " +
                    "(u.email like concat('%', :filter, '%')) or " +
                    "(u.name like concat('%', :filter, '%')) or " +
                    "(u.lastName like concat('%', :filter, '%'))" +
                    ") "
    )
    Page<UserEntity> getUserEntities(
            String status,
            String filter,
            Pageable pageable
    );
}
