package com.aerodream.artwork_service.Controller;

import com.aerodream.artwork_service.Dto.Artwork.ArtworkCreateDto;
import com.aerodream.artwork_service.Dto.Artwork.ArtworkResponseDto;
import com.aerodream.artwork_service.Dto.Artwork.ArtworkUpdateDto;
import com.aerodream.artwork_service.Service.ArtworkService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/artwork")
public class ArtworkController {

    private final ArtworkService artworkService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @PostMapping("/upload")
    public ArtworkResponseDto uploadArtwork(ArtworkCreateDto createDto,
                                            @RequestHeader("X-Creator-CreatorId") Long creatorId) throws FileUploadException {
        return artworkService.createArtwork(createDto, creatorId);
    }

    @GetMapping("/{artworkId}")
    public ArtworkResponseDto getArtwork(@PathVariable Long artworkId){
        return artworkService.getArtworkById(artworkId);
    }

    @PutMapping("/{artworkId}/like")
    public ArtworkResponseDto likeOrUnlikeArtwork(@PathVariable Long artworkId,
                                                  @RequestHeader("X-User-UserId") Long userId) {
        return artworkService.likeOrUnlikeArtwork(artworkId, userId);
    }

    @PutMapping("/{artworkId}")
    public ArtworkResponseDto updateArtwork(@RequestHeader("X-Creator-CreatorId") Long creatorId,
                                            ArtworkUpdateDto updateDto) throws AccessDeniedException {
        return artworkService.updateArtwork(updateDto, creatorId);
    }
}
