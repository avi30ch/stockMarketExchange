import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;
import com.google.gson.JsonObject;



public class JDBCConnector{
	public static final long serialVersionUID = 1;
	
	public int registerUser(String username, String password, String email)
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		int userID = -1;
		System.out.println("Loggin");
		try {
			conn = DatabaseUtil.getConnection();
			st = conn.createStatement();
			
			rs = st.executeQuery("SELECT * FROM login.Users WHERE username='" + username + "'");
			if(!rs.next()) {
				st = conn.createStatement();
				rs = st.executeQuery("SELECT * FROM Users WHERE email='" + email + "'");
				if(!rs.next()) {
					rs.close();
					st.execute("INSERT INTO Users (username, password, email, CashBalance) VALUES ('" + username + "','" + password + "','" + email + "', 50000)");
					
					rs = st.executeQuery("SELECT LAST_INSERT_ID()");
					if (rs.next()) {
					    userID = rs.getInt(1); // This will get the last inserted user ID.
					}

				}
				else {
					userID = -2;
				}
			}
		}
		catch(SQLException sqle) {
			System.out.println("SQLException in registerUser.");
			sqle.printStackTrace();
		}
		finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(st!=null) {
					st.close();
				}
				if(conn != null) {
					conn.close();
				}
			}
			catch(SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		return userID;
	}
	public int loginUser(String username, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		int userID = -1;
		
		try {
			conn = DatabaseUtil.getConnection();
			st = conn.createStatement();
			
			rs = st.executeQuery("SELECT * FROM login.Users WHERE username='" + username + "'");
			
			if(rs.next()) {
				st = conn.createStatement();
				rs = st.executeQuery("SELECT * FROM Users WHERE password='" + password + "'");
				if(rs.next()) {
					System.out.println("Logged in");
					rs = st.executeQuery("SELECT LAST_INSERT_ID()");
					if (rs.next()) {
					    userID = rs.getInt(1); // This will get the last inserted user ID.
					}
				}
				else {
					userID = -2;
				}
			}
		}
		catch(SQLException sqle) {
			System.out.println("SQLException in registerUser.");
			sqle.printStackTrace();
		}
		finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(st!=null) {
					st.close();
				}
				if(conn != null) {
					conn.close();
				}
			}
			catch(SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		return userID;
		
	}
	public int portfolio(String username, String ticker, int quantity) {
	    
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		int userID = -1;
		int tradeID = -1;
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			st = conn.createStatement();
			
			rs = st.executeQuery("SELECT * FROM login.Users WHERE username='" + username);
			
			if(rs.next()) {
				userID = rs.getInt(2);
				st = conn.createStatement();
				st.execute("INSERT INTO login.Portfolio (userID, Ticker, Quantity, Price) VALUES ('" + userID + "','" + ticker + "','" + quantity + 35.0);
					System.out.println("Logged in");
					rs = st.executeQuery("SELECT LAST_INSERT_ID()");
					if (rs.next()) {
					    tradeID = rs.getInt(1); // This will get the last inserted trader ID.
				}
				else {
					tradeID = -2;
				}
			}
		}
		catch(SQLException sqle) {
			System.out.println("SQLException in registerUser.");
			sqle.printStackTrace();
		}
		
		return tradeID;
	}
	public double getUserCash(int userID) throws SQLException, ClassNotFoundException {
        double balance = -1;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql = "SELECT balance FROM Users WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    balance = rs.getDouble(5);
                }
            }
        }
        return balance;
    }
	public boolean processTrade(String ticker, int quantity, int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;

        try {
            conn = DatabaseUtil.getConnection();
            // Assuming you have a table for trades
            String sql = "INSERT INTO login.Portfolio (userId, ticker, quantity, tradeDate) VALUES (?, ?, ?, NOW())";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setString(2, ticker);
            pstmt.setInt(3, quantity);

            int affectedRows = pstmt.executeUpdate();
            isSuccess = affectedRows > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
        
        return isSuccess;
    }
}
