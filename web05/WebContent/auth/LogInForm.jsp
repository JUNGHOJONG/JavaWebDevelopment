<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
		<title>사용자 로그인</title>
	</head>
	<body>
		<h1>사용자 로그인</h1>
		<form action="login.do" method="post">
			이메일: <input type="text" name="email"><br> 
			암호: <input type="password" name="password"><br>
			<input type="submit" value="로그인">
		</form>
	</body>
</html>