package com.kixs.spider.service.impl;

import com.kixs.spider.dao.PoetryAuthorDao;
import com.kixs.spider.entity.PoetryAuthorEntity;
import com.kixs.spider.service.PoetryAuthorService;
import com.kixs.spider.utils.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (PoetryAuthor)表服务实现类
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/8/18 16:54
 */
@Service("poetryAuthorService")
public class PoetryAuthorServiceImpl extends BaseServiceImpl<PoetryAuthorDao, PoetryAuthorEntity> implements PoetryAuthorService {

}