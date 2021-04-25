<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "board.BoardCtrl"%>
<%@ include file="color.jspf" %>
<html>
<head>
<title>게시판</title>
<link href="board/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="board/script.js"></script>
</head>
<body bgcolor="<%=bodyback_c%>">
<p>글수정</p>
<br>
<form method="post" name="writeform"
	action="/Myjsp/BoardCtrl?cmd=update&pageNum=${UPDATE_PAGE}&num=${UPDATE_LIST.num}" 
	onsubmit="return writeSave()">
<table>
	<tr><!-- value속성을 넣어줌으로서 기본적으로 DB내용을 깔아놓음 -->
		<td width="70" bgcolor="<%=value_c%>" align="center">이 름</td>
		<td align="left" width="330">
			<input type="text" size="10" maxlength="10" name="writer"
				value="${UPDATE_LIST.writer}" style="ime-mode:active;">
			<input type="hidden" name="num" value="${UPDATE_LIST.num}">
		</td>
	</tr>
	<tr>
		<td width="70" bgcolor="<%=value_c%>" align="center">제 목</td>
		<td align="left" width="330">
			<input type="text" size="40" maxlength="50" name="subject"
				value="${UPDATE_LIST.subject}" style="ime-mode:active;">
		</td>
	</tr>
		<tr>
		<td width="70" bgcolor="<%=value_c%>" align="center">Email</td>
		<td align="left" width="330">
			<input type="text" size="40" maxlength="30" name="email"
				value="${UPDATE_LIST.email}" style="ime-mode:active;">
		</td>
	</tr>
		<tr>
		<td width="70" bgcolor="<%=value_c%>" align="center">내 용</td>
		<td align="left" width="330">
			<textarea name="content" rows="13" cols="40"
				style="ime-mode:active;">${UPDATE_LIST.content}</textarea>
		</td>
	</tr>
		<tr>
		<td width="70" bgcolor="<%=value_c%>" align="center">비밀번호</td>
		<td align="left" width="330">
			<input type="password" size="8" maxlength="12"
				name="passwd" style="ime-mode:inactive;">
		</td>
	</tr>
<tr>
	<td colspan=2 bgcolor="<%=value_c %>" align="center">
		<input type="submit" value="글수정">
		<input type="reset" value="다시작성">
		<input type="button" value="목록보기"
				onclick="document.location.href='/Myjsp/BoardCtrl?cmd=sltMul&pageNum=${UPDATE_PAGE}'">
	</td>
	</tr>
</table>
</form>
</body>
</html>