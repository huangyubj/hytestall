package com.hy.sort.tree;

import java.util.Arrays;

/**
 * 最小堆测试
 */
public class MinHeapTest {

    public static void main(String[] args) {
        MinHeapTest minHeapTest = new MinHeapTest();
        int[] heap = new int[]{7,100,3,10,5,2,8,9,6};
        /**
         * 一、7,100,3,6,5,2,8,9,10
         * 二、7,100,2,6,5,3,8,9,10
         * 三、7,5,2,6,100,3,8,9,10
         *      7,5,2,6,100,3,8,9,10  --2  3 8 不替换
         * 四、 2,5,7,6,100,3,8,9,10
         *      2,5,7,6,100,3,8,9,10 --7 3  8
         *      2,5,3,6,100,7,8,9,10
         *
         */
        minHeapTest.buildHeap(heap);
        System.out.println(Arrays.toString(heap) + "\n\n\n");
    }

    public void downAdjust(int[] array, int parentIndex, int length){
        int childIndex = parentIndex * 2 + 1;
        int temp = array[parentIndex];
        while (childIndex < length){
            //存在右节点，取小的子节点
            if(childIndex + 1 < length && array[childIndex] > array[childIndex + 1])
                childIndex++;
            //父节点最小，不做变化
            if(temp <= array[childIndex])
                break;
            System.out.println(String.format("父节点index=%d,val=%d与 子节点index=%d, val=%d 替换", parentIndex, array[parentIndex], childIndex, array[childIndex]));
            array[parentIndex] = array[childIndex];
            //以当前节点为父节点，继续寻找下一个下沉对象
            parentIndex = childIndex;
            childIndex = childIndex*2+1;
        }
        array[parentIndex] = temp;
        System.out.println(Arrays.toString(array));
    }

    public void buildHeap(int[] array){
        for (int i = (array.length-2)/2; i >= 0; i--) {
            //从最后一个非叶子节点开始下沉
            downAdjust(array, i, array.length);
        }
    }
}
