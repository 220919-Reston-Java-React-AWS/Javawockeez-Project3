# Javawockeez-Project3

Spring-Backend

#### Endpoints
The urls shown should be affixed to either [the local host](http://localhost:8080) or [the online host](http://220919javawockeezcapstonebackend-env.eba-gtdtyrfr.us-east-1.elasticbeanstalk.com), depending on whether you are running a ocal version of the back-end or not.


###### Account
Responsible for updating a user's information.

	PATCH /account/update-account
		- Requires body to contain JSON with the Account-Update-Request object (basically, a user object: first name, last name, email, and password)
		- Id of the object must be that of the user being updated, that field cannot change.
		- Returns the updated profile.

###### Profile
Responsible for updating a user's profile (very similar to the one above). Contains information not about the user, but specific to the user (i.e. posts they made, personal-page settings, and security question information)

	GET /profile/posts/{id}
		- {id} must be filled with the user's id, i.e. /profile/posts/1
		- Returns all posts/comments made by the user.

	GET /profile/user/{id}
		- {id} must be filled with the user's id, i.e. /profile/posts/1
		- Returns user information (their personal information)

	GET /profile/page/{id}
		- {id} must be filled with the user's id, i.e. /profile/page/1
		- Returns the user's profile (page-settings)

	PATCH /profile/update-profile
		- Requires body to contain JSON with the updated Profile (same as what is returned from GET /profile/page/{id})
		- ID of the updated profile must be consistant
		- Returns the updated profile

	GET /profile/questions1
		- Returns a list of questions for the first Security Question.
		- Identical to the endpoint in authentication.

	GET /profile/questions2
		- Returns a list of questions for the second Security Question.
		- Identical to the endpoint in authentication.

	GET /profile/questions3
		- Returns a list of questions for the third Security Question.
		- Identical to the endpoint in authentication.

	POST /profile/update-questions/{id}
		- {id} must be filled with the user's id, i.e. /profile/update-questions/1
		- Requires an Update-Questions object (containing all three of the new questions with their answers).
	

###### Authentication
Responsible for login, logout, registration, and forgot-password-help. Basically, covers everything you would see from the login page in the front end.

	POST /auth/login
		- Requires body to contain JSON with the login object (email and password).
		- Returns JSON of full user object/information (password excluded) if login is successful.
		- Returns JSON of an exception detailing the issue (bad email/password) if login is unsuccessful.

	POST /auth/logout
		- Requires nothing (if the user is recognized by the system, they are removed; otherwise they are not logged into the back-end to begin with)
		- Returns nothing (other than a success status)

	POST /auth/register
		- Requires body to contain JSON with the Register-Request object (user's First Name, Last Name, Email, Password; and three security questions with their answers)
		- Returns JSON of the created user (password excluded)

	POST /auth/forgot-password
		- Requires body to contain JSON with the Question-Request object (the email and password of the user, and the 3 security questions and the user's answer.
		- Returns text saying the "Password successfully updated" if successful.
		- Returns nothing (other than a bad-status code) if unsuccessful.

	GET /auth/questions1
		- Returns a list of questions for the first Security Question.
		- Identical to the endpoint in profile.

	GET /auth/questions2
		- Returns a list of questions for the second Security Question.
		- Identical to the endpoint in profile.

	GET /auth/questions3
		- Returns a list of questions for the third Security Question.
		- Identical to the endpoint in profile.

	GET /auth/security-questions/{email}
		- {email} is to be filled in, i.e. /auth/security-questions/test@test.net
		- Returns a list of the users security questions and their answers.

###### Posts
Responsible for all information on social-media posts, along with comments (which are just sub-posts).
	GET /post
		- Returns all main posts, along with their comments.

	PUT /post
		- Requires body to contain JSON with the post being edited/uploaded. If the ID exists in the system, that post will be updated. If it does not, a new post will be created and the ID will be automatically generated and assigned.
		- Returns JSON of the post, along with updated information (i.e. ID and post-date)

	DELETE /post
		- Requires body to contain JSON with the post being deleted (particularly the ID and comments)
		- Returns a success message, even if the post was not found.

	GET /post/all
		- Returns all posts and comments, along with any comments inside them.