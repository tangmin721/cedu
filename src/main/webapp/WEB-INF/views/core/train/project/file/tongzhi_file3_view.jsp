<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
</script>


			<tr>
                  <td>3</td>
                  <td>参训通知</td>
                  <c:choose>
				   <c:when test="${empty objFile3}"> 
				    	<td>
	                  		<span id="file3_span" class="label label-default">未上传</span>
	                  	</td>
	                  	<td id="file3_td_status">
	                  	</td>
	                  	<td id="file3_td_oprate">
						 </td>
				   </c:when>
				   <c:otherwise>  
				   		
				   		<td>
	                  		<span id="file3_span" class="label label-primary">已上传</span>
	                  	</td>
	                  	<td id="file3_td_status">
	                  		
	                  		<!--  	<c:if test="${objFile3.status==0 }"><span class="label label-default">暂未提交</span></c:if>
	                  			<c:if test="${objFile3.status==1 }"><span class="label label-primary">待审核</span></c:if>
	                  			<c:if test="${objFile3.status==2 }"><span class="label label-success">审核通过</span></c:if>
	                  			<c:if test="${objFile3.status==3 }"><span class="label label-danger">审核不通过</span></c:if>-->
	                  		
	                  	</td>
	                  	<td id="file3_td_oprate">
	                  		<div id="file3_btn_group" class="btn-group pull-left" role="group">
									<a target="_blank" href="${objFile3.fileId }" class="btn btn-blue" >下载</a>
							</div>
	                  	</td>
				   </c:otherwise>
				  
				</c:choose>
              </tr>