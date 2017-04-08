<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

				<c:if test="${item.status ==1 or item.status ==2 or item.status==3}">
						<button type="button" class="btn btn-dark-blue" data-icon="file-text" onclick="orgFileCreate(${item.id})" data-toggle="tooltip"  data-placement="top" title="机构材料管理"></button>
                        <button type="button" class="btn btn-dark-orange" data-icon="users" onclick="personManager(${item.id})" data-toggle="tooltip"  data-placement="top" title="参培人员与成绩管理"></button>
				</c:if>
