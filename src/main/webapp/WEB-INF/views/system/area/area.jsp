<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<div class="bjui-pageContent">
      <div class="row" style="height:100%;">
        <div class="col-md-4" style="height:100%;">
            <div class="panel panel-default wrapdiv">
                <div class="panel-heading headerdiv"><h3 class="panel-title"><i class="fa fa-map-marker fa-fw"></i>省</h3></div>
                <div id="div_sys_area_province" class="contentdiv" data-url="${ctx}/system/area/province/provinceList" data-toggle="autoajaxload"></div>
            </div>
        </div>
        <div class="col-md-4" style="height:100%;">
            <div class="panel panel-default wrapdiv">
                <div class="panel-heading headerdiv"><h3 class="panel-title"><i class="fa fa-tint fa-fw"></i>市</h3></div>
                <div id="div_sys_area_city" class="contentdiv" data-url="${ctx}/system/area/city/cityList" data-toggle="autoajaxload"></div>
            </div>
        </div>
        <div class="col-md-4" style="height:100%;">
            <div class="panel panel-default wrapdiv">
                <div class="panel-heading headerdiv"><h3 class="panel-title"><i class="fa fa-thumb-tack fa-fw"></i>县</h3></div>
                <div id="div_sys_area_town" class="contentdiv" data-url="${ctx}/system/area/town/townList" data-toggle="autoajaxload"></div>
            </div>
        </div>
    </div>
</div>
