<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MySQL DB 查詢範例</title>

<script type="text/javascript">
debugger;
	let errMsg = '${errMsg}';
	if (errMsg != ''){
		alert(errMsg);
	}

</script>

</head>
<body>

	<form action="doQueryCity" method="post">

		<span style="color:red">*</span>
		CountryCode
<!-- 		<select name=countryCode> -->
<%-- 			<c:forEach items="${countryCodeList }" var="item" varStatus="status"> --%>
<%-- 				<option value="${item.countrycode }">${item.countrycode }</option> --%>
<%-- 			</c:forEach> --%>
			
<!-- 		</select> -->

		<input type="text" name="countryCode" value="${countryCode }"><br/>
		<br/>
		District<input type="text" name="district" value="${district }"><br/>
		
		<input type="submit" value="送出">
	</form>
	<hr/>
	<table border="1">
		<tr>
			<td>ID</td>
			<td>Name</td>
			<td>CountryCode</td>
			<td>District</td>
			<td>Population</td>
		</tr>
		<c:if test="${not empty cityRst }">
			<c:forEach items="${cityRst}" var="item" varStatus="status">
				<tr>
					<td>${item.ID }</td>
					<td>${item.Name }</td>
					<td>${item.CountryCode }</td>
					<td>${item.District }</td>
					<td>${item.Population }</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</body>
</html>
