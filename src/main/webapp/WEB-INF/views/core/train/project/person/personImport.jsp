<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
  	$('#person_import_wizard').bootstrapWizard({tabClass: 'bwizard-steps',
  			nextSelector: '.next', 
  			previousSelector: '.pre',
  			finishSelector : '.finish'
  		 ,onPrevious:function(tab, navigation, index){
  			if(index!=2) {
  				$('#person_import_wizard').find('.bjui-pageFooter .again').hide();
  			}
  		 }
  		 ,onNext: function(tab, navigation, index) {
			if(index==2) {
				if(!$('#person_file').val()) {
					$(this).alertmsg('warn', '请选择需要导入的Excel文件！');
					$('#person_file').focus();
					return false;
				}else{
					$('#person_import_form').attr('action','${ctx }/core/train/project/person/checkExcel/'+${pid});
					$('#person_import_form').bjuiajax('ajaxForm', {callback:function(json){
			    		if(json.statusCode=='300' ||json.statusCode=='301'){
			    			$(this).alertmsg('error', json.message);
			    			$('#sch_imp').trigger("click");//回退上一步
			    		}else if(json.statusCode=='350'){
			    			 var trMsg = '';
			                 $(json.data).each(function(){
			    				trMsg += '<tr>'
			            		+'<td>'
			            		+' &nbsp;&nbsp; <span class="warning"><i class="glyphicon glyphicon-info-sign"></i></span> &nbsp;&nbsp;'
		            			+ this
		            			+'</td>'
		            			+'</tr>';
		            		})
			    			var result = makeExcelOkResult(json.message,trMsg,'red');
			                 console.log(result);
			    			$('#tab3').html(result);
			    			$('#person_import_wizard').find('.bjui-pageFooter .again').show();
			    			$('#person_import_wizard').find('.bjui-pageFooter .again').off().on('click',function(){
			    				$('#sch_imp').trigger("click");//回退上一步
			    				$('#sch_imp_next').trigger("click");//再次下一步
			    			});
			    			$('#person_import_wizard').find('.bjui-pageFooter .next').addClass('disabled');
			    			$('#person_import_wizard').find('.bjui-pageFooter .next').attr('disabled',"true");
			    		}else{
			    			var result = makeExcelOkResult(json.message,'','green');
			    			$('#tab3').html(result);
			    			$('#person_import_wizard').find('.bjui-pageFooter .again').hide();
			    		}
					}})
				}
			}
			
			if(index==3) {
				$('#person_import_form').attr('action','${ctx }/core/train/project/person/importExcel/'+${pid});
				$('#person_import_form').bjuiajax('ajaxForm', {callback:function(json){
		    		if(json.statusCode=='300' ||json.statusCode=='301'){
		    			$(this).alertmsg('error', json.message);
		    			$('#sch_imp').trigger("click");//回退上一步
		    			$('#person_import_wizard').find('.bjui-pageFooter .again').show();
		    			$('#person_import_wizard').find('.bjui-pageFooter .again').off().on('click',function(){
		    				$('#sch_imp').trigger("click");//回退上一步
		    				$('#sch_imp_next').trigger("click");//再次下一步
		    			});
		    		}else if(json.statusCode=='350'){
		    			var trMsg = '';
		    			$(json.data).each(function(){
		    				trMsg += '<tr>'
		            		+'<td>'
		            		+' &nbsp;&nbsp; <span class="warning"><i class="glyphicon glyphicon-info-sign"></i></span> &nbsp;&nbsp;'
	            			+ this
	            			+'</td>'
	            			+'</tr>';
	            		})
		    			var result = makeExcelOkResult(json.message,trMsg,'red');
		    			
		    			$('#tab4').html(result);
		    			$('#sch_imp').trigger("click");//回退上一步
		    			$('#person_import_wizard').find('.bjui-pageFooter .next').addClass('disabled');
		    			$('#person_import_wizard').find('.bjui-pageFooter .next').attr('disabled',"true");
		    		}else{
		    			var result = makeExcelOkResult(json.message,'','green');
		    			$('#tab4').html(result);
		    		}
				}})
			}
		
		}, onTabShow: function(tab, navigation, index) {
			var $total = navigation.find('li').length;
			var $current = index+1;
			var $percent = ($current/$total) * 100;
			$('#person_import_wizard').find('.bar').css({width:$percent+'%'});
			
			// If it's the last tab then hide the last button and show the finish instead
			if($current >= $total) {
				$('#person_import_wizard').find('.bjui-pageFooter .pre').hide();
				$('#person_import_wizard').find('.bjui-pageFooter .next').hide();
				$('#person_import_wizard').find('.bjui-pageFooter .finish').show();
				$('#person_import_wizard').find('.bjui-pageFooter .finish').removeClass('disabled');
				$('#person_import_wizard').find('.bjui-pageFooter .next').removeAttr("disabled");
			} else {
				$('#person_import_wizard').find('.bjui-pageFooter .pre').show();
				$('#person_import_wizard').find('.bjui-pageFooter .next').show();
				$('#person_import_wizard').find('.bjui-pageFooter .next').removeAttr("disabled");
				$('#person_import_wizard').find('.bjui-pageFooter .finish').hide();
			}
			
		},onTabClick: function(tab, navigation, index) {
			return false;
		}
  	});
  	
  	
  	$('#person_import_wizard .again').click(function() {
	});
  	
	$('#person_import_wizard .finish').click(function() {
		$(this).dialog('closeCurrent');
		$('#project_person_search_btn').trigger('click');
	});
	
	function checkPersonSize(size){
        if(size>SYS_FILE_SIZE){
        		$(this).alertmsg('warn','文件最大为50M.\n\n请重新选择文件或进行分批处理 .');
                $('#person_file').val('');
                return false;
        }
    }
    function checkPersonFile(obj){
          var file=$(obj).val();
          var ext=file.substr(file.lastIndexOf('.')+1);
          if(!(ext=='xlsx' || ext=='xls')){
        	  	$(this).alertmsg('warn','只允许后缀为.xlsx或.xls的Excel文件,请重新选择文件.');
                $(obj).val('');
                return ;
          }
          if(obj.files){    
                var size=obj.fileSize || obj.files[0].fileSize || obj.files[0].size;
                checkPersonSize(size);
          }
     }
