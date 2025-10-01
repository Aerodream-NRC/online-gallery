package com.aerodream.artwork_service.Dto.Artwork;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkResponseDto {

    private Long id;

    private Long creatorId;

    private String title;

    private String description;

    private String imageS3Key;

    private Long collectionId;

    private Map<Long, LocalDateTime> likes;

    private Set<String> comments;

    private boolean isHiddenComments;

    private boolean isSold;

    private LocalDateTime createdAt;
}