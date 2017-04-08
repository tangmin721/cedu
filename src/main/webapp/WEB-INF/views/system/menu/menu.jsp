<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<div class="bjui-pageContent">
    <div class="row" style="height:100%;">
        <div class="col-md-2" style="height:100%;">
            <div class="panel panel-default wrapdiv">
                <div class="panel-heading headerdiv"><h3 class="panel-title"><i class="fa fa-tree fa-fw"></i>菜单树
							 <button id="menu-remove-cache" class="btn btn-warning pull-right" style="display:block;padding-bottom:2px;">清缓存</button>
						</h3>
				</div>
                <div id="div_sys_menu_tree" class="contentdiv" style="overflow:auto" data-url="${ctx}/system/menu/tree" data-type="POST" data-toggle="autoajaxload"></div>
            </div>
        </div>
        <div class="col-md-10" style="height:100%;">
        	<div style="height:25%;">
        		<div class="panel panel-default wrapdiv" style="height:25%;">
                	<div class="panel-heading headerdiv"><h3 class="panel-title"><i class="fa fa-edit fa-fw"></i>本节点属性编辑</h3></div>
                	<div id="div_sys_menu_tree_from" class="contentdiv" data-url="${ctx}/system/menu/menuTreeForm" data-type="POST" data-toggle="autoajaxload"></div>
            	</div>
        	</div>
        	<div style="height:75%;">
	            <div class="panel panel-default wrapdiv" style="height:75%;">
	                <div class="panel-heading headerdiv"><h3 class="panel-title"><i class="fa fa-indent fa-fw"></i>下级菜单列表</h3></div>
	                <div id="div_sys_menu_list" class="contentdiv" data-url="${ctx}/system/menu/menuList" data-toggle="autoajaxload"></div>
	            </div>
            </div>
        </div>
    </div>
</div>