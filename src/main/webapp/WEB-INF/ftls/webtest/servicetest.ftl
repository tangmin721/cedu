<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
</head>
<body>
<form name="chooseMethodForm" method="post" action="showForm.action" target="showForm">
    <div>
        <label></label>
        <input type="text" name="serviceClazz" value="${serviceClazz}" />
    </div>
    <div>
        <label>loginType:</label><select name="serviceMethod">
            <#list methodList as m>
            <option value="${m}"></option>
            </#list>
        </select>
    </div>

    <div><button type="submit">提交</button></div>
</form>
<iframe name="showForm" id="showForm"></iframe>
</body>
</html>