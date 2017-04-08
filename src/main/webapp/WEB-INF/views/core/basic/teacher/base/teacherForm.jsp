<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">

<c:if test="${sessionScope.currentUser.type ==5}">
$('#f_teacher_area_td').css('display','none'); 
</c:if>

$("#f_teacher_edu_stage").change(function(){
    var stage = $("#f_teacher_edu_stage option:selected").val();
    if(stage){
   	 //联动学科下拉框
   	 $.ajax({ 
            type: "POST", 
            url: "${ctx}/core/basic/conf/course/courses/"+stage,
            dataType : "json",
            success: function(data) {
                var str = "";
                if(data){
                    $(data).each(function(n){
                        str += "<option value='"+this.id+"'>" + this.name+"</option>";
                    });
                }
                $("#f_teacher_edu_course").html("<option value=''>--请选择--</option>");
                if(str!=""){
                    $("#f_teacher_edu_course").append(str);  
                }
                $('#f_teacher_edu_course').selectpicker('refresh');
            },
            error :  function(){
                $(this).alertmsg('error', '系统错误！');
            }
        }); 
   	 
   	//联动年级下拉框
   	 $.ajax({ 
            type: "POST", 
            url: "${ctx}/core/basic/conf/grade/grades/"+stage,
            dataType : "json",
            success: function(data) {
                var str = "";
                if(data){
                    $(data).each(function(n){
                        str += "<option value='"+this.id+"'>" + this.name+"</option>";
                    });
                }
                $("#f_teacher_edu_grade").html("<option value=''>--请选择--</option>");
                if(str!=""){
                    $("#f_teacher_edu_grade").append(str);  
                }
                $('#f_teacher_edu_grade').selectpicker('refresh');
            },
            error :  function(){
           	 $(this).alertmsg('error', '系统错误！');
            }
        }); 
    }else{
   	 $("#f_teacher_edu_course").html("<option value=''>--请选择--</option>");
   	 $('#f_teacher_edu_course').selectpicker('refresh');
   	 
   	 $("#f_teacher_edu_grade").html("<option value=''>--请选择--</option>");
   	 $('#f_teacher_edu_grade').selectpicker('refresh');
    }
    
    
   });

//加载婚姻状况字典树
function f_teacher_marry_rj() {
    return ${marrys}
}

//加载22_1+*教职工来源
function f_teacher_workerFrom1_rj() {
    return ${workerFrom1s}
}

//加载22_1+*教职工来源
function f_teacher_workerFrom2_rj() {
    return ${workerFrom2s}
}

function f_teacher_workerType1_rj() {
    return ${workerType1s}
}

function f_teacher_workerType2_rj() {
    return ${workerType2s}
}

function f_teacher_workerType3_rj() {
    return ${workerType3s}
}

function f_teacher_workerType4_rj() {
    return ${workerType4s}
}

function f_teacher_isJoinBase_rj() {
    return ${isJoinBases}
}



function checkuploadTchImg(obj){
    var file=$(obj).val();
    var ext = file.substr(file.lastIndexOf("."));
    if(ext!='.jpg'){
        $(this).alertmsg('warn', "只允许后缀为.jpg文件,请重新选择文件！");
        $(obj).val('');
        return false;
    }else{
        if(obj.files){
            var size=obj.fileSize || obj.files[0].fileSize || obj.files[0].size;
            if(size>61440){
                $(this).alertmsg('warn','文件最大为60K！');
                $('#tch_file').val('');
                return false;
            }
        }
    }
    return true;
}

//上传照片
function uploadTchImg(obj) {
    if (checkuploadTchImg(obj)) {
        var options = {
            url : '${ctx}/system/upload/upload.do',
            dataType : 'json',
            success : function (data) {
                if(data.statusCode==300){
                    $(this).alertmsg('error', data.message);
                }else {
                    console.log(data.data.downloadUrl);
                    $('#tch_img').attr('src',data.data.downloadUrl);
                    $('#tch_path').val(data.data.fileId);
                }
            }
        }
        $('#f_tw_base_form').ajaxSubmit(options);
    }
}

