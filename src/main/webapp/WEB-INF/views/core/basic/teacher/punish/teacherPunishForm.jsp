<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
//删除回调
$('#punish_edit').on('afterdelete.bjui.tabledit', function(e) {
    var $tbody = $(e.relatedTarget)
    
    console.log('你删除了一条数据，还有['+ $tbody.find('> tr').length +']条数据！')
})

//取消，刷新
$('#cancel_punish').off().on('click', function(e) {
	$('#div_tw_punish').bjuiajax('refreshLayout', {
		target:'#div_tw_punish',
		url:"${ctx}/core/basic/teacher/punish/teacherPunishForm/"+wizard_tid
	});
})

//保存回调
function savePunishCb(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('#div_tw_punish').bjuiajax('refreshLayout', {
			target:'#div_tw_punish',
			url:"${ctx}/core/basic/teacher/punish/teacherPunishForm/"+wizard_tid
		});
	}
}
</script>
<div class="bjui-pageHeader">
    <div class="bjui-searchBar">
        <div class="alert alert-info search-inline"><i class="fa fa-info-circle"></i> 双击行可编辑</div>&nbsp;
        <button type="button" class="btn-blue" data-toggle="tableditadd" data-target="#punish_edit" data-num="1" data-icon="plus-circle">添加处分情况</button>&nbsp;
    </div>
</div>
<div class="bjui-pageContent tableContent">
    <form action="${ctx }/core/basic/teacher/punish/saveTeacherPunishs" id="punish_form" class="pageForm" data-toggle="validate" method="post" data-callback="savePunishCb">
        <table id="punish_edit" class="table table-bordered table-hover table-striped table-top"  style="word-break:break-all; word-wrap:break-word;" 
        	 data-toggle="tabledit" data-initnum="0" data-action="${ctx }/core/basic/teacher/punish/saveTeacherPunish" 
        	 data-callback="savePunishCb" data-single-noindex="true">
            <thead>
                <tr data-idname="punish[#index#].id">
                	<th title="tid" style="display:none"><input type="hidden" name="punish[#index#].tid" value="${tid }"></th>
                    <th title="排序"><input type="text" name="punish[#index#].seq" data-rule="required" size="5" ></th>
                    <th title="处分时间"><input type="text" name="punish[#index#].tdate" data-toggle="datepicker" readonly data-rule="required;date"></th>
                    <th title="处分发布单位">
                    	<input type="text" name="punish[#index#].dept" data-rule="required" value="">
                    </th>
                    <th title="处分级别">
                    	<select name="punish[#index#].level" data-toggle="selectpicker" data-rule="required">
                    		<option value="">--请选择--</option>
                    		<c:forEach items="${levels }" var="item">
			               		<option  value="${item.id }">${item.name }</option>
			               	</c:forEach>
                    	</select>
                    </th>
                    <th title="事由">
                    	<textarea name="punish[#index#].memo" data-toggle="autoheight" rows="1"></textarea>
                    </th>
                    <th title="" data-addtool="true" width="100">
                    	<shiro:hasPermission name="TeacherPunish:create">
                        <a href="javascript:;" class="btn btn-green" data-toggle="dosave">保存</a>
                        </shiro:hasPermission>
                         <shiro:hasPermission name="TeacherPunish:delete">
                        <a href="${ctx }/core/basic/teacher/punish/deleteById/punish[#index#].id" class="btn btn-red row-del" data-confirm-msg="确定要删除该行信息吗？">删</a>
                   		</shiro:hasPermission>
                    </th>
                </tr>
            </thead>
            <tbody>
            	 <c:forEach items="${punishs }" var="item">
            	 	<tr data-id="${item.id }">
	            	 	<td style="display:none">${item.tid }</td>
	                    <td>${item.seq }</td>
	                    <td><fmt:formatDate value="${item.tdate }" pattern="yyyy-MM-dd"/></td>
	                    <td>${item.dept }</td>
	                    <td data-val="${item.level }"></td>
	                    <td>${item.memo }</td>
	                    <td data-noedit="true">
	                    	<shiro:hasPermission name="TeacherPunish:create">
	                        <button type="button" class="btn-green" data-toggle="doedit">编辑</button>
	                        </shiro:hasPermission>
	                        <shiro:hasPermission name="TeacherPunish:delete">
	                        <a href="${ctx }/core/basic/teacher/punish/deleteById/${item.id }" class="btn btn-red row-del" data-confirm-msg="确定要删除该行信息吗？">删</a>
	                    	</shiro:hasPermission>
	                    </td>
                	</tr>
            	 </c:forEach>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button id="cancel_punish" type="button" class="btn-red" data-icon="refresh">取消</button></li>
         <shiro:hasPermission name="TeacherPunish:create">
        <li><button type="submit" class="btn-blue" data-icon="save">全部保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>
