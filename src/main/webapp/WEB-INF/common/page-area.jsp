<%@ page contentType="text/html;charset=UTF-8"%>
<div class="bjui-pageFooter">
    <div class="pages">
        <span>每页&nbsp;</span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize">
                <option value="5">5</option>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
            </select>
        </div>
        <span>&nbsp;共${page.total }条</span>
    </div>
    <div class="pagination-box pull-right" data-toggle="pagination" data-total="${page.total }" data-page-num="2" data-page-size="${page.pageSize }" data-page-current="${page.pageCurrent }"></div>
</div>