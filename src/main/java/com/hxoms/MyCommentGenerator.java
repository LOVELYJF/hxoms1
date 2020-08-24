package com.hxoms;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.util.List;

/**
 * 生成自定义的注释和注解
 *
 * @author sunqian
 * @date 2020/5/4 17:26
 */
public class MyCommentGenerator extends DefaultCommentGenerator {

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("com.hxoms.common.hxannotation.ColumnAnnotation");
        topLevelClass.addImportedType("com.hxoms.common.hxannotation.IdAnnotation");
        topLevelClass.addImportedType("com.hxoms.common.hxannotation.TableAnnotation");
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        for (IntrospectedColumn column : allColumns) {
            String shortName = column.getFullyQualifiedJavaType().getShortName();
            if ("Date".equals(shortName)) {
                topLevelClass.addImportedType("com.fasterxml.jackson.annotation.JsonFormat");
                topLevelClass.addImportedType("org.springframework.format.annotation.DateTimeFormat");
            }
        }
        topLevelClass.addAnnotation("@TableAnnotation(TableName = \"" + introspectedTable.getFullyQualifiedTable() +
                "\", TableDescription=\"" + introspectedTable.getRemarks().split(";")[0] + "\")");
        //swagger注解
        topLevelClass.addAnnotation("@ApiModel(value = \"" + introspectedTable.getRemarks().split(";")[0] + "\")");
    }

    /**
     * 为字段增加注解
     *
     * @author sunqian
     * @date 2020/5/4 16:40
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        for (IntrospectedColumn col : primaryKeyColumns) {
            if (col.getActualColumnName().equals(introspectedColumn.getActualColumnName())) {
                field.addAnnotation("@IdAnnotation");
            }
        }

        field.addAnnotation("@ColumnAnnotation(FieldName = \"" + introspectedColumn.getActualColumnName() + "\",   FieldDescription=\"" + introspectedColumn.getRemarks() + "\")");
        String shortName = field.getType().getShortName();

        if ("Date".equals(shortName)) {
            field.addAnnotation("@JsonFormat(pattern = \"yyyy.MM.dd\")");
            field.addAnnotation("@DateTimeFormat(pattern = \"yyyy.MM.dd\")");
        }
        //swagger注解
        field.addAnnotation("@ApiModelProperty(value=\"" + introspectedColumn.getRemarks() + "\")");
    }

    /**
     * get和set方法不设置任何注释
     *
     * @author sunqian
     * @date 2020/5/4 16:39
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    }
}