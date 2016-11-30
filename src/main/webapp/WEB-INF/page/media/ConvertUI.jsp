<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/commons/common.jsp" %>
<c:set var="ctp" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctp }/static/jquery/jquery-3.1.0.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$(".convert").click(function(){
			var id = $(this).next().val();
			var $td = $(this).parent();
				 $(this).replaceWith("<b>进行中</b>");
       		var url = "${ctp }/xm/pro/sp/convert/"+id;
       		var args = {"method":"POST","time":new Date()};
       		$.post(url,args,function(data){
				if(data != 0){
					$td.find("b").replaceWith("<b>完成</b>");
				}else{
					alert("转码文件过多或系统异常！");
					$td.find("b").replaceWith("<input type='submit' value='转码' class='convert' >"
											+"<input type='hidden' value='"+id+"' class='mediaId'> ");
				}
			});
			})
				$(".freeConverts").click(function(){
					var thisBtn = this;
					var id = $(this).next(":hidden").val();
					var url = "${ctp }/xm/pro/sp/convertArea/"+id;
					var args = {"method":"POST","time":new Date()};
					$.post(url,args,function(data){
						if (data == "1"){
							var stat = $(thisBtn).val();
							if (stat == "移除待转码列表"){
								$(thisBtn).val("添加到转码列表");
							}
							if (stat == "添加到转码列表"){
								$(thisBtn).val("移除待转码列表");
							}
						}
					})
				})
			
		})
	</script>
<title>Insert title here</title>
</head>
<body>
	<center>
		<br>
		<br>
		<table border="1" cellpadding="10" cellspacing="0">
		<tr>
			<td colspan="6" align="center">
				<h3>未转码视频列表</h3>
			</td>
		</tr>
		
		<tr>
			<td>视频名称：</td>
			<td>视频地址：</td>
			<td>视频描述：</td>
			<td>上传时间：</td>
			<td>转码：</td>
			<td>转码区：</td>
		</tr>
		
		<c:if test="${empty medias }">
			<tr>
				<td colspan="6" align="center">
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
							<input type="submit" value="转码" class="convert" >
							<input type="hidden" value="${media.id }" class="mediaId"> 
					</td>
					<td>
							<c:if test="${!media.convertArea}">
								<input type="submit" value="添加到转码列表" class="freeConverts">
								<input type="hidden" value="${media.id }"> 
							</c:if>
							<c:if test="${media.convertArea}">
								<input type="submit" value="移除待转码列表" class="freeConverts">
								<input type="hidden" value="${media.id }"> 
							</c:if>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	<a href="${ctp }/xm/pro/sp/list">返回</a>
	</center>
</body>
</html>