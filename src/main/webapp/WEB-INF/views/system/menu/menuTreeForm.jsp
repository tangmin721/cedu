<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushMenuTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.menu_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/menu/menuList'});
		$("#div_sys_menu_tree").bjuiajax('refreshLayout', {
			target:$('#div_sys_menu_tree'),
			data: {id:"1"}
		});
		$("#div_sys_menu_tree_from").bjuiajax('refreshLayout', {
			target:$('#div_sys_menu_tree_from')
		});
	}
}

$('#ff_menu_active').bootstrapSwitch();
$('#ff_menu_shower').bootstrapSwitch();
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/system/menu/saveMenu" id="ff_menu_form"  method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushMenuTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="pid" value="${entity.pid}" id="ff_menu_pid">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
				 	<td>
                        <label for="ff_menu_seq" class="control-label x180">排序序号：</label>
                        <input type="text" name="seq" id="ff_menu_seq" value="${entity.seq }"  size="20" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                    <td>
                        <label for="ff_menu_pname" class="control-label x180"><span class="red">*</span>父节点：</label>
                    	<input type="text" name="pname" id="ff_menu_pname" size="20" value="${pname }" <c:if test="${entity.pid ==0}">disabled</c:if> <c:if test="${entity.pid !=0}">readonly</c:if> data-toggle="dialog" data-id="menuform1-lookup"
                        	data-url="${ctx }/system/menu/treeFormLookup"  data-width="300" data-height="425" data-mask="true" data-title="选择父节点"/>
                    </td>
                    <td>
                        <label for="ff_menu_name" class="control-label x180"><span class="red">*</span>模块名称：</label>
                        <input type="text" name="name" id="ff_menu_name" value="${entity.name }"  data-rule="required" size="20">
                    </td>
                 </tr>
				 <tr>
                    <td>
                        <label for="ff_menu_modelClzName" class="control-label x180"> 对应模块的类名：</label>
                        <input type="text" name="modelClzName" id="ff_menu_modelClzName" value="${entity.modelClzName }"  size="20">
                    </td>
                    <td>
                        <label for="ff_menu_faicon" class="control-label x180">图标名称：</label>
                        <input type="text" name="faicon" id="ff_menu_faicon" size="20" value="${entity.faicon }"  data-toggle="lookup" 
                        	data-url="${ctx }/system/faicon/faiconList"  data-width="650" data-height="425"  data-title="图标查询"/>
                        <i class="fa fa-${entity.faicon }"></i>
                    </td>
                    <td>
                        <label for="ff_menu_tabid" class="control-label x180">NAVID：</label>
                        <input type="text" name="tabid" id="ff_menu_tabid" value="${entity.tabid }"  size="20">
                    </td>
                 </tr>
				 <tr>
                    <td>
                        <label for="ff_menu_url" class="control-label x180">URL：</label>
                        <input type="text" name="url" id="ff_menu_url" value="${entity.url }"  size="20">
                    </td>
                    <td>
                        <label for="ff_menu_theLast" class="control-label x180">是否是最下一级：</label>
                         <input type="checkbox" name="theLast" id="ff_menu_theLast" data-toggle="icheck" data-label="是" <c:if test="${entity.theLast }">checked</c:if>>
                    </td>
                    <td>
                        <label for="ff_menu_active" class="control-label x180"><span class="red">*</span>是否激活：</label>
						<input id="ff_menu_active" type="checkbox"  name="active"  data-size="mini" data-on-color="success" data-off-color="warning" data-on-text="激活"  data-off-text="未激活" <c:if test="${entity.active }">checked</c:if>>
                    </td>
                 </tr>
				 <tr>
				 	 <td>
                        <label for="ff_menu_shower" class="control-label x180"><span class="red">*</span>是否显示菜单：</label>
						<input id="ff_menu_shower" type="checkbox"  name="shower"  data-size="mini" data-on-color="success" data-off-color="warning" data-on-text="显示"  data-off-text="不显示" <c:if test="${entity.shower }">checked</c:if>>
                    </td>
                    <td colspan="2">
                        <label for="ff_menu_memo" class="control-label x180">备注：</label>
                        <textarea name="memo" id="ff_menu_memo" data-toggle="autoheight" cols="20" rows="1">${entity.memo}</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
    </ul>
</div>