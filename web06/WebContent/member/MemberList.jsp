<%@ page import="spms.vo.Member"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
<title>회원 목록</title>
</head>
<body>
<jsp:include page="/Header.jsp"/>
<h1>회원 목록</h1>
<p><a href='add.do'>신규 회원</a></p>
<p><a href='sort.do'>회원 정렬</a></p>
<%
ArrayList<Member> members = ( ArrayList<Member> ) request.
getAttribute( "members" );
for( Member n : members ){
%>
<%=n.getNo()%>.
<a href='update.do?no=<%=n.getNo()%>'><%=n.getName()%></a>,
<%=n.getEmail()%>,
<%=n.getCreatedDate()%>
<a href='remove.do?no=<%=n.getNo()%>'>[삭제]</a><br>
<%}%>
<jsp:include page="/Tail.jsp"/>
</body>
</html>