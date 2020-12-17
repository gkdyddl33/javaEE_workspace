/*
 	mvc 패턴으로 개발하다 보면, 늘어나는 컨트롤러에 대해 일일이 매핑과정을 진행해야 한다..
 	이때 너무 많은 매핑은 관리하기가 까다롭다. 따라서 유지보수성이 떨어진다..
 	현실과 유사하게,어플리케이션에서도 대형 업무처리시 클라이언트의 요청을 곧바로 해당 컨트롤러에게
 	처리하게 하지 않고, 중간에 메인 컨트롤러를 두고, 모든 요청을 이 메인 컨트롤러에서 처리하여 적절한
 	하위 컨트롤러에게 전담시키는 방식을 이용한다..
 	
 	이 컨트롤러는 웹어플리케이션의 모든~~everything 요청을 1차적으로 받는다!
*/
package com.controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blood.controller.BloodController;
import movie.controller.MovieController;
import movie.model.MovieAdviser;

public class DispatcherServlet extends HttpServlet{
	// doRequest에 new를 하면은 계속 요청이 들어올때마다 new 되므로 메모리 낭비..
	FileInputStream fis;
	Properties props;
	
	// init은 서블릿의 생명주기에서 최초의 접속자에 의해 톰켓이 인스턴스를 생성하면서 이와 동시에 초기화 메서드로써 호출된다.
	public void init(ServletConfig config) throws ServletException {
		// d드라이브가 아닌 getRealPath이 필요..jsp의 내장객체 중 application 에 대한 정보를 갖는 application 내장객체에서 지원함
		ServletContext context = config.getServletContext();
		String contextConfigLocation = config.getInitParameter("contextConfigLocation");
		String savePath = context.getRealPath(contextConfigLocation);
		System.out.println(savePath);
		
		try {
			fis = new FileInputStream(savePath);	// 경로+파일명까지 포함
			props = new Properties();
			props.load(fis);	// 스트림과 프로퍼티스 연결 -> 검색남음..if문
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request,response);
	}
	
	// get or post 상관없이, 모든 요청을 이 메서드에서 처리하자!
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// 1단계 : 요청을 받는다.
		request.setCharacterEncoding("utf-8");
		// 클라이언트가 영화를 원하면? -> 영화 담당 컨트롤러에게 전가		
		// 클라이언트가 혈액형을 원하면? 혈액형 담당 컨트롤러에게 전가
		
		// 2단계 : 요청을 분석 -> 알맞는 컨트롤러에게 전가.(글쓰기? 삭제? 등등..원하는게 뭐지..?를 분석)
		//			해답은 클라이언트 요청 자체에 있다. 즉 요청시 사용된 uri 가 곧 요청 구분값이다!!
		String uri = request.getRequestURI();
		System.out.println("지금 들어온 요청은 "+uri);
		
		Controller controller = null;	// if문 밖으로 뺄 수 있게 된다.. 공통으로 부모 자료형으로 되어있으므로
		// new 대신 string 사용하는 방법
		String className = null;
		
		// ---- if문 대신, properties  객체를 이용하여 파일을 읽어와 key 값으로 메모리에 올려질 컨트롤러의 이름을 value로 반환받자 
		className = props.getProperty(uri);	// /movie.do..
		
		try {
			Class controllerClass = Class.forName(className);		// movie.MovieController -> 변하게하자.
			// 인스턴스 생성
			controller = (Controller)controllerClass.newInstance();
			// 2~3단계 : 알맞는 로직 객체에게 일 시킨다.(전달)
			controller.execute(request, response);		// 다형적으로 실행됨(다형성)
			// 5단계 : 알맞는 결과를 보여주자..클라이언트로 하여금 지정한 url로 재접속을 유도하자.
			response.sendRedirect(controller.getViewPage());	// 계속 페이지는 바껴야 하므로 하위 컨트롤러에게 전가 -> 변수선언(하위컨트롤러물어보는것)
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	// 서블릿의 생명주기 메서드 중 서블릿이 소멸할 때 호출되는 메서드인 destory()에, 스트림 등의 자원을 닫는 처리를 하자
	@Override
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
