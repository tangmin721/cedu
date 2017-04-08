<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>


<link rel="stylesheet" type="text/css" href="${ctx}/static/css/reset.css" media="screen, projection">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/layout.css" media="screen, projection">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/main.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/teacher.css">
<script type="text/javascript" src="${ctx}/static/js/biz.gzjs"></script>

<script type="text/javascript">


function teacherLoadViewDiv(div_id){
	var url = "";
	if(div_id=="div1_div"){
		url = "${ctx}/core/basic/teacher/studyExp/teacherStudyExpList/${entity.id}";
	}else if(div_id=="div2_div"){
		url = "${ctx}/core/basic/teacher/workExp/teacherWorkExpList/${entity.id}";
	}else if(div_id=="div3_div"){
		url = "${ctx}/core/basic/teacher/postEngage/teacherPostEngageList/${entity.id}/${entity.schoolType}";
	}else if(div_id=="div4_div"){
		url = "${ctx}/core/basic/teacher/professionTechnical/professionTechnicalList/${entity.id}";
	}else if(div_id=="div5_div"){
		url = "${ctx}/core/basic/teacher/teacherPay/teacherPayList/${entity.id}/${entity.schoolType}";
	}else if(div_id=="div6_div"){
		url = "${ctx}/core/basic/teacher/assess/teacherAssessList/${entity.id}";
	}else if(div_id=="div7_div"){
		url = "${ctx}/core/basic/teacher/teacherQualification/teacherQualificationList/${entity.id}";
	}else if(div_id=="div8_div"){
		url = "${ctx}/core/basic/teacher/teacherMorality/teacherMoralityList/${entity.id}";
	}else if(div_id=="div9_div"){
		url = "${ctx}/core/basic/teacher/teacherEducation/teacherEducationList/${entity.id}/${entity.schoolType}";
	}else if(div_id=="div10_div"){
		url = "${ctx}/core/basic/teacher/teacherTalentProject/teacherTalentProjectList/${entity.id}";
	}else if(div_id=="div11_div"){
		url = "${ctx}/core/basic/teacher/trainExp/teacherTrainExpList/${entity.id}";
	}else if(div_id=="div12_div"){
		url = "${ctx}/core/basic/teacher/overseasStudy/teacherOverseasStudyList/${entity.id}";
	}else if(div_id=="div13_div"){
		url = "${ctx}/core/basic/teacher/credent/teacherCredentList?tid=${entity.id}";
	}else if(div_id=="div14_div"){
		url = "${ctx}/core/basic/teacher/communRotate/teacherCommunRotateList/${entity.id}/${entity.schoolType}";
	}else if(div_id=="div15_div"){
		url = "${ctx}/core/basic/teacher/contactWay/teacherContactWayList/${entity.id}/${entity.schoolType}";
	}else if(div_id=="div16_div"){
		url = "${ctx}/core/basic/teacher/score/teacherScoreList?tid=${entity.id}";
	}
	
	$('#'+div_id).bjuiajax('doLoad', {
		target:"#"+div_id,
		url:url,
		type:"GET"
	})
}

var tabGroupHeight = $(".tabGroup").height();
var winHeight = $('#tch_contain').parent().height()-55;
$("#container").css("height",tabGroupHeight>winHeight?tabGroupHeight:winHeight+"px");
$("#content_div .shrink").each(function(){
	var $imgObj = $(this);
	$imgObj.click(function(){
		var shrinkId = $imgObj.attr("shrinkId");
		var src = $imgObj.attr("src");
		if(shrinkId=="div0_div"){
			if('none' == $("#"+shrinkId).css("display")){
				$("#"+shrinkId).css("display","");
				$imgObj.attr('src','${ctx}/static/images/tbtn_shrink.png');
			}else{
				$("#"+shrinkId).css("display","none");
				$imgObj.attr('src','${ctx}/static/images/tbtn_spread.png');
			}
		}else {
			//根据 展开，收起按钮 加载div
			if(src.indexOf("tbtn_shrink") >= 0){
				$("#"+shrinkId).css("display","none");
				$imgObj.attr('src','${ctx}/static/images/tbtn_spread.png');
			}else{
				$("#"+shrinkId).css("display","");
				$imgObj.attr('src','${ctx}/static/images/tbtn_shrink.png');
				teacherLoadViewDiv(shrinkId);
			}
		}
       });
});


$($("#outerWrap ul li")[0]).addClass('selectedTab')

