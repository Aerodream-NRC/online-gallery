package com.aerodream.artwork_service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    private ArtworkEntity artwork;

    @Column(name = "user_id")
    private Long userId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private Set<Long> likes = new HashSet<>();

    @Column(name = "comment_body", nullable = false)
    private String commentBody;

    @Column(name = "is_hidden")
    private boolean isHidden = false;

    @Column(name = "is_liked_by_creator")
    private boolean isLikedByCreator = false;

    public CommentEntity(ArtworkEntity artwork, Long userId, String commentBody) {
        this.artwork = artwork;
        this.userId = userId;
        this.commentBody = commentBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void like(Long userId) {
        likes.add(userId);
    }

    public void unLike(Long userId) {
        likes.remove(userId);
    }
}