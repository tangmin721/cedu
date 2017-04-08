package com.yanxiu.ce.core.score.service.impl;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.common.utils.DateUtils;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.core.score.dao.ScoreDao;
import com.yanxiu.ce.core.score.dto.CheckScoreDto;
import com.yanxiu.ce.core.score.dto.Tyear;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreMineDetail;
import com.yanxiu.ce.core.score.entity.ScoreQuery;
import com.yanxiu.ce.core.score.enums.ScoreStatusEnum;
import com.yanxiu.ce.core.score.enums.ScoreTypeEnum;
import com.yanxiu.ce.core.score.service.ScoreMineDetailService;
import com.yanxiu.ce.core.score.service.ScoreService;
import com.yanxiu.ce.core.train.entity.Project;
import com.yanxiu.ce.core.train.service.ProjectService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 学时申报管理
 *
 * @author tangmin
 * @date 2016-08-02 14:59:01
 */
@Service("scoreService")
public class ScoreServiceImpl extends BaseServiceImpl<Score, ScoreQuery> implements ScoreService {
    @Autowired
    private ScoreDao dao;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DictItemService dictItemService;
    
    @Autowired
    private ScoreMineDetailService scoreMineDetailService;

    @Override
    protected BaseDao<Score, ScoreQuery> dao() {
        return this.dao;
    }

    /**
     * 获取seq
     */
    @Override
    public Integer selectMaxSeq() {
        Integer selectMaxSeq = this.dao.selectMaxSeq();
        if (selectMaxSeq != null) {
            return selectMaxSeq;
        }
        return 0;
    }

    /**
     * 校验entity是否可修改（code是否存在）
     */
    @Override
    public Boolean checkNameExit(Score entity) {
        Long count = this.dao.selectCheckNameExit(entity.getName(), entity.getId());
        if (count > 0) {
            return false;
        }
        return true;
    }

    /**
     * 新增or修改
     */
    @Override
    @Transactional
    public String saveOrUpdate(Score entity) {
        Long tid = entity.getTid();
        Long pid = entity.getPid();
        if (tid != null) {
            Teacher teacher = teacherService.selectById(tid);
            if (teacher != null) {
                entity.setProvince(teacher.getProvince());
                entity.setCity(teacher.getCity());
                entity.setTown(teacher.getTown());
                entity.setSchool(teacher.getSchool());
                entity.setTname(teacher.getName());
                entity.setTno(teacher.getTno().toString());
            }

        }

        if (pid != null) {
            Project project = projectService.selectById(pid);
            if (project != null) {
                Long trainTypeId = project.getTrainType();
                DictItem trainType = dictItemService.selectById(trainTypeId);

                String name = trainType.getName() + "(" + project.getName() + ")";

                entity.setName(name);
                entity.setYear(project.getSchoolYear());
                entity.setPno(project.getPno());
                entity.setLevel(project.getTrainLevel());

                if (this.checkPidTidExit(entity)) {
                    throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"该项目您已经有学时记录，不能再申请！");
                }
            }

        }

        if (entity.getScoreType() == ScoreTypeEnum.XUELI.getValue()) {
            String name = "提升学历(" + dictItemService.selectById(entity.getDegree()).getName() + ")";
            entity.setName(name);
        }

        //校级待审
        entity.setStatus(ScoreStatusEnum.SCHOOL_CHECKING.getValue());

