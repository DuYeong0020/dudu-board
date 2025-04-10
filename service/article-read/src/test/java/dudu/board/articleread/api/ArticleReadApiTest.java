package dudu.board.articleread.api;

import dudu.board.articleread.service.response.ArticleReadResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class ArticleReadApiTest {
    RestClient restClient = RestClient.create("http://localhost:9005");

    @Test
    void restTest() {
        ArticleReadResponse response = restClient.get()
                .uri("v1/articles/{articleId}", 168574505104900096L)
                .retrieve()
                .body(ArticleReadResponse.class);

        System.out.println("response = " + response);
    }

}
