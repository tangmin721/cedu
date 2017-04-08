
package com.yanxiu.ce.common.yanxiuapi;

import java.util.Collection;
import java.util.List;



/**
 * @author richardzhao
 *
 */

/**
 * java版本的escape和 unescape[对应javaScript里的函数]
 * 
 * 此工具类还用作velocity的工具包，处理不同的字符处理；
 * 
 */
public class EscapUnescape{
   
    public static String escape(String src) {
        int i;
        char j;
        if(src==null || src.equalsIgnoreCase("")) return "";
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j)
                    || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    /**
     * unescape ===>js
     * @param src
     * @return String
     */
    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }
    /**
     * 返回数组的数据元素
    */
    public Object getObject(Object[] array,int index){
    	if(array!=null&&index<array.length&&index>=0){
    		return array[index];
    	}else{
    		return null;
    	}
    }
    
    /**
     * 返回集合的元素
    */
    public Object getObject(Collection array,int index){
    	if(array!=null&&index<array.size()&&index>=0){
    		return array.toArray()[index];
    	}else{
    		return null;
    	}
    }
    /***
     * 
     * @param origin 字符源头
     * @param defaultStr 多出字符要显示的内容
     * @param length  规定长度
     * @param showLength 要截取的长度
     * @param codeType 字符编码方式
     * @return
     */
    public static String getCutterString(String origin,String defaultStr,int length,int showIndex,String codeType) {
		String name="";
       if(origin==null)
    	   name="";
		if(origin!=null){
			//if(shortName.length()>20)
			try {
				if(origin.getBytes(codeType).length>length){//解决中文字符
					return CharacterSetToolkit.substring(origin,showIndex)+(defaultStr!=null?defaultStr:"");
				}else{
					return origin;
				}
					
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return name;	
	}
    
    /***
     * 
     * @param origin 字符源头
     * @param defaultStr 多出字符要显示的内容
     * @param length  规定长度
     * @param showLength 要截取的长度
     * @param codeType 字符编码方式
     * @return
     */
    public static String getCutStr(String origin,int length) {
		String name="";
       if(origin==null)
    	   name="";
		if(origin!=null){
			//if(shortName.length()>20)
			return getCutterString(origin,"..",length,length-2,"GBK");
		}
		return name;	
	}
    
   public static int getSize(List list){
	   if(list!=null)
		   return list.size();
	   else
		   return 0;
   } 

    public  static void main(String[]args){
    	System.out.print(getCutStr("唐奇阿奇霉素颗粒剂",16));
    }
    
    public static Long getTime(){
    	return System.currentTimeMillis() ;
    }
}