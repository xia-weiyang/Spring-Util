package com.jiushig.sample.springUtil;

import com.jiushig.springutil.HttpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by zk on 2019/1/31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpUtilTest {

    @Test
    public void getImage() {
        HttpUtil.download(new HttpUtil.Builder()
                .setUrl("https://www.oschina.net/img/hot3.png"), "/a/b.png");
    }
}
