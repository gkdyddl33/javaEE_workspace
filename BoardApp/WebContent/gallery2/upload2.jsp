<%@page import="common.FileManager"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.DefaultFileItemFactory"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	// 파일 업로드 컴포넌트의 종류는 여러가지지만, 그 중 아파치 공식 업로드 컴포넌트를 사용해본다.
	String saveDir = "D:/workspace/javaEE_workspace/BoardApp/WebContent/data";
	int maxSize = 2*1024*1024;
	
	// 업로드 객체를 생성해주는 팩토리 객체 : 서버의 저장경로..용량제한..등 주로 설정을 담당
	DefaultFileItemFactory itemFactory = new DefaultFileItemFactory();
	itemFactory.setRepository(new File(saveDir));	// 저장경로 설정
	
	itemFactory.setSizeThreshold(maxSize);
	
	// 해당 객체가 업로드를 수행한다.
	ServletFileUpload upload = new ServletFileUpload();
	// 업로드 수행하기 전 ---------- 인코딩 처리
	request.setCharacterEncoding("utf-8");
	List<FileItem> items = upload.parseRequest(request);
	for(FileItem item : items){
		if(!item.isFormField()){
			String ext = FileManager.getExtend(item.getName());
			String filename = System.currentTimeMillis()+"."+ext;
			 
			File file = new File(saveDir+"/"+filename);
			item.write(file);
			out.print("원래 파일명:"+item.getName()+"<br>");
			out.print("생성된 파일명:"+filename+"<br>");
			out.print("저장된 위치:"+saveDir+"<br>");
			out.print("업로드 파일크기:"+item.getSize()+" bytes <br>");
		}
	}
%>