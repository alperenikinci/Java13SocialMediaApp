package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateUserRequestDto;
import com.bilgeadam.dto.request.UserProfileUpdateRequestDto;
import com.bilgeadam.dto.response.UserProfileResponseDto;
import com.bilgeadam.entity.UserProfile;
import com.bilgeadam.rabbitmq.model.RegisterElasticModel;
import com.bilgeadam.rabbitmq.model.RegisterModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserProfileMapper {

    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);
    UserProfile fromCreateRequestToUserProfile(final CreateUserRequestDto dto);

    UserProfile fromUpdateRequestToUserProfile (final UserProfileUpdateRequestDto dto);

    UserProfile fromRegisterModelToUserProfile(RegisterModel model);

    CreateUserRequestDto fromRegisterModelToCreateUserDto(final RegisterModel registerModel);
    RegisterElasticModel fromUserProfileToRegisterElasticModel(final UserProfile userProfile);

    UserProfileResponseDto fromUserProfileToUserProfileResponse(final UserProfile userProfile);

}
