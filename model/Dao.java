import java.sql.DriverManager;
import java.sql.PreparedStatement;

// 直接データベースにアクセスするクラス

class Dao {

    private static final String loadClassName = "org.sqlite.JDBC";
    private static final String connDB = "jdbc:sqlite:data.db";

    private static final String INSERT_STATEMENT = "INSERT INTO texts(uuid,text) VALUES(?,?)";
    private static final String SELECT_STATEMENT = "SELECT * FROM texts";
    private static final String DELETE_STATEMENT = "DELETE FROM texts WHERE uuid = ";

    Connection conn = null;

    public void addData(String newData) throws Exception  {
        openConn();
        conn.setAutoCommit(false);
        PreparedStatement pStmt = conn.prepareStatement(INSERT_STATEMENT);
        pStmt.setString(2, newData);
        pStmt.executeUpdate();
        conn.commit();
    }

    public List<TextModel> FetchData() throws Exception {
        openConn();
        List<TextModel> textList;
        PreparedStatement pStmt = conn.prepareStatement(SELECT_STATEMENT);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("uuid");
            String text = rs.getString("text");
            TextModel textModel = new TextModel(id, text);
            textList.add(textModel);
        }
        return textList;
    }

    public void deleteData(int targetData) throws Exception {
        openConn();
        conn.setAutoCommit(false);
        PreparedStatement pStmt = conn.prepareStatement(DELETE_STATEMENT);
        pStmt.setInt(1,targetData);
        pStmt.executeUpdate();
    }

    private void openConn() throws Exception {
        Class.forName(loadClassName);
        conn = DriverManager.getConnection(connDB);
    }
}