package com.village.villageupload.codeGen.controller;

import com.village.villageupload.codeGen.gen.DBConnect;
import com.village.villageupload.codeGen.gen.DbType;
import com.village.villageupload.codeGen.entity.GenDatasourceConfigEntity;
import com.village.villageupload.codeGen.gen.GeneratorConfig;
import com.village.villageupload.codeGen.service.GenDatasourceConfigService;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/genDatasourceConfig")
public class GenDatasourceConfigController {

    @Autowired
    private GenDatasourceConfigService genDatasourceConfigService;

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAll")
    public BaseResponsePojo<Object> listAll(GenDatasourceConfigEntity resource){
        return BaseResponse.newSuccessResponse(genDatasourceConfigService.listAll(resource));
    }

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAllPage")
    public BaseResponsePojo<Object> listAllPage(GenDatasourceConfigEntity resource){
        return BaseResponse.newSuccessResponse(genDatasourceConfigService.listAllPage(resource));
    }

    /**
     * 新增数据
     * @param resource
     * @return
     */
    @PostMapping("/insert")
    public BaseResponsePojo<Object> insert(@RequestBody GenDatasourceConfigEntity resource){
        GenDatasourceConfigEntity genDatasourceConfigEntity = genDatasourceConfigService.insert(resource);
        return BaseResponse.newSuccessResponse(genDatasourceConfigEntity);
    }

    /**
     * 修改数据
     * @param resource
     * @return
     */
    @PostMapping("/update")
    public BaseResponsePojo<Object> update(@RequestBody GenDatasourceConfigEntity resource){
        GenDatasourceConfigEntity genDatasourceConfigEntity = genDatasourceConfigService.update(resource);
        return BaseResponse.newSuccessResponse(genDatasourceConfigEntity);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public BaseResponsePojo<Object> deleteById(@RequestBody String id){
        int num = genDatasourceConfigService.deleteById(id);
        return BaseResponse.newSuccessResponse();
    }

    @GetMapping("/table")
    public BaseResponsePojo<Object> listTable(String id) {
        List list = genDatasourceConfigService.listTable(id);
        return BaseResponse.newSuccessResponse(list);
    }

    @PostMapping("/test")
    public BaseResponsePojo<Object> test(@RequestBody GenDatasourceConfigEntity datasourceConfig) {
        String error = DBConnect.testConnection(GeneratorConfig.build(datasourceConfig));
        if (error != null) {
            return BaseResponse.newErrorResponse(error);
        }
        return BaseResponse.newSuccessResponse();
    }

    @GetMapping("/dbtype")
    public BaseResponsePojo<Object> dbType(GenDatasourceConfigEntity datasourceConfig) {
        List<DbTypeShow> dbTypeShowList = Stream.of(DbType.values())
                .map(dbType -> new DbTypeShow(dbType.getDisplayName(), dbType.getType()))
                .collect(Collectors.toList());
        return BaseResponse.newSuccessResponse(dbTypeShowList);
    }
    private static class DbTypeShow {
        private String label;
        private Integer dbType;

        public DbTypeShow(String label, Integer dbType) {
            this.label = label;
            this.dbType = dbType;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Integer getDbType() {
            return dbType;
        }

        public void setDbType(Integer dbType) {
            this.dbType = dbType;
        }
    }
}
