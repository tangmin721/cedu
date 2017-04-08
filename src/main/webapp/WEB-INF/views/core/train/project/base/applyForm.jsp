<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
	var wizard_applyid = undefined;
  	$('#apply_form_wizard').bootstrapWizard({tabClass: 'bwizard-steps',
  			nextSelector: '.next', 
  			previousSelector: '.pre',
  			finishSelector : '.finish'
  		 ,onNext: function(tab, navigation, index) {
  			if(index==1) {
  				$('#f_project_roster_apply_form').isValid(function(v){
  					passflag = v;
  				    if(v){
  				    	$('#f_project_roster_apply_form').bjuiajax('ajaxForm', {callback:function(json){
  				    		if(json.statusCode!='200'){
  				    			$(this).alertmsg('error', json.message);
  				    			$('#rc_pre').trigger("click");//回退上一步
  				    		}else{
  				    			console.log(json);
  				    			wizard_applyid = json.data.id;
  				    			$('#f_project_roster_apply_form input[name="id"]').val(json.data.id);
  				    			$('#f_project_roster_apply_form input[name="version"]').val(json.data.version);
  				    			$('#apply_search').trigger("click");
  				    			
  				    			//刷新2
  				    			$('#div_apply_detail_list').bjuiajax('refreshLayout', {
  				    				target:'#div_apply_detail_list',
  				    				type:'post',
  				    				url:"${ctx}/core/train/project/roster/detail/projectRosterDetailList/"+wizard_applyid
  				    			});
  				    		}
  				    	}})
  				    }
  				});
  				if(!passflag){
  					return false;
  				}
			} 
			
		
		}, onTabShow: function(tab, navigation, index) {
			var $total = navigation.find('li').length;
			var $current = index+1;
			var $percent = ($current/$total) * 100;
			$('#apply_form_wizard').find('.bar').css({width:$percent+'%'});
			
			// If it's the last tab then hide the last button and show the finish instead
			if($current >= $total) {
				//$('#apply_form_wizard').find('.bjui-pageFooter .pre').hide();
				$('#apply_form_wizard').find('.bjui-pageFooter .next').hide();
				$('#rp_up_btn').show();
				//$('#apply_form_wizard').find('.bjui-pageFooter .finish').removeClass('disabled');
			} else {
				$('#apply_form_wizard').find('.bjui-pageFooter .next').show();
				$('#rp_up_btn').hide();
			}
			
		},onTabClick: function(tab, navigation, index) {
			if(index==0){
				$(this).alertmsg('info', '请点击右下角的【下一步】按钮', {displayMode:'slide', displayPosition:'middlecenter', title:'系统友情提示'})
				return false;
			}
		}
  	});
  	
	//监听关闭dialog  触发事件
//	$(document).on('bjui.beforeCloseDialog', function(e) {
//	    var $dialog = $(e.target)
//	    $('.teacher_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/base/teacherList'});
//	})

	$('#rp_up_btn').click(function() {
 		var applyId =  $('#f_project_roster_apply_form input[name="id"]').val();
 		var flag = false;
 		var message = "";
 		if(applyId){
	 		$.ajax({ 
		        type: "POST", 
		        url: "${ctx}/core/train/project/roster/checkCount/"+applyId,
		        dataType : "json",
		        cache : false, 
		        async : false, 
		        success: function(data) {
		        	console.log(data);
		        	flag =  data.data.result;
		        	message = data.data.msg;
		        },
		        error :  function(){
		        	$(this).alertmsg('error', '系统错误！');
		        }
		    }); 
 		}
 		
 		if(!flag){
 			$(this).alertmsg('warn', message);
 		}
		if(flag){
			$.ajax({ 
		        type: "POST", 
		        url: "${ctx}/core/train/project/roster/reportRoster/"+applyId,
		        dataType : "json",
		        success: function(data) {
		        	console.log(data);
		        	$(this).alertmsg('ok', data.message);
		        	$(this).dialog('close','project_roster_apply_form');
		        	$('#project_search1').trigger('click');
  		        	$('#project_search2').trigger('click');
		        },
		        error :  function(){
		        	$(this).alertmsg('error', '系统错误！');
		        }
		    }); 
		}
	});

</script>
<style>

</style>
<div id="apply_form_wizard" style="height:100%;">
<div class="bjui-pageContent ">
	<div style="padding:10px 10px 0px 10px;">
		申请单状态：<c:if test="${status ==0}"><span class="label label-default">未上报</span></c:if>
			<c:if test="${status ==1}"><span class="label label-info">已上报</span></c:if>
			<c:if test="${status ==2}"><span class="label label-primary">审核通过</span>审批理由：</c:if>
			<c:if test="${status ==3}"><span class="label label-success">审核不通过</span>审批理由：</c:if>
	</div>
	<div class="wrapdiv2">
		  <ul class="headerdiv2" style="padding:10px;">
		  	<li><a href="#tab1" data-toggle="tab"><span class="label">1</span> 填写主单</a></li>
			<li><a href="#tab2" data-toggle="tab"><span class="label">2</span> 选择人员</a></li>
			
		</ul>
		
          <div class="tab-content contentdiv2" >
		    <div class="tab-pane" id="tab1" style="height:100%;overflow-y:auto;">
		    	<div style="height:100%;padding:0px;overflow-y:auto;">
		    		<div id="div_apply_list" class="contentdiv"
		    			<c:if test="${empty id}">
		    				data-url="${ctx}/core/train/project/roster/projectRosterForm/${pid}" 
		    			</c:if>
		    			<c:if test="${not empty id}">
		    				data-url="${ctx}/core/train/project/roster/projectRosterForm/${pid}?id=${id }" 
		    			</c:if>
		    			data-type="POST" data-toggle="autoajaxload"></div>
		    	</div>
		    </div>
		    <div class="tab-pane" id="tab2" style="height:100%;overflow-y:auto;">
		     	<div style="height:100%;padding:0px;overflow-y:auto;">
		     		名额：${quotaNum }
		    		<div id="div_apply_detail_list" class="contentdiv"></div>
		    	</div>
		    </div>
		</div>
      </div>
	
</div>
<div class="bjui-pageFooter" style="border-top:1px #ddd solid;">
	<button type="button" id="rc_pre" class="btn-dark-blue pull-left pre" data-icon="backward">上一步</button>
	<button type="button" class="btn-dark-blue pull-right next" >下一步 <span class="fa fa-forward"></span></button>
	<c:if test="${status == 0 || status == 3}">
		<button type="button" id="rp_up_btn" class="btn-red pull-right" style="display:none;">上报 <span class="fa fa-upload"></span></button>
	</c:if>
	
</div>
</div>
