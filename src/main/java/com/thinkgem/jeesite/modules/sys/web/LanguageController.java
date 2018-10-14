package com.thinkgem.jeesite.modules.sys.web;

import com.thinkgem.jeesite.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 国际化控制器
 *
 * @author LinZhaoguan
 */
@Controller
@RequestMapping("${adminPath}/sys/i18n")
public class LanguageController {

    @Autowired
    CookieLocaleResolver resolver;

    /**
     * 语言切换
     */
    @RequestMapping("/language")
    public ModelAndView language(HttpServletRequest request, HttpServletResponse response, String language) {

        if (StringUtils.isNotBlank(language)) {
            language = language.toLowerCase();
            switch (language) {
                case "zh_cn":
                    resolver.setLocale(request, response, Locale.CHINA);
                    break;
                case "en":
                    resolver.setLocale(request, response, Locale.ENGLISH);
                    break;
                default:
                    resolver.setLocale(request, response, Locale.CHINA);
                    break;
            }
        }
        return new ModelAndView("redirect:/");
    }

}
