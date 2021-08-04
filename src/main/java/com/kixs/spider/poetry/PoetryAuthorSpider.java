package com.kixs.spider.poetry;

/**
 * 诗词作者信息爬取
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/8/4 11:34
 */
public class PoetryAuthorSpider {

    public static void main(String[] args) throws Exception {
        PoetryAuthorCrawler crawler = new PoetryAuthorCrawler("crawl", true);
        /*start crawl with depth of 4*/
        crawler.start(4);
    }
}
