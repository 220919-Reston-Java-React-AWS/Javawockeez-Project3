package com.revature.models;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@OnDelete(action = OnDeleteAction.CASCADE)
    private int id; // Unique identifier for each post

	@Lob
	private String text; // The text of the posts/comment. Stored as a lob, so length isn't as limited, but access problems can arise.

	private String imageUrl; // Url of an image to be included with a post.

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
	private List<Post> comments; // Comments on the post (basically sub-posts)

	@ManyToOne
	private User author; // The user who made the post

	@Column(insertable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
	@GeneratedValue
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP) // I don't know why so many annotations were needed on this one, seems awfully redundant...
	Date postDate; // The date the post was posted.


	// Basically, only look at the ID when checking if posts are equal. Makes editing smoother.
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Post post = (Post) o;
		return getId() == post.getId();
	}

	// Ruins our test coverage here because it's never used, but you should do this with the method above.
	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
