package com.letatoma.solution;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

class   SolutionTest {

    private static final String TEST_CASE_1 = "abbacada";
    private static final int EXPECTED_RESULT_1 = 4;

    private static final String TEST_CASE_2 = "baababa";
    private static final int EXPECTED_RESULT_2 = 6;

    private static final String TEST_CASE_3 = "aaaaaaa";
    private static final int EXPECTED_RESULT_3 = 21;

    private static final String TEST_CASE_4 = "";
    private static final int EXPECTED_RESULT_4 = 0;

    private static final String TEST_CASE_5 = "a";
    private static final int EXPECTED_RESULT_5 = 0;

    private static final String TEST_CASE_6 = "tomek";
    private static final int EXPECTED_RESULT_6 = 0;

    private static final String TEST_CASE_7 = "abcdefghijklmnoqprstuwxyzzyxwutsrpqonmlkjihgfedcba";
    private static final int EXPECTED_RESULT_7 = 25;

    private static final String TEST_CASE_8 = "abababbbabbbaba";
    private static final int EXPECTED_RESULT_8 = 24;

    private static final char FIRST_LETTER_IN_RANGE = 'a';
    private static final char LAST_LETTER_IN_RANGE = 'z';


    private Algorithm algorithm = s -> new Solution().solutionFastest(s);

    @DisplayName("Test cases from codility should pass")
    @Test
    void testCodilityTestCases(){
        MatcherAssert.assertThat(algorithm.solution(TEST_CASE_1), Is.is(EXPECTED_RESULT_1));
        MatcherAssert.assertThat(algorithm.solution(TEST_CASE_2), Is.is(EXPECTED_RESULT_2));
        MatcherAssert.assertThat(algorithm.solution(TEST_CASE_3), Is.is(EXPECTED_RESULT_3));
        MatcherAssert.assertThat(algorithm.solution(TEST_CASE_4), Is.is(EXPECTED_RESULT_4));
        MatcherAssert.assertThat(algorithm.solution(TEST_CASE_7), Is.is(EXPECTED_RESULT_7));
        MatcherAssert.assertThat(algorithm.solution(TEST_CASE_8), Is.is(EXPECTED_RESULT_8));

    }

    @DisplayName("Tests corner cases with no palindromes")
    @Test
    void testCodilityNoPalindromes() {
        MatcherAssert.assertThat(algorithm.solution(TEST_CASE_4), Is.is(EXPECTED_RESULT_4));
        MatcherAssert.assertThat(algorithm.solution(TEST_CASE_5), Is.is(EXPECTED_RESULT_5));
        MatcherAssert.assertThat(algorithm.solution(TEST_CASE_6), Is.is(EXPECTED_RESULT_6));
    }

    @DisplayName("Displays performance of the algorithms for different sized one letter palindormes")
    @Test
    void testEfficiency() {
        runEfficiencyTest(SolutionTest::fillWithConstantValue, 10);
        runEfficiencyTest(SolutionTest::fillWithConstantValue,100);
        runEfficiencyTest(SolutionTest::fillWithConstantValue,1000);
        runEfficiencyTest(SolutionTest::fillWithConstantValue,10_000);
        runEfficiencyTest(SolutionTest::fillWithConstantValue,100_000);
        runEfficiencyTest(SolutionTest::fillWithConstantValue,1_000_000);

        // Run tests for chaotic input
        runEfficiencyTest(SolutionTest::fillWithRandomChars, 10);
        runEfficiencyTest(SolutionTest::fillWithRandomChars,100);
        runEfficiencyTest(SolutionTest::fillWithRandomChars,1000);
        runEfficiencyTest(SolutionTest::fillWithRandomChars,10_000);
        runEfficiencyTest(SolutionTest::fillWithRandomChars,100_000);
        runEfficiencyTest(SolutionTest::fillWithRandomChars,1_000_000);

        // Run tests for chaotic input
        runEfficiencyTest(SolutionTest::fillWithTwoChars, 10);
        runEfficiencyTest(SolutionTest::fillWithTwoChars,100);
        runEfficiencyTest(SolutionTest::fillWithTwoChars,1000);
        runEfficiencyTest(SolutionTest::fillWithTwoChars,10_000);
        runEfficiencyTest(SolutionTest::fillWithTwoChars,200_000);
        runEfficiencyTest(SolutionTest::fillWithTwoChars,1_000_000);

    }

    private void runEfficiencyTest(Filler filler, int stringSize) {
        StringBuffer testedPalindrome = filler.fill(stringSize);
        System.out.println(testedPalindrome);
        long start = System.currentTimeMillis();
        algorithm.solution(testedPalindrome.toString());
        long end = System.currentTimeMillis();
        System.out.println("Algorithm time for size : " + stringSize + " is " + (end - start) + " ms");
    }

    private static StringBuffer fillWithConstantValue(int fillSize) {
        char fillChar = 'a';
        StringBuffer testedPalindrome = new StringBuffer(fillSize);
        for (int i = 0; i < fillSize; i++) {
            testedPalindrome.append(fillChar);
        }
        return testedPalindrome;

    }

    private static StringBuffer fillWithRandomChars(int fillSize) {
        Random randomCharGenerator = new Random();
        StringBuffer testedPalindrome = new StringBuffer(fillSize);
        for (int i = 0; i < fillSize; i++) {
            char fillChar = (char) (randomCharGenerator.nextInt(LAST_LETTER_IN_RANGE - FIRST_LETTER_IN_RANGE + 1) + FIRST_LETTER_IN_RANGE);
            testedPalindrome.append(fillChar);
        }
        return testedPalindrome;

    }

    private static StringBuffer fillWithTwoChars(int fillSize) {
        StringBuffer testedPalindrome = new StringBuffer(fillSize);
        char fillChar = 'a';
        for (int i = 0; i < fillSize; i++) {
            testedPalindrome.append(fillChar);
            fillChar = fillChar == 'a' ? 'b' : 'a';
        }
        return testedPalindrome;

    }

    private interface Filler {
        StringBuffer fill(int fillSize);
    }

    private interface Algorithm {
        int solution(String s);
    }

}