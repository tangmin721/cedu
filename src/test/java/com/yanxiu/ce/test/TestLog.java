package com.yanxiu.ce.test;

import com.yanxiu.ce.test.junit.SpringJunitTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: slf4j.Logger
 * @author: tangmin
 * @date: 2017年02月27日 09:57
 * @version: 1.0
 */
public class TestLog extends SpringJunitTest{
    Logger logger = LoggerFactory.getLogger(TestLog.class);

    @Test
    public void test(){
        String parm1 = "parm1 str";
        logger.info("hello,my parm:{}",parm1);

        String parm2 = "parm2 str";
        logger.info("hello,my parm:%s",123);

        try {
            throw new  RuntimeException("错误信息");
        }catch (Exception e){
            logger.error("error:{}",e);
        }



    }
}
