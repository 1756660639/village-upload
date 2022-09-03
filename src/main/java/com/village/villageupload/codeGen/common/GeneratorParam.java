package com.village.villageupload.codeGen.common;

import java.util.List;

public class GeneratorParam {
	/** datasource_config主键 */
	private String datasourceConfigId;

	/** 表名 */
	private List<String> tableNames;

	/** template_config主键 */
	private List<String> templateConfigIdList;

	private String packageName;

	private String delPrefix;

	private String author;

	private String charset = "UTF-8";

	public String getDatasourceConfigId() {
		return datasourceConfigId;
	}

	public void setDatasourceConfigId(String datasourceConfigId) {
		this.datasourceConfigId = datasourceConfigId;
	}

	public List<String> getTableNames() {
		return tableNames;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}

	public List<String> getTemplateConfigIdList() {
		return templateConfigIdList;
	}

	public void setTemplateConfigIdList(List<String> templateConfigIdList) {
		this.templateConfigIdList = templateConfigIdList;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getDelPrefix() {
		return delPrefix;
	}

	public void setDelPrefix(String delPrefix) {
		this.delPrefix = delPrefix;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	public String toString() {
		return "GeneratorParam{" +
				"datasourceConfigId=" + datasourceConfigId +
				", tableNames=" + tableNames +
				", templateConfigIdList=" + templateConfigIdList +
				", packageName='" + packageName + '\'' +
				", delPrefix='" + delPrefix + '\'' +
				", author='" + author + '\'' +
				", charset='" + charset + '\'' +
				'}';
	}
}
