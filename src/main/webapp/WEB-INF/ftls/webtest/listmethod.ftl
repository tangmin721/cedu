<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
</head>
<body>
<form name="serviceForm" method="post" action="showForm.action" target="showForm">
    <div>
        <label>服务类</label>
        <input type="text" name="serviceClazz" value="${serviceClazz}" />

        <label>服务方法:</label><select name="serviceMethod">
            <#list methodList as m>
            <option value="${m}">${m}</option>
            </#list>
        </select>
        <button type="submit">提交</button>
    </div>
</form>
<iframe name="showForm" id="showForm" style="width:100%;height:500px;"></iframe>
</body>
</html>