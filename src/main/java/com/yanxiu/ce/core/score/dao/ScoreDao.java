package com.yanxiu.ce.core.score.dao;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreQuery;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 学时申报管理
 *
 * @author tangmin
 * @date 2016-08-02 14:59:01
 */
@MybatisDao
public interface ScoreDao extends BaseDao<Score, ScoreQuery> {

    /**
     * 获取seq
     *
     * @return Integer
     */
    Integer selectMaxSeq();

    /**
     * 查找name为@name，且id不为@id的记录条数
     *
     * @param name
     * @param id
     * @return
     */
    public Long selectCheckNameExit(@Param("name") String name, @Param("id") Long id);

    /**
     * 更新状态
     *
     * @param id
     * @param checkDesc
     * @param status
     * @return
     */
    Integer updateStatus(@Param("id") Long id, @Param("checkDesc") String checkDesc, @Param("status") Integer status, @Param("checkDate") Date checkDate);

    /**
     * 管理员修改学分
     * @param id
     * @param schoolUpdateMemo
     * @param townUpdateMemo
     * @return
     */
    Integer adminUpdate(@Param("id") Long id, @Param("version") Integer version,@Param("score") Integer score, @Param("schoolUpdateMemo") String schoolUpdateMemo,@Param("townUpdateMemo") String townUpdateMemo);


    /**
     * 批量审核
     *
     * @param ids
     * @return
     */
    Long checkMultiByIds(@Param("ids") List<Long> ids, @Param("checkDesc") String checkDesc, @Param("checkDate") Date checkDate, @Param("status") Integer status);


    /**
     * 一键审核
     *
     * @param checkDesc
     * @param checkDate
     * @param status
     * @param province
     * @param city
     * @param town
     * @param school
     * @param currentStatus
     * @return
     */
    Long checkOneKey(@Param("checkDesc") String checkDesc, @Param("checkDate") Date checkDate, @Param("status") Integer status,
                     @Param("province") Integer province,
                     @Param("city") Integer city,
                     @Param("town") Integer town,
                     @Param("school") Long school,
                     @Param("currentStatus") Integer currentStatus);


    /**
     * 计算申报的学时
     * @param type
     * @param id   score实体的id，用于判断修改本条记录时的校验
     * @param tid
     * @param startDate
     * @param endDate
     * @return
     */
    Integer countScoreInTyar(@Param("scoreType") Integer scoreType, @Param("id") Long id, @Param("tid") Long tid, @Param("startDate") String startDate, @Param("endDate") String endDate);


    /**
     * 获取根据pid，tid的记录条数
     * @param pid
     * @param tid
     * @return
     */
    Long selectCheckPidTidExit(@Param("pid") Long pid, @Param("tid") Long tid, @Param("id") Long id);
}
