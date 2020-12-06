package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBManager;

// ����о߿�� - CRUD �۾��� �����Ѵ�.
public class NoticeDAO2 {

	DBManager dbManager = new DBManager();
	
	public int regist(Notice2 notice) {// insert �� ����
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		con = dbManager.getConnection();
		String sql = "insert into notice(author,title,content) values(?,?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, notice.getAuthor());	// �ۼ���
			pstmt.setString(2, notice.getTitle());	// ����
			pstmt.setString(3, notice.getContent());	// ����
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		return result;
	}
	
	// insert �� ����? �װ� list�� �ű�� ��� ���ڵ带
	public ArrayList selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ���ڵ��..�̴ϱ� ���� ���� ��ü
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
				list.add(notice);// �ݺ����� �������� ���� ����Ʈ�� ��ƾ� �������.
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt, rs);
		}
		return list;
	}
	// �󼼺���
	public Notice2 select(int notice_id) {
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Notice2 notice = null;
		
		String sql = "select * from notice where notice_id=?"; // 1������? 1�� ���� �� ��������
		try {
			con = dbManager.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {// vo �� �ø��� - 1�� ������ ���ؼ�
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
