package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateFollowRequestDto;
import com.bilgeadam.entity.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IFollowMapper {

    IFollowMapper INSTANCE= Mappers.getMapper(IFollowMapper.class);
    Follow fromFollowRequestToFollow(final CreateFollowRequestDto dto, final String followingUsersId);
}