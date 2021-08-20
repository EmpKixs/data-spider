package com.kixs.spider;

import com.kixs.spider.service.AuthorTmpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 数据清洗测试
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/8/20 11:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorTmpServiceTests {

    @Resource
    private AuthorTmpService authorTmpService;

    @Test
    public void testDataScrubbing() {
        authorTmpService.dataScrubbing();
    }
}
