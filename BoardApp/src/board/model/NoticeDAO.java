/*
 	DAO��?
 	- Data Access Object �� �ǹ��ϴ� ���ø����̼��� ���� �о� ���
 	- Data Access �� �����ͺ��̽��ʹ� Create(=insert)R(=select)U(update)D(delete) �۾��� �����Ѵٴ� �ǹ�
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
	
	//  �� ���뼺 ������� ���� swing���� ���� �ۼ�
	public int regist(Notice notice) {
		Connection con=null;
		PreparedStatement pstmt = null;
		int result=0; // try �ȿ������� �Ⱥ��̹Ƿ� �� ��� �� �� ����� �����ϴ� �� ���ֱ�
		
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
		return result; // ������� �׳� ��ȯ..
	}
	
	// �ڡڡ� ��� ���ڵ� �������� - list.jsp
	public ArrayList selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Notice> list = new ArrayList<Notice>(); // rs�� ��ü�� �༮
		
		con = dbManager.getConnection();
		String sql = "select * from notice order by notice_id desc";
		try {
			pstmt = con.prepareStatement(sql);
			rs= pstmt.executeQuery(); // ��� ���ڵ带 ���� �;� �ϹǷ� 1���� ���ڵ� notice�� �������� ��ƾ� �Ѵ�
													// ��� ����Ʈ�� �������� ��Ƶּ� ����ؾ� �Ѵ�.
			// ��, rs �� ���ڵ尡 ������ �̹Ƿ�, ������ �̹Ƿ� vo ���� �������� �ʿ��ϴ� ��!!
			// ��ü�� ��Ƴ��� ������ �����ϴ� �����ӿ��� CollectionFramework�̹Ƿ�, �� �� �ϳ���
			// api�� �̿��� ����.
			while(rs.next()) {
				// �մ�? �׷� �Ű� �����ž�..
				// �ٵ� �̹� �Ʒ� notice���� �����ϰ� �̽�.. �װ� �̿�
				Notice notice = new Notice();	// empty���� vo ����

				notice.setNotice_id(rs.getInt("notice_id"));
				notice.setAuthor(rs.getString("author"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setRegdate(rs.getString("regdate"));
				notice.setHit(rs.getInt("hit"));
				
				// notice ������ 1�پ� ��ƾ� �Ѵ�. ������� ����
				list.add(notice); // rs�� ���� �׾ �̹� ������� ������ ���ܳ��� ����
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt, rs);
		}
		return list; // rs�� �ƴ� arrayList�� ��ȯ���� ����ִ°�
	}
	
	// �ڡڡ� �Խù� 1�� ��������(�󼼺���)
	// detail.jsp�� �����θ� ����� ��ũ��Ʈ ������ ��Ƶд�.
	public Notice select(int notice_id) {
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Notice notice = null; // rs ��� ������ 1���� ���� ��ü
		
		String sql = "select * from notice where notice_id=?";
		con = dbManager.getConnection();	// ���� ��ü ���
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_id);	// ���ε� ���� �� ����	
			rs = pstmt.executeQuery();
			
			// �ڡ� ���� ź���� rs�� �� �״´�.. ���� rs�� ��ü�� ��ü�� �ʿ��ϴ�.
			// rs�� ���ڵ� �� ���� ��� ��ü�̹Ƿ�, ���ڵ� 1���� ��� ���޿����� ���Ǵ� vo�� �̿�����
			if(rs.next()) {
				// ���ڵ尡 �����ϴ� ��. ���� �̶� vo�� �ø���!!
				notice = new Notice();	// empty���� vo ����
				// notice�� rs�� ������ ���~�Űܽ���!
				notice.setNotice_id(rs.getInt("notice_id"));
				notice.setAuthor(rs.getString("author"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setRegdate(rs.getString("regdate"));
				notice.setHit(rs.getInt("hit"));
				
				// ------------ ��ȸ������
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
		return notice; // rs�� �ױ����� ��ȯ!!
	}
	
	// �ڡڡ� �Խù� 1�� ����
	public int update(Notice notice) {// notice�� �ϳ��� ��ü = �� ������
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
	
	// �ڡڡ� �Խù� 1�� ����
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
