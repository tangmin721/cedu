<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<script type="text/javascript">
	$(function(){
	})
</script>

<script type="text/javascript">
$('#test-datagrid').datagrid({
    gridTitle : 'datagrid 完整示例',
    showToolbar: true,
    toolbarItem: 'all',
    local: 'remote',
    dataUrl: '${ctx}/test/list.json',
    dataType: 'json',
    sortAll: true,
    filterAll: true,
    columns: [
        {
            label: '继承的信息',
            columns: [{
                name: 'createTime',
                label: '创建日期',
                hide:   true,
                align: 'center',
                width: 130,
                type: 'date',
                pattern: 'yyyy-MM-dd HH:mm:ss',
                render: function(value) {
                    return value ? value.substr(0, 16) : value
                }
            },
            {
                name: 'deleted',
                label: '删除标记',
                hide:   true,
                align: 'center',
                width: 50,
                type : 'select',
                attrs: {readonly:'readonly'},
                items: [{'0':'正常'},{'1':'已删除'} ],
                render: $.datagrid.renderItem
            }]
        },
        {
            label: '个人信息',
            columns: [{
                name: 'name',
                label: '姓名',
                type: 'string',
                align: 'center',
                rule: 'required',
                width: 70
            },
            {
                name: 'sex',
                label: '性别',
                align: 'center',
                width: 50,
                type : 'boolean',
                attrs: {readonly:'readonly'},
                items: [{'0':'男'}, {'1':'女'}],
                render: $.datagrid.renderItem
            },
            {
                name: 'age',
                label: '年龄',
                align: 'center',
                type : 'spinner',
                //calc: 'avg',
                //calcTit: '平均年龄',
                //calcDecimal: 2,
                width: 45
            },
            {
                name: 'birthday',
                label: '生日',
                align: 'center',
                type: 'date',
                width: 100,
                pattern: 'yyyy-MM-dd',
                render: function(value) {
                    return value ? value.substr(0, 16) : value
                }
             },
             {
                 name: 'email',
                 label: 'Email',
                 align: 'center',
                 type : 'string',
                 //calc: 'avg',
                 //calcTit: '平均年龄',
                 //calcDecimal: 2,
                 width: 45
             },
             {
                 name: 'price',
                 label: '价格',
                 align: 'center',
                 //calc: 'avg',
                 //calcTit: '平均年龄',
                 //calcDecimal: 2,
                 width: 45
             },
            
            ]
        },
        {
            name: 'memo',
            label: '备注',
            align: 'center',
            type : 'textarea',
            width: 70
        }
       
    ],
    hiddenFields: [{name:'deptcode'}],
    editUrl: '${ctx}/static/ajaxDone1.html',
    importOption: {type:"dialog", options:{url:'test/input', width:400, height:600}},
    exportOption: {type:"ajax", options:{url:"test/input"}},
    paging: {pageSize:10, selectPageSize:'10,20,50,100,500,1000',showPagenum:10,pageCurrent:1},
    showCheckboxCol: true,
    showEditBtnsCol: true,
    linenumberAll: true,
    fullGrid:true
})
</script>
<div class="bjui-pageContent">
    <div>
        <div class="row">
		  <div class="col-md-6">
		  	<table id="test-datagrid" data-width="100%" data-height="500" class="table table-bordered">
        	</table>
          </div>
		</div>
    </div>
</div>