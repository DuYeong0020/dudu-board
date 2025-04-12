package dudu.board.articleread.cache;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class OptimizedCacheTTLTest  {

    @Test
    void ofTest() {
        // given
        long ttlSeconds = 10L;

        // when
        OptimizedCacheTTL optimizedCacheTTL = OptimizedCacheTTL.of(ttlSeconds);

        // then
        assertEquals(optimizedCacheTTL.getLogicalTTL(), Duration.ofSeconds(ttlSeconds));
        assertEquals(optimizedCacheTTL.getPhysicalTTL(),
                Duration.ofSeconds(ttlSeconds).plusSeconds(OptimizedCacheTTL.PHYSICAL_TTL_DELAY_SECONDS));
    }
}