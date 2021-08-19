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
                .addUrl("https://www.shicimingju.com/category/all")
                .addPipeline(pipeline)
                .thread(5)
                .run();
    }

    public static void main(String[] args) {
        Spider.create(new PoetryAuthorCrawler())
                .addUrl("https://www.shicimingju.com/category/all")
                .thread(5)
                .run();
    }

}
