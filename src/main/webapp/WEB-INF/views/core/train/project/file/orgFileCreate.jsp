<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
	function checkProjectSize(size){
	    if(size>1024*1024*100){
	            alert('文件最大为100M.\n\n请重新选择文件或进行分批处理 .');
	            $('#school_file').val('');
	            return false;
	    }
	}
	function checkProjectFile(obj){
	      var file=$(obj).val();
	      var ext=file.substr(file.lastIndexOf('.')+1);
	      if(obj.files){    
	            var size=obj.files[0].size;
	            return checkProjectSize(size);
	      }
	}
</script>

<!--<div class="bjui-pageHeader">
		 <div class="btn-group pull-left" role="group">
				<button type="button" id="org_file_add" class="btn btn-blue" data-icon="plus-circle" >添加更多典型生成资源</button>
		</div> 
</div>-->

<style type="text/css">
.file-box{ position:relative;width:48px;cursor:pointer;}
.file{ position:absolute; top:0;height:24px; filter:alpha(opacity:0);opacity: 0;width:48px }

.file-box-again{ position:relative;width:75px;cursor:pointer;}
.file-again{ position:absolute; top:0;height:24px; filter:alpha(opacity:0);opacity: 0;width:75px }
</style>

<div id="teacher_table" class="bjui-pageContent tableContent">
  	<table data-toggle="tablefixed" data-width="100%">
			<thead>
				<tr>
					<th align="center" width="56">材料编号</th>
					<th align="center" width="120">材料名称</th>
					<th align="center" width="56">上传状态</th> 
					<th align="center" width="56">上传</th>
					<th align="center" width="56">审核状态</th>
					<th align="center" width="120">操作</th>
				</tr>
			</thead>  
          <tbody>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/req_file1.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/shishi_file2.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/tongzhi_file3.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/kebiao_file4.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/mingdan_file5.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/pinjia_file6.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/baogao_file7.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/ziyuan_file8.jsp"%>
          </tbody>
      </table>
</div>
    
