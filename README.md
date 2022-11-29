# Javawockeez-Project3

Spring-Backend

#### Endpoints
The urls shown should be affixed to either [the local host](http://localhost:8080) or [the online host](http://220919javawockeezcapstonebackend-env.eba-gtdtyrfr.us-east-1.elasticbeanstalk.com), depending on whether you are running a ocal version of the back-end or not.

###### Posts
	GET /post
		- Returns all main posts, along with their comments.

	PUT /post
		- Body
		- Returns the post along with updated information (i.e. ID and post-date)

	GET /post/all
		- Returns all posts and comments, along with any comments inside them.