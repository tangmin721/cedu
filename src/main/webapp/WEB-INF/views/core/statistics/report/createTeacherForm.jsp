<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
</script>

<div class="bjui-pageContent">
    <form action="${ctx}/core/statistics/report/makeTeachers" id="mk_tch_form" method="post" data-toggle="validate" data-alertmsg="false" >
        <table class="table table-condensed table-hover" style="width:100%;margin-top:-1px;">
            <tbody>
				 <tr>
                    <td>
                        <label for="mk_tch_phone" class="control-label x100">名称开始编号：</label>
                        <input type="text" name="startNum" id="mk_tch_phone" maxlength="11" size="20">
                    </td>
                </tr>
				 <tr>
                    <td>
                        <label for="mk_tch_email" class="control-label x100">生成教师数量：</label>
                        <input type="text" name="totalNum" id="mk_tch_email" maxlength="100" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <li><button type="submit" class="btn-blue" data-icon="save">开始生成测试数据</button></li>
    </ul>
</div>