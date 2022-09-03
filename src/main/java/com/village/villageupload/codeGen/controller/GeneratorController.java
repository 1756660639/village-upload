package com.village.villageupload.codeGen.controller;

import com.village.villageupload.codeGen.common.GeneratorParam;
import com.village.villageupload.codeGen.entity.GenDatasourceConfigEntity;
import com.village.villageupload.codeGen.gen.GeneratorConfig;
import com.village.villageupload.codeGen.service.GenDatasourceConfigService;
import com.village.villageupload.codeGen.service.impl.GeneratorServiceImpl;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanghc
 */
@RestController
@RequestMapping("/generate")
public class GeneratorController {

    @Autowired
    private GenDatasourceConfigService datasourceConfigService;

    @Autowired
    private GeneratorServiceImpl generatorService;

    /**
     * 生成代码
     *
     * @param generatorParam 生成参数
     * @return 返回代码内容
     */
    @RequestMapping("/code")
    public BaseResponsePojo<Object> code(@RequestBody GeneratorParam generatorParam) {
        String datasourceConfigId = generatorParam.getDatasourceConfigId();
        GenDatasourceConfigEntity datasourceConfig = datasourceConfigService.getById(datasourceConfigId);
        GeneratorConfig generatorConfig = GeneratorConfig.build(datasourceConfig);
        return BaseResponse.newSuccessResponse(generatorService.generate(generatorParam, generatorConfig));
    }

}
