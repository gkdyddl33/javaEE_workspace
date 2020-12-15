// �����ͺ��̽��� �ϴ��Ϸ� �����Ǵ� Ŭ����
package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.DatabaseUtil;

public class UserDAO {
	
	public int join(String userID, String userPassword) {
		Connection con = DatabaseUtil.getConnection();
		String sql = "insert into user values(?,?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPassword);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
