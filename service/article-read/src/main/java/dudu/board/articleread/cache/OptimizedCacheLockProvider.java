package dudu.board.articleread.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OptimizedCacheLockProvider {

    private static final String KEY_PREFIX = "optimized-cache-lock::";
    private static final Duration LOCK_TTL = Duration.ofSeconds(3);
    private final RedisTemplate redisTemplate;

    public boolean lock(String key) {
        return redisTemplate.opsForValue().setIfAbsent(
                generateLockKey(key),
                "",
                LOCK_TTL
        );
    }

    public void unlock(String key) {
        redisTemplate.delete(generateLockKey(key));
    }

    private String generateLockKey(String key) {
        return KEY_PREFIX + key;
    }
}
