<%@page import="board.model.QnADAO"%>
<%@page import="board.model.QnA"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/inc/lib.jsp" %>
<%
	// 답글을 작성해서 넘겨주기 - 즉 파라미터 넘기기
	request.setCharacterEncoding("utf-8");
	String writer=request.getParameter("writer");
	String title=request.getParameter("title");
	String content = request.getParameter("content");
	String team=request.getParameter("team"); //내본글 team
	String rank=request.getParameter("rank");//내본글 rank
	String depth=request.getParameter("depth");//내본글 depth
	
	// 빈공간 확보..? 그럼 한 레코드 등록 = vo
	// vo 에 먼저 넘겨받은 파라미터 값을 넣고.. dao의 메소드로 sql 실행..
	QnA qna = new QnA();
	qna.setWriter(writer);
	qna.setTitle(title);
	qna.setContent(content);
	qna.setTeam(Integer.parseInt(team));
	qna.setRank(Integer.parseInt(rank));
	qna.setDepth(Integer.parseInt(depth));
	
	
	QnADAO dao = new QnADAO();
	
	int result = dao.reply(qna);
	if(result==0){
		out.print(getMsgBack("답변등록실패"));
	}else{
		out.print(getMsgURL("답변이 등록되었습니다", "/qna/list.jsp"));
	}
	
%>