package com.village.villageupload.codeGen.service.impl;

import com.village.villageupload.codeGen.common.GeneratorParam;
import com.village.villageupload.codeGen.entity.GenTemplateConfigEntity;
import com.village.villageupload.codeGen.gen.*;
import com.village.villageupload.codeGen.service.GenGenerateHistoryService;
import com.village.villageupload.codeGen.service.GenTemplateConfigService;
import com.village.villageupload.codeGen.service.GeneratorService;
import com.village.villageupload.codeGen.util.FormatUtil;
import com.village.villageupload.codeGen.util.VelocityUtil;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 生成代码逻辑
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

    static ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Autowired
    private GenTemplateConfigService templateConfigService;

    @Autowired
    private GenGenerateHistoryService generateHistoryService;

    @Value("${village.format-xml}")
    private String formatXml;


    /**
     * 生成代码内容,map的
     *
     * @param generatorParam 生成参数
     * @param generatorConfig 数据源配置
     * @return 一张表对应多个模板
     */
    @Override
    public List<CodeFile> generate(GeneratorParam generatorParam, GeneratorConfig generatorConfig) {
        List<SQLContext> contextList = this.buildSQLContextList(generatorParam, generatorConfig);
        List<CodeFile> codeFileList = new ArrayList<>();

        for (SQLContext sqlContext : contextList) {
            setPackageName(sqlContext, generatorParam.getPackageName());
            setDelPrefix(sqlContext, generatorParam.getDelPrefix());
            setAuthor(sqlContext, generatorParam.getAuthor());
            for (String tcId : generatorParam.getTemplateConfigIdList()) {
                GenTemplateConfigEntity template = templateConfigService.getById(tcId);
                String folder = template.getFolder();
                if (StringUtils.isEmpty(folder)) {
                    folder = template.getName();
                }
                String fileName = doGenerator(sqlContext, template.getFileName());
                String content = doGenerator(sqlContext, template.getContent());
                content = this.formatCode(fileName, content);
                CodeFile codeFile = new CodeFile();
                codeFile.setFolder(folder);
                codeFile.setFileName(fileName);
                codeFile.setContent(content);
                codeFileList.add(codeFile);
            }
        }

        executorService.execute(() -> {
            generateHistoryService.saveHistory(generatorParam);
        });

        return codeFileList;
    }

    // 格式化代码
    @Override
    public String formatCode(String fileName, String content) {
        if (Objects.equals("true", formatXml) && fileName.endsWith(".xml")) {
            return FormatUtil.formatXml(content);
        }
        return content;
    }


    /**
     * 返回SQL上下文列表
     *
     * @param generatorParam 参数
     * @param generatorConfig 配置
     * @return 返回SQL上下文
     */
    @Override
    public List<SQLContext> buildSQLContextList(GeneratorParam generatorParam, GeneratorConfig generatorConfig) {

        List<String> tableNames = generatorParam.getTableNames();
        List<SQLContext> contextList = new ArrayList<>();
        SQLService service = SQLServiceFactory.build(generatorConfig);

        TableSelector tableSelector = service.getTableSelector(generatorConfig);
        tableSelector.setSchTableNames(tableNames);

        List<TableDefinition> tableDefinitions = tableSelector.getTableDefinitions();

        for (TableDefinition tableDefinition : tableDefinitions) {
            SQLContext sqlContext = new SQLContext(tableDefinition);
            sqlContext.setDbName(generatorConfig.getDbName());
            contextList.add(sqlContext);
        }

        return contextList;
    }

    @Override
    public void setPackageName(SQLContext sqlContext, String packageName) {
        if (StringUtils.hasText(packageName)) {
            sqlContext.setPackageName(packageName);
        }
    }

    @Override
    public void setDelPrefix(SQLContext sqlContext, String delPrefix) {
        if (StringUtils.hasText(delPrefix)) {
            sqlContext.setDelPrefix(delPrefix);
        }
    }

    @Override
    public void setAuthor(SQLContext sqlContext, String author) {
        if (StringUtils.hasText(author)) {
            sqlContext.setAuthor(author);
        }
    }

    @Override
    public String doGenerator(SQLContext sqlContext, String template) {
        if (template == null) {
            return "";
        }
        VelocityContext context = new VelocityContext();
        Object pkColumn = sqlContext.getTableDefinition().getPkColumn();
        if (pkColumn == null) {
            pkColumn = Collections.emptyMap();
        }
        context.put("context", sqlContext);
        context.put("table", sqlContext.getTableDefinition());
        context.put("pk", pkColumn);
        context.put("columns", sqlContext.getTableDefinition().getColumnDefinitions());
        context.put("csharpColumns", sqlContext.getTableDefinition().getCsharpColumnDefinitions());

        return VelocityUtil.generate(context, template);
    }

}
