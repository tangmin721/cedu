package com.yanxiu.ce.core.train;

import com.yanxiu.ce.core.train.entity.ProjectPerson;
import com.yanxiu.ce.core.train.service.ProjectPersonService;
import com.yanxiu.ce.test.junit.SpringJunitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

public class TestProjectPerson extends SpringJunitTest {
	
	@Autowired
	private ProjectPersonService service;
	
	@Test
	@Rollback(value = false)
	public void testInsert() {
		for(long i=12;i<133;i++){
			ProjectPerson projectPerson = new ProjectPerson();
			projectPerson.setTid(i);
			projectPerson.setPid(13l);
			if(i%3==1){
				projectPerson.setPass(1);
			}else if(i%3==0){
				projectPerson.setPass(0);
			}
			projectPerson.setOid(3l);
			service.insert(projectPerson);
		}
	}



}
