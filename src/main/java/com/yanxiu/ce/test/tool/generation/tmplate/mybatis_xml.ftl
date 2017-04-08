<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${CONFIG.packagePath}.dao.${entity.className}Dao">
	
	<sql id="table"> ${entity.tableName} </sql>
	<sql id="columns"> <#list entity.allfieldList as e>${e.columnName}<#if e_has_next>,</#if></#list> </sql>
	
	<resultMap id="${entity.firstLowName}" type="${entity.className}">
<#list entity.allfieldList as e>		<result property="${e.fieldName}" column="${e.columnName}" />
		</#list>
	</resultMap>
	
	<!--单条插入-->
	<insert id="insert" parameterType="${entity.className}" keyProperty="id" useGeneratedKeys="true">
		insert into
		<include refid="table" />
		<trim prefix="(" suffix=")" suffixOverrides=",">
			version,createtime,deleted,
<#list entity.myfieldList as e>			<if test="${e.fieldName} != null">${e.columnName}<#if e_has_next>,</#if></if>
			</#list>
		</trim>
		values
		<trim prefix="(" suffix=")" suffixOverrides=",">
			0,${r"#{"}${"createTime"}${r"}"},${r"#{"}${"deleted"}${r"}"},
<#list entity.myfieldList as e>			<if test="${e.fieldName} != null">${r"#{"}${e.fieldName}${r"}"}<#if e_has_next>,</#if></if>
			</#list>			
		</trim>
	</insert>
	
	<!--批量插入-->
	<insert id="insertBatch" parameterType="java.util.List" useGeneratedKeys="true">
		insert into
		<include refid="table" />
		(<include refid="columns" />)
		values
		<foreach collection="list" item="item" index="index" separator=",">
			(null,0,${r"#{"}${"item.createTime"}${r"}"},${r"#{"}${"item.deleted"}${r"}"},<#list entity.myfieldList as e>${r"#{"}item.${e.fieldName}${r"}"}<#if e_has_next>,</#if></#list>)
		</foreach>
	</insert>
	
	<!-- 根据主键查询 -->
	<select id="selectById" parameterType="long" resultMap="${entity.firstLowName}">
		select <include refid="columns" /> from <include refid="table" />
		where
		id=${r"#{"}${"id"}${r"}"}
	</select>
	
	<!-- 根据主键批量查询 -->
	<select id="selectByIds" parameterType="java.util.List" resultMap="${entity.firstLowName}" >
		select <include refid="columns" /> from <include refid="table" />
		where id in
		<foreach collection="list" separator="," item="id" open="(" close=")">
			${r"#{"}${"id"}${r"}"}
		</foreach>
	</select>
	
	
	<!-- 单条更新:version+1 实现乐观锁 -->
	<update id="update" parameterType="${entity.className}">
		update
		<include refid="table" />
		<set>
			version = ${r"#{"}${"version"}${r"}"}+1 ,
			deleted = ${r"#{"}${"deleted"}${r"}"},
<#list entity.myfieldList as e>			${e.columnName} = ${r"#{"}${e.fieldName}${r"}"}<#if e_has_next>,</#if>
			</#list>			
		</set>
		<where>
			id = ${r"#{"}${"id"}${r"}"} and version = ${r"#{"}${"version"}${r"}"}
		</where>
	</update>
	
	<!-- 根据id彻底删除 -->
	<delete id="deleteById" parameterType="long">
		delete from <include refid="table" />
		where
		id=${r"#{"}${"id"}${r"}"}
	</delete>
	
	<!-- 根据主键批量删除 -->
	<delete id="deleteByIds" parameterType="java.util.List">
		delete from <include refid="table" />
		where
		id in
		<foreach collection="list" separator="," item="id" open="(" close=")">
			${r"#{"}${"id"}${r"}"}
		</foreach>
	</delete>
	
	<!--片段list字段查询器-->
	<sql id="selectorFields">
		select
		<if test="fields != null">
			${r"${"}${"fields"}${r"}"}
		</if>
		<if test="fields == null">
			<include refid="columns" />
		</if>
		from  <include refid="table" />
	</sql>
	
	<!--片段list条件判断-->
	<sql id="selectorWhere">
		<where>
			<if test="deleted != null and deleted !=''"> and deleted=${r"#{"}${"deleted"}${r"}"}</if>
<#list entity.myfieldList as e>			<if test="${e.fieldName} != null and ${e.fieldName} !=''"> 
				<if test="${e.fieldName}Like == false"> and ${e.columnName}=${r"#{"}${e.fieldName}${r"}"}</if>
				<if test="${e.fieldName}Like == true"> and ${e.columnName} like  CONCAT('%',${r"#{"}${e.fieldName}${r"}"},'%')</if>
			</if>
			</#list>					
			<!--<if test="startTime != null"> and birthday <![CDATA[   >=  ]]>${r"#{"}${"startTime"}${r"}"} </if>
			<if test="endTime != null"> and  birthday <![CDATA[   <  ]]> ${r"#{"}${"endTime"}${r"}"}</if>
			-->
		</where>
	</sql>
	
	<!--片段list排序-->
	<sql id="selectorOrder">
		<if test="orderFields != null and orderFields.size >0">
			order by
			<foreach collection="orderFields" separator="," item="orderField">
				${r"${"}${"orderField.fieldName"}${r"}"} ${r"${"}${"orderField.order"}${r"}"}
			</foreach>
		</if>
	</sql>
	
	<!--片段list分页-->
	<sql id="selectorLimit">
		<if test="startRow != null">
			limit  ${r"#{"}${"startRow"}${r"}"},${r"#{"}${"pageSize"}${r"}"}
		</if>
	</sql>
	
	<!--查询所有记录-->
	<select id="selectList" parameterType="${entity.className}Query" resultMap="${entity.firstLowName}">
		<include refid="selectorFields" />
		<include refid="selectorWhere" />
		<include refid="selectorOrder" />
	</select>
	
	<!-- 分页 -->
	<select id="selectListPage" parameterType="${entity.className}Query" resultMap="${entity.firstLowName}">
		<include refid="selectorFields" />
		<include refid="selectorWhere" />
		<include refid="selectorOrder" />
		<include refid="selectorLimit" />
	</select>
	
	<!--总条数-->
	<select id="selectListTotal" parameterType="${entity.className}Query" resultType="long">
		select count(1) from <include refid="table" />
		<include refid="selectorWhere" />
	</select>
	
	<!--///////////////////////////////自定义/////////////////////////-->
	<!--获取最大排序号-->
	<select id="selectMaxSeq" resultType="int">
		select max(seq) from <include refid="table" />
	</select>
	
	<!--校验Name是否存在-->
	<select id="selectCheckNameExit" resultType="long">
		select count(1) from <include refid="table" />
		<where>
			<if test="name != null and  name !=''"> and name=${r"#{"}${"name"}${r"}"}</if>
			<if test="id != null and  id !=''"> and id!=${r"#{"}${"id"}${r"}"}</if>
		</where>
	</select>
</mapper>