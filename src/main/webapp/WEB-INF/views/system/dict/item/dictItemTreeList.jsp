<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<style>
	.wrapdiv5{margin: 0;padding:0 !important;padding:1px 0 29px 0;width:100%;height:100%;overflow:hidden;position: absolute;}
	.wrapdiv5 .headerdiv5{width: 100%;height: 1px;overflow: hidden;position: absolute;top: 0;width: 100%;}
	.wrapdiv5 .contentdiv5{position:absolute!important;position:relative;top:1px!important;top:0;bottom:0;width:100%;bottom:29px;overflow:hidden;height:auto!important;height:100%;}
</style>
<script type="text/javascript">

	$('#dict-item-add-root').on('click', function(e) {
		var list = dictCatlog.selectedIds();
		if(list.length==0){
			$(this).alertmsg('warn', '请选择左侧字典目录的行！');
		}else if(list.length>1){
			$(this).alertmsg('warn', '只能选择一行字典目录进行！');
		}else{
			var catlogId = list[0];
			$(this).dialog({id:'dict_item_form', url:'${ctx}/system/dict/item/dictItemTreeForm', data:{catlogId:catlogId},type:'POST',title:'添加字典项',mask:'true',width:'400',height:'260'});
		}
	});

	window.objDictItem = {
		selectedId : undefined,
		changeZtree: function(initExpandAll) {
			var $panel = $('#div_sys_dict_item');
			var $tree  = $('#dict-item-ztree');
			var $span  = $('#sys-dict-item-ztree-span');
			var $i  = $('#sys-dict-item-ztree-i');
			var op     = $tree.data();
			var expand = true;
			if(initExpandAll===1){
				$tree.data('expandAll', expand);
			}else{
				expand = op.expandAll ? false : true;
				$tree.data('expandAll', expand);
			}

			$tree.data('addHoverDom','edit');
			$tree.data('removeHoverDom','edit');

			if(expand){
				$span.text('收缩');
				$i.removeClass('fa-plus-square-o');
				$i.addClass('fa-minus-square-o');
			}else{
				$span.text('展开');
				$i.removeClass('fa-minus-square-o');
				$i.addClass('fa-plus-square-o');
			}
			$panel.initui();
		}
	}

	//初始化树展开
	objDictItem.changeZtree(1);

	//单击事件
	function dictItemZtreeClick(event, treeId, treeNode) {
		event.preventDefault();
		objDictItem.selectedId = treeNode.id;

		var $detail = $('#ztree-detail')
		if ($detail.attr('tid') == treeNode.tId) return
		console.log(treeNode)
		if (treeNode.name) $('#j_item_name').val(treeNode.name)

		if (treeNode.seq) {
			$('#j_item_seq').val(treeNode.seq)
		} else {
			$('#j_item_seq').val('')
		}

		if (treeNode.code) {
			$('#j_item_code').val(treeNode.code)
		} else {
			$('#j_item_code').val('')
		}
		if (treeNode.memo) {
			$('#j_item_memo').val(treeNode.memo)
		} else {
			$('#j_item_memo').val('')
		}
		$detail.show()
	}

	function MenuZtreeOk(){
		alert(123);
	}

	//保存属性
	function M_Ts_Menu() {
		var zTree  = $.fn.zTree.getZTreeObj("dict-item-ztree")
		var seq   = $.trim($("#j_item_seq").val())
		var name   = $('#j_item_name').val()
		var code    = $('#j_item_code').val()
		var memo  = $('#j_item_memo').val()

		var re = /^\d+$/;

		if(!re.test(seq)){
			$("#j_item_seq").focus();
			$(this).alertmsg('error', '序号必须为数字！');
			return;
		}

		if ($.trim(name).length == 0) {
			$(this).alertmsg('error', '字典项名称不能为空！');
			return;
		}
		var upNode = zTree.getSelectedNodes()[0]

		if (!upNode) {
			$(this).alertmsg('error','未选中任何节点！');
			return
		}
		upNode.seq   = seq
		upNode.name   = name
		upNode.code    = code
		upNode.memo  = memo
		zTree.updateNode(upNode)
		$(this).alertmsg('ok','更新成功！');
	}

	function saveItemTree() {
		var treeObj = $.fn.zTree.getZTreeObj("dict-item-ztree");
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		var itemsJson = JSON.stringify(nodes);
		console.log(itemsJson)
		$.ajax({
			type: "POST",
			url: '${ctx}/system/dict/item/saveDictItemTree/'+${catlogId},
			data:{
				itemsJson :itemsJson
			},
			dataType : "json",
			success: function(data) {
				$("#div_sys_dict_item").bjuiajax('refreshLayout', {
					target:$('#div_sys_dict_item'),
					data: {catlogId:${catlogId}}
				});
				$(this).alertmsg('ok', '保存成功！');
			},
			error :  function(){
				$(this).alertmsg('error', '系统错误！');
			}
		});
	}

</script>

<div style="padding:5px 0 0 5px;">
	<button class="btn btn-blue" onclick="objDictItem.changeZtree();">
		<i id="sys-dict-item-ztree-i" class="fa fa-plus-square-o"></i><span id="sys-dict-item-ztree-span">展开</span>
	</button>
	<button id="dict-item-add-root" class="btn btn-blue" data-icon="plus-circle"><span>顶级节点</span></button>
	<button class="btn btn-success" data-icon="save" onclick="saveItemTree()"><span>保存树到数据库</span></button>
</div>

<div class="clearfix wrapdiv5">
	<div style="float:left; width:220px; overflow:auto;" class="contentdiv5">
		<ul id="dict-item-ztree" class="ztree" data-toggle="ztree"
			data-options="{onClick: 'dictItemZtreeClick',onAsyncSuccess:'MenuZtreeOk'}" >
			<c:forEach items="${page.list }" var="dictItem" varStatus="index">
				<li data-id="${dictItem.id }"  data-seq="${dictItem.seq }" data-code="${dictItem.code }"  data-memo="${dictItem.memo }"
					<c:if test="${not empty dictItem.parentId }">data-pid="${dictItem.parentId }"</c:if>
				>${dictItem.name }</li>
			</c:forEach>
		</ul>
	</div>
	<div id="ztree-detail" style="display:none; margin-left:230px; width:300px; height:240px;">
		<div class="bs-example" data-content="详细信息">
			<div class="form-group">
				<label for="j_item_seq" class="control-label x100"><span class="red">*</span>排序序号：</label>
				<input type="text"  name="m.seq" id="j_item_seq"  data-rule="required;digits" size="15" data-toggle="spinner" data-min="0" data-max="999" data-step="1" data-rule="integer">
			</div>
			<div class="form-group">
				<label for="j_item_name" class="control-label x100"><span class="red">*</span>字典项名称：</label>
				<input type="text"  name="m.name" id="j_item_name" size="15"  />
			</div>
			<div class="form-group">
				<label for="j_item_code" class="control-label x100">编码：</label>
				<input type="text" class="form-control" name="m.code" id="j_item_code" size="15"  />
			</div>
			<div class="form-group">
				<label for="j_item_memo" class="control-label x100">备注：</label>
				<input type="text" class="form-control" name="m.memo" id="j_item_memo" size="15" />
			</div>
			<div class="form-group" style="padding-top:8px; border-top:1px #DDD solid;">
				<label class="control-label x100"></label>
				<button class="btn btn-green" onclick="M_Ts_Menu();">更新前端树</button>
			</div>
		</div>
	</div>
</div>