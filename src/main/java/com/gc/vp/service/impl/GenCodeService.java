package com.gc.vp.service.impl;

import com.gc.vp.entity.vo.code.ComTreeReq;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class GenCodeService {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public String genCode(List<ComTreeReq> comTreeReq) {
        // 创建configuration对象,得到模板文件保存的目录
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        // 加载一个模板文件，创建一个模板对象
        Locale locale = new Locale("zh");
        HashMap<String,List<ComTreeReq>> map = new HashMap<>();
        map.put("comList",comTreeReq);
        Template t;
        try {
            t = configuration.getTemplate("page.ftl", locale, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String code;
        try {
            code = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return code;
    }
}
