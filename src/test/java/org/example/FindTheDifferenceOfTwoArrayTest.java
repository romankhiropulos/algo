package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FindTheDifferenceOfTwoArrayTest {

    private static int[] ARR_1;
    private static int[] ARR_2;
    private static int[] ARR_3;
    private static int[] ARR_4;

    static {
        ARR_1 = new int[]{1, 2, 3, 4, 5, 7};
        ARR_2 = new int[]{0, 1, 2, 6};
        ARR_3 = new int[]{1, 2, 3, 8};
        ARR_4 = new int[]{2, 4, 6, 9, 10};
    }

    @Test
    void findDifference() {
        List<List<Integer>> difference = FindTheDifferenceOfTwoArray.findDifference(ARR_1, ARR_2);
        assertTrue(new ArrayList<>(List.of(List.of(3, 4, 5, 7), List.of(0, 6))).equals(difference));
        difference = FindTheDifferenceOfTwoArray.findDifference(ARR_3, ARR_4);
        assertTrue(new ArrayList<>(List.of(List.of(1, 3, 8), List.of(4, 6, 9, 10))).equals(difference));
    }
}