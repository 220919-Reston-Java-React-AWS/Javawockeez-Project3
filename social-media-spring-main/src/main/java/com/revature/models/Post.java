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
    private int id;
	@Lob
	private String text;
	private String imageUrl;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Post> comments;
	@ManyToOne
	private User author;
	@Column(insertable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
	@GeneratedValue
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	Date postDate;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Post post = (Post) o;
		return getId() == post.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
