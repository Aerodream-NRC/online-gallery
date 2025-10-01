package com.aerodream.user_service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "creators")
public class CreatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "subscriptions",
            joinColumns = @JoinColumn(name = "creator_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> subscribers = new HashSet<>();

    @Column(name = "collections_id")
    private Set<Long> collectionsId = new HashSet<>();

    @OneToOne(mappedBy = "creator",
            fetch = FetchType.LAZY)
    private UserEntity user;

    private String firstname;

    private String lastname;

    @Column(name = "is_ready_for_order")
    private boolean isReadyForOrder = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatorEntity that = (CreatorEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void addSubscriber(UserEntity user) {
        subscribers.add(user);
        user.getSubscriptions().add(this);
    }

    public void removeSubscriber(UserEntity user) {
        subscribers.remove(user);
        user.getSubscriptions().remove(this);
    }

    public void addCollection(Long collectionId) {
        collectionsId.add(collectionId);
    }

    public void removeCollection(Long collectionId) {
        collectionsId.remove(collectionId);
    }
}