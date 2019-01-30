package com.jiushig.sample.springUtil;

import com.jiushig.springutil.BASE64;
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
public class BASE64Test {
    private final Logger logger = LoggerFactory.getLogger(BASE64Test.class);

    @Test
    public void decrypt(){
        logger.info(BASE64.decrypt("MQ=="));
    }

    @Test
    public void encrypt(){
        logger.info(BASE64.decrypt("1"));
    }
}
