package com.yanxiu.ce.test.tool.servicetest;

import java.io.Writer;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.common.core.controller.BaseController;

/**
 * @Description: 测试service
 * @author: tangm
 * @date: 2016年2月20日 
 * @version: 1.0
 */
@Controller
@RequestMapping("/webtest")
public class WebTestController extends BaseController implements ApplicationContextAware{

    ApplicationContext applicationContext;
    
    /**
     * 进入测试页面
     * @return
     */
    @RequestMapping("/list")
    public String list(){
        return "/webtest/list";
    }

    /**
     * 提交测试的service,到达的页面
     * @param serviceClazz
     * @param model
     * @return
     * @throws ClassNotFoundException
     */
    @RequestMapping("/listmethod")
    public String list(@RequestParam(value="serviceClazz",required=false) String strServiceClazz,Model model)
            throws ClassNotFoundException {
        Class<?> serviceClazz = Class.forName(strServiceClazz);

        List<String> methodList = new ArrayList<String>();
        for (Method method : serviceClazz.getDeclaredMethods()) {
            if(Modifier.isPublic(method.getModifiers())){
                System.out.println(method.getName());
                methodList.add(method.getName());
            }
        }
        model.addAttribute("serviceClazz", strServiceClazz);
        model.addAttribute("methodList", methodList);
        return "/webtest/listmethod";
    }

    /**
     * 测试service传参
     * @param strServiceClazz
     * @param methodName
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/showForm")
    public String showForm(@RequestParam(value="serviceClazz",required=false) String strServiceClazz,
    		@RequestParam(value="serviceMethod",required=false) String methodName,
    		Model model)
            throws Exception {
        Class<?> serviceClazz = Class.forName(strServiceClazz);
        Method method = this.findMehtodByName(serviceClazz, methodName);
        ServiceParam[] params = ServiceParam.parseServiceParams(method);
        List<FormItem> itemList = new ArrayList<FormItem>();
        for(ServiceParam p:params){
            itemList.addAll(FormItem.build(p.getType(), p.getName()));
        }
        boolean isMutipart = false;
//        for(FormItem item:itemList){
//            if(item.getInputType().equals("file")){
//                isMutipart = true;
//            }
//        }

        model.addAttribute("isMutipart", isMutipart);
        model.addAttribute("serviceClazz",strServiceClazz);
        model.addAttribute("serviceMethod",methodName);
        model.addAttribute("formItems", itemList);
        return "/webtest/serviceform";
    }

    /**
     * 输出结果
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("testResult")
    public void testPost(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String strServiceClazz = request.getParameter("serviceClazz");
        Class<?> serviceClazz = Class.forName(strServiceClazz);
        String methodName = request.getParameter("serviceMethod");
        Method method = this.findMehtodByName(serviceClazz, methodName);
        ServiceParam[] params = ServiceParam.parseServiceParams(method);
        Object[] o = new Object[params.length];

        Map<String,String> map = new HashMap<String,String>();

        for(String s:request.getParameterMap().keySet()){
            map.put(s, request.getParameter(s));
        }

        for(int i=0;i<params.length;i++){
            o[i] = ServiceParam.parseParam(map, params[i]);
        }
        Object service = this.applicationContext.getBean(serviceClazz);
        Object result = method.invoke(service, o);
        response.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.flush();
        writer.close();
    }

    private Method findMehtodByName(Class<?> serviceClazz, String methodName) {
        for (Method method : serviceClazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

}

