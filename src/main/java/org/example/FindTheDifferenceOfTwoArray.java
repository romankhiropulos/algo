package org.example;

import java.util.*;

/**
 * Input: nums1 = [1,2,3],
 * nums2 = [2,4,6]
 * Output: [[1,3],[4,6]]
 */
public class FindTheDifferenceOfTwoArray {

    /**
     * Time O(N)
     * Memory O(N)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static List<List<Integer>> findDifference1(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        Arrays.stream(nums1).forEach(set1::add);
        Arrays.stream(nums2).forEach(set2::add);
        return List.of(set1.stream().filter(x -> !set2.contains(x)).toList(), set2.stream().filter(x -> !set1.contains(x)).toList());
    }

    /**
     * nums1 and nums1 need be sorted and have only unique members
     * Time O(N)
     * Memory O(1)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static List<List<Integer>> findDifference(int[] nums1, int[] nums2) {

        List<Integer> resultSub1 = new ArrayList<>();
        List<Integer> resultSub2 = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                resultSub1.add(nums1[i]);
                i++;
            } else if (nums1[i] > nums2[j]) {
                resultSub2.add(nums2[j]);
                j++;
            } else {
                i++;
                j++;
            }
        }
        while (i < nums1.length) {
            resultSub1.add(nums1[i]);
            i++;
        }
        while (j < nums2.length) {
            resultSub2.add(nums2[j]);
            j++;
        }
        return List.of(resultSub1, resultSub2);
    }
}
