package com.kixs.spider.poetry;

import com.kixs.spider.dao.PoetryAuthorDao;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;

/**
 * 诗词作者信息爬取
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/8/4 11:34
 */
@Component
public class PoetryAuthorSpider {

    @Resource
    private PoetryAuthorDao poetryAuthorDao;

    /**
     * 爬取
     */
    public void crawl() {
        PoetryAuthorPipeline pipeline = new PoetryAuthorPipeline();
        pipeline.setPoetryAuthorDao(poetryAuthorDao);

        Spider.create(new PoetryAuthorCrawler())
                .addUrl("https://so.gushiwen.cn/authors/")
                .addPipeline(pipeline)
                .thread(5)
                .run();
    }

}
