  
<%@page import="board.model.CommentsDAO"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/inc/lib.jsp" %>
<%
	//여기서 쿼리실행할 것은 아니지만, 계획을 세우기 위해 ....
	request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="comments" class="board.model.Comments"/>
<jsp:setProperty property="*" name="comments"/>
<%
	if(new CommentsDAO().insert(comments)==0){
		out.print(getMsgBack("댓글 등록실패"));
	}else{
		out.print(getMsgURL("댓글이 등록되었습니다", "detail.jsp?news_id="+comments.getNews_id()));
	}
%>
