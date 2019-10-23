☆作成したもの
テキストファイルを読み込んでDBに格納するプログラム
(UnifyDB.java)
DB作成時のアクセス用DAO
(TextDAO.java)
加えて、実行時に作成したDB(data.db)が追加されます

☆動作時に使用するもの
data.txt(dataset_example.txt)と同じもの
TextDAO.java
UnifyDB.java
sqlite-jdbc-3.21.0.jar

☆動作環境
とりあえずeclipseで動くことは確認できてます
(だたし、jarファイルをビルドパスを通してインポートする必要あり)

☆できたこと
テキストファイルからデータセット読み込んで、動詞を名前に持つテーブルを作成する

☆できなかったこと・注意点
検索時にもっと分かりやすくなりかつ汎用性のある格納方法検討中…

isという名前のテーブルが作成できないことが途中で判明したので、動詞のテーブルは、全ての単語の後ろに"s"を足してテーブル名としてあるので、実行時はお気を付けください(下の実行例に具体例あり)

今後は、これの改良と検索用のDAOなどの検索方法の検討などしようかと考え中です


☆実行例(sqlite3がインストールされていないと動かないです)
作成されたDBの確認のため
とりあえず格納はちゃんとできてました
これはER図とかで表現した方がいい感じなのでしょうか？

C:\Users\Owner\java\kadai2>sqlite3 data.db
SQLite version 3.28.0 2019-04-16 19:49:53
Enter ".help" for usage hints.
sqlite> .table
hass      iss       lovess    studiess
sqlite> select * from iss where id = 1;
1|Hanako|a girl
sqlite> select * from iss where id = 2;
2|Hanako|a student
sqlite> select * from iss where id = 3;
3|student|a kind of human
sqlite> select * from iss where id = 9;
9|dog|a kind of mammal
sqlite> select * from iss where id = 10;
sqlite> select * from hass where id = 1;
1|Hanako|a hobby of playing video-games
sqlite> select * from hass where id = 2;
2|Hanako|a hobby of playing air-guitar
sqlite> select * from hass where id = 3;
3|Taro|a hobby of playing video-games
sqlite> select * from hass where id = 4;
4|Taro|a pet named Jiro
sqlite> select * from hass where id = 5;
5|Jiro|a hobby of playing frisbee
sqlite> select * from studiess where id = 1;
1|Hanako|philosophy
sqlite> select * from lovess where id = 1;
1|Hanako|Taro
sqlite>