</script>
<style>

</style>
<div id="person_import_wizard" style="height:100%;">
<div class="bjui-pageContent ">
	<div class="wrapdiv2">
		  <ul class="headerdiv2" style="padding:10px;">
		  	
		  	<li><a href="#tab1" data-toggle="tab"><span class="label">1</span> 下载模板</a></li>
			<li><a href="#tab2" data-toggle="tab"><span class="label">2</span> 上传Excel文件</a></li>
			<li><a href="#tab3" data-toggle="tab"><span class="label">3</span> 验证数据有效性</a></li>
			<li><a href="#tab4" data-toggle="tab"><span class="label">4</span> 导入数据</a></li>
		</ul>
          <div class="tab-content contentdiv2" >
		    <div class="tab-pane" id="tab1" style="height:100%;overflow-y:auto;">
		    	<div style="height:100%;padding:10px;overflow-y:auto;">
		    		<a href="${ctx }/static/excel/projectPerson/ProjectPerson-tmpl.xls"
		    		data-icon="cloud" class="btn btn-dark-blue">点我！下载导入Excel模板</a>
		    	</div>
		    </div>
		    <div class="tab-pane" id="tab2" style="height:100%;overflow-y:auto;">
			        <table class="table table-condensed table-hover" style="width:100%;">
			            <tbody>
    						 <tr>
			                    <td>
			                         <form id="person_import_form"  action="" method="post" enctype="multipart/form-data" >
						                <input id="person_file"  class="file" name="file" type="file" onchange="checkPersonFile(this);">
						            </form>
			                    </td>
			                </tr>
			            </tbody>
			        </table>
		    </div>
		    
			<div class="tab-pane" id="tab3" style="height:100%;overflow-y:auto;">
		    </div>
		    
			<div class="tab-pane" id="tab4" style="height:100%;overflow-y:auto;">
		    </div>
		</div>
      </div>
	
</div>
<div class="bjui-pageFooter" style="border-top:1px #c3ced5 solid;">
	<button type="button" class="btn-dark-blue pull-left pre" id="sch_imp" data-icon="backward">上一步</button>
	<button type="button" class="btn-dark-blue pull-right next" id="sch_imp_next" >下一步 <span class="fa fa-forward"></span></button>
	<button type="button" class="btn-red pull-right again" style="display:none;">再次验证 <span class="fa fa-check-square-o"></span></button>
	<button type="button" class="btn-green pull-right next finish" data-icon="check-circle" style="display:none;">完成 </button>
</div>
</div>
