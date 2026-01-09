package com.aerodream.user_service.Config;

import com.aerodream.user_service.Dto.Creator.CreatorCreateDto;
import com.aerodream.user_service.Dto.Creator.CreatorResponseDto;
import com.aerodream.user_service.Dto.Creator.CreatorUpdateDto;
import com.aerodream.user_service.Dto.User.UserCreateDto;
import com.aerodream.user_service.Dto.User.UserResponseDto;
import com.aerodream.user_service.Dto.User.UserUpdateDto;
import com.aerodream.user_service.Entity.CreatorEntity;
import com.aerodream.user_service.Entity.UserEntity;
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

        configureCreatorMappings(modelMapper);
        configureUserMappings(modelMapper);

        return modelMapper;
    }

    private void configureUserMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(UserCreateDto.class, UserEntity.class)
                .addMappings(mapping -> {
                    mapping.map(UserCreateDto::getLogin, UserEntity::setLogin);
                    mapping.map(UserCreateDto::getEmail, UserEntity::setEmail);
                });
        modelMapper.typeMap(UserEntity.class, UserResponseDto.class)
                .addMappings(mapping -> {
                    mapping.map(UserEntity::getId, UserResponseDto::setId);
                    mapping.map(UserEntity::getUsername, UserResponseDto::setUsername);
                    mapping.map(UserEntity::getEmail, UserResponseDto::setEmail);
                    mapping.map(UserEntity::getRoles, UserResponseDto::setRoles);
                    mapping.map(UserEntity::getCreatedAt, UserResponseDto::setCreatedAt);
                    mapping.map(UserEntity::getSavedArtworksId, UserResponseDto::setSavedArtworksId);
                    mapping.map(UserEntity::getSubscriptions, UserResponseDto::setSubscriptions);
                    mapping.skip(UserResponseDto::setCreatorId);
                    mapping.skip(UserResponseDto::setCreator);
                });
        modelMapper.typeMap(UserUpdateDto.class, UserEntity.class)
                .addMappings(mapping -> {
                    mapping.map(UserUpdateDto::getUsername, UserEntity::setUsername);
                    mapping.map(UserUpdateDto::getEmail, UserEntity::setEmail);
                });
    }

    private void configureCreatorMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(CreatorCreateDto.class, CreatorEntity.class)
                .addMappings(mapping -> {
                    mapping.map(CreatorCreateDto::getFirstname, CreatorEntity::setFirstname);
                    mapping.map(CreatorCreateDto::getLastname, CreatorEntity::setLastname);
                    mapping.skip(CreatorEntity::setId);
                });
        modelMapper.typeMap(CreatorEntity.class, CreatorResponseDto.class)
                .addMappings(mapping -> {
                    mapping.map(CreatorEntity::getId, CreatorResponseDto::setCreatorId);
                    mapping.map(context -> context.getUser().getId(), CreatorResponseDto::setUserId);
                    mapping.map(CreatorEntity::getFirstname, CreatorResponseDto::setFirstName);
                    mapping.map(CreatorEntity::getLastname, CreatorResponseDto::setLastName);
                    mapping.map(CreatorEntity::getCollectionsId, CreatorResponseDto::setCollectionsId);
                    mapping.map(CreatorEntity::isReadyForOrder, CreatorResponseDto::setReadyForOrder);
                    mapping.skip(CreatorResponseDto::setSubscribers);
                });
        modelMapper.typeMap(CreatorUpdateDto.class, CreatorEntity.class)
                .addMappings(mapping -> {
                    mapping.map(CreatorUpdateDto::isReadyForOrder, CreatorEntity::setReadyForOrder);
                    mapping.skip(CreatorEntity::setId);
                });
    }
}
