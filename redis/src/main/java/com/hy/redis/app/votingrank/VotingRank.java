package com.hy.redis.app.votingrank;

import com.hy.redis.utils.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 投票排名
 * 2.初始化票池，一人一次只能一票，
 * 3.投票截止时间
 * 4.投票得分
 */
public class VotingRank {

    private Jedis jedis;

    //记录发布时间的key
    public static final String VOTE_TIME_NAME = "votetime";
    //记录分数的key
    public static final String VOTE_SCORE_NAME = "votescore";
    //投票人员池的key
    private final String VOTE_POOL_NAME = "voted";
    //投票截止时间，超时失效不能投票
    private final String VOTE_OVER_FLAG = "votflag";

    public VotingRank(int time){
        jedis = new Jedis("127.0.0.1");
        jedis.set(VOTE_OVER_FLAG, "1", "NX", "PX", time);
    }
    /**
     * 发布投票内容
     * @param voteData 投票内容，自定义投票内容
     */
    public String pubVoting(Map<String, String> voteData){
        String voteId = "vote:" + String.valueOf(jedis.incr("voteId"));
        voteData.put("voteId", voteId);
        //1.发布投票内容
        jedis.hmset(voteId, voteData);
        //2.初始化票池，一人一个作品只能一票
        jedis.sadd(VOTE_POOL_NAME+ voteId, "");
        long score = System.currentTimeMillis()/1000;
        //文章发布时间可用来排序
        jedis.zadd(VOTE_TIME_NAME, score, voteId);
        jedis.zadd(VOTE_SCORE_NAME, 0, voteId);
        return voteId;
    }

    /**
     * 进行投票
     * @param voter
     * @param voteId
     */
    public void voting(String voter, String voteId){
        if(!jedis.exists(VOTE_OVER_FLAG)){
            System.out.println("投票已经结束了");
            return;
        }
        //投票人员池添加人员成功，投票
        if(jedis.sadd(VOTE_POOL_NAME+ voteId, voter) == 1){
            jedis.zincrby(VOTE_SCORE_NAME, 1, voteId);
            System.out.println(voter + " 向" + voteId+"投了一票");
        }
    }

    /**
     * 根据指定key排序 取出分页数据
     * @param typeKey
     * @param page
     * @param count
     * @return
     */
    public List<Map<String, String>> getSortVote(String typeKey, int page, int count){
        //倒序取出文章
        Set<String> voteids = jedis.zrevrange(typeKey, (page -1) * count, (page) * count);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (String voteid : voteids) {
            list.add(jedis.hgetAll(voteid));
        }
        return list;
    }

    public void test(){
        ScanParams params = new ScanParams();
        params.count(5);
        params.match("vot*");
        ScanResult<Tuple> result = jedis.zscan(VOTE_SCORE_NAME, "0", params);
        List<Tuple> list = result.getResult();
        for(Tuple t : list){
            String name = t.getElement();
            double score = t.getScore();
        }
    }
}
