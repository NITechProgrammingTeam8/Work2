import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TextDAO{

	public static int WORDNUM = 0;
	public static Connection conn = null;
	public static final String connDB = "jdbc:sqlite:data.db";
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
			//rs.close();
			//pStmt.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	// 格納用tableを作成
	public static void createTab(){
		//Connection conn = null;
		PreparedStatement pStmt = null;
		String sql;
		try{

		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection(connDB);
		conn.setAutoCommit(false);

		sql = "create table texts(id int, line text)";
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
		//Connection conn = null;
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
		//System.out.println("id = " + id);
		id++;

		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		} finally {
		/*try{
			//pStmt.close();
			//conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}*/
		}
	}

}