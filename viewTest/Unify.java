//package Work2;
/***
Unify Program written



変数:前に？をつける．

Examle:
% Unify "Takayuki" "Takayuki"
true

% Unify "Takayuki" "Takoyuki"
false

% Unify "?x am Takayuki" "I am Takayuki"
?x = I .

% Unify "?x is ?x" "a is b"
false

% Unify "?x is ?x" "a is a"
?x = a .

% Unify "?x is a" "b is ?y"
?x = b.
?y = a.

% Unify "?x is a" "?y is ?x"
?x = a.
?y = a.

Unify は，ユニフィケーション照合アルゴリズムを実現し，
パターン表現を比較して矛盾のない代入によって同一と判断
できるかどうかを調べる．

ポイント！
ここでは，ストリング同士の単一化であるから，出現検査を行う必要はない．
しかし，"?x is a"という表記を"is(?x,a)"とするなど，構造を使うならば，
単一化において出現検査を行う必要がある．
例えば，"a(?x)"と"?x"を単一化すると ?x = a(a(a(...))) となり，
無限ループに陥ってしまう．

***/

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

class Unify {
  public static void main(String arg[]){
	/* 「?x is a boy」と「?x loves ?y」の両方が与えられたときに，
	 * (?x, ?y) の全ての可能な変数束縛の集合として,
	 * {(Taro, Jiro), (Jiro, Hanako)}を返すこと．
	 */
	System.out.println((new Unifier()).unify(arg[0],arg[1]));

	  //Unifier un = new Unifier();
	  //un.unify(arg[0], arg[1]);

  }
}

class Unifier {
  StringTokenizer st1;
  String buffer1[];
  StringTokenizer st2;
  String buffer2[];
  HashMap<String,String> vars;
  int flag = 0;		//Missフラグ		//現状は,Missとは1文におけるトークン単位における定数同士のmissにします.
  List<String> KeyList;		//マッチング候補のKey
  List<String> ValueList;	//マッチング候補のValue

  //コンストラクタ
  Unifier(){
	  System.out.println("コンストラクタ1に入りました");
      vars = new HashMap<String,String>();
  }

  //コンストラクタ
  public boolean unify(String string1,String string2,HashMap<String,String> bindings){
      System.out.println("コンストラクタ2に入りました");
	  this.vars = bindings;
      return unify(string1,string2);
  }

