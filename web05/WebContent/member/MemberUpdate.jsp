<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="spms.vo.Member"%>
<%Member member = ( Member ) request.getAttribute( "member" );%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ȸ������</title>
</head>
<body>
<h1>ȸ������</h1>
<form action='update' method='post'>
��ȣ: <input type='text' name='no' value='<%=request.getParameter( "no" )%>' readonly><br>
�̸�: <input type='text' name='name' value='<%=member.getName()%>'><br>
�̸���: <input type='text' name='email' value='<%=member.getEmail()%>'><br>
������: <%=member.getCreatedDate()%><br>
<input type='submit' value='����'>
<input type='button' value='����'
onclick='location.href=remove?no=<%=request.getParameter( "no" )%>'>
<input type='button' value='���'
onclick='location.href=list'>
</form>
</body>
</html>