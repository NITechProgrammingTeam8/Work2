☆作成したもの
テキストファイルに対して読み書きを行うプログラム
(TextCon.java)
DB作成時のアクセス用DAO
(TextDAO.java)
実行用仮プログラム
(Test.java)
加えて、実行時に作成したDB(data.db)が追加されます


☆改良を加えたもの(極僅かのものを含む)
Presenter.java
≪try-catch関係≫
≪private TextCon textCon = new TextCon();とこれに関連する行≫
Dao.java
≪getIDメソッド≫
≪DBアクセスの際のtextをlistに変更≫
≪その他細かい修正≫
TextModel.java
≪ゲッタ追加≫
ViewInterface.java
≪import java.util.List;≫
GUI.java
≪presenter = new Presenter(); // 【Text.java実行用】≫
Unify.java
≪System.out.println((new Unifier()).unify(arg[0],line));≫


☆動作時に使用するもの
data.txt(dataset_example.txt)と同じもの
TextDAO.java
TextCon.java
sqlite-jdbc-3.21.0.jar
Test.java
Presenter.java


☆動作環境
とりあえずeclipseで動くことは確認できてます
(だたし、jarファイルをビルドパスを通してインポートする必要あり)


☆できること
テキストファイルからデータセット読み込んで、行番号とその一文を格納したDBを作成する

DBに文章の追加、削除
検索時のDBからのデータの取得

プログラム終了時に格納されているDBの内容をテキストファイルに上書きする


☆注意点
DBからデータ取得してリストに格納するとき、uuidが要るかどうか検討中です
どちらでも変更可能にプログラムは作成しました
現在はuuidとtextを格納してます

この時の名前がlineになってなかったのは私の伝達ミスですねすみません…混乱するといけないので、lineへの修正は必要最低限にしてあります(DBアクセス部分のみ)

テーブル名はtexts
要素としてuuid(int),line(text)を持つ

色々変更しましたが、DBアクセス関係を変更しただけなので、おそらく他に影響はないです


☆実行例
実行すると、同じファイルに上書きする形でテキストファイルが書き換わるのでご注意ください
Test.java実行後のテキストファイルはdataset_example_af.txtという名前であげておくので、確認はそちらでお願いします
