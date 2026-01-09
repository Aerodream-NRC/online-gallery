package com.aerodream.user_service.Service;

import com.aerodream.user_service.Dto.Creator.CreatorCreateDto;
import com.aerodream.user_service.Dto.Creator.CreatorResponseDto;
import com.aerodream.user_service.Dto.Creator.CreatorUpdateDto;
import com.aerodream.user_service.Entity.CreatorEntity;
import com.aerodream.user_service.Entity.UserEntity;
import com.aerodream.user_service.Enum.RoleEnum;
import com.aerodream.user_service.Exception.CreatorNotFoundException;
import com.aerodream.user_service.Exception.UserNotFoundException;
import com.aerodream.user_service.Repository.CreatorRepository;
import com.aerodream.user_service.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CreatorService {

    private final UserRepository userRepository;
    private final CreatorRepository creatorRepository;
    private final ModelMapper modelMapper;

    public CreatorResponseDto makeUserCreator(CreatorCreateDto createDto) throws AccessDeniedException {
        log.info("Making user with ID: {} creator", createDto.getUserId());

        UserEntity user = userRepository.findById(createDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + createDto.getUserId()));

        if (user.isCreator())
            throw new AccessDeniedException("You already is creator");

        CreatorEntity creator = new CreatorEntity();
        modelMapper.map(createDto, creator);
        creator.setUser(user);
        CreatorEntity savedCreator = creatorRepository.save(creator);
        user.setCreator(savedCreator);
        user.getRoles().add(RoleEnum.ROLE_CREATOR);

        log.info("Made user with ID: {} creator with ID: {}", createDto.getUserId(), savedCreator.getId());
        return convertCreatorEntityToResponseDto(savedCreator);
    }

    @Transactional(readOnly = true)
    public CreatorResponseDto getCreator(Long id) throws CreatorNotFoundException {
        log.info("Fetching creator with ID: {}", id);

        CreatorEntity creator = creatorRepository.findById(id)
                .orElseThrow(() -> new CreatorNotFoundException("Creator not found with ID: " + id));
        return convertCreatorEntityToResponseDto(creator);
    }

    @Transactional()
    public CreatorResponseDto updateCreator(CreatorUpdateDto updateDto, Long userId) throws AccessDeniedException {
        log.info("Updating creator with ID: {}", updateDto.getId());

        CreatorEntity creator = creatorRepository.findById(updateDto.getId())
                .orElseThrow(() -> new CreatorNotFoundException("Creator not found with ID: " + updateDto.getId()));

        if (!creator.getUser().getId().equals(userId))
            throw new AccessDeniedException("You can update only your profile");

        creator.setReadyForOrder(updateDto.isReadyForOrder());

        log.info("Updated creator with ID: {}", updateDto.getId());
        return convertCreatorEntityToResponseDto(creator);
    }

    private CreatorResponseDto convertCreatorEntityToResponseDto(CreatorEntity entity) {
        CreatorResponseDto responseDto = modelMapper.map(entity, CreatorResponseDto.class);

        for (UserEntity user : entity.getSubscribers()) {
            responseDto.getSubscribers().add(user.getId());
        }

        return responseDto;
    }
}