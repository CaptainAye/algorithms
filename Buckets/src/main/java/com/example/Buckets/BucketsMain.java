package com.example.Buckets;

import java.util.HashMap;
import java.util.Map;

public class BucketsMain {
    public int solution(int numberOfBuckets, int sameBallsCount, int buckets[], int colours[])
    {
        Map<String, Integer> ballMap = new HashMap<>();
        int currentCount = 0;
        if (buckets.length != numberOfBuckets && colours.length != numberOfBuckets ) {
            throw new IllegalArgumentException("Number of buckets and colours does not comply");
        }
        for (int k=0; k< buckets.length; k++) {
            String key = Integer.toString(buckets[k]) + colours[k];
            currentCount = increaseMapCount(ballMap, key);
            if (currentCount == sameBallsCount){
                return k;
            }
        }
        return -1;
    }

    private <T> int increaseMapCount(Map<T, Integer> map, T key){
        Integer currentCount = 1;
        if (map.containsKey(key)){
            currentCount = map.get(key);
            map.put(key, ++currentCount);
        } else {
            map.put(key, currentCount);
        }
        return currentCount;

    }


}
