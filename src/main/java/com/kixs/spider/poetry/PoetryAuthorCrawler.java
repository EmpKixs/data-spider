package com.kixs.spider.poetry;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kixs.spider.entity.PoetryAuthorEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 诗词作者爬取器
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/8/4 13:59
 */
@Slf4j
public class PoetryAuthorCrawler implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(5000);

    @Override
    public void process(Page page) {
        // 作者详情页
        if(page.getUrl().regex("https://so.gushiwen.cn/authorv\\w+.aspx").match()) {
            PoetryAuthorEntity author = new PoetryAuthorEntity();
            author.setId(IdWorker.getIdStr());
            author.setName(page.getHtml().xpath("//div[@class='main3']")
                    .xpath("//div[@class='sonspic']//h1//b/text()").toString());
            author.setDescription(page.getHtml().xpath("//div[@class='main3']")
                    .xpath("//div[@class='sonspic']//p/text()").toString());
            author.setUrl(page.getUrl().toString());
            page.putField("author", author);
        }
        // 作者列表页
        List<String> links = page.getHtml().xpath("//div[@class='sonspic']")
                .links().regex("https://so.gushiwen.cn/authorv\\w+.aspx")
                .all().stream().distinct().collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(links)) {
            page.addTargetRequests(links);
        }
        // 下一页
        List<String> nextList = page.getHtml().xpath("//div[@class='pagesright']//a[@class='amore']").links().all();
        if (CollectionUtils.isNotEmpty(nextList)) {
            page.addTargetRequests(nextList);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}
