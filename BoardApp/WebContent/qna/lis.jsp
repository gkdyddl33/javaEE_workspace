<%@page import="board.model.QnA"%>
<%@page import="java.util.List"%>
<%@page import="board.model.QnADAO"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	QnADAO qnADAO = new QnADAO();
	List<QnA> list = qnADAO.selectAll();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<style>
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
img{
	box-sizing:border-box;
}
a{
	text-decoration:none;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(function(){
	$("button").on("click",function(){
		//자바스크립트에서 링크 구현? 
		location.href="/qna/regist_form.jsp";
	});
}); //onload
</script>
</head>
<body>

<table>
  <tr>
    <th>No</th>
    <th>제목</th>
    <th>작성자</th>
	<th>등록일</th>
	<th>조회수</th>
  </tr>
	<%for(int i=0;i<list.size();i++){ %>
	<%QnA qna = list.get(i); %>
  <tr>
    <td>26</td>
    <td>    	
    	<%if(qna.getDepth()>0){// 자식이 존재한다. %>
    		<img src="/images/reply.png" style="margin-left:<%=10*qna.getDepth()%>px">
    	<%} %>    
		<a href="/qna/detail.jsp?qna_id="></a>
	</td>
    <td><%=qna.getWriter() %></td>
	<td><%=qna.getRegdate().substring(0, 10) %></td>
	<td><%=qna.getHit() %></td>
  </tr>
	<%} %>
  <tr>
	<td colspan="5" > 
		<button>글등록</button>
	</td>
  </tr>
  <tr>
	<td colspan="5" style="text-align:center"> 
		<%@ include file="/inc/footer.jsp"%>
	</td>
  </tr>
	
</table>

</body>
</html>