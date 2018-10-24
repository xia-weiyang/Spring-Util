package com.jiushig.sample.springUtil;

import com.jiushig.springutil.RSA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zk on 2018/10/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RSATest {

    @Test
    public void initTest() throws Exception {
        RSA.init();
    }
}
