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
	
	// --> ������, Ŭ������ ����� ���� ���̱⿡ �ν��Ͻ��� ������ ��ȸ�� ���� Ŭ�������� �δ�������.
	public static PoolManager2 getInstance() {
		if(instance==null) {
			instance = new PoolManager2();
		}
		return instance;
	}
	
	
	// Connection pool �� �ʿ��� �ڿ��� ��ȯ���ִ� �뿩 �޼ҵ�
	public Connection getConnection() {
		Connection con = null;
		try {
			con = ds.getConnection();			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return con;
	}
	// �ݳ����ִ� �޼ҵ�
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
