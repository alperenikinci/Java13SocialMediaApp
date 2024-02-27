package com.bilgeadam.mapper;

import com.bilgeadam.domain.UserProfile;
import com.bilgeadam.rabbitmq.model.RegisterElasticModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ElasticMapper {

    ElasticMapper INSTANCE = Mappers.getMapper(ElasticMapper.class);

    UserProfile fromRegisterElasticModelToUserProfile(final RegisterElasticModel registerElasticModel);
}
