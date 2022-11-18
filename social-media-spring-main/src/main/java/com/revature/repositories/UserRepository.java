package com.revature.repositories;

import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);


    /*updating password custom query
    @Modifying
    @Query("update users u set u.password = :password where u.email = :email")
    void updatePassword(@Param(value = "email") String email, @Param(value = "password") String password);*/
}
