/*
 	mvc �������� �����ϴ� ����, �þ�� ��Ʈ�ѷ��� ���� ������ ���ΰ����� �����ؾ� �Ѵ�..
 	�̶� �ʹ� ���� ������ �����ϱⰡ ��ٷӴ�. ���� ������������ ��������..
 	���ǰ� �����ϰ�,���ø����̼ǿ����� ���� ����ó���� Ŭ���̾�Ʈ�� ��û�� ��ٷ� �ش� ��Ʈ�ѷ�����
 	ó���ϰ� ���� �ʰ�, �߰��� ���� ��Ʈ�ѷ��� �ΰ�, ��� ��û�� �� ���� ��Ʈ�ѷ����� ó���Ͽ� ������
 	���� ��Ʈ�ѷ����� �����Ű�� ����� �̿��Ѵ�..
 	
 	�� ��Ʈ�ѷ��� �����ø����̼��� ���~~everything ��û�� 1�������� �޴´�!
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
	// doRequest�� new�� �ϸ��� ��� ��û�� ���ö����� new �ǹǷ� �޸� ����..
	FileInputStream fis;
	Properties props;
	
	// init�� ������ �����ֱ⿡�� ������ �����ڿ� ���� ������ �ν��Ͻ��� �����ϸ鼭 �̿� ���ÿ� �ʱ�ȭ �޼���ν� ȣ��ȴ�.
	public void init(ServletConfig config) throws ServletException {
		// d����̺갡 �ƴ� getRealPath�� �ʿ�..jsp�� ���尴ü �� application �� ���� ������ ���� application ���尴ü���� ������
		ServletContext context = config.getServletContext();
		String contextConfigLocation = config.getInitParameter("contextConfigLocation");
		String savePath = context.getRealPath(contextConfigLocation);
		System.out.println(savePath);
		
		try {
			fis = new FileInputStream(savePath);	// ���+���ϸ���� ����
			props = new Properties();
			props.load(fis);	// ��Ʈ���� ������Ƽ�� ���� -> �˻�����..if��
			
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
	
	// get or post �������, ��� ��û�� �� �޼��忡�� ó������!
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// 1�ܰ� : ��û�� �޴´�.
		request.setCharacterEncoding("utf-8");
		// Ŭ���̾�Ʈ�� ��ȭ�� ���ϸ�? -> ��ȭ ��� ��Ʈ�ѷ����� ����		
		// Ŭ���̾�Ʈ�� �������� ���ϸ�? ������ ��� ��Ʈ�ѷ����� ����
		
		// 2�ܰ� : ��û�� �м� -> �˸´� ��Ʈ�ѷ����� ����.(�۾���? ����? ���..���ϴ°� ����..?�� �м�)
		//			�ش��� Ŭ���̾�Ʈ ��û ��ü�� �ִ�. �� ��û�� ���� uri �� �� ��û ���а��̴�!!
		String uri = request.getRequestURI();
		System.out.println("���� ���� ��û�� "+uri);
		
		Controller controller = null;	// if�� ������ �� �� �ְ� �ȴ�.. �������� �θ� �ڷ������� �Ǿ������Ƿ�
		// new ��� string ����ϴ� ���
		String className = null;
		
		// ---- if�� ���, properties  ��ü�� �̿��Ͽ� ������ �о�� key ������ �޸𸮿� �÷��� ��Ʈ�ѷ��� �̸��� value�� ��ȯ���� 
		className = props.getProperty(uri);	// /movie.do..
		
		try {
			Class controllerClass = Class.forName(className);		// movie.MovieController -> ���ϰ�����.
			// �ν��Ͻ� ����
			controller = (Controller)controllerClass.newInstance();
			// 2~3�ܰ� : �˸´� ���� ��ü���� �� ��Ų��.(����)
			controller.execute(request, response);		// ���������� �����(������)
			// 5�ܰ� : �˸´� ����� ��������..Ŭ���̾�Ʈ�� �Ͽ��� ������ url�� �������� ��������.
			response.sendRedirect(controller.getViewPage());	// ��� �������� �ٲ��� �ϹǷ� ���� ��Ʈ�ѷ����� ���� -> ��������(������Ʈ�ѷ�����°�)
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	// ������ �����ֱ� �޼��� �� ������ �Ҹ��� �� ȣ��Ǵ� �޼����� destory()��, ��Ʈ�� ���� �ڿ��� �ݴ� ó���� ����
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
