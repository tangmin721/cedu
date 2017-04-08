<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">

</script>

<div class="bjui-pageContent">
    <form action="${ctx}/system/permission/savePermission" id="f_menu_permission_form" method="post" data-toggle="validate" data-alertmsg="false" data-callback="refushPermissionTable">
        <input type="hidden" name="id" value="${entity.id}">
        <input type="hidden" name="version" value="${entity.version}">
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
            	<tr>
                    <td>
                        <label for="f_menu_permission_clzName" class="control-label x85"><span class="red">*</span>类名：</label>
                        <input type="hidden" name="menuId"  value="${entity.menuId }" >
                        <input type="text" name="clzName" id="f_menu_permission_clzName" value="${entity.clzName }" data-rule="required" size="15">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_menu_permission_name" class="control-label x85"><span class="red">*</span>名称：</label>
                        <input type="text" name="name" id="f_menu_permission_name" value="${entity.name }" data-rule="required" size="15">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="f_menu_permission_code" class="control-label x85"><span class="red">*</span>编码：</label>
                        <input type="text" name="code" id="f_menu_permission_code" value="${entity.code }" data-rule="required" size="15">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="f_menu_permission_faicon" class="control-label x85">图标：</label>
                        <input type="text" name="faicon" id="f_menu_permission_faicon" value="${entity.faicon }"  size="15">
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