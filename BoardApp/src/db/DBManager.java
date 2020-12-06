package db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager{
	String driver = "org.mariadb.jdbc.Driver";
	String url = "jdbc:mariadb://localhost:3306/db1202";
	String user="root";
	String password ="1234";

	public Connection getConnection(){// 접속
		Connection con=null;
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,password);

		}catch(ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("드라이버를 로드할수 없네요");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return con;
	}
	public void release(Connection con){// 해제
	
		if(con!=null){
			try{
				con.close();
			}catch(SQLException e){
			}
		}
	}
	public void release(Connection con, PreparedStatement pstmt){ //DML용
		if(pstmt!=null){
			try{
				pstmt.close();
			}catch(SQLException e){
			}
		}
		if(con!=null){
			try{
				con.close();
			}catch(SQLException e){
			}
		}

	}
	public  void release(Connection con, PreparedStatement pstmt,ResultSet rs){
		if(rs!=null){
			try{
				rs.close();
			}catch(SQLException e){
			}
		}
		if(pstmt!=null){
			try{
				pstmt.close();
			}catch(SQLException e){
			}
		}
		if(con!=null){
			try{
				con.close();
			}catch(SQLException e){
			}
		}
	}
}