<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

				<shiro:hasRole name="SCHOOL_ADMIN">
				<c:if test="${item.status ==0}">
					<shiro:hasPermission name="Project:changeStatus">
						<button type="button" class="btn btn-primary" data-icon="share-square-o" onclick="optProject(${item.id},1)" data-toggle="tooltip"  data-placement="top" title="发布"></button>
					</shiro:hasPermission>
					<shiro:hasPermission name="Project:delete">
						<button type="button" class="btn btn-danger" data-icon="trash-o" onclick="deleteProject(${item.id})"  data-toggle="tooltip"  data-placement="top" title="删除"></button>
					</shiro:hasPermission>
					<shiro:hasPermission name="Project:update">
						<button type="button" class="btn btn-green" data-icon="edit" onclick="editProject(${item.id})" data-toggle="tooltip"  data-placement="top" title="编辑"></button>
					</shiro:hasPermission>
				</c:if>
				<c:if test="${item.status ==1}">
					<shiro:hasPermission name="Project:changeStatus">
						<c:if test="${item.cstatus ==11 || item.cstatus==13}">
							<button type="button" class="btn btn-info" data-icon="ban" onclick="optProject(${item.id},0)" data-toggle="tooltip"  data-placement="top" title="取消发布"></button>
						</c:if>
						<c:if test="${item.cstatus ==12}">
							<button type="button" class="btn btn-primary" data-icon="ge" onclick="optProject(${item.id},2)" data-toggle="tooltip"  data-placement="top" title="变更状态为【培训中】"></button>
						</c:if>
					</shiro:hasPermission>
					<shiro:hasPermission name="Project:update">
						<c:if test="${item.cstatus ==12}">
							<button type="button" class="btn btn-dark-blue" data-icon="cog" onclick="confTrainDept(${item.id})" data-toggle="tooltip"  data-placement="top" title="设置培训单位"></button>
							<button type="button" class="btn btn-purple" data-icon="check-square-o" onclick="fileCheck(${item.id})" data-toggle="tooltip"  data-placement="top" title="机构材料审核"></button>
							<button type="button" class="btn btn-dark-orange" data-icon="users" onclick="personManager(${item.id})" data-toggle="tooltip"  data-placement="top" title="参培人员与成绩管理"></button>
						</c:if>
					</shiro:hasPermission>
				</c:if>
				<c:if test="${item.status ==2}">
					<c:if test="${item.cstatus ==12}">
						<button type="button" class="btn btn-primary" data-icon="ra" onclick="optProject(${item.id},3)" data-toggle="tooltip"  data-placement="top" title="变更状态为【培训结束】"></button>
					</c:if>
						<button type="button" class="btn btn-purple" data-icon="file-word-o" onclick="fileCheck(${item.id})" data-toggle="tooltip"  data-placement="top" title="机构材料查看"></button>
						<button type="button" class="btn btn-dark-orange" data-icon="users" onclick="personManager(${item.id})" data-toggle="tooltip"  data-placement="top" title="参培人员与成绩管理"></button>
				</c:if>
				<c:if test="${item.status ==3}">
					<shiro:hasPermission name="Project:update">
						<button type="button" class="btn btn-dark-blue" data-icon="file-word-o" onclick="fileView(${item.id})" data-toggle="tooltip"  data-placement="top" title="机构材料查看"></button>
						<button type="button" class="btn btn-dark-orange" data-icon="users" onclick="personManager(${item.id})" data-toggle="tooltip"  data-placement="top" title="参培人员与成绩管理"></button>
					</shiro:hasPermission>
				</c:if>
				</shiro:hasRole>
				
				
				
				
				
				
				
				
				<shiro:lacksRole name="SCHOOL_ADMIN">
					<c:if test="${item.status ==0}">
					<shiro:hasPermission name="Project:changeStatus">
						<button type="button" class="btn btn-primary" data-icon="share-square-o" onclick="optProject(${item.id},1)" data-toggle="tooltip"  data-placement="top" title="发布"></button>
					</shiro:hasPermission>
					<shiro:hasPermission name="Project:delete">
						<button type="button" class="btn btn-danger" data-icon="trash-o" onclick="deleteProject(${item.id})"  data-toggle="tooltip"  data-placement="top" title="删除"></button>
					</shiro:hasPermission>
					<shiro:hasPermission name="Project:update">
						<button type="button" class="btn btn-green" data-icon="edit" onclick="editProject(${item.id})" data-toggle="tooltip"  data-placement="top" title="编辑"></button>
					</shiro:hasPermission>
				</c:if>
				<c:if test="${item.status ==1}">
					<shiro:hasPermission name="Project:changeStatus">
						<button type="button" class="btn btn-info" data-icon="ban" onclick="optProject(${item.id},0)" data-toggle="tooltip"  data-placement="top" title="取消发布"></button>
						<button type="button" class="btn btn-primary" data-icon="ge" onclick="optProject(${item.id},2)" data-toggle="tooltip"  data-placement="top" title="变更状态为【培训中】"></button>
					</shiro:hasPermission>
					<shiro:hasPermission name="Project:update">
						<button type="button" class="btn btn-green" data-icon="edit" onclick="editProject(${item.id})" data-toggle="tooltip"  data-placement="top" title="编辑"></button>
						<button type="button" class="btn btn-dark-blue" data-icon="cog" onclick="confTrainDept(${item.id})" data-toggle="tooltip"  data-placement="top" title="设置培训单位"></button>
						<button type="button" class="btn btn-purple" data-icon="check-square-o" onclick="fileCheck(${item.id})" data-toggle="tooltip"  data-placement="top" title="机构材料审核"></button>
						<button type="button" class="btn btn-dark-orange" data-icon="users" onclick="personManager(${item.id})" data-toggle="tooltip"  data-placement="top" title="参培人员与成绩管理"></button>
					</shiro:hasPermission>
				</c:if>
				<c:if test="${item.status ==2}">
						<button type="button" class="btn btn-primary" data-icon="ra" onclick="optProject(${item.id},3)" data-toggle="tooltip"  data-placement="top" title="变更状态为【培训结束】"></button>
						<button type="button" class="btn btn-purple" data-icon="file-word-o" onclick="fileCheck(${item.id})" data-toggle="tooltip"  data-placement="top" title="机构材料查看"></button>
						<button type="button" class="btn btn-dark-orange" data-icon="users" onclick="personManager(${item.id})" data-toggle="tooltip"  data-placement="top" title="参培人员与成绩管理"></button>
				</c:if>
				<c:if test="${item.status ==3}">
					<shiro:hasPermission name="Project:update">
						<button type="button" class="btn btn-dark-blue" data-icon="file-word-o" onclick="fileView(${item.id})" data-toggle="tooltip"  data-placement="top" title="机构材料查看"></button>
						<button type="button" class="btn btn-dark-orange" data-icon="users" onclick="personManager(${item.id})" data-toggle="tooltip"  data-placement="top" title="参培人员与成绩管理"></button>
					</shiro:hasPermission>
				</c:if>
				</shiro:lacksRole>