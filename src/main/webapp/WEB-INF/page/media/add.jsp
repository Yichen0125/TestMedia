<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${ctp }/static/jquery/jquery-3.1.0.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#fileList").on("change",$("tr td input"),function(){
				
					/*  var fileName = $("tr td input")[$("tr td input").length-2].files[0];
					console.log($("tr td input")[$("tr td input").length-2]);
					
					$($("tr td input")[$("tr td input").length-2]).parent().next().children(":input:eq(0)").val(fileName.name); 
					 */
					var $tr = $("<tr></tr>");
					$tr.append("<td><input type='file' class='fileUrl' name='myMedias'></td>");
					$("#fileList").append($tr);
			})
			
			$("#addAll").click(function(){
				
				$("#addAllForm").submit();
				
			})
			
		})
	</script>
</head>
<body>
	<center>
	<br>
	<br>
	<c:if test="${!empty requestScope.message }">
		<script type="text/javascript">
			alert(requestScope.message);
		</script>
	</c:if>
	<form action="addAll" method="post" id="addAllForm" enctype="multipart/form-data">
		<table border="1" cellpadding="10" cellspacing="0" id="fileList">
			<tr>
				<td>
					<input type="file" class="fileUrl" name="myMedias">
				</td>
				<!-- <td>
					<input type="text" class="fileName" disabled="disabled" name="title" value="">
				</td> -->
			</tr>
					
		</table>
	</form>
	<input type="button" value="全部提交" id="addAll">
	</center>
</body>
</html>