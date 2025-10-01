package com.aerodream.user_service.Repository;

import com.aerodream.user_service.Entity.CreatorEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreatorRepository extends JpaRepository<CreatorEntity, Long> {

    Optional<CreatorEntity> findByUserId(@NonNull Long id);

    @Query("SELECT c FROM CreatorEntity c JOIN c.user u WHERE u.login = :login")
    Optional<CreatorEntity> findByUserLogin(@Param("login") String login);

    @Query("SELECT c FROM CreatorEntity c JOIN c.user u WHERE u.email = :email")
    Optional<CreatorEntity> findByUserEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CreatorEntity c WHERE c.user.id = :userId")
    boolean isUserCreator(@Param("userId") Long userId);

    @Query("SELECT c, SIZE(c.subscribers) as subscriberCount FROM CreatorEntity c ORDER BY subscriberCount DESC")
    Page<Object[]> findPopularCreators(Pageable pageable);

    @Query("SELECT SIZE(c.subscribers) FROM CreatorEntity c WHERE c.id = :creatorId")
    Integer getSubscriberCount(@Param("creatorId") Long creatorId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CreatorEntity c JOIN c.subscribers s WHERE c.id = :creatorId AND s.id = :userId")
    boolean isUserSubscribed(@Param("creatorId") Long creatorId, @Param("userId") Long userId);
}