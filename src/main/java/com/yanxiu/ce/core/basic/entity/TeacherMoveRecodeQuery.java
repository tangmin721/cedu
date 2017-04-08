package com.yanxiu.ce.core.basic.entity;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.entity.BaseQuery;
import com.yanxiu.ce.system.entity.City;
import com.yanxiu.ce.system.entity.Province;
import com.yanxiu.ce.system.entity.Town;

import java.util.List;

/**
 * 教师调动管理
 * @author tangmin
 * @date 2016-09-27 16:50:41
 */
public class TeacherMoveRecodeQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private Boolean tidLike = true;
	
	private String moveStatus;
	private String sendDate;
	private Boolean sendDateLike = true;
	
	private String sendMemo;
	private Boolean sendMemoLike = true;
	
	private String takeDate;
	private Boolean takeDateLike = true;
	
	private String takeMemo;
	private Boolean takeMemoLike = true;

	private Integer province=0;
	private Integer city=0;
	private Integer town=0;
	private Long school=0l;

	List<Province> queryProvinces = Lists.newArrayList();
	List<City> queryCitys = Lists.newArrayList();
	List<Town> queryTowns = Lists.newArrayList();
	List<School> querySchools= Lists.newArrayList();

	private Integer tprovince=0;
	private Integer tcity=0;
	private Integer ttown=0;
	private Long tschool=0l;

	private List<Province> queryTprovinces = Lists.newArrayList();
	private List<City> queryTcitys = Lists.newArrayList();
	private List<Town> queryTtowns = Lists.newArrayList();
	private List<School> queryTschools= Lists.newArrayList();

	private String name;
	private Boolean nameLike = true;
	private String sex;
	private String tno;
	private Boolean tnoLike = true;
	private String idCard;
	private Boolean idCardLike = true;
	private String type;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Boolean getTidLike() {
		return tidLike;
	}

	public void setTidLike(Boolean tidLike) {
		this.tidLike = tidLike;
	}

	public String getMoveStatus() {
		return moveStatus;
	}

	public void setMoveStatus(String moveStatus) {
		this.moveStatus = moveStatus;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public Boolean getSendDateLike() {
		return sendDateLike;
	}

	public void setSendDateLike(Boolean sendDateLike) {
		this.sendDateLike = sendDateLike;
	}

	public String getSendMemo() {
		return sendMemo;
	}

	public void setSendMemo(String sendMemo) {
		this.sendMemo = sendMemo;
	}

	public Boolean getSendMemoLike() {
		return sendMemoLike;
	}

	public void setSendMemoLike(Boolean sendMemoLike) {
		this.sendMemoLike = sendMemoLike;
	}

	public String getTakeDate() {
		return takeDate;
	}

	public void setTakeDate(String takeDate) {
		this.takeDate = takeDate;
	}

	public Boolean getTakeDateLike() {
		return takeDateLike;
	}

	public void setTakeDateLike(Boolean takeDateLike) {
		this.takeDateLike = takeDateLike;
	}

	public String getTakeMemo() {
		return takeMemo;
	}

	public void setTakeMemo(String takeMemo) {
		this.takeMemo = takeMemo;
	}

	public Boolean getTakeMemoLike() {
		return takeMemoLike;
	}

	public void setTakeMemoLike(Boolean takeMemoLike) {
		this.takeMemoLike = takeMemoLike;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getTown() {
		return town;
	}

	public void setTown(Integer town) {
		this.town = town;
	}

	public Long getSchool() {
		return school;
	}

	public void setSchool(Long school) {
		this.school = school;
	}

	public Integer getTprovince() {
		return tprovince;
	}

	public void setTprovince(Integer tprovince) {
		this.tprovince = tprovince;
	}

	public Integer getTcity() {
		return tcity;
	}

	public void setTcity(Integer tcity) {
		this.tcity = tcity;
	}

	public Integer getTtown() {
		return ttown;
	}

	public void setTtown(Integer ttown) {
		this.ttown = ttown;
	}

	public Long getTschool() {
		return tschool;
	}

	public void setTschool(Long tschool) {
		this.tschool = tschool;
	}

	public List<Province> getQueryTprovinces() {
		return queryTprovinces;
	}

	public void setQueryTprovinces(List<Province> queryTprovinces) {
		this.queryTprovinces = queryTprovinces;
	}

	public List<City> getQueryTcitys() {
		return queryTcitys;
	}

	public void setQueryTcitys(List<City> queryTcitys) {
		this.queryTcitys = queryTcitys;
	}

	public List<Town> getQueryTtowns() {
		return queryTtowns;
	}

	public void setQueryTtowns(List<Town> queryTtowns) {
		this.queryTtowns = queryTtowns;
	}

	public List<School> getQueryTschools() {
		return queryTschools;
	}

	public void setQueryTschools(List<School> queryTschools) {
		this.queryTschools = queryTschools;
	}

	public List<Province> getQueryProvinces() {
		return queryProvinces;
	}

	public void setQueryProvinces(List<Province> queryProvinces) {
		this.queryProvinces = queryProvinces;
	}

	public List<City> getQueryCitys() {
		return queryCitys;
	}

	public void setQueryCitys(List<City> queryCitys) {
		this.queryCitys = queryCitys;
	}

	public List<Town> getQueryTowns() {
		return queryTowns;
	}

	public void setQueryTowns(List<Town> queryTowns) {
		this.queryTowns = queryTowns;
	}

	public List<School> getQuerySchools() {
		return querySchools;
	}

	public void setQuerySchools(List<School> querySchools) {
		this.querySchools = querySchools;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getNameLike() {
		return nameLike;
	}

	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}

	public Boolean getTnoLike() {
		return tnoLike;
	}

	public void setTnoLike(Boolean tnoLike) {
		this.tnoLike = tnoLike;
	}

	public Boolean getIdCardLike() {
		return idCardLike;
	}

	public void setIdCardLike(Boolean idCardLike) {
		this.idCardLike = idCardLike;
	}
}