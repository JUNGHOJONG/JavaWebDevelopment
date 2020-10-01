<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-type" content="text/html"; charset="UTF-8">
<title>프로젝트 목록</title>
<style>
	table, th, td {border: 1px solid black;}
</style>
</head>
<body>
<jsp:include page="/Header.jsp"/>
<h1>프로젝트 목록</h1>
<p><a href='add.do'>신규 프로젝트</a></p>
<table>
<c:forEach var="project" items="${projects}">
<tr>
	<th>번호</th>
	<th>제목</th>
	<th>시작일</th>
	<th>종료일</th>
	<th>상태</th>
	<th></th>
</tr>
<tr>
	<td>${project.no}</td>
	<td><a href='update.do?no='${project.no}>${project.title}</a></td>
	<td>${project.startDate}</td>
	<td>${project.endDate}</td>
	<td>${project.state}</td>
	<td><a href='remove.do?no='${project.no}>삭제</a></td>
</tr>
</c:forEach>
</table>
<jsp:include page="/Tail.jsp"/>
</body>
</html>