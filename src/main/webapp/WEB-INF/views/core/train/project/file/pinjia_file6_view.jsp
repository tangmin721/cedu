<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
</script>


			<tr>
                  <td>6</td>
                  <td>教学质量评价（限集中培训）</td>
                  <c:choose>
				   <c:when test="${empty objFile6}"> 
				    	<td>
	                  		<span id="file6_span" class="label label-default">未上传</span>
	                  	</td>
	                  	<td id="file6_td_status">
	                  	</td>
	                  	<td id="file6_td_oprate">
	                  		<a target="_blank" href="${down_url}教学质量评价模板.xlsx" class="btn btn-purple" >下载模板</a>
						 </td>
				   </c:when>
				   <c:otherwise>  
				   		
				   		<td>
	                  		<span id="file6_span" class="label label-primary">已上传</span>
	                  	</td>
	                  	<td id="file6_td_status">
	                  		
	                  		<!--  	<c:if test="${objFile6.status==0 }"><span class="label label-default">暂未提交</span></c:if>
	                  			<c:if test="${objFile6.status==1 }"><span class="label label-primary">待审核</span></c:if>
	                  			<c:if test="${objFile6.status==2 }"><span class="label label-success">审核通过</span></c:if>
	                  			<c:if test="${objFile6.status==3 }"><span class="label label-danger">审核不通过</span></c:if>-->
	                  		
	                  	</td>
	                  	<td id="file6_td_oprate">
	                  		<div id="file6_btn_group" class="btn-group pull-left" role="group">
	                  				<a target="_blank" href="${down_url}教学质量评价模板.xlsx" class="btn btn-purple" >下载模板</a>
									<a target="_blank" href="${objFile6.fileId }" class="btn btn-blue" >下载</a>
							</div>
	                  	</td>
				   </c:otherwise>
				  
				</c:choose>
              </tr>