import java.sql.DriverManager;

// 直接データベースにアクセスするクラス

class Dao {

    private static final String loadClassName = "org.sqlite.JDBC";
    private static final String connDB = "jdbc:sqlite:data.db";

    Connection conn = null;

    public void addData(String newData) throws Exception  {
        openConn();
    }

    public List<String> SearchData(String targetData) throws Exception {
        openConn();
    }

    public void deleteData(String targetData) throws Exception {
        openConn();
    }

    private void openConn() throws Exception {
        Class.forName(loadClassName);
        conn = DriverManager.getConnection(connDB);
    }
}