import java.util.List;

interface ViewInterface {
    void successStart();
    void successFinish();
    void successAddData();
    void showSearchResult(List<TextModel> resultList);
    void successDeleteData();
    void showResultList(List<TextModel> resultList);
    void showError(String errorText);
    void showNoData();
}