<%@page import="common.file.FileManager"%>
<%@page import="board.model.Board"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="board.model.BoardDAO"%>
<%@ include file="/inc/lib.jsp"  %>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	BoardDAO dao = new BoardDAO();

	String saveDir = application.getRealPath("/data");
	int maxSize = 2*1024*1024;
	
	// 파일 업로드 공장설계
	DiskFileItemFactory factory = new DiskFileItemFactory();
	factory.setRepository(new File(saveDir));
	factory.setSizeThreshold(maxSize);
	factory.setDefaultCharset("utf-8");
	
	// 업로드
	ServletFileUpload upload = new ServletFileUpload();
	List<FileItem> items = upload.parseRequest(request);
	Board board = new Board();
	
	for(FileItem item:items){
		if(item.isFormField()){// 텍스트필드가 잇다면..
			if(item.getFieldName().equals("board_id")){
				board.setBoard_id(Integer.parseInt(item.getString()));
			}else if(item.getFieldName().equals("filename")){
				board.setFilename(item.getString());
			}else if(item.getFieldName().equals("title")){
				board.setTitle(item.getString());
			}else if(item.getFieldName().equals("writer")){
				board.setWriter(item.getString());
			}else if(item.getFieldName().equals("content")){
				board.setContent(item.getString());
			}
		}else{// 업로드 된 파일이 발견된다면..
			if(item.getName().length()>0){// 이름을 가져와라..파일명이 있다면..
				// 기존 파일 삭제
				FileManager.deleteFile(saveDir+"/"+board.getFilename());
				// 새로운 파일 처리
				String newName = System.currentTimeMillis()+"."+FileManager.getExtend(item.getName());
				item.write(new File(saveDir+"/"+newName));
				board.setFilename(newName);
			}
		}
	}
	int result = dao.update(board);
	if(result==0){
		out.print(getMsgBack("수정실패"));
	}else{
		out.print(getMsgURL("수정성공", "/board/list.jsp"));		
	}
%>