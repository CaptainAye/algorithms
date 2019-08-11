package com.letatoma.solution;

import java.util.*;

public class Solution {

    public int solution(int[] A) {

        boolean shouldTransform = true;
        SummableList positiveList = new SummableList();
        SummableList negativeList = new SummableList();
        int difference = initializeLists(A, positiveList, negativeList);
        if (A.length < 1 || difference == 0) {
            shouldTransform = false;
        }
        if (shouldTransform) {
            difference = executeTransformation(positiveList, negativeList, difference, this::moveElementBetweenLists);
            if (Math.abs(difference) > 1) {
                difference = executeTransformation(positiveList, negativeList, difference, this::switchElementsBetweenLists);
            }
        }

        return Math.abs(difference);
    }

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

    private int executeTransformation(SummableList positiveList, SummableList negativeList, int difference, Transformation transformation) {
        boolean shouldContinue;
        do {
            boolean shouldMovePositive = difference > 0;
            difference = Math.abs(difference);
            shouldContinue = shouldMovePositive ?
                    transformation.transform(positiveList, negativeList, difference) :
                    transformation.transform(negativeList, positiveList, difference) ;
            difference = positiveList.getSum() - negativeList.getSum();
        } while (shouldContinue);
        return difference;

    }

    private boolean switchElementsBetweenLists(List<Integer> sourceList, List<Integer> targetList, int difference) {
        boolean shouldContinueSwitch = false;
        Integer sourceElementToSwitch;
        Integer targetElementToSwitch;

        Collections.sort(sourceList);
        Collections.sort(targetList);

        if (sourceList.size() == 0 || targetList.size() == 0) {
            return false;
        }

        IndexPair indicesToSwitch = findElementsToSwitch(sourceList, targetList, difference);

        if (indicesToSwitch != null) {
            sourceElementToSwitch = sourceList.get(indicesToSwitch.getSourceIndex());
            targetElementToSwitch = targetList.get(indicesToSwitch.getTargetIndex());
            sourceList.remove(sourceElementToSwitch);
            targetList.remove(targetElementToSwitch);
            sourceList.add(targetElementToSwitch);
            targetList.add(sourceElementToSwitch);
            difference -= 2*(sourceElementToSwitch - targetElementToSwitch);
            shouldContinueSwitch = Math.abs(difference) > 1;
        }

        return shouldContinueSwitch;
    }

    private IndexPair findElementsToSwitch(List<Integer> sourceList, List<Integer> targetList, int difference) {
        int sourceIndex = 0;
        int targetIndex = 0;
        IndexPair switchIndices = null;

        boolean shouldContinue;
        int minimalDifference = difference;
        do {
            int sourceElement = sourceList.get(sourceIndex);
            int targetElement = targetList.get(targetIndex);
            int currentDifference = Math.abs(difference - 2*(sourceElement - targetElement));
            if ( currentDifference < minimalDifference) {
                minimalDifference = currentDifference;
                switchIndices = new IndexPair(sourceIndex, targetIndex);
            }
            if (sourceElement - targetElement > 0) {
                targetIndex++;
            } else {
                sourceIndex++;
            }
            shouldContinue = sourceIndex < sourceList.size() && targetIndex < targetList.size();

        } while (shouldContinue);
        return switchIndices;
    }

    private boolean moveElementBetweenLists(List<Integer> sourceList, List<Integer> targetList, int difference) {
            Integer indexToMove = findIndexToMove(sourceList, difference);
            if (indexToMove != null) {
                int index = indexToMove;
                Integer elementToMove = sourceList.remove(index);
                targetList.add(elementToMove);
            }

        return indexToMove != null;
    }

    private Integer findIndexToMove(List<Integer> sourceList, int difference) {
        int minimalDifference = difference;
        Integer indexToMove = null;
        boolean moveOperationFound = false;
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
        return indexToMove;
    }
}
