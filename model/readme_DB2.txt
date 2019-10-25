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
テキストファイルからデータセット読み込んで、行番号とその一文を格納したDBを作成する

☆できなかったこと・注意点
検索時にもっと分かりやすくなりかつ汎用性のある格納方法検討中…

テーブル名はtexts
要素としてid(int),line(text)を持つ


☆実行例(sqlite3がインストールされていないと動かないです)
作成されたDBの確認のため
とりあえず格納はちゃんとできてました
これはER図とかで表現した方がいい感じなのでしょうか？

C:\Users\Owner\java4\kadai2\try>sqlite3 data.db
SQLite version 3.28.0 2019-04-16 19:49:53
Enter ".help" for usage hints.
sqlite> .table
texts
sqlite> select * from texts where id = 0;
sqlite> select * from texts where id = 1;
1|Hanako is a girl
sqlite> select * from texts where id = 2;
2|Hanako is a student
sqlite> select * from texts where id = 3;
3|student is a kind of human
sqlite> select * from texts where id = 4;
4|human is a kind of mammal
sqlite> select * from texts where id = 5;
5|Hanako has a hobby of playing video-games
sqlite> select * from texts where id = 6;
6|Hanako has a hobby of playing air-guitar
sqlite> select * from texts where id = 7;
7|Hanako studies philosophy
sqlite> select * from texts where id = 8;
8|Hanako loves Taro
sqlite> select * from texts where id = 9;
9|Taro is a boy
sqlite> select * from texts where id = 10;
10|Taro is a student
sqlite> select * from texts where id = 11;
11|Taro has a hobby of playing video-games
sqlite> select * from texts where id = 12;
12|Taro studies informatics
sqlite> select * from texts where id = 13;
13|Taro loves Jiro
sqlite> select * from texts where id = 14;
14|Taro has a pet named Jiro
sqlite> select * from texts where id = 15;
15|Jiro is a boy
sqlite> select * from texts where id = 16;
16|Jiro is a dog
sqlite> select * from texts where id = 17;
17|dog is a kind of mammal
sqlite> select * from texts where id = 18;
18|Jiro has a hobby of playing frisbee
sqlite> select * from texts where id = 19;
19|Jiro loves Hanako
sqlite> select * from texts where id = 20;
sqlite>