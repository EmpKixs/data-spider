package com.kixs.spider;

import com.kixs.spider.poetry.PoetryAuthorSpider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSpiderApplicationTests {

    @Resource
    private PoetryAuthorSpider poetryAuthorSpider;

    @Test
    public void test() {
        poetryAuthorSpider.crawl();
    }
}
