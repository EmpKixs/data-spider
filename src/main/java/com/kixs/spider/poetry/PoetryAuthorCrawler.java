package com.kixs.spider.poetry;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import lombok.extern.slf4j.Slf4j;

/**
 * 诗词作者爬取器
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/8/4 13:59
 */
@Slf4j
public class PoetryAuthorCrawler extends BreadthCrawler {

    public PoetryAuthorCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        /*start pages*/
        this.addSeed("https://blog.csdn.net/wangmx1993328/article/details/81662001");

        /*do not fetch jpg|png|gif*/
        //this.addRegex("-.*\\.(jpg|png|gif).*");
        /*do not fetch url contains #*/
        //this.addRegex("-.*#.*");

        setThreads(5);
        getConf().setTopN(100);

        //enable resumable mode
        //setResumable(true);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        log.info("URL：" + page.url());
    }
}
