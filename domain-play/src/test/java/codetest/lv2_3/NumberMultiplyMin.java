package codetest.lv2_3;

import java.util.Arrays;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12941
 */
public class NumberMultiplyMin {

    public static void main(String[] args) {
        NumberMultiplyMin n = new NumberMultiplyMin();

        int[] A = {1, 4, 2};
        int[] B = {5, 4, 4};
        int result1 = n.solution(A, B);
        System.out.println("result1 : " + result1);

        int[] C = {1, 2};
        int[] D = {3, 4};
        int result2 = n.solution(C, D);
        System.out.println("result2 : " + result2);
    }

    public int solution(int[] A, int[] B) {
        int sum = 0;

        Arrays.sort(A);
        Arrays.sort(B);
        int[] reverseB = new int[B.length];
        for (int i = 0; i < B.length; i++) {
            reverseB[i] = B[B.length - 1 - i];
        }

        for (int i = 0; i < A.length; i++) {
            sum += A[i] * reverseB[i];
        }

        return sum;
    }
}
