package com.aerodream.user_service.Service;

import com.aerodream.user_service.Dto.User.UserCreateDto;
import com.aerodream.user_service.Dto.User.UserResponseDto;
import com.aerodream.user_service.Dto.User.UserUpdateDto;
import com.aerodream.user_service.Entity.UserEntity;
import com.aerodream.user_service.Enum.RoleEnum;
import com.aerodream.user_service.Exception.UserAlreadyExistException;
import com.aerodream.user_service.Exception.UserNotFoundException;
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
public class UserCRUDService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserResponseDto createUser(UserCreateDto createDto) throws MatchException, UserAlreadyExistException, IllegalAccessException {

        if (!createDto.getPassword().equals(createDto.getConfirmPassword()))
            throw new IllegalAccessException("Password and Confirm password not match");
        if (userRepository.existsByLogin(createDto.getLogin()) ||
                userRepository.existsByEmail(createDto.getEmail()))
            throw new UserAlreadyExistException("User already exist");

        log.info("Creating new user with login {} and email {}", createDto.getLogin(), createDto.getEmail());

        UserEntity user = modelMapper.map(createDto, UserEntity.class);
        user.getRoles().add(RoleEnum.ROLE_USER);
        user.setUsername(createDto.getLogin());
        UserEntity savedUser = userRepository.save(user);

        log.info("Created new user with ID: {}", savedUser.getId());

        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long userId) throws UserNotFoundException {
        log.info("Fetching user with ID: {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (user.isDeleted())
            throw new UserNotFoundException("User not found with ID: " + userId);

        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional(rollbackFor = {UserNotFoundException.class, AccessDeniedException.class})
    public UserResponseDto updateUser(UserUpdateDto updateDto, Long userId) throws UserNotFoundException {

        log.info("Updating user with ID: {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (user.isDeleted())
            throw new UserNotFoundException("User not found with ID: " + userId);

        modelMapper.map(updateDto, user);

        log.info("Updated user with ID: {}", userId);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional
    public void deleteUser(Long userId) {
        log.info("Deleting user with ID: {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        user.setDeleted(true);

        log.info("User with ID: {} is deleted {}", userId, user.isDeleted());
    }

    @Transactional
    public UserResponseDto recoveryUser(Long userId) {
        log.info("Recovery user with ID: {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        user.setDeleted(false);

        return modelMapper.map(user, UserResponseDto.class);
    }
}