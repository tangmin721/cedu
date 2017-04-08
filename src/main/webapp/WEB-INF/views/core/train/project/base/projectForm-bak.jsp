<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushProjectTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.project_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/train/project/base/projectList'});
		$(this).dialog('closeCurrent');
	}
}

function ratio(){
	var ratioScore = $("#f_project_ratioScore").val();
	var ratioHour = $("#f_project_ratioHour").val();
	var score = $("#f_project_score").val();
	var hour = $("#f_project_hour").val();
	//alert("ratioScore="+ratioScore+",ratioHour="+ratioHour+",score="+score+",hour="+hour);
	if(ratioScore && ratioHour){
		if(score){
			$("#f_project_hour").val(score*ratioHour/ratioScore);
		}else{
			if(hour){
				$("#f_project_score").val(hour*ratioScore/ratioScore);
			}
		}
	}
}

$("#f_project_ratioScore").change(function(){
	ratio();
});

$("#f_project_ratioHour").change(function(){
	ratio();
});

$("#f_project_score").change(function(){
	ratio();
});

$("#f_project_hour").change(function(){
	ratio();
});


$("#f_project_stage").change(function(){
    var stage = $("#f_project_stage option:selected").val();
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
                $("#f_project_course").html("<option value=''>--请选择--</option>");
                if(str!=""){
                    $("#f_project_course").append(str);  
                }
                $('#f_project_course').selectpicker('refresh');
            },
            error :  function(){
            	  $(this).alertmsg('error', '系统错误！');
            }
        }); 
    }else{
   	 $("#f_project_course").html("<option value=''>--请选择--</option>");
   	 $('#f_project_course').selectpicker('refresh');
   	 
   	 $("#f_project_grade").html("<option value=''>--请选择--</option>");
   	 $('#f_project_grade').selectpicker('refresh');
    }
   	 
});

