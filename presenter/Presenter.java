import java.io.FileNotFoundException;

// DaoやTextDAOなどのModelクラスとViewクラスの接合部

class Presenter {

    private Dao dao = new Dao();
    private TextDAO textDao = new TextDAO();

    private ViewInterface view;

    public Presenter(ViewInterface view) {
        this.view = view;
    }

    // GUI起動時にTextDAOに対してテキストファイルからデータベースへの読み込みを指示する
    // あおしゅー：起動時に必ずこのメソッドを呼んでください
    // のりこさん：テキストファイルからデータベースへの読み込みを行うメソッドを用意してください
    public void start() {
        try {
            textDao.readTextFile();
            view.successStart();
        } catch(FileNotFoundException e) {
            view.showError(e.toString());
        }
    }

    // GUI終了時にTextDAOに対してデータベースからテキストファイルへの書き込みを指示する
    // あおしゅー：終了時に必ずこのメソッドを呼んでください
    // のりこさん：データベースからテキストファイルへの書き込みを行うメソッドを用意してください
    public void finish() {
        try {
            textDao.writeTextFile();
            view.successFinish();
        } catch(FileNotFoundException e) {
            view.showError(e.toString());
        }
        
    }

    // 新規データをデータベースに書き込むように指示する
    // あおしゅー：データ追加のアクションを受け取ったとき、このメソッドを呼んでください
    public void addData(String newData) {
        try {
            dao.addData(newData);
            view.successAddData();
        } catch(SQLException e) {
            view.showError(e.toString());
        } catch(ClassNotFoundException e) {
            view.showError(e.toString());
        }
    }

    // 検索データをデータベースから取得するように指示する
    // あおしゅー：データ検索のアクションを受け取ったとき、このメソッドを呼んでください
    public void searchData(String targetData) {
        List<String> resultList;
        try {
            resultList = dao.SearchData(targetData);
            if(resultList.length() == 0) {
                view.showNoData();
            } else {
                view.showResultList(resultList);
            }
        } catch(SQLException e) {
            view.showError(e.toString());
        } catch(ClassNotFoundException e) {
            view.showError(e.toString());
        }
    }

    // 削除データをデータベースに削除するように支持する
    // あおしゅー：データ削除のアクションを受け取ったとき、このメソッドを呼んでください
    public void deleteData(String targetData) {
        try {
            dao.deleteData(targetData);
            view.successDeleteData();
        } catch(SQLException e) {
            view.showError(e.toString());
        } catch(ClassNotFoundException e) {
            view.showError(e.toString());
        }
    }
}