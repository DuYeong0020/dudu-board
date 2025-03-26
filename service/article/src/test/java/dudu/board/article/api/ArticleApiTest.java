package dudu.board.article.api;


import dudu.board.article.service.response.ArticlePageResponse;
import dudu.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

public class ArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9006");



    @Test
    void createTest() {
        ArticleResponse response = create(new ArticleCreateRequest(
                "hi", "my content", 1L, 1L
        ));
        System.out.println("response = " + response);
    }

    ArticleResponse create(ArticleCreateRequest request) {
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void readTest() {
        ArticleResponse response = read(163123349595648000L);
        System.out.println("response = " + response);
    }

    ArticleResponse read(Long articleId) {
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void updateTest() {
        update(163123349595648000L);
        ArticleResponse response = read(163123349595648000L);
        System.out.println("response = " + response);
    }

    void update(Long articleId) {
        restClient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(new ArticleUpdateRequest("hi 2", "my content 22"))
                .retrieve();
    }

    @Test
    void deleteTest() {
        restClient.delete()
                .uri("/v1/articles/{articleId}", 163123349595648000L)
                .retrieve();
    }

    @Test
    void readAllTest() {
        ArticlePageResponse response = restClient.get()
                .uri("/v1/articles?boardId=1&pageSize=30&page=1")
                .retrieve()
                .body(ArticlePageResponse.class);

        System.out.println("response.getArticleCount() = " + response.getArticleCount());
        for (ArticleResponse article : response.getArticles()) {
            System.out.println("article = " + article);
        }
    }

    @Test
    void readAllInfiniteScrollTest() {
        List<ArticleResponse> articles1 = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=10")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
                });
        System.out.println("firstPage");
        for (ArticleResponse articleResponse : articles1) {
            System.out.println("articleResponse.getArticleId() = " + articleResponse.getArticleId());
        }

        Long lastArticleId = articles1.get(articles1.size() - 1).getArticleId();
        List<ArticleResponse> articles2 = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=10&lastArticleId=%s".formatted(lastArticleId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
                });

        System.out.println("secondPage");
        for (ArticleResponse articleResponse : articles2) {
            System.out.println("articleResponse.getArticleId() = " + articleResponse.getArticleId());
        }
    }



    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }

}