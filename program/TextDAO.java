/*
 * DBにアクセスする
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TextDAO{

	public static Connection conn = null;
	public static final String connDB = "jdbc:sqlite:data.db";
	//public static final String connDB = "jdbc:sqlite:rugby.db";
	public static int id = 1;

	public static void conCom(){
		try{
			conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static void openConn(){
		try{
		Connection conn = null;
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection(connDB);
		conn.setAutoCommit(false);

		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	public static void closeConn(){
		try{
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	// 格納用tableを作成
	public static void createTab(){
		PreparedStatement pStmt = null;
		String sql;
		try{

		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection(connDB);
		conn.setAutoCommit(false);

		sql = "create table if not exists texts(uuid int, line text)";
		pStmt = conn.prepareStatement(sql);
		pStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	// tableに追加
	public static void insertTextTab(String line) {
		PreparedStatement pStmt = null;
		String sql;
		try{

		Class.forName("org.sqlite.JDBC");
		if(conn == null){
			 openConn();
		}

		sql = "insert into texts values(?, ?)";
		pStmt = conn.prepareStatement(sql);
		pStmt.setInt(1, id);
		pStmt.setString(2, line);
		pStmt.executeUpdate();
		id++;

		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		} finally {

		}
	}

	// DBのデータを取得
	public static ArrayList<String> getDBData() {
		conn = null;
		openConn();
        ArrayList<String> DBList = new ArrayList<String>();
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		String sql;
		try{

			Class.forName("org.sqlite.JDBC");
			if(conn == null){
				 conn = DriverManager.getConnection(connDB);
				 conn.setAutoCommit(false);
			}

			sql = "select line from texts";
			pStmt = conn.prepareStatement(sql);
			rs = pStmt.executeQuery();
			while (rs.next()) {
	            String text = rs.getString("line");
	            DBList.add(text);
	        }
			rs.close();
			pStmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		} finally {

		}
		return DBList;
	}

	// DBtableの中身の削除
	public static void deleteData() {
		conn = null;
		PreparedStatement pStmt = null;
		try{
		    Class.forName("org.sqlite.JDBC");
		    if(conn == null){
				 conn = DriverManager.getConnection(connDB);
				 conn.setAutoCommit(false);
			}
	    	String sql = "delete from texts";
	    	pStmt = conn.prepareStatement(sql);
	   		pStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		} finally {
			try{
				conCom();
				pStmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}