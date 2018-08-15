package com.jiushig.springutil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;

/**
 * Created by zk on 2018/8/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonUtilTest {
    private final Logger logger = LoggerFactory.getLogger(CommonUtilTest.class);

    @Test
    public void getSystemPath() {
        logger.info(CommonUtil.getSystemPath());
    }

    @Test
    public void isEmpty() {
        assertTrue(CommonUtil.isEmpty(""));
    }

    @Test
    public void copyObject() {
    }
}