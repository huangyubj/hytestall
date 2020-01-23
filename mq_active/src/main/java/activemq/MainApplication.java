package activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(value = "com.hy.test.testall.rabbitmq")
public class MainApplication {


	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
