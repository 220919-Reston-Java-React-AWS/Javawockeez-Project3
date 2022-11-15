package com.revature.services;

import java.util.List;

import com.revature.models.User;
import org.springframework.stereotype.Service;

import com.revature.models.Post;
import com.revature.repositories.PostRepository;

@Service
public class PostService {

	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public List<Post> getAll() {
		return this.postRepository.findAll();
	}

	// added for all user profile posts by id
	public List<Post> getAllByAuthor(User id) {
		return this.postRepository.findAllByAuthor(id);
	}

	public Post upsert(Post post) {
		return this.postRepository.save(post);
	}

	public void remove(Post post){
		this.postRepository.delete(post);
	}
}
