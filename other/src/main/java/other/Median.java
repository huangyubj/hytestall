package other;

/**
 * 中位数
 * 从头移动，一直到中位点
 */
public class Median {
    public static void main(String[] args) {
        int[] nums1 = new int[]{2,3,4};
        int[] nums2 = new int[]{1};
        System.out.println(new Median().findMedianSortedArrays(nums1,nums2));
        int[] nums3 = new int[]{0,0};
        int[] nums4 = new int[]{0,0};
        System.out.println(new Median().findMedianSortedArrays1(nums3, nums4));
    }
    public double findMedianSortedArrays(int[] nums1, int[] nums2){
        int m = nums1.length,n = nums2.length;
        int aStart = 0,bStart = 0;
        int left = -1,right = -1;
        int len = m + n;
        for (int i = 0; i <= len/2; i++) {
            left = right;
            if(aStart < m && (bStart >= n || nums1[aStart] < nums2[bStart]) ){
                right = nums1[aStart++];
            }else{
                right = nums2[bStart++];
            }
        }
        if((len&1) == 0){
            return (left+right)/2.0;
        }
        return right;
    }

    public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        int n1len = nums1.length;
        int n2len = nums2.length;
        int len = n1len + n2len;
        int midIdx = len/2 + len%2;
        double midNum = 0;
        int tempIdx=0;
        int n1BeginIdx=0;
        int n2BeginIdx=0;
        while (true){
            if(n1BeginIdx == n1len){
                if(len%2 == 0){
                    midNum = (nums2[n2BeginIdx + midIdx - tempIdx -1] + nums2[n2BeginIdx + midIdx - tempIdx])/2.0;
                }else{
                    midNum = nums2[n2BeginIdx + midIdx - tempIdx -1];
                }
                break;
            }

            if(n2BeginIdx == n2len){
                if(len%2 == 0){
                    midNum = (nums1[n1BeginIdx + midIdx - tempIdx -1] + nums1[n1BeginIdx + midIdx - tempIdx])/2.0;
                }else{
                    midNum = nums1[n1BeginIdx + midIdx - tempIdx - 1];
                }
                break;
            }
            int val1 = nums1[n1BeginIdx];
            int val2 = nums2[n2BeginIdx];
            if(val1 < val2){
                n1BeginIdx++;
                midNum = val1;
            }else{
                n2BeginIdx++;
                midNum = val2;
            }
            tempIdx++;
            if(tempIdx == midIdx){
                if(len%2 == 0){
                    int next;
                    if(n1BeginIdx == n1len){
                        next = nums2[n2BeginIdx];
                    }else if(n2BeginIdx == n2len){
                        next = nums1[n1BeginIdx];
                    }else{
                        int v1 = nums1[n1BeginIdx];
                        int v2 = nums2[n2BeginIdx];
                        next = v1 < v2 ? v1 : v2;
                    }
                    midNum = (midNum + next)/2.0;
                }
                break;
            }
        }
        return midNum;
    }
}