  public boolean unify(String string1,String string2){

	  //System.out.println(string1);
      //System.out.println(string2);

      //2回も同じ処理するの嫌だから配列にしてループさせます.
      String[] args = {string1, string2};

      //とりあえずデバック用
      for(int i = 0; i < args.length; i++)
          System.out.println("args[" + i + "] = " + args[i]);

      //こっからメイン!
      for(int num = 0; num < args.length; num++) {
	  /*text読み込み*/
	  try {    // ファイル読み込みに失敗した時の例外処理のためのtry-catch構文
		  //String fileName = "dataset.txt"; // ファイル名指定
		  String fileName = "dataset_example.txt";

		  // 文字コードUTF-8を指定してBufferedReaderオブジェクトを作る
		  BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

		  // 変数lineに1行ずつ読み込むfor文
		  for (String line = in.readLine(); line != null; line = in.readLine()) {

			  System.out.println("line = " + line);
			  /************ここからは通常の処理内容****************/
		      // 同じなら成功
		      if(args[num].equals(line)) {
		    	  //return true;	//ここは帰らずに,データを保存してほしんだけど...

		    	  //ここfor文全体を囲めば,いいな!! 今回,どうでもいいし!

		      }

		      // 各々トークンに分ける
		      st1 = new StringTokenizer(args[num]);
		      st2 = new StringTokenizer(line);

		      // 数が異なったら失敗
		      if(st1.countTokens() != st2.countTokens()) {
		    	  //return false;  //ミスっても,帰らずに...

		    	  //ここfor文全体を囲めば,null入れて補正させる必要もないな!!!!!

		    	  System.out.println("Search Error 1");
		      }

		      // 定数同士

		      /* (注意点)
		       * 文字列をトークンごとに分解したときに,トークンの数が合わないとErrorが起きるので...
		       * 長いほうに合わせます!
		       */
		      int length = st1.countTokens();
		      if(st1.countTokens() < st2.countTokens())
		    	  length = st2.countTokens();

		      buffer1 = new String[length];
		      buffer2 = new String[length];
		      KeyList = new ArrayList<String>();   //Keyリスト
		      ValueList = new ArrayList<String>(); //Valueリスト

		      //System.out.println("length = " + length);
		      //System.out.println("st1.countTokens() = " + st1.countTokens());
		      //System.out.println("st2.countTokens() = " + st2.countTokens());

		      /* 調整した分, 無いところにはnullを入れとく
		       * (注意点)
		       * st1.countTokens()の値は,st1.nextToken()でトークンを取り出すたびに減ります!
		       * → あらかじめ保存しておきますわ！
		       */
		      int st1len = st1.countTokens();
		      int st2len = st2.countTokens();

		      for(int i = 0 ; i < length; i++){ //4

		    	  //トークン1に関して
		    	  if(i < st1len) //4
		    		  buffer1[i] = st1.nextToken();
		    	  else
		    		  buffer1[i] = null;

		    	  //トークン2に関して
		    	  if(i < st2len) //3
		    		  buffer2[i] = st2.nextToken();
		    	  else
		    		  buffer2[i] = null;

		          System.out.println("buffer1[" + i + "] = " + buffer1[i]);
		    	  System.out.println("buffer2[" + i + "] = " + buffer2[i]);
		      }


		      System.out.println("flag = " + flag);

		      for(int i = 0 ; i < length ; i++){ //1語ずつマッチングしていきます
		    	  System.out.println("flag = " + flag);

		          if(!tokenMatching(buffer1[i],buffer2[i])){ //マッチングできてないなら...
		              //return false;
		        	  System.out.println("Search Error 2");

		        	  /* (注意)
		        	   *  1語でもマッチングしてないものがあれば,先のマッチング成功は無しにする
		        	   * ex) ?x is a boy と Hanako is a girl
		        	   * ?x = Hanakoで先にマッチングするが,その後, boy != girlなので先のマッチング結果を取り消す.
		        	   * (感)おそらく,1回マッチングしちゃうんと,上書きされないってことですね.
		        	   *
		        	   * んで...return falseで返すとしっかりそこ無かったことにしてくれるんですよね～ ← いやそうでもないか...
		        	   * falseって返して逃げてるだけか...
		        	   *
		        	   * 希望としては, ここでミスったら,次の文章に進んでほしいんですよね
		        	   */

		        	  //if(flag == 1) {	//もし(一度マッチングしてるのに)他の単語でエラーが起きたら...

		        		  //HashMapからさっき入れたKey(tokenMatchingメソッドより,変数)を探し,そいつを削除するぜ！
		        		  //↑それだと1つのKeyに複数対応ができなくなるので...
		        		  //やはりKeyとValueセットにしないと!
		        		  //for(int len = 0; len < KeyList.size(); len++) {
		        		  //	  vars.remove(KeyList.get(len),ValueList.get(len));
		        		  //}
		        		  // ↑ だと, ミスした後にマッチングしたら,それを消去できない.
		        		  /* eg)
		        		   *line = Hanako studies philosophy
							buffer1[0] = ?x
							buffer2[0] = Hanako
							buffer1[1] = loves
							buffer2[1] = studies
							buffer1[2] = ?y
							buffer2[2] = philosophy
							flag = 0
							flag = 0
							token1 = ?x
							token2 = Hanako
							Search Error 2
							途中結果は{?x=Taro}

							flag = 0
							token1 = loves
							token2 = studies
							Search Error 2
							途中結果は{?x=Taro}

							flag = 0
							token1 = ?y
							token2 = philosophy
							途中結果は{?x=Taro, ?y=philosophy}  //←ここを消せない...

							やはり,「HashMapに入れて,その後,ダメだったら消す」というやり方はOUT!!!
		        	  }*/
		        	  flag = 1;
		          }
		          //とりあえずデバック用
		          //System.out.println("途中結果は"+vars.toString()+"\n");  //もう1トークン事にやる必要ないので！

		      }//1文の解析がすべて終わって...
	          if(flag == 0) {	//missflagが一度も上がっていないなら,
	        	  for(int len = 0; len < KeyList.size(); len++)
	        		  vars.put(KeyList.get(len), ValueList.get(len));  //HashMapに格納
	          }

	          System.out.println("途中結果は"+vars.toString()+"\n");
	          //System.out.println("Valuesは"+vars.values()+"\n");
	          flag = 0; //falgのリセット
		      /***********************************************/
		  }
		  in.close();  // ファイルを閉じる
	  } catch (IOException e) {
      e.printStackTrace(); // 例外が発生した所までのスタックトレースを表示
	  }

	  System.out.println(num+1 + "つ目の結果は"+vars.toString()+"\n");
      } //←引数2つループ用

      // 最後まで O.K. なら成功
      System.out.println("最終結果は"+vars.toString());


      //HashMapのメソッド↓
      //https://docs.oracle.com/javase/jp/8/docs/api/java/util/HashMap.html
      System.out.println("Key:?xに該当する値:Value = " + vars.get("?x"));
      System.out.println("Key:?yに該当する値:Value = " + vars.get("?y"));

      return true;
  }

