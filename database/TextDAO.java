import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TextDAO{

	public static int WORDNUM = 0;
	public static Connection conn = null;
	public static final String connDB = "jdbc:sqlite:data.db";

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
			//rs.close();
			//pStmt.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	// 動詞毎のtableを作成
	public static void createTab(String tablename){
		//Connection conn = null;
		PreparedStatement pStmt = null;
		String sql;
		StringBuilder buf = new StringBuilder();
		try{

		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection(connDB);
		conn.setAutoCommit(false);

		//sql = "create table tbname(id int, namea text, nameb text)";
		buf.append("create table ");
		buf.append(tablename);
		buf.append("(id int, namea text, nameb text)");
		sql = buf.toString();
		buf.setLength(0);
		pStmt = conn.prepareStatement(sql);
		pStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	public static void insertTextTab(String tablename, int id, String namea, String nameb) {
		//Connection conn = null;
		PreparedStatement pStmt = null;
		String sql;
		StringBuilder buf = new StringBuilder();
		try{

		Class.forName("org.sqlite.JDBC");
		if(conn == null){
			 openConn();
		}

		//sql = "insert into Documents values(?, ?, ?)";
		buf.append("insert into ");
		buf.append(tablename);
		buf.append(" values(?, ?, ?)");
		sql = buf.toString();
		buf.setLength(0);
		pStmt = conn.prepareStatement(sql);
		pStmt.setInt(1, id);
		pStmt.setString(2, namea);
		pStmt.setString(3, nameb);
		pStmt.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		} finally {

		}
	}
}