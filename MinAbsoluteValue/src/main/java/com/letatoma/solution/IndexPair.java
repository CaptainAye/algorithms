package com.letatoma.solution;

public class IndexPair {
    private Integer sourceIndex;
    private Integer targetIndex;

    IndexPair(Integer sourceIndex, Integer targetIndex) {
        this.sourceIndex = sourceIndex;
        this.targetIndex = targetIndex;
    }

    Integer getSourceIndex() {
        return sourceIndex;
    }

    Integer getTargetIndex() {
        return targetIndex;
    }
}
