package com.hy.redis.app.votingrank;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class VotingRankTest {
    //来个1分钟投票
    private static VotingRank votingRank = new VotingRank(60000);
    private  static List<String> voteIdList = new ArrayList<String>();
    private static String[] voteItem = new String[]{
            "去天路",
            "去滑雪",
            "去K歌",
            "还是不去了"
    };
    private static AtomicInteger idx = new AtomicInteger(-1);
    public static void main(String[] args) {
        //来几个投票项
//        for (int i = 0; i < voteItem.length; i++) {
//            new PubVote(Thread.currentThread()).start();
//        }
//        Random r = new Random();
//        Scanner scanner = new Scanner(System.in);
//        while (true){
//            String cmd = scanner.next();
//            if("tp".equals(cmd)){
//                //20个人随机投票
//                int i = r.nextInt(20);
//                votingRank.voting(i+"", voteIdList.get(i%voteItem.length));
//            }else if("show".equals("cmd")){
//                List<Map<String, String>> mp = votingRank.getSortVote(VotingRank.VOTE_SCORE_NAME,1,2);
//                for(Map<String,String > m : mp){
//                    System.out.println(m.get("title") + m.get("title"));
//                }
//            }
//        }

        List<Map<String, String>> mp = votingRank.getSortVote(VotingRank.VOTE_SCORE_NAME,1,2);
        for(Map<String,String > m : mp){
            System.out.println(m.get("voteId") + "--" + m.get("title"));
        }
        votingRank.test();
    }


    static class PubVote extends Thread{
        Thread thread;
        public PubVote(Thread thread){
            this.thread = thread;
        }
        @Override
        public void run() {
            try {
                int s = Math.abs(new Random().nextInt(1000));
                System.out.println(s);
                Thread.sleep(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Map<String, String> map = new HashMap<String, String>();
            int indext = idx.incrementAndGet();
            map.put("title", voteItem[indext]);
            String id = votingRank.pubVoting(map);
            System.out.println(id);
            voteIdList.add(id);
        }
    }
}
