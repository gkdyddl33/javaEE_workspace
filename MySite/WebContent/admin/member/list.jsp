<%@page import="admin.member.Admin"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	// 세션에서 데이터 꺼내기
	// out.print("당신이 사용하고 있는 세션은"+session);
	Admin admin = (Admin)session.getAttribute("ad");
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/admin/inc/head.jsp" %>
<style>
body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
}
table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
  border: 1px solid #ddd;
}

th, td {
  text-align: left;
  padding: 16px;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}
</style>
</head>
<div>
	<%=admin.getMid()%>
	<a href="/admin/logout.jsp">로그아웃</a>
</div> 
<body>
<%@ include file="/admin/inc/topnavi.jsp" %>
<h2>회원 목록</h2>

<table>
  <tr>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Points</th>
  </tr>
  <tr>
    <td>Jill</td>
    <td>Smith</td>
    <td>50</td>
  </tr>
  <tr>
    <td>Eve</td>
    <td>Jackson</td>
    <td>94</td>
  </tr>
  <tr>
    <td>Adam</td>
    <td>Johnson</td>
    <td>67</td>
  </tr>
</table>

</body>
</html>
