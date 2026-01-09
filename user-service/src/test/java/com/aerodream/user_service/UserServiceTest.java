package com.aerodream.user_service;

import com.aerodream.user_service.Dto.User.UserCreateDto;
import com.aerodream.user_service.Dto.User.UserResponseDto;
import com.aerodream.user_service.Entity.UserEntity;
import com.aerodream.user_service.Enum.RoleEnum;
import com.aerodream.user_service.Exception.UserAlreadyExistException;
import com.aerodream.user_service.Repository.UserRepository;
import com.aerodream.user_service.Service.UserCRUDService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserCRUDService userService;

    @Test
    void createUser() throws AccessDeniedException, UserAlreadyExistException {
        UserCreateDto createDto = new UserCreateDto();
        createDto.setLogin("login");
        createDto.setEmail("email");
        createDto.setPassword("123");
        createDto.setConfirmPassword("123");

        UserEntity userToSave = new UserEntity();
        userToSave.setLogin(createDto.getLogin());
        userToSave.setEmail(createDto.getEmail());

        UserEntity savedUser = new UserEntity();
        savedUser.setId(1L);
        savedUser.setLogin(createDto.getLogin());
        savedUser.setEmail(createDto.getEmail());
        userToSave.getRoles().add(RoleEnum.ROLE_USER);

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(savedUser.getId());
        responseDto.setUsername(savedUser.getLogin());
        responseDto.setEmail(savedUser.getEmail());
        responseDto.setRoles(new HashSet<>());
        responseDto.getRoles().add(RoleEnum.ROLE_USER);

        when(modelMapper.map(savedUser, UserResponseDto.class)).thenReturn(responseDto);
        when(modelMapper.map(createDto, UserEntity.class)).thenReturn(userToSave);

        when(userRepository.save(userToSave)).thenReturn(savedUser);

//        UserResponseDto result = userService.createUser(createDto);

        verify(userRepository).save(argThat(user ->
                user.getLogin().equals(createDto.getLogin()) &&
                        user.getEmail().equals(createDto.getEmail())
        ));
        verify(modelMapper).map(createDto, UserEntity.class);
        verify(modelMapper).map(savedUser, UserResponseDto.class);

//        assertEquals(result.getLogin(), createDto.getLogin());
//        assertEquals(result.getEmail(), createDto.getEmail());
    }

    @Test
    void getUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setLogin("login");
        user.setEmail("email");
        user.getRoles().add(RoleEnum.ROLE_USER);

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setEmail(user.getEmail());
        responseDto.setRoles(user.getRoles());

        when(modelMapper.map(user, UserResponseDto.class)).thenReturn(responseDto);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDto result = userService.getUser(1L);

        assertEquals(result.getId(), user.getId());
        assertEquals(result.getEmail(), user.getEmail());
    }

//    @Test
//    void userSaveOrDeleteArtwork() {
//
//    }

//    @Test
//    void updateUser_UpdatingLogin() throws AccessDeniedException {
//        UserUpdateDto updateDto = new UserUpdateDto();
//        updateDto.setId(1L);
//        updateDto.setLogin("login");
//
//        UserEntity user = new UserEntity();
//        user.setId(1L);
//        user.setLogin("old login");
//        user.setEmail("email");
//        user.setPassword("123");
//        user.getRoles().add(RoleEnum.ROLE_USER);
//
//        UserEntity updatedUser = new UserEntity();
//        user.setId(1L);
//        user.setLogin(updateDto.getLogin());
//        user.setEmail("email");
//        user.setPassword("123");
//        user.getRoles().add(RoleEnum.ROLE_USER);
//
//        UserResponseDto responseDto = new UserResponseDto();
//        responseDto.setId(user.getId());
//        responseDto.setLogin(updateDto.getLogin());
//        responseDto.setEmail(user.getEmail());
//        responseDto.setRoles(user.getRoles());
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        when(modelMapper.map(updateDto, user)).thenReturn(updatedUser);
//
//        when(modelMapper.map(user, UserResponseDto.class)).thenReturn(responseDto);
//
//        UserResponseDto result = userService.updateUser(updateDto, 1L);
//
//        assertEquals(result.getLogin(), updateDto.getLogin());
//    }
}
