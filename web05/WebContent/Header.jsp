<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="spms.vo.Member"%>
<% Member m = (Member) session.getAttribute( "member" );%>
<div style="background-color:#00008b; color:#ffffff; height:20px; padding:5px;">
Davinci.J's web project
<span style="float:right;">
<%=m.getEmail()%>
<a style="color:white;" href="<%=request.getContextPath()%>/auth/logout.do">로그 아웃</a>
</span>
</div>