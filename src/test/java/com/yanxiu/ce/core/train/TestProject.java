package com.yanxiu.ce.core.train;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.yanxiu.ce.core.train.dao.ProjectDao;
import com.yanxiu.ce.core.train.entity.Project;
import com.yanxiu.ce.core.train.entity.ProjectQuery;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetail;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetailQuery;
import com.yanxiu.ce.core.train.service.ProjectService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

public class TestProject extends SpringJunitTest {
	
	@Autowired
	private ProjectDao dao;
	

	@Test
	public void getJoinPids() {
		List<Long> notJoinedPids = dao.getJoinPids(420000, 420100, 0, 0l, 2, "hubei_admin",0);
		System.out.println(notJoinedPids);
	}

	
	@Test
	public void query() {
		ProjectQuery query = new ProjectQuery();
		List<Long> joinPids = Lists.newArrayList();
		query.setSelectJoinFlag(true);
		query.getJoinPids().addAll(joinPids);
		List<Project> selectListPage = dao.selectListPage(query );
		System.out.println("size::::::::::"+selectListPage.size());
	}
}
