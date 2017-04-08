<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushScoreTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.score_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/score/myScoreList'});
		$(this).dialog('closeCurrent');
	}
}

$('#f_score_pno').on('keyup', function(e) {
	var pno = $('#f_score_pno').val();
	var year = $('#f_score_year').val();
	var level = $('#f_score_level').val();

  	 //根据pno联动
  	 $.ajax({ 
           type: "POST", 
           url: "${ctx}/core/train/project/base/getProjects?pno="+pno+'&year='+year+'&level='+level,
           dataType : "json",
           success: function(data) {
               var str = "";
               if(data){
                   $(data).each(function(n){
                       str += "<option value='"+this.id+"'>" + this.name+"</option>";
                   });
               }
               $("#f_score_pid").html("<option value=''>--请选择--</option>");
               if(str!=""){
                   $("#f_score_pid").append(str);  
               }
               $('#f_score_pid').selectpicker('refresh');
           },
           error :  function(){
           	  $(this).alertmsg('error', '系统错误！');
           }
       }); 
		   
});

$('#f_score_year').on('change', function(e) {
	var pno = $('#f_score_pno').val();
	var year = $('#f_score_year').val();
	var level = $('#f_score_level').val();
	
   	 //根据pno联动
   	 $.ajax({ 
            type: "POST", 
            url: "${ctx}/core/train/project/base/getProjects?pno="+pno+'&year='+year+'&level='+level,
            dataType : "json",
            success: function(data) {
                var str = "";
                if(data){
                    $(data).each(function(n){
                        str += "<option value='"+this.id+"'>" + this.name+"</option>";
                    });
                }
                $("#f_score_pid").html("<option value=''>--请选择--</option>");
                if(str!=""){
                    $("#f_score_pid").append(str);  
                }
                $('#f_score_pid').selectpicker('refresh');
            },
            error :  function(){
            	  $(this).alertmsg('error', '系统错误！');
            }
        }); 
		  
});

$('#f_score_level').on('change', function(e) {
	var pno = $('#f_score_pno').val();
	var year = $('#f_score_year').val();
	var level = $('#f_score_level').val();
   	 //根据pno联动
   	 $.ajax({ 
            type: "POST", 
            url: "${ctx}/core/train/project/base/getProjects?pno="+pno+'&year='+year+'&level='+level,
            dataType : "json",
            success: function(data) {
                var str = "";
                if(data){
                    $(data).each(function(n){
                        str += "<option value='"+this.id+"'>" + this.name+"</option>";
                    });
                }
                $("#f_score_pid").html("<option value=''>--请选择--</option>");
                if(str!=""){
                    $("#f_score_pid").append(str);  
                }
                $('#f_score_pid').selectpicker('refresh');
            },
            error :  function(){
            	  $(this).alertmsg('error', '系统错误！');
            }
        }); 
});

$('#clear_score_project_btn').on('click',function(e){
	$('#f_score_pno').val("");
	$('#f_score_year').val("");
	$('#f_score_level').val("");
})

</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/score/saveScore8" id="f_score_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushScoreTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="scoreType" value="8">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
        	<thead>
        		<th>申报信息</th>
        	</thead>
            <tbody>
				 <tr>
                    <td>
                        <label for="f_score_type" class="control-label x100">学时类型：</label>
                        <input name="type" type="hidden" id="f_score_type" value="1">
                       	 非集中培训
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_pid" class="control-label x100"><span class="red">*</span>项目名称：</label>
                        <select id="f_score_pid" name="pid"  data-rule="required"  data-toggle="selectpicker"  data-width="200px" data-live-search="true">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${projects }" var="item">
			               		<option  value="${item.id }" <c:if test="${item.id==entity.pid}">selected</c:if>>${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
                 <tr>
                    <td>
                        <label for="f_score_score" class="control-label x100"><span class="red">*</span>所获学时：</label>
                        <input type="text" name="score" id="f_score_score" value="${entity.score }" data-rule="required;digits" maxlength="3" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
         <div class="bjui-searchBar">
	        <div class="alert alert-info search-inline"><i class="fa fa-info-circle"></i>培训电子档案(双击行可编辑)</div>&nbsp;
	        <button type="button" class="btn-blue" data-toggle="tableditadd" data-target="#sdetail_edit" data-num="1" data-icon="plus-circle">添加</button>&nbsp;
	    </div>
        <table id="sdetail_edit" class="table table-bordered table-hover table-striped table-top"  style="word-break:break-all; word-wrap:break-word;" 
        	 data-toggle="tabledit" data-initnum="0" data-single-noindex="true">
            <thead>
                <tr data-idname="sdetail[#index#].id">
                	<th title="id" style="display:none"><input type="hidden" name="sdetail[#index#].id" rows="1"></th>
                    <th title="学习课程">
                    	<textarea name="sdetail[#index#].courseName" data-toggle="autoheight" data-rule="required" rows="1"></textarea>
                    </th>
                    <th title="课程学时">
                    	<textarea name="sdetail[#index#].hour" data-toggle="autoheight" data-rule="required;digits" rows="1"></textarea>
                    </th>
                    <th title="课程授课专家">
                    	<textarea name="sdetail[#index#].experter" data-toggle="autoheight" rows="1"></textarea>
                    </th>
                    <th title="" data-addtool="true" width="100">
                        <a href="${ctx }/core/basic/teacher/assess/deleteById/assess[#index#].id" class="btn btn-red row-del" data-confirm-msg="确定要删除该行信息吗？">删</a>
                    </th>
                </tr>
            </thead>
            <tbody>
            	 <c:forEach items="${sdetails }" var="item">
            	 	<tr data-id="${item.id }">
            	 		<td title="id" style="display:none">${item.id}</td>
	                    <td>${item.courseName }</td>
	                    <td>${item.hour }</td>
	                    <td>${item.experter }</td>
	                    <td data-noedit="true">
	                        <a href="${ctx }/core/score/mine/detail/deleteById/${item.id }" class="btn btn-red row-del" data-confirm-msg="确定要删除该行信息吗？">删</a>
	                    </td>
                	</tr>
            	 </c:forEach>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="Score:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>