package com.yanxiu.ce.core.train;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.train.dao.ProjectRosterDetailDao;
import com.yanxiu.ce.core.train.entity.ProjectRoster;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetail;
import com.yanxiu.ce.core.train.entity.ProjectRosterDetailQuery;
import com.yanxiu.ce.core.train.entity.ProjectRosterQuery;
import com.yanxiu.ce.core.train.service.ProjectRosterService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

public class TestProjectRoster extends SpringJunitTest {
	
	@Autowired
	private ProjectRosterService service;
	
	@Autowired
	private ProjectRosterDetailDao dao;
	
	@Test
	public void testInsert() {
		ProjectRosterQuery query = new ProjectRosterQuery();
		query.setPid("6");
		List<Long> statusIds = Lists.newArrayList(1l,2l,3l,4l);
		query.setStatusIds(statusIds);
		Pagination<ProjectRoster> page = service.selectListPagination(query);
		System.out.println(page.getList());
	}

	@Test
	public void dao() {
		ProjectRosterDetailQuery query = new ProjectRosterDetailQuery();
		query.setPageSize(3l);
		query.setPid("6");
		 List<ProjectRosterDetail> selectTrainTeachersPage = dao.selectTrainTeachersPage(query);
		 System.out.println(selectTrainTeachersPage);
	}

}
