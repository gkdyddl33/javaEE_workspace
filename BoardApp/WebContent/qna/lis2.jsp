<%@page import="board.model.QnA"%>
<%@page import="java.util.List"%>
<%@page import="board.model.QnADAO"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	// db연동
	QnADAO qnADAO = new QnADAO();
	List<QnA> list = qnADAO.selectAll();
	
	int currentPage =1;	// 현재 페이지 
	// 1. paging처리..총 레코드수, 한 페이지당 보여질 레코드 수, 총 페이지수,한 블록당 보여질 페이지 수(이전,다음위해)
	int totalRecord = list.size();
	int pageSize = 10;
	int totalPage = (int)Math.ceil((float)totalRecord/pageSize);
	int blockSize = 10;
	
	// 2. 누군가 파라미터를 넘겼을 때..페이지 선택했을 때..
	// 즉 페이지 넘버를 클릭한 경우를 전제로 숫자화 시키는 코드이다..
	if(request.getParameter("currentPage")!=null){// 현재페이지가 아니라면?
		// 현재페이지
		currentPage = Integer.parseInt(request.getParameter("currentPage")); // 파라미터는 문자, 페이지 수는 숫자
	}
	
	// 이전,다음을 위해 작성하는 코드
	int firstPage = currentPage-(currentPage-1)%blockSize;	// 시작 값
	int lastPage = firstPage +(blockSize-1);	// 끝값
	
	// --- num
	int num = totalRecord-(currentPage-1)*pageSize;	// 페이지 당 시작번호
	int curPos = (currentPage-1)*pageSize;	// 페이지 당 List에서의 시작 index
%>
<%="totalRecord "+totalRecord+"<br>" %>
<%="pageSize "+pageSize+"<br>" %>
<%="totalPage "+totalPage+"<br>" %>
<%="blockSize "+blockSize+"<br>" %>
<%="currentPage "+currentPage+"<br>" %>
<%="firstPage "+firstPage+"<br>" %>
<%="lastPage "+lastPage+"<br>" %>
<%="num "+num+"<br>" %>
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
.pageNum{
	font-size:20pt;
	color:red;
	font-weight:bold;
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
	<%for(int i=0;i<pageSize;i++){// 한 페이지당 보여질.. %>
	<%if(num<1)break; // 레코드가 없다면?%>
	<%QnA qna = list.get(curPos++); // 1page:0~9, 2page:10~19.. %>
  <tr>
    <td><%=num-- %></td>
    <td>    	
    	<%if(qna.getDepth()>0){// 자식이 존재한다. %>
    		<img src="/images/reply.png" style="margin-left:<%=10*qna.getDepth()%>px">
    	<%} %>    
		<%=qna.getTitle() %>
	</td>
    <td><%=qna.getWriter() %></td>
	<td><%=qna.getRegdate().substring(0, 10) %></td>
	<td><%=qna.getHit() %></td>
  </tr>
	<%} %>
  <tr>
	<td colspan="5" style="text-aling:center">
	<%if((firstPage-1)>0){// 페이지가 있다면 %> 
		<a href="/qna/lis2.jsp?currentPage=<%=firstPage-1%>">◀</a>
	<%}else{ %>
		<a href="javascript:alert('처음 페이지입니다.');">◀</a>
	<%} %>		
	<%for(int i=firstPage;i<=lastPage;i++){ %>
		<%if(i>totalPage)break; %>
		<a href="/qna/lis2.jsp?currentPage=<%=i%>" <%if(currentPage==i){ %> class="pageNum" <%} %>>[<%=i %>]</a>
	<%} %>				
		<a href="/qna/lis2.jsp?currentPage=<%=lastPage+1%>">▶</a>
	</td>
  </tr>
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