function changeTfs(tfs_type){
	if(tfs_type==0){
		$('#f_teacher_workerFrom1_td').show();
		$('#f_teacher_workerFrom2_td').hide();
		$('#f_teacher_workerFrom1_ztree').attr("data-rule", "required");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerFrom2_ztree", "");
		
		$('#f_teacher_workerType1_td').show();
		$('#f_teacher_workerType2_td').hide();
		$('#f_teacher_workerType3_td').hide();
		$('#f_teacher_workerType4_td').hide();
		$('#f_teacher_workerType1_ztree').attr("data-rule", "required");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerType2_ztree", "");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerType3_ztree", "");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerType4_ztree", "");
		
		
		//是否受过特教专业培养培训
		$('#f_teacher_isTjpx_td').show();
		//是否有特殊教育从业证书
		$('#f_teacher_isTszs_td').show();
		$('#f_teacher_isTjpx').attr("data-rule", "required");
		$('#f_teacher_isTszs').attr("data-rule", "required");
		
		//是否全日制师范类专业毕业
		$('#f_teacher_isQrz_td').show();
		$('#f_teacher_isQrz').attr("data-rule", "required");
		
		//是否“双师型”教师
		$('#f_teacher_isDoubleTch_td').hide();
		$('#f_tw_base_form').validator("setField", "isDoubleTch", "");
		
		//是否具备职业技能等级证书
		$('#f_teacher_isProfessCard_td').hide();
		$('#f_tw_base_form').validator("setField", "isProfessCard", "");
		
		//企业工作(实践)时长
		$('#f_teacher_workDateTimer_td').hide();
		$('#f_tw_base_form').validator("setField", "workDateTimer", "");
		
		//是否属于免费（公费）师范生
		$('#f_teacher_isMian_td').show();
		//是否参加基层服务项目
		$('#f_teacher_isJoinBase_ztree_td').show();
		$('#f_teacher_isMian').attr("data-rule", "required");
		$('#f_teacher_isJoinBase_ztree').attr("data-rule", "required");
		
		//参加基层服务项目起始年月
		$('#f_teacher_joinBaseStart_td').show();
		//参加基层服务项目结束年月
		$('#f_teacher_joinBaseEnd_td').show();
		
		//是否县级及以上骨干教师
		$('#f_teacher_isTownUpBone_td').show();
		//是否心理健康教育教师
		$('#f_teacher_isHealth_td').show();
		$('#f_teacher_isTownUpBone').attr("data-rule", "required");
		$('#f_teacher_isHealth').attr("data-rule", "required");
		
		//是否全日制学前教育专业毕业
		$('#f_teacher_isPreTch_td').hide();
		//是否受过学前教育专业培养培训
		$('#f_teacher_isPreTrain_td').hide();
		$('#f_tw_base_form').validator("setField", "isPreTch", "");
		$('#f_tw_base_form').validator("setField", "isPreTrain", "");
		
		//进本校年月
		$("#teacher_join_school").show();
		$("#teacher_join_school_kind").hide();
		
		//从事特教起始年月
		$("#f_teacher_specStartDate_td").hide();
		$("#f_teacher_specStartDate").attr("disabled", true);
		
		//籍贯
		$("#f_teacher_nativer_span").hide();
		$('#f_tw_base_form').validator("setField", "nativer", "");
		$("#f_teacher_nativer").trigger('validate');
		
		//健康状况
		$("#f_teacher_health_span").hide();
		$('#f_tw_base_form').validator("setField", "health", "");
		$("#f_teacher_health").trigger('validate');
		
		//信息技术应用能力
		$("#f_teacher_computerAbility_span").show();
		$("#f_teacher_computerAbility").attr("data-rule", "required");
	}else if(tfs_type == 1){
		$('#f_teacher_workerFrom1_td').show();
		$('#f_teacher_workerFrom2_td').hide();
		$('#f_teacher_workerFrom1_ztree').attr("data-rule", "required");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerFrom2_ztree", "");
		
		$('#f_teacher_workerType1_td').hide();
		$('#f_teacher_workerType2_td').show();
		$('#f_teacher_workerType3_td').hide();
		$('#f_teacher_workerType4_td').hide();
		$('#f_teacher_workerType2_ztree').attr("data-rule", "required");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerType1_ztree", "");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerType3_ztree", "");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerType4_ztree", "");
		
		//是否受过特教专业培养培训
		$('#f_teacher_isTjpx_td').hide();
		//是否有特殊教育从业证书
		$('#f_teacher_isTszs_td').hide();
		$('#f_tw_base_form').validator("setField", "isTjpx", "");
		$('#f_tw_base_form').validator("setField", "isTszs", "");
		
		//是否全日制师范类专业毕业
		$('#f_teacher_isQrz_td').hide();
		$('#f_tw_base_form').validator("setField", "isQrz", "");
		
		//是否“双师型”教师
		$('#f_teacher_isDoubleTch_td').show();
		$('#f_teacher_isDoubleTch').attr("data-rule", "required");
		
		//是否具备职业技能等级证书
		$('#f_teacher_isProfessCard_td').show();
		$('#f_teacher_isProfessCard').attr("data-rule", "required");
		
		//企业工作(实践)时长
		$('#f_teacher_workDateTimer_td').show();
		$('#f_teacher_workDateTimer').attr("data-rule", "required");
		
		//是否属于免费（公费）师范生
		$('#f_teacher_isMian_td').hide();
		//是否参加基层服务项目
		$('#f_teacher_isJoinBase_ztree_td').hide();
		$('#f_tw_base_form').validator("setField", "isMian", "");
		$('#f_tw_base_form').validator("setField", "f_teacher_isJoinBase_ztree", "");
		
		//参加基层服务项目起始年月
		$('#f_teacher_joinBaseStart_td').hide();
		//参加基层服务项目结束年月
		$('#f_teacher_joinBaseEnd_td').hide();
		
		//是否县级及以上骨干教师
		$('#f_teacher_isTownUpBone_td').hide();
		//是否心理健康教育教师
		$('#f_teacher_isHealth_td').hide();
		$('#f_tw_base_form').validator("setField", "isTownUpBone", "");
		$('#f_tw_base_form').validator("setField", "isHealth", "");
		
		//是否全日制学前教育专业毕业
		$('#f_teacher_isPreTch_td').hide();
		//是否受过学前教育专业培养培训
		$('#f_teacher_isPreTrain_td').hide();
		$('#f_tw_base_form').validator("setField", "isPreTch", "");
		$('#f_tw_base_form').validator("setField", "isPreTrain", "");
		
		//进本校年月
		$("#teacher_join_school").show();
		$("#teacher_join_school_kind").hide();
		
		//从事特教起始年月
		$("#f_teacher_specStartDate_td").hide();
		$("#f_teacher_specStartDate").attr("disabled", true);
		
		//籍贯
		$("#f_teacher_nativer_span").show();
		$('#f_teacher_nativer').attr("data-rule", "required");
		
		//健康状况
		$("#f_teacher_health_span").show();
		$('#f_teacher_health').attr("data-rule", "required");
		
		//信息技术应用能力
		$("#f_teacher_computerAbility_span").hide();
		$('#f_tw_base_form').validator("setField", "computerAbility", "");
		$("#f_teacher_computerAbility").trigger('validate');
	}else if(tfs_type == 2){
		$('#f_teacher_workerFrom1_td').show();
		$('#f_teacher_workerFrom2_td').hide();
		$('#f_teacher_workerFrom1_ztree').attr("data-rule", "required");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerFrom2_ztree", "");
		
		$('#f_teacher_workerType1_td').hide();
		$('#f_teacher_workerType2_td').hide();
		$('#f_teacher_workerType3_td').show();
		$('#f_teacher_workerType4_td').hide();
		$('#f_teacher_workerType3_ztree').attr("data-rule", "required");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerType1_ztree", "");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerType2_ztree", "");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerType4_ztree", "");
		
		//是否受过特教专业培养培训
		$('#f_teacher_isTjpx_td').show();
		//是否有特殊教育从业证书
		$('#f_teacher_isTszs_td').show();
		$('#f_teacher_isTjpx').attr("data-rule", "required");
		$('#f_teacher_isTszs').attr("data-rule", "required");
		
		//是否全日制师范类专业毕业
		$('#f_teacher_isQrz_td').show();
		$('#f_teacher_isQrz').attr("data-rule", "required");
		
		//是否“双师型”教师
		$('#f_teacher_isDoubleTch_td').hide();
		$('#f_tw_base_form').validator("setField", "isDoubleTch", "");
		
		//是否具备职业技能等级证书
		$('#f_teacher_isProfessCard_td').hide();
		$('#f_tw_base_form').validator("setField", "isProfessCard", "");
		
		//企业工作(实践)时长
		$('#f_teacher_workDateTimer_td').hide();
		$('#f_tw_base_form').validator("setField", "workDateTimer", "");
		
		//是否属于免费（公费）师范生
		$('#f_teacher_isMian_td').show();
		//是否参加基层服务项目
		$('#f_teacher_isJoinBase_ztree_td').show();
		$('#f_teacher_isMian').attr("data-rule", "required");
		$('#f_teacher_isJoinBase_ztree').attr("data-rule", "required");
		
		//参加基层服务项目起始年月
		$('#f_teacher_joinBaseStart_td').show();
		//参加基层服务项目结束年月
		$('#f_teacher_joinBaseEnd_td').show();
		
		//是否县级及以上骨干教师
		$('#f_teacher_isTownUpBone_td').show();
		//是否心理健康教育教师
		$('#f_teacher_isHealth_td').show();
		$('#f_teacher_isTownUpBone').attr("data-rule", "required");
		$('#f_teacher_isHealth').attr("data-rule", "required");
		
		//是否全日制学前教育专业毕业
		$('#f_teacher_isPreTch_td').hide();
		//是否受过学前教育专业培养培训
		$('#f_teacher_isPreTrain_td').hide();
		$('#f_tw_base_form').validator("setField", "isPreTch", "");
		$('#f_tw_base_form').validator("setField", "isPreTrain", "");
		
		//进本校年月
		$("#teacher_join_school").show();
		$("#teacher_join_school_kind").hide();
		
		//从事特教起始年月
		$("#f_teacher_specStartDate_td").show();
		$("#f_teacher_specStartDate").removeAttr("disabled");
		
		//籍贯
		$("#f_teacher_nativer_span").show();
		$('#f_teacher_nativer').attr("data-rule", "required");
		
		//健康状况
		$("#f_teacher_health_span").show();
		$('#f_teacher_health').attr("data-rule", "required");
		
		//信息技术应用能力
		$("#f_teacher_computerAbility_span").show();
		$("#f_teacher_computerAbility").attr("data-rule", "required");
	}else if(tfs_type == 3){
		$('#f_teacher_workerFrom1_td').hide();
		$('#f_teacher_workerFrom2_td').show();
		$('#f_teacher_workerFrom2_ztree').attr("data-rule", "required");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerFrom1_ztree", "");
		
		$('#f_teacher_workerType1_td').hide();
		$('#f_teacher_workerType2_td').hide();
		$('#f_teacher_workerType3_td').hide();
		$('#f_teacher_workerType4_td').show();
		$('#f_teacher_workerType4_ztree').attr("data-rule", "required");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerType1_ztree", "");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerType2_ztree", "");
		$('#f_tw_base_form').validator("setField", "f_teacher_workerType3_ztree", "");
		
		//是否受过特教专业培养培训
		$('#f_teacher_isTjpx_td').hide();
		//是否有特殊教育从业证书
		$('#f_teacher_isTszs_td').hide();
		$('#f_tw_base_form').validator("setField", "isTjpx", "");
		$('#f_tw_base_form').validator("setField", "isTszs", "");
		
		//是否全日制师范类专业毕业
		$('#f_teacher_isQrz_td').show();
		$('#f_teacher_isQrz').attr("data-rule", "required");
		
		//是否“双师型”教师
		$('#f_teacher_isDoubleTch_td').hide();
		$('#f_tw_base_form').validator("setField", "isDoubleTch", "");
		
		//是否具备职业技能等级证书
		$('#f_teacher_isProfessCard_td').hide();
		$('#f_tw_base_form').validator("setField", "isProfessCard", "");
		
		//企业工作(实践)时长
		$('#f_teacher_workDateTimer_td').hide();
		$('#f_tw_base_form').validator("setField", "workDateTimer", "");
		
		//是否属于免费（公费）师范生
		$('#f_teacher_isMian_td').show();
		//是否参加基层服务项目
		$('#f_teacher_isJoinBase_ztree_td').show();
		$('#f_teacher_isMian').attr("data-rule", "required");
		$('#f_teacher_isJoinBase_ztree').attr("data-rule", "required");
		
		//参加基层服务项目起始年月
		$('#f_teacher_joinBaseStart_td').show();
		//参加基层服务项目结束年月
		$('#f_teacher_joinBaseEnd_td').show();
		
		//是否县级及以上骨干教师
		$('#f_teacher_isTownUpBone_td').show();
		//是否心理健康教育教师
		$('#f_teacher_isHealth_td').show();
		$('#f_teacher_isTownUpBone').attr("data-rule", "required");
		$('#f_teacher_isHealth').attr("data-rule", "required");
		
		//从事特教起始年月
		
		//是否全日制特殊教育专业毕业
		
		//是否全日制学前教育专业毕业
		$('#f_teacher_isPreTch_td').show();
		//是否受过学前教育专业培养培训
		$('#f_teacher_isPreTrain_td').show();
		$('#f_teacher_isPreTch').attr("data-rule", "required");
		$('#f_teacher_isPreTrain').attr("data-rule", "required");
		
		//进本校年月
		$("#teacher_join_school").hide();
		$("#teacher_join_school_kind").show();
		
		//从事特教起始年月
		$("#f_teacher_specStartDate_td").hide();
		$("#f_teacher_specStartDate").attr("disabled", true);
		
		//籍贯
		$("#f_teacher_nativer_span").hide();
		$('#f_tw_base_form').validator("setField", "nativer", "");
		$("#f_teacher_nativer").trigger('validate');
		
		//健康状况
		$("#f_teacher_health_span").hide();
		$('#f_tw_base_form').validator("setField", "health", "");
		$("#f_teacher_health").trigger('validate');
		
		//信息技术应用能力
		$("#f_teacher_computerAbility_span").show();
		$("#f_teacher_computerAbility").attr("data-rule", "required");
	}
}

