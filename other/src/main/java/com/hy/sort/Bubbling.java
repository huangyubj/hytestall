package com.hy.sort;

import org.junit.Test;

import java.util.Arrays;

public class Bubbling {

//    int[] array = new int[]{22,243,12,123,12,657,324,1,4};
    int[] array = new int[]{6,9,7,2,3,8,11,12,13,14,17};

    @Test
    public void bubbling(){
        int bubblingIndex = array.length - 1;
        boolean isBubbling = false;
        for (int i = 0; i < array.length; i++) {
            isBubbling = false;
            int tempBdidx = bubblingIndex;
            for (int j = 0; j < bubblingIndex ; j++) {
                if(array[j] > array[j+1]){
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    tempBdidx = j ;
                    isBubbling = true;
                }
            }
            if(!isBubbling)
                break;
            bubblingIndex = tempBdidx;
        }
        System.out.println(Arrays.toString(array));
    }
}
