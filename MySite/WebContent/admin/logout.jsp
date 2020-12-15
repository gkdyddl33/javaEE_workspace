<%@ page contentType="text/html;charset=utf-8"%>
<%
	// 세션은 객체이므로, 개발자가 직접 메모리에서 제거할 수 없다.
	// 자바의 원칙 : 가비지 컬렉터
	// 따라서 세션을 더 이상 사용하지 않도록 무효화시키자.
	session.invalidate();	// 무효하다=사용하지않겟다.
%>
<script>
alert("로그아웃 되었습니다.");
location.href="/admin/login_form.jsp";
</script>