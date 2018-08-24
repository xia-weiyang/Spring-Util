package com.jiushig.sample.springUtil;

import com.jiushig.springutil.CommonUtil;
import com.jiushig.springutil.ServerInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by zk on 2018/8/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
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
    public void getPort() {
        logger.info(String.valueOf(ServerInfo.getPort()));
    }
}