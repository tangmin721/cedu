package com.yanxiu.ce.core.mq.consumer;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.utils.HttpClientUtil;
import com.yanxiu.ce.common.yanxiuapi.user.YanxiuResultDto;
import com.yanxiu.ce.core.mq.entity.RegisterMsg;
import com.yanxiu.ce.core.mq.enums.MsgStatusEnum;
import com.yanxiu.ce.core.mq.enums.MsgTypeEnum;
import com.yanxiu.ce.core.mq.service.RegisterMsgService;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.service.UserService;

/**
 * 业务逻辑类    处理队列中的消息 执行业务逻辑
 * @author tangmin
 * @date 2016年8月11日
 */
@Component("registerBiz")
public class RegisterBiz {
	
	protected static final Logger logger = LoggerFactory.getLogger(RegisterBiz.class);
	
	@Autowired
	private ThreadPoolTaskExecutor threadPool;
	
	@Autowired
	private UserService userService;
	
	@Value("${teachehubei.registerUserUrl}")
	private String  REGISTER_URL;
	
	@Value("${teachehubei.syncUserUrl}")
	private String  SYNC_URL;
	
	@Value("${teachehubei.appKey}")
	private String  APP_KEY;
	
	@Autowired
	private RegisterMsgService registerMsgService;
	
	@Transactional
	public void register(final RegisterMsg register) {
		threadPool.execute(new Runnable() {
			public void run() {
				try {
					
					if(register.getType()==MsgTypeEnum.REGISTER_USER.getValue()){
						registerUser(register);
					}else if(register.getType()==MsgTypeEnum.SYNC_USER.getValue()){
						syncUser(register);
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
					//更新数据库记录
					RegisterMsg dbMsg = registerMsgService.selectById(register.getId());
					dbMsg.setStatus(MsgStatusEnum.SYS_ERR.getValue());
					dbMsg.setConsumerTime(new Date());
					dbMsg.setMemo("execute异常："+e.getStackTrace());
					registerMsgService.saveOrUpdate(dbMsg);
				}
			}

			
		});
	}
	
	/**
	 * 注册用户
	 * @param register
	 */
	private void registerUser(final RegisterMsg register) {
		//业务处理
		Map<String, String> param = Maps.newHashMap();
		param.put("appKey", APP_KEY);
		param.put("realname", register.getRealname());
		param.put("password", register.getPassword());
		param.put("passport", register.getPassport());
		param.put("mobile", register.getMobile());
		param.put("email", register.getEmail());
		param.put("idCard", register.getIdCard());
		param.put("schoolName", register.getSchoolName());
		param.put("course", register.getCourse());
		param.put("stage", register.getStage());
		param.put("grade", register.getGrade());
		param.put("province", register.getProvince());
		param.put("city", register.getCity());
		param.put("area", register.getArea());
		param.put("gender", register.getGender());
		
		String result = HttpClientUtil.doPost(REGISTER_URL, param);
		
		//数据库zhong记录
		RegisterMsg dbMsg = registerMsgService.selectById(register.getId());
		dbMsg.setConsumerTime(new Date());//消费时间
		
		if(StringUtils.isBlank(result)){//如果返回结果为空，说明是连接不上接口
			
			dbMsg.setStatus(MsgStatusEnum.SYS_ERR.getValue());
			dbMsg.setMemo("register接口返回结果为空，程序错误");
			registerMsgService.saveOrUpdate(dbMsg);
		}else{
			YanxiuResultDto resultObj = JSON.parseObject(result, YanxiuResultDto.class);
			if(resultObj.getCode()==0){
				//得到uid
				Long uid = Long.parseLong(resultObj.getData().toString());
				
				//更新到user里
				User user = userService.selectByLoginName(register.getPassport());
				if(user!=null){
					userService.updateUid(user.getLoginName(),uid);
				}
				
				dbMsg.setStatus(MsgStatusEnum.FINISH.getValue());
				dbMsg.setMemo(resultObj.getDesc());
				registerMsgService.saveOrUpdate(dbMsg);
			}else{
				//更新数据库记录
				dbMsg.setStatus(MsgStatusEnum.BZ_ERR.getValue());
				dbMsg.setMemo("code:"+resultObj.getCode()+",desc:"+resultObj.getDesc());
				registerMsgService.saveOrUpdate(dbMsg);
			}
		}
	}
	
	
	/**
	 * 同步用户
	 * @param register
	 */
	private void syncUser(final RegisterMsg register) {
		//业务处理
		Map<String, String> param = Maps.newHashMap();
		param.put("appKey", APP_KEY);
		
		param.put("uid", register.getUid());
		
		param.put("passport", register.getPassport());
		
		param.put("mobile", register.getMobile());
		param.put("email", register.getEmail());
		param.put("idCard", register.getIdCard());
		param.put("schoolName", register.getSchoolName());
		param.put("course", register.getCourse());
		param.put("stage", register.getStage());
		param.put("grade", register.getGrade());
		param.put("province", register.getProvince());
		param.put("city", register.getCity());
		param.put("area", register.getArea());
		param.put("gender", register.getGender());
		String result = HttpClientUtil.doPost(SYNC_URL, param);
		
		//数据库zhong记录
		RegisterMsg dbMsg = registerMsgService.selectById(register.getId());
		dbMsg.setConsumerTime(new Date());//消费时间
		
		if(StringUtils.isBlank(result)){//如果返回结果为空，说明是连接不上接口
			
			dbMsg.setStatus(MsgStatusEnum.SYS_ERR.getValue());
			dbMsg.setMemo("sync接口返回结果为空，程序错误");
			registerMsgService.saveOrUpdate(dbMsg);
		}else{
			YanxiuResultDto resultObj = JSON.parseObject(result, YanxiuResultDto.class);
			if(resultObj.getCode()==0){
				dbMsg.setStatus(MsgStatusEnum.FINISH.getValue());
				dbMsg.setMemo(resultObj.getDesc());
				registerMsgService.saveOrUpdate(dbMsg);
			}else{
				//更新数据库记录
				dbMsg.setStatus(MsgStatusEnum.BZ_ERR.getValue());
				dbMsg.setMemo("code:"+resultObj.getCode()+",desc:"+resultObj.getDesc());
				registerMsgService.saveOrUpdate(dbMsg);
			}
		}
	}
	
}

