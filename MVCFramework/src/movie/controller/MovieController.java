/*
 	������ jsp�� ����ϰ� �־��� ��Ʈ�ѷ��μ��� ������ ���� Ŭ������ �и���Ű��
 	�׷��� jsp�� ������ �������� �Ǳ� ������ �������� �� ��ü���� �����ϴ�..
*/
package movie.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Controller;

import blood.model.BloodAdviser;
import movie.model.MovieAdviser;

// MovieController �� Controller �ڷ������� ��������
public class MovieController implements Controller{
		
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �Ķ���� �ޱ�
		String movie=request.getParameter("movie");
		
		// 3�ܰ� : �˸´� ���� ��ü���� �� ��Ų��.
		MovieAdviser adviser = new MovieAdviser();
		String msg = adviser.getAdvice(movie);
		
		// ����� ���� ����� �������� View�� ����ϹǷ�, �� �������� ó���ϸ� �ȵȴ�.
		// ��� jsp���� �޼����� �����ַ���, ������ �޸𸮿� �ӽ������� ������ ���� �ʿ䰡 �ִ�.
		// ����μ��� ���ǿ� ����.
		// 4�ܰ� : Ŭ���̾�Ʈ���� ������ ����� ������ ���´�. ������..
		HttpSession session = request.getSession();
		session.setAttribute("msg", msg);
				
	}

	@Override
	public String getViewPage() {

		return "/movie/movie_result.jsp";
	}
}
