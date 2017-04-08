package com.yanxiu.ce.core.mq.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.core.mq.dao.RegisterMsgDao;
import com.yanxiu.ce.core.mq.entity.RegisterMsg;
import com.yanxiu.ce.core.mq.entity.RegisterMsgQuery;
import com.yanxiu.ce.core.mq.enums.MsgStatusEnum;
import com.yanxiu.ce.core.mq.producer.RegisterProducer;
import com.yanxiu.ce.core.mq.service.RegisterMsgService;

/**
 * 消息管理
 * @author tangmin
 * @date 2016-08-12 14:31:04
 */
@Service("registerMsgService")
public class RegisterMsgServiceImpl extends BaseServiceImpl<RegisterMsg, RegisterMsgQuery> implements RegisterMsgService{
	@Autowired
	private RegisterMsgDao dao;

	@Autowired
	private RegisterProducer registerProducer;
	
	@Override
	protected BaseDao<RegisterMsg, RegisterMsgQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 获取seq
	 */
	@Override
	public Integer selectMaxSeq() {
		Integer selectMaxSeq = this.dao.selectMaxSeq();
		if(selectMaxSeq!=null){
			return selectMaxSeq;
		}
		return 0;
	}

	/**
	 * 校验entity是否可修改（code是否存在）
	 */
	@Override
	public Boolean checkUserIdExit(RegisterMsg entity) {
//		Long count = this.dao.selectCheckNameExit(entity.getName(), entity.getId());
//		if(count>0){
//			return false;
//		}
		return true;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(RegisterMsg entity) {
		String msg = "";
		if(entity.getId()==null){
			this.insert(entity);
			msg = "添加成功！";
		}else {
			this.update(entity);
				msg = "编辑成功！";
		}
		return msg;
	}

	@Override
	@Transactional
	public void producer() {
		
//		YanxiuUserInfo register = new YanxiuUserInfo();
//		registerProducer.sendMessage(register);
		
		/**
		 * 1、查询出状态为0的 100条记录
		 *  
		 * 2、遍历，发送到队列里，并设置producer time  并设置num=num+1  状态为 MQ_ING
		 *  
		 */
		
		RegisterMsgQuery query = new RegisterMsgQuery();
		//query.setStatus(MsgStatusEnum.UNPROCESS.getValue()+"");
		
		query.setSelectJoinFlag(true);
		List<Integer> statuses = Lists.newArrayList();
		statuses.add(MsgStatusEnum.UNPROCESS.getValue());
		statuses.add(MsgStatusEnum.SYS_ERR.getValue());
		statuses.add(MsgStatusEnum.BZ_ERR.getValue());
		
		query.setJoinPids(statuses);
		query.setPageSize(100l);
		List<RegisterMsg> registerMsgs = this.dao.selectListPage(query);
		for(RegisterMsg registerMsg:registerMsgs){
			
			registerMsg.setProducerTime(new Date());
			registerMsg.setNum(registerMsg.getNum()+1);
			registerMsg.setStatus(MsgStatusEnum.MQ_ING.getValue());
			
			this.dao.update(registerMsg);
			
			//发送到消息队列
			registerProducer.sendMessage(registerMsg);
		}
		
		
		
	}

}