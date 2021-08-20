package com.kixs.spider.service;

import com.kixs.spider.entity.AuthorTmpEntity;
import com.kixs.spider.utils.service.BaseService;

/**
 * (AuthorTmp)表服务接口
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/8/20 09:15
 */
public interface AuthorTmpService extends BaseService<AuthorTmpEntity> {

    /**
     * 数据清洗
     */
    void dataScrubbing();
}