package com.mobiliz.repository;

import com.mobiliz.repository.entity.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserProfileRepository extends MongoRepository<UserProfile,String> {
    Optional<UserProfile> findByAuthId(Long authId);
    Optional<UserProfile> findByEmail(String email);
}
