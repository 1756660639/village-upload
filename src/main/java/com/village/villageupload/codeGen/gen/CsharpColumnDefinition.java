package com.village.villageupload.codeGen.gen;

import com.village.villageupload.codeGen.gen.converter.ColumnTypeConverter;
import com.village.villageupload.codeGen.gen.converter.CsharpColumnTypeConverter;
import com.village.villageupload.codeGen.util.FieldUtil;

/**
 * 提供C# Velocity变量
 * @author tanghc
 */
public class CsharpColumnDefinition extends ColumnDefinition {

    private static final ColumnTypeConverter COLUMN_TYPE_CONVERTER = new CsharpColumnTypeConverter();

    public String getField() {
        return FieldUtil.underlineFilter(getColumnName());
    }

    public String getProperty() {
        return FieldUtil.upperFirstLetter(getField());
    }

    @Override
    public ColumnTypeConverter getColumnTypeConverter() {
        return COLUMN_TYPE_CONVERTER;
    }
}
