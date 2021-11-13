package project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	
	public static Connection connect() {
		Connection con=null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url="jdbc:oracle:thin:@localhost:1521:xe";			
			con=DriverManager.getConnection(url,"c##yhw9243","1111");
			con.setAutoCommit(false);
			return con;
		}catch(ClassNotFoundException ce) {
			ce.printStackTrace();
		}catch(SQLException se) {
			se.printStackTrace();
		}
		return null;
	}
	public static void disconnect(Connection con) {
		try {
			if(con!=null) con.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	public static void close(Statement st) {
		try {
			if(st!=null) st.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	public static void close(Statement st,Statement st2) {
		try {
			if(st2!=null) st2.close();
			if(st!=null) st.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	public static void close(Statement st,Statement st2,Statement st3) {
		try {
			if(st3!=null) st3.close();
			if(st2!=null) st2.close();
			if(st!=null) st.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	public static void close(Statement st,ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	public static void close(Statement st,Statement st2,ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(st2!=null) st2.close();
			if(st!=null) st.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	public static void close(Statement st,Statement st1,ResultSet rs,ResultSet rs1) {
		try {
			if(rs1!=null) rs.close();
			if(rs!=null)rs.close();
			if(st1!=null) st1.close();
			if(st!=null) st.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	public static void close(Statement st,Statement st1,Statement st2,ResultSet rs,ResultSet rs1,ResultSet rs2) {
		try {
			if(rs2!=null) rs.close();
			if(rs1!=null) rs.close();
			if(rs!=null)rs.close();
			if(st2!=null) st2.close();
			if(st1!=null) st1.close();
			if(st!=null) st.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
}
