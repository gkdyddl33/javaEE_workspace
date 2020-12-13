<%@page import="java.io.IOException"%>
<%@page import="java.io.File"%>
<%@page import="common.db.FileManager"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	// 클라이언트가 전송한 제목 텍스트 및 바이너리 파일을 서버의 특정 디렉토리에 저장해보자 = 업로드
	
	// 전송하는 것을 파라미터로 받을 것..인코딩 처리
	request.setCharacterEncoding("utf-8");

	// IO, 네트워크 등의 처리를 해야 하는데 누군가 만든 라이브러리를 이용해서 개발시간을 단축하자
	// 현재 우리가 선택한 라이브러리는 cos.jar 라는 Oreilly라는 출판사에서 제작한 컴포넌트이다.
	
	// 파일을 저장할 디렉토리 만들기
	String saveDirectory = "D:/workspace/javaEE_workspace/BoardApp/WebContent/data";
	int maxSize = 2*1024*1024;
	String encoding = "utf-8";
	try{
		MultipartRequest multi = new MultipartRequest(request,saveDirectory,maxSize,encoding);
		String msg = multi.getParameter("msg");
		out.print("님이 전송한 메시지는 "+msg);
		
		// 파일이 저장된다? 그럼 파일명을 개발자가 정해야 한다.. 이것을 시간의 밀리세컨트까지 구해보자.
		long time = System.currentTimeMillis();	// time+확장자..
		String ori = multi.getOriginalFileName("photo");
		String ext = FileManager.getExtend(ori);
		String filename = time+"."+ext;
		out.print("내가 조작한 파일은 "+filename);
		
		// 파일명 조작 완료? 이제 그 파일을 다루어보자..
		// File 클래스에 파일명을 바꾸는 메소드를 찾아보자....
		File savedFile = multi.getFile("photo");
		savedFile.renameTo(new File(saveDirectory+"/"+filename)); //교체
		
		// 파일명 교체? 그럼 클라이언트에게 전송~
		response.sendRedirect("gallery/photo_list.jsp");
	}catch(IOException e){
		e.printStackTrace();	// 콘솔로그에 에러 출력
		out.print("업로드 용량이 너무 큽니다.");
	}
%>