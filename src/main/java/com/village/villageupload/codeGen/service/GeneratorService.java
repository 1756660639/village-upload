package com.village.villageupload.codeGen.service;

import com.village.villageupload.codeGen.common.GeneratorParam;
import com.village.villageupload.codeGen.gen.CodeFile;
import com.village.villageupload.codeGen.gen.GeneratorConfig;
import com.village.villageupload.codeGen.gen.SQLContext;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 生成代码逻辑
 */
public interface GeneratorService {

    static ExecutorService executorService = Executors.newFixedThreadPool(2);


    /**
     * 生成代码内容,map的
     *
     * @param generatorParam 生成参数
     * @param generatorConfig 数据源配置
     * @return 一张表对应多个模板
     */
    List<CodeFile> generate(GeneratorParam generatorParam, GeneratorConfig generatorConfig);


    // 格式化代码
    String formatCode(String fileName, String content);


    /**
     * 返回SQL上下文列表
     *
     * @param generatorParam 参数
     * @param generatorConfig 配置
     * @return 返回SQL上下文
     */
    List<SQLContext> buildSQLContextList(GeneratorParam generatorParam, GeneratorConfig generatorConfig);

    void setPackageName(SQLContext sqlContext, String packageName);

    void setDelPrefix(SQLContext sqlContext, String delPrefix);

    void setAuthor(SQLContext sqlContext, String author);

    String doGenerator(SQLContext sqlContext, String template);

}
