<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">

function checkupload5file(obj){
	var file=$(obj).val();
	var ext = file.substr(file.lastIndexOf("."));
	if(ext!='.xlsx' && ext!='.xls'){
		$(this).alertmsg('warn', "只允许后缀为.xlsx或.xls的Excel文件,请重新选择文件！");
		$(obj).val('');
		return false;
	}else{  
		if(obj.files){  
	        var size=obj.fileSize || obj.files[0].fileSize || obj.files[0].size;
	        if(size>SYS_FILE_SIZE){
	        	$(this).alertmsg('warn','文件最大为50M！');
	            $('#upload5_file').val('');
	            return false;
	        }
		}
	}
	return true;
}

function upload5(obj){
	if(checkupload5file(obj)){
		$("#file5_form").bjuiajax('ajaxForm', {
  			callback:function(data){
  				if(data.statusCode==300){
  					$(this).alertmsg('error', data.message);
  				}else{
  					// url    path(保存数据库的)
  					if(data.data.fileId!="empt"){
  						$(this).alertmsg('ok', "上传成功！");
  						$("#file5_span").removeClass("label-default").addClass("label-primary");
  						$("#file5_span").text("已上传");
  						
  						$("#file5_status_span").removeClass("label-default").addClass("label-primary");
  						$("#file5_status_span").text("待审核");
  						
  						
  						var againUp = '<div class="file-box-again">'
  							+'<form id="file5_form" action="${ctx }/core/train/project/file/upload5" method="post" enctype="multipart/form-data">	'
  							+'<input type="hidden" name="pid" value="${pid }"/>'
  							+'<input type="hidden" name="orgId" value="${orgId }"/>'
  							+'<input type="file" name="file" class="file-again" size="28" onchange="upload5(this);" id="upload5_file" />'
  							+'<button type="button" class="btn btn-green">重新上传</button>'
  							+'</form>'
  							+'</div>';
  						
  						var hadUp ='<a target="_blank" href="${down_url}参训学员名单.xlsx" class="btn btn-purple" >下载模板</a>'
  								+'<a target="_blank" href="'+data.data.downloadUrl+'" class="btn btn-blue">下载</a>'
  								+'<button type="button" class="btn btn-red" onclick="deleteFile5('+data.data.projectFileId+')">删除</button>';
  								
  						var checkStatus	='<span class="label label-primary">待审核</span>';		
  								
  						//上传栏		
  						$("#file5_td_upload").html(againUp);
  						//状态栏
  						//$("#file5_td_status").html(checkStatus);
  						//操作栏
  						$("#file5_td_oprate").html(hadUp);
  						
  					}else{
  						$(this).alertmsg('error', "上传失败！");
  					}
  					
  				}
  				
  			}
  		});  
	}
		
}
	
	function deleteFile5(projectFileId){
		$.ajax({ 
	        type: "POST", 
	        url: "${ctx}/core/train/project/file/deleteFile5/"+projectFileId,
	        dataType : "json",
	        success: function(data) {
	        	$(this).alertmsg('ok', "删除成功");
	        	$("#file5_span").removeClass("label-primary").addClass("label-default");
				$("#file5_span").text("未上传");
				
				$("#file5_status_span").removeClass("label-primary").addClass("label-default");
				$("#file5_status_span").text("");
				
				var upstr = '<div id="file5_btn_group" class="btn-group pull-left" role="group">'
	   			+'<div class="file-box">'
	   			+'<form id="file5_form" action="${ctx }/core/train/project/file/upload5" method="post" enctype="multipart/form-data">'
	   			+'<input type="hidden" name="pid" value="${pid }"/>'
	   			+'		<input type="hidden" name="orgId" value="${orgId }"/>'
	   			+'		<input type="file" name="file" class="file" size="28" onchange="upload5(this);" id="upload5_file" />'
	   			+'		<button type="button" class="btn btn-blue">上传</button>'
	   			+'	</form>'
	   			+'</div>'
	   			+'</div>';
				
				//上传栏		
				$("#file5_td_upload").html(upstr);
				//状态栏
				//$("#file5_td_status").html("");
				//操作栏
				$("#file5_td_oprate").html('<a target="_blank" href="${down_url}参训学员名单.xlsx" class="btn btn-purple" >下载模板</a>');
	        },
	        error :  function(){
	        	$(this).alertmsg('error', '系统错误！');
	        }
	    }); 
	}
</script>


			<tr>
                  <td>5</td>
                  <td>参训学员名单</td>
                  <c:choose>
				   <c:when test="${empty objFile5}"> 
				    	<td>
	                  		<span id="file5_span" class="label label-default">未上传</span>
	                  	</td>
	                  	<td id="file5_td_upload">
	                  		<div id="file5_btn_group" class="btn-group pull-left" role="group">
					   			<div class="file-box">
									<form id="file5_form" action="${ctx }/core/train/project/file/upload5" method="post" enctype="multipart/form-data">	
										<input type="hidden" name="pid" value="${pid }">
										<input type="hidden" name="orgId" value="${orgId }">
										<input type="file" name="file" class="file" size="28" onchange="upload5(this);" id="upload5_file" />
										<button type="button" class="btn btn-blue">上传</button>
									</form>
								</div>   
							</div>
	                  	</td>
	                  	<td id="file5_td_status">
	                  	</td>
	                  	<td id="file5_td_oprate">
	                  		<a target="_blank" href="${down_url}参训学员名单.xlsx" class="btn btn-purple" >下载模板</a>
						 </td>
				   </c:when>
				   <c:otherwise>  
				   		
				   		<td>
	                  		<span id="file5_span" class="label label-primary">已上传</span>
	                  	</td>
	                  	<td id="file5_td_upload">
                  				<div class="file-box-again">
									<form id="file5_form" action="${ctx }/core/train/project/file/upload5" method="post" enctype="multipart/form-data">	
										<input type="hidden" name="pid" value="${pid }">
										<input type="hidden" name="orgId" value="${orgId }">
										<input type="file" name="file" class="file-again" size="28" onchange="upload5(this);" id="upload5_file" />
										<button type="button" class="btn btn-green">重新上传</button>
									</form>
								</div>   
						  </td>
	                  	<td id="file5_td_status">
	                  		
	                  		<!--  	<c:if test="${objFile5.status==0 }"><span class="label label-default">暂未提交</span></c:if>
	                  			<c:if test="${objFile5.status==1 }"><span class="label label-primary">待审核</span></c:if>
	                  			<c:if test="${objFile5.status==2 }"><span class="label label-success">审核通过</span></c:if>
	                  			<c:if test="${objFile5.status==3 }"><span class="label label-danger">审核不通过</span></c:if>-->
	                  		
	                  	</td>
	                  	<td id="file5_td_oprate">
	                  		<div id="file5_btn_group" class="btn-group pull-left" role="group">
	                  				<a target="_blank" href="${down_url}参训学员名单.xlsx" class="btn btn-purple" >下载模板</a>
									<a target="_blank" href="${objFile5.fileId }" class="btn btn-blue" >下载</a>
									<button type="button" class="btn btn-red" onclick="deleteFile5(${objFile5.id })">删除</button>
							</div>
	                  	</td>
				   </c:otherwise>
				  
				</c:choose>
              </tr>