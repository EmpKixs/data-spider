package com.kixs.spider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kixs.spider.entity.PoetryAuthorEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (PoetryAuthor)表数据库访问层
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/8/18 16:54
 */
@Mapper
@Repository
public interface PoetryAuthorDao extends BaseMapper<PoetryAuthorEntity> {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param poetryAuthor 实例对象
     * @return 对象列表
     */
    List<PoetryAuthorEntity> queryAll(PoetryAuthorEntity poetryAuthor);

    /**
     * 修改数据
     *
     * @param poetryAuthor 实例对象
     * @return 影响行数
     */
    int updateEntity(PoetryAuthorEntity poetryAuthor);

}