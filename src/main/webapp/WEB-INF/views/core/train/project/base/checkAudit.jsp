<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<script type="text/javascript">
$('#f_project_roster_check').bootstrapSwitch();

$('#passed').on('click',function(){
	var flag = false;
	
	$('#f_check_audit_form').isValid(function(v){
			passflag = v;
		    if(v){
		    	$('#check_audit_status').val(2);
		    	$('#f_check_audit_form').bjuiajax('ajaxForm', {callback:function(json){
		    		console.log(json);
		    		if(json.statusCode!='200'){
		    			$(this).alertmsg('error', json.message);
		    		}else{
		    			$(this).alertmsg('ok', json.message);
		    			$('#check_audit_search').trigger('click');
		    			$(this).dialog('close','project_roster_check_form')
		    		}
		    	}})
		    }
		});
});

$('#unpassed').on('click',function(){
	var flag = false;
	$('#f_check_audit_form').isValid(function(v){
		passflag = v;
	    if(v){
	    	$('#check_audit_status').val(3);
	    	$('#f_check_audit_form').bjuiajax('ajaxForm', {callback:function(json){
	    		console.log(json);
	    		if(json.statusCode!='200'){
	    			$(this).alertmsg('error', json.message);
	    		}else{
	    			$(this).alertmsg('ok', json.message);
	    			$('#check_audit_search').trigger('click');
	    			$(this).dialog('close','project_roster_check_form')
	    		}
	    	}})
	    }
	});
});

</script>

    
<div class="bjui-pageContent">
      <form action="${ctx}/core/train/project/roster/auditSave" id="f_check_audit_form" method="post">
        <input type="hidden" name="id" value="${applyId}">
        <input type="hidden" name="status" value="" id="check_audit_status">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_project_roster_checkDesc" class="control-label x100"><span class="red">*</span>审批意见：</label>
                        <textarea name="checkDesc" id="f_project_roster_checkDesc" data-toggle="autoheight" cols="30" rows="2" maxlength="400" data-rule="required" >${entity.checkDesc}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
    	<c:if test="${status==2 || status==3 }">
         <li><button type="button" id="unpassed" class="btn-red" data-icon="close">审核不通过</button></li>
         <li><button type="button" id="passed" class="btn-green" data-icon="save">审核已通过</button></li>
        </c:if>
        <c:if test="${status==1 }">
         <li><button type="button" id="unpassed" class="btn-danger" data-icon="close">不通过</button></li>
         <li><button type="button" id="passed" class="btn-success" data-icon="save">通过</button></li>
        </c:if>
    </ul>
</div>