package com.jiushig.sample.springUtil;

import com.jiushig.springutil.AES;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zk on 2019/1/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AESTest {
    private final Logger logger = LoggerFactory.getLogger(BASE64Test.class);

    @Test
    public void decrypt() {
        logger.info(AES.decrypt("61J3HK2CX66QFpGT9Nd9uA==", "1"));
    }

    @Test
    public void encrypt() {
        logger.info(AES.encrypt("1", "1"));
    }
}
