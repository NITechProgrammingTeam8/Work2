// GUIによる入出力処理を行うクラス
// 後々、Viewインターフェースを実装してください

class GUI {

    Presenter presenter;
    init() {
        presenter = new Presenter(this);
    }

}