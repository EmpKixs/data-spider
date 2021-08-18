package com.kixs.spider.poetry;

import com.kixs.spider.dao.PoetryAuthorDao;
import com.kixs.spider.entity.PoetryAuthorEntity;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 诗词作者Pipeline
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/8/18 17:00
 */
@Slf4j
public class PoetryAuthorPipeline implements Pipeline {

    private PoetryAuthorDao poetryAuthorDao;

    public void setPoetryAuthorDao(PoetryAuthorDao poetryAuthorDao) {
        this.poetryAuthorDao = poetryAuthorDao;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        Object data = resultItems.get("author");
        if (Objects.nonNull(data)) {
            PoetryAuthorEntity author = (PoetryAuthorEntity) data;
            author.setVersion(LocalDate.now());
            poetryAuthorDao.insert(author);
        }
    }
}
