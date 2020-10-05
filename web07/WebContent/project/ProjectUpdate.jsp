<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>프로젝트 정보</title>
		<style>
			ul {padding: 0;}
			li {list-style:none;}
			label {
				float: left;
				text-align: right;
				width: 60px;
			}
		</style>
	</head>
	<body>
		<jsp:include page="/Header.jsp"/>
		<h1>프로젝트 정보</h1>
		<form action="update.do" method="post">
			<ul>
				<li>
					<label for="no">번호</label>
					<input id="no" type='text' name='no' size="5" value="${project.no}" readonly>
				</li>
				<li>
					<label for="title">제목</label>
					<input id="title" type="text" name="title" value="${project.title}" size="40">
				</li>
				<li>
					<label for="content">내용</label>
					<textarea id="content" name="content" value="${project.content}" rows="5" cols="40"></textarea>
				</li>
				<li>
					<label for="startDate">시작일</label>
					<input id="startDate" type="text" name="startDate" value="${project.startDate}">
				</li>
				<li>
					<label for="endDate">종료일</label>
					<input id="endDate" type="text" name="endDate" value="${project.endDate}">
				</li>
				<li>
					<label for="state">상태</label>
					<select id="state" name="state">
						<option value="0" ${project.state == 0 ? "selected" : ""}>준비</option>
						<option value="1" ${project.state == 1 ? "selected" : ""}>진행</option>
						<option value="2" ${project.state == 2 ? "selected" : ""}>완료</option>
						<option value="3" ${project.state == 3 ? "selected" : ""}>취소</option>
					</select>
				</li>
				<li>
					<label for="tags">태그</label>
					<!-- 왜 프로퍼티 값이 tags가 아니고 tag일까..... -->
					<input id="tags" type="text" name="tags" value="${project.tags}">
				</li>
			</ul>
			<button type="submit">저장</button>
			<button type="button" onclick='location.href="remove.do?no=${project.no}";'>삭제</button>
			<button type="button" onclick='location.href="list.do"'>취소</button>
		</form>
		<jsp:include page="/Tail.jsp"/>
	</body>
</html>