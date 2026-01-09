package com.aerodream.user_service.Controller;

import com.aerodream.user_service.Dto.User.UserCreateDto;
import com.aerodream.user_service.Dto.User.UserResponseDto;
import com.aerodream.user_service.Dto.User.UserUpdateDto;
import com.aerodream.user_service.Exception.UserAlreadyExistException;
import com.aerodream.user_service.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserCreateDto createDto) throws UserAlreadyExistException, IllegalAccessException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(createDto));
    }

    @GetMapping("/{userId:\\d+}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PatchMapping("/edit")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserUpdateDto updateDto, @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(userService.updateUser(updateDto, Long.parseLong(userId)));
    }
}