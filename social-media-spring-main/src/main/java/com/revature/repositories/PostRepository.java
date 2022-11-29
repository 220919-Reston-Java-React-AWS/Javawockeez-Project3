package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Post;

import javax.transaction.Transactional;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer>{

    // Find all posts by a user
    @Transactional
    List<Post> findAllByAuthorId(int author);
}
