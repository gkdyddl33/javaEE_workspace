package common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PoolManager2 {
	InitialContext context;
	DataSource ds;
	private static PoolManager2 instance;
	
	public PoolManager2() {
		try {
			context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");			
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	// --> 하지만, 클래스는 쓰라고 만든 것이기에 인스턴스를 가져갈 기회를 현재 클래스에서 부담해주자.
	public static PoolManager2 getInstance() {
		if(instance==null) {
			instance = new PoolManager2();
		}
		return instance;
	}
	
	
	// Connection pool 이 필요한 자에게 반환해주는 대여 메소드
	public Connection getConnection() {
		Connection con = null;
		try {
			con = ds.getConnection();			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return con;
	}
	// 반납해주는 메소드
	public void release(Connection con) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void release(Connection con,PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
	}
	public void release(Connection con,PreparedStatement pstmt,ResultSet rs) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
