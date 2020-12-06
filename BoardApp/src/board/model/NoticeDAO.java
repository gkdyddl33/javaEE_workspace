/*
 	DAO란?
 	- Data Access Object 를 의미하는 어플리케이션의 설계 분야 용어
 	- Data Access 란 데이터베이스와는 Create(=insert)R(=select)U(update)D(delete) 작업을 전담한다는 의미
*/
package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBManager;

public class NoticeDAO {
	DBManager dbManager = new DBManager();
	
	//  ★ 재사용성 고려하지 않은 swing만의 로직 작성
	public int regist(Notice notice) {
		Connection con=null;
		PreparedStatement pstmt = null;
		int result=0; // try 안에있으면 안보이므로 글 등록 후 그 결과값 보관하는 것 빼주기
		
		con = dbManager.getConnection();
		
		String sql = "insert into notice(author,title,content) values(?,?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, notice.getAuthor());	// 작성자
			pstmt.setString(2, notice.getTitle());	// 제목
			pstmt.setString(3, notice.getContent());	// 내용
			
			result = pstmt.executeUpdate();			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		return result; // 결과값을 그냥 반환..
	}
	
	// ★★★ 모든 레코드 가져오기 - list.jsp
	public ArrayList selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Notice> list = new ArrayList<Notice>(); // rs를 대체할 녀석
		
		con = dbManager.getConnection();
		String sql = "select * from notice order by notice_id desc";
		try {
			pstmt = con.prepareStatement(sql);
			rs= pstmt.executeQuery(); // 모든 레코드를 가져 와야 하므로 1개의 레코드 notice를 집단으로 모아야 한다
													// 어레이 리스트로 집합으로 담아둬서 사용해야 한다.
			// 즉, rs 는 레코드가 복수개 이므로, 여러개 이므로 vo 또한 여러개가 필요하는 뜻!!
			// 객체를 모아놓은 집합을 지원하는 프레임웍은 CollectionFramework이므로, 이 중 하나는
			// api를 이용해 본다.
			while(rs.next()) {
				// 잇니? 그럼 옮겨 심을거야..
				// 근데 이미 아래 notice에서 진행하고 이슴.. 그걸 이용
				Notice notice = new Notice();	// empty상태 vo 생성

				notice.setNotice_id(rs.getInt("notice_id"));
				notice.setAuthor(rs.getString("author"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setRegdate(rs.getString("regdate"));
				notice.setHit(rs.getInt("hit"));
				
				// notice 변수가 1줄씩 담아야 한다. 사라지기 전에
				list.add(notice); // rs가 이제 죽어도 이미 담아져서 흔적을 남겨놓고 잇음
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt, rs);
		}
		return list; // rs가 아닌 arrayList를 반환하자 담겨있는걸
	}
	
	// ★★★ 게시물 1건 가져오기(상세보기)
	// detail.jsp에 디자인만 남기고 스크립트 영역을 담아둔다.
	public Notice select(int notice_id) {
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Notice notice = null; // rs 대신 데이터 1건을 담을 객체
		
		String sql = "select * from notice where notice_id=?";
		con = dbManager.getConnection();	// 접속 객체 얻기
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_id);	// 바인드 변수 값 지정	
			rs = pstmt.executeQuery();
			
			// ★★ 지금 탄생한 rs는 곧 죽는다.. 따라서 rs를 대체할 객체가 필요하다.
			// rs는 레코드 한 건을 담는 객체이므로, 레코드 1건을 담아 전달용으로 사용되는 vo를 이용하자
			if(rs.next()) {
				// 레코드가 존대하는 것. 따라서 이때 vo를 올리자!!
				notice = new Notice();	// empty상태 vo 생성
				// notice에 rs의 정보를 모두~옮겨심자!
				notice.setNotice_id(rs.getInt("notice_id"));
				notice.setAuthor(rs.getString("author"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setRegdate(rs.getString("regdate"));
				notice.setHit(rs.getInt("hit"));
				
				// ------------ 조회수증가
				sql = "update notice set hit=hit+1 where notice_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, notice_id);
				pstmt.executeLargeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt,rs);
		}
		return notice; // rs가 죽기전에 반환!!
	}
	
	// ★★★ 게시물 1건 수정
	public int update(Notice notice) {// notice는 하나의 객체 = 각 데이터
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "update notice set author=?, title=?, content=? where notice_id=?";
		
		con=dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, notice.getAuthor());
			pstmt.setString(2, notice.getTitle());
			pstmt.setString(3, notice.getContent());
			pstmt.setInt(4, notice.getNotice_id());						
			result = pstmt.executeUpdate();	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dbManager.release(con,pstmt);
		}
		return result;
	}
	
	// ★★★ 게시물 1건 삭제
	public int delete(int notice_id) {
		Connection con = null;		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "delete from notice where notice_id=?";
		
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dbManager.release(con,pstmt);
		}
		return result;
	}
}
