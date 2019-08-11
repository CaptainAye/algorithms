package com.letatoma.solution;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

class SolutionTest {

    private static final int[] TEST_EXAMPLE_1 = {1, 5, 2, -2};
    private static final int EXPECTED_RESULT_1 = 0;

    private static final int[] TEST_EXAMPLE_2 = {3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
    private static final int EXPECTED_RESULT_2 = 0;

    private static final int[] TEST_EXAMPLE_3 = {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
    private static final int EXPECTED_RESULT_3 = 3;

    private static final int[] TEST_EXAMPLE_4 = {};
    private static final int EXPECTED_RESULT_4 = 0;

    private static final int[] TEST_EXAMPLE_5 = {3,3,3,4,5};
    private static final int EXPECTED_RESULT_5 = 0;

    private static final int[] TEST_EXAMPLE_6 = {3, 1};
    private static final int EXPECTED_RESULT_6 = 2;

    private static final int[] TEST_EXAMPLE_7 = {12, -10, 1, 1, 99, 86};
    private static final int EXPECTED_RESULT_7 = 7;

    private static final int[] TEST_EXAMPLE_8 = {1, 5, -2, 5, 2, 3};
    private static final int EXPECTED_RESULT_8 = 0;

    private static final int[] TEST_EXAMPLE_9 = {10, 11, 12, 13, 14, 15};
    private static final int EXPECTED_RESULT_9 = 1;

    private static final int[] TEST_EXAMPLE_10 = {10, 1, 1, 1, 1, 1, 1};
    private static final int EXPECTED_RESULT_10 = 4;

    @Nested
    class SummableListTests {

        @Test
        void testSummableList() {
            SummableList list = new SummableList();
            list.add(5);
            list.add(10);
            MatcherAssert.assertThat(list.getSum(), Is.is(15));
        }

        @Test
        void testStreamConversion() {
            int[] array = {-5, 10, 6, 15, -7};

            SummableList testList = Arrays.stream(array).boxed().collect(SummableList::new, SummableList::add, SummableList::addAll);
            MatcherAssert.assertThat(testList.getSum(), Is.is(19));

        }

        @Test
        void testRemove() {
            SummableList list = new SummableList();
            list.add(5);
            list.add(10);

            Integer element = list.get(1);
            list.remove(element);
            MatcherAssert.assertThat(list.getSum(), Is.is(5));
        }

        @Test
        void testEmpty() {
            MatcherAssert.assertThat(new SummableList().getSum(), Is.is(0));
        }



    }


    @Nested
    @DisplayName("Tests for codility")
    class CodilityTests {

        @DisplayName("tests example from codility")
        @Test
        void testCodilityExample() {
            //MatcherAssert.assertThat(new Solution().solution(TEST_EXAMPLE_1), Is.is(EXPECTED_RESULT_1));
            //MatcherAssert.assertThat(new Solution().solution(TEST_EXAMPLE_5), Is.is(EXPECTED_RESULT_5));
            //MatcherAssert.assertThat(new Solution().solution(TEST_EXAMPLE_6), Is.is(EXPECTED_RESULT_6));
            MatcherAssert.assertThat(new Solution().solution(TEST_EXAMPLE_8), Is.is(EXPECTED_RESULT_8));
            MatcherAssert.assertThat(new Solution().solution(TEST_EXAMPLE_9), Is.is(EXPECTED_RESULT_9));
            MatcherAssert.assertThat(new Solution().solution(TEST_EXAMPLE_10), Is.is(EXPECTED_RESULT_10));
        }
    }

    @Nested
    @DisplayName("Tests successful min counting")
    class OrdinaryTests {

        @DisplayName("tests example like even/odd number of the same element ")
        @Test
        void testExample() {
            MatcherAssert.assertThat(new Solution().solution(TEST_EXAMPLE_2), Is.is(EXPECTED_RESULT_2));
            MatcherAssert.assertThat(new Solution().solution(TEST_EXAMPLE_3), Is.is(EXPECTED_RESULT_3));
            MatcherAssert.assertThat(new Solution().solution(TEST_EXAMPLE_7), Is.is(EXPECTED_RESULT_7));
        }
    }

    @Nested
    @DisplayName("Tests corner cases")
    class CornerCaseTests {

        @DisplayName("tests example like no elements in the array etc.")
        @Test
        void testExample() {
            MatcherAssert.assertThat(new Solution().solution(TEST_EXAMPLE_4), Is.is(EXPECTED_RESULT_4));
        }
    }

    @Nested
    @DisplayName("Tests performance of the solution")
    //@Disabled
    class EfficiencyTests {

        @DisplayName("tests performance")
        @Test
        void testExample() {
            int[] noOfElements = {1, 100, 10_000, 100_000, 200_000};
            for (int elements : noOfElements) {
                int[] example = generateExample(elements);
                long startTime = System.currentTimeMillis();
                new Solution().solution(example);
                long endTime = System.currentTimeMillis();
                System.out.println("time is : " + (endTime - startTime) + " millis");
            }
        }
    }

    private int[] generateExample(int noOfElements) {
        int[] ret = new int[noOfElements];
        Random intGenerator = new Random();
        for (int i = 0; i < noOfElements; i++) {
            ret[i] = intGenerator.nextInt(201) - 100;
        }
        return ret;
    }

}