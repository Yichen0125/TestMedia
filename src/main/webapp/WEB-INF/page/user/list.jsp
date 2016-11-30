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
	<form action="${ctp }/user/list">
		<div class="page_title">
			用户管理
		</div>
		<div class="button_bar">
			<button class="common_button" id="new">新建</button>
			<button class="common_button" onclick="document.forms[1].submit();">
				查询
			</button>
		</div>
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title">
					用户名
				</th>
				<td class="input_content">
					<input type="text" name="search_LIKES_userName" value="${param.search_LIKES_userName }"/>
				</td>
				<th class="input_title">
					状态
				</th>
				<td class="input_content">
					<select name="search_EQS_enabled" >
						<option value="">
							全部
						</option>
						<option value="1" <c:if test="${param.search_EQS_enabled == 1 }">
							selected="selected"
						</c:if> >
							有效
						</option>
						<option value="0" <c:if test="${param.search_EQS_enabled == 0 && !empty param.search_EQS_enabled}">
							selected="selected"
						</c:if> >
							无效
						</option>
					</select>
				</td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />
		
		<c:if test="${page != null && page.totalElements > 0 }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th class="data_title" style="width: 40px;">
						编号
					</th>
					<th class="data_title" style="width: 50%;">
						用户名
					</th>
					<th class="data_title" style="width: 20%;">
						状态
					</th>
					<th class="data_title">
						操作
					</th>
				</tr>
				<c:forEach var="user" items="${page.content }">
					<tr>
						<td class="data_cell" style="text-align: right; padding: 0 10px;">
						${user.id}
						</td>
						<td id="nameLable" class="data_cell" style="text-align: center;">
						${user.name}
						</td>
						<td class="data_cell">
						${user.enabled == 1 ? "有效" : "无效"}
						</td>
						<td class="data_cell">
							<img id="delete-${user.id}"
								title="删除" src="${ctp }/static/images/bt_del.gif" class="op_button" />
							<img id="update-${user.id}"
								class="op_button" src="${ctp }/static/images/bt_edit.gif" title="编辑" />
						</td>
					</tr>
				</c:forEach>
			</table>
			<tags:page page="${page }"></tags:page>
		</c:if>
		<c:if test="${page == null || page.totalElements == 0 }">
			没有任何数据
		</c:if>
	</form>
</body>
</html>
