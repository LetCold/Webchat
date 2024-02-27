package com.phamthainguyen.webchat.responsitory;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phamthainguyen.webchat.models.entity.User;



public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
