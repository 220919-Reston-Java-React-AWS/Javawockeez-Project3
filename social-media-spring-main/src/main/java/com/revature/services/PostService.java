package com.revature.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.models.Post;
import com.revature.repositories.PostRepository;

import javax.transaction.Transactional;

@Service
public class PostService {

	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public List<Post> getAll() {
		return this.postRepository.findAll();
	}

	public Post upsert(Post post) {
		return this.postRepository.save(post);
	}

	@Transactional
	public void remove(Post post){

		for (Post comment: post.getComments()){
			remove(comment);
		}

		this.postRepository.delete(post);
	}
}
