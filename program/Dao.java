import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// 直接データベースにアクセスするクラス

class Dao {

    private static final String loadClassName = "org.sqlite.JDBC";
    private static final String connDB = "jdbc:sqlite:data.db";
    private static final String INSERT_STATEMENT = "INSERT INTO texts(uuid,line) VALUES(?,?)";
    private static final String SELECT_STATEMENT = "SELECT * FROM texts";
    private static final String DELETE_STATEMENT = "DELETE FROM texts WHERE uuid = ?";

    Connection conn = null;

    public void addData(String newData) throws Exception  {
        openConn();
        conn.setAutoCommit(false);
        int id = getNo() + 1;
        PreparedStatement pStmt = conn.prepareStatement(INSERT_STATEMENT);
        pStmt.setInt(1, id);
        pStmt.setString(2, newData);
        pStmt.executeUpdate();
        conn.commit();
    }



    public List<TextModel> FetchData() throws Exception {

        openConn();
        List<TextModel> textList = new ArrayList<>();
        PreparedStatement pStmt = conn.prepareStatement(SELECT_STATEMENT);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("uuid");
            String text = rs.getString("line");
            TextModel textModel = new TextModel(id, text);
            textList.add(textModel);
        }
        return textList;

    }

    /*
    // 【DBからlineしか取得しない場合】
    public List<String> FetchData() throws Exception {
    	openConn();
    	conn.setAutoCommit(false);
    	List<String> textList = new ArrayList<String>();
		PreparedStatement pStmt = conn.prepareStatement(SELECT_STATEMENT);;
		ResultSet rs = pStmt.executeQuery();
		while (rs.next()) {
			String text = rs.getString("line");
			textList.add(text);
        }
		rs.close();
		pStmt.close();
		return textList;
    }
    */


    public void deleteData(int targetData) throws Exception {
        openConn();
        conn.setAutoCommit(false);
        PreparedStatement pStmt = conn.prepareStatement(DELETE_STATEMENT);
        pStmt.setInt(1, targetData);
        pStmt.executeUpdate();
        conn.commit();
    }

    private void openConn() throws Exception {
        Class.forName(loadClassName);
        conn = DriverManager.getConnection(connDB);
    }

    public int getNo() {
		conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		String sql;
		int num = -1;
		try{
			Class.forName("org.sqlite.JDBC");
			if(conn == null){
				 conn = DriverManager.getConnection(connDB);
				 conn.setAutoCommit(false);
			}
			sql = "select max(uuid) from texts";
			pStmt = conn.prepareStatement(sql);

			rs = pStmt.executeQuery();
			if(rs.next()){
				num = rs.getInt("max(uuid)");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		} finally {
			try{
				rs.close();
				pStmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return num;
	}
}