$("#outerWrap ul li").unbind().click(function(){
	$("#outerWrap ul li").removeClass('selectedTab');
    var num = $(this).addClass('selectedTab').attr("status");
    var id = $("#content_div .tabContent [id*=_div]")[num].id;

	//点击左侧导航，同时加载显示div
	$("#"+id).css("display","");
	$("#content_div .shrink").each(function(){
		var $imgObj = $(this);
		var shrinkId = $imgObj.attr("shrinkId");
		if(shrinkId == id){
			$imgObj.attr('src','${ctx}/static/images/tbtn_shrink.png');
		}
	});

	$("#blueline").animate({top: $(this).get(0).offsetTop}, 500);
	$("#container").animate({scrollTop: $("#" + id).get(0).offsetTop-40}, 500);

	teacherLoadViewDiv(id);
});

	if ('${entity.schoolType}' == 0) {
		$('#f_teacher_workerFrom1_td').show();
		$('#f_teacher_workerFrom2_td').hide();
		$('#f_teacher_workerFrom1_td_val').show();
		$('#f_teacher_workerFrom2_td_val').hide();

		$('#f_teacher_workerType1_td').show();
		$('#f_teacher_workerType2_td').hide();
		$('#f_teacher_workerType3_td').hide();
		$('#f_teacher_workerType4_td').hide();
		$('#f_teacher_workerType1_td_val').show();
		$('#f_teacher_workerType2_td_val').hide();
		$('#f_teacher_workerType3_td_val').hide();
		$('#f_teacher_workerType4_td_val').hide();

		//是否受过特教专业培养培训
		$('#f_teacher_isTjpx_td').show();
		//是否有特殊教育从业证书
		$('#f_teacher_isTszs_td').show();
		$('#f_teacher_isTjpx_td_val').show();
		$('#f_teacher_isTszs_td_val').show();

		//是否全日制师范类专业毕业
		$('#f_teacher_isQrz_td').show();
		$('#f_teacher_isQrz_td_val').show();

		//是否“双师型”教师
		$('#f_teacher_isDoubleTch_td').hide();
		$('#f_teacher_isDoubleTch_td_val').hide();

		//是否具备职业技能等级证书
		$('#f_teacher_isProfessCard_td').hide();
		$('#f_teacher_isProfessCard_td_val').hide();

		//企业工作(实践)时长
		$('#f_teacher_workDateTimer_td').hide();
		$('#f_teacher_workDateTimer_td_val').hide();

		//是否属于免费（公费）师范生
		$('#f_teacher_isMian_td').show();
		//是否参加基层服务项目
		$('#f_teacher_isJoinBase_ztree_td').show();
		$('#f_teacher_isMian_td_val').show();
		$('#f_teacher_isJoinBase_ztree_td_val').show();

		//参加基层服务项目起始年月
		$('#f_teacher_joinBaseStart_td').show();
		$('#f_teacher_joinBaseStart_td_val').show();
		//参加基层服务项目结束年月
		$('#f_teacher_joinBaseEnd_td').show();
		$('#f_teacher_joinBaseEnd_td_val').show();

		//是否县级及以上骨干教师
		$('#f_teacher_isTownUpBone_td').show();
		$('#f_teacher_isTownUpBone_td_val').show();
		//是否心理健康教育教师
		$('#f_teacher_isHealth_td').show();
		$('#f_teacher_isHealth_td_val').show();

		//是否全日制学前教育专业毕业
		$('#f_teacher_isPreTch_td').hide();
		$('#f_teacher_isPreTch_td_val').hide();
		//是否受过学前教育专业培养培训
		$('#f_teacher_isPreTrain_td').hide();
		$('#f_teacher_isPreTrain_td_val').hide();
		//从事特教起始年月
		$("#f_teacher_specStartDate_td").hide();
		$("#f_teacher_specStartDate_td_val").hide();
	} else if ('${entity.schoolType}' == 1) {
		$('#f_teacher_workerFrom1_td').show();
		$('#f_teacher_workerFrom2_td').hide();
		$('#f_teacher_workerFrom1_td_val').show();
		$('#f_teacher_workerFrom2_td_val').hide();

		$('#f_teacher_workerType1_td').hide();
		$('#f_teacher_workerType2_td').show();
		$('#f_teacher_workerType3_td').hide();
		$('#f_teacher_workerType4_td').hide();
		$('#f_teacher_workerType1_td_val').hide();
		$('#f_teacher_workerType2_td_val').show();
		$('#f_teacher_workerType3_td_val').hide();
		$('#f_teacher_workerType4_td_val').hide();

		//是否受过特教专业培养培训
		$('#f_teacher_isTjpx_td').hide();
		//是否有特殊教育从业证书
		$('#f_teacher_isTszs_td').hide();
		$('#f_teacher_isTjpx_td_val').hide();
		$('#f_teacher_isTszs_td_val').hide();

		//是否全日制师范类专业毕业
		$('#f_teacher_isQrz_td').hide();
		$('#f_teacher_isQrz_td_val').hide();

		//是否“双师型”教师
		$('#f_teacher_isDoubleTch_td').show();
		$('#f_teacher_isDoubleTch_td_val').show();

		//是否具备职业技能等级证书
		$('#f_teacher_isProfessCard_td').show();
		$('#f_teacher_isProfessCard_td_val').show();

		//企业工作(实践)时长
		$('#f_teacher_workDateTimer_td').show();
		$('#f_teacher_workDateTimer_td_val').show();

		//是否属于免费（公费）师范生
		$('#f_teacher_isMian_td').hide();
		$('#f_teacher_isMian_td_val').hide();
		//是否参加基层服务项目
		$('#f_teacher_isJoinBase_ztree_td').hide();
		$('#f_teacher_isJoinBase_ztree_td_val').hide();

		//参加基层服务项目起始年月
		$('#f_teacher_joinBaseStart_td').hide();
		$('#f_teacher_joinBaseStart_td_val').hide();
		//参加基层服务项目结束年月
		$('#f_teacher_joinBaseEnd_td').hide();
		$('#f_teacher_joinBaseEnd_td_val').hide();

		//是否县级及以上骨干教师
		$('#f_teacher_isTownUpBone_td').hide();
		$('#f_teacher_isTownUpBone_td_val').hide();
		//是否心理健康教育教师
		$('#f_teacher_isHealth_td').hide();
		$('#f_teacher_isHealth_td_val').hide();

		//是否全日制学前教育专业毕业
		$('#f_teacher_isPreTch_td').hide();
		$('#f_teacher_isPreTch_td_val').hide();
		//是否受过学前教育专业培养培训
		$('#f_teacher_isPreTrain_td').hide();
		$('#f_teacher_isPreTrain_td_val').hide();
		//从事特教起始年月
		$("#f_teacher_specStartDate_td").hide();
		$("#f_teacher_specStartDate_td_val").hide();
	} else if ('${entity.schoolType}' == 2) {
		$('#f_teacher_workerFrom1_td').show();
		$('#f_teacher_workerFrom2_td').hide();
		$('#f_teacher_workerFrom1_td_val').show();
		$('#f_teacher_workerFrom2_td_val').hide();

		$('#f_teacher_workerType1_td').hide();
		$('#f_teacher_workerType2_td').hide();
		$('#f_teacher_workerType3_td').show();
		$('#f_teacher_workerType4_td').hide();
		$('#f_teacher_workerType1_td_val').hide();
		$('#f_teacher_workerType2_td_val').hide();
		$('#f_teacher_workerType3_td_val').show();
		$('#f_teacher_workerType4_td_val').hide();

		//是否受过特教专业培养培训
		$('#f_teacher_isTjpx_td').show();
		//是否有特殊教育从业证书
		$('#f_teacher_isTszs_td').show();
		$('#f_teacher_isTjpx_td_val').show();
		$('#f_teacher_isTszs_td_val').show();

		//是否全日制师范类专业毕业
		$('#f_teacher_isQrz_td').show();
		$('#f_teacher_isQrz_td_val').show();

		//是否“双师型”教师
		$('#f_teacher_isDoubleTch_td').hide();
		$('#f_teacher_isDoubleTch_td_val').hide();

		//是否具备职业技能等级证书
		$('#f_teacher_isProfessCard_td').hide();
		$('#f_teacher_isProfessCard_td_val').hide();

		//企业工作(实践)时长
		$('#f_teacher_workDateTimer_td').hide();
		$('#f_teacher_workDateTimer_td_val').hide();

		//是否属于免费（公费）师范生
		$('#f_teacher_isMian_td').show();
		$('#f_teacher_isMian_td_val').show();
		//是否参加基层服务项目
		$('#f_teacher_isJoinBase_ztree_td').show();
		$('#f_teacher_isJoinBase_ztree_td_val').show();

		//参加基层服务项目起始年月
		$('#f_teacher_joinBaseStart_td').show();
		$('#f_teacher_joinBaseStart_td_val').show();
		//参加基层服务项目结束年月
		$('#f_teacher_joinBaseEnd_td').show();
		$('#f_teacher_joinBaseEnd_td_val').show();

		//是否县级及以上骨干教师
		$('#f_teacher_isTownUpBone_td').show();
		$('#f_teacher_isTownUpBone_td_val').show();
		//是否心理健康教育教师
		$('#f_teacher_isHealth_td').show();
		$('#f_teacher_isHealth_td_val').show();

		//是否全日制学前教育专业毕业
		$('#f_teacher_isPreTch_td').hide();
		$('#f_teacher_isPreTch_td_val').hide();
		//是否受过学前教育专业培养培训
		$('#f_teacher_isPreTrain_td').hide();
		$('#f_teacher_isPreTrain_td_val').hide();
		
		//从事特教起始年月
		$("#f_teacher_specStartDate_td").show();
		$("#f_teacher_specStartDate_td_val").show();

	} else if ('${entity.schoolType}' == 3) {
		$('#f_teacher_workerFrom1_td').hide();
		$('#f_teacher_workerFrom2_td').show();
		$('#f_teacher_workerFrom1_td_val').hide();
		$('#f_teacher_workerFrom2_td_val').show();

		$('#f_teacher_workerType1_td').hide();
		$('#f_teacher_workerType2_td').hide();
		$('#f_teacher_workerType3_td').hide();
		$('#f_teacher_workerType4_td').show();
		$('#f_teacher_workerType1_td_val').hide();
		$('#f_teacher_workerType2_td_val').hide();
		$('#f_teacher_workerType3_td_val').hide();
		$('#f_teacher_workerType4_td_val').show();


		//是否受过特教专业培养培训
		$('#f_teacher_isTjpx_td').hide();
		//是否有特殊教育从业证书
		$('#f_teacher_isTszs_td').hide();
		$('#f_teacher_isTjpx_td_val').hide();
		$('#f_teacher_isTszs_td_val').hide();

		//是否全日制师范类专业毕业
		$('#f_teacher_isQrz_td').show();
		$('#f_teacher_isQrz_td_val').show();

		//是否“双师型”教师
		$('#f_teacher_isDoubleTch_td').hide();
		$('#f_teacher_isDoubleTch_td_val').hide();

		//是否具备职业技能等级证书
		$('#f_teacher_isProfessCard_td').hide();
		$('#f_teacher_isProfessCard_td_val').hide();

		//企业工作(实践)时长
		$('#f_teacher_workDateTimer_td').hide();
		$('#f_teacher_workDateTimer_td_val').hide();

		//是否属于免费（公费）师范生
		$('#f_teacher_isMian_td').show();
		$('#f_teacher_isMian_td_val').show();
		//是否参加基层服务项目
		$('#f_teacher_isJoinBase_ztree_td').show();
		$('#f_teacher_isJoinBase_ztree_td_val').show();

		//参加基层服务项目起始年月
		$('#f_teacher_joinBaseStart_td').show();
		$('#f_teacher_joinBaseStart_td_val').show();
		//参加基层服务项目结束年月
		$('#f_teacher_joinBaseEnd_td').show();
		$('#f_teacher_joinBaseEnd_td_val').show();

		//是否县级及以上骨干教师
		$('#f_teacher_isTownUpBone_td').show();
		$('#f_teacher_isTownUpBone_td_val').show();
		//是否心理健康教育教师
		$('#f_teacher_isHealth_td').show();
		$('#f_teacher_isHealth_td_val').show();

		//从事特教起始年月

		//是否全日制特殊教育专业毕业

		//是否全日制学前教育专业毕业
		$('#f_teacher_isPreTch_td').show();
		$('#f_teacher_isPreTch_td_val').show();
		//是否受过学前教育专业培养培训
		$('#f_teacher_isPreTrain_td').show();
		$('#f_teacher_isPreTrain_td_val').show();
		
		//从事特教起始年月
		$("#f_teacher_specStartDate_td").hide();
		$("#f_teacher_specStartDate_td_val").hide();

	}
	
