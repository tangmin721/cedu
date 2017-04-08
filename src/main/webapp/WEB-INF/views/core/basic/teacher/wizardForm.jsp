<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
	var tfs_type = ${schoolType};
	var wizard_tid = undefined;
  	$('#teacher_form_wizard').bootstrapWizard({tabClass: 'bwizard-steps-tch',
  			firstSelector:'.first',
  			nextSelector: '.next', 
  			previousSelector: '.pre',
  			finishSelector : '.finish'
  		 ,onNext: function(tab, navigation, index) {
  			// alert("onNext-index:"+index);
  			if(index==1) {
  				var passflag = false;
  				$('#f_tw_base_form').isValid(function(v){
  					passflag = v;
  				    if(v){
  				    	$('#f_tw_base_form').bjuiajax('ajaxForm', {callback:function(json){
  				    		console.log(json);
  				    		if(json.statusCode!='200'){
  				    			$(this).alertmsg('error', json.message);
  				    			$('#tw_first').trigger("click");//回退到基础信息（第一步）
  				    		}else{
  				    			//更新tid,tno
  				    			wizard_tid = json.data.tid;
  				    			$('#f_tw_base_form input[name="id"]').val(wizard_tid);
  				    			$('#f_tw_base_form input[name="tno"]').val(json.data.tno);
  				    			$('#f_tw_base_form input[name="version"]').val(json.data.version);
  				    			$('.teacher_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/base/teacherList'});
  				    			//刷新 其他tab页的tid
			    				//刷新 2 学习经历
			    				$('#div_tw_study_exp').bjuiajax('refreshLayout', {
			    					target:'#div_tw_study_exp',
			    					//url:"${ctx}/core/basic/teacher/studyExp/teacherStudyExpForm/"+wizard_tid
			    					url:"${ctx}/core/basic/teacher/studyExp/teacherStudyExpList/"+wizard_tid
			    				});
			    				
			    				//刷新 3 工作经历
			    				$('#div_tw_work_exp').bjuiajax('refreshLayout', {
			    					target:'#div_tw_work_exp',
			    					url:"${ctx}/core/basic/teacher/workExp/teacherWorkExpList/"+wizard_tid
			    				});
			    				
			    				//刷新 4  岗位聘任
			    				$('#div_tw_post_engage').bjuiajax('refreshLayout', {
			    					target:'#div_tw_post_engage',
			    					url:"${ctx}/core/basic/teacher/postEngage/teacherPostEngageList/"+wizard_tid+"/"+tfs_type
			    				});
			    				
			    				//刷新 5 专业技术职务聘任  
			    				$('#div_tw_profession_tech').bjuiajax('refreshLayout', {
			    					target:'#div_tw_profession_tech',
			    					url:"${ctx}/core/basic/teacher/professionTechnical/professionTechnicalList/"+wizard_tid
			    				});
			    				
			    				//刷新 6 基本待遇  
			    				$('#div_tw_tch_pay').bjuiajax('refreshLayout', {
			    					target:'#div_tw_tch_pay',
			    					url:"${ctx}/core/basic/teacher/teacherPay/teacherPayList/"+wizard_tid+"/"+tfs_type
			    				});
			    				
			    				//刷新 7 年度考核
			    				$('#div_tw_assess').bjuiajax('refreshLayout', {
			    					target:'#div_tw_assess',
			    					url:"${ctx}/core/basic/teacher/assess/teacherAssessList/"+wizard_tid
			    				});
			    				
			    				//刷新 8 教师资格
			    				$('#div_tw_tch_qualify').bjuiajax('refreshLayout', {
			    					target:'#div_tw_tch_qualify',
			    					url:"${ctx}/core/basic/teacher/teacherQualification/teacherQualificationList/"+wizard_tid
			    				});
			    				
			    				//刷新 9 师德信息
			    				$('#div_tw_tch_morality').bjuiajax('refreshLayout', {
			    					target:'#div_tw_tch_morality',
			    					url:"${ctx}/core/basic/teacher/teacherMorality/teacherMoralityList/"+wizard_tid
			    				});
			    				
			    				//刷新10 教育教学
			    				$('#div_tw_education').bjuiajax('refreshLayout', {
			    					target:'#div_tw_education',
			    					type:'post',
			    					url:"${ctx}/core/basic/teacher/teacherEducation/teacherEducationList/"+wizard_tid+"/"+tfs_type
			    				});
			    				
			    				//刷新 11 入选人才项目
			    				$('#div_tw_talent_project').bjuiajax('refreshLayout', {
			    					target:'#div_tw_talent_project',
			    					url:"${ctx}/core/basic/teacher/teacherTalentProject/teacherTalentProjectList/"+wizard_tid
			    				});
			    				
			    				//刷新 12 国内培训  
			    				$('#div_tw_train_exp').bjuiajax('refreshLayout', {
			    					target:'#div_tw_train_exp',
			    					//url:"${ctx}/core/basic/teacher/trainExp/teacherTrainExpForm/"+wizard_tid
			    					url:"${ctx}/core/basic/teacher/trainExp/teacherTrainExpList/"+wizard_tid
			    				});
			    				
			    				//刷新 13 海外研修（访学）
			    				$('#div_tw_oversea_study').bjuiajax('refreshLayout', {
			    					target:'#div_tw_oversea_study',
			    					url:"${ctx}/core/basic/teacher/overseasStudy/teacherOverseasStudyList/"+wizard_tid
			    				});
			    				
			    				//刷新 14 技能及证书  
			    				$('#div_tw_credent').bjuiajax('refreshLayout', {
			    					target:'#div_tw_credent',
			    					url:"${ctx}/core/basic/teacher/credent/teacherCredentList?tid="+wizard_tid
			    				});
			    				
			    				//刷新15 交流轮岗
			    				$('#div_tw_commun_rotate').bjuiajax('refreshLayout', {
			    					target:'#div_tw_commun_rotate',
			    					url:"${ctx}/core/basic/teacher/communRotate/teacherCommunRotateList/"+wizard_tid+"/"+tfs_type
			    				});
			    				
			    				//刷新16 联系方式
			    				$('#div_tw_contact_way').bjuiajax('refreshLayout', {
			    					target:'#div_tw_contact_way',
			    					url:"${ctx}/core/basic/teacher/contactWay/teacherContactWayList/"+wizard_tid+"/"+tfs_type
			    				});
			    				
			    				//刷新17 学时学分  
			    				$('#div_tw_score').bjuiajax('refreshLayout', {
			    					target:'#div_tw_score',
			    					url:"${ctx}/core/basic/teacher/score/teacherScoreList?tid="+wizard_tid
			    				});
			    				
			    				//刷新2
	 				    		//$('#div_tw_academic_exch').bjuiajax('refreshLayout', {
	 				    		//	target:'#div_tw_academic_exch',
	 				    		//	type:'post',
	 				    		//	url:"${ctx}/core/basic/teacher/academicExchange/teacherAcademicExchangeList/"+wizard_tid
	 				    		//});
			    				
			    				//刷新3
			    				//$('#div_tw_sbuject_study').bjuiajax('refreshLayout', {
			    				//	target:'#div_tw_sbuject_study',
			    				//	type:'post',
			    				//	url:"${ctx}/core/basic/teacher/subjectStudy/teacherSubjectStudyList/"+wizard_tid
			    				//});
			    				
			    				//刷新 7  
			    				//$('#div_tw_award').bjuiajax('refreshLayout', {
			    				//	target:'#div_tw_award',
			    				//	url:"${ctx}/core/basic/teacher/award/teacherAwardForm/"+wizard_tid
			    				//});

			    				//刷新 8  
			    				//$('#div_tw_academic').bjuiajax('refreshLayout', {
			    				//	target:'#div_tw_academic',
			    				//	//url:"${ctx}/core/basic/teacher/academic/teacherAcademicForm/"+wizard_tid
			    				//	url:"${ctx}/core/basic/teacher/academic/teacherAcademicList/"+wizard_tid
			    				//});

			    				//刷新 12 
			    				//$('#div_tw_punish').bjuiajax('refreshLayout', {
			    				//	target:'#div_tw_punish',
			    				//	url:"${ctx}/core/basic/teacher/punish/teacherPunishForm/"+wizard_tid
			    				//});
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
			$('#teacher_form_wizard').find('.bar').css({width:$percent+'%'});
			
			$('#teacher_form_wizard').find('.bjui-pageFooter .first').hide();//第一步的按钮隐藏
			// If it's the last tab then hide the last button and show the finish instead
			if($current >= $total) {
				$('#teacher_form_wizard').find('.bjui-pageFooter .pre').hide();
				$('#teacher_form_wizard').find('.bjui-pageFooter .next').hide();
				$('#teacher_form_wizard').find('.bjui-pageFooter .finish').show();
				$('#teacher_form_wizard').find('.bjui-pageFooter .finish').removeClass('disabled');
			} else {
				$('#teacher_form_wizard').find('.bjui-pageFooter .next').show();
				$('#teacher_form_wizard').find('.bjui-pageFooter .finish').hide();
				$('#teacher_form_wizard').find('.bjui-pageFooter .pre').show();
			}
			if($current == 1){
				$('#teacher_form_wizard').find('.bjui-pageFooter #save_base_info').show();
				$('#teacher_form_wizard').find('.bjui-pageFooter .pre').hide();
			}else{
				$('#teacher_form_wizard').find('.bjui-pageFooter #save_base_info').hide();
			}
			
		},onTabClick: function(tab, navigation, index) {
		//	console.log(navigation);
		//	alert("click:"+navigation.context.ownerDocument.activeElement.childNodes["0"].firstChild.data);
			if(index==0){
				var flag = false;
  				$('#f_tw_base_form').isValid(function(v){
  					flag = v;
  				});
  				if(!flag){
  					return false;
  				}
				$('#tw_next').trigger("click");
			//	return false;
			}
		}
  	});
  	
	$('#teacher_form_wizard .finish').click(function() {
		$(this).alertmsg('ok', '保存信息成功！');
	 //	$('.teacher_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/base/teacherList'});
		$(this).dialog('closeCurrent');
	});
	
	//基本信息保存
	$('#teacher_form_wizard #save_base_info').click(function() {
		$('#f_tw_base_form').isValid(function(v){
				passflag = v;
			    if(v){
					$('#f_tw_base_form').bjuiajax('ajaxForm', {callback:function(json){
	    				console.log(json);
	    				if(json.statusCode!='200'){
	    					$(this).alertmsg('error', json.message);
	    					$('#tw_pre').trigger("click");//回退上一步
	    				}else{
	    					$('.teacher_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/base/teacherList'});
	    					$(this).dialog('closeCurrent');
	    				}
	    			}
					})
		
				}
			    });
			if(!passflag){
				return false;
			}
	});
	
	//监听关闭dialog  触发事件
//	$(document).on('bjui.beforeCloseDialog', function(e) {
//	    var $dialog = $(e.target)
//	    $('.teacher_pager_form').bjuiajax('doSearch', {url:'${ctx}/core/basic/teacher/base/teacherList'});
//	})

</script>
<style>
	.tchwrapdiv{margin: 0;padding:0 !important;padding:0 0 26px 0;width:100%;height:100%;overflow:hidden;position: absolute;}
	.tchwrapdiv .tchheaderdiv{width: 100%;height: 30px;overflow: hidden;position: absolute;top: 0;width: 100%;}
	.tchwrapdiv .tchcontentdiv{position:absolute!important;position:relative;bottom:26px!important;top:0;bottom:0;width:100%;overflow:hidden;height:auto!important;height:100%;}
</style>
<div id="teacher_form_wizard" style="height:100%;">
<div class="bjui-pageContent">
	<div class="row" style="height:100%;">
		<div class="col-md-2" style="height:100%;">
			<div class="panel panel-default tchwrapdiv">
				<div class="tchcontentdiv" style="overflow:auto" >
				<ul>
					<li><a href="#tab1" data-toggle="tab"><span class="label">&nbsp;&nbsp;1</span> 基本信息 <span class="red">*</span></a></li>
					<!--
                  	<li><a href="#tab2" data-toggle="tab"><span class="label">2</span> 学籍档案</a></li>
                  	<li><a href="#tab3" data-toggle="tab"><span class="label">3</span> 教学&学历</a></li>
                   	-->
					<li><a href="#tab2" data-toggle="tab"><span class="label">&nbsp;&nbsp;2</span> 学习经历 <span class="red">*</span></a></li>
					<li><a href="#tab3" data-toggle="tab"><span class="label">&nbsp;&nbsp;3</span> 工作经历</a></li>
					<li><a href="#tab4" data-toggle="tab"><span class="label">&nbsp;&nbsp;4</span> 岗位聘任</a></li>
					<li><a href="#tab5" data-toggle="tab"><span class="label">&nbsp;&nbsp;5</span> 专业技术职务聘任</a></li>
					<li><a href="#tab6" data-toggle="tab"><span class="label">&nbsp;&nbsp;6</span> 基本待遇</a></li>
					<li><a href="#tab7" data-toggle="tab"><span class="label">&nbsp;&nbsp;7</span> 年度考核</a></li><!-- 考核情况 -->
					<li><a href="#tab8" data-toggle="tab"><span class="label">&nbsp;&nbsp;8</span> 教师资格</a></li>
					<li><a href="#tab9" data-toggle="tab"><span class="label">&nbsp;&nbsp;9</span> 师德信息</a></li>
					<li><a href="#tab10" data-toggle="tab"><span class="label">10</span> 教育教学</a></li> <!-- 学术交流  -->
					<li><a href="#tab11" data-toggle="tab"><span class="label">11</span> 入选人才项目</a></li> <!-- 课题研究  -->
					<li><a href="#tab12" data-toggle="tab"><span class="label">12</span> 国内培训</a></li> <!-- 培训经历 -->
					<li><a href="#tab13" data-toggle="tab"><span class="label">13</span> 海外研修(访学)</a></li> <!-- 获奖情况  -->
					<li><a href="#tab14" data-toggle="tab"><span class="label">14</span> 技能及证书</a></li><!-- 证书管理  -->
					<li><a href="#tab15" data-toggle="tab"><span class="label">15</span> 交流轮岗</a></li> <!-- 论文/专著  -->
					<li><a href="#tab16" data-toggle="tab"><span class="label">16</span> 联系方式</a></li> <!-- 学时学分  -->
					<li><a href="#tab17" data-toggle="tab"><span class="label">17</span> 学时学分</a></li> <!-- 处分情况  -->
				</ul>
				</div>
			</div>
		</div>
		<div class="col-md-10" style="height:100%;">
			<div class="panel panel-default tchwrapdiv">
				<div class="tab-content tchcontentdiv"  style="overflow:auto">
					<div class="tab-pane" id="tab1">
						<div id="div_tw_base"
								<c:if test="${empty tid}">
									data-url="${ctx}/core/basic/teacher/base/teacherForm?type=${type }"
								</c:if>
								<c:if test="${not empty tid}">
									data-url="${ctx}/core/basic/teacher/base/teacherForm/${tid}?type=${type }"
								</c:if>
							 data-type="POST" data-toggle="autoajaxload">
						</div>
					</div>
					<!--
                    <div class="tab-pane" id="tab2" style="height:100%;overflow-y:auto;">
                         <div style="height:100%;padding:0px;overflow-y:auto;">
                            <div id="div_tw_archival" class="contentdiv"></div>
                        </div>
                    </div>

                    <div class="tab-pane" id="tab3" style="height:100%;overflow-y:auto;">
                        <div style="height:100%;padding:0px;overflow-y:auto;">
                            <div id="div_tw_edu" class="contentdiv"></div>
                        </div>
                    </div>
                     -->
                    <!-- 学习经历 -->
					<div class="tab-pane" id="tab2">
							<div id="div_tw_study_exp"></div>
					</div>
					<!-- 工作经历  -->
					<div class="tab-pane" id="tab3">
							<div id="div_tw_work_exp" ></div>
					</div>
					<!-- 岗位聘任 -->
					<div class="tab-pane" id="tab4" >
							<div id="div_tw_post_engage"></div>
					</div>
					<!-- 专业技术职务聘任 -->
					<div class="tab-pane" id="tab5" >
							<div id="div_tw_profession_tech"></div>
					</div>
					<!-- 基本待遇 -->
					<div class="tab-pane" id="tab6" >
							<div id="div_tw_tch_pay"></div>
					</div>
					<!-- 年度考核 -->
					<div class="tab-pane" id="tab7">
							<div id="div_tw_assess" ></div>
					</div>
					<!-- 教师资格 -->
					<div class="tab-pane" id="tab8" >
							<div id="div_tw_tch_qualify"></div>
					</div>
					<!-- 师德信息  -->
					<div class="tab-pane" id="tab9" >
							<div id="div_tw_tch_morality"></div>
					</div>
					<!-- 教育教学 -->
					<div class="tab-pane" id="tab10">
							<div id="div_tw_education"></div>
					</div>
					<!-- 入选人才项目 -->
					<div class="tab-pane" id="tab11" >
							<div id="div_tw_talent_project"></div>
					</div>
					<!-- 国内培训 -->
					<div class="tab-pane" id="tab12">
							<div id="div_tw_train_exp"></div>
					</div>
					<!-- 海外研修(访学) -->
					<div class="tab-pane" id="tab13" >
							<div id="div_tw_oversea_study"></div>
					</div>
					<!-- 技能及证书 -->
					<div class="tab-pane" id="tab14" >
							<div id="div_tw_credent"  ></div>
					</div>
					<!-- 交流轮岗 -->
					<div class="tab-pane" id="tab15" >
							<div id="div_tw_commun_rotate"></div>
					</div>
					<!-- 联系方式 -->
					<div class="tab-pane" id="tab16">
							<div id="div_tw_contact_way" ></div>
					</div>
					<!-- 学时学分 -->
					<div class="tab-pane" id="tab17" >
							<div id="div_tw_score"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="bjui-pageFooter" style="border-top:1px #ddd solid;">
	<button type="button" id="tw_first" class="btn-dark-blue pull-left first" data-icon="backward">第一步</button>
	<button type="button" id="tw_pre" class="btn-dark-blue pull-left pre" data-icon="backward">上一步</button>
	<button type="button" id="tw_next" class="btn-dark-blue pull-right next" >下一步 <span class="fa fa-forward"></span></button>
	<button type="button" id="save_base_info" class="btn-green pull-right" data-icon="check-circle" style="display:true;">保存</button>
	<button type="button" class="btn-green pull-right next finish" data-icon="check-circle" style="display:none;">完成 </button>
</div>
</div>
