package com.kixs.spider.poetry;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kixs.spider.entity.PoetryAuthorEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.HtmlNode;

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

    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36")
            .setCharset("utf-8")
            .setSleepTime(5000);

    @Override
    public void process(Page page) {
        // 作者详情页
        if(page.getUrl().regex("https://www.shicimingju.com/chaxun/zuozhe/\\d+.html").match()) {
            PoetryAuthorEntity author = new PoetryAuthorEntity();
            author.setId(IdWorker.getIdStr());
            author.setName(page.getHtml().xpath("//div[@class='card about_zuozhe']").xpath("//h4//a/text()").toString());
            author.setDescription(Jsoup.parse(page.getHtml().xpath("//div[@class='card about_zuozhe']")
                    .xpath("//div[@class='des']")
                    .replace("<[a|A]\\s*[^>]*>(.*?)</[a|A]>", "$1")
                    .replace("<[b|B][r|R]>", "").toString()).text());
            author.setDynasty(page.getHtml().xpath("//div[@class='card about_zuozhe']")
                    .xpath("//div[@class='aside_left']//div[@class='aside_val']")
                    .xpath("//a/text()").toString());
            author.setUrl(page.getUrl().toString());
            page.putField("author", author);
        }
        // 作者列表页-作者项
        List<String> links = page.getHtml()
                .xpath("//div[@class='card zuozhe_card']")
                .xpath("//div[@class='zuozhe_list_item']//h3")
                .links().all();
        if (CollectionUtils.isNotEmpty(links)) {
            page.addTargetRequests(links);
        }
        // 所有页导航
        if (page.getUrl().regex("https://www.shicimingju.com/category/all").match()) {
            // 仅在第一页把所有页面请求加入
            List<String> navList = page.getHtml()
                    .xpath("//div[@id='list_nav']")
                    .xpath("//div[@id='list_nav_all']")
                    .links().all();
            if (CollectionUtils.isNotEmpty(navList)) {
                page.addTargetRequests(navList);
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}
