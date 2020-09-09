<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="spms.vo.Member"%>
<%Member member = ( Member ) request.getAttribute( "member" );%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보</title>
</head>
<body>
<h1>회원정보</h1>
<form action='update' method='post'>
번호: <input type='text' name='no' value='<%=request.getParameter( "no" )%>' readonly><br>
이름: <input type='text' name='name' value='<%=member.getName()%>'><br>
이메일: <input type='text' name='email' value='<%=member.getEmail()%>'><br>
가입일: <%=member.getCreatedDate()%><br>
<input type='submit' value='저장'>
<input type='button' value='삭제'
onclick='location.href=remove?no=<%=request.getParameter( "no" )%>'>
<input type='button' value='취소'
onclick='location.href=list'>
</form>
</body>
</html>