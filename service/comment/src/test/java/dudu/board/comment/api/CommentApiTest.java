package dudu.board.comment.api;

import dudu.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class CommentApiTest {

    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create() {
        CommentResponse response1 = createComment(new CommentCreateRequest(1L, "my comment1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(1L, "my comment1", response1.getCommentId(), 1L));
        CommentResponse response3 = createComment(new CommentCreateRequest(1L, "my comment1", response1.getCommentId(), 1L));

        System.out.printf("commentId=%s", response1.getCommentId());
        System.out.printf("\tcommentId=%s", response2.getCommentId());
        System.out.printf("\tcommentId=%s", response3.getCommentId());
        // commentId=163837987290750976	commentId=163837987445940224	commentId=163837987487883264
    }

    @Test
    void read() {
        CommentResponse response = restClient.get()
                .uri("/v1/comments/{commentId}", 163837987487883264L)
                .retrieve()
                .body(CommentResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void delete() {
        // commentId=163837987290750976
        //      commentId=163837987445940224
        //      commentId=163837987487883264
        restClient.delete()
                .uri("/v1/comments/{commentId}", 163837987487883264L)
                .retrieve();
    }

    CommentResponse createComment(CommentCreateRequest request) {
        return restClient.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }



    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequest {
        private Long articleId;
        private String content;
        private Long parentCommentId;
        private Long writerId;
    }
}
