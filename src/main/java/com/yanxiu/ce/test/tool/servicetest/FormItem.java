package com.yanxiu.ce.test.tool.servicetest;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yanxiu.ce.common.mybatis.annotation.Entity;

/**
 * @Description: Form表单字段解析
 * @author: tangm
 * @date: 2016年2月20日 
 * @version: 1.0
 */
public class FormItem {
    String name;
    String inputType = "text";
    boolean required = false;

    String[] values = null;

    private FormItem() {

    }

    public static FormItem createSelectItem(String name,Class<?> enumClazz){
        FormItem item = new FormItem();
        item.setName(name);
        item.setInputType("select");
        Object[] objs = enumClazz.getEnumConstants();
        String[] values = new String[objs.length];
        for(int i=0;i<objs.length;i++){
            values[i] = objs[i].toString();
        }
        item.setValues(values);
        return item;
    }

    public static FormItem createInputFile(String name){
        FormItem item = new FormItem();
        item.setName(name);
        item.setInputType("file");
        return item;
    }

    public static FormItem createInputNumber(String name){
        FormItem item = new FormItem();
        item.setName(name);
        item.setInputType("number");
        return item;
    }

    public static FormItem createInputText(String name){
        FormItem item = new FormItem();
        item.setName(name);
        item.setInputType("text");
        return item;
    }

    public static FormItem createInputDate(String name){
        FormItem item = new FormItem();
        item.setName(name);
        item.setInputType("date");
        return item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public static List<FormItem> build(Class<?> beanClazz, String name) {
        List<FormItem> list = new ArrayList<FormItem>();

        if (beanClazz == String.class) {
            list.add(FormItem.createInputText(name));
        } else if (beanClazz == Integer.class 
                || beanClazz == Long.class
                || beanClazz == BigDecimal.class) {
            list.add(FormItem.createInputNumber(name));
        } else if (beanClazz == Date.class) {
            list.add(FormItem.createInputDate(name));
        } else if (beanClazz.isEnum()) {
            list.add(FormItem.createSelectItem(name, beanClazz));
        } else if (beanClazz.isArray()){
            System.out.println(beanClazz);
            if(beanClazz.getComponentType().equals(CommonsMultipartFile.class)){
                list.add(FormItem.createInputFile(name));
            }
        } else if(List.class.isAssignableFrom(beanClazz)){

        } else {
            PropertyDescriptor[] props = BeanUtils
                    .getPropertyDescriptors(beanClazz);
            if(beanClazz.getAnnotation(Entity.class)!=null){
                for (PropertyDescriptor prop : props) {
                    list.addAll(build(prop.getPropertyType(), prop.getName()));
                }
            }
        }
        return list;
    }

    public String toString() {
        String html = "";
        if(this.inputType.equals("text")){
            html = "<input type=\"text\" name=\"" + this.name + "\" />";
        }else if(this.inputType.equals("select")){
            StringBuilder sb = new StringBuilder();
            sb.append("<select name=\""+this.name+"\">");
            for(String s:this.values){
                sb.append("<option value=\""+s+"\">"+s+"</option>");
            }
            sb.append("</select>");
            html = sb.toString();
        }else if(this.inputType.equals("file")){
            html = "<input type=\"file\" name=\"" + this.name + "\" />";
        }else if(this.inputType.equals("date")){
            html = "<input class=\"date\" type=\"text\" name=\"" + this.name + "\" />";
        }else if(this.inputType.equals("number")){
            html = "<input class=\"number\" type=\"text\" name=\"" + this.name + "\" />";
        }
        return html;
    }

    public String getHtml(){
        return this.toString();
    }
}