package com.village.villageupload.codeGen.gen.mysql;

import com.village.villageupload.codeGen.gen.ColumnSelector;
import com.village.villageupload.codeGen.gen.GeneratorConfig;
import com.village.villageupload.codeGen.gen.TableDefinition;
import com.village.villageupload.codeGen.gen.TableSelector;

import java.util.Map;

import static com.village.villageupload.codeGen.util.FieldUtil.convertString;

/**
 * 查询mysql数据库表
 */
public class MySqlTableSelector extends TableSelector {

	public MySqlTableSelector(ColumnSelector columnSelector,
                              GeneratorConfig dataBaseConfig) {
		super(columnSelector, dataBaseConfig);
	}

	@Override
	protected String getShowTablesSQL(GeneratorConfig generatorConfig) {
		String dbName = generatorConfig.getDbName();
		// 兼容dbName包含特殊字符会报错的情况
		if (!(dbName.startsWith("`") && dbName.endsWith("`"))) {
			dbName = String.format("`%s`",dbName);
		}
		String sql = "SHOW TABLE STATUS FROM " + dbName;
		if(this.getSchTableNames() != null && this.getSchTableNames().size() > 0) {
			StringBuilder tables = new StringBuilder();
			for (String table : this.getSchTableNames()) {
				tables.append(",'").append(table).append("'");
			}
			sql += " WHERE NAME IN (" + tables.substring(1) + ")";
		}
		return sql;
	}

	@Override
	protected TableDefinition buildTableDefinition(Map<String, Object> tableMap) {
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(convertString(tableMap.get("NAME")));
		tableDefinition.setComment(convertString(tableMap.get("COMMENT")));
		return tableDefinition;
	}

}
