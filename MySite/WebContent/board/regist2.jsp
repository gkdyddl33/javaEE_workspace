<%@page import="java.io.File"%>
<%@page import="common.file.FileManager"%>
<%@page import="board.model.BoardDAO"%>
<%@page import="board.model.Board"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/inc/lib.jsp"%>
<%
	// 업로드 설정 - multipart/form-data
	DiskFileItemFactory factory = new DiskFileItemFactory();
	String realPath = application.getRealPath("/data");
	
	// 업로드된 정보를 가지고 있는 객체
	ServletFileUpload upload = new ServletFileUpload();
	List<FileItem> items = upload.parseRequest(request); // 요청한 업로드정보..
	
	Board board = new Board();
	BoardDAO dao = new BoardDAO();
	boolean flag = false;	// 처음에는 업로드가 안되있다가
	
	for(FileItem item : items){
		if(item.isFormField()){// 정보가 있다면?
			if(item.getFieldName().equals("title")){
				board.setTitle(item.getString());
			}else if(item.getFieldName().equals("writer")){
				board.setWriter(item.getString());
			}else if(item.getFieldName().equals("content")){
				board.setContent(item.getString());
			}
		}else{// 정보가 아닌 파일이라면? 이미지..
			//ut.print(item.getName());	// 파일 이름을 가져와줘..		
			//그 해당 파일의 경로를 가져오자. 파일명 + 확장자
			long time = System.currentTimeMillis();
			String newName = time+"."+FileManager.getExtend(item.getName());
			File file = new File(realPath+"/"+newName);
			item.write(file);
			flag = true;	// 업로드가 완려되면 바뀜
			out.print("업로드 완료");
		}
	}
	if(flag){
		int result = dao.insert(board);
		if(result==0){
			out.print(getMsgBack("등록실패"));
		}else{
			out.print(getMsgURL("등록성공", "/board/list.jsp"));
		}
	}
	
%>