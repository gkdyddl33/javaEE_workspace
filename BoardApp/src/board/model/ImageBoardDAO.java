/*
 	ImageBoard ���̺� ���� CRUD���� �����ϴ� DAO ����
*/
package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBManager;

public class ImageBoardDAO {
	DBManager dbManager =new DBManager(); 	// DBManager�� �ν��Ͻ��� ���� ���� - DAO�� �ν��Ͻ��� ������ ��
	
	
	// create(insert)
	public int insert(ImageBoard board) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into imageboard(author,title,content,filename) values(?,?,?,?)";
		int result = 0;
		
		con = dbManager.getConnection();
		try {// �ۼ��Ұ͸�!
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getAuthor());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getFilename());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(con,pstmt);
		}
		return result;
	}
	
	// selectAll()
	public ArrayList selectAll() {// ���!
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		ArrayList<ImageBoard> list = new ArrayList<ImageBoard>();
		
		con = dbManager.getConnection();
		String sql = "select * from imageboard";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {// vo�� �̿��� ���ڵ带 ��Ƽ� �ֱ�
				ImageBoard board = new ImageBoard();
				board.setBoard_id(rs.getInt("board_id"));
				board.setAuthor(rs.getString("author"));
				board.setTitle(rs.getString("title"));
				board.setRegdate(rs.getString("regdate"));
				board.setContent(rs.getString("content"));
				board.setHit(rs.getInt("hit"));
				board.setFilename(rs.getString("filename"));
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt, rs);
		}
		return list;
	}
	
	// select
	public ImageBoard select(int board_id) {// 1��
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		ImageBoard board= null; // �ܼ��̹Ƿ�..
		
		con = dbManager.getConnection();
		String sql = "select * from imageboard where board_id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_id); // �Ű�����
			rs= pstmt.executeQuery();
			
			if(rs.next()) {
				board = new ImageBoard();
				board.setBoard_id(rs.getInt("board_id"));
				board.setAuthor(rs.getString("author"));
				board.setTitle(rs.getString("title"));
				board.setRegdate(rs.getString("regdate"));
				board.setContent(rs.getString("content"));
				board.setHit(rs.getInt("hit"));
				board.setFilename(rs.getString("filename"));
			}
			// ��ȸ��
			pstmt = con.prepareStatement("update imageboard set hit=hit+1 where board_id=?");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt, rs);
		}
		return board;
	}
	
	// update
	public int update(ImageBoard board) {// vo
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		con = dbManager.getConnection();		
		String sql = "update imageboard set author=?,title=?,content=?,filename=? where board_id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getAuthor());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getFilename());
			pstmt.setInt(5, board.getBoard_id());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			dbManager.release(con, pstmt);
		}
		return result;
	}
	
	//delete
		public int delete(int board_id) {
			Connection con=null;
			PreparedStatement pstmt=null;
			int result=0;
			
			String sql="delete from imageboard where board_id=?";
			con=dbManager.getConnection();
			try {
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, board_id);
				result=pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dbManager.release(con, pstmt);
			}
			return result;
		}
}
