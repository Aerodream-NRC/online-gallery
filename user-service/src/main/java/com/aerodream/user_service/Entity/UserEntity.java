package com.aerodream.user_service.Entity;

import com.aerodream.user_service.Enum.RoleEnum;
import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(nullable = false)
    private boolean deleted = false;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private CreatorEntity creator;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<RoleEnum> roles = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private Set<Long> savedArtworksId = new HashSet<>();

    @ManyToMany(
            mappedBy = "subscribers",
            fetch = FetchType.LAZY
    )
    private Set<CreatorEntity> subscriptions = new HashSet<>();

    public boolean isCreator() {
        return this.creator != null &&
                this.roles.contains(RoleEnum.ROLE_CREATOR);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void subscribe(CreatorEntity creator) {
        subscriptions.add(creator);
        creator.getSubscribers().add(this);
    }

    public void unSubscribe(CreatorEntity creator) {
        subscriptions.remove(creator);
        creator.getSubscribers().remove(this);
    }

    public void saveArtwork(Long artworkId) {
        savedArtworksId.add(artworkId);
    }

    public void removeArtwork(Long artworkId) {
        savedArtworksId.remove(artworkId);
    }
}