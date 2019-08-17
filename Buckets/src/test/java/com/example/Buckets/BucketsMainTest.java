package com.example.Buckets;

import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Assertions for buckets task")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BucketsMainTest {

    BucketsMain buckets;
    @BeforeAll
    private void setBuckets(){
        buckets = new BucketsMain();
    }

    @Nested
    @DisplayName("When solution is correct")
    class WhenSolutionIsCorrect {
        @Test
        @DisplayName("Return should be 4 as the first example")
        void correctSolutionTesting() {
            int n = 3;
            int q = 2;
            int[] b = {1, 2, 0, 1, 1, 0, 0, 1};
            int[] c = {0, 3, 0, 2, 0, 3, 0, 0};
            assertThat(buckets.solution(n,q,b,c),is(4));
        }

        @Test
        @DisplayName("Return should be -1 as the second example")
        void correctSolutionTesting2() {
            int n = 2;
            int q = 2;
            int[] b = {0, 1};
            int[] c = {5, 5,};
            assertThat(buckets.solution(n,q,b,c),is(-1));
        }

        @Test
        @DisplayName("Return should be 5 as the third example")
        void correctSolutionTesting3() {
            int n = 2;
            int q = 2;
            int[] b = {0, 1, 0, 1, 0, 1};
            int[] c = {1, 3, 0, 0, 3, 3};
            assertThat(buckets.solution(n,q,b,c),is(5));
        }

    }

}