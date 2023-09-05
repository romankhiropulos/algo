package org.algorithms;

import java.util.*;

/**
 * Дан НЕотсортированный массив из N чисел от 1 до N,
 * при этом несколько чисел из диапазона [1, N] пропущено,
 * а некоторые присутствуют дважды.
 * Найти все пропущенные числа без использования дополнительной памяти.
 * <p>
 * Лучше придумать решение по времени O(N)
 */
public class FindAbsentNumbersInArray {

    private static final Integer[] ARR = new Integer[10];

    static {
        ARR[0] = 1;
        ARR[1] = 2;
        ARR[2] = 3;
        ARR[3] = 3;
        ARR[4] = 5;
        ARR[5] = 6;
        ARR[6] = 6;
        ARR[7] = 8;
        ARR[8] = 9;
        ARR[9] = 10;
    }

    public static List<Integer> findDisappearedNumbers(Integer[] nums) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int valAsIndex = Math.abs(nums[i]) - 1;
            if (nums[valAsIndex] > 0) {
                nums[valAsIndex] = - nums[valAsIndex];
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                result.add(i + 1);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(findDisappearedNumbers(ARR));
    }
}
