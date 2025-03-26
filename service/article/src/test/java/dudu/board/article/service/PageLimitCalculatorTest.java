package dudu.board.article.service;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageLimitCalculatorTest {

    @Test
    void calculatePageLimitTest() {
        calculatePageListTest(1L, 30L, 10L, 301L);
        calculatePageListTest(11L, 30L, 10L, 601L);
        calculatePageListTest(31L, 3L, 10L, 121L);
    }

    void calculatePageListTest(Long page, Long pageSize, Long movablePageCount, Long expected) {
        Long result = PageLimitCalculator.calculatePageLimit(page, pageSize, movablePageCount);
        assertThat(result).isEqualTo(expected);
    }
}