<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushProjectPersonTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('#project_person_search_btn').trigger('click');
		$(this).dialog('close','project_person_form');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/train/project/person/saveProjectPerson" method="post" id="f_project_person_form" data-toggle="validate" data-alertmsg="false" data-callback="refushProjectPersonTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <input type="hidden" name="tid" value="${entity.tid}">
        <input type="hidden" name="pid" value="${entity.pid}">
        <input type="hidden" name="oid" value="${entity.oid}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label class="control-label x100">学校：</label>
                        ${provinceName} ${cityName} ${townName} ${schoolName}
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label class="control-label x100">姓名：</label>
                        ${name}
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label class="control-label x100">标识码：</label>
                        ${tno}
                    </td>
                </tr>
                 <tr>
                     <td>
                         <label class="control-label x100">身份证：</label>
                         ${idCard}
                     </td>
                 </tr>
				 <tr>
                    <td>
                        <label class="control-label x100"><span class="red">*</span>成绩：</label>
                        <input type="radio" name="pass"  data-toggle="icheck" value="1" data-rule="checked" <c:if test="${entity.pass==1}">checked</c:if> data-label="优秀&nbsp;&nbsp;">
                        <input type="radio" name="pass"  data-toggle="icheck" value="2" <c:if test="${entity.pass==2}">checked</c:if> data-label="合格">
                        <input type="radio" name="pass"  data-toggle="icheck" value="0" <c:if test="${entity.pass==0}">checked</c:if> data-label="不合格">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="ProjectPerson:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>