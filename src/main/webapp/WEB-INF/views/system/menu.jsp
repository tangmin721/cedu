<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<ul id="bjui-hnav-navbar">
      <c:forEach items="${menus }" var="rootNode" varStatus="index">
      	<li <c:if test="${index.first}"> class="active" </c:if>><a href="javascript:;" data-toggle="slidebar"><i class="fa fa-${rootNode.faicon }"></i>${rootNode.name }</a>
      		<div class="items hide" data-noinit="true">
      			<c:forEach items="${rootNode.levelOnechildren }" var="levelOne">
      				<ul id="ul-${levelOne.id }" class="ztree ztree_main" data-toggle="ztree" data-on-click="MainMenuClick" data-expand-all="true" data-faicon="${levelOne.faicon }" data-tit="${levelOne.name }">
	      					<li data-id="${levelOne.id }" data-pid="${levelOne.pid }" data-faicon="folder-open-o" data-faicon-close="folder-o">${levelOne.name }</li>
	      					<c:forEach items="${levelOne.levelTwochildren }" var="levelTwo">
	      						<li data-id="${levelTwo.id }" data-pid="${levelTwo.pid }" data-url="${ctx }/${levelTwo.url }" data-tabid="${levelTwo.tabid }" data-faicon="${levelTwo.faicon }">${levelTwo.name }</li>
	      					</c:forEach>
      				</ul>
      			</c:forEach>
      		</div>
      	</li>
      </c:forEach>
</ul>