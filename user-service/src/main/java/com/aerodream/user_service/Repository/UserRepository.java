package com.aerodream.user_service.Repository;

import com.aerodream.user_service.Entity.UserEntity;
import com.aerodream.user_service.Enum.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByLogin(String login);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r = :role")
    List<UserEntity> findAllByRole(@Param("role") RoleEnum role);

}