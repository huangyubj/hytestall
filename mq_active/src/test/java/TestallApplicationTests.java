import activemq.MainApplication;
import activemq.queue.Productor;
import activemq.replyto.ReplyProductor;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.Destination;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class TestallApplicationTests {

	@Autowired
	private Productor productor;
	@Autowired
	private ReplyProductor reProductor;

	@Test
	void testActiveMQQueue(){
		for (int i = 0; i < 3; i++) {
			productor.sendQueueMessage("queue.test", "NO:" + i + " test message ");
		}
	}

	@Test
	void testActiveMQTopic(){
		Destination destination = new ActiveMQTopic("topic.test");
		for (int i = 0; i < 3; i++) {
			productor.sendMessage(destination, "NO:" + i + " test message ");
		}
	}

	@Test
	void testActiveMQReply() throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			reProductor.sendMessage("consum.reply.test", "NO:" + i + " test message ");
		}
		//主线程结束，消息未接受会存在不打印消息
		Thread.currentThread().sleep(1000);
	}


	@Test
	void contextLoads() {

	}

}
