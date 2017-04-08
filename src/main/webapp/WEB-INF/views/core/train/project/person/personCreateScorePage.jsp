<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
	var person_timer = null;
	var person_progress_flag = false;//启动线程标记
	var person_progress_flag_end = false;//可以结束线程标记
	var person_progress_flag_ing = false;//正在执行线程标记
	var person_propgress_totalNum = 0;
	var person_propgress_currentNum = 0;
	var person_propgress_redis_key = "";
	function showProgress(pid)
	{
		if(!person_progress_flag_end){
			if(person_progress_flag && person_propgress_totalNum>0){
				console.log(2);
				var thepercent = person_propgress_currentNum/person_propgress_totalNum*100;
				console.log(thepercent);
				var tmp = parseFloat(thepercent).toFixed(0);
				console.log(tmp);
				$(".progress-my-val").text(tmp+"%");
				$(".progress-my-in").css("width",tmp+"%");

				$(".person_current_num_cs").text(person_propgress_currentNum);
				$(".person_total_num_cs").text(person_propgress_totalNum);

				//只执行一次生成学时线程
				if(person_progress_flag_ing){
					console.log(3);
					person_progress_flag_ing = false;//关闭ing 只让他执行一次
					$.ajax({
						type: "POST",
						url: "${ctx}/core/train/project/person/makeScore/"+pid+"/"+person_propgress_redis_key,
						dataType : "json",
						success: function() {
						}
					});
				}

				//循环调用直到生成完成
				if(person_propgress_currentNum<person_propgress_totalNum){
					console.log(4+":"+person_propgress_currentNum);
					console.log(4);
					$.ajax({
						type: "POST",
						async: false,
						url: "${ctx}/core/train/project/person/getCurrentNum/"+person_propgress_redis_key,
						dataType : "json",
						success: function(data) {
							console.log(data.data);
							person_propgress_currentNum = data.data.currentNum;
						},
						error :  function(){
							$(this).alertmsg('error', '系统错误4！');
						}
					});
					person_timer = setTimeout("showProgress('"+pid+"')", 500);
				}else{
					console.log(5);
					clearTimeout(person_timer);
					$('#person-cancel-score-btn').hide();
					$("#person-make-score-dispaly-btn").hide();
					$('#person-success-score-btn').show();

					$(".person_confirm").hide();
					$(".person_ing").hide();
					$(".person_end").show();
					person_progress_flag_end = true;
				}
			}else{
				$(".progress-my").show();
				$(".person_confirm").hide();
				$(".person_ing").show();

				$("#person-make-score-btn").hide();
				$("#person-make-score-dispaly-btn").show();
				$('#person-close-score-btn').hide();
				$('#person-cancel-score-btn').show();

				$.ajax({
					type: "POST",
					async: false,
					url: "${ctx}/core/train/project/person/startMakeScore/"+pid,
					dataType : "json",
					success: function(data) {
						console.log(data.data);
						person_propgress_totalNum = data.data.totalNum;
						person_propgress_redis_key = data.data.redisKey;
						person_progress_flag = true;//开始线程
						person_progress_flag_ing = true;//只调用一次的方法开始可以调了
						console.log(1);
						showProgress(pid);
					},
					error :  function(){
						$(this).alertmsg('error', '系统错误1！');
					}
				});
			}
		}
	}

	//暂不授予按钮
	$('#person-close-score-btn').on('click',function () {
		if(person_timer){
			clearTimeout(person_timer);
		}
		$(this).dialog('close','np-org-person-create-page');
	});

	$('#person-cancel-score-btn').on('click',function () {
		if(person_timer){
			clearTimeout(person_timer);
		}
		$.ajax({
			type: "POST",
			async: false,
			url: "${ctx}/core/train/project/person/cancelMakeScore/"+${pid}+"?redisKey="+person_propgress_redis_key,
			dataType : "json",
			success: function(data) {
				$(this).alertmsg('ok', '取消成功！');
			},
			error :  function(){
				$(this).alertmsg('error', '系统错误！');
			}
		});
		$(this).dialog('close','np-org-person-create-page');
	});

	$('#person-success-score-btn').on('click',function () {
		if(person_timer){
			clearTimeout(person_timer);
		}
		$(this).dialog('close','np-org-person-create-page');
		$('#project_person_search_btn').trigger('click');
	});

