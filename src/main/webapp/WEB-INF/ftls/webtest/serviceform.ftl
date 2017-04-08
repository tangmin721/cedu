<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>test form</title>
<style>
input.number{border:1px solid #ccc;}
input.date{border:1px solid #000;}
label span{display:inline-block;width:180px;text-align:right;font-weight:bold;}
</style>
</head>
<body>
<table style="width:100%;">
    <tr>
        <td style="width:50%;">
            <form name="testServiceForm" method="post" action="testResult.action" target="testResult" <#if isMutipart>enctype="multipart/form-data"</#if>>
                <div>
                    <label>服务类：</label>
                    <input type="text" name="serviceClazz" value="${serviceClazz}" />
                </div>
                <div>
                    <label>服务方法：</label>
                    <input type="text" name="serviceMethod" value="${serviceMethod}" />
                </div>
                <hr />
                <#list formItems as item>
                <div>
                    <label><span>${item.name}:</span>${item.html}</label>
                </div>
                </#list>

                <div><button type="submit">提交</button></div>
            </form>
        </td>
        <td style="width:50%;">
            <iframe name="testResult" id="testResult"></iframe>
        </td>
    </tr>
</table>
</body>
</html>