package com.letatoma.solution;

import java.util.List;

interface Transformation {
    boolean transform(List<Integer> sourceList, List<Integer> targetList, int difference);
}
