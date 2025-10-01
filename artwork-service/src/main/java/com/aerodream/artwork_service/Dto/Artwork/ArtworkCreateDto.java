package com.aerodream.artwork_service.Dto.Artwork;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkCreateDto {

    private Long creatorId;

    private String title;

    private String description;

    private MultipartFile imageFile;

    private Long collectionId;

    private Set<String> tags;
}