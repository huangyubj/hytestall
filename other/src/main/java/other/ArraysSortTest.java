package other;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class ArraysSortTest {

    public static void main(String[] args) {
        int[] nums = {5,1,2,7,3,123,78,456,3};
        int[] nums2 = {5,1,2,7,3,123,78,456,3};
        SortA[] sorts = new SortA[nums.length];
        for (int i = 0; i < nums.length; i++) {
            sorts[i] = new SortA(nums[i]);
        }
        Arrays.sort(sorts);
        Arrays.sort(nums);
        new HashMap<>().put("","");
        for (int i = 0; i < sorts.length; i++) {
            System.out.println(sorts[i].toString());
        }
    }

    static class SortA implements Comparable<SortA>{
        private int num;

        public SortA(int num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "SortA{" +
                    "num=" + num +
                    '}';
        }

        @Override
        public int compareTo(SortA o) {
            return o.num > num ? 1 : -1;
        }
    }
}
