<%@page import="common.db.FileManager"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.catalina.ant.ServerinfoTask"%>
<%@page import="org.apache.commons.fileupload.DefaultFileItemFactory"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	/*
		파일 업로드 컴포넌트의 종류엔 여러가지가 있지만, 그 중 아파치의 공식 업로드 컴포넌트를 사용해본다.
	*/
	
	String saveDir = "D:/workspace/javaEE_workspace/BoardApp/WebContent/data";
	int maxSize = 2*1024*1024;	// 2M byte제한	

	// 업로드 객체를 생성해주는 팩토리 객체 : 주로 설정을 담당(서버의 저장경로, 파일의 용량제한..)
	DefaultFileItemFactory itemFactory = new DefaultFileItemFactory();
	itemFactory.setRepository(new File(saveDir));	// 공장의 저장소를 조작할거다 = 경로
																		// 임시 디렉토리로 저장될 위치!
																		
	itemFactory.setSizeThreshold(maxSize);	// 문지방을 넘지 못하게..
																// 용량을 지정한 크기로 제한..
																		
	// 이 객체가 실제 업로드를 수행함.
	ServletFileUpload upload = new ServletFileUpload(itemFactory); // 설정정보를 생성자의 인수로 전달!
	
	request.setCharacterEncoding("utf-8");
	// FileItem은 클라이언트의 전송 정보 하나하나를 의미한다. 즉, html에서의 input 텍스트박스,
	// file 컴포넌트들..
	// 우리의 경우 input type="text"가 FileItem에 담기고
	// 우리의 경우 input type="file"가 FileItem에 담기고
	List<FileItem> items = upload.parseRequest(request);	// 업로드 컴포넌트에게 클라이언트의 요청 정보를 전달한다!
	
	for(FileItem item : items){		
		// 반복문으로 처리되다보니, 파일만 따로 처리를 하려면, 구분로직이 필요함..
		//out.print(item.getFieldName()+"은 텍스트 박스여부"+item.isFormField()+"<br>");
		
		// input type이 아닌 아이들만 업로드 처리 = 텍스트파일
		if(!item.isFormField()){
			// ----------------------- 업로드 처리하자!
			// ----------------- 메모리상의 이미지 정보를 실제 물리적 파일로 저장하자!
			// 새롭게 생성할 파일명
			out.print("당신이 업로드한 파일의 원래 이름은 "+item.getName());
			
			String ext = FileManager.getExtend(item.getName());	// 확장자구하기
			String filename = System.currentTimeMillis()+"."+ext;
			
			File file = new File(saveDir+"/"+filename);	// empty + 파일의 풀경로 삽입
			item.write(file); // 저장정보를 File 클래스의 인스턴스로 전달!
			out.print("원래 파일명:"+item.getName()+"<br>");
			out.print("생성된 파일명:"+filename+"<br>");
			out.print("저장된 위치:"+saveDir+"<br>");
			out.print("업로드 파일크기:"+item.getSize()+" bytes <br>");
		}
	}
%>