</script>
<div class="bjui-pageContent">
<div class="teacher_containter" id="tch_contain">
	<div class="teacher_top">
		<div class="teacher_top_left"></div>
	    <div class="teacher_top_middle">
	        <img src="${ctx}/static/images/jsxx_icon.png" class="jsxx_icon">
	        <span class="teacher_top_title">教师个人信息</span>
	        <span class="teacher_top_title title_mark">
	            	<!--  （审核通过）     -->
	            <span class="title-red">【如果涉及涉密信息，请按照国家有关规定填写】</span>
            </span>
        	 <span class="teacher_top_title">个人标识码：${entity.tno }</span> 	
	    </div>
	    <div class="teacher_top_right"></div>
	</div>
 	<!--内容区-->
	<div class="tabmain">
		<div id="outerWrap">
			<div id="blueline" class="blueline" style="top: 0px;"></div>
			<ul class="tabGroup">
					<li class="tabOption selectedTab" status="0">基本信息<span class="bt-red">*</span></li>
					
					<li class="tabOption" status="1">学习经历<span class="bt-red">*</span></li>
					
					<li class="tabOption" status="2">工作经历<span class="bt-red">*</span></li>
					
					<li class="tabOption" status="3">岗位聘任<span class="bt-red">*</span></li>
				
					<li class="tabOption" status="4">专业技术职务聘任<span class="bt-red">*</span></li>
					
					<li class="tabOption" status="5">基本待遇<span class="bt-red">*</span></li>
					
					<li class="tabOption" status="6">年度考核<span class="bt-red">*</span></li>

					<li class="tabOption" status="7">教师资格<span class="bt-red">*</span></li>
					
					<li class="tabOption" status="8">师德信息<span class="bt-red">*</span></li>

					<li class="tabOption" status="9">教育教学<span class="bt-red">*</span></li>
				
					<li class="tabOption" status="10">入选人才项目</li>
				
					<li class="tabOption" status="11">国内培训<span class="bt-red">*</span></li>
				
					<li class="tabOption" status="12">海外研修(访学)</li>
				
					<li class="tabOption" status="13">技能及证书<span class="bt-red">*</span></li>
				
					<li class="tabOption" status="14">交流轮岗</li>
				
					<li class="tabOption" status="15">联系方式</li>
					
					<li class="tabOption" status="16">学时学分</li>
			</ul>
			<div id="container" style="height: 462px;">
				<div id="content_div">
						<div class="tabContent">
							<div class="tab_title">
								<span class="tab_title_text">基本信息<span class="bt-red">*</span></span>
								<div class="t_btn">
									<img src="${ctx}/static/images/tbtn_shrink.png" border="0" shrinkid="div0_div" class="shrink" style="margin-left: 4px;">
								</div>
							</div>
							<div id="div0_div">
								<table class="table table-condensed table-bordered table-hover" style="width:100%;margin-top:-1px;">
									<tbody>
				<tr>
                    <td class="control-label">
                        <label for="f_teacher_name" >姓名：</label>
                    </td>
                    <td><span>${entity.name }</span></td>
                    <td rowspan="8" colspan="2"> 
                        <img width="154" height="189" id="tch_img" src='<c:if test="${not empty entity.path}">${tchImgurl}</c:if><c:if test="${empty entity.path}">${ctx}/static/images/default_img.jpg</c:if>'/>
                    </td>
                </tr>
                <tr>
                     <td class="control-label">
                        <label for="f_teacher_oldName" >曾用名：</label>
                     </td>
                     <td>${entity.oldName }</td>
                 </tr>
                 <tr>
                     <td  class="control-label">
                         <label for="f_teacher_gender" >性别：</label>
                     </td>
                     <td>${entity.genderName }</td>
                 </tr>
                 <tr>
                     <td  class="control-label">
                         <label for="f_teacher_tchWorkNum">教职工号：</label>
                     </td>
                     <td>${entity.tchWorkNum }</td>
                 </tr>
                 <tr>
                     <td class="control-label">
                         <label for="f_teacher_nationality">国籍/地区：</label>
                     </td>
                     <td>${entity.nationalityName }</td>
                 </tr>
                 <tr>
                     <td class="control-label">
                         <label for="f_teacher_paperType">身份证件类型：</label>
                     </td>
                     <td>${entity.paperTypeName }</td>
                 </tr>
                 <tr>
                     <td class="control-label">
                         <label for="f_teacher_idCard">身份证件号：</label>
                     </td>
                     <td>${entity.idCard }</td>
                 </tr>
                 <tr>
                     <td class="control-label">
                         <label for="f_teacher_birthday">出生日期：</label>
                     </td>
                     <td><fmt:formatDate value="${entity.birthday }" pattern="yyyy-MM-dd"/></td>
                 </tr>
                 <tr >
                     <td class="control-label">
                        <label for="f_teacher_nativer">籍贯：</label>
                     </td>
                     <td>${entity.nativer }</td>
                      <td class="control-label">
                        <label for="f_teacher_birthPlace">出生地：</label>
                     </td>
                     <td>${entity.birthPlace }</td>
                 </tr>
                 <tr>
                     <td class="control-label">
                         <label for="f_teacher_nation" >民族：</label>
                     </td>
                     <td>${entity.nationName }</td>
                     <td class="control-label">
                     	<label for="f_teacher_politicType">政治面貌：</label>
                     </td>
                     <td>${entity.politicTypeName }</td>
                 </tr>
                 <tr>
                 	<td class="control-label">
                        <label for="f_teacher_marry_ztree"> 婚姻状况：</label>
                    </td>
                    <td>${entity.marryName }</td>
                    <td class="control-label">
                     	<label for="f_teacher_health">健康状况：</label>
                     </td>
                     <td>${entity.healthName }</td>
                 </tr>
                  <tr>
                     <td class="control-label">
                         <label for="f_teacher_workDay">参加工作年月：</label>
                     </td>
                     <td><fmt:formatDate value="${entity.workDay }" pattern="yyyy-MM-dd"/></td>
                     <td  class="control-label">
                         <label for="f_teacher_joinSchoolDay">进本校年月：</label>
                     </td>
                     <td><fmt:formatDate value="${entity.joinSchoolDay }" pattern="yyyy-MM-dd"/></td>
                 </tr>
                 <tr>
                 	<td id="f_teacher_workerFrom1_td" class="control-label">
                        <label for="f_teacher_workerFrom1_ztree">教职工来源：</label>
                    </td>
                    <td id="f_teacher_workerFrom1_td_val">${entity.workerFrom1Name }</td>
                    <td id="f_teacher_workerFrom2_td" style="display:none" class="control-label">
                     	<label for="f_teacher_workerFrom2_ztree">教职工来源：</label>
                    </td>
                    <td id="f_teacher_workerFrom2_td_val" style="display:none" >${entity.workerFrom2Name }</td>
                    
                    <td id="f_teacher_workerType1_td" class="control-label">
                        <label for="f_teacher_workerType1_ztree">教职工类别：</label>
                    </td>
                    <td id="f_teacher_workerType1_td_val">${entity.workerType1Name }</td>
                    
                    <td id="f_teacher_workerType2_td" style="display:none" class="control-label">
                     	<label for="f_teacher_workerType2_ztree">教职工类别：</label>
                    </td>
                    <td id="f_teacher_workerType2_td_val" style="display:none" >${entity.workerType2Name }</td>
                 	<td id="f_teacher_workerType3_td" style="display:none"  class="control-label">
                        <label for="f_teacher_workerType3_ztree">教职工类别：</label>
                    </td>
                    <td id="f_teacher_workerType3_td_val" style="display:none">${entity.workerType3Name }</td>
                    <td id="f_teacher_workerType4_td" style="display:none"  class="control-label">
                        <label for="f_teacher_workerType4_ztree">教职工类别：</label>
                    </td>
                    <td id="f_teacher_workerType4_td_val" style="display:none">${entity.workerType4Name }</td>
                 </tr>
                  <tr>
                     <td class="control-label">
                         <label for="f_teacher_atSchool">是否在编：</label>
                     </td>
                     <td>${entity.atSchoolName }</td>
                     <td class="control-label">
                         <label for="f_teacher_usePersonType">用人形式：</label>
                     </td>
                     <td>${entity.usePersonTypeName }</td>
                 </tr>
                 <tr>
                 	<td class="control-label">
                         <label for="f_teacher_signContract">签订合同情况：</label>
                     </td>
                     <td>${entity.signContractName }</td>
                     <td id="f_teacher_isQrz_td" class="control-label">
                         <label for="f_teacher_isQrz">是否全日制师范类专业毕业：</label>
                     </td>
                     <td id="f_teacher_isQrz_td_val" >${entity.isQrzName }</td>
                     <td id="f_teacher_isDoubleTch_td" style="display:none" class="control-label">
                        <label for="f_teacher_isDoubleTch">是否“双师型”教师：</label>
                     </td>
                     <td id="f_teacher_isDoubleTch_td_val" style="display:none">${entity.isDoubleTchName }</td>
                 </tr>
                 <tr>
                 	 <td class="control-label">
                         <label>最高学历：</label>
                     </td>
                     <td>${studyExpEntity.degreeName }</td>
                     <td class="control-label">
                         <label >获得最高学历的院校或机构：</label>
                     </td>
                     <td>${studyExpEntity.univ }</td>
                 </tr>
                 <tr>
                 	 <td class="control-label">
                         <label>最高学位层次：</label>
                     </td>
                     <td>${studyExpEntity.academicDegreeN }</td>
                     <td class="control-label">
                         <label >最高学位名称：</label>
                     </td>
                     <td>${studyExpEntity.academicDegreeNameN }</td>
                 </tr>
                 <tr>
                 	<td class="control-label">
                         <label>获得最高学位的院校或机构：</label>
                     </td>
                     <td>${studyExpEntity.academicUnit }</td>
                     <td class="control-label">
                         <label >现任岗位类别：</label>
                     </td>
                     <td>${postEngageEntity.postTypeName }</td>
                 </tr>
                 <tr>
                 	 <td class="control-label">
                         <label>现任岗位等级：</label>
                     </td>
                     <td>${postEngageEntity.postLevelName }</td>
                     <td class="control-label">
                         <label >现任专业技术职务：</label>
                     </td>
                     <td>${professEntity.professionDutyName }</td>
                 </tr>
                 <tr>
                 	<td id="f_teacher_isTjpx_td" style="display:none" class="control-label">
                        <label for="f_teacher_isTjpx" >是否受过特教专业培养培训：</label>
                     </td>
                     <td id="f_teacher_isTjpx_td_val" style="display:none">${entity.isTjpxName }</td>
                     <td id="f_teacher_isTszs_td"  style="display:none" class="control-label">
                         <label for="f_teacher_isTszs">是否有特殊教育从业证书：</label>
                     </td>
                     <td id="f_teacher_isTszs_td_val"  style="display:none">${entity.isTszsName }</td>
                 </tr>
                 <tr>
                 	<td class="control-label">
                         <label for="f_teacher_computerAbility">信息技术应用能力：</label>
                     </td>
                     <td>${entity.computerAbilityName }</td>
                     <td id="f_teacher_isMian_td"  class="control-label">
                         <label for="f_teacher_isMian">是否属于免费（公费）师范生：</label>
                     </td>
                     <td id="f_teacher_isMian_td_val">${entity.isMianName }</td>
                     <td id="f_teacher_isProfessCard_td" style="display:none" class="control-label">
                         <label for="f_teacher_isProfessCard">是否具备职业技能等级证书：</label>
                     </td>
                     <td id="f_teacher_isProfessCard_td_val" style="display:none">${entity.isProfessCardName }</td>
                 </tr>
                 <tr>
                 	<td id="f_teacher_isJoinBase_ztree_td" class="control-label">
                        <label for="f_teacher_isJoinBase_ztree" class="control-label x180">是否参加基层服务项目：</label>
                     </td>
                     <td id="f_teacher_isJoinBase_ztree_td_val">${entity.isJoinBaseName }</td>
                     <td id="f_teacher_joinBaseStart_td"  class="control-label">
                     	<label for="f_teacher_joinBaseStart" class="control-label x180">参加基层服务项目起始年月：</label>
                     </td>
                     <td id="f_teacher_joinBaseStart_td_val"><fmt:formatDate value="${entity.joinBaseStart }" pattern="yyyy-MM-dd"/></td>
                 </tr>
                 <tr>
                     <td id="f_teacher_joinBaseEnd_td" class="control-label">
                     	<label for="f_teacher_joinBaseEnd">参加基层服务项目结束年月：</label>
                     </td>
                     <td id="f_teacher_joinBaseEnd_td_val"><fmt:formatDate value="${entity.joinBaseEnd }" pattern="yyyy-MM-dd"/></td>
                     <td class="control-label">
                        <label for="f_teacher_isTj">是否是特级教师：</label>
                     </td>
                     <td>${entity.isTjName }</td>
                     <td id="f_teacher_workDateTimer_td" style="display: none" class="control-label">
                         <label for="f_teacher_workDateTimer">企业工作(实践)时长：</label>
                     </td>
                     <td id="f_teacher_workDateTimer_td_val" style="display: none">${entity.workDateTimerName }</td>
                 </tr>
                 <tr>
                     <td id="f_teacher_isTownUpBone_td"  class="control-label">
                     	 <label for="f_teacher_isTownUpBone">是否县级及以上骨干教师：</label>
                     </td>
                     <td id="f_teacher_isTownUpBone_td_val"> ${entity.isTownUpBoneName }</td>
                     <td id="f_teacher_isHealth_td" class="control-label">
                        <label for="f_teacher_isHealth">是否心理健康教育教师：</label>
                     </td>
                     <td id="f_teacher_isHealth_td_val">${entity.isHealthName }</td>
                 </tr>
                 <tr>
                	 <td id="f_teacher_isPreTch_td" style="display: none" class="control-label">
                         <label for="f_teacher_isPreTch">是否全日制学前教育专业毕业：</label>
                     </td>
                     <td id="f_teacher_isPreTch_td_val" style="display: none">${entity.isPreTchName }</td>
                     <td id="f_teacher_isPreTrain_td" style="display: none" class="control-label">
                         <label for="f_teacher_isPreTrain">是否受过学前教育专业培养培训：</label>
                     </td>
                     <td id="f_teacher_isPreTrain_td_val" style="display: none">${entity.isPreTrainName }</td>
                 </tr>
                 <tr>
                     <td class="control-label">
                     	 <label for="f_teacher_personStatus">人员状态：</label>
                     </td>
                     <td>${entity.personStatusName }</td>
                     <td id="f_teacher_specStartDate_td" style="display: none" class="control-label">
                     	 <label for="f_teacher_specStartDate">从事特教起始年月：</label>
                     </td>
                     <td id="f_teacher_specStartDate_td_val" style="display: none"><fmt:formatDate value="${entity.specStartDate }" pattern="yyyy-MM-dd"/></td>
                  </tr>
									</tbody>
								</table>
							</div>
						</div>

						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">学习经历<span class="bt-red">*</span></span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div1_div" class="shrink" style="margin-left:4px;">
		                        </div> 
		                    </div>
							<div id="div1_div" class="contentdiv"></div>
						<%--<div id="div1_div" class="contentdiv" data-url="${ctx}/core/basic/teacher/studyExp/teacherStudyExpList/${entity.id}" data-type="POST" data-toggle="autoajaxload">
		                    </div>--%>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">工作经历<span class="bt-red">*</span></span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div2_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
							<div id="div2_div" class="contentdiv tab-pane-my bjui-layout"></div>
		                 <%--   <div id="div2_div" class="contentdiv" data-url="${ctx}/core/basic/teacher/workExp/teacherWorkExpList/${entity.id}" data-type="POST" data-toggle="autoajaxload">
                       		</div>--%>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">岗位聘任<span class="bt-red">*</span></span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div3_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div3_div" class="contentdiv tab-pane-my bjui-layout">
                       		 </div>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">专业技术职务聘任<span class="bt-red">*</span></span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div4_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div4_div" class="contentdiv tab-pane-my bjui-layout">
                       		 </div>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">基本待遇<span class="bt-red">*</span></span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div5_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div5_div" class="contentdiv tab-pane-my bjui-layout">
                       		 </div>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">年度考核<span class="bt-red">*</span></span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div6_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div6_div" class="contentdiv tab-pane-my bjui-layout">
                       		 </div>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">教师资格<span class="bt-red">*</span></span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div7_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div7_div" class="contentdiv tab-pane-my bjui-layout">
                       		 </div>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">师德信息<span class="bt-red">*</span></span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div8_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div8_div" class="contentdiv tab-pane-my bjui-layout">
                       		 </div>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">教育教学<span class="bt-red">*</span></span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div9_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div9_div" class="contentdiv tab-pane-my bjui-layout">
                       		 </div>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">入选人才项目</span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div10_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div10_div" class="contentdiv tab-pane-my bjui-layout">
                       		 </div>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">国内培训<span class="bt-red">*</span></span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div11_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div11_div" class="contentdiv">
                       		 </div>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">海外研修(访学)</span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div12_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div12_div" class="contentdiv tab-pane-my bjui-layout">
                       		 </div>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">技能及证书<span class="bt-red">*</span></span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div13_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div13_div" class="contentdiv tab-pane-my bjui-layout">
                       		 </div>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">交流轮岗</span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div14_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div14_div" class="contentdiv tab-pane-my bjui-layout">
                       		 </div>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">联系方式</span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div15_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div15_div" class="contentdiv tab-pane-my bjui-layout">
                       		 </div>
						</div>
						<div class="tabContent">
							<div class="tab_title">
		                        <span class="tab_title_text">学时学分</span>
		                        <div class="t_btn">
		                            <img src="${ctx}/static/images/tbtn_spread.png" border="0" shrinkid="div16_div" class="shrink" style="margin-left:4px;">
		                        </div>
		                    </div>
		                    <div id="div16_div" class="contentdiv tab-pane-my bjui-layout">
                       		 </div>
						</div>
			</div>
		</div>
	</div>
</div>
<div class="footer">
	<span></span>
</div>
</div>