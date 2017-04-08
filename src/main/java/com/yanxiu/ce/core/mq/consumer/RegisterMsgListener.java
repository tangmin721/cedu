package com.yanxiu.ce.core.mq.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;

import com.alibaba.fastjson.JSONObject;
import com.yanxiu.ce.core.mq.entity.RegisterMsg;

/**
 * 队列监听器   监听注册队列里是否有消息，如果有，就进行消费处理
 * @author tangmin
 * @date 2016年8月11日
 */
public class RegisterMsgListener implements SessionAwareMessageListener<Message>{
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterMsgListener.class);
	
	@Autowired
	private RegisterBiz registerBiz;
	
	public synchronized void onMessage(Message message, Session session) throws JMSException {
		try {
			ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
			final String ms = msg.getText();
			logger.info("==>receive message:" + ms);
			logger.info("在onMessage中线程ID是"+Thread.currentThread());
			RegisterMsg register = JSONObject.parseObject(ms, RegisterMsg.class);// 转换成相应的对象
			if (register == null) {
				return;
			}

			try {
				//注册
				registerBiz.register(register);
			} catch (Exception e) {
				logger.error("==>RegisterException:", e);
			}
		} catch (Exception e) {
			logger.error("==>", e);
		}
	}
	
	
	

}

