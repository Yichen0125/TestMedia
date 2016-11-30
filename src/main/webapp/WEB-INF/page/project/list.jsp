<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ include file="/commons/common.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<script type="text/javascript">
		$(function(){
			
			$("#new").click(function(){
				window.location.href="${ctp}" + "/user/toAddUI";
				return false;
			});
			
			$("img[id^='update-']").click(function(){
				var id = this.id.split("-")[1];
				
				window.location.href = "${ctp}/user/toEditUI/"+id;
			})
			
			$("img[id^='delete-']").click(function(){
				
				var flag = confirm("确认删除吗");
				if(flag){
					var id = this.id.split("-")[1];
					var thisImg = $(this);
					
					var url = "${ctp}/user/"+id;
					var args = {"_method":"DELETE","time":new Date()};
					
					$.post(url,args,function(data){
						if(data == 1){
							alert("删除成功");
							thisImg.parent().parent().remove();
						}
						
					});
				}
			})
		})
	</script>
</head>

<body class="main">
<center>
	<form action="${ctp }/xm/pro/list">
	<br>
	<br>
	<br>
	<br>
	
	<br>
	<br>
	<table border="1" cellpadding="10" cellspacing="0">
		<tr>
				<th>
					项目名称	
				</th>
				<td>
					<input type="text" name="search_LIKES_name" value="${param.search_LIKES_name }"/>
				</td>
				<th>
					项目编号
				</th>
				<td>
					<input type="text" name="search_LIKES_pronum" value="${param.search_LIKES_pronum }"/>
				</td>
				<td colspan="3" align="center">
				<button class="common_button" onclick="document.forms[1].submit();">
				查询
				</button>
				</td>
		</tr>
		<tr>
					<th>
						项目编号
					</th>
					<th>
						项目名称
					</th>
					<th>
						负责人
					</th>
					<th>
						状态
					</th>
					<th>
						创建时间
					</th>
					<th>
						要求
					</th>
					<th>
						操作
					</th>
			</tr>
			<c:if test="${empty page.content }">
				<tr>
					<td colspan="7">暂时没有项目</td>
				</tr>
			</c:if>
			<c:if test="${!empty page.content }">
				<c:forEach var="project" items="${page.content }">
					<tr>
						<td>
						${project.pronum}
						</td>
						<td>
						${project.name}
						</td>
						<td>
						${project.user.name}
						</td>
						<td>
						${project.status}
						</td>
						<td>
							<fmt:formatDate value="${project.date }"/>
						</td>
						<td>
						${project.yq}
						</td>
						<td>
							<input type="button" value="删除">
						</td>
					</tr>
					
				</c:forEach>
			</c:if>
					<tr>
						<td colspan="7" align="center">
							<tags:page page="${page }"></tags:page>
						</td>
					</tr>
	</table>

	</form>
</center>
</body>
</html>
