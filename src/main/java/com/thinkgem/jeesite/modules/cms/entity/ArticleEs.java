package com.thinkgem.jeesite.modules.cms.entity;

import java.io.Serializable;

/**
 * 文章搜索es
 *
 * @author ThinkGem
 * @version 2018-10-12
 */
public class ArticleEs implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;      //主键id

    private String title;   //标题

    private String keyword; //关键字

    private String content; //内容

    private Integer weight; //权重，越大越靠前

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * 构造方法
     *
     * @param id      主键id
     * @param title   标题
     * @param keyword 关键字
     * @param content 内容
     * @param weight  权重
     */
    public ArticleEs(String id, String title, String keyword, String content, Integer weight) {
        this.id = id;
        this.title = title;
        this.keyword = keyword;
        this.content = content;
        this.weight = weight;
    }

    public ArticleEs() {

    }
}
