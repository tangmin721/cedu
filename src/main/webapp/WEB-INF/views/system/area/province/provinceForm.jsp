<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
function refushProvinceTable(json){
	if(json.statusCode!='200'){
		$(this).alertmsg('error', json.message);
	}else{
		$('.province_pager_form').bjuiajax('doSearch', {url:'${ctx}/system/area/province/provinceList'});
		$(this).dialog('closeCurrent');
	}
}
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/system/area/province/saveProvince" id="f_province_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushProvinceTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
            	<tr>
                    <td>
                        <label for="f_province_seq" class="control-label x85"><span class="red">*</span>序号：</label>
                        <input type="text" name="seq" id="f_province_seq" value="${entity.seq }" data-rule="required;digits" size="15" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_province_provinceNo" class="control-label x85"><span class="red">*</span>provinceNo：</label>
                        <input type="text" name="provinceNo" id="f_province_provinceNo" value="${entity.provinceNo }" data-rule="required" size="15">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_province_provinceName" class="control-label x85"><span class="red">*</span>省：</label>
                        <input type="text" name="provinceName" id="f_province_provinceName" value="${entity.provinceName }" data-rule="required" size="15">
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