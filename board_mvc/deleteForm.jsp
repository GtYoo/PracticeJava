<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="color.jspf" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link href="board/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	<!-- -->
	function deleteSave() {
		if(!document.delForm.passwd.value) {
			alert("비밀번호를 입력하십시요.");
			documnet.delForm.passwd.focus();
			return false;
		}
	}
</script>
</head>
<body bgcolor="<%=bodyback_c%>">
<p>글삭제</p>
<br>
<form method="POST" name="delForm"
	action="/Myjsp/BoardCtrl?cmd=delete&pageNum=${PAGENUM}"
	onsubmit="return deleteSave()">
	<table>
		<tr height="30">
			<td align=center bgcolor="<%=value_c%>">
				<b>비밀번호를 입력해 주세요.</b></td>
		</tr>
		<tr height="30">
			<td align=center >비밀번호 : 
				<input type="password" name="passwd" size="8" maxlength="12">
				<input type="hidden" name="num" value="${NUM}">
			</td>
		</tr>
		<tr height="30">
			<td align=center bgcolor="<%=value_c%>">
				<input type="submit" value="글삭제" >
				<input type="button" value="글목록"
				 	onclick="document.location.href='/Myjsp/BoardCtrl?cmd=sltMul&pageNum=${PAGENUM}'">
			</td>
		</tr>		 
	</table>
</form>
</body>
</html>