package com.yanxiu.ce.core.basic.dto;

/**
 * 保存教师信息的时候，为了返回更多信息
 * @author tangm
 *
 */
public class TeacherSaveDto {
	/**
	 * 教师的id
	 */
	private Long tid;
	
	/**
	 * version
	 */
	private Integer version;
	
	/**
	 * 消息，是添加，还是修改
	 */
	private String msg;
	
	/**
	 * 标识码
	 */
	private String tno;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}
	

}
