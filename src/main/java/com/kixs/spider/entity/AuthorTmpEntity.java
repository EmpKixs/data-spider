package com.kixs.spider.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (AuthorTmp)实体类
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/8/20 09:21
 */
@Data
@TableName("author_tmp")
public class AuthorTmpEntity implements Serializable {
    private static final long serialVersionUID = -21731438292955804L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 朝代
     */
    private String aDynasty;
    /**
     * 朝代
     */
    private String paDynasty;
    /**
     * 生平简介
     */
    private String aDescription;
    /**
     * 生平简介
     */
    private String paDescription;
    /**
     * 生平简介（短）
     */
    private String aShortDescription;
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
     * 主键ID
     */
    private String aId;
    /**
     * 主键ID
     */
    private String paId;


}