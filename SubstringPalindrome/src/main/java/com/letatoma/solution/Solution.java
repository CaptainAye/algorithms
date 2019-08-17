package com.letatoma.solution;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Solution {

    private static final int MAX_PALINDROMES_COUNT = 100_000_000;

    private static final int START_INDEX = 0;
    private static final int END_INDEX = 1;

    private static final String smallCaseRegexp = "[a-z]*";
    public int solutionOn2(String S) {

        validateStringIsLowerCase(S);

        int substringPalindromesCount = 0;

        char[] wordToProcess = S.toCharArray();
        for (int i=0; i < wordToProcess.length - 1; i++) {
            int maxPalindromeLengthEvenBase = findLargestPalindrome(wordToProcess, i, i+1);
            int maxPalindromeLengthOddBase = findLargestPalindrome(wordToProcess, i);
            substringPalindromesCount += (maxPalindromeLengthEvenBase / 2) + (maxPalindromeLengthOddBase / 2);
        }
        return substringPalindromesCount > MAX_PALINDROMES_COUNT ? -1 : substringPalindromesCount;
    }

    public int solutionFaster(String S) {
        validateStringIsLowerCase(S);
        char[] wordToProcess = S.toCharArray();
        int palindromeCount = countSubstringPalindromes(wordToProcess);
        return palindromeCount > MAX_PALINDROMES_COUNT ? -1 : palindromeCount;
    }


    private int palindromesCount;
    private boolean resetBackStepSize;
    private int backStepSize;

    private void resetFields() {
        palindromesCount = 0;
        resetBackStepSize = false;
        backStepSize = 0;
    }

    private void resetBackStepSize() {
        backStepSize = 0;
        resetBackStepSize = false;
    }

    private void incrementBackStepSize() {
        backStepSize+=2;
        resetBackStepSize = true;
    }

    private void incrementPalindromeCount() {
        palindromesCount++;
        incrementBackStepSize();
    }

    public int solutionFastest(String S) {
        resetFields();
        validateStringIsLowerCase(S);
        boolean shouldScanPrevious;
        char[] wordToProcess = S.toCharArray();
        int precedingIndex = 0;

        int firstIndex;
        int lastIndex;
        boolean isSingleCharPalindrome = false;

        for (int currentIndex=1; currentIndex < wordToProcess.length; currentIndex++) {
            shouldScanPrevious = backStepSize > 0;

            if (precedingIndex >= 0 && precedingIndex == currentIndex - 1 && wordToProcess[currentIndex] == wordToProcess[precedingIndex]) {
                char currentChar = wordToProcess[currentIndex];
                firstIndex = precedingIndex;
                do {
                    currentIndex++;
                } while (currentIndex < wordToProcess.length && wordToProcess[currentIndex] == currentChar);
                lastIndex = currentIndex;
                int singleCharPalindromeSize = lastIndex - firstIndex;
                palindromesCount += singleCharPalindromeSize * (singleCharPalindromeSize - 1) / 2;
                backStepSize += 2;
                resetBackStepSize = true;
                precedingIndex = firstIndex - 1;
                if (lastIndex >= wordToProcess.length) {
                    break;
                }

            }

            if (precedingIndex >= 0 && wordToProcess[currentIndex] == wordToProcess[precedingIndex]) {
                incrementPalindromeCount();
            } else {
                if (resetBackStepSize) {
                    resetBackStepSize();
                    precedingIndex = currentIndex - 1;
                    if (precedingIndex >= 0 && wordToProcess[currentIndex] == wordToProcess[precedingIndex]) {
                        incrementPalindromeCount();
                    }
                }
                if (backStepSize == 0) {
                    precedingIndex--;
                    if (precedingIndex >= 0 && wordToProcess[currentIndex] == wordToProcess[precedingIndex]) {
                        incrementPalindromeCount();
                        backStepSize++;
                    }
                    shouldScanPrevious = false;
                }
            }
            if (shouldScanPrevious) {
                precedingIndex = currentIndex - 1;
                if (precedingIndex >= 0 && wordToProcess[currentIndex] == wordToProcess[precedingIndex]) {
                    incrementPalindromeCount();
                } else {
                    precedingIndex--;
                    if (precedingIndex >= 0 && wordToProcess[currentIndex] == wordToProcess[precedingIndex]) {
                        incrementPalindromeCount();
                    }
                }
            }
            precedingIndex = currentIndex - backStepSize;
        }
        return palindromesCount > MAX_PALINDROMES_COUNT ? -1 : palindromesCount;
    }

    private int countSubstringPalindromes(char[] wordToProcess) {
        int palindromesCount = 0;
        List<int[]> sameCharPalindromes = findSameCharPalindromes(wordToProcess);
        for (int[] startEndPositions : sameCharPalindromes) {
            int startPosition = startEndPositions[START_INDEX];
            int endPosition = startEndPositions[END_INDEX];
            int palindromeLength = endPosition - startPosition + 1;
            palindromesCount += palindromeLength * (palindromeLength - 1)/ 2;
            int leftIndex = startPosition - 1;
            int rightIndex = endPosition + 1;
            //find larger palindromes which have single char palindromes inside them
            while (!exceedsWordBoundries(leftIndex, rightIndex, wordToProcess.length) && wordToProcess[leftIndex] == wordToProcess[rightIndex]) {
                leftIndex--;
                rightIndex++;
                palindromesCount++;
            }
        }
        // Palindrome which consists of the single char in the middle i.e. ada, cbababc
        List<int[]> singleDifferentCharPalindromes = findPalindromesWithDifferentLetterInTheMiddle(wordToProcess, sameCharPalindromes);
        for (int[] startEndPositions : singleDifferentCharPalindromes) {
            int startPosition = startEndPositions[START_INDEX];
            int endPosition = startEndPositions[END_INDEX];
            int palindromeLength = endPosition - startPosition + 1;
            palindromesCount+= palindromeLength / 2;
        }

        return palindromesCount;

    }

    private List<int[]> findSameCharPalindromes(char[] wordToProcess) {
        int substringStart = 0;
        int substringEnd = 0;
        List<int[]> palindromesList = new ArrayList<>();

        if (wordToProcess.length > 0) {
            char currentCharacter = wordToProcess[0];
            for (int i = 1; i <= wordToProcess.length; i++) {
                if (i == wordToProcess.length || currentCharacter != wordToProcess[i]) {
                    substringEnd = i - 1;
                    //ignore single letters
                    if (substringEnd != substringStart) {
                        palindromesList.add(new int[]{substringStart, substringEnd});
                    }
                    if (i != wordToProcess.length) {
                        substringStart = i;
                        currentCharacter = wordToProcess[i];
                    }
                }
            }
        }
        return palindromesList;
    }

    private List<int[]> findPalindromesWithDifferentLetterInTheMiddle(char[] wordToProcess, List<int[]> sameCharPalindromes) {
        int substringStart;
        int substringEnd;
        int currentSameCharPalindrome = 0;
        int startingIndex = 1;
        if (sameCharPalindromes.size() > currentSameCharPalindrome && sameCharPalindromes.get(currentSameCharPalindrome)[START_INDEX] == 0) {
            startingIndex = sameCharPalindromes.get(currentSameCharPalindrome)[END_INDEX] + 1;
            currentSameCharPalindrome++;

        }
        List<int[]> palindromesList = new ArrayList<>();
        for (int i = startingIndex; i < wordToProcess.length - 1; i++) {
            if (sameCharPalindromes.size() > currentSameCharPalindrome && sameCharPalindromes.get(currentSameCharPalindrome)[START_INDEX] == i) {
                i = sameCharPalindromes.get(currentSameCharPalindrome)[END_INDEX]; // no need to increment i as it will be incremented after continue; operation
                currentSameCharPalindrome++;
                continue;
            }
            char middleChar = wordToProcess[i];
            substringEnd = 0;
            substringStart = 0;
            int currentLeftIndex = i - 1;
            int currentRightIndex = i + 1;
            boolean isDifferentChar = middleChar != wordToProcess[currentLeftIndex] && middleChar != wordToProcess[currentRightIndex];

            while (isDifferentChar && !exceedsWordBoundries(currentLeftIndex,currentRightIndex, wordToProcess.length) && wordToProcess[currentLeftIndex] == wordToProcess[currentRightIndex]) {
                substringStart = currentLeftIndex;
                substringEnd = currentRightIndex;
                currentLeftIndex--;
                currentRightIndex++;
            }
            if (substringStart != substringEnd) {
                palindromesList.add(new int[]{substringStart, substringEnd});
            }
        }

        return palindromesList;
    }



    private boolean exceedsWordBoundries(int leftIndex, int rightIndex, int wordLength) {
        return leftIndex < 0 || rightIndex >= wordLength;
    }

    //Kiedy baza ma dwie litery
    private int findLargestPalindrome(char[] word, int leftIndex, int rightIndex) {
        int currentLeftIndex = leftIndex;
        int currentRightIndex = rightIndex;
        int palindromeLength = 0;
        while (!exceedsWordBoundries(currentLeftIndex, currentRightIndex, word.length) && word[currentLeftIndex] == word[currentRightIndex]) {
            palindromeLength = currentRightIndex - currentLeftIndex + 1;
            currentLeftIndex--;
            currentRightIndex++;
        }

        return palindromeLength;

    }

    private int findLargestPalindrome(char[] word, int centralIndex) {
        return findLargestPalindrome(word, centralIndex, centralIndex);
    }

    private void validateStringIsLowerCase(String s) {
        Pattern regexpPattern = Pattern.compile(smallCaseRegexp);
        if (!regexpPattern.matcher(s).matches()) {
            throw new IllegalArgumentException("Only lower case letters accepted");
        }
    }
}
