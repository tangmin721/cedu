<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
jQuery(document).ready(function($) {
    $('#undo_redo').multiselect();
});

function refushUserRoleForm(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$(this).alertmsg('ok', json.message);
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
	<form action="${ctx}/system/user/saveUserRole" id="ff_user_role_form" data-toggle="validate" data-alertmsg="false" data-callback="refushUserRoleForm">
		<input type="hidden" name="userId" value="${userId}">
	    <div class="row" style="padding:2px;">
	           <div class="col-xs-5">
	           	   <label for="undo_redo" class="control-label x120"><div style="font-size:14px;padding:4px;">已分配角色</div></label>
	               <select name="from[]" id="undo_redo" class="form-control" style="width:200px;height:420px;" size="20" multiple="multiple">
	                   <c:forEach items="${hadRoles }" var="item">
	                          <option value="${item.id }">${item.name }</option>
	                      </c:forEach>
	               </select>
	           </div>
	
	           <div class="col-xs-2" style="padding-top:164px;">
	               <button type="button" id="undo_redo_rightAll" class="btn btn-default btn-block"><i class="glyphicon glyphicon-forward"></i></button>
	               <button type="button" id="undo_redo_rightSelected" class="btn btn-default btn-block"><i class="glyphicon glyphicon-chevron-right"></i></button>
	               <button type="button" id="undo_redo_leftSelected" class="btn btn-default btn-block"><i class="glyphicon glyphicon-chevron-left"></i></button>
	               <button type="button" id="undo_redo_leftAll" class="btn btn-default btn-block"><i class="glyphicon glyphicon-backward"></i></button>
	           </div>
	
	           <div class="col-xs-5" style="padding-left:8px">
	           	   <label for="undo_redo" class="control-label x120"><div style="font-size:14px;padding:4px;">可分配角色</div></label>
	               <select id="undo_redo_to" class="form-control"  style="width:200px;height:420px;" size="20" multiple="multiple">
	                    <c:forEach items="${unHadRoles }" var="item">
	                          <option value="${item.id }">${item.name }</option>
	                      </c:forEach>
	              </select>
	           </div>
	    </div>
	  </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
    </ul>
</div>