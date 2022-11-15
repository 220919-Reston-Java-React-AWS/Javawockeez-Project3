INSERT INTO users (id, email, password, first_name, last_name) VALUES (
    1,
    'testuser@gmail.com',
    'password',
    'Test',
    'User'
);

INSERT INTO posts (id, text, image_url, author_id) VALUES (
    10000,
    'The classic',
    'https://i.imgur.com/fhgzVEt.jpeg',
    1
),
(
    10001,
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
    '',
    1
);

INSERT INTO profile (about, avatar_image_url, banner_image_url, user_id) VALUES (
    'Hi. I am a test user, and I love Windows 95.',
    'https://i.imgur.com/gVXqYHR.jpeg',
    'https://i.imgur.com/MrGY5EL.jpeg',
    1
);