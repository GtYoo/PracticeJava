<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "board.BoardCtrl"%>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ include file = "color.jspf" %>

<html>
<head>
<title>게시판</title>
<link href="board/style.css" rel="stylesheet" type = "text/css">
</head>
<body bgcolor = "<%=bodyback_c%>">
<p>글내용 보기</p>
<form>
<table>
 <tr height="30">
	<td align= "center" width="125" bgcolor="<%=value_c %>">글번호</td>
	<td align= "center" width="125" align="center">
		${ONELIST.num}</td>
	<td align="center" width="125" bgcolor="<%=value_c%>"> 조회수</td>
	<td align="center" width="125" align="center">
		${ONELIST.readcount}</td>
	</tr>
	<tr height="30">
		<td align ="center" width="125" bgcolor="<%=value_c%>">작성자</td>
		<td align ="center" width="125" align="center">
		${ONELIST.writer}</td>
	<td align="center" width="125" bgcolor="<%=value_c%>"> 작성일</td>
	<td align="center" width="125" align="center">
		${ONELIST.reg_date}</td>
	</tr>
	<tr height="30">
		<td align="center" width="125" bgcolor="<%=value_c%>">글제목</td>
		<td align="center" width="375" align="center" colspan="3">
		${ONELIST.subject}</td>
	</tr>		
	<tr>
		<td align="center" width="125" bgcolor="<%=value_c%>">글내용</td>
		<td align="left" width="375" colspan="3">
		<pre>${ONELIST.content}</pre></td>
	</tr>
	<tr height="30">
		<td colspan="4" bgcolor="<%=value_c%>" align="right">
		<input type="button" value="글수정"							
		onclick="document.location.href='/Myjsp/BoardCtrl?cmd=update_Be&num=${ONELIST.num}&pageNum=${PAGENO}'">
		&nbsp;&nbsp;&nbsp;&nbsp;									<!-- PK -->				 <!-- 이전페이지 -->
		<input type = "button" value ="글삭제"
		onclick="document.location.href='/Myjsp/BoardCtrl?cmd=delete_Be&num=${ONELIST.num}&pageNum=${PAGENO}'">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="답글쓰기"
		onclick="document.location.href='/Myjsp/BoardCtrl?cmd=insert_Be&num=${param.num}&ref=${ONELIST.ref}&re_step=${ONELIST.re_step}&re_level=${ONELIST.re_level}'">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="글목록"
		onclick="document.location.href='/Myjsp/BoardCtrl?cmd=sltMul&pageNum=${PAGENO}'"> <!-- 글목록으로 되돌아가기 이전페이지 -->
		</td>
	</tr>
</table>
</form>
</body>
</html>