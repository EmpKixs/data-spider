package com.kixs.spider.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * (PoetryAuthor)实体类
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/8/18 16:54
 */
@Data
@TableName("poetry_author")
public class PoetryAuthorEntity implements Serializable {
    private static final long serialVersionUID = 933139218002562238L;
    /**
     * 主键ID
     */
    private String id;
    /**
     * 作者名称
     */
    private String name;
    /**
     * 生年
     */
    private String birthDate;
    /**
     * 卒年
     */
    private String obitDate;
    /**
     * 朝代
     */
    private String dynasty;
    /**
     * 生平简介
     */
    private String description;
    /**
     * 详情介绍页面地址
     */
    private String url;
    /**
     * 数据版本日期
     */
    private LocalDate version;


}