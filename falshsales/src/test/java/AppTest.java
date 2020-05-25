import com.hy.FlashsalesApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlashsalesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void flashsaleTest() throws InterruptedException {
        String url = "http://localhost:9999/placeOrder";
        int testnum = 3;
        CountDownLatch countDownLatch = new CountDownLatch(testnum);
        for(int i = 0; i < testnum; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
                new Thread(() -> {
                    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                    params.add("id", "4");
                    Integer result = testRestTemplate.postForObject(url, params, Integer.class);
//                    if(result != 0) {
                        System.out.println("-------------" + result);
//                    }
                }
                ).start();
            } catch (Exception e) {
            }
        }
        countDownLatch.await();
        System.out.println("over");
    }
}
