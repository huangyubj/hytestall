package com.hy.sort;

import org.junit.Test;

import java.util.Arrays;

public class Quick {

        int[] array = new int[]{22,243,12,123,12,657,324,1,4};
//    int[] array = new int[]{6,9,7,2,3,8,11,12,13,14,17};

    @Test
    public void test(){
        float ss = 3.14F;
        quick(array, 0 ,array.length-1);
        System.out.println(Arrays.toString(array));
    }


    public void quick(int[] arr, int left, int right){
        if(left < right){
            int index = getIndex(arr, left, right);
            quick(arr, left, index - 1);
            quick(arr, index + 1, right);
        }
    }
    public int getIndex(int[] arr, int left, int right){
        int temp = arr[left];
        while (left < right){
            System.out.println("---" + left);
            while (left <right && temp <= arr[right]){
                right--;
            }
            arr[left] = arr[right];
            while (left<right && temp >= arr[left]){
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = temp;
        return left;
    }
}
