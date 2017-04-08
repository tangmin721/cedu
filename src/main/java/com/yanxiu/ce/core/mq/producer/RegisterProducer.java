package com.yanxiu.ce.core.mq.producer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yanxiu.ce.core.mq.entity.RegisterMsg;

/**
 * 注册  消息生产者
 * @author tangmin
 * @date 2016年8月11日
 */
@Service("registerProducer")
public class RegisterProducer {

	@Autowired
	@Qualifier("registerJmsTemplate")
	private JmsTemplate jmsTemplate;
	
	/**
	 * 发送消息.
	 * @param mail 
	 */
	public void sendMessage(final RegisterMsg msg) {
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(JSONObject.toJSONString(msg));
			}
		});
	}
}