changeTfs(tfs_type);

//监控学校信息的变化
$('#teacher_form_school').on('change',function(){
	
	var tfschool = $("#teacher_form_school option:selected").val()
	if(tfschool){
	   	 //判断
	   	 $.ajax({ 
	            type: "POST", 
	            url: "${ctx}/core/basic/school/getSchoolType/"+tfschool,
	            dataType : "json",
	            success: function(data) {
	            	tfs_type = data.message;
	            	changeTfs(tfs_type);
	            },
	            error :  function(){
	                $(this).alertmsg('error', '系统错误！');
	            }
        }); 
	}
})

//是否参加基层服务项目  选中0-否时，开始时间和结束 时间不填写
//树下拉选择框：：选择事件
function pt_S_NodeCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId),
		nodes = zTree.getCheckedNodes(true)
	var ids = '', names = ''

	for (var i = 0; i < nodes.length; i++) {
		ids   += ','+ nodes[i].id
		names += ','+ nodes[i].name
	}
	if (ids.length > 0) {
		ids = ids.substr(1), names = names.substr(1)
	}

	var $from = $('#'+ treeId).data('fromObj')

	if ($from && $from.length) {

		$from.val(names)
		if (names == "0-否") {
			$('#f_teacher_joinBaseStart').val("");
			$('#f_teacher_joinBaseEnd').val("");
			$('#f_teacher_joinBaseStart').attr("disabled", true);
			$('#f_teacher_joinBaseEnd').attr("disabled", true);
		} else {
			$('#f_teacher_joinBaseStart').attr("disabled", false);
			$('#f_teacher_joinBaseEnd').attr("disabled", false);
		}
	}
		$('#' + treeId + '_id').val(ids)

}

