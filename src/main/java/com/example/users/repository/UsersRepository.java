package com.example.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.users.entity.UserEntity;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long> 
{
   UserEntity findByEmail(String email);

  UserEntity findByUserId(String userId);
}
