// package com.revature.serviceTests;


// import com.revature.models.Post;
// import com.revature.repositories.PostRepository;
// import com.revature.services.PostService;
// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.springframework.boot.test.context.SpringBootTest;

// import static org.mockito.Mockito.when;

// @SpringBootTest
// public class PostServiceTest {

//     @Mock
//     PostRepository postRepository;

//     @InjectMocks
//     PostService ps;

//     @Test
//     public void upsert_INSERT_post_EXPECT_post(){
//         Post tmpPost = new Post();
//         when(postRepository.save(tmpPost)).thenReturn(tmpPost);

//         Assertions.assertEquals(tmpPost, ps.upsert(tmpPost));
//     }
// }
