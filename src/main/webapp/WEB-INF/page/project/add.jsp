<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
<br>
<br>
<br>
<br>
<form action="${ctp }/xm/pro/add" method="post" enctype="multipart/form-data">
	<table cellpadding="10" cellspacing="0" border="1">
			<%-- <tr>
				<td>项目名：</td>
				<td><input type="text" name="search_LIKES_userName" value="${param.search_LIKES_userName }"/></td>
				<td> </td>
				<td> </td>
				<td><input type="button" value="查询"></td>
			</tr> --%>
			<tr>
				<td colspan="5"><h3>新建项目</h3></td>
			</tr>
			
			<tr>
				<td>客户：</td>
				<td><input type="text" name="cus"/></td>
			</tr>
			<tr>
				<td>项目编号：</td>
				<td><input type="text" name="pronum" value="${pronum }" disabled="disabled"/>
					<input type="hidden" name="pronum" value="${pronum }"></td>
				
			</tr>
			<tr>
				<td>项目名称：</td>
				<td><input type="text" name="name"/></td>
			</tr>
			<tr>
				<td>项目文件：</td>
				<td><input type="file" name="Myfile"/></td>
			</tr>
			<tr>
				<td>项目负责人：</td>
				<td>
					<select name="user.name">
						<c:forEach items="${userList }" var="user">
							<option>${user.name } </option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>项目成员：</td>
				<td>
					<c:forEach items="${empList }" var="emp">
						<input name="empList.employee.empName" type="checkbox" value="${emp.id }"/>${emp.empName }
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>项目要求：</td>
				<td><input type="text" name="yq"/></td>
			</tr>
			<tr>
				<td colspan="5" align="center"><input type="submit" value="保存订单"></td>
			</tr>
	</table>
</form>
</center>
</body>
</html>