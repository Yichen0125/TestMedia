<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctp" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${ctp }/static/jquery/jquery-3.1.0.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$(".removeById").click(function(){
					var $thisTr = $(this).parent().parent();
					console.log($thisTr);
					var id = $(this).next().val();
		       		var url = "${ctp}/media/meer/removeById/"+id;
		       		var args = {"method":"POST","time":new Date()};
		       		$.post(url,args,function(data){
		       			if (data == "1"){
		       				$thisTr.remove();
		       			}
					})
			})
		})
	</script>
</head>
<body>
	<center>
		<table border="1" cellpadding="10" cellspacing="0">
		<tr>
			<td align="center" colspan="5">
				<h3>待转码列表</h3>
			</td>
		</tr>
		<tr>
			<td>视频名称：</td>
			<td>视频地址：</td>
			<td>视频描述：</td>
			<td>上传时间：</td>
			<td>操作</td>
		</tr>
		<c:if test="${empty unConvertList }">
			<tr>
				<td colspan="5" align="center">
					<h3>暂时没有视频</h3>
				</td>
			</tr>
		</c:if>
		<c:if test="${!empty unConvertList }">
			<c:forEach items="${unConvertList }" var="media">
				<tr>
					<td>${media.title }</td>
					<td>${media.src }</td>
					<td>${media.descript }</td>
					<td><fmt:formatDate value="${media.uptime }"/></td>
					<td>
						<input type="button" class="removeById" value="移除">
						<input type="hidden" value="${media.id }">
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	</center>
</body>
</html>