package com.thinkgem.jeesite.modules.cms.service;

import com.thinkgem.jeesite.common.utils.ElasticUtils;
import com.thinkgem.jeesite.modules.cms.entity.ArticleEs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * es Service
 *
 * @author wenbin
 * @version 2018-10-13
 */
@Service
public class ElasticService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 根据id查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Map getArticle(String id) {
        Map map = new HashMap();
        try {
            map = ElasticUtils.getDocument("article", id);
        }catch (Exception e) {
            logger.error("查询article根据id从es出错:" + e.getMessage(), e);
        }
        return map;
    }

    /**
     * 查询
     *
     * @param keyword    关键字
     * @param start      起始数量
     * @param count      一页数量
     * @param sort       排序字段, 倒序, 如果按照字段排序, fielddata请先设置为true
     * @param fieldNames 字段, 多个
     * @return
     * @throws Exception
     */
    public Map searchArticle(String keyword, int start, int count, String sort, String... fieldNames) {
        Map map = new HashMap();
        try {
            map = ElasticUtils.search("article", keyword, start, count, sort, fieldNames);
        } catch (Exception e) {
            logger.error("查询article从es出错:" + e.getMessage(), e);
        }
        return map;
    }

    /**
     * 添加到es
     *
     * @param articleEs
     * @throws Exception
     */
    public void addArticle(ArticleEs articleEs) {
        try {
            ElasticUtils.addDocument(articleEs);
        } catch (Exception e) {
            logger.error("添加article到es出错:" + e.getMessage(), e);
        }
    }

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    public void delArticle(String id) {
        try {
            ArticleEs articleEs = new ArticleEs();
            articleEs.setId(id);
            ElasticUtils.deleteDocument(articleEs);
        } catch (Exception e) {
            logger.error("删除article从es出错:" + e.getMessage(), e);
        }
    }

    /**
     * 修改
     *
     * @param articleEs
     * @throws Exception
     */
    public void updateArticle(ArticleEs articleEs) {
        try {
            ElasticUtils.updateDocument(articleEs);
        } catch (Exception e) {
            logger.error("更新article从es出错:" + e.getMessage(), e);

        }
    }


}
