package com.example.stones;

import java.util.*;

public class Stones {
    long solution (List<Integer> currentStones, List<Integer> targetStones) {
        if (currentStones.size() != targetStones.size()) {
            throw new IllegalArgumentException("Incorrect values");
        }
        List<Integer> differences = new ArrayList<>(currentStones.size());

        for (int i = 0; i < currentStones.size(); i++) {
            // negative difference - there are less stones than needed, positive difference - there is more stones than needed
            differences.add( currentStones.get(i) - targetStones.get(i));
        }

        if (differences.stream().reduce((element, sum) -> sum += element).get() != 0) {
            return -1;
        }

        int blocksNeeded;
        int indexOfTowerToFill;
        int blocksAvailable;
        int distance;
        long countSteps = 0;
        Deque<Integer> stackOfTowerWhichNeedBlocks = new ArrayDeque<>();
        for (int i = 0; i < differences.size(); i++) {
            if (differences.get(i) < 0 ) {
                stackOfTowerWhichNeedBlocks.addFirst(i);
            } else if (differences.get(i) > 0) {
                while (differences.get(i) > 0) {
                    int indexOfAvailableBlocksTower = i;
                    if (stackOfTowerWhichNeedBlocks.size() > 0) {
                        indexOfTowerToFill = stackOfTowerWhichNeedBlocks.removeFirst();
                        blocksNeeded = differences.get(indexOfTowerToFill) * -1;
                        blocksAvailable = differences.get(i);
                        distance = i - indexOfTowerToFill;
                        if (distance % 2 != 0) {
                            if (differences.get(i + 1) > 0) {
                                indexOfAvailableBlocksTower = i + 1;
                                distance += 1;
                            }
                        }
                        int singleBlocksSteps = distance % 2 != 0 ? distance / 2 + 1 : distance/2;
                        if (blocksNeeded <= blocksAvailable) {
                            countSteps += singleBlocksSteps * blocksNeeded;
                            differences.set(indexOfTowerToFill, differences.get(indexOfTowerToFill) + blocksNeeded);
                            differences.set(indexOfAvailableBlocksTower, differences.get(indexOfAvailableBlocksTower) - blocksNeeded);
                        } else {
                            countSteps += singleBlocksSteps * blocksAvailable;
                            differences.set(indexOfTowerToFill, differences.get(indexOfTowerToFill) + blocksAvailable);
                            differences.set(indexOfAvailableBlocksTower, differences.get(indexOfAvailableBlocksTower) - blocksAvailable);
                            stackOfTowerWhichNeedBlocks.addFirst(indexOfTowerToFill);
                        }
                    } else { // stack is empty - elements ahead need blocks
                        blocksAvailable = differences.get(indexOfAvailableBlocksTower);
                        if (differences.get(indexOfAvailableBlocksTower + 1) < 0) {
                            blocksNeeded = differences.get(indexOfAvailableBlocksTower + 1) * -1;
                            if (blocksNeeded <= blocksAvailable) {
                                int singleBlocksSteps = 1;
                                countSteps += singleBlocksSteps * blocksNeeded;
                                differences.set(indexOfAvailableBlocksTower + 1, differences.get(indexOfAvailableBlocksTower + 1) + blocksNeeded);
                                differences.set(indexOfAvailableBlocksTower, differences.get(indexOfAvailableBlocksTower) - blocksNeeded);
                                blocksAvailable -= blocksNeeded;
                                if (blocksAvailable > 0) {
                                    countSteps += singleBlocksSteps * blocksAvailable;
                                    differences.set(indexOfAvailableBlocksTower + 2, differences.get(indexOfAvailableBlocksTower + 2) + blocksAvailable);
                                    differences.set(indexOfAvailableBlocksTower, differences.get(indexOfAvailableBlocksTower) - blocksAvailable);
                                }
                            } else {
                                int singleBlocksSteps = 1;
                                countSteps += singleBlocksSteps * blocksAvailable;
                                differences.set(indexOfAvailableBlocksTower + 1, differences.get(indexOfAvailableBlocksTower + 1) + blocksAvailable);
                                differences.set(indexOfAvailableBlocksTower, differences.get(indexOfAvailableBlocksTower) - blocksAvailable);
                            }


                        } else { // move all the blocks to the furthest point
                            int singleBlocksSteps = 1;
                            countSteps += singleBlocksSteps * blocksAvailable;
                            differences.set(indexOfAvailableBlocksTower + 2, differences.get(indexOfAvailableBlocksTower + 2) + blocksAvailable);
                            differences.set(indexOfAvailableBlocksTower, differences.get(indexOfAvailableBlocksTower) - blocksAvailable);
                        }

                    }

                }
            }
        }
        return countSteps % 1000000007L;
    }
}
