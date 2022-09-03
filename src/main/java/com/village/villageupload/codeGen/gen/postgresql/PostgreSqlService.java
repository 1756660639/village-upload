package com.village.villageupload.codeGen.gen.postgresql;

import com.village.villageupload.codeGen.gen.GeneratorConfig;
import com.village.villageupload.codeGen.gen.SQLService;
import com.village.villageupload.codeGen.gen.TableSelector;

/**
 * @author tanghc
 */
public class PostgreSqlService implements SQLService {
    @Override
    public TableSelector getTableSelector(GeneratorConfig generatorConfig) {
        return new PostgreSqlTableSelector(new PostgreSqlColumnSelector(generatorConfig), generatorConfig);
    }

}
