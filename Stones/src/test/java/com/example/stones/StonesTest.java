package com.example.stones;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class StonesTest {

    private List<Integer> testInputOne = Arrays.asList(1,1,2,4,3);
    private List<Integer> testOutputOne = Arrays.asList(2,2,2,3,2);
    private int expectedResultOne = 3;
    private List<Integer> testInputTwo = Arrays.asList(0, 0, 2, 1, 8, 8, 2, 0);
    private List<Integer> testOutputTwo = Arrays.asList(8, 5, 2, 4, 0, 0, 0, 2);
    private int expectedResultTwo = 31;

    private List<Integer> testInputThree = Arrays.asList(1000000000, 1000000000, 1000000000, 0, 0, 0);
    private List<Integer> testOutputThree = Arrays.asList( 0, 0, 0, 1000000000, 1000000000, 1000000000);
    private int expectedResultThree = 999999972;


    private List<Integer> testInputFour = Arrays.asList(2);
    private List<Integer> testOutputFour = Arrays.asList( 1);
    private int expectedResultFour = -1;

    private Stones stones = new Stones();




    @Test
    void assertStonesCorrectlyCalculated(){
        Assertions.assertAll(() -> Assertions.assertEquals(expectedResultOne, stones.solution(testInputOne,testOutputOne)),
                () -> Assertions.assertEquals(expectedResultTwo,stones.solution(testInputTwo,testOutputTwo)),
                () -> Assertions.assertEquals(expectedResultThree,stones.solution(testInputThree,testOutputThree)),
                () -> Assertions.assertEquals(expectedResultFour,stones.solution(testInputFour,testOutputFour)));
    }
}
