<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<div class="bjui-pageContent">
      <div class="row" style="height:100%;">
        <div class="col-md-6" style="height:100%;">
            <div class="panel panel-default wrapdiv">
                <div class="panel-heading headerdiv"><h3 class="panel-title"><i class="fa fa-book fa-fw"></i>字典目录
                		<button id="dict-remove-cache" class="btn btn-warning pull-right" style="display:block;padding-bottom:2px;">清缓存</button>
                	</h3>
                </div>
                <div id="div_sys_dict_catlog" class="contentdiv" data-url="${ctx}/system/dict/catlog/dictCatlogList" data-toggle="autoajaxload"></div>
            </div>
        </div>
        <div class="col-md-6" style="height:100%;">
            <div class="panel panel-default wrapdiv">
                <div class="panel-heading headerdiv"><h3 class="panel-title"><i class="fa fa-list fa-fw"></i>字典项</h3></div>
                <div id="div_sys_dict_item" class="contentdiv" data-url="${ctx}/system/dict/item/dictItemList" data-toggle="autoajaxload"></div>
            </div>
        </div>
    </div>
</div>
