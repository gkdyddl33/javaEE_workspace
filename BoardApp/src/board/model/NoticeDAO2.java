package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBManager;

// 설계분야용어 - CRUD 작업을 전담한다.
public class NoticeDAO2 {

	DBManager dbManager = new DBManager();
	
	public int regist(Notice2 notice) {// insert 문 설계
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
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
		return result;
	}
	
	// insert 문 설계? 그걸 list로 옮기기 모든 레코드를
	public ArrayList selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 레코드들..이니깐 집합 담을 객체
		ArrayList<Notice2> list = new ArrayList<Notice2>();
		
		con = dbManager.getConnection();
		String sql = "select * from notice order by notice_id desc";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Notice2 notice = new Notice2();
				notice.setNotice_id(rs.getInt("notice_id"));
				notice.setAuthor(rs.getString("author"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setRegdate(rs.getString("regdate"));
				notice.setHit(rs.getInt("hit"));
				list.add(notice);// 반복문이 끝나가기 전에 리스트에 담아야 담아진다.
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt, rs);
		}
		return list;
	}
	// 상세보기
	public Notice2 select(int notice_id) {
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Notice2 notice = null;
		
		String sql = "select * from notice where notice_id=?"; // 1번누름? 1번 정보 싹 가져오기
		try {
			con = dbManager.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {// vo 에 올리자 - 1번 정보에 대해서
				notice = new Notice2();
				notice.setNotice_id(rs.getInt("notice_id"));
				notice.setAuthor(rs.getString("author"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setRegdate(rs.getString("regdate"));
				notice.setHit(rs.getInt("hit"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt,rs);
		}
		return notice;
	}
}
