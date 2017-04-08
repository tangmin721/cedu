package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherMovePojo;
import com.yanxiu.ce.core.basic.entity.TeacherMovePojoQuery;
import org.apache.poi.ss.formula.functions.T;

/**
 * 教师调动管理
 * @author tangmin
 * @date 2016-09-27 12:18:18
 */
public interface TeacherMovePojoService extends BaseService<TeacherMovePojo, TeacherMovePojoQuery>{
    /**
     * 更新教师调动状态
     * @param moveStatus
     * @param tid
     * @return
     */
    Long updateMoveStatus(Integer moveStatus,Long tid);


    /**
     * 更新省市县校
     * @param province
     * @param city
     * @param town
     * @param school
     * @param tid
     * @return
     */
    Long updatePcts(Integer province,Integer city,Integer town,Long school,Long tid);


}
