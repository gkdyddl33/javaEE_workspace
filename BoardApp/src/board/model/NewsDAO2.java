package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBManager;

public class NewsDAO2 {
	DBManager dbManager = new DBManager();
	
	public ArrayList selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		ArrayList<News> list = new ArrayList<News>();
		
		con = dbManager.getConnection();
		
		// -------------------------
		StringBuilder sb = new StringBuilder();
		sb.append("select n.news_id as news_id, writer, title, regdate, hit, count(comment_id)as cnt");
		sb.append("from news n left outer join comments c");
		sb.append("on n.news_id = c.comments_id");
		sb.append("group by n.news_id, writer,title,regdate,hit order by n.news_id desc");
		
		String sql = "select * from news order by news_id=?";		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				News news = new News();
				news.setNews_id(rs.getInt("news_id"));
				news.setTitle(rs.getString("title"));
				news.setWriter(rs.getString("writer"));
//				news.setContent(rs.getString("content"));
				news.setRegdate(rs.getString("regdate"));
				news.setHit(rs.getInt("hit"));
				news.setCnt(rs.getInt("cnt"));
				list.add(news);				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt, rs);
		}	
		
		return list;
	}
}
