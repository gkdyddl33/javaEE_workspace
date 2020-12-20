/*
	웹 상의 모든 클라이언트의 요청을 받고, 응답을 전담하는 컨트롤러 정의
	1. 요청을 받는다.(DispathcherServlet)
	2. 요청을 분석하고 전달한다.(DispathcherServlet)
	3. 로직에게 알맞는 일을 시킨다.(하위 컨트롤러)
	4. 결과를 저장한다.(하위 컨트롤러)
	5. 클라이언트에게 알맞는 결과 뷰(페이지)를 보여준다. 
*/
package com.model2.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DispatcherServlet extends HttpServlet{
	FileReader fis;	// 컨트롤러 매핑 설정파일을 읽기 위한 스트림
	JSONObject controllerMap;	// 컨트롤러의 정보들이 들어있는 맵
	JSONObject 	viewMap;	// 컨트롤러의 정보들이 들어있는 맵
	
	public void init(ServletConfig config) throws ServletException {
		String contextConfigLocation = config.getInitParameter("contextConfigLocation");
		String realPath = config.getServletContext().getRealPath(contextConfigLocation);
		System.out.println(realPath);
		
		try {			
			fis = new FileReader(realPath);
			JSONParser jsonParser = new JSONParser();
			
			// 2단계 : 파싱 = 분석
			JSONObject json = (JSONObject)jsonParser.parse(fis);
			// 데이터 접근..
			controllerMap = (JSONObject)json.get("controller");	
			viewMap = (JSONObject)json.get("view");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}				
	}
	
	// 1단계 : 요청을 받는다 = doXXX
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request,response);
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request,response);
	}
	
	// 2단계 : 요청을 분석하고 전달한다.
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();	//요청의 구분값!
		
		// if문을 대신할 구조화된 데이터를 선택하자(xml,json,properties)
		String controllerName = (String)controllerMap.get(uri);	
		System.out.println("지금 들어온 요청을 처리할 컨트롤러 클래스는 "+controllerName);
	
		Class controllerClass=null;
		Controller controller=null;
		// 동생 하위 컨트롤러의 이름을 알았으니, 인스턴스를 만들고, execute(),getResultView() 호출
		try {
			controllerClass = Class.forName(controllerName);	// 문자열로 지정한 클래스에 대한 클래스 반환
			controller = (Controller)controllerClass.newInstance();	// 인스턴스 생성
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}	
		controller.execute(request, response); 	// 3단계 업무 마무리 응답전달
		// 5단계 : 클라이언트에게 알맞는 뷰를 보여주자.
		String resultKey = controller.getResultView();
		System.out.println(resultKey);
		String viewPage = (String)viewMap.get(resultKey);	// jsp 명칭반환
				
		// 동생 컨트롤러로부터 넘겨받은 키 값을 이용하여 실제 페이지를 검색하고, 그 결과를 이용하여
		// 클라이언트가 보내게 될 페이지를 넘겨주자.
		// 응답시 sendRedirect로 처리해야 할 경우가 있고, 글작성 후 리스트, 전혀 다른 페이지로 재접속을 시도하게 할때..
		if(controller.isForward()) {
			// 때로는 forwarding으로 처리해야 할 경우가 있다. 데이터를 유지하고 싶을 때..기존꺼를 사용하고 싶을 때..
			RequestDispatcher dis = request.getRequestDispatcher(viewPage);
			dis.forward(request, response);
		}else {// 끊어버리기.
			response.sendRedirect(viewPage);	// 재접속을 새롭게 시도할 경우
		}
	}
		
	public void destroy() {
		if(fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
