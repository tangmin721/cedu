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
});

//$('#f_score_score').on('change',function(){
//    var scoreValue = $('#f_score_score').val();
//    scoreValue = $.trim(scoreValue);
//    if(scoreValue){
//        alert(scoreValue);
//        $('#result_15').fadeIn(300).delay(2000).fadeOut(500);
//    }
//});
$('#f_score_pid').on('change', function(e) {
    $('#f_score_score').val("");
    $('#f_score_form').isValid(function(v) {
    });
});


</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/score/saveScore" id="f_score_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushScoreTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="scoreType" value="1">
         <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
         	<thead>
        		<th>项目过滤</th>
        	</thead>
         	<tbody>
        		<tr>
                    <td>
                        <label for="f_score_pno" class="control-label x100">项目编号：</label>
                        <input type="text" name="pno" id="f_score_pno"  maxlength="20" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_year" class="control-label x100">项目年度：</label>
                        <select id="f_score_year" name="year"   data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${years }" var="item">
			               		<option  value="${item.id }">${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_score_level" class="control-label x100">培训等级：</label>
                         <select id="f_score_level" name="level"   data-toggle="selectpicker"  data-width="200px">
			               	<option value="">--请选择--</option>
			               	<c:forEach items="${trainLevels }" var="item">
			               		<option  value="${item.id }" >${item.name }</option>
			               	</c:forEach>
						</select>&nbsp;
						<!--   <button type="button" class="btn btn-blue" data-icon="undo" id="clear_score_project_btn">清空查询</button>&nbsp;-->
                    </td>
                </tr>
             <tbody>
        </table>
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
        	<thead>
        		<th>申报信息</th>
        	</thead>
            <tbody>
				 <tr>
                    <td>
                        <label for="f_score_type" class="control-label x100">学时类型：</label>
                        <input name="type" type="hidden" id="f_score_type" value="0">
                       	 集中培训
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
                        <input type="text" name="score" id="f_score_score" value="${entity.score }" data-rule="required;digits;remote[post:${ctx}/core/score/checkScore, id, pid, tid, scoreType, version, type, score]" data-ok="学时有效" maxlength="3" size="20">
                    </td>
                </tr>
                 <tr>
                     <td>
                         <label><span style="color:#ff3700;font-weight: normal;"><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申报说明：&nbsp;&nbsp;单个项目登记学时不得超过40学时，一个继续教育周期内累计登记不能超过100学时。</span></label>
                     </td>
                 </tr>
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