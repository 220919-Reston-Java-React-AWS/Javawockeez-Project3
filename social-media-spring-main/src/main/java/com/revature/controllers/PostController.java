package com.revature.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.annotations.Authorized;
import com.revature.models.Post;
import com.revature.services.PostService;

// The endpoint for all post/comment requests
@RestController
@RequestMapping("/post")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class PostController {

    // The only service-layer class this uses
	private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Get literally all posts and comments. Note that you will get a comment separately even if it appears within another post/comment
    @Authorized
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(this.postService.getAll());
    }

    // Get just the main posts (with their comments included), and not something that will appear in another.
    @Authorized
    @GetMapping
    public ResponseEntity<List<Post>> getParents() {
        return ResponseEntity.ok(this.postService.getParents());
    }

    // Add/modify a post (which is determined by whether the ID already exists or not)
    @Authorized
    @PutMapping
    public ResponseEntity<Post> upsertPost(@RequestBody Post post) {
    	return ResponseEntity.ok(this.postService.upsert(post));
    }

    // Delete a post/comment, along with all it's children
    @Authorized
    @DeleteMapping
    public ResponseEntity removePost(@RequestBody Post post) {
        this.postService.remove(post);
        return ResponseEntity.ok("Removed Successfully"); // The only one that returns text, and not JSON
    }
}
