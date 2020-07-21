package construct.sort;

import construct.tree.TreeTest;
import org.junit.Test;

import java.util.Arrays;

public class Heap {

    int[] array = new int[]{22,243,12,123,12,657,324,1,4};
    //    int[] array = new int[]{6,9,7,2,3,8,11,12,13,14,17};

    @Test
    public void testMinHeap(){
        int len = array.length;
        buildMinHeap(array);
        System.out.println(Arrays.toString(array));
        for (int i = len -1; i > 0 ; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            len--;
            downAdjustMin(array, 0, len);
            System.out.println(Arrays.toString(array));
        }
    }
    @Test
    public void testMaxHeap(){
        int len = array.length;
        buildMaxHeap(array);
        System.out.println(Arrays.toString(array));
        for (int i = len -1; i > 0 ; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            len--;
            downAdjustMax(array, 0, len);
            System.out.println(Arrays.toString(array));
        }
    }

    public void downAdjustMin(int[] array, int parentIndex, int length){
        int childIndex = parentIndex * 2 + 1;
        int temp = array[parentIndex];
        while (childIndex < length){
            //存在右节点，取小的子节点
            if(childIndex + 1 < length && array[childIndex] > array[childIndex + 1])
                childIndex++;
            //父节点最小，不做变化
            if(temp <= array[childIndex])
                break;
//            System.out.println(String.format("父节点index=%d,val=%d与 子节点index=%d, val=%d 替换", parentIndex, array[parentIndex], childIndex, array[childIndex]));
            array[parentIndex] = array[childIndex];
            //以当前节点为父节点，继续寻找下一个下沉对象
            parentIndex = childIndex;
            childIndex = childIndex*2+1;
        }
        array[parentIndex] = temp;
    }

    public void buildMinHeap(int[] array){
        for (int i = (array.length-2)/2; i >= 0; i--) {
            //从最后一个非叶子节点开始下沉
            downAdjustMin(array, i, array.length);
        }
    }

    public void downAdjustMax(int[] array, int parentIndex, int length){
        int childIndex = parentIndex * 2 + 1;
        int temp = array[parentIndex];
        while (childIndex < length){
            //存在右节点，取小的子节点
            if(childIndex + 1 < length && array[childIndex] < array[childIndex + 1])
                childIndex++;
            //父节点最小，不做变化
            if(temp >= array[childIndex])
                break;
//            System.out.println(String.format("父节点index=%d,val=%d与 子节点index=%d, val=%d 替换", parentIndex, array[parentIndex], childIndex, array[childIndex]));
            array[parentIndex] = array[childIndex];
            //以当前节点为父节点，继续寻找下一个下沉对象
            parentIndex = childIndex;
            childIndex = childIndex*2+1;
        }
        array[parentIndex] = temp;
    }

    public void buildMaxHeap(int[] array){
        for (int i = (array.length-2)/2; i >= 0; i--) {
            //从最后一个非叶子节点开始下沉
            downAdjustMax(array, i, array.length);
        }
    }
}
