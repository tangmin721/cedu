<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<div class="bjui-pageContent">
    <div class="row" style="height:100%;">
        <div style="height:100%;">
        	<div style="height:75%;">
	            <div class="panel panel-default wrapdiv" style="height:75%;">
	                <div class="panel-heading headerdiv"><h3 class="panel-title"><i class="fa fa-indent fa-fw"></i>申请单信息</h3></div>
	                <div id="div_sys_menu_list" class="contentdiv" data-url="${ctx}/core/train/project/roster/checkView/${applyId}" data-toggle="autoajaxload"></div>
	            </div>
            </div>
            <div style="height:25%;">
        		<div class="panel panel-default wrapdiv" style="height:25%;">
                	<div class="panel-heading headerdiv"><h3 class="panel-title"><i class="fa fa-edit fa-fw"></i>审批</h3></div>
                	<div id="div_sys_menu_tree_from" class="contentdiv" data-url="${ctx}/core/train/project/roster/checkAudit/${applyId}" data-type="POST" data-toggle="autoajaxload"></div>
            	</div>
        	</div>
        </div>
    </div>
</div>