<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">

window.objTreeLookMenu = {
	selectedId : undefined,
	ztreeClick : function(event, treeId, treeNode) {
	    event.preventDefault();
	    objMenu.selectedId = treeNode.id;
	    $("#f_menu_pid").val(treeNode.id);
	    $("#f_menu_pname").val(treeNode.name);
	    if(treeNode.id){
	    	$(this).dialog('closeCurrent');
		}
	},
	changeZtree: function(initExpandAll) {
	    var $panel = $('#div_sys_menu_ztreelookup');
	    var $tree  = $('#system-menu-menu-ztreelookup');
	    var $span  = $('#sys-menu-ztreelookup-span');
	    var $i  = $('#sys-menu-ztreelookup-i');
	    var op     = $tree.data();
	    var expand = true;
	    if(initExpandAll===1){
	    	 $tree.data('expandAll', expand);
	    }else{
	    	expand = op.expandAll ? false : true;
	        $tree.data('expandAll', expand);
	    }
       	if(expand){
       		$span.text('收缩全部');
       		$i.removeClass('fa-plus-square-o');
       		$i.addClass('fa-minus-square-o');
       	}else{
       		$span.text('展开全部');
       		$i.removeClass('fa-minus-square-o');
       		$i.addClass('fa-plus-square-o');
       	}
       	$panel.initui();
	}
}

//初始化树展开
objTreeLookMenu.changeZtree(1);

</script>
<div class="bjui-pageHeader">
		<button class="btn btn-blue" onclick="objTreeLookMenu.changeZtree();">
			<i id="sys-menu-ztreelookup-i" class="fa fa-plus-square-o"></i><span id="sys-menu-ztreelookup-span">展开全部</span>
		</button>&nbsp;
</div>
<div class="bjui-pageContent" id="div_sys_menu_ztreelookup">
	<ul id="system-menu-menu-ztreelookup" class="ztree" data-toggle="ztree" 
      data-options="{onClick: 'objTreeLookMenu.ztreeClick'}" >
       <c:forEach items="${menuTreelookup }" var="menu" varStatus="index">
       	<li data-id="${menu.id }" 
       		<c:if test="${not empty menu.pid }">data-pid="${menu.pid }"</c:if>  
       		<c:if test="${not empty menu.url }">data-url="${menu.url }"</c:if> 
       		<c:if test="${not empty menu.tabid }">data-tabid="${menu.tabid }"</c:if> 
       		<c:if test="${not empty menu.faicon }">data-faicon="${menu.faicon }"</c:if> 
       	>${menu.name }</li>
       </c:forEach>
</ul>
</div>

	

