package com.model2.controller;

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

public class Dispatcher extends HttpServlet{
	FileReader fis;
	JSONObject controllerMap;	// 컨트롤러의 정보들이 들어있는 맵
	JSONObject viewMap;	// 컨트롤러의 정보들이 들어있는 맵
	
	public void init(ServletConfig config) throws ServletException {
		String contextConfigLocation = config.getInitParameter("contextConfigLocation");	// json.mapping
		String realPath = config.getServletContext().getRealPath(contextConfigLocation);
		
		try {
			fis = new FileReader(realPath);	// 경로 - json - 파싱 분석..
			JSONParser jsonParser = new JSONParser();
			
			JSONObject json = (JSONObject) jsonParser.parse(fis);		// mapping.json
			// ---------- mapping.json 안에 있는  key 와 value...
			controllerMap = (JSONObject) json.get("controller");		// json의 controller key..			
			viewMap = (JSONObject) json.get("view");	// json 의 view key..
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	// 1단계 : 요청을 받는다..
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request,response);
	}
	private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 2단계 : 요청을 분석한다.
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();	// 요청 분석..주소값..
		
		// if문 대신할 구조화된 데이터를 선택하자.(xml,<json>,properties)
		String  controllerName = (String) controllerMap.get(uri);	// 요청..json의 controller key..
		Class controllerClass = null;
		Controller controller = null;
		
		try {
			controllerClass = Class.forName(controllerName);	// 클래스의 클래스 반환..
			controller = (Controller)controllerClass.newInstance();		// 인스턴스화..
						
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		controller.execute(request, response);	// 인스턴스화 한거에 전달하자..
		
		// 마지막단계..
		// 클라이언트에게 알맞는 뷰를 보여주자.
		String resultKey = controller.getResultView();	// 보여줄 페이지 메소드를 담자..
		String viewPage = (String) viewMap.get(resultKey);	// 보여줄 메소드를 가져오자..
		
		// 위에 코드를 가지고 내가 선택한 페이지에 맞춰서 넘겨주게 하드코딩 하지 않은 것임..
		if(controller.isForward()) {
			RequestDispatcher dis = request.getRequestDispatcher(viewPage);
			dis.forward(request, response); 		// forword..ex)상세보기같이 데이터 유지시..
		}else {
			response.sendRedirect(viewPage); 		// 재접속을 시도할 경우..
		}
	}


	public void destroy() {
		if(fis !=null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
