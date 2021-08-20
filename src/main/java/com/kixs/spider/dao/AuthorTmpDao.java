package com.kixs.spider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kixs.spider.entity.AuthorTmpEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (AuthorTmp)表数据库访问层
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/8/20 09:15
 */
@Mapper
@Repository
public interface AuthorTmpDao extends BaseMapper<AuthorTmpEntity> {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param authorTmp 实例对象
     * @return 对象列表
     */
    List<AuthorTmpEntity> queryAll(AuthorTmpEntity authorTmp);

    /**
     * 修改数据
     *
     * @param authorTmp 实例对象
     * @return 影响行数
     */
    int updateEntity(AuthorTmpEntity authorTmp);

}