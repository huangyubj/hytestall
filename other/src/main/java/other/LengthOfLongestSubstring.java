package other;

import java.util.HashMap;
import java.util.Map;

public class LengthOfLongestSubstring {
    public static void main(String[] args) {
        System.out.println(new LengthOfLongestSubstring().lengthOfLongestSubstring("dvdf"));
    }
    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int longest = 0,winBegin = 0;
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Integer idx = map.get(c);
            if(idx != null && idx >= winBegin){
                winBegin = idx + 1;
            }else{
                idx = 0;
            }
            map.put(c, i);
            longest = Math.max(longest, i - winBegin + 1);
        }
        return longest;
    }
}
