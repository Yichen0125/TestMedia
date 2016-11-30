<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
	<c:if test="${!empty requestScope.message }">
		<script type="text/javascript">
			alert(requestScope.message);
		</script>
	</c:if>
	<br>
	<br>
	<a href="${ctp }/xm/pro/sp/add"><input type="button" value="添加视频"></a>
	<br>
	<br>
	<a href="${ctp }/xm/pro/sp/toConvertUI"><input type="button" value="查看未转码视频"></a>
	<br>
	<br>
	<table border="1" cellpadding="10" cellspacing="0">
		<tr>
			<td colspan="5" align="center">
				<h3>视频列表</h3>
			</td>
		</tr>
		
		<tr>
			<td>视频名称：</td>
			<td>视频地址：</td>
			<td>视频描述：</td>
			<td>上传时间：</td>
			<td>转码：</td>
		</tr>
		
		<c:if test="${empty medias }">
			<tr>
				<td colspan="5" align="center">
					<h3>暂时没有视频</h3>
				</td>
			</tr>
		</c:if>
		<c:if test="${!empty medias }">
			<c:forEach items="${medias }" var="media">
				<tr>
					<td>${media.title }</td>
					<td>${media.src }</td>
					<td>${media.descript }</td>
					<td><fmt:formatDate value="${media.uptime }"/></td>
					<td>
						<c:if test="${media.convert == true }">已转码</c:if>
						<c:if test="${!media.convert == true }">未转码</c:if>
					</td>
				</tr>
			</c:forEach>							
		</c:if>
	</table>
</center>
	
</body>
</html>