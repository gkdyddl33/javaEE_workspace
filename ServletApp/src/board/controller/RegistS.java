package board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import board.model.Board;
import board.model.BoardDAO;
import common.file.FileManager;

public class RegistS extends HttpServlet{
	BoardDAO boardDAO = new BoardDAO();
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 출력스트림 뽑아놓기
		response.setContentType("text/html;charset=utf-8");	// 인코딩
		PrintWriter out = response.getWriter();
		
		// multipart/form-data 업로드 컴포넌트 사용
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletContext context = request.getServletContext();	
		String saveDir = context.getRealPath("/data");
		
		factory.setRepository(new File(saveDir));
		factory.setSizeThreshold(2*1024*1024);
		factory.setDefaultCharset("utf-8");		
		
		// 업로드 파싱 정보 분석
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items =upload.parseRequest(request);
			Board board = new Board();
			boolean flag = false;	// 파일업로드 성공여부
			
			for(FileItem item : items) {
				if(item.isFormField()) {
					if(item.getFieldName().equals("title")) {
						board.setTitle(item.getString());
					}else if(item.getFieldName().equals("writer")) {
						board.setWriter(item.getString());
					}else if(item.getFieldName().equals("content")) {
						board.setContent(item.getString());
					}
				}else {
					long time = System.currentTimeMillis();
					String newName = time+"."+FileManager.getExtend(item.getName());
					
					board.setFilename(newName);	// vo 에 담자 - insert문에 들어간다.
					item.write(new File(saveDir+"/"+newName));
				}
			}
			if(flag) {
				int result = boardDAO.insert(board);
				if(result==0) {
					out.print("<script>");
					out.print("alert('등록실패')");
					out.print("history.back()");
					out.print("</script>");
				}else {
					out.print("<script>");
					out.print("alert('등록성공')");
					out.print("location.href='/board/list.jsp';");
					out.print("</script>");
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
