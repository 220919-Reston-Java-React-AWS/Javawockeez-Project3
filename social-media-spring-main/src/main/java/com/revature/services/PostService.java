package com.revature.services;

import java.util.ArrayList;
import java.util.HashSet;
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

	public List<Post> getParents() {
		return removeChildren( this.postRepository.findAll() );
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


	private List<Post> removeChildren(List<Post> allPosts){

		// Must be done first since we can't know if post has parents or grandchildren yet
		HashSet<Integer> commentIds = new HashSet<Integer>();
		for (Post post : allPosts){
			for (Post comment : post.getComments()){
				commentIds.add( comment.getId() );
			}
		}

		ArrayList<Post> parents = new ArrayList<>();
		for (Post post : allPosts){
			if ( !commentIds.contains( post.getId() ) ){
				parents.add(post);
			}
		}

		return parents;
	}
}
