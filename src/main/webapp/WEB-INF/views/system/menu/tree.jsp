<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">

$('#menu-add-root').on('click', function(e) {
	$(this).dialog({id:'menu_form', url:'${ctx}/system/menu/menuForm?pid=1', type:'POST',title:'添加菜单',mask:'true',width:'600',height:'420'});
});

$('#menu-remove-cache').on('click', function(e) {
	$(this).bjuiajax('doAjax', {url:'${ctx}/system/menu/removeMenuCache'});
});

window.objMenu = {
	selectedId : undefined,
	changeZtree: function(initExpandAll) {
	    var $panel = $('#div_sys_menu_tree');
	    var $tree  = $('#system-menu-menu-ztree');
	    var $span  = $('#sys-menu-ztree-span');
	    var $i  = $('#sys-menu-ztree-i');
	    var op     = $tree.data();
	    var expand = true;
	    if(initExpandAll===1){
	    	 $tree.data('expandAll', expand);
	    }else{
	    	expand = op.expandAll ? false : true;
	        $tree.data('expandAll', expand);
	    }
       	if(expand){
       		$span.text('收缩');
       		$i.removeClass('fa-plus-square-o');
       		$i.addClass('fa-minus-square-o');
       	}else{
       		$span.text('展开');
       		$i.removeClass('fa-minus-square-o');
       		$i.addClass('fa-plus-square-o');
       	}
       	$panel.initui();
	}
}

//初始化树展开
objMenu.changeZtree(1);

//单击事件
function ZtreeClick(event, treeId, treeNode) {
    event.preventDefault();
    objMenu.selectedId = treeNode.id;
	$("#div_sys_menu_list").bjuiajax('refreshLayout', {
		target:$('#div_sys_menu_list'),
		data: {pid:treeNode.id}
	});
	
	$("#div_sys_menu_tree_from").bjuiajax('refreshLayout', {
		target:$('#div_sys_menu_tree_from'),
		data: {id:treeNode.id}
	});
}

function MenuZtreeOk(){
	alert(123);
}

</script>
<div style="padding:5px 0 0 5px;">
	<button id="sys-menu-ztree-btn1" class="btn btn-blue" onclick="objMenu.changeZtree();">
		<i id="sys-menu-ztree-i" class="fa fa-plus-square-o"></i><span id="sys-menu-ztree-span">展开</span>
	</button>
	<button id="menu-add-root" class="btn btn-blue" data-icon="plus-circle"><span>顶级节点</span></button>
</div>
<ul id="system-menu-menu-ztree" class="ztree" data-toggle="ztree" 
      data-options="{onClick: 'ZtreeClick',onAsyncSuccess:'MenuZtreeOk'}" >
       <c:forEach items="${menuTree }" var="menu" varStatus="index">
       	<li data-id="${menu.id }" 
       		<c:if test="${not empty menu.pid }">data-pid="${menu.pid }"</c:if>  
       		<c:if test="${not empty menu.url }">data-url="${menu.url }"</c:if> 
       		<c:if test="${not empty menu.tabid }">data-tabid="${menu.tabid }"</c:if> 
       		<c:if test="${not empty menu.faicon }">data-faicon="${menu.faicon }"</c:if> 
       	>${menu.name }</li>
       </c:forEach>
</ul>

