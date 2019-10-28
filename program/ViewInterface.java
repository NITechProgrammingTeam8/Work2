package Work2ver2;
import java.util.List;

interface ViewInterface {
    //データベース初期化完了メソッド
    void successStart();
    //テキストファイル記録完了メソッド
    void successFinish();
    //データ追加完了メソッド
    void successAddData();
    //検索結果反映メソッド
    void showSearchResult(List<String> resultList);
    //データ削除メソッド
    void successDeleteData();
    //一覧表示メソッド
    void showResultList(List<TextModel> resultList);
    //例外処理表示メソッド
    void showError(String errorText);
    //データ無し表示メソッド
    void showNoData();
}