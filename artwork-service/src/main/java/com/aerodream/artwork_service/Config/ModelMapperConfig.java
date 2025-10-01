package com.aerodream.artwork_service.Config;

import com.aerodream.artwork_service.Dto.Artwork.ArtworkCreateDto;
import com.aerodream.artwork_service.Dto.Artwork.ArtworkResponseDto;
import com.aerodream.artwork_service.Dto.Artwork.ArtworkUpdateDto;
import com.aerodream.artwork_service.Dto.Collection.CollectionCreateDto;
import com.aerodream.artwork_service.Dto.Collection.CollectionResponseDto;
import com.aerodream.artwork_service.Dto.Collection.CollectionUpdateDto;
import com.aerodream.artwork_service.Dto.Comment.CommentCreateDto;
import com.aerodream.artwork_service.Dto.Comment.CommentResponseDto;
import com.aerodream.artwork_service.Dto.Comment.CommentUpdateBodyDto;
import com.aerodream.artwork_service.Dto.Comment.CommentUpdateDto;
import com.aerodream.artwork_service.Entity.ArtworkEntity;
import com.aerodream.artwork_service.Entity.CollectionEntity;
import com.aerodream.artwork_service.Entity.CommentEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        configureArtworkMappings(modelMapper);
        configureCollectionMappings(modelMapper);
        configureCommentMappings(modelMapper);


        return modelMapper;
    }

    private void configureArtworkMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(ArtworkCreateDto.class, ArtworkEntity.class)
                .addMappings(mapping -> {
                    mapping.map(ArtworkCreateDto::getTitle, ArtworkEntity::setTitle);
                    mapping.map(ArtworkCreateDto::getDescription, ArtworkEntity::setDescription);
                    mapping.map(ArtworkCreateDto::getCreatorId, ArtworkEntity::setCreatorId);
                    mapping.skip(ArtworkEntity::setImageS3Key);
                });
        modelMapper.typeMap(ArtworkEntity.class, ArtworkResponseDto.class)
                .addMappings(mapping -> {
                    mapping.map(ArtworkEntity::getId, ArtworkResponseDto::setId);
                    mapping.map(ArtworkEntity::getTitle, ArtworkResponseDto::setTitle);
                    mapping.map(ArtworkEntity::getDescription, ArtworkResponseDto::setDescription);
                    mapping.map(ArtworkEntity::getCreatedAt, ArtworkResponseDto::setCreatedAt);
                    mapping.map(ArtworkEntity::getLikes, ArtworkResponseDto::setLikes);
                    mapping.map(ArtworkEntity::getImageS3Key, ArtworkResponseDto::setImageS3Key);
                    mapping.map(ArtworkEntity::getCreatorId, ArtworkResponseDto::setCreatorId);
                    mapping.map(context -> context.getCollection().getId(), ArtworkResponseDto::setCollectionId);
                    mapping.map(ArtworkEntity::isSold, ArtworkResponseDto::setSold);
                    mapping.map(ArtworkEntity::isHiddenComments, ArtworkResponseDto::setHiddenComments);
                });
        modelMapper.typeMap(ArtworkUpdateDto.class, ArtworkEntity.class)
                .addMappings(mapping -> {
                    mapping.map(ArtworkUpdateDto::getDescription, ArtworkEntity::setDescription);
                    mapping.map(ArtworkUpdateDto::getTitle, ArtworkEntity::setTitle);
                    mapping.map(ArtworkUpdateDto::isHiddenComments, ArtworkEntity::setHiddenComments);
                    mapping.map(ArtworkUpdateDto::isSold, ArtworkEntity::setSold);
                    mapping.skip(ArtworkEntity::setId);
                    mapping.skip(ArtworkEntity::setCollection);
                    mapping.skip(ArtworkEntity::setComments);
                    mapping.skip(ArtworkEntity::setTags);
                });
    }

    private void configureCollectionMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(CollectionCreateDto.class, CollectionEntity.class)
                .addMappings(mapping -> {
                    mapping.map(CollectionCreateDto::getTitle, CollectionEntity::setTitle);
                    mapping.map(CollectionCreateDto::getCreatorId, CollectionEntity::setCreatorId);
                    mapping.map(CollectionCreateDto::getDescription, CollectionEntity::setDescription);
                });
        modelMapper.typeMap(CollectionEntity.class, CollectionResponseDto.class)
                .addMappings(mapping -> {
                    mapping.map(CollectionEntity::getId, CollectionResponseDto::setId);
                    mapping.map(CollectionEntity::getTitle, CollectionResponseDto::setTitle);
                    mapping.map(CollectionEntity::getDescription, CollectionResponseDto::setDescription);
                    mapping.map(CollectionEntity::getCreatorId, CollectionResponseDto::setCreatorId);
                    mapping.map(CollectionEntity::getCreatedAt, CollectionResponseDto::setCreatedAt);
                    mapping.map(CollectionEntity::getUpdatedAt, CollectionResponseDto::setUpdatedAt);
                    mapping.skip(CollectionResponseDto::setArtworksId);
                });
        modelMapper.typeMap(CollectionUpdateDto.class, CollectionEntity.class)
                .addMappings(mapping -> {
                    mapping.map(CollectionUpdateDto::getUpdatedAt, CollectionEntity::setUpdatedAt);
                    mapping.map(CollectionUpdateDto::getTitle, CollectionEntity::setTitle);
                    mapping.map(CollectionUpdateDto::getDescription, CollectionEntity::setDescription);
                });
    }

    private void configureCommentMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(CommentCreateDto.class, CommentEntity.class)
                .addMappings(mapping -> {
                    mapping.map(CommentCreateDto::getCommentBody, CommentEntity::setCommentBody);
                    mapping.map(CommentCreateDto::getUserId, CommentEntity::setUserId);
                    mapping.skip(CommentEntity::setArtwork);
                });
        modelMapper.typeMap(CommentEntity.class, CommentResponseDto.class)
                .addMappings(mapping -> {
                    mapping.map(CommentEntity::getId, CommentResponseDto::setId);
                    mapping.map(CommentEntity::getCommentBody, CommentResponseDto::setCommentBody);
                    mapping.map(CommentEntity::getLikes, CommentResponseDto::setLikes);
                    mapping.map(CommentEntity::getUserId, CommentResponseDto::setUserId);
                    mapping.map(CommentEntity::getCreatedAt, CommentResponseDto::setCreatedAt);
                    mapping.map(CommentEntity::getUpdatedAt, CommentResponseDto::setUpdatedAt);
                    mapping.map(context -> context.getArtwork().getId(), CommentResponseDto::setArtworkId);
                    mapping.map(CommentEntity::isHidden, CommentResponseDto::setHidden);
                    mapping.map(CommentEntity::isLikedByCreator, CommentResponseDto::setLikedByCreator);
                });
        modelMapper.typeMap(CommentUpdateBodyDto.class, CommentEntity.class)
                .addMappings(mapping -> {
                    mapping.map(CommentUpdateBodyDto::getCommentBody, CommentEntity::setCommentBody);
                    mapping.skip(CommentUpdateBodyDto::getUpdatedAt, CommentEntity::setUpdatedAt);
                    mapping.skip(CommentEntity::setId);
                });
        modelMapper.typeMap(CommentUpdateDto.class, CommentEntity.class)
                .addMappings(mapping -> {
                    mapping.map(CommentUpdateDto::isHidden, CommentEntity::setHidden);
                    mapping.map(CommentUpdateDto::isLikedByCreator, CommentEntity::setLikedByCreator);
                    mapping.skip(CommentEntity::setId);
                    mapping.skip(CommentEntity::setArtwork);
                });
    }
}
