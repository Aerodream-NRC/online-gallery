package com.aerodream.user_service.Entity;

import com.aerodream.user_service.Enum.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    private String password;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "is_enabled")
    private Boolean isEnabled = true;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
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

    //TODO вынести методы для авторизации из сущности

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return this.isEnabled;
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