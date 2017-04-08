<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">

//选择事件 JSON.stringify(obj)将JSON转为字符串。JSON.parse(string)将字符串转为JSON格式；
function role_S_NodeCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId),
        nodes = zTree.getCheckedNodes(true);
    var formNodes = [];
    for (var i = 0; i < nodes.length; i++) {
    	var cbstr = {};
    	cbstr.id = nodes[i].id;
    	cbstr.perm = nodes[i].perm;
    	formNodes.push(cbstr);
    }
    $('#role_perm_tree_input').val(JSON.stringify(formNodes));
}
//单击事件
function role_S_NodeClick(event, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    zTree.checkNode(treeNode, !treeNode.checked, true, true);
    event.preventDefault();
}

function ztree_returnjson() {
	return ${permTree};
}

function rolePermSaveCb(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$(this).alertmsg('ok', json.message);
		$(this).dialog('closeCurrent');
	}
}
</script>
<div class="bjui-pageContent">
    <div style="padding:15px;">
    	 <form action="${ctx}/system/role/saveRolePerm" id="f_role_perm_tree_form" data-toggle="validate" data-alertmsg="false" data-callback="rolePermSaveCb">
    	 	<input type="hidden" name="ids" id="role_perm_tree_input" value="${ids }" data-toggle="selectztree" size="18" data-tree="#role_perm_tree" >
    	 	<input type="hidden" name="roleId" value="${roleId }">
	    	<ul id="role_perm_tree"  class="ztree" data-toggle="ztree" 
	    		data-expand-all="true" 
	    		data-check-enable="true" 
	    		data-on-check="role_S_NodeCheck"
	    		data-on-click="role_S_NodeClick" 
	    		data-options="{nodes:'ztree_returnjson'}"></ul>
    	</form>
    </div>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
    </ul>
</div>