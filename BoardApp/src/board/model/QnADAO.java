package board.model;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBManager;

public class QnADAO {
	DBManager dbManager = new DBManager();
	/*
	1.기존에 내가본글보다 rank가 큰 글의 rank는 모두 1씩 증가되시오!! (공간확보 )
    	update  qna  rank=rank+1 where rank > 내본글 rank and 
    	team=내본team

	2.빈 공간을 내가 차지!!(답변)
   		insert  qna(~team, rank, depth) values(내본team,내본rank+1,내본depth+1)
   		
   	트랜잭션이란?
   	- 세부업무가 모두 성공해야, 전체를 성공으로 간주하는 논리적 업무 수행단위
	*/
	
	// insert : 원글 등록(부모)
	public int insert(QnA qna) {	
		Connection con=null;
		PreparedStatement pstmt=null;
		int result=0;
		
		String sql="insert into qna(writer, title ,content) values(?,?,?)";
		try {
			con=dbManager.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, qna.getWriter());
			pstmt.setString(2, qna.getTitle());
			pstmt.setString(3, qna.getContent());
			result=pstmt.executeUpdate();//실행
			
			//team을 방금 들어간 레코드에 의해 발생한 pk 값으로 업데이트!!!
			sql="update qna set team=(select last_insert_id()) where qna_id=(select last_insert_id())";
			pstmt=con.prepareStatement(sql); //쿼리문 1:1 대응하게!!
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		return result;
	}
	
	// 답변글(자식)
	public int reply(QnA qna) {//하나의 글 등록..vo
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		con = dbManager.getConnection();
		String sql = "update qna set rank=rank+1 where team=? and rank>?";
		
		try {
			// con.setAutoCommit(true); -> oracle은 commit을 해줘야 하는데 java는 자동으로 commit됨
			// 근데 SQL Plus에서 내가 commit하면 commit되게 할거야
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qna.getTeam());
			pstmt.setInt(2, qna.getRank());
			result = pstmt.executeUpdate();
			
			sql = "insert into qna(writer,title,content,team,rank,depth)";
			sql+=" values(?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, qna.getWriter());
			pstmt.setString(2, qna.getTitle());
			pstmt.setString(3, qna.getContent());
			pstmt.setInt(4, qna.getTeam());
			pstmt.setInt(5, qna.getRank()+1);
			pstmt.setInt(6, qna.getDepth()+1);
			result = pstmt.executeUpdate();
			con.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			// 두 쿼리문 중에 에러가 하나라도 발생하면, 차라리 처음부터 없었던 일로 하자..
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				con.setAutoCommit(true); // 원상복귀
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbManager.release(con, pstmt);
		}		 
		return result;
	}
	
	// selectAll 
	public ArrayList selectAll() { // 목록..모든 레코드 집합
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<QnA> list = new ArrayList<QnA>();
		
		con=dbManager.getConnection();
		String sql = "select * from qna order by team asc,rank asc";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				QnA qna = new QnA();
				qna.setQna_id(rs.getInt("qna_id"));
				qna.setWriter(rs.getString("writer"));
				qna.setTitle(rs.getString("title"));
				qna.setContent(rs.getString("content"));
				qna.setRegdate(rs.getString("regdate"));
				qna.setHit(rs.getInt("hit"));
				qna.setTeam(rs.getInt("team"));
				qna.setRank(rs.getInt("rank"));
				qna.setDepth(rs.getInt("depth"));
				
				list.add(qna);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt, rs);
		}
		return list;
	}
	
	// select
	public QnA select(int qna_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		QnA qna = null;
		ArrayList<QnA> list = new ArrayList<QnA>();
		
		con=dbManager.getConnection();
		String sql = "select * from qna where qna_id=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qna_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				qna = new QnA();
				qna.setQna_id(rs.getInt("qna_id"));
				qna.setWriter(rs.getString("writer"));
				qna.setTitle(rs.getString("title"));
				qna.setContent(rs.getString("content"));
				qna.setRegdate(rs.getString("regdate"));
				qna.setHit(rs.getInt("hit"));
				qna.setTeam(rs.getInt("team"));
				qna.setRank(rs.getInt("rank"));
				qna.setDepth(rs.getInt("depth"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt, rs);
		}
		return qna;
	}
	
	// update
	public int update() {
		int result = 0;
		return result;
	}
	
	// delete
	public int delete() {
		int result = 0;
		return result;
	}
}
