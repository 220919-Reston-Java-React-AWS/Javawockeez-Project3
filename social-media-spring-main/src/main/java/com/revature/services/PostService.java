package com.revature.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.revature.exceptions.InvalidInputException;
import com.revature.models.User;
import org.jetbrains.annotations.NotNull;
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

	// added for all user profile posts by id
	public List<Post> getAllByAuthor(User id) {
		return this.postRepository.findAllByAuthorId(id.getId());
	}

	public List<Post> getParents() {
		return ignoreChildren( this.postRepository.findAll() );
	}

	public Post upsert(Post post) {
		return this.postRepository.save(post);
	}

	public Post findParent(Post post) throws InvalidInputException {
		List<Post> allPosts = getAll();

		for (Post testPost : allPosts){
			if (testPost.getComments().contains(post)){
				return testPost;
			}
//			for (Post comment : testPost.getComments()){
//				if (comment.getId() == post.getId()){
//					return testPost;
//				}
//			}
		}

		throw new InvalidInputException();
	}

	@Transactional
	public void remove(Post post){

		// Remove the post from the parent (otherwise you get a foreign-key error)
		try {
			Post parent = findParent(post);
			parent.getComments().remove(post);
			upsert(parent);

		} catch (InvalidInputException e) {} // Happens if there is no parent. Regardless, proceed as normal.

		this.postRepository.delete(post);
	}

	private @NotNull List<Post> ignoreChildren(@NotNull List<Post> allPosts){

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