$('#f_project_confirmFlag').bootstrapSwitch();
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/train/project/base/saveProject" id="f_project_form" data-toggle="validate" data-alertmsg="false" data-callback="refushProjectTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="optType" value="${optType}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td  colspan="2" id="f_project_area_td">
                        <label for="f_project_area" class="control-label x100">省市县校：</label>
                        <shiro:hasAnyRoles name="SUP_ADMIN,SYS_ADMIN,PROVINCE_ADMIN,CITY_ADMIN,TOWN_ADMIN,SCHOOL_ADMIN">
                        	<div <c:if test="${sessionScope.currentUser.province==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.province!=0}">  style="display: none;" </c:if>>
			            	 <select id="project_form_province" name="province" data-toggle="selectpicker" 
			             	data-nextselect="#project_form_city"  data-refurl="${ctx}/system/area/city/citys?province={value}" data-size="20" data-width="140px">
								<option value="">--省市--</option>
				               	<c:forEach items="${provinces }" var="item">
				                     		<option value="${item.provinceNo }" <c:if test="${item.provinceNo==entity.province}">selected</c:if>>${item.provinceName }</option>
				                </c:forEach>
			                </select>
			                </div>
			                 <div <c:if test="${sessionScope.currentUser.city==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.city!=0}">  style="display: none;" </c:if>>
			        		<select name="city" id="project_form_city" data-toggle="selectpicker" 
			        			 data-nextselect="#project_form_town" 
			       				 data-refurl="${ctx}/system/area/town/towns?city={value}"
			        			   data-size="20" data-width="140px">
			        			 <option value="">--城市--</option>
			        			 <c:forEach items="${citys }" var="item">
			                     		<option value="${item.cityNo }" <c:if test="${item.cityNo==entity.city}">selected</c:if>>${item.cityName }</option>
			                	</c:forEach>
			            	</select>
			            	</div>
			            	<div <c:if test="${sessionScope.currentUser.town==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.town!=0}">  style="display: none;" </c:if>>
				            <select name="town" id="project_form_town" data-toggle="selectpicker" 
				            	data-nextselect="#project_form_school" 
				            	data-refurl="${ctx}/core/basic/school/schools?town={value}"
				            	 data-size="20" data-width="140px">
				            	<option value="">--区县--</option>
			        			 <c:forEach items="${towns }" var="item">
			                     		<option value="${item.townNo }" <c:if test="${item.townNo==entity.town}">selected</c:if>>${item.townName }</option>
			                	</c:forEach>
				            </select>
				            </div>
				             <div <c:if test="${sessionScope.currentUser.school==0}">style="display: inline;"</c:if><c:if test="${sessionScope.currentUser.school!=0}">  style="display: none;" </c:if>>
				            <select name="school" id="project_form_school" data-toggle="selectpicker"  <c:if test="${sessionScope.currentUser.school!=0}">style="display: none;"</c:if>
				            	 data-size="20" data-width="140px" data-live-search="true">
				            	<option value="">--学校--</option>
			        			 <c:forEach items="${schools }" var="item">
			                     		<option value="${item.id }" <c:if test="${item.id==entity.school}">selected</c:if>>${item.name }</option>
			                	</c:forEach>
				            </select>
				            </div>
			            </shiro:hasAnyRoles>
                    </td>
                </tr>
                </tr>
				 <tr>
                     <td colspan="2">
                        <label for="f_project_name" class="control-label x100"><span class="red">*</span>项目名称：</label>
                        <input type="text" name="name" id="f_project_name" value="${entity.name }" data-rule="required" maxlength="62" size="62">
                    </td>
                </tr>
                <tr>
                     <td colspan="2">
                        <label for="f_project_pno" class="control-label x100">项目编号：</label>
                        <input type="text" name="pno" id="f_project_pno" value="${entity.pno }" disabled maxlength="62" size="62">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_stage" class="control-label x100"><span class="red">*</span>学段：</label>
                        <select id="f_project_stage" name="stage"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${stages }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.stage}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                     <td>
                        <label for="f_project_course" class="control-label x100"><span class="red">*</span>学科：</label>
                        <select id="f_project_course" name="course"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${courses }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.course}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_project_trainType" class="control-label x100"><span class="red">*</span>培训类型：</label>
                        <select id="f_project_trainType" name="trainType"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${trainTypes }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.trainType}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_schoolYear" class="control-label x100"><span class="red">*</span>学年：</label>
                        <select id="f_project_schoolYear" name="schoolYear"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${schoolYears }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.schoolYear}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                     <td>
                        <label for="f_project_term" class="control-label x100"><span class="red">*</span>学期：</label>
                        <select id="f_project_term" name="term"  data-rule="required"  data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${terms }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.term}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                     <td colspan="2">
                        <label for="f_project_ratioScore" class="control-label x100"><span class="red">*</span>学分：学时</label>
                        <input type="text" name="ratioScore" id="f_project_ratioScore" value='<fmt:formatNumber value="${entity.ratioScore }" pattern="#.##"/>' data-rule="required" maxlength="9" size="9"> : 
                         <input type="text" name="ratioHour" id="f_project_ratioHour" value='<fmt:formatNumber value="${entity.ratioHour }" pattern="#.##"/>' data-rule="required" maxlength="9" size="9">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_score" class="control-label x100"><span class="red">*</span>学分：</label>
                        <input type="text" name="score" id="f_project_score" value='<fmt:formatNumber value="${entity.score }" pattern="#.##"/>' data-rule="required" maxlength="20" size="20">
                    </td>
                     <td>
                        <label for="f_project_hour" class="control-label x100"><span class="red">*</span>学时：</label>
                        <input type="text" name="hour" id="f_project_hour" value='<fmt:formatNumber value="${entity.hour }" pattern="#.##"/>' data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_project_startDate" class="control-label x100"><span class="red">*</span>开始时间：</label>
                        <input type="text" name="startDate" id="f_project_startDate" value='<fmt:formatDate value="${entity.startDate }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="required;date" size="20">
                    </td>
                    <td>
                        <label for="f_project_endDate" class="control-label x100"><span class="red">*</span>结束时间：</label>
                        <input type="text" name="endDate" id="f_project_endDate" value='<fmt:formatDate value="${entity.endDate }" pattern="yyyy-MM-dd"/>' data-toggle="datepicker" data-rule="required;date" size="20">
                    </td>
                </tr>
				 <tr>
                      <td colspan="2">
                        <label for="f_project_trainTageter" class="control-label x100"><span class="red">*</span>培训对象：</label>
                        <input type="text" name="trainTageter" id="f_project_trainTageter" value="${entity.trainTageter }" data-rule="required" maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_project_checkTypes" class="control-label x100">考核方式：</label>
                        <c:forEach items="${checkTypes }" var="item" varStatus="index">
                       		 <input type="checkbox" name="checkType" value="${item.id }" id="f_project_checkTypes" data-toggle="icheck" data-label="${item.name }" <c:if test="${checkTypeIdList.contains(item.id) }">checked</c:if> >
			             </c:forEach>
                    </td>
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_project_description" class="control-label x100">项目描述：</label>
                        <textarea name="description" id="f_project_description" data-toggle="autoheight" cols="62" rows="2" maxlength="400">${entity.description}</textarea>
                    </td>
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_project_taget" class="control-label x100">项目目标：</label>
                        <textarea name="taget" id="f_project_taget" data-toggle="autoheight" cols="62" rows="2" maxlength="400">${entity.taget}</textarea>
                    </td>
                </tr>
				 <tr>
                    <td colspan="2">
                        <label for="f_project_content" class="control-label x100">内容简介：</label>
                        <textarea name="content" id="f_project_content" data-toggle="autoheight" cols="62" rows="2" maxlength="400">${entity.content}</textarea>
                    </td>
                </tr>
				 <tr>
                     <td>
                        <label for="f_project_implWay" class="control-label x100"><span class="red">*</span>培训方式：</label>
                        <input type="radio" name="implWay" id="f_project_implWay0" data-toggle="icheck" value="0" data-rule="checked" <c:if test="${entity.implWay==0}">checked</c:if> data-label="集中培训&nbsp;&nbsp;">
                        <input type="radio" name="implWay" id="f_project_implWay1" data-toggle="icheck" value="1" <c:if test="${entity.implWay==1}">checked</c:if> data-label="网络研修">
                        <input type="radio" name="implWay" id="f_project_implWay2" data-toggle="icheck" value="2" <c:if test="${entity.implWay==2}">checked</c:if> data-label="影子学习">
                        <input type="radio" name="implWay" id="f_project_implWay3" data-toggle="icheck" value="3" <c:if test="${entity.implWay==3}">checked</c:if> data-label="在岗实践">
                        <input type="radio" name="implWay" id="f_project_implWay4" data-toggle="icheck" value="4" <c:if test="${entity.implWay==4}">checked</c:if> data-label="混合式学习">
                    </td>
                    <td>
                        <label for="f_project_confirmFlag" class="control-label x100"><span class="red">*</span>确认开关：</label>
                        <input id="f_project_confirmFlag" type="checkbox"  name="confirmFlag"  data-size="mini" data-on-color="success" 
                        	data-off-color="warning" data-on-text="需要确认"  data-off-text="不需要确认" <c:if test="${entity.confirmFlag }">checked</c:if>>
                    </td>
                </tr>
				 <tr> 
                    <td colspan="2">
                        <label for="f_project_memo" class="control-label x100">备注：</label>
                         <textarea name="memo" id="f_project_memo" data-toggle="autoheight" cols="62" rows="2" maxlength="400">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="Project:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>