package dudu.board.hotarticle.service;

import dudu.board.hotarticle.repository.ArticleCommentCountRepository;
import dudu.board.hotarticle.repository.ArticleLikeCountRepository;
import dudu.board.hotarticle.repository.ArticleViewCountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class HotArticleScoreCalculatorTest {

    @InjectMocks
    HotArticleScoreCalculator hotArticleScoreCalculator;
    @Mock
    ArticleLikeCountRepository articleLikeCountRepository;
    @Mock
    ArticleViewCountRepository articleViewCountRepository;
    @Mock
    ArticleCommentCountRepository articleCommentCountRepository;

    @Test
    void calculateTest() {
        Long articleId = 1L;
        long likeCount = RandomGenerator.getDefault().nextLong(100);
        long commentCount = RandomGenerator.getDefault().nextLong(100);
        long viewCount = RandomGenerator.getDefault().nextLong(100);
        given(articleLikeCountRepository.read(articleId)).willReturn(likeCount);
        given(articleViewCountRepository.read(articleId)).willReturn(viewCount);
        given(articleCommentCountRepository.read(articleId)).willReturn(commentCount);

        // when
        long score = hotArticleScoreCalculator.calculate(articleId);

        // then
        assertEquals(likeCount * 3 + commentCount * 2 + viewCount * 1, score);
    }
}