function makeExcelOkResult(headMsg, trMsg,color) {
	var result = '<table class="table table-condensed table-hover" style="width:100%;">'
			+ '<tbody>'
			+ '<tr>'
			+ '<td>'
			+ '<label class="control-label '
			+ color+'"><span class="msg-box n-right" style="" for="f_user_type">'
			+ '<span class="msg-wrap n-tip" role="alert" style="left: 0px; opacity: 1;"><span class="n-arrow"><b></b><i></i></span>'
			+ '<span class="glyphicon glyphicon-info-sign '
			+ color+'" style="top: -1px; font-size: 24px; vertical-align: middle;"></span>'
			+ '<span class="n-msg '
			+ color+'">'
			+ headMsg
			+ '</span>'
			+ '</span>'
			+ ' </span>' + '</label>' + '</td>' + '</tr>'
			+trMsg
			+'</tbody>' + ' </table>';
	return result;
}


//树下拉选择框：：选择事件
function S_NodeCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId),
		nodes = zTree.getCheckedNodes(true)
	var ids = '', names = ''

	for (var i = 0; i < nodes.length; i++) {
		ids   += ','+ nodes[i].id
		names += ','+ nodes[i].name
	}
	if (ids.length > 0) {
		ids = ids.substr(1), names = names.substr(1)
	}

	var $from = $('#'+ treeId).data('fromObj')

	if ($from && $from.length) {
		$from.val(names)
	}
	$('#'+treeId+'_id').val(ids)
	var tmpid = (treeId+'_id').replace('select_tree_id','ztree')
	$('#'+tmpid).trigger('validate')

}
//树下拉选择框：：单击事件
function S_NodeClick(event, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId)

	zTree.checkNode(treeNode, !treeNode.checked, true, true)

	event.preventDefault()
}

//查看teacher详情
function teacherView(id){
	$(this).dialog({id:'teacher-view-dialog', url:'/core/basic/teacher/base/teacherView/'+id+'?type=0', title:'教师详细信息',mask:'true',width:'1030',height:'660'});
}

//字符串去除空白
function trim(s) { return s.replace(/^\s+|\s+$/g, ""); };

//验证身份证号并获取出生日期
function getBirthdatByIdNo(iIdNo) {
	var tmpStr = "";
	var idDate = "";
	var tmpInt = 0;
	var strReturn = "";

	iIdNo = trim(iIdNo);

	if ((iIdNo.length != 15) && (iIdNo.length != 18)) {
		strReturn = "输入的身份证号位数错误";
		return strReturn;
	}

	if (iIdNo.length == 15) {
		tmpStr = iIdNo.substring(6, 12);
		tmpStr = "19" + tmpStr;
		tmpStr = tmpStr.substring(0, 4) + "-" + tmpStr.substring(4, 6) + "-" + tmpStr.substring(6)

		return tmpStr;
	} else {
		tmpStr = iIdNo.substring(6, 14);
		tmpStr = tmpStr.substring(0, 4) + "-" + tmpStr.substring(4, 6) + "-" + tmpStr.substring(6)
		return tmpStr;
	}
}

//data转换
function getDate(date){
	var dates = date.split("-");
	var dateReturn = '';
	 
	for(var i=0; i<dates.length; i++){
		dateReturn+=dates[i];
	}
	return dateReturn;
}

function getNowYYYYMMDD() {
    var date = new Date();
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentdate;
}
