package com.aerodream.user_service.Service;

import com.aerodream.user_service.Dto.User.UserCreateDto;
import com.aerodream.user_service.Dto.User.UserResponseDto;
import com.aerodream.user_service.Dto.User.UserUpdateDto;
import com.aerodream.user_service.Exception.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeycloakClient keycloakClient;
    private final UserCRUDService crudService;

    public UserResponseDto createUser(UserCreateDto createDto) throws UserAlreadyExistException, IllegalAccessException {
        UserResponseDto responseDto = crudService.createUser(createDto);

        keycloakClient.createUser(createDto, responseDto.getId());

        return responseDto;
    }

    public UserResponseDto getUser(Long userId) {
        return crudService.getUser(userId);
    }

    public UserResponseDto updateUser(UserUpdateDto updateDto, Long userId) {
        return crudService.updateUser(updateDto, userId);
    }

    public void deleteUser(Long userId) {
        crudService.deleteUser(userId);
    }

    public UserResponseDto recoveryUser(Long userId) {
        return crudService.recoveryUser(userId);
    }
}
