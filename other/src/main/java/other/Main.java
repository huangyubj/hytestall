package other;

import java.util.*;

public class Main {





    public static void main(String[] args) {
//        System.out.println(GetLeastNumbers_Solution(new int[]{12,15,5,3,2,9,6,8,1}, 3));
//
//        Scanner in = new Scanner(System.in);
//
//        while (in.hasNextLine()) {
//            String line = in.nextLine();
//            int k = Integer.parseInt(in.nextLine());
//            int[] input = spliteLine(line);
//            ArrayList<Integer> res = GetLeastNumbers_Solution(input, k);
//            System.out.println(res);
//        }
//        String str = "ssscccWWWea";
//        System.out.println(sort(str));
//        String line = "ACwc#$%PO";
//        System.out.println(change(line));
//        char c = 'a';//97
//        char c1 = 'z';//122
//        char c2 = 'A';//65
//        char c3 = 'Z';//90
//        System.out.println(c - c1);
//        System.out.println(Integer.valueOf(c));
//        System.out.println(Integer.valueOf(c1));
//        System.out.println(Integer.valueOf(c2));
//        System.out.println(Integer.valueOf(c3));
        System.out.println(maximumGap(new int[]{3,1,2}));;
        calBits(2);
    }

    private static int[] calBits(int val) {
        int[] a = new int[val+1];
        a[0] = 0;
        int i = 1;
        while(i <= val){
            //i & (i-1) 是吧i最右边的1去掉，即 i中1的个数为 i&(i-1)中1 得个数加1
            // a[i]对应着i中含有1的个数，所以a[i]中1的个数 为a[i&(i+1)]的个数加1
            a[i] = a[i & ( i - 1)] + 1;
            i++;
        }
        return a;
    }

    //======================================================================================================================================================================

    public static int maximumGap(int[] nums) {
        if (nums.length < 2) return 0;
        //找到最小值、最大值
        int max = 0, min = 0;
        for (int num : nums) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }
        //计算桶大小,桶数量至少一个
        int bucketSize = Math.max((max - min) / (nums.length - 1), 1);
        Bucket[] buckets = new Bucket[(max - min) / bucketSize + 1];
        //入桶,每个桶只关心桶内的最大值和最小值
        for (int i = 0; i < nums.length; i++) {
            int idx = (nums[i] - min) / bucketSize;
            if (buckets[idx] == null) buckets[idx] = new Bucket();
            buckets[idx].max = Math.max(nums[i], buckets[idx].max);
            buckets[idx].min = Math.min(nums[i], buckets[idx].min);
        }
        //前一个桶的 max
        int preMax = -1;
        //最大间隔
        int maxGap = 0;
        for (int i = 0; i < buckets.length; i++) {
            //桶不为空,并且不是第一个桶
            if (buckets[i] != null && preMax != -1) {
                //桶之间的间隔
                maxGap = Math.max(maxGap, buckets[i].min - preMax);
            }
            //记录前一个桶的 max
            if (buckets[i] != null) {
                preMax = buckets[i].max;
            }
        }
        return maxGap;
    }
    static class Bucket {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
    }
//======================================================================================================================================================================

    private static String change(String line) {
        String str = line.replaceAll("[^(A-Za-z)]", "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch >='A' && ch <= 'Z'){
                ch = (char) (ch + 32);
            }else if (ch >='a' && ch <= 'z'){
                ch = (char) (ch - 32);
            }
            sb.append(ch);
        }
        return sb.toString();
    }

//======================================================================================================================================================================

    private static String sort(String line) {
        if(line == null || "".equals(line))
            return line;
        Map<Character, Integer> counter = new HashMap<Character, Integer>();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            int num = counter.getOrDefault(c, 0);
            counter.put(c, num + 1);
        }
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(counter.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {

            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                if(o1.getValue() == o2.getValue()){
                    return o1.getKey() < o2.getKey() ? -1 : 1;
                }
                return o1.getValue() > o2.getValue() ? -1 : 1;
            }
        });
        StringBuilder res = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : list) {
            for (int i = 0; i < entry.getValue(); i++) {
                res.append(entry.getKey());
            }
        }
        return res.toString();
    }

    //======================================================================================================================================================================
    public static ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        if(k < 1 || input.length < k)
            return null;
        ArrayList<Integer> list = new ArrayList<Integer>(k);
        for (int i = 0; i < input.length; i++) {
            int val = input[i];
            for (int j = i+1; j < input.length; j++) {
                int val_j = input[j];
                if(val > val_j){
                    input[j] = val;
                    input[i] = val_j;
                    val = val_j;
                }
            }
            if(i+1 == k)
                break;
        }
        for (int i = 0; i < k; i++) {
            list.add(input[i]);
        }
        return list;
    }

    private static int[] spliteLine(String line) {
        String[] strArr = line.split(",");
        int[] intArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            intArr[i] = Integer.valueOf(strArr[i]);
        }
        return intArr;
    }


}