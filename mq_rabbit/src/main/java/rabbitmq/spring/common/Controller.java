package rabbitmq.spring.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
@RequestMapping("/rabbitmq")
public class Controller {

    @Autowired
    private Producter rabbitProducter;

    @ResponseBody
    @GetMapping("/send")
    public String sendOne(){
        rabbitProducter.sendMessage("hello rabbitMQ");
        try {
            Thread.currentThread().sleep(1000);
            return "send success";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
