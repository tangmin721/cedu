<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
  	$('#project_report_wizard').bootstrapWizard({tabClass: 'bwizard-steps',
  			nextSelector: '.next', 
  			previousSelector: '.pre'
  		 ,onNext: function(tab, navigation, index) {
  			// alert("onNext-index:"+index);
  			if(index==1) {
  				var flag = false;
  				$('#f_project_report_config_form').isValid(function(v){
  					passflag = v;
  				    if(v){
  				    	$('#f_project_report_config_form').bjuiajax('ajaxForm', {callback:function(json){
  				    		console.log(json);
  				    		if(json.statusCode!='200'){
  				    			$(this).alertmsg('error', json.message);
  				    			$('#rc_pre').trigger("click");//回退上一步
  				    		}else{
  				    			$('#f_project_report_config_form input[name="id"]').val(json.data.id);
  				    			$('#f_project_report_config_form input[name="version"]').val(json.data.version);
  				    			
  				    			//刷新2
  				    			$('#div_pr_quota').bjuiajax('refreshLayout', {
  				    				target:'#div_pr_quota',
  				    				type:'post',
  				    				url:"${ctx}/core/train/project/quota/projectQuotaList/${pid}"
  				    			});
  				    		}
  				    	}})
  				    }
  				});
  				if(!passflag){
  					return false;
  				}
			} 
			if(index==2) {
				
			}
			
			if(index==3) {
				
			}
		
		}, onTabShow: function(tab, navigation, index) {
			var $total = navigation.find('li').length;
			var $current = index+1;
			var $percent = ($current/$total) * 100;
			$('#project_report_wizard').find('.bar').css({width:$percent+'%'});
			
			// If it's the last tab then hide the last button and show the finish instead
			if($current >= $total) {
				//$('#project_report_wizard').find('.bjui-pageFooter .pre').hide();
				$('#project_report_wizard').find('.bjui-pageFooter .next').hide();
				$('#project_report_wizard').find('.bjui-pageFooter .finish').show();
				$('#project_report_wizard').find('.bjui-pageFooter .finish').removeClass('disabled');
			} else {
				$('#project_report_wizard').find('.bjui-pageFooter .next').show();
				$('#project_report_wizard').find('.bjui-pageFooter .finish').hide();
			}
			
		},onTabClick: function(tab, navigation, index) {
			if(index==0){
				$(this).alertmsg('info', '请点击右下角的【下一步】按钮', {displayMode:'slide', displayPosition:'middlecenter', title:'系统友情提示'})
				return false;
			}
		}
  	});
  	
	$('#project_report_wizard .finish').click(function() {
		$(this).alertmsg('ok', '保存信息成功！');
		$('.teacher_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/base/teacherList'});
		$(this).dialog('closeCurrent');
	});
	
	//监听关闭dialog  触发事件
//	$(document).on('bjui.beforeCloseDialog', function(e) {
//	    var $dialog = $(e.target)
//	    $('.teacher_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/base/teacherList'});
//	})

</script>
<style>

</style>
<div id="project_report_wizard" style="height:100%;">
<div class="bjui-pageContent ">
	<div class="wrapdiv2">
		  <ul class="headerdiv2" style="padding:10px;">
		  	<li><a href="#tab1" data-toggle="tab"><span class="label">1</span> 报名配置</a></li>
			<li><a href="#tab2" data-toggle="tab"><span class="label">2</span> 名额分配</a></li>
		</ul>
          <div class="tab-content contentdiv2" >
		    <div class="tab-pane" id="tab1" style="height:100%;overflow-y:auto;">
		    	<div style="height:100%;padding:0px;overflow-y:auto;">
		    		<div id="div_pr_config" class="contentdiv" data-url="${ctx}/core/train/project/reportConfig/projectReportConfigForm/${pid}" 
		    			data-type="POST" data-toggle="autoajaxload"></div>
		    	</div>
		    </div>
		    <div class="tab-pane" id="tab2" style="height:100%;overflow-y:auto;">
		     	<div style="height:100%;padding:0px;overflow-y:auto;">
		    		<div id="div_pr_quota" class="contentdiv"></div>
		    	</div>
		    </div>
		</div>
      </div>
	
</div>
<div class="bjui-pageFooter" style="border-top:1px #ddd solid;">
	<button type="button" id="rc_pre" class="btn-dark-blue pull-left pre" data-icon="backward">上一步</button>
	<button type="button" class="btn-dark-blue pull-right next" >下一步 <span class="fa fa-forward"></span></button>
	<button type="button" class="btn-green pull-right next finish" data-icon="check-circle" style="display:none;">完成 </button>
</div>
</div>