//监控证件号码信息的变化
$('#f_teacher_idCard').on('change',function(){
	var paperType = $("#f_teacher_paperType option:selected").text();
	var cardNo = $("#f_teacher_idCard").val();
	if(paperType == '1-居民身份证'){
	   	//根据身份证号获取出生日期
	   	if(cardNo == ''){
	   		return;
	   	}else{
	   		var birthDay = getBirthdatByIdNo(cardNo);
	   		if( birthDay =='输入的身份证号位数错误'){
	   			$("#f_teacher_birthday").val("");
	   			$(this).alertmsg('error', birthDay+'！');
	   		}else{
	   			$("#f_teacher_birthday").val(birthDay);
	   			$("#f_teacher_birthday").trigger('validate');
	   		}
	   	}
	}
})
//监控身份证类型信息的变化
$('#f_teacher_paperType').on('change',function(){
	var paperType = $("#f_teacher_paperType option:selected").text();
	var cardNo = $("#f_teacher_idCard").val();
	if(paperType == '1-居民身份证'){
	   	//根据身份证号获取出生日期
	   	if(cardNo == ''){
	   		return;
	   	}else{
	   		var birthDay = getBirthdatByIdNo(cardNo);
	   		if( birthDay =='输入的身份证号位数错误'){
	   			$("#f_teacher_birthday").val("");
	   			$(this).alertmsg('error', birthDay+'！');
	   		}else{
	   			$("#f_teacher_birthday").val(birthDay);
	   			$("#f_teacher_birthday").trigger('validate');
	   		}
	   	}
	}
})

//监控国籍信息的变化
$("#f_teacher_nationality").on('change',function(){
	var nationlityName = $("#f_teacher_nationality option:selected").text();
	if(nationlityName == '156-中国'){
		$('#f_teacher_nation').attr("disabled", false);
		$('#f_teacher_politicType').attr("disabled", false);
		$('#f_teacher_nation option').removeAttr("disabled");
		$('#f_teacher_politicType option').removeAttr("disabled");
		$("#f_teacher_nation").selectpicker('refresh');
		$("#f_teacher_politicType").selectpicker('refresh');
		$("#f_teacher_nation_span").show();
		$("#f_teacher_politicType_span").show();
		$('#f_teacher_nation').attr("data-rule", "required");
		$('#f_teacher_politicType').attr("data-rule", "required");
		$('#f_teacher_nation').trigger('validate');
		$('#f_teacher_politicType').trigger('validate');
	}else{
		$("#f_teacher_nation_span").hide();
		$("#f_teacher_politicType_span").hide();
		$('#f_tw_base_form').validator("setField", "nation", "");
		$('#f_tw_base_form').validator("setField", "politicType", "");
		
		$('#f_teacher_nation').trigger('validate');
		$('#f_teacher_politicType').trigger('validate');
		
		$("#f_teacher_nation option").removeAttr("selected");
		$("#f_teacher_nation").selectpicker('refresh');

		$("#f_teacher_politicType option").removeAttr("selected");
		$("#f_teacher_politicType").selectpicker('refresh');

		$("#f_teacher_nation").attr("disabled", true)
		$("#f_teacher_politicType").attr("disabled", true);
	}
});

//监控是否在编信息的变化
$("#f_teacher_atSchool").on('change',function(){
	var atSchoolName = $("#f_teacher_atSchool option:selected").text();
	if(atSchoolName == '0-否'){
		$('#f_teacher_usePersonType').attr("disabled", false);
		$('#f_teacher_usePersonType option').removeAttr("disabled");
		$("#f_teacher_usePersonType").selectpicker('refresh');
		$("#f_teacher_usePersonType_span").show();
		$('#f_teacher_usePersonType').attr("data-rule", "required");
		$('#f_teacher_usePersonType').trigger('validate');
		
		$("#f_teacher_signContract option").removeAttr("selected");
		$("#f_teacher_signContract option[value=81464]").attr("selected","selected");
		$("#f_teacher_signContract").selectpicker('refresh');
	}else{
		$("#f_teacher_usePersonType_span").hide();
		$('#f_tw_base_form').validator("setField", "usePersonType", "");
		$('#f_teacher_usePersonType').trigger('validate');
		$("#f_teacher_usePersonType option").removeAttr("selected");
		$("#f_teacher_usePersonType").selectpicker('refresh');
		$("#f_teacher_usePersonType").attr("disabled", true);
		
		$("#f_teacher_signContract option").removeAttr("selected");
		$("#f_teacher_signContract option[value=81463]").attr("selected","selected");
		$("#f_teacher_signContract").selectpicker('refresh');
	}
});

//政治面貌多选限定
$("#f_teacher_politicType").change(function (){
	var fund = $("#f_teacher_politicType option:selected").text();
	var str = fund.split(" ");
	for(var i = 0; i < str.length; i++){
		if(str[i] == "13-群众" && str.length > 2){
			$(this).alertmsg('warn', '选择【13-群众】时，不能多选！')
			//$("#f_teacher_pay_insuranceHousFund option").selectpicker('deselectAll');
			$("#f_teacher_politicType option").removeAttr("selected");
			$("#f_teacher_politicType").selectpicker('refresh');
			return;
		}else if(str[i] == "12-无党派民主人士" && str.length > 2){
			$(this).alertmsg('warn', '选择【12-无党派民主人士】时，不能多选！')
			//$("#f_teacher_pay_insuranceHousFund option").selectpicker('deselectAll');
			$("#f_teacher_politicType option").removeAttr("selected");
			$("#f_teacher_politicType").selectpicker('refresh');
			return;
		}
	}
});

