<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>
<%@ taglib prefix="cmoa" tagdir="/WEB-INF/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>角色管理</title>
</head>
<body class="main">

	<div class="page_title">角色管理</div>

	<div class="button_bar">
		<button class="common_button"
			onclick="window.location.href='/cmoa/role/input'">新建</button>
	</div>

	<form action="role-list">

		<br />

			<c:if test="${page==null }">
				<table>
					<tr>
						<td>暂时没有数据</td>
					</tr>
				</table>
			</c:if>
		<table class="data_list_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="data_title">编号</th>
				<th class="data_title">角色名</th>
				<th class="data_title">角色描述</th>
				<th class="data_title">状态</th>
				<th class="data_title">操作</th>
			</tr>

			<c:if test="${page!=null }">
				<c:forEach items="${page.content }" var="role">
					<tr>
						<td class="data_cell" style="text-align: right; padding: 0 10px;">
							${role.id }
						</td>
						<td class="data_cell" style="text-align: center;">
							${role.name }
						</td>
						<td class="data_cell" style="text-align: left;">
							${role.description }
						</td>
						<td class="data_cell" style="text-align: center;">
							${role.enabled==true?"有效":"无效" }
						</td>
						<td class="data_cell">
						<img onclick="window.location.href='assign/${role.id}'" title="分配权限"
							src="/cmoa/static/images/bt_linkman.gif" class="op_button" /> 
						<img onclick="window.location.href='delete/${role.id}'" title="删除"
							src="/cmoa/static/images/bt_del.gif" class="op_button" /></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
			<cmoa:page page="${page }"></cmoa:page>
	</form>
</body>
</html>

