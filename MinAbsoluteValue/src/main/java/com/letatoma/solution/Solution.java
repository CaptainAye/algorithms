package com.letatoma.solution;

import java.util.*;

class SummableList extends ArrayList<Integer> {
    private int sum = 0;

    public int getSum() {
        return sum;
    }

    @Override
    public boolean add(Integer integer) {
        sum += integer;
        return super.add(integer);
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Integer)) {
            throw new IllegalArgumentException();
        }
        Integer integer = (Integer) o;
        sum -= integer;
        return super.remove(o);
    }

    @Override
    public Integer remove(int index) {
        Integer number = this.get(index);
        sum -= number;
        return super.remove(index);
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        for (Integer element : c) {
            sum += element;
            this.add(element);
        }
        return true;
    }
}

interface Transformation {
    boolean transform(List<Integer> sourceList, List<Integer> targetList, int difference);
}

public class Solution {

    private int initializeLists(int A[], SummableList positiveList, SummableList negativeList) {
        int difference = 0;
        for (int i = 0; i < A.length; i++) {
            A[i] = Math.abs(A[i]);
            if (Math.abs(difference + A[i]) < Math.abs(difference - A[i])) {
                positiveList.add(A[i]);
            } else {
                negativeList.add(A[i]);
            }
            difference = positiveList.getSum() - negativeList.getSum();
        }
        return difference;
    }

    public int solution(int[] A) {

        boolean shouldContinue = true;
        SummableList positiveList = new SummableList();
        SummableList negativeList = new SummableList();
        int difference = initializeLists(A, positiveList, negativeList);

        if (A.length < 1 || difference == 0) {
            shouldContinue = false;
        }

        Transformation transformation = this::moveNumberToMinimizeDifference;

        while(shouldContinue) {
            boolean shouldMovePositive = difference > 0;
            difference = Math.abs(difference);
            shouldContinue = shouldMovePositive ?
                    moveNumberToMinimizeDifference(positiveList, negativeList, difference) :
                    moveNumberToMinimizeDifference(negativeList, positiveList, difference) ;
            difference = positiveList.getSum() - negativeList.getSum();
        }

      shouldContinue = Math.abs(difference) > 1;

        while (shouldContinue) {
            boolean isPositiveBigger = difference > 0;
            difference = Math.abs(difference);
            shouldContinue = isPositiveBigger ?
                    switchNumbersToMinimizeDifference(positiveList, negativeList, difference) :
                    switchNumbersToMinimizeDifference(negativeList, positiveList, difference) ;
            difference = positiveList.getSum() - negativeList.getSum();
        }

        //System.out.println("Move counter: " + moveCounter + " switch counter: " + switchCounter);
        return Math.abs(difference);
    }

    private boolean switchNumbersToMinimizeDifference(List<Integer> sourceList, List<Integer> targetList, int difference) {
        boolean switchOperationFound = false;
        Integer sourceElementToSwitch;
        Integer targetElementToSwitch;
        int sourceIndexToSwitch = 0;
        int targetIndexToSwitch = 0;
        int minimalDifference = difference;

        Collections.sort(sourceList);
        Collections.sort(targetList);

        if (sourceList.size() == 0 || targetList.size() == 0) {
            return false;
        }

        int sourceIndex = 0;
        int targetIndex = 0;

        boolean shouldContinue = true;

        while (shouldContinue) {
            int sourceElement = sourceList.get(sourceIndex);
            int targetElement = targetList.get(targetIndex);
            int currentDifference = Math.abs(difference - 2*(sourceElement - targetElement));
            if ( currentDifference < minimalDifference) {
                minimalDifference = currentDifference;
                sourceIndexToSwitch = sourceIndex;
                targetIndexToSwitch = targetIndex;
                switchOperationFound = true;
            }
            if (sourceElement - targetElement > 0) {
                targetIndex++;
            } else {
                sourceIndex++;
            }
            shouldContinue = sourceIndex <sourceList.size() && targetIndex < targetList.size();

        }
        if (switchOperationFound) {
            sourceElementToSwitch = sourceList.get(sourceIndexToSwitch);
            targetElementToSwitch = targetList.get(targetIndexToSwitch);
            sourceList.remove(sourceIndexToSwitch);
            targetList.remove(targetIndexToSwitch);
            sourceList.add(targetElementToSwitch);
            targetList.add(sourceElementToSwitch);
            switchOperationFound = minimalDifference > 1;
        }
        return switchOperationFound;
    }

    private boolean moveNumberToMinimizeDifference(List<Integer> sourceList, List<Integer> targetList, int difference) {
        boolean moveOperationFound = false;
        int minimalDifference = difference;
        int indexToMove = 0;
        for (int i =0; i < sourceList.size(); i++) {
            int element = sourceList.get(i);
            int currentDifference = Math.abs(difference - 2*element);
            if (currentDifference < minimalDifference ||
                    (moveOperationFound && currentDifference == minimalDifference && sourceList.get(indexToMove) > element)) {
                indexToMove = i;
                minimalDifference = currentDifference;
                moveOperationFound = minimalDifference > 1;
            }
        }
        if (moveOperationFound) {
            Integer elementToMove = sourceList.remove(indexToMove);
            targetList.add(elementToMove);
        }

        return moveOperationFound;

    }
}
