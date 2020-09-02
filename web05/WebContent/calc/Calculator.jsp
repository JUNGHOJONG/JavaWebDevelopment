<!-- jsp 지시자 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- jsp 스크립트릿 -->
<% 
String v1 = "";
String v2 = "";
String op = "";
String[] selected = { "", "", "", "" };
String result = "";

if( request.getParameter( "v1" ) != null ){
	v1 = request.getParameter( "v1" );
	v2 = request.getParameter( "v2" );
	op = request.getParameter( "op" );
	result = calculator( v1, v2, op ); 
}
if( op.equals( "+" ) ){
	selected[0] = "selected";
}else if( op.equals( "-" ) ){
	selected[1] = "selected";	
}else if( op.equals( "*" ) ){
	selected[2] = "selected";
}else{
	selected[3] = "selected";
}
%>
<!-- jsp 선언문 -->
<%! 
public String calculator( String v1, String v2, String op ){
	int value1 = Integer.parseInt( v1 );
	int value2 = Integer.parseInt( v2 );
	if( op.equals( "+" )){
		return String.valueOf( value1 + value2 );
	}else if( op.equals( "-" )){
		return String.valueOf( value1 - value2 );
	}else if( op.equals( "*" )){
		return String.valueOf( value1 * value2 );
	}else{
		if( value2 == 0 ) return "0으로 나눌 수 없습니다.";
		return String.valueOf( value1 / value2 ); // 예외처리 필요
	}
}
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Contect-type" content="text/html"; charset="UTF-8">
	<title>calculator</title>
</head>
<body>
<h1>JSP 계산기</h1>
<form action="Calculator.jsp" method="get">
<input type="text" name="v1" size="4" value="<%=v1%>">
<select name="op">
<option value="+" <%=selected[0]%>>+</option>
<option value="-" <%=selected[1]%>>-</option>
<option value="*" <%=selected[2]%>>*</option>
<option value="/" <%=selected[3]%>>/</option>
</select>
<input type="text" name="v2" size="4" value="<%=v2%>">
<input type="submit" value="=">
<input type="text" size="8" value="<%=result%>">
</form>
</body>
</html>