package com.thinkgem.jeesite.test.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.entity.ArticleEs;
import com.thinkgem.jeesite.modules.cms.service.ElasticService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * es所有文章测试Controller
 *
 * @author wenbin
 * @version 2018-10-12
 */
@Controller
@RequestMapping(value = "/cms/es")
public class TestElasticController extends BaseController {

    @Autowired
    private ElasticService elasticService;

    @RequestMapping(value = "article", method = RequestMethod.GET)
    @ResponseBody
    public Object searchArticle(
            @RequestParam(value = "fieldName", required = false) String fieldName,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
            @RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
            @RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return elasticService.getArticle(id);
        } else {
            return elasticService.searchArticle(name, start, count, null, fieldName);
        }
    }

    @RequestMapping(value = "article", method = RequestMethod.POST)
    @ResponseBody
    public Object addArticle(@RequestBody ArticleEs articleEs) {
        elasticService.addArticle(articleEs);
        return "success";
    }

    @RequestMapping(value = "article", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delArticle(String id) {
        elasticService.delArticle(id);
        return "success";
    }

    @RequestMapping(value = "article", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateArticle(ArticleEs articleEs) {
        elasticService.updateArticle(articleEs);
        return "success";
    }


}