  boolean tokenMatching(String token1,String token2){
	  System.out.println("token1 = " + token1);
	  System.out.println("token2 = " + token2);
	  if(token1 == null || token2 == null) return false;
      if(token1.equals(token2)) return true;
      if( var(token1) && !var(token2)) return varMatching(token1,token2);
      if(!var(token1) &&  var(token2)) return varMatching(token2,token1);
      if( var(token1) &&  var(token2)) return varMatching(token1,token2);
      return false;
  }

  boolean varMatching(String vartoken,String token){
      if(vars.containsKey(vartoken)){		//HashMapにvartokenというキー(Not値)が存在するかどうか
          if(token.equals(vars.get(vartoken))){		//"HashMapにKeyであるvartokenに対応するValue値" と "token"が同じだったら...
              return true;
         } else { //ちがってても対応しないと！とりあえず,なんでもtrueにしちゃおう！(笑)
              return false;
          }
      } else {
          replaceBuffer(vartoken,token);
          if(vars.containsValue(vartoken)){		//HashMapにvartokenという値(Notキー)が存在するかどうか
              replaceBindings(vartoken,token);
          }
          //ここではまだしません.
          //vars.put(vartoken,token);		//キーと値を対応づけ
          //格納しておくだけ
          KeyList.add(vartoken);
          ValueList.add(token);
          //flag = 1;		//HashMapに格納させれたのでFlag上げます! → flagの使い方変更
      }
      return true;
  }

  /*
   * 配列中に存在する同じ名前の変数をすべて定数に置き換える
   */
  void replaceBuffer(String preString,String postString){
      for(int i = 0 ; i < buffer1.length ; i++){
          if(preString.equals(buffer1[i])){
              buffer1[i] = postString;
          }
          if(preString.equals(buffer2[i])){
              buffer2[i] = postString;
          }
      }
  }

  /*
   * x=a(定数), y=xの場合に, y=aにしちゃいます.
   * (実行例)
   * ?x is a
	 ?y is ?x
	 preString = ?y
	 postString = a
	 key = ?x
	 {?x=a, ?y=a}
	 true
   *
   * ?y is ?x
	 ?x is a
	 preString = ?x
	 postString = a
	 key = ?y
	 {?x=a, ?y=a}
	 true
   */
  void replaceBindings(String preString,String postString){
  Iterator<String> keys;
  for(keys = vars.keySet().iterator(); keys.hasNext();){
      String key = (String)keys.next();
      //System.out.println("preString = " + preString);
      //System.out.println("postString = " + postString);
      //System.out.println("key = " + key);
      if(preString.equals(vars.get(key))){  //get(key)で,keyに対応づけられた"値"を取り出す
    	  //System.out.println("入,"+vars.get(key));
    	  vars.put(key,postString);
      }
  }
  }


  boolean var(String str1){
  // 先頭が ? なら変数
	  return str1.startsWith("?");
  }

}