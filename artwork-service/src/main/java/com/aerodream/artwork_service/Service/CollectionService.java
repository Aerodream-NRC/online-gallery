package com.aerodream.artwork_service.Service;

import com.aerodream.artwork_service.Dto.Collection.CollectionCreateDto;
import com.aerodream.artwork_service.Dto.Collection.CollectionResponseDto;
import com.aerodream.artwork_service.Dto.Collection.CollectionUpdateDto;
import com.aerodream.artwork_service.Entity.ArtworkEntity;
import com.aerodream.artwork_service.Entity.CollectionEntity;
import com.aerodream.artwork_service.Exception.CollectionNotFoundException;
import com.aerodream.artwork_service.Repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CollectionService {

    private final ModelMapper modelMapper;
    private final CollectionRepository collectionRepository;

    public CollectionResponseDto createCollection(CollectionCreateDto createDto) {
        log.info("Creating collection for creator ID: {}", createDto.getCreatorId());

        CollectionEntity collection = modelMapper.map(createDto, CollectionEntity.class);
        CollectionEntity savedCollection = collectionRepository.save(collection);
        //TODO передать в крейтор сервис для добавления коллекции

        log.info("Created collection with ID: {}", savedCollection.getId());
        return convertCollectionToResponseDto(collection);
    }

    @Transactional(readOnly = true)
    public CollectionResponseDto getCollectionsById(Long id) throws CollectionNotFoundException {
        log.info("Fetching collection with ID: {}", id);

        CollectionEntity collection = collectionRepository.findById(id)
                .orElseThrow(() -> new CollectionNotFoundException("Collection not found with ID: " + id));

        return convertCollectionToResponseDto(collection);
    }

    @Transactional(rollbackFor = {CollectionNotFoundException.class, AccessDeniedException.class})
    public CollectionResponseDto updateCollection(Long id, CollectionUpdateDto updateDto, Long creatorId) throws CollectionNotFoundException, AccessDeniedException {
        log.info("Updating collection with ID: {}", id);

        CollectionEntity collection = collectionRepository.findById(id)
                .orElseThrow(() -> new CollectionNotFoundException("Collection not found with ID: " + id));

        if (!collection.getCreatorId().equals(creatorId))
            throw new AccessDeniedException("You can change only your collections");

        modelMapper.map(updateDto, collection);

        log.info("Updated collection with ID: {}", collection.getId());
        return convertCollectionToResponseDto(collection);
    }

    private CollectionResponseDto convertCollectionToResponseDto(CollectionEntity entity) {
        CollectionResponseDto responseDto = modelMapper.map(entity, CollectionResponseDto.class);
        responseDto.setCreatorId(entity.getCreatorId());
        responseDto.setArtworksId(convertArtworksSetToLongArtworksId(entity.getArtworks()));

        return responseDto;
    }

    private Set<Long> convertArtworksSetToLongArtworksId(Set<ArtworkEntity> entitySet) {
        Set<Long> artworksId = new HashSet<>();

        for (ArtworkEntity entity : entitySet) {
            artworksId.add(entity.getId());
        }

        return artworksId;
    }
}