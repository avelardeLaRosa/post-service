package com.posts.app.repository;

import com.posts.app.entities.PostEntity;
import com.posts.app.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPostRepository extends JpaRepository<PostEntity, Integer> {

    Optional<PostEntity> findByUniqueIdentifierAndStatus(String uniqueIdentifier, String status);

    @Query(
            value = "SELECT p FROM PostEntity  p " +
                    "INNER JOIN UserEntity u on p.userEntity.id = u.id " +
                    "where p.status = :status " +
                    "and (u.email = :email or :email is null) "
    )
        Page<PostEntity> getPostEntities(
            String status,
            String email ,
            Pageable pageable
    );
    @Query(
            value = "SELECT p FROM PostEntity  p " +
                    "INNER JOIN UserEntity u on p.userEntity.id = u.id " +
                    "where p.status = :status " +
                    "and p.uniqueIdentifier = :uniqueIdentifier " +
                    "and u.email = :email "
    )
    Optional<PostEntity> getPostByUser(
            String status,
            String uniqueIdentifier,
            String email
    );
}