</script>
<style>
	.person_info{
		padding:10px;font-size: large;color: #000000;text-align:center;
	}

	.person_current_num_cs{
		font-size: large;color: red;
	}

	.person_total_num_cs{
		font-size: large;color: green;
	}
	/*
 * Copyright (c) 2013 Thibaut Courouble
 * http://www.cssflow.com
 *
 * Licensed under the MIT License:
 * http://www.opensource.org/licenses/mit-license.php
 */

	.progress-my {
		overflow: hidden;
		margin: 0 auto;
		padding: 0 15px;
		width: 220px;
		height: 34px;
		background: #d3d5d9;
		border-radius: 17px;
		background-image: -webkit-linear-gradient(top, #ebecef, #bfc3c7);
		background-image: -moz-linear-gradient(top, #ebecef, #bfc3c7);
		background-image: -o-linear-gradient(top, #ebecef, #bfc3c7);
		background-image: linear-gradient(to bottom, #ebecef, #bfc3c7);
		-webkit-box-shadow: inset 0 1px rgba(255, 255, 255, 0.8), 0 2px 4px rgba(0, 0, 0, 0.35), 0 0 0 1px rgba(0, 0, 0, 0.1), 0 0 0 6px #b6babe, 0 7px rgba(255, 255, 255, 0.1);
		box-shadow: inset 0 1px rgba(255, 255, 255, 0.8), 0 2px 4px rgba(0, 0, 0, 0.35), 0 0 0 1px rgba(0, 0, 0, 0.1), 0 0 0 6px #b6babe, 0 7px rgba(255, 255, 255, 0.1);
	}

	.progress-my-val {
		float: right;
		margin-left: 15px;
		font: bold 15px/34px Helvetica, Arial, sans-serif;
		color: #333;
		text-shadow: 0 1px rgba(255, 255, 255, 0.6);
	}

	.progress-my-bar {
		display: block;
		overflow: hidden;
		height: 8px;
		margin: 13px 0;
		background: #b8b8b8;
		border-radius: 4px;
		background-image: -webkit-linear-gradient(top, rgba(0, 0, 0, 0.2), transparent 60%);
		background-image: -moz-linear-gradient(top, rgba(0, 0, 0, 0.2), transparent 60%);
		background-image: -o-linear-gradient(top, rgba(0, 0, 0, 0.2), transparent 60%);
		background-image: linear-gradient(to bottom, rgba(0, 0, 0, 0.2), transparent 60%);
		-webkit-box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.2), 0 1px rgba(255, 255, 255, 0.6);
		box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.2), 0 1px rgba(255, 255, 255, 0.6);
	}

	.progress-my-in {
		display: block;
		min-width: 8px;
		height: 8px;
		background: #1997e6;
		background-image: -webkit-linear-gradient(top, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 60%, rgba(0, 0, 0, 0) 61%, rgba(0, 0, 0, 0.2)), -webkit-linear-gradient(left, #147cd6, #24c1fc);
		background-image: -moz-linear-gradient(top, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 60%, rgba(0, 0, 0, 0) 61%, rgba(0, 0, 0, 0.2)), -moz-linear-gradient(left, #147cd6, #24c1fc);
		background-image: -o-linear-gradient(top, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 60%, rgba(0, 0, 0, 0) 61%, rgba(0, 0, 0, 0.2)), -o-linear-gradient(left, #147cd6, #24c1fc);
		background-image: linear-gradient(to bottom, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 60%, rgba(0, 0, 0, 0) 61%, rgba(0, 0, 0, 0.2)), linear-gradient(to right, #147cd6, #24c1fc);
		border-radius: 4px;
		-webkit-box-shadow: inset 0 1px rgba(0, 0, 0, 0.2), inset 0 0 0 1px rgba(0, 0, 0, 0.2);
		box-shadow: inset 0 1px rgba(0, 0, 0, 0.2), inset 0 0 0 1px rgba(0, 0, 0, 0.2);
	}

</style>
<div class="bjui-pageContent ">
	<div class="person_info person_confirm">该操作将为当前项目下成绩合格的所有教师进行对应的项目学时授予，您确定要进行此项操作吗？</div>
	<div class="person_info person_ing" style="display:none;">请稍后，该项目下成绩合格的老师学时批量授予中...<span class="person_current_num_cs"></span>/<span class="person_total_num_cs"></span></div>
	<div class="person_info person_end" style="display:none;">该项目下合格教师学时数据授予完成！</div>
	<div class="progress-my" style="display:none;">
		<span class="progress-my-val">0%</span>
		<span class="progress-my-bar"><span class="progress-my-in" style="width: 0"></span></span>
	</div>
</div>
<div class="bjui-pageFooter">
	<div style="text-align:center;">
		<button type="button" id="person-make-score-dispaly-btn" class="btn btn-green" data-icon="check-circle" style="display:none;" disabled>确定</button>
		<button type="button" id="person-make-score-btn" class="btn btn-green" data-icon="check-square-o" onclick="showProgress(${pid})">确定</button>
		<button type="button" id="person-close-score-btn" class="btn btn-red" data-icon="rotate-right">暂不授予</button>
		<button type="button" id="person-cancel-score-btn" class="btn btn-red" data-icon="rotate-right" style="display:none;" >取消</button>
		<button type="button" id="person-success-score-btn" class="btn btn-success" data-icon="check-circle" style="display:none;">该项目下合格教师学时数据全部授予完成！</button>
	</div>
</div>

