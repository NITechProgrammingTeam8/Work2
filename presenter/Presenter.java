// DaoやTextDAOなどのModelクラスとViewクラスの接合部

class Presenter {

    private Dao dao = new Dao();
    private TextDAO textDao = new TextDAO();

    // GUI起動時にTextDAOに対してテキストファイルからデータベースへの読み込みを指示する
    // あおしゅー：起動時に必ずこのメソッドを呼んでください
    // のりこさん：テキストファイルからデータベースへの読み込みを行うメソッドを用意してください
    public void start() {

    }

    // GUI終了時にTextDAOに対してデータベースからテキストファイルへの書き込みを指示する
    // あおしゅー：終了時に必ずこのメソッドを呼んでください
    // のりこさん：データベースからテキストファイルへの書き込みを行うメソッドを用意してください
    public void finish() {

    }

    // 新規データをデータベースに書き込むように指示する
    // あおしゅー：データ追加のアクションを受け取ったとき、このメソッドを呼んでください
    public void addData(String newData) {
        dao.addData(newData);
    }

    // 検索データをデータベースから取得するように指示する
    // あおしゅー：データ検索のアクションを受け取ったとき、このメソッドを呼んでください
    public List<String> searchData(String targetData) {
        dao.SearchData(targetData);
    }

    // 削除データをデータベースに削除するように支持する
    // あおしゅー：データ削除のアクションを受け取ったとき、このメソッドを呼んでください
    public void deleteData(String targetData) {
        dao.deleteData(targetData);
    }
}