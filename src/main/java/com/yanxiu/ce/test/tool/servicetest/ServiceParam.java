package com.yanxiu.ce.test.tool.servicetest;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yanxiu.ce.common.mybatis.annotation.Entity;
import com.yanxiu.ce.common.utils.DateUtils;

/**
 * @Description: 测试service的传入参数
 * @author: tangm
 * @date: 2016年2月20日 
 * @version: 1.0
 */
public class ServiceParam {
    String name;
    Class<?> type;

    public ServiceParam(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    private static BeanUtilsBean beanUtilsBean = null;

    public static BeanUtilsBean getBeanUtilsBean(){
        if(beanUtilsBean==null){
            ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean(){
                @SuppressWarnings({ "all" })
                @Override
                public Object convert(String value, Class clazz) {
                    //this.register(converter, clazz);
                    if (clazz.isEnum()){
                        return Enum.valueOf(clazz, value);
                    }else{
                        return super.convert(value, clazz);
                    }
                }

            };

            DateConverter dateConverter = new DateConverter();
            dateConverter.setPatterns(
                new String[]{
                    "yyyy-MM-dd",
                    "yyyy-MM-dd HH:mm:ss",
                    "dd/MM/yyyy"
                }
            );
            convertUtilsBean.register(dateConverter, java.util.Date.class);
            beanUtilsBean = new BeanUtilsBean(convertUtilsBean);
        }
        return beanUtilsBean;
    }

    /**
     * 解析参数
     * @param model
     * @param param
     * @return
     * @throws Exception
     */
    public static Object parseParam(Map<String,String> model,ServiceParam param) throws Exception{
        Object value = null;
        Class<?> beanClazz = param.getType();
        String strVal = (String)model.get(param.getName());
        if (beanClazz == String.class) {
            if(StringUtils.isNotBlank(strVal)){
                value = ConvertUtils.convert(model.get(param.getName()), beanClazz);
            }
        } else if (beanClazz == Integer.class 
                || beanClazz == Long.class
                || beanClazz == BigDecimal.class) {
            if(StringUtils.isNotBlank(strVal)){
                value = ConvertUtils.convert(model.get(param.getName()), beanClazz);
            }
        } else if (beanClazz == Date.class) {
            if(StringUtils.isNotBlank(strVal)){
                value = DateUtils.guessDate(strVal);
            }
        } else if (beanClazz.isEnum()) {
            if(StringUtils.isNotBlank(strVal)){
                value = ConvertUtils.convert(model.get(param.getName()), beanClazz);
            }
        } else if(beanClazz == CommonsMultipartFile.class){

        } else if(beanClazz.isArray() && beanClazz.getComponentType()==CommonsMultipartFile.class){

        } else {
            PropertyDescriptor[] props = PropertyUtils
                    .getPropertyDescriptors(beanClazz);
            System.out.println(beanClazz);
            value = beanClazz.newInstance();
            if(beanClazz.getAnnotation(Entity.class)!=null){
                for (PropertyDescriptor prop : props) {
                    String str = (String)model.get(prop.getName());
                    if(StringUtils.isBlank(str)){
                        continue;
                    }
                    try{
                        getBeanUtilsBean().setProperty(value, prop.getName(), model.get(prop.getName()));
                    }catch(Exception e){
                        System.out.println(prop.getName());
                        System.out.println(model.get(prop.getName()));
                        e.printStackTrace();

                    }
                }
            }
        }
        return value;
    }

    /**
     * service的方法 参数 解析
     * @param method
     * @return
     * @throws Exception
     */
    public static ServiceParam[] parseServiceParams(Method method) throws Exception {
        System.out.println(method.getDeclaringClass().getName());
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(ServiceParam.class)); 
        CtClass cc = pool.get(method.getDeclaringClass().getName());
        CtMethod cm = cc.getDeclaredMethod(method.getName());
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr  = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            System.out.println(cc.getName());
            throw new RuntimeException(cc.getName()+",LocalVariableAttribute attr is null");
        }

        ServiceParam[] params = null;
        try {
            params = new ServiceParam[cm.getParameterTypes().length];
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        Class<?>[] pcs = method.getParameterTypes();
        for (int i = 0; i < params.length; i++) {
            String name = attr.variableName(i + pos);
            Class<?> type = pcs[i];
            params[i] = new ServiceParam(name, type);
        }
        return params;
    }
}
