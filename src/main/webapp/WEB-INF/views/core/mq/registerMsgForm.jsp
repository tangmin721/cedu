<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushRegisterMsgTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.register_msg_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/mq/registerMsgList'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/mq/saveRegisterMsg" id="f_register_msg_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushRegisterMsgTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="f_register_msg_status" class="control-label x100">状态：</label>
                        <input type="text" name="status" id="f_register_msg_status" value="${entity.status }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_type" class="control-label x100">消息类型：</label>
                        <input type="text" name="type" id="f_register_msg_type" value="${entity.type }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_num" class="control-label x100">发送到队列次数：</label>
                        <input type="text" name="num" id="f_register_msg_num" value="${entity.num }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_memo" class="control-label x100">描述：</label>
                        <input type="text" name="memo" id="f_register_msg_memo" value="${entity.memo }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_producerTime" class="control-label x100">发送到队列的时间：</label>
                        <input type="text" name="producerTime" id="f_register_msg_producerTime" value='<fmt:formatDate value="${entity.producerTime }" pattern="yyyy-MM-dd HH:mm:ss"/>'   maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_consumerTime" class="control-label x100">消费时间：</label>
                        <input type="text" name="consumerTime" id="f_register_msg_consumerTime"  value='<fmt:formatDate value="${entity.consumerTime }" pattern="yyyy-MM-dd HH:mm:ss"/>'  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_uid" class="control-label x100">uid：</label>
                        <input type="text" name="uid" id="f_register_msg_uid" value="${entity.uid }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_password" class="control-label x100">password：</label>
                        <input type="text" name="password" id="f_register_msg_password" value="${entity.password }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_passport" class="control-label x100">passport：</label>
                        <input type="text" name="passport" id="f_register_msg_passport" value="${entity.passport }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_appKey" class="control-label x100">appKey：</label>
                        <input type="text" name="appKey" id="f_register_msg_appKey" value="${entity.appKey }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_mobile" class="control-label x100">mobile：</label>
                        <input type="text" name="mobile" id="f_register_msg_mobile" value="${entity.mobile }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_email" class="control-label x100">email：</label>
                        <input type="text" name="email" id="f_register_msg_email" value="${entity.email }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_realname" class="control-label x100">realname：</label>
                        <input type="text" name="realname" id="f_register_msg_realname" value="${entity.realname }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_idCard" class="control-label x100">idCard：</label>
                        <input type="text" name="idCard" id="f_register_msg_idCard" value="${entity.idCard }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_schoolName" class="control-label x100">schoolName：</label>
                        <input type="text" name="schoolName" id="f_register_msg_schoolName" value="${entity.schoolName }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_course" class="control-label x100">course：</label>
                        <input type="text" name="course" id="f_register_msg_course" value="${entity.course }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_stage" class="control-label x100">stage：</label>
                        <input type="text" name="stage" id="f_register_msg_stage" value="${entity.stage }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_grade" class="control-label x100">grade：</label>
                        <input type="text" name="grade" id="f_register_msg_grade" value="${entity.grade }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_province" class="control-label x100">province：</label>
                        <input type="text" name="province" id="f_register_msg_province" value="${entity.province }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_city" class="control-label x100">city：</label>
                        <input type="text" name="city" id="f_register_msg_city" value="${entity.city }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_area" class="control-label x100">area：</label>
                        <input type="text" name="area" id="f_register_msg_area" value="${entity.area }"  maxlength="30" size="30">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_register_msg_gender" class="control-label x100">gender：</label>
                        <input type="text" name="gender" id="f_register_msg_gender" value="${entity.gender }"  maxlength="30" size="30">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <shiro:hasPermission name="RegisterMsg:create">
        	<li><button type="submit" class="btn-blue" data-icon="save">保存</button></li>
        </shiro:hasPermission>
    </ul>
</div>