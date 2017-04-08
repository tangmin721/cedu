<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
		function fileCheckPass(pid,orgId,type){
			$.ajax({ 
		        type: "POST", 
		        url: '${ctx}/core/train/project/file/saveFilePass/'+pid+'/'+orgId+'/'+type,
		        dataType : "json",
		        success: function(data) {
		        	$(this).dialog('refresh','np-file-check');//刷新dialog
		        	$(this).alertmsg('ok', '审核通过！');
		        },
		        error :  function(){
		        	$(this).alertmsg('error', '系统错误！');
		        }
		    }); 
		}
		
		function fileCheckUnPass(pid,orgId,type){
			$(this).dialog({id:'np-org-file-create', url:'${ctx}/core/train/project/file/fileUnPassForm/'+pid+'/'+orgId+'/'+type, type:'POST',title:'材料审核不通过，请输入为什么不通过',mask:'true',width:'550',height:'240'});
		}
		
		function viewFileCheckUnPass(pid,orgId,type){
			$(this).dialog({id:'np-org-file-create', url:'${ctx}/core/train/project/file/fileUnPassForm/'+pid+'/'+orgId+'/'+type, type:'POST',title:'材料审核不通过，请输入为什么不通过',mask:'true',width:'550',height:'240'});
		}
		
		function orgView(id){
			  $(this).dialog({id:'file-org-view-dialog', url:'${ctx}/core/train/org/base/trainOrgView/'+id, title:'培训机构详细',mask:'true',width:'400',height:'480'});
		}
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
	      			<td><a href="javascript:;" onclick="orgView(${dto.orgId })">${dto.orgName }</a></td>
	      			<td>
	      					<c:if test="${not empty dto.reqProjectFile}">
	      						<a target="_blank" href="${dto.reqProjectFile.fileId }" class="btn btn-dark-blue"  data-icon="file-word-o" 
	      							data-icon="cloud-download" data-toggle="tooltip"  data-placement="top" title="${dto.reqProjectFile.fileName }" ></a>
	      						
	                  			<c:if test="${dto.reqProjectFile.status==2 }"><a href="#" class="btn btn-primary pull-right" data-icon="check-circle" >审核通过</a></c:if>
	      						<c:if test="${dto.reqProjectFile.status==3 }"><a href="javascript:viewFileCheckUnPass(${dto.pid},${dto.orgId},1);" class="btn btn-danger pull-right" data-icon="times-circle" >审核不通过</a></c:if>
	      						
	      						<c:if test="${dto.reqProjectFile.status==1 }">
	                  			<div class="pull-right">
							        <div class="btn-group">
							            <button type="button" class="btn-purple dropdown-toggle" data-toggle="dropdown" data-icon="check-square-o">
							           	待审核
							            <span class="caret"></span></button>
							            <ul class="dropdown-menu right" role="menu">
							                <li><a href="javascript:fileCheckPass(${dto.pid},${dto.orgId},1);" id="teacher_export_all">&nbsp;<span style="color: green;"><span class="fa fa-check-circle"></span>&nbsp; 审核通过</span></a></li>
							                <li class="divider"></li>
							                <li><a href="javascript:fileCheckUnPass(${dto.pid},${dto.orgId},1);" id="teacher_export_search" >&nbsp;<span style="color: red;"><span class="fa fa-times-circle"></span>&nbsp; 审核不通过</span></a></li>
							            </ul>
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
	      						<c:if test="${dto.shishiProjectFile.status==3 }"><a href="javascript:viewFileCheckUnPass(${dto.pid},${dto.orgId},2);" class="btn btn-danger pull-right" data-icon="times-circle" >审核不通过</a></c:if>
	      						
	      						<c:if test="${dto.shishiProjectFile.status==1 }">
	                  			<div class="pull-right">
							        <div class="btn-group">
							            <button type="button" class="btn-purple dropdown-toggle" data-toggle="dropdown" data-icon="check-square-o">
							           	待审核
							            <span class="caret"></span></button>
							            <ul class="dropdown-menu right" role="menu">
							                <li><a href="javascript:fileCheckPass(${dto.pid},${dto.orgId},2);" id="teacher_export_all">&nbsp;<span style="color: green;"><span class="fa fa-check-circle"></span>&nbsp; 审核通过</span></a></li>
							                <li class="divider"></li>
							                <li><a href="javascript:fileCheckUnPass(${dto.pid},${dto.orgId},2);" id="teacher_export_search" >&nbsp;<span style="color: red;"><span class="fa fa-times-circle"></span>&nbsp; 审核不通过</span></a></li>
							            </ul>
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
    

