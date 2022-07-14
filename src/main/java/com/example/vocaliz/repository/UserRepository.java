package com.example.vocaliz.repository;

import com.example.vocaliz.userModule.entity.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface UserRepository extends MongoRepository<AppUser, String> {
    boolean existsByEmail(String email);
    AppUser findByUserId(String userId);
    AppUser findByEmail(String email);
}
