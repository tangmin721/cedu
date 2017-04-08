package com.yanxiu.ce.core.mq.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.mq.entity.RegisterMsg;
import com.yanxiu.ce.core.mq.entity.RegisterMsgQuery;

/**
 * 消息管理
 * @author tangmin
 * @date 2016-08-12 14:31:04
 */
public interface RegisterMsgService extends BaseService<RegisterMsg, RegisterMsgQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean checkUserIdExit(RegisterMsg entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(RegisterMsg entity);

	/**
	 * 启动produce 注册消息队列
	 */
	void producer();

}
