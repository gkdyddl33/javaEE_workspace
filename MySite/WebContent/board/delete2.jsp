<%@page import="common.file.FileManager"%>
<%@page import="board.model.BoardDAO"%>
<%@ include file="/inc/lib.jsp" %>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	// 삭제? 이미지+글
	String board_id= request.getParameter("board_id");
	String filename = request.getParameter("filename");
	
	BoardDAO dao = new BoardDAO();
	
	// 이미지 먼저 삭제, 레코드 삭제
	String path = application.getRealPath("/data");
	if(FileManager.deleteFile(path+"/"+filename)){
		int result = dao.delete(Integer.parseInt(board_id));
		if(result==0){
			out.print(getMsgBack("게시물 삭제실패"));
		}else{
			out.print(getMsgURL("삭제성공", "list.jsp"));
		}
	}else{
		out.print(getMsgBack("파일을 삭제할 수 없습니다."));
	}
%>