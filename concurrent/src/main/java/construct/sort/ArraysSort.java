package construct.sort;

import java.util.Arrays;
import java.util.Random;

public class ArraysSort {

    public static void main(String[] args) {
        int length = 100;
        // 小于47
//        int length = 46;
        // 小于286
//        int length = 285;
        int[] list = new int[length];
        Random random = new Random(1);
        for (int i = 0; i < length; i++) {
            list[i] = random.nextInt(10000);
        }
        Arrays.sort(list);
    }
}