</script>

    <form action="${ctx}/core/basic/teacher/base/saveTeacher" id="f_tw_base_form" method="post">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="type" value="${type}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-bordered table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
            	 <tr>
                    <td id="f_teacher_area_td" colspan="2">
                        <label for="f_teacher_area" class="control-label x120"><span class="red">*</span>地区：</label>

                        <shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
                        	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
			            	 <select id="teacher_form_province" name="province" data-toggle="selectpicker" 
			             	data-nextselect="#teacher_form_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
								<option value="">--省市--</option>
				               	<c:forEach items="${provinces }" var="item">
				                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==entity.province}">selected</c:if>>${item.provinceName }</option>
				                </c:forEach>
			                </select>
			                </div>
			                 <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
			        		<select name="city" id="teacher_form_city" data-toggle="selectpicker" 
			        			 data-nextselect="#teacher_form_town" 
			       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
			        			   data-size="20" data-width="140px">
			        			 <option value="">--城市--</option>
			        			 <c:forEach items="${citys }" var="item">
			                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==entity.city}">selected</c:if>>${item.cityName }</option>
			                	</c:forEach>
			            	</select>
			            	</div>
			            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
				            <select name="town" id="teacher_form_town" data-toggle="selectpicker" 
				            	data-nextselect="#teacher_form_school" 
				            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
				            	 data-size="20" data-width="140px">
				            	<option value="">--区县--</option>
			        			 <c:forEach items="${towns }" var="item">
			                     		<option value="${item.townNo }" <c:if test="${item.townNo==entity.town}">selected</c:if>>${item.townName }</option>
			                	</c:forEach>
				            </select>
				            </div>
				             <div <c:if test="${sessionScope.currentUser.school==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.school!=0}">  style="display: none;" </c:if>>
				            <select name="school" id="teacher_form_school" data-toggle="selectpicker"  <c:if test="${sessionScope.currentUser.school!=0}">style="display: none;"</c:if>
				            	 data-size="20" data-width="140px" data-live-search="true" data-rule="required">
				            	<option value="">--学校--</option>
			        			 <c:forEach items="${schools }" var="item">
			                     		<option value="${item.id }" <c:if test="${item.id==entity.school}">selected</c:if>>${item.name }</option>
			                	</c:forEach>
				            </select>
				            </div>
			            </shiro:hasAnyRoles>
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_teacher_name" class="control-label x120"><span class="red">*</span>姓名：</label>
                        <input type="text" name="name" id="f_teacher_name" value="${entity.name }" data-rule="required" maxlength="20" size="20">
                    </td>
                    <td rowspan="7">
                        <label  class="control-label x120">照片规格:<br>26mm(宽)<br>32mm(高)<br>分辨率150dpi以上<br>照片要求为jpg格式<br>文件大小应小于60K：</label>
                        <img width="154" height="189" id="tch_img" src='<c:if test="${not empty entity.path}">${tchImgurl}</c:if><c:if test="${empty entity.path}">${ctx}/static/images/default_img.jpg</c:if>'/>
                    </td>
                </tr>
                <tr >
                     <td>
                        <label for="f_teacher_oldName" class="control-label x120">曾用名：</label>
                        <input type="text" name="oldName" id="f_teacher_oldName" value="${entity.oldName }"  maxlength="20" size="20">
                     </td>
                 </tr>
                 <tr>
                     <td>
                         <label for="f_teacher_gender"  class="control-label x120"><span class="red">*</span>性别：</label>
                         <select id="f_teacher_gender" name="gender" data-toggle="selectpicker" data-size="10" data-live-search="true"  data-width="200px" data-rule="required">
                             <option value="">--请选择--</option>
                             <c:forEach items="${genders }" var="item">
                                 <option  value="${item.id }" <c:if test="${item.id==entity.gender}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                 </tr>
                 <tr>
                     <td>
                         <label for="f_teacher_tchWorkNum" class="control-label x120">教职工号：</label>
                         <input type="text" name="tchWorkNum" id="f_teacher_tchWorkNum" value="<c:if test='${empty entity.tchWorkNum }'>0</c:if><c:if test='${not empty entity.tchWorkNum }'>${entity.tchWorkNum }</c:if>"  maxlength="20" size="20">
                     </td>
                 </tr>
                 <tr>
                     <td>
                         <label for="f_teacher_nationality" class="control-label x120"><span class="red">*</span>国籍/地区：</label>
                         <select id="f_teacher_nationality" name="nationality" data-toggle="selectpicker" data-size="10" data-live-search="true" data-rule="required" data-width="200px">
                             <option value="">--请选择--</option>
                             <c:forEach items="${nationalitys }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.nationality and item.name=='156-中国'}">selected</c:if><c:if test="${item.id==entity.nationality}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                 </tr>
                 <tr>
                     <td>
                         <label for="f_teacher_paperType" class="control-label x120"><span class="red">*</span>身份证件类型：</label>
                         <select id="f_teacher_paperType" name="paperType" data-toggle="selectpicker" data-size="10" data-live-search="true"  data-width="200px" data-rule="required">
                             <option value="">--请选择--</option>
                             <c:forEach items="${paperTypes }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.paperType and item.name=='1-居民身份证'}">selected</c:if><c:if test="${item.id==entity.paperType}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                 </tr>
                 <tr>
                     <td>
                         <label for="f_teacher_idCard" class="control-label x120"><span class="red">*</span>身份证件号：</label>
                         <input type="text" name="idCard" id="f_teacher_idCard" value="${entity.idCard }"  maxlength="18" size="20" data-rule="required">
                     </td>
                 </tr>
                 <tr>
                     <td>
                         <label for="f_teacher_birthday" class="control-label x120"><span class="red">*</span>出生日期：</label>
                         <input type="text" name="birthday" id="f_teacher_birthday" value='<fmt:formatDate value="${entity.birthday }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="出生日期: required; date;" size="20">
                     </td>
                     <td >
                         <label for="tch_file" class="control-label x120">人员照片：</label>
                         <input type="hidden" name="path" id="tch_path" value="${entity.path}"/>
                         <input type="file" name="file" id="tch_file" style="display: inline-block" onchange="uploadTchImg(this);">
                     </td>
                 </tr>
                 <tr >
                     <td>
                        <label for="f_teacher_nativer" class="control-label x120"><span class="red" id="f_teacher_nativer_span">*</span>籍贯：</label>
                        <input type="text" name="nativer" id="f_teacher_nativer" value="${entity.nativer }"  maxlength="20" size="20">
                     </td>
                      <td>
                        <label for="f_teacher_birthPlace" class="control-label x120">出生地：</label>
                        <input type="text" name="birthPlace" id="f_teacher_birthPlace" value="${entity.birthPlace }"  maxlength="20" size="20">
                     </td>
                 </tr>
                 <tr>
                     <td>
                         <label for="f_teacher_nation" class="control-label x120"><span id="f_teacher_nation_span" class="red" <c:if test="${not empty entity.nationality and entity.nationality != 81297}">style="display: none;"</c:if>>*</span>民族：</label>
                         <select id="f_teacher_nation" name="nation" data-toggle="selectpicker" data-size="10" data-live-search="true"  data-width="200px" <c:if test="${not empty entity.nationality and entity.nationality != 81297}">disabled="true"</c:if> data-rule="required">
                             <option value="">--请选择--</option>
                             <c:forEach items="${nations }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.nation and (entity.nationality == 81297 or empty entity.nationality) and item.name=='汉族'}">selected</c:if><c:if test="${item.id==entity.nation}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                     <td>
                     	<label for="f_teacher_politicType" class="control-label x120"><span id="f_teacher_politicType_span" class="red" <c:if test="${not empty entity.nationality and entity.nationality != 81297}">style="display: none;"</c:if>>*</span>政治面貌：</label>
                         <select id="f_teacher_politicType" name="politicType" data-toggle="selectpicker" data-size="10" data-live-search="true" multiple data-width="200px" <c:if test="${not empty entity.nationality and entity.nationality != 81297}">disabled="true"</c:if> data-rule="required">
                             <option value="">--请选择--</option>
                             <c:forEach items="${politicTypes }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.politicType and (entity.nationality == 81297 or empty entity.nationality)  and item.name=='13-群众'}">selected</c:if><c:if test="${item.checked}">selected</c:if>>${item.name } </option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                 </tr>
                 <tr>
                 	<td>
                        <label for="f_teacher_marry_ztree" class="control-label x120"> 婚姻状况：</label>
                        <input type="hidden" name="marry" value="${entity.marry}" id="f_teacher_marry_select_tree_id" >
                        <input type="text" id="f_teacher_marry_ztree" value="${marryName}" data-toggle="selectztree" size="20" data-tree="#f_teacher_marry_select_tree" readonly>
                        <ul style="height: 160px" id="f_teacher_marry_select_tree" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_marry_rj'}"
                            >
                        </ul>
                    </td>
                    <td>
                     	<label for="f_teacher_health" class="control-label x120"><span class="red" id="f_teacher_health_span">*</span>健康状况：</label>
                         <select id="f_teacher_health" name="health" data-toggle="selectpicker" data-size="10" data-live-search="true"  data-width="200px" >
                             <option value="">--请选择--</option>
                             <c:forEach items="${healths }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.health and item.name=='1-健康或良好'}">selected</c:if><c:if test="${item.id==entity.health}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                 </tr>
                  <tr>
                     <td>
                         <label for="f_teacher_workDay" class="control-label x120"><span class="red">*</span>参加工作年月：</label>
                         <input type="text" name="workDay" id="f_teacher_workDay" value='<fmt:formatDate value="${entity.workDay }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="参加工作年月: required; date; match[gt, birthday, date]" size="20">
                     </td>
                     <td >
                         <label for="f_teacher_joinSchoolDay" class="control-label x120"><span class="red">*</span><span id="teacher_join_school">进本校年月：</span><span id="teacher_join_school_kind" style="display: none;">进本园年月：</span></label>
                         <input type="text" name="joinSchoolDay" id="f_teacher_joinSchoolDay" value='<fmt:formatDate value="${entity.joinSchoolDay }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="required; date; match[gte, workDay, date]" size="20">
                     </td>
                 </tr>
                 <tr>
                 	<td id="f_teacher_workerFrom1_td">
                        <label for="f_teacher_workerFrom1_ztree" class="control-label x120"><span class="red">*</span>教职工来源：</label>
                        <input type="hidden" name="workerFrom1" value="${entity.workerFrom1}" id="f_teacher_workerFrom1_select_tree_id" >
                        <input type="text" id="f_teacher_workerFrom1_ztree" name="f_teacher_workerFrom1_ztree" value="${workerFrom1Name}"  data-toggle="selectztree" size="20" data-tree="#f_teacher_workerFrom1_select_tree" readonly>
                        <ul style="height: 160px" id="f_teacher_workerFrom1_select_tree" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_workerFrom1_rj'}"
                            >
                        </ul>
                    </td>
                    <td id="f_teacher_workerFrom2_td" style="display:none">
                     	<label for="f_teacher_workerFrom2_ztree" class="control-label x120"><span class="red">*</span>教职工来源：</label>
                        <input type="hidden" name="workerFrom2" value="${entity.workerFrom2}" id="f_teacher_workerFrom2_select_tree_id" >
                        <input type="text" id="f_teacher_workerFrom2_ztree" name="f_teacher_workerFrom2_ztree" value="${workerFrom2Name}" data-toggle="selectztree" size="20" data-tree="#f_teacher_workerFrom2_select_tree" readonly>
                        <ul style="height: 160px" id="f_teacher_workerFrom2_select_tree" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_workerFrom2_rj'}"
                            >
                        </ul>
                     </td>
                     <td id="f_teacher_workerType1_td">
                        <label for="f_teacher_workerType1_ztree" class="control-label x120"><span class="red">*</span>教职工类别：</label>
                        <input type="hidden" name="workerType1" value="${entity.workerType1}" id="f_teacher_workerType1_select_tree_id" >
                        <input type="text" id="f_teacher_workerType1_ztree" name="f_teacher_workerType1_ztree" value="${workerType1Name}"  data-toggle="selectztree" size="20" data-tree="#f_teacher_workerType1_select_tree" readonly>
                        <ul style="height: 160px" id="f_teacher_workerType1_select_tree" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_workerType1_rj'}"
                            >
                        </ul>
                    </td>
                    <td id="f_teacher_workerType2_td" style="display:none">
                     	<label for="f_teacher_workerType2_ztree" class="control-label x120"><span class="red">*</span>教职工类别：</label>
                        <input type="hidden" name="workerType2" value="${entity.workerType2}" id="f_teacher_workerType2_select_tree_id" >
                        <input type="text" id="f_teacher_workerType2_ztree" name="f_teacher_workerType2_ztree" value="${workerType2Name}"  data-toggle="selectztree" size="20" data-tree="#f_teacher_workerType2_select_tree" readonly>
                        <ul style="height: 160px" id="f_teacher_workerType2_select_tree" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_workerType2_rj'}"
                            >
                        </ul>
                     </td>
                 	<td id="f_teacher_workerType3_td" style="display:none">
                        <label for="f_teacher_workerType3_ztree" class="control-label x120"><span class="red">*</span>教职工类别：</label>
                        <input type="hidden" name="workerType3" value="${entity.workerType3}" id="f_teacher_workerType3_select_tree_id" >
                        <input type="text" id="f_teacher_workerType3_ztree" name="f_teacher_workerType3_ztree" value="${workerType3Name}"  data-toggle="selectztree" size="20" data-tree="#f_teacher_workerType3_select_tree" readonly>
                        <ul style="height: 160px" id="f_teacher_workerType3_select_tree" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_workerType3_rj'}"
                            >
                        </ul>
                    </td>
                    <td id="f_teacher_workerType4_td" style="display:none">
                        <label for="f_teacher_workerType4_ztree" class="control-label x120"><span class="red">*</span>教职工类别：</label>
                        <input type="hidden" name="workerType4" value="${entity.workerType4}" id="f_teacher_workerType4_select_tree_id" >
                        <input type="text" id="f_teacher_workerType4_ztree" name="f_teacher_workerType4_ztree" value="${workerType4Name}"  data-toggle="selectztree" size="20" data-tree="#f_teacher_workerType4_select_tree" readonly>
                        <ul style="height: 160px" id="f_teacher_workerType4_select_tree" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_workerType4_rj'}"
                            >
                        </ul>
                    </td>
                 </tr>
                  <tr>
                     <td>
                         <label for="f_teacher_atSchool" class="control-label x120"><span class="red">*</span>是否在编：</label>
                         <select id="f_teacher_atSchool" name="atSchool" data-toggle="selectpicker" data-size="10"   data-width="200px" data-rule="required">
                             <option value="">--请选择--</option>
                             <c:forEach items="${isFlags }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.atSchool and item.name=='0-否'}">selected</c:if><c:if test="${item.id==entity.atSchool}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                     <td>
                         <label for="f_teacher_usePersonType" class="control-label x120"><span id="f_teacher_usePersonType_span" class="red" <c:if test="${not empty entity.atSchool and empty entity.usePersonType }">style="display: none;"</c:if> >*</span>用人形式：</label>
                         <select id="f_teacher_usePersonType" name="usePersonType" data-toggle="selectpicker" data-size="10" data-live-search="true"  data-width="200px" <c:if test="${not empty entity.atSchool and empty entity.usePersonType }">disabled="true"</c:if> data-rule="required">
                             <option value="">--请选择--</option>
                             <c:forEach items="${usePersonTypes }" var="item">
                                 <option  value="${item.id }" <c:if test="${item.id==entity.usePersonType}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                 </tr>
                 <tr>
                 	<td>
                         <label for="f_teacher_signContract" class="control-label x120"><span class="red">*</span>签订合同情况：</label>
                         <select id="f_teacher_signContract" name="signContract" data-toggle="selectpicker" data-size="10" data-live-search="true"  data-width="200px" data-rule="required">
                             <option value="">--请选择--</option>
                             <c:forEach items="${signContracts }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.signContract and item.name=='2-劳动合同'}">selected</c:if><c:if test="${item.id==entity.signContract}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                     <td id="f_teacher_isQrz_td">
                         <label for="f_teacher_isQrz" class="control-label x120"><span class="red">*</span>是否全日制师范类专业毕业：</label>
                         <select id="f_teacher_isQrz" name="isQrz" data-toggle="selectpicker" data-size="10"   data-width="200px" data-rule="required">
                             <option value="">--请选择--</option>
                             <c:forEach items="${isFlags }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.isQrz and item.name=='0-否'}">selected</c:if><c:if test="${item.id==entity.isQrz}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                     <td id="f_teacher_isDoubleTch_td" style="display:none">
                        <label for="f_teacher_isDoubleTch" class="control-label x120"><span class="red">*</span>是否“双师型”教师：</label>
                         <select id="f_teacher_isDoubleTch" name="isDoubleTch" data-toggle="selectpicker" data-size="10"   data-width="200px">
                             <option value="">--请选择--</option>
                             <c:forEach items="${isFlags }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.isDoubleTch and item.name=='0-否'}">selected</c:if><c:if test="${item.id==entity.isDoubleTch}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                 </tr>
                 <tr>
                 	<td id="f_teacher_isTjpx_td" style="display:none">
                        <label for="f_teacher_isTjpx" class="control-label x120"><span class="red">*</span>是否受过特教专业培养培训：</label>
                         <select id="f_teacher_isTjpx" name="isTjpx" data-toggle="selectpicker" data-size="10"   data-width="200px" >
                             <option value="">--请选择--</option>
                             <c:forEach items="${isFlags }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.isTjpx and item.name=='0-否'}">selected</c:if><c:if test="${item.id==entity.isTjpx}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                     <td id="f_teacher_isTszs_td"  style="display:none">
                         <label for="f_teacher_isTszs" class="control-label x120"><span class="red">*</span>是否有特殊教育从业证书：</label>
                         <select id="f_teacher_isTszs" name="isTszs" data-toggle="selectpicker" data-size="10"   data-width="200px" >
                             <option value="">--请选择--</option>
                             <c:forEach items="${isFlags }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.isTszs and item.name=='0-否'}">selected</c:if><c:if test="${item.id==entity.isTszs}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                 </tr>
                 <tr>
                 	<td>
                         <label for="f_teacher_computerAbility" class="control-label x120"><span class="red" id="f_teacher_computerAbility_span">*</span>信息技术应用能力：</label>
                         <select id="f_teacher_computerAbility" name="computerAbility" data-toggle="selectpicker" data-size="10" data-live-search="true"  data-width="200px">
                             <option value="">--请选择--</option>
                             <c:forEach items="${computerAbilitys }" var="item">
                                 <option  value="${item.id }" <c:if test="${item.id==entity.computerAbility}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                     <td id="f_teacher_isMian_td" >
                         <label for="f_teacher_isMian" class="control-label x120"><span class="red">*</span>是否属于免费（公费）师范生：</label>
                         <select id="f_teacher_isMian" name="isMian" data-toggle="selectpicker" data-size="10"   data-width="200px" data-rule="required">
                             <option value="">--请选择--</option>
                             <c:forEach items="${isMains }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.isMian and item.name=='0-否'}">selected</c:if><c:if test="${item.id==entity.isMian}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                     <td id="f_teacher_isProfessCard_td" style="display:none">
                         <label for="f_teacher_isProfessCard" class="control-label x120"><span class="red">*</span>是否具备职业技能等级证书：</label>
                         <select id="f_teacher_isProfessCard" name="isProfessCard" data-toggle="selectpicker" data-size="10"   data-width="200px" >
                             <option value="">--请选择--</option>
                             <c:forEach items="${isFlags }" var="item">
                                 <option  value="${item.id }" <c:if test="${item.id==entity.isProfessCard}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                 </tr>
                 <tr>
                 	<td id="f_teacher_isJoinBase_ztree_td">
                        <label for="f_teacher_isJoinBase_ztree" class="control-label x120"><span class="red">*</span>是否参加基层服务项目：</label>
                        <input type="hidden" name="isJoinBase" value="<c:if test="${empty isJoinBaseName}">81476</c:if><c:if test="${not empty isJoinBaseName}">${entity.isJoinBase}</c:if>" id="f_teacher_isJoinBase_select_tree_id" >
                        <input type="text" id="f_teacher_isJoinBase_ztree" name="f_teacher_isJoinBase_ztree" value="<c:if test="${empty isJoinBaseName}">0-否</c:if><c:if test="${not empty isJoinBaseName}">${isJoinBaseName}</c:if>" data-rule="required" data-toggle="selectztree" size="20" data-tree="#f_teacher_isJoinBase_select_tree" readonly>
                        <ul style="height: 160px" id="f_teacher_isJoinBase_select_tree" class="ztree hide" data-toggle="ztree" data-expand-all="true"
                            data-check-enable="true" data-chk-style="radio" data-radio-type="all" data-on-check="pt_S_NodeCheck"
                            data-on-click="S_NodeClick"
                            data-setting=" { check:{enable: true},data: { simpleData: { enable: true, idKey: 'id', pIdKey: 'parentId', } } }"
                            data-options="{nodes:'f_teacher_isJoinBase_rj'}"
                            >
                        </ul>
                     </td>
                     <td id="f_teacher_joinBaseStart_td" >
                     	<label for="f_teacher_joinBaseStart" class="control-label x120">参加基层服务项目起始年月：</label>
                         <input type="text" name="joinBaseStart" id="f_teacher_joinBaseStart" <c:if test="${empty entity.id}">disabled="true"</c:if> value='<fmt:formatDate value="${entity.joinBaseStart }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="20">
                     </td>
                 </tr>
                 <tr>
                     <td id="f_teacher_joinBaseEnd_td">
                     	<label for="f_teacher_joinBaseEnd" class="control-label x120">参加基层服务项目结束年月：</label>
                         <input type="text" name="joinBaseEnd" id="f_teacher_joinBaseEnd" <c:if test="${empty entity.id}">disabled="true"</c:if> value='<fmt:formatDate value="${entity.joinBaseEnd }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="date" size="20">
                     </td>
                     <td>
                        <label for="f_teacher_isTj" class="control-label x120"><span class="red">*</span>是否是特级教师：</label>
                         <select id="f_teacher_isTj" name="isTj" data-toggle="selectpicker" data-size="10" data-rule="required"  data-width="200px">
                             <option value="">--请选择--</option>
                             <c:forEach items="${isFlags }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.isTj and item.name=='0-否'}">selected</c:if><c:if test="${item.id==entity.isTj}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                     <td id="f_teacher_workDateTimer_td" style="display: none">
                         <label for="f_teacher_workDateTimer" class="control-label x120"><span class="red">*</span>企业工作(实践)时长：</label>
                         <select id="f_teacher_workDateTimer" name="workDateTimer" data-toggle="selectpicker" data-size="10"   data-width="200px" >
                             <option value="">--请选择--</option>
                             <c:forEach items="${workDateTimers }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.workDateTimer and item.name=='0-无'}">selected</c:if><c:if test="${item.id==entity.workDateTimer}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                 </tr>
                 <tr>
                     <td id="f_teacher_isTownUpBone_td" >
                     	 <label for="f_teacher_isTownUpBone" class="control-label x120"><span class="red">*</span>是否县级及以上骨干教师：</label>
                         <select id="f_teacher_isTownUpBone" name="isTownUpBone" data-toggle="selectpicker" data-size="10"   data-width="200px">
                             <option value="">--请选择--</option>
                             <c:forEach items="${isFlags }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.isTownUpBone and item.name=='0-否'}">selected</c:if><c:if test="${item.id==entity.isTownUpBone}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                     <td id="f_teacher_isHealth_td">
                        <label for="f_teacher_isHealth" class="control-label x120"><span class="red">*</span>是否心理健康教育教师：</label>
                         <select id="f_teacher_isHealth" name="isHealth" data-toggle="selectpicker" data-size="10"   data-width="200px">
                             <option value="">--请选择--</option>
                             <c:forEach items="${isHealths }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.isHealth and item.name=='0-否'}">selected</c:if><c:if test="${item.id==entity.isHealth}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                 </tr>
                 <tr>
                	 <td id="f_teacher_isPreTch_td" style="display: none">
                         <label for="f_teacher_isPreTch" class="control-label x120"><span class="red">*</span>是否全日制学前教育专业毕业：</label>
                         <select id="f_teacher_isPreTch" name="isPreTch" data-toggle="selectpicker" data-size="10"   data-width="200px" >
                             <option value="">--请选择--</option>
                             <c:forEach items="${isFlags }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.isPreTch and item.name=='0-否'}">selected</c:if><c:if test="${item.id==entity.isPreTch}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                     <td id="f_teacher_isPreTrain_td" style="display: none">
                         <label for="f_teacher_isPreTrain" class="control-label x120"><span class="red">*</span>是否受过学前教育专业培养培训：</label>
                         <select id="f_teacher_isPreTrain" name="isPreTrain" data-toggle="selectpicker" data-size="10"   data-width="200px" >
                             <option value="">--请选择--</option>
                             <c:forEach items="${isFlags }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.isPreTrain and item.name=='0-否'}">selected</c:if><c:if test="${item.id==entity.isPreTrain}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                 </tr>
                 <tr>
                     <td>
                     	 <label for="f_teacher_personStatus" class="control-label x120"><span class="red">*</span>人员状态：</label>
                         <select id="f_teacher_personStatus" name="personStatus" data-toggle="selectpicker" data-size="10" data-rule="required"  data-width="200px">
                             <option value="">--请选择--</option>
                             <c:forEach items="${personStatuss }" var="item">
                                 <option  value="${item.id }" <c:if test="${empty entity.personStatus and item.name=='100-在本单位任职'}">selected</c:if><c:if test="${item.id==entity.personStatus}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                     <td id="f_teacher_specStartDate_td" style="display: none">
                         <label for="f_teacher_specStartDate" class="control-label x120"><span class="red">*</span>从事特教起始年月：</label>
                         <input type="text" name="specStartDate" id="f_teacher_specStartDate" value='<fmt:formatDate value="${entity.specStartDate }" pattern="yyyy-MM-dd"/>' disabled="true" data-toggle="datepicker" data-rule="从事特教起始年月: required; date; match[gte, workDay, date]" size="20">
                     </td>
                     <!--  
                     <td id="f_teacher_isDoubleTch_td" style="display:none">
                        <label for="f_teacher_isDoubleTch" class="control-label x120">是否“双师型”教师：</label>
                         <select id="f_teacher_isDoubleTch" name="isDoubleTch" data-toggle="selectpicker" data-size="10"   data-width="200px">
                             <option value="">--请选择--</option>
                             <c:forEach items="${isFlags }" var="item">
                                 <option  value="${item.id }" <c:if test="${item.id==entity.isDoubleTch}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                     -->
                 </tr>
                 <!--  
                 <tr>
                	 <td id="f_teacher_isProfessCard_td" style="display:none">
                         <label for="f_teacher_isProfessCard" class="control-label x120">是否具备职业技能等级证书：</label>
                         <select id="f_teacher_isProfessCard" name="isProfessCard" data-toggle="selectpicker" data-size="10"   data-width="200px" >
                             <option value="">--请选择--</option>
                             <c:forEach items="${isFlags }" var="item">
                                 <option  value="${item.id }" <c:if test="${item.id==entity.isProfessCard}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                     <td id="f_teacher_workDateTimer_td" style="display: none">
                         <label for="f_teacher_workDateTimer" class="control-label x120">企业工作(实践)时长：</label>
                         <select id="f_teacher_workDateTimer" name="workDateTimer" data-toggle="selectpicker" data-size="10"   data-width="200px" >
                             <option value="">--请选择--</option>
                             <c:forEach items="${workDateTimers }" var="item">
                                 <option  value="${item.id }" <c:if test="${item.id==entity.workDateTimer}">selected</c:if>>${item.name }</option>
                             </c:forEach>
                         </select>&nbsp;
                     </td>
                 </tr>
                 -->
                <tr>
                    <td colspan="2">
                        <label for="f_teacher_memo" class="control-label x120">备注：</label>
                        <textarea name="memo" id="f_teacher_memo" data-toggle="autoheight" cols="40" rows="2" maxlength="400">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
