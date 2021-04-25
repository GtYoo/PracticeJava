<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "board.BoardCtrl"%>
<%@ page import = "java.util.List" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ include file= "color.jspf" %>

<html>
<head>
<link href="board/style.css" rel="stylesheet" type="text/css">
<title>게시판</title>
</head>
<body bgcolor="<%=bodyback_c%>">
<p>글목록(전체 글:${COUNT})</p>

<table>
	<tr>
		<td align="right" bgcolor="<%=value_c %>">
			<a href="/Myjsp/BoardCtrl?cmd=insert_Be">글쓰기</a>
		</td>
	</tr>
</table>

<c:if test="${COUNT == 0}">
<table>
<tr>
	<td align="center">
		게시판에 저장된 글이 없습니다.
	</td>
</table>
</c:if> 

<table>
	<tr height="30" bgcolor="<%=value_c%>">
		<td align="center" width="50" >번 호</td>
		<td align="center" width="250" >제   목</td>
		<td align="center" width="100" >작성자</td>
		<td align="center" width="150" >작성일</td>
		<td align="center" width="50" >조 회</td>
		<td align="center" width="100" >IP</td>
	</tr>
	<c:forEach var="cnt" begin="0" end="${LIST.size() - 1}">
	<tr height="30" bgcolor="<%=value_c%>">
		<td align="center" width="50" >${LIST[cnt].num}</td>
		<td align="left" width="250">
		<c:if test="${LIST[cnt].re_level > 0}">
			<img src="board/images/level.png" width="${5 * (LIST[cnt].re_level)}" height="16">
			<img src="board/images/re.png">
		</c:if>
		<a href="/Myjsp/BoardCtrl?cmd=sltOne&num=${LIST[cnt].num}&pageNum=${CP}">${LIST[cnt].subject}</a>
		<c:if test="${LIST[cnt].readcount >= 20}">
			<img src="board/images/hot.png" border="0" height="16">
		</c:if>
		</td>
		<td align="left" width="100" ><a href="mailto:${LIST[cnt].email}">${LIST[cnt].writer}</a></td>
		<td width="150" >${LIST[cnt].reg_date}</td>
		<td width="50" >${LIST[cnt].readcount}</td>
		<td width="100" >${LIST[cnt].ip}</td>
	</tr>
	</c:forEach>
</table>
<!-- 게시판 글 출력 끝- -->

	<c:if test="${STARTPAGE > 10}">
	<a href="/Myjsp/BoardCtrl?cmd=sltMul&pageNum=${STARTPAGE - 10}">[이전]</a>
	</c:if>
	<c:forEach var="i" begin="${STARTPAGE}" end="${ENDPAGE}">
	<a href="/Myjsp/BoardCtrl?cmd=sltMul&pageNum=${i}">[${i}]</a>
	</c:forEach>
	<c:if test="${ENDPAGE < PAGECOUNT}">
	<a href="/Myjsp/BoardCtrl?cmd=sltMul&pageNum=${STARTPAGE + 10}">[다음]</a>
	</c:if>


</body>
</html>