<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="color.jspf"%>
<%@ page import = "board.BoardCtrl"%>

<html>
<head>

<title>게시판</title>
<link href="board/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="board/script.js"></script>
</head>

<body bgcolor="<%=bodyback_c%>">
	<p>글쓰기</p>
	<form method="post" name="wrireform" action="/Myjsp/BoardCtrl?cmd=insert"
		onsubmit="return writeSave()">
		<input type="hidden" name="num" value="${NUM}">
		<input type="hidden" name="ref" value="${REF}"> 
		<input type="hidden" name="re_step" value="${RE_STEP}"> 
		<input type="hidden" name="re_level" value="${RE_LEVEL}">

		<table>
			<tr>
				<td align="right" colspan="2" bgcolor="<%=value_c%>">
				<a href="list.jsp">글목록</a></td>
			</tr>
			<tr>
				<td width="70" bgcolor="<%=value_c%>" align="center">이 름</td>
				<td width="330" align="left">
					<input type="text" size="10" maxlength="10" name="writer" 
						style="ime-mode: active;">
				</td>
				<!-- active:한글 -->
			</tr>
			<tr>
				<td width="70" bgcolor="<%=value_c%>" align="center">제 목</td>
				<td width="330" align="left">
					<input type="text" size="40" maxlength="50" name="subject" 
						value="${COMENT}" style="ime-mode:active;">
				</td>
			</tr>
			<tr>
				<td width="70" bgcolor="<%=value_c%>" align="center">Email</td>
				<td width="330" align="left">	
					<input type="text" size="40" maxlength="30" name="email"
						style="ime-mode: inactive;">
				</td>
				<!-- active:영 문 -->
			</tr>
			<tr>
				<td width="70" bgcolor="<%=value_c%>" align="center">내 용</td>
				<td width="330" align="left">
					<textarea name="content" rows="13"
						cols="40" style="ime-mode: inactive;"></textarea>
				</td>
			</tr>
			<tr>
				<td width="70" bgcolor="<%=value_c%>" align="center">비밀번호</td>
				<td width="330" align="left">
					<input type="password" size="8"
						maxlength="12" name="passwd" style="ime-mode: active;">
				</td>
			</tr>
			<tr>
				<td colspan=2 bgcolor="<%=value_c%>" align="center">
					<input type="submit" value="글쓰기">
					<input type="reset" value="다시작성">
					<input type="button" value="목록보기" onClick="window.location='list.jsp'">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>