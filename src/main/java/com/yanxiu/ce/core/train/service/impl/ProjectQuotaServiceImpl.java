package com.yanxiu.ce.core.train.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.train.dao.ProjectQuotaDao;
import com.yanxiu.ce.core.train.entity.ProjectQuota;
import com.yanxiu.ce.core.train.entity.ProjectQuotaQuery;
import com.yanxiu.ce.core.train.entity.ProjectUserStatus;
import com.yanxiu.ce.core.train.entity.ProjectUserStatusQuery;
import com.yanxiu.ce.core.train.service.ProjectQuotaService;
import com.yanxiu.ce.core.train.service.ProjectReportConfigService;
import com.yanxiu.ce.core.train.service.ProjectUserStatusService;
import com.yanxiu.ce.system.enums.UserTypeEnum;

/**
 * 名额分配管理
 * @author tangmin
 * @date 2016-04-21 09:51:46
 */
@Service("projectQuotaService")
public class ProjectQuotaServiceImpl extends BaseServiceImpl<ProjectQuota, ProjectQuotaQuery> implements ProjectQuotaService{
	@Autowired
	private ProjectQuotaDao dao;
	
	@Autowired
	private ProjectUserStatusService pusService;
	
	@Autowired
	private ProjectReportConfigService confService;

	@Override
	protected BaseDao<ProjectQuota, ProjectQuotaQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 获取seq
	 */
	@Override
	public Integer selectMaxSeq(Long pid) {
		Integer selectMaxSeq = this.dao.selectMaxSeq(pid);
		if(selectMaxSeq!=null){
			return selectMaxSeq;
		}
		return 0;
	}
	
	
	/**
	 * 覆写删除方法，删除的时候，同时删除ProjectUserStatus
	 */
	@Override
	public Long deleteById(Long id) {
		ProjectQuota old = this.selectById(id);
		ProjectUserStatusQuery pusQuery = new ProjectUserStatusQuery();
		pusQuery.setProvince(old.getProvince().toString());
		pusQuery.setCity(old.getCity().toString());
		pusQuery.setTown(old.getTown().toString());
		pusQuery.setSchool(old.getSchool().toString());
		pusQuery.setPid(old.getPid().toString());
		ProjectUserStatus pus = null;
		List<ProjectUserStatus> puses = pusService.selectList(pusQuery );
		if(puses!=null && puses.size()>0){
			pus = puses.get(0);
		}
		if(pus!=null){
			pusService.deleteById(pus.getId());
		}
		
		return this.dao.deleteById(id);
	};
	
	@Override
	public Long deleteByIds(List<Long> ids) {
		Long result = 0l;
		for(Long id:ids){
			this.deleteById(id);
			result++;
		}
		return result;
	};
	

	/**
	 * 校验entity是否可修改（code是否存在）
	 */
	@Override
	public Boolean checkNameExit(ProjectQuota entity) {
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
	public String saveOrUpdate(ProjectQuota entity) {
		if(entity.getProvince()==null){
			entity.setProvince(0);
		}
		if(entity.getCity()==null){
			entity.setCity(0);
		}
		if(entity.getTown()==null){
			entity.setTown(0);
		}
		if(entity.getSchool()==null){
			entity.setSchool(0l);
		}
		if(!checkNameExit(entity)){
			if(entity.getId()==null){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"名称已经存在，新增失败");
			}else {
				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"名称已经存在，修改失败");
			}
		}
		
		
		Integer countedPid = this.countedPid(entity.getPid());//已分配人数
		Integer countPidNum = confService.countPidNum(entity.getPid());//项目配置人数
		
		if(entity.getId()==null){
			
			Integer countPpcts = this.countPpcts(entity.getPid(), entity.getProvince(), entity.getCity(), entity.getTown(), entity.getSchool());
			if(countPpcts>0){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"该机构已经分配名额，保存失败");
			}
			
			Integer restNum = countPidNum-countedPid;//剩余人数
			if(entity.getNum()>restNum){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"分配名额已经超过预分配名额，保存失败");
			}
			
			this.insert(entity);
			//添加的同时，创建一条记录
			ProjectUserStatus pus = new ProjectUserStatus();
			entity = this.selectById(entity.getId());
			changePusPro(entity, pus);
			pusService.insert(pus);
			
		}else {
			//修改的同时，也需要修改原记录对应的ProjectUserStatus
			//1、先找到
			ProjectQuota old = this.selectById(entity.getId());
			
			Integer restNum = countPidNum-countedPid+old.getNum();//剩余人数
			if(entity.getNum()>restNum){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"分配名额已经超过预分配名额，保存失败");
			}
			
			ProjectUserStatusQuery pusQuery = new ProjectUserStatusQuery();
			pusQuery.setProvince(old.getProvince().toString());
			pusQuery.setCity(old.getCity().toString());
			pusQuery.setTown(old.getTown().toString());
			pusQuery.setSchool(old.getSchool().toString());
			pusQuery.setPid(old.getPid().toString());
			ProjectUserStatus pus = null;
			List<ProjectUserStatus> puses = pusService.selectList(pusQuery );
			if(puses!=null && puses.size()>0){
				pus = puses.get(0);
			}
			this.update(entity);
			
			if(pus==null){
				pus = new ProjectUserStatus();
			}
			changePusPro(entity, pus);
			pusService.saveOrUpdate(pus);
			
		}
		return "保存成功！";
	}

	/**
	 * 根据ProjectQuota设置ProjectUserStatus属性
	 * @param entity
	 * @param pus
	 */
	private void changePusPro(ProjectQuota entity, ProjectUserStatus pus) {
		pus.setProvince(entity.getProvince());
		pus.setCity(entity.getCity());
		pus.setTown(entity.getTown());
		pus.setSchool(entity.getSchool());
		
		Integer userType = UserTypeEnum.SCHOOL_ADMIN.getValue();
		if(entity.getSchool()!=0){
			userType = UserTypeEnum.MASTER.getValue();
		}else{
			userType = UserTypeEnum.TOWN_ADMIN.getValue();
			if(entity.getTown()==0){
				userType = UserTypeEnum.CITY_ADMIN.getValue();
			} 
			if(entity.getCity()==0){
				userType = UserTypeEnum.PROVINCE_ADMIN.getValue();
			}
			if(entity.getProvince()==0){
				userType = UserTypeEnum.SUP_ADMIN.getValue();
			}
		}
		pus.setUserType(userType);
		
	//	pus.setStatus(ProjectMenuEnum.NOTJOINED.getValue());
		pus.setPid(entity.getPid());;
	}

	
	@Override
	public Integer countedPid(Long pid) {
		return this.dao.countedPid(pid);
	}

	@Override
	public Integer countPpcts(Long pid, Integer province, Integer city,
			Integer town, Long school) {
		return this.dao.countPpcts(pid, province, city, town, school);
	}

	@Override
	public Integer numSelectPpcts(Long pid, Integer province, Integer city,
			Integer town, Long school) {
		return this.dao.numSelectPpcts(pid, province, city, town, school);
	}

	
	
	
}