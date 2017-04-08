<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushTownTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.town_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/area/town/townList'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/system/area/town/saveTown" id="f_town_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushTownTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
            	 <tr>
                    <td>
                        <label for="f_town_seq" class="control-label x85"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_town_seq" value="${entity.seq }" data-rule="required;digits" size="15" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_town_cityNo" class="control-label x85"><span class="red">*</span>cityNo：</label>
                        <input type="text" name="cityNo" id="f_town_cityNo" value="${entity.cityNo }" data-rule="required" size="15">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_town_townNo" class="control-label x85"><span class="red">*</span>townNo：</label>
                        <input type="text" name="townNo" id="f_town_townNo" value="${entity.townNo }" data-rule="required" size="15">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_town_townName" class="control-label x85"><span class="red">*</span>县/区：</label>
                        <input type="text" name="townName" id="f_town_townName" value="${entity.townName }" data-rule="required" size="15">
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