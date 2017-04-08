<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
		
</script>


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
					<th align="center" width="56">审核状态</th>
					<th align="center" width="120">下载</th>
				</tr>
			</thead>  
          <tbody>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/req_file1_view.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/shishi_file2_view.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/tongzhi_file3_view.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/kebiao_file4_view.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/mingdan_file5_view.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/pinjia_file6_view.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/baogao_file7_view.jsp"%>
		 	  <%@ include file="/WEB-INF/views/core/train/project/file/ziyuan_file8_view.jsp"%>
          </tbody>
      </table>
</div>
    
