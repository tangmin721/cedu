<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">

//删除回调
$('#award_edit').on('afterdelete.bjui.tabledit', function(e) {
    var $tbody = $(e.relatedTarget)
    console.log('你删除了一条数据，还有['+ $tbody.find('> tr').length +']条数据！')
})

//取消，刷新
$('#cancel_award').off().on('click', function(e) {
	$('#div_tw_award').bjuiajax('refreshLayout', {
		target:'#div_tw_award',
		url:"${ctx}/core/basic/teacher/award/teacherAwardForm/"+wizard_tid
	});
})

//保存回调
function saveAwardCb(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('#div_tw_award').bjuiajax('refreshLayout', {
			target:'#div_tw_award',
			url:"${ctx}/core/basic/teacher/award/teacherAwardForm/"+wizard_tid
		});
	}
}
</script>
<div class="bjui-pageHeader">
    <div class="bjui-searchBar">
        <div class="alert alert-info search-inline"><i class="fa fa-info-circle"></i> 双击行可编辑</div>&nbsp;
        <button type="button" class="btn-blue" data-toggle="tableditadd" data-target="#award_edit" data-num="1" data-icon="plus-circle">添加获奖情况</button>&nbsp;
    </div>
</div>
<div class="bjui-pageContent tableContent">
    <form action="${ctx }/core/basic/teacher/award/saveTeacherAwards" method="post" id="award_form" class="pageForm" data-toggle="validate" method="post" data-callback="saveAwardCb">
        <table id="award_edit" class="table table-bordered table-hover table-striped table-top"  style="word-break:break-all; word-wrap:break-word;"  
        		data-toggle="tabledit"  data-initnum="0" 
        		data-action="${ctx }/core/basic/teacher/award/saveTeacherAward" data-callback="saveAwardCb" data-single-noindex="true">
            <thead>
                <tr data-idname="award[#index#].id">
                	<th title="tid" style="display:none"><input type="hidden" name="award[#index#].tid" value="${tid }"></th>
                    <th title="排序"><input type="text" name="award[#index#].seq" data-rule="required" size="5" ></th>
                     <th title="奖项名称">
                    	<input type="text" name="award[#index#].name" data-rule="required" value="">
                    </th>
                    <th title="获奖时间"><input type="text" name="award[#index#].takeDate" data-toggle="datepicker"  data-rule="required;date" readonly></th>
                    <th title="颁奖单位">
                    	<input type="text" name="award[#index#].dept"  value="">
                    </th>
                    <th title="备注">
                    	<textarea name="award[#index#].memo" data-toggle="autoheight" rows="1"></textarea>
                    </th>
                    <th title="" data-addtool="true" width="100">
                    	<shiro:hasPermission name="TeacherAward:create">
                        <a href="javascript:;" class="btn btn-green" data-toggle="dosave">保存</a>
                        </shiro:hasPermission>
                         <shiro:hasPermission name="TeacherAward:delete">
                        <a href="${ctx }/core/basic/teacher/award/deleteById/award[#index#].id" class="btn btn-red row-del" data-confirm-msg="确定要删除该行信息吗？">删</a>
                   		</shiro:hasPermission>
                    </th>
                </tr>
            </thead>
            <tbody>
            	 <c:forEach items="${awards }" var="item">
            	 	<tr data-id="${item.id }">
	            	 	<td style="display:none">${item.tid }</td>
	                    <td>${item.seq }</td>
	                    <td>${item.name }</td>
	                    <td><fmt:formatDate value="${item.takeDate }" pattern="yyyy-MM-dd"/></td>
	                    <td>${item.dept }</td>
	                    <td>${item.memo }</td>
	                    <td data-noedit="true">
	                    	<shiro:hasPermission name="TeacherAward:create">
	                        <button type="button" class="btn-green" data-toggle="doedit">编辑</button>
	                        </shiro:hasPermission>
	                        <shiro:hasPermission name="TeacherAward:delete">
	                        <a href="${ctx }/core/basic/teacher/award/deleteById/${item.id }" class="btn btn-red row-del" data-confirm-msg="确定要删除该行信息吗？">删</a>
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
       <li><button id="cancel_award" type="button" class="btn-red" data-icon="refresh">取消</button></li>
        <shiro:hasPermission name="TeacherAward:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>
