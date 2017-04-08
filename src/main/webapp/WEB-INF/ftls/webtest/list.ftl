<!DOCTYPE html>
<html>
<head>
<base id="base" href="${base}">
<meta charset="utf-8" />
<title>首页</title>
</head>
<body>
	<form method="post" action="${base}/webtest/listmethod">
		<table border=1>
			<tr>
				<td>
					<div>
						<label>Service Class:</label>
					</div>
				</td>
				<td><div>
						<input type="text" name="serviceClazz"
							value="com.yanxiu.ce.test.service.TestTableService"
							style="width:400px;" />
					</div></td>
			</tr>
		</table>
		<div>
			<button type="submit">提交</button>
		</div>
	</form>
</body>
</html>