package com.revature.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.revature.exceptions.InvalidInputException;
import com.revature.models.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import com.revature.models.Post;
import com.revature.repositories.PostRepository;

import javax.transaction.Transactional;

// Service layer for media-post related logic
@Service
public class PostService {

	// The only repository this layer accesses
	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	//     ---------------------------------------------------------------------------------------     //

	// Get literally all posts/comments. They contain all their comments too.
	public List<Post> getAll() {
		return this.postRepository.findAll();
	}

	// Get all posts/comments by a given author.
	// added for all user profile posts by id
	@Transactional
	public List<Post> getAllByAuthor(User id) {
		return this.postRepository.findAllByAuthorId(id.getId());
	}

	// Get just the main posts (which contain their comments).
	public List<Post> getParents() {
		return ignoreComments( this.postRepository.findAll() );
	}

	//     ---------------------------------------------------------------------------------------     //

	// Add/modify a post (determined by ID)
	public Post upsert(Post post) {
		return this.postRepository.save(post);
	}

	//     ---------------------------------------------------------------------------------------     //

	// Find the post which has this as a comment. Throws an exception if none are found (it's a main post)
	// Required for the method remove() to work successfully (there has to be a better way, it's such a simple query)
	public Post findParent(Post post) throws InvalidInputException {
		// Get ALL the posts/comments
		List<Post> allPosts = getAll();

		// Search for the one that contains this as a comment (there should only be one, if any)
		for (Post testPost : allPosts){
			if (testPost.getComments().contains(post)){
				return testPost;
			}
		}

		// Throw an exception if there is no result (it's a main post)
		throw new InvalidInputException();
	}

	// Remove a post and all it's comments.
	@Transactional
	public void remove(Post post){

		// Remove the post from the parent (otherwise you get a foreign-key error)
		try {
			Post parent = findParent(post);
			parent.getComments().remove(post); // Remove this post from it's comments
			upsert(parent); // Save the parent

		} catch (InvalidInputException e) {} // Happens if there is no parent, in which case, do nothing and proceed.

		// Delete the post from the repository. Ideally, this would have been the only line.
		this.postRepository.delete(post);
	}

	//     ---------------------------------------------------------------------------------------     //

	// Remove anything that is a comment of another post.
	private @NotNull List<Post> ignoreComments(@NotNull List<Post> allPosts){

		// For every comment, on every post; get the post ID.

		// Must be done since we can't know if a given post has parents (this is a comment) or
		// grandchildren (comments within the posts comments) without checking all the others (or doing a simple SQL query on the post-comment table).
		HashSet<Integer> commentIds = new HashSet<Integer>();
		for (Post post : allPosts){
			for (Post comment : post.getComments()){
				commentIds.add( comment.getId() );
			}
		}

		// If it is not a comment, add it to the list of main posts.
		ArrayList<Post> parents = new ArrayList<>();
		for (Post post : allPosts){
			if ( !commentIds.contains( post.getId() ) ){
				parents.add(post);
			}
		}

		// Return the main posts.
		return parents;
	}
}
