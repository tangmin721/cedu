package com.yanxiu.ce.core.mq.producer.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.yanxiu.ce.core.mq.entity.RegisterMsg;
import com.yanxiu.ce.core.mq.producer.RegisterProducer;
import com.yanxiu.ce.test.junit.SpringJunitTest;

/**
 * 测试   注册消息发送
 * @author tangmin
 * @date 2016年8月11日
 */
public class TestRegisterProducer extends SpringJunitTest{
	
	@Autowired
	private RegisterProducer registerProducer;
	
	/**
	 * appKey
	 */
	@Value("${teachehubei.appKey}")
	private String  APP_KEY;
	
	@Test
	public void testSentMsg(){
		RegisterMsg ms = new RegisterMsg();
		
		ms.setAppKey(APP_KEY);
		ms.setIdCard("433333198909091234");
		
		registerProducer.sendMessage(ms);
	}

}
