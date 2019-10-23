interface ViewInterface {
    void successStart();
    void successFinish();
    void successAddData();
    void showResultList(List<String> resultList);
    void successDeleteData();
    void showError(String errorText);
    void showNoData();
}