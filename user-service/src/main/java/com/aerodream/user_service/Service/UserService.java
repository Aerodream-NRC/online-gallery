package com.aerodream.user_service.Service;

import com.aerodream.user_service.Dto.User.UserCreateDto;
import com.aerodream.user_service.Dto.User.UserLoginDto;
import com.aerodream.user_service.Dto.User.UserResponseDto;
import com.aerodream.user_service.Dto.User.UserUpdateDto;
import com.aerodream.user_service.Entity.CreatorEntity;
import com.aerodream.user_service.Entity.UserEntity;
import com.aerodream.user_service.Enum.RoleEnum;
import com.aerodream.user_service.Exception.CreatorNotFoundException;
import com.aerodream.user_service.Exception.UserAlreadyExistException;
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
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CreatorRepository creatorRepository;

    public UserResponseDto createUser(UserCreateDto createDto) throws MatchException, AccessDeniedException, UserAlreadyExistException {
        log.info("Creating new user with login {} and email {}", createDto.getLogin(), createDto.getEmail());

        if (!createDto.getPassword().equals(createDto.getConfirmPassword()))
            throw new AccessDeniedException("Password and Confirm password not match");
        if (userRepository.existsByLogin(createDto.getLogin()))
            throw new UserAlreadyExistException("User already exist with login: " + createDto.getLogin());
        if (userRepository.existsByEmail(createDto.getEmail()))
            throw new UserAlreadyExistException("User already exist with email: " + createDto.getEmail());

        UserEntity user = modelMapper.map(createDto, UserEntity.class);
        user.getRoles().add(RoleEnum.ROLE_USER);
        UserEntity savedUser = userRepository.save(user);

        log.info("Created new user with ID: {}", savedUser.getId());
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    @Transactional(readOnly = true)
    public UserResponseDto userAuthentication(UserLoginDto loginDto) {

        return ;
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) throws UserNotFoundException {
        log.info("Fetching user with ID: {}", id);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional(rollbackFor = {UserNotFoundException.class})
    public UserResponseDto userSaveArtwork(Long userId, Long artworkId) {
        log.info("User with ID: {} saving artwork with ID: {} to favorite", userId, artworkId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (user.getSavedArtworksId().contains(artworkId)) {
            user.removeArtwork(artworkId);
            //TODO передать в микросервис картин для удаления изера

            log.info("User with ID: {} remove artwork with ID: {} from favorite", userId, artworkId);
        } else {
            user.saveArtwork(artworkId);
            //TODO передать в микросервис картин для добавления юзера

            log.info("User with ID: {} add artwork with ID: {} to favorite", userId, artworkId);
        }

        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional(rollbackFor = {UserNotFoundException.class, AccessDeniedException.class})
    public UserResponseDto updateUser(UserUpdateDto updateDto, Long userId) throws AccessDeniedException, UserNotFoundException {
        log.info("Updating user with ID: {}", updateDto.getId());

        if (!updateDto.getId().equals(userId))
            throw new AccessDeniedException("You can change only your profile");

        UserEntity user = userRepository.findById(updateDto.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + updateDto.getId()));

        modelMapper.map(updateDto, user);

        log.info("Updated user with ID: {}", updateDto.getId());
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional(rollbackFor = {UserNotFoundException.class, CreatorNotFoundException.class})
    public UserResponseDto subscribeUser(Long userId, Long creatorId) throws UserNotFoundException, CreatorNotFoundException {
        log.info("User with ID: {} try to subscribe to creator with ID: {}", userId, creatorId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        CreatorEntity creator = creatorRepository.findById(creatorId)
                .orElseThrow(() -> new CreatorNotFoundException("Creator not found with ID: " + creatorId));

        if (user.getSubscriptions().contains(creator) && creator.getSubscribers().contains(user)) {
            user.unSubscribe(creator);

            log.info("User with ID: {} unsubscribe from creator with ID: {}", userId, creatorId);
        } else {
            user.subscribe(creator);

            log.info("User with ID: {} subscribed to creator with ID: {}", userId, creatorId);
        }

        return modelMapper.map(user, UserResponseDto.class);
    }
}