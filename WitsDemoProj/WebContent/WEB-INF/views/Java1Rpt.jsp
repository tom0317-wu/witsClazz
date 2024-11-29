<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ACCESS DB 查詢範例</title>

<script type="text/javascript">

	let errMsg = '${errMsg}';
	if (errMsg != ''){
		alert(errMsg);
	}

</script>

</head>
<body>

	<form action="Java1RptRst" method="post">

		<span style="color:red">*</span>
		PG帳號<input type="text" name=empId value="${empId }"><br/>
		Jira 單號<input type="text" name="jiraNo" value="${jiraNo }"><br/>
		
		<input type="submit" value="送出">
	</form>
	<hr/>
	<table border="1">
		<tr>
			<td>idx</td>
			<td>Jira單號</td>
			<td>程式ID</td>
			<td>程式名稱</td>
			<td>PG姓名</td>
		</tr>
		<c:if test="${not empty rptData }">
			<c:forEach items="${rptData}" var="item" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${item.JIRA_NO }</td>
					<td>${item.PROG_ID }</td>
					<td>${item.PROG_NAME }</td>
					<td>${item.PG_NAME }</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</body>
</html>
