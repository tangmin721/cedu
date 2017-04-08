package com.yanxiu.ce.system.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 菜单管理
 * @author tangmin
 * @table sys_menu
 * @date 2016年2月26日
 */
public class Menu extends BaseEntity {
	private static final long serialVersionUID = -413552517814282978L;
	
	/**
	 * 父节点id
	 */
	private Long pid;
	
//	/**
//	 * 后代元素
//	 */
//	private List<Menu> children = Lists.newArrayList();
	
	/**
	 * 模块名称
	 */
	@NotBlank(message="模块名称不能为空",groups={Insert.class,Update.class})
	@Size(max = 50,groups={Insert.class,Update.class})
	private String name;
	
	/**
	 * 对应模块的类名
	 */
	@Size(max = 50,groups={Insert.class,Update.class})
	private String modelClzName;
	
	/**
	 * 图标名称
	 */
	@Size(max = 50,groups={Insert.class,Update.class})
	private String faicon;
	
	/**
	 * 层级1横向的,2 left头 3,4-5..ztree,自动计算
	 */
	private Integer level;
	
	/**
	 * 导航TAB的id
	 */
	@Size(max = 50,groups={Insert.class,Update.class})
	private String tabid;
	
	/**
	 * url
	 */
	@Size(max = 100,groups={Insert.class,Update.class})
	private String url;
	
	/**
	 * 是否是最下一级（如果是最下一级，则有authrizer ）
	 */
	private Boolean theLast;
	
	/**
	 * 标志符，用于授权名称（类似module:save）,为了性能，以“|”分割模块功能
	 */
	@Size(max = 200,groups={Insert.class,Update.class})
	private String authrizer;
	
	/**
	 * 排序序号
	 */
	private Integer seq;
	
	/**
	 * 备注
	 */
	@Size(max = 200,groups={Insert.class,Update.class})
	private String memo;
	
	/**
	 * 激活状态:默认未激活 0 未激活，1已激活
	 */
	private Boolean active=false;
	
	/**
	 * 是否在菜单导航中显示菜单:默认不显示 ， 0 不显示，1显示
	 */
	private Boolean shower = false;
	
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

//	public List<Menu> getChildren() {
//		return children;
//	}
//
//	public void setChildren(List<Menu> children) {
//		this.children = children;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModelClzName() {
		return modelClzName;
	}

	public void setModelClzName(String modelClzName) {
		this.modelClzName = modelClzName;
	}

	public String getFaicon() {
		return faicon;
	}

	public void setFaicon(String faicon) {
		this.faicon = faicon;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getTabid() {
		return tabid;
	}

	public void setTabid(String tabid) {
		this.tabid = tabid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Boolean getTheLast() {
		return theLast;
	}

	public void setTheLast(Boolean theLast) {
		this.theLast = theLast;
	}

	public String getAuthrizer() {
		return authrizer;
	}

	public void setAuthrizer(String authrizer) {
		this.authrizer = authrizer;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Boolean getShower() {
		return shower;
	}

	public void setShower(Boolean shower) {
		this.shower = shower;
	}
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
