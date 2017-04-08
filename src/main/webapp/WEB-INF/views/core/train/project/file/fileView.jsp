<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
</script>

<div id="project_check_table" class="bjui-pageContent tableContent">
  	<table data-toggle="tablefixed" data-width="100%">
			<thead>
				<tr>
					<th align="center" width="15">序号</th>
					<th align="center" width="80">机构名称</th>
					<th align="center" width="60">需求分析</th> 
					<th align="center" width="60">实施方案</th>
					<th align="center" width="100">其他材料</th>
				</tr>
			</thead>  
          <tbody>
          	<c:forEach items="${dtos }" var="dto" varStatus="index">
          	   <tr>
	      			<td>${index.count }</td>
	      			<td>${dto.orgName }</td>
	      			<td>
	      					<c:if test="${not empty dto.reqProjectFile}">
	      						<a target="_blank" href="${dto.reqProjectFile.fileId }" class="btn btn-dark-blue"  data-icon="file-word-o" 
	      							data-icon="cloud-download" data-toggle="tooltip"  data-placement="top" title="${dto.reqProjectFile.fileName }" ></a>
	      						
	                  			<c:if test="${dto.reqProjectFile.status==2 }"><a href="#" class="btn btn-primary pull-right" data-icon="check-circle" >审核通过</a></c:if>
	      						<c:if test="${dto.reqProjectFile.status==3 }"><a href="javascript:viewFileCheckUnPass(${dto.pid},${dto.orgId},1);" class="btn btn-danger" data-icon="times-circle" >审核不通过</a></c:if>
	      						
	      						<c:if test="${dto.reqProjectFile.status==1 }">
	                  			<div class="pull-right">
							        <div class="btn-group">
							            <button type="button" class="btn-primary dropdown-toggle">
							           	待审核
							        </div>
							    </div>
							    </c:if>
	      					</c:if>
	      					<c:if test="${empty dto.reqProjectFile}">
	      						<span class="label label-default">未上传</span>
	      					</c:if>
	      			</td>
	      			<td>
	      				<c:if test="${not empty dto.shishiProjectFile}">
	      						<a target="_blank" href="${dto.shishiProjectFile.fileId }" class="btn btn-dark-blue"  data-icon="file-word-o" 
	      							data-icon="cloud-download" data-toggle="tooltip"  data-placement="top" title="${dto.shishiProjectFile.fileName }" ></a>
	      						
	                  			<c:if test="${dto.shishiProjectFile.status==2 }"><a href="#" class="btn btn-primary pull-right" data-icon="check-circle" >审核通过</a></c:if>
	      						<c:if test="${dto.shishiProjectFile.status==3 }"><a href="javascript:viewFileCheckUnPass(${dto.pid},${dto.orgId},2);" class="btn btn-danger" data-icon="times-circle" >审核不通过</a></c:if>
	      						
	      						<c:if test="${dto.shishiProjectFile.status==1 }">
	                  			<div class="pull-right">
							        <div class="btn-group">
							            <button type="button" class="btn-primary dropdown-toggle">
							           	待审核
							        </div>
							    </div>
							    </c:if>
	      					</c:if>
	      					<c:if test="${empty dto.shishiProjectFile}">
	      						<span class="label label-default">未上传</span>
	      					</c:if>
	      			</td>
	      			<td>
	      				<c:forEach items="${dto.otherProjectFiles }" var="projectFile" varStatus="index">
	      					<c:if test="${projectFile.type!=1 && projectFile.type!=2}">
	      						<a target="_blank" href="${projectFile.fileId }" class="btn btn-blue"  data-icon="file-word-o" 
	      							data-icon="cloud-download" data-toggle="tooltip"  data-placement="top" title="${projectFile.fileName }" ></a>
	      					</c:if>
	      				</c:forEach>
	      			</td>
      		   </tr>
     		 </c:forEach>
          </tbody>
      </table>
</div>
    

