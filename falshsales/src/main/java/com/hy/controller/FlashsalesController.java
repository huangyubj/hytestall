//package com.hy.controller;
//
//import com.hy.current.RedisLock;
//import com.hy.entity.Catalog;
//import com.hy.service.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.script.RedisScript;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//public class FlashsalesController {
//
//    private static final String LOCKPRE = "good_lock_";
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private RedisLock redisLock;
//
//    @PostMapping("/addGoodsInfo")
//    @ResponseBody
//    public String addGoodsInfo(String name, int total, int sold)  {
//        Integer id = null;
//        try {
//            Catalog catalog = new Catalog();
//            catalog.setName(name);
//            catalog.setTotal(total);
//            catalog.setSold(sold);
//            id = orderService.addGoodsInfo(catalog);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "init is error";
//        }
//        return String.format("insert id=%d", id);
//    }
//
//    /**
//     * 直接处理，获取不到锁的将秒杀失败
//     * @param id
//     * @return
//     */
//    @PostMapping("/placeOrder")
//    @ResponseBody
//    public String placeOrder(int id){
//        boolean locked = false;
//        String key = LOCKPRE + id;
//        String uuid = String.valueOf(id);
//        try {
//            while (!locked){
//                locked = redisLock.lock(key, uuid, "15");
//                if(locked){
//                    Integer orderId = orderService.placeOrer(id);
//                    return String.valueOf(orderId);
//                }else{
//                    return "get lock faild";
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            if(locked){
//                redisLock.unLock(key, uuid);
//            }
//        }
//        return "error";
//    }
//
//    @PostMapping("/placeOrerWithDatabase")
//    @ResponseBody
//    public String placeOrerWithDatabase(int id){
//        Integer orderId = null;
//        try {
//            orderId = orderService.placeOrerWithDatabase(id);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return String.valueOf(orderId);
//    }
//    /**
//     * 通过队列缓存下单请求，按照请求顺序处理订单
//     * @param id
//     * @return
//     */
//    @PostMapping("/placeOrderWithQueue")
//    @ResponseBody
//    public String placeOrderWithqueue(int id){
//        Integer orderId = null;
//        try {
//            orderId = orderService.placeOrerWithqueue(id);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return String.valueOf(orderId);
//    }
//
//    @PostMapping("/placeOrderWhithMq")
//    @ResponseBody
//    public String placeOrderWithMq(int id){
//        Integer orderId = null;
//        try {
//            orderId = orderService.placeOrerWithMq(id);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return String.valueOf(orderId);
//    }
//}