        //加入其它信息冗余字段
        String msg = "";
        if (entity.getId() == null) {
            this.insert(entity);
            msg = "添加成功！";
        } else {
            this.update(entity);
            msg = "编辑成功！";
        }
        return msg;
    }

    @Override
    @Transactional
	public String saveOrUpdate(Score entity, List<ScoreMineDetail> details) {
    	Long tid = entity.getTid();
        Long pid = entity.getPid();
        if (tid != null) {
            Teacher teacher = teacherService.selectById(tid);
            if (teacher != null) {
                entity.setProvince(teacher.getProvince());
                entity.setCity(teacher.getCity());
                entity.setTown(teacher.getTown());
                entity.setSchool(teacher.getSchool());
                entity.setTname(teacher.getName());
                entity.setTno(teacher.getTno().toString());
            }

        }

        if (pid != null) {
            Project project = projectService.selectById(pid);
            if (project != null) {
                Long trainTypeId = project.getTrainType();
                DictItem trainType = dictItemService.selectById(trainTypeId);

                String name = trainType.getName() + "(" + project.getName() + ")";

                entity.setName(name);
                entity.setYear(project.getSchoolYear());
                entity.setPno(project.getPno());
                entity.setLevel(project.getTrainLevel());

                if (this.checkPidTidExit(entity)) {
                    throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"该项目您已经有学时记录，不能再申请！");
                }
            }

        }

        if (entity.getScoreType() == ScoreTypeEnum.XUELI.getValue()) {
            String name = "提升学历(" + dictItemService.selectById(entity.getDegree()).getName() + ")";
            entity.setName(name);
        }

        //校级待审
        entity.setStatus(ScoreStatusEnum.SCHOOL_CHECKING.getValue());
        

        //加入其它信息冗余字段
        String msg = "";
        if (entity.getId() == null) {
            this.insert(entity);
            msg = "添加成功！";
        } else {
            this.update(entity);
            msg = "编辑成功！";
        }
        
        Long sid = entity.getId();
        for(ScoreMineDetail detail:details){
        	detail.setSid(sid);
        	if(detail.getId() == null){
        		scoreMineDetailService.insert(detail);
        	}else{
        		scoreMineDetailService.update(detail);
        	}
        }
        
        return msg;
	}
    
    @Override
    @Transactional
    public String saveStatus(Long id, String checkDesc, Integer status) {
        this.dao.updateStatus(id, checkDesc, status, new Date());
        return "操作成功！";
    }

    @Override
    @Transactional
    public Long checkMultiByIds(List<Long> ids, String checkDesc, Integer status) {
        return this.dao.checkMultiByIds(ids, checkDesc, new Date(), status);
    }

    @Override
    @Transactional
    public Long checkOneKey(String checkDesc, Integer status, Integer province,
                            Integer city, Integer town, Long school, Integer currentStatus) {
        return this.dao.checkOneKey(checkDesc, new Date(), status, province, city, town, school, currentStatus);
    }

    @Override
    public CheckScoreDto checkScore(Score entity) throws ParseException {
        Boolean result = true;
        String msg = "";

        //1、项目学时
        if (entity.getScoreType() == ScoreTypeEnum.XIANGMU.getValue()) {
            if (entity.getScore() > 40) {
                result = false;
                msg = "单个项目登记学时不得超过40学时。";
            } else {
                if(entity.getPid()!=null){
                    Project project = projectService.selectById(entity.getPid());
                    DictItem schoolYearDict = dictItemService.selectById(project.getSchoolYear());
                    String strDate = schoolYearDict.getName()+"-01-02";
                    Date theDate = DateUtils.SHORT_DATE_FORMAT.parse(strDate);

                    Integer hadScore = this.countUpdateScoreInTyear(theDate,entity.getScoreType(), entity.getId(), entity.getTid());
                    Integer totalScore = hadScore + entity.getScore();
                    if (totalScore > 100) {
                        result = false;
                        int tmpscore =  100-hadScore;
                        if(entity.getId()==null){
                            if(tmpscore>0) {
                                msg = "一个继续教育周期内【项目学时】累计登记不能超过【100】学时。<br>您已申请了【" + hadScore + "】学时。最多只能再申请【" + tmpscore + "】学时，请修改。";
                            }else {
                                msg = "一个继续教育周期内【项目学时】累计登记不能超过【100】学时。<br>您已申请了【" + hadScore + "】学时。本继续教育周期不能继续再申报学时了。";
                            }
                        }else {
                            if(tmpscore>0) {
                                msg = "一个继续教育周期内【项目学时】累计登记不能超过【100】学时。<br>您已申请了【" + hadScore + "】学时。本次修改您最多可以填【" + tmpscore + "】学时";
                            }else{
                                msg = "一个继续教育周期内【项目学时】累计登记不能超过【100】学时。<br>您已申请了【" + hadScore + "】学时。本继续教育周期不能继续再申报学时了。";
                            }
                        }

                    }
                }else{
                    result = false;
                    msg = "请先选择【项目】";
                }

            }
        }
        //2、学历提升
        if (entity.getScoreType() == ScoreTypeEnum.XUELI.getValue()) {
            if (entity.getScore() > 120) {
                result = false;
                msg = "一个继续教育周期内【学历提升】累计登记不能超过【120】学时。";
            }else{
                if(entity.getYear()!=null){
                    DictItem schoolYearDict = dictItemService.selectById(entity.getYear());
                    String strDate = schoolYearDict.getName()+"-01-02";
                    Date theDate = DateUtils.SHORT_DATE_FORMAT.parse(strDate);
                    Integer hadScore = this.countUpdateScoreInTyear(theDate,entity.getScoreType(), entity.getId(), entity.getTid());
                    Integer totalScore = hadScore + entity.getScore();
                    if (totalScore > 120) {
                        result = false;
                        int tmpscore =  120-hadScore;
                        if(entity.getId()==null) {
                            if(tmpscore>0){
                                msg = "一个继续教育周期内【学历提升】累计登记不能超过【120】学时。<br>您已申请了【" + hadScore + "】学时。最多只能再申请【"+tmpscore+"】学时，请修改。";
                            }else {
                                msg = "一个继续教育周期内【学历提升】累计登记不能超过【120】学时。<br>您已申请了【" + hadScore + "】学时。本继续教育周期不能继续再申报学时了。";
                            }

                        }else {
                            if(tmpscore>0) {
                                msg = "一个继续教育周期内【学历提升】累计登记不能超过【120】学时。<br>您已申请了【" + hadScore + "】学时。本次修改您最多可以填【" + tmpscore + "】学时";
                            }else {
                                msg = "一个继续教育周期内【学历提升】累计登记不能超过【120】学时。<br>您已申请了【" + hadScore + "】学时。本继续教育周期不能继续再申报学时了。";
                            }
                        }
                    }
                }else {
                    result = false;
                    msg = "请先填写【年度】";
                }

            }


        }

        CheckScoreDto dto = new CheckScoreDto();
        dto.setResult(result);
        dto.setMsg(msg);
        return dto;
    }


    /**
     * 计算一个老师 本教育周期内获取  某种 scoreType
     * 用于校验 新增  和 其他统计
     * @param scoreType
     * @param tid
     * @return
     */
    @Override
   public Integer countScoreInyear(Date date,Integer scoreType,Long tid) throws ParseException {
        Tyear tyearObj = Tyear.getTyearObj(date);
        Integer integer = this.dao.countScoreInTyar(scoreType, null, tid, tyearObj.getStartYear(), tyearObj.getEndYear());
        return integer;
   }

    /**
     * 计算一个老师 除了本条score申报记录  其他本教育周期内获取集中OR非集中培训的学时
     * 仅用于校验  修改申报记录
     * @param scoreType
     * @param id
     * @param tid
     * @return
     */
    @Override
    public Integer countUpdateScoreInTyear(Date date,Integer scoreType,Long id,Long tid) throws ParseException {
        Tyear tyearObj = Tyear.getTyearObj(date);
        Integer integer = this.dao.countScoreInTyar(scoreType, id, tid, tyearObj.getStartYear(), tyearObj.getEndYear());
        return integer;
    }

    /**
     * 学校管理员，县级管理员 修改 老师的学时
     * @param score
     * @return
     */
    @Override
    public String adminUpdate(Score score) {
        Integer integer = this.dao.adminUpdate(score.getId(), score.getVersion(), score.getScore(),score.getSchoolUpdateMemo(), score.getTownUpdateMemo());
        return "保存成功";
    }

    @Override
    public Boolean checkPidTidExit(Score entity) {
        Long count = this.dao.selectCheckPidTidExit(entity.getPid(), entity.getTid(),entity.getId());
        if(count>0){
            return true;
        }else {
            return false;
        }
    }

	@Override
	@Transactional
	public void deleteScore(Long id) {
		Score score = this.selectById(id);
		if(score.getScoreType()==ScoreTypeEnum.MINEYANXIU.getValue()){
			scoreMineDetailService.deleteBySid(id);
		}
		this.dao.deleteById(id);
	}

	

}