package com.yanxiu.ce;

import com.yanxiu.ce.core.basic.enums.ExcelColEnum;

public class MainTest {
//	public static void main(String[] args) {
//			System.out.println(System.getProperty("java.io.tmpdir"));
//	}
	
	public static void main(String[] args) {
		MainTest test = new MainTest();
        System.out.println(test.lpad(10, 12112));
        String str = "　县";
        System.out.println(str);
        System.out.println(str.replaceAll("　", ""));
        System.out.println(str.trim());
        System.out.println(ExcelColEnum.getName(1));
        System.out.println(System.getProperty("java.io.tmpdir"));
        
        System.out.println("image/gif".indexOf("image2"));
        
        String idCard = "431111198808081234";
        String plainpw = idCard.substring(idCard.length()-6, idCard.length());
        System.out.println(plainpw);

        for (int i = 0; i < 10000; i++) {
            if (i%9==0 && i%8==1 && i%7==0 && i%6==3 && i%5==1 && i%4==1 && i%3==0){
                System.out.println(i);
                break;
            }
        }
    }

    /**
     * 补齐不足长度
     * @param length 长度
     * @param number 数字
     * @return
     */
    private String lpad(int length, int number) {
        String f = "%0" + length + "d";
        return String.format(f, number);
    }
    
    
   
}
