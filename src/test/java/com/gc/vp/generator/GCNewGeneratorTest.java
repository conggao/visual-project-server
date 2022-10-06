package com.gc.vp.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class GCNewGeneratorTest {
    private final String url = "jdbc:mysql://localhost:3306/visual-proj?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8";
    private final String userName = "root";
    private final String password = "ywx950704";
    private final String projectPath = System.getProperty("user.dir");


    @Test
    public void generator() {
        FastAutoGenerator.create(url, userName, password)
                .globalConfig(builder -> {
                    builder.author("conggao") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.gc.vp") // 设置父包名
//                            .moduleName("system") // 设置父包模块名
                            .controller("web")
                            .service("service")
                            .entity("entity.po")
                            .mapper("mapper")
                            .xml("mapper.xml")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder
                            .addExclude(new ArrayList<>())
                            .addTablePrefix("vp_")
                            // 设置控制器
                            .controllerBuilder()
                            .enableRestStyle()
                            .enableHyphenStyle()
                            // 设置实体
                            .entityBuilder()
                            .enableTableFieldAnnotation()
                            .enableLombok()
                            .enableChainModel()
                            .logicDeleteColumnName("enable")
                            .enableRemoveIsPrefix()
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                            .formatFileName("%sPo");
                })
                .templateConfig(builder -> {
                    builder.controller("templates/mybatis/controller.java")
                            .entity("templates/mybatis/entity.java")
                            .mapper("/templates/mybatis/mapper.java")
                            .xml("/templates/mybatis/mapper.xml");
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
