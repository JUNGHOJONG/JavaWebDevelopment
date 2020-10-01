<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="spms.vo.Member"%>
<jsp:useBean id="member"
			scope="session"
			class="spms.vo.Member"/>
<div style="background-color:#00008b; color:#ffffff; height:20px; padding:5px;">
Davinci.J's web project
<% if(member.getEmail() != null) {%>
<span style="float:right;">
<%=member.getEmail()%>
<!--고르 아웃 링크 경오 다시보기 logout.do 가능하지 않나?? -->
<a style="color:white;" href="<%=request.getContextPath()%>/auth/logout.do">로그 아웃</a>
</span>
<%}%>
</div>