package com.bilgeadam.repository;

import com.bilgeadam.domain.UserProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserProfileRepository  extends ElasticsearchRepository<UserProfile,String> {

}
