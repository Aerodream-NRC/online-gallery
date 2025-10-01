package com.aerodream.artwork_service.Dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;

    private Long artworkId;

    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String commentBody;

    private Set<Long> likes;

    private boolean isHidden;

    private boolean isLikedByCreator;
}