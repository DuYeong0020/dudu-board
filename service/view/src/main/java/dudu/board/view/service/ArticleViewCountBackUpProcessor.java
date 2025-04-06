package dudu.board.view.service;

import dudu.board.common.outboxmessagerelay.OutboxEventPublisher;
import dudu.board.view.entity.ArticleViewCount;
import dudu.board.view.repository.ArticleViewCountBackUpRepository;
import kuke.board.common.event.EventType;
import kuke.board.common.event.payload.ArticleViewedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ArticleViewCountBackUpProcessor {

    private final OutboxEventPublisher outboxEventPublisher;
    private final ArticleViewCountBackUpRepository articleViewCountBackUpRepository;

    @Transactional
    public void backUp(Long articleId, Long viewCount) {
        int result = articleViewCountBackUpRepository.updateViewCount(articleId, viewCount);
        if (result == 0) {
            articleViewCountBackUpRepository.findById(articleId)
                    .ifPresentOrElse(ignored -> {},
                            () -> articleViewCountBackUpRepository.save(
                                    ArticleViewCount.init(articleId, viewCount)));
        }
        outboxEventPublisher.publish(
                EventType.ARTICLE_VIEWED,
                ArticleViewedEventPayload.builder()
                        .articleId(articleId)
                        .articleViewCount(viewCount)
                        .build(),
                articleId
        );
    }
}
