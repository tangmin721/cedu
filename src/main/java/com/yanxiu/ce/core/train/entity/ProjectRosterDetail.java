package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;

/**
 * 报名名单的详单
 * @author tangmin
 * @date 2016年4月20日
 */
public class ProjectRosterDetail extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 名单的id
	 */
	private Long rosterId;
	
	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 教师名称
	 */
	@Transient
	private String name;
	
	/**
	 * 教师编号
	 */
	@Transient
	private String tno;
	
	/**
	 * 学分获取状态
	 */
	@Transient
	private Integer status;

	public Long getRosterId() {
		return rosterId;
	}

	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ProjectRosterDetail [rosterId=" + rosterId + ", tid=" + tid
				+ ", name=" + name + ", tno=" + tno + ", getId()=" + getId()
				+ "]";
	}

	
	

}
