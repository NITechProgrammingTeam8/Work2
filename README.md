# Work2
知能プログラミング演習Ⅱ第二回課題

# MVPアーキテクチャの導入
## Model
   - 今回のデータベースやテキストファイルに当たる
   - データ等を保管する機構または通信部に値する
## Presenter
   - ViewとModelの間に入って疎関係な依存を築き円滑な利用を実現する機構
   - Viewによって使用され、Modelを使用する関係にある
   - エラーハンドリングを行う
   - Viewからの処理を受け取り、内部制御を担当する
## View
   - 入出力処理を受け持つ
   - Presenterメソッドを叩く

# GitHubの使用方法
## 1.リポジトリをローカルにcloneする
   - 自分のPCの知能プログラミング演習Ⅱ用のディレクトリに移動して git clone [リポジトリのurl]　を実行する
## 2.ブランチを作る
何か変更を行う時は、必ずブランチを作るようにしてください
masterブランチに直接変更を加えることは、他の開発者との変更衝突が起きるリスクが非常に高いです
取り返しのつかないことになりうるので masterへのpushは原則厳禁です
   - ローカルにブランチを作成する git branch [ブランチ名] を実行
   - 作成したブランチに移動する git checkout [ブランチ名] を実行
   - リモートにブランチを登録する git push -u origin [ブランチ名]を実行
## 3.変更を行う（今回は探索アルゴリズム単位）
作成したブランチ内で変更を行います
必ず、自分のいるブランチをチェックしてから変更を行いましょう git branch を実行
   - コードの変更を行う
   - 新規作成ファイルが存在する場合には、そのファイルをGit管理に登録する git add [ファイル名] を実行
   - コミットする git commit -a -m "コミットメッセージ" を実行
   - プッシュしてリモートに反映する git push origin を実行
   - コードの変更を行うに戻って、変更が全て終わるまで繰り返す
## 4.全ての変更が完了
3における変更が完了した場合です
今回は増田(switch23)にお知らせください
マージ作業に移ります
   - GitHub上からマージを行う
   - ローカルでmasterブランチに移動する git checkout master を実行
   - ローカルに変更を反映する git pull origin master を実行
   - ローカルブランチを削除する git branch --delete [ブランチ名] を実行
   - GitHub上からリモートブランチを削除する
## 5.リモートへの反映が完了

# 練習
自分のレポートを追加してGitに反映してみる
## 0.準備
   - 現在Gitに反映させていない内容がある場合は、 git push origin を実行する
   - git checkout master を実行
   - git pull origin master を実行
   - git branch --delete [自作したブランチ] を実行
## 1.ブランチ作成
   - git branch feature/[自分の名前]report を実行
   - git checkout feature/[自分の名前]report を実行
   - git push -u origin feature/[自分の名前]report を実行
## 2.レポート作成
   - Work1/reportに移動 cd Work1/report を実行
   - touch [学籍番号].tex を実行
   - [学籍番号].tex を編集
## 3.Gitに反映
   - git add --all を実行
   - git commit -a -m "Add report" を実行
   - git push origin を実行
   - 増田に連絡
   - 増田がmerge
## 4.merge後
   - git checkout master を実行
   - git pull origin master を実行
   - git branch --delete feature/[自分の名前]report を実行
   
# 問題・トラブルシューティング
   - エラーを貼り付けてGoogle先生に問い合わせる
   - 連絡いただければZoomでペアプロします
