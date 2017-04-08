package com.yanxiu.ce.core.basic.dao;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.basic.entity.TeacherMovePojo;
import com.yanxiu.ce.core.basic.entity.TeacherMovePojoQuery;
import org.apache.ibatis.annotations.Param;

/**
 * 教师调动管理
 * @author tangmin
 * @date 2016-09-27 12:18:18
 */
@MybatisDao
public interface TeacherMovePojoDao extends BaseDao<TeacherMovePojo, TeacherMovePojoQuery>{

    Long updateMoveStatus(@Param("moveStatus")Integer moveStatus, @Param("tid")Long tid);

    Long updatePcts(@Param("province")Integer province,@Param("city")Integer city,@Param("town")Integer town,
                    @Param("school")Long school,@Param("tid")Long tid);
}
