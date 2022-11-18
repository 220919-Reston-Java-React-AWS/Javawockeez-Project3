package com.revature.services;

import com.revature.exceptions.InvalidInputException;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import java.net.URI;
import java.util.Optional;

@Service
public class ProfileService {

    private final UserService userService;
    private final PostService postService;

    private final ProfileRepository profileRepository;

    public ProfileService(UserService userService, PostService postService, ProfileRepository profileRepository) {
        this.userService = userService;
        this.postService = postService;
        this.profileRepository = profileRepository;
    }

    // get the user by id (profile url)
    public Optional<User> findByCredentials(int id) {
        return userService.findByCredentials(id);
    }

    // get the list of post by user id
    public List<Post> getAllByAuthor(User author) {
        return postService.getAllByAuthor(author);
    }

    // get the profile of User
    public Optional<Profile> findByUser(User user){
        return profileRepository.findByUser(user);
    }

    // update the profile banner image
    public Profile patchProfileData(Profile update) {
        return this.profileRepository.saveAndFlush(update);
    }
}
