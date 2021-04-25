package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardCommon {
	
	static String driver = "oracle.jdbc.driver.OracleDriver";
	static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	static String user = "dev";
	static String password = "123456";
	
	public static Connection dbConnect() throws Exception {
		
		Connection con;
		

		Class.forName(driver);
		con = DriverManager.getConnection(url, user, password);
		
		con.setAutoCommit(false); //트렌젝션 처리할려면 필수입력사항. 기본은 트루다

		return con;
	}
	
	public static void dbClose(ResultSet rs) {
		
		try {
			if(rs != null) {
				rs.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("rs close error!");
		}
	}
	
	public static void dbClose(PreparedStatement pstmt) {
		
		try {
			if(pstmt != null) {
				pstmt.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("pstmt close error!");
		}
	}
	
	public static void dbClose(Connection con) {
		
		try {
			if(con != null) {
				con.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("con close error!");
		}
	}
	
	public static void commit(Connection con) {
		
		try {
			if(con != null) {
				con.commit();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("commit rror!");
		}
	}
	
	public static void rollback(Connection con) {
		
		try {
			if(con != null) {
				con.rollback();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("rollback rror!");
		}
	}
}
