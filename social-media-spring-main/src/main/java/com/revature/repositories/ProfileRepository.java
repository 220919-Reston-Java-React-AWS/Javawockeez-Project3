package com.revature.repositories;

import com.revature.models.Profile;
import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer>{

    @Transactional
    Optional<Profile> findByUser(User user);
}
