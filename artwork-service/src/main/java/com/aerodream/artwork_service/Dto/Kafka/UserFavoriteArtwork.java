package com.aerodream.artwork_service.Dto.Kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserFavoriteArtwork {
    private Long artworkId;
    private Long userId;

    public UserFavoriteArtwork(Long artworkId, Long userId) {
        this.artworkId = artworkId;
        this.userId = userId;
    }
}