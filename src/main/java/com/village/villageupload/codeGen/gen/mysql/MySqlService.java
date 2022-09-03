package com.village.villageupload.codeGen.gen.mysql;

import com.village.villageupload.codeGen.gen.GeneratorConfig;
import com.village.villageupload.codeGen.gen.SQLService;
import com.village.villageupload.codeGen.gen.TableSelector;

public class MySqlService implements SQLService {

	@Override
	public TableSelector getTableSelector(GeneratorConfig generatorConfig) {
		return new MySqlTableSelector(new MySqlColumnSelector(generatorConfig), generatorConfig);
	}

}
