//package Work2ver2;
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
	//System.out.println((new Unifier()).unify(arg[0],arg[1]));
	  if(arg.length != 2){
		    System.out.println("Usgae : % Matching [string1]");
		}

		List<String> answer = new ArrayList<String>(); // 答えを格納

		View view = new View(); // インスタンス？の作成
		Presenter presenter = new Presenter(view);

		presenter.start(); // DBの作成

		presenter.searchData(arg[0]); // 検索語入力
		answer = view.getSr(); // 答えを取得

		//answer.add((new Matcher()).matching(arg[0],line));//ここでarg[0]引数1, arg[1]に引数2

		//テスト出力用
		for(int i = 0; i < answer.size(); i++) {
			if(answer.get(i) != " ") {
				System.out.println("★answer = " + answer.get(i));
			}

		}

	  //Unifier un = new Unifier();
	  //un.unify(arg[0], arg[1]);

	  //?x is a boy, ?x loves ?y
  }
}

class Unifier {
  StringTokenizer st1;
  String buffer1[];
  StringTokenizer st2;
  String buffer2[];
  HashMap<String,List<String>> vars;	//さあ,Challengeするぜ！複数人マッチング!
  ArrayList<ArrayList<String>> vars2list;
  int valueName;	//変数の種類管理
  List<String> varslist;				//これも追加!
  int flag = 0;		//Missフラグ		//現状は,Missとは1文におけるトークン単位における定数同士のmissにします.
  List<String> KeyList;		//マッチング候補のKey
  List<String> ValueList;	//マッチング候補のValue
  List<String> arg1List;		//第1引数のマッチング結果を第2引数に反映させるため
  List<String> keyList;		//現在見ているKeyのインデントを返すため
  StringTokenizer t1;
  String buf1[];
  StringTokenizer t2;
  String buf2[];
  ArrayList<String> numH;
  ArrayList<String> arrayCopy;
  int flag2;
  List<String> lastMessage;

  //コンストラクタ
  Unifier(){
	  System.out.println("コンストラクタ1に入りました");
      vars = new HashMap<String, List<String>>();
      KeyList = new ArrayList<String>();   //Keyリスト
      //varslist = new ArrayList<String>();
      vars2list = new ArrayList<ArrayList<String>>();
      arg1List = new ArrayList<String>();
      valueName = 0;
      arrayCopy = null;
      flag2 = 0;
      lastMessage = new ArrayList<String>();
  }

  /*
  //コンストラクタ   ← 使ってないけどね...
  public boolean unify(String string1,String string2,HashMap<String,List<String>> bindings){
      System.out.println("コンストラクタ2に入りました");
	  this.vars = bindings;
      return unify(string1,string2);
  }
  */
  public List<String> unify(String string1,String string2, List<String> textName ){

    //2回も同じ処理するの嫌だから配列にしてループさせます.
    ArrayList<String> args = new ArrayList<String>();
    args.add(string1);
    args.add(string2);

    //まずは変数が何種類あるかを知りたい
    t1 = new StringTokenizer(args.get(0));
    t2 = new StringTokenizer(args.get(1));
    buf1 = new String[t1.countTokens()];
    buf2 = new String[t2.countTokens()];
    numH = new ArrayList<String>();
    for(int i=0; i<t1.countTokens(); i++) {
    	buf1[i] = t1.nextToken();
    	if(var(buf1[i]))
    			numH.add(buf1[i]);
    }
    for(int i=0; i<t2.countTokens(); i++) {
    	buf2[i] = t2.nextToken();
    	if(var(buf2[i])) {
    		for(int j=0; j<numH.size(); j++) {
    			if(!buf2[i].equals(numH.get(j)))
    				numH.add(buf2[i]);
    		}
    	}
    }
    System.out.println("変数の数 = " + numH.size());

    //1つ目の引数,2つ目の引数,順番に処理するよ！
    for(int num = 0; num < args.size(); num++) {

	  /* (注意)
	   * args[0]でとった変数の値をargs[1]に反映させて, 変数束縛させる
	   * 変数束縛の複数性より,2つ目の引数は1つ以上になるので,その分,for文を回さないといけない...
	   */

      if(num == 1) {  //2回目以降で
    	  System.out.println("num = " + num);
    	  System.out.println("args.size() = " + args.size());
    	  System.out.println("varslist = " + varslist.toString());
    	  System.out.println("vars2list = " + vars2list.toString());
    	  System.out.println("vars = " + vars.toString());

    //	for(int keyNum = 0; keyNum < KeyList.size(); keyNum++) { //変数(?x,?yなどなど)の数だけ...
    		for(int valueNum = 0; valueNum < varslist.size(); valueNum++) { //その変数に対応する値(Taro, Jiroなどなど)の数だけ
    			//args[1]に変わるargs1List{?x=Taro, ?x=Jiroなどなど}
    			System.out.println("KeyList.get("+ 0 +") = " + KeyList.get(0));
    			System.out.println("varslist.get("+valueNum+") = " + varslist.get(valueNum));

    			//文字列の中に?が入っていたら...
    			if(string2.contains("?")) {
    				//文字列として置き換えて,新しく作成！
    				String stringx = string2.replace(KeyList.get(0), varslist.get(valueNum));
    				System.out.println("stringx = " + stringx);
    				args.add(stringx);	//などなど
    			}
    		}
    //	}
    	args.remove(1); //まずは,1個置き換えて...//ここも引数2個で限定しちゃっているけど...
    	System.out.println("args.size() = " + args.size());
      }	//ん～いまは引数2つで許して...

      varslist = new ArrayList<String>();   //複数人対応のValue:値を格納させる.
      /* (miss)
       * ここでvalue:値のリストを作ると,2回目のループと3回目のループで別ものが生成されてしまう.
       * だって,args[2],args[3]で{?x=Taro,?x=Jiroの別々に?yを生成させとるもんね}
       * やはり,変数の数に対応させたvalueリストを複数生成させるしかない.
       */

	  /*text読み込み*/
//	  try {    // ファイル読み込みに失敗した時の例外処理のためのtry-catch構文
//		  String fileName = "dataset.txt"; // ファイル名指定
		  //String fileName = "dataset_example.txt";

//		  // 文字コードUTF-8を指定してBufferedReaderオブジェクトを作る
//		  BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

		  // 変数lineに1行ずつ読み込むfor文
//		  for (String line = in.readLine(); line != null; line = in.readLine()) {
      	  for(int textNum = 0; textNum < textName.size(); textNum++) {
			  System.out.println("line = " + textName.get(textNum));
			  System.out.println("textName.size() = " + textName.size());
			  /************ここからは通常の処理内容****************/
		      // 同じなら成功
		      if(args.get(num).equals(textName.get(textNum))) {
		    	  //return true;	//ここは帰らずに,データを保存してほしんだけど...
		    	  //ここfor文全体を囲めば,いいな!! 今回,どうでもいいし!
		      }

		      // 各々トークンに分ける
		      st1 = new StringTokenizer(args.get(num)); //ふつうの引数で
		      st2 = new StringTokenizer(textName.get(textNum));  //ここはふつうにデータセットの内容

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
		      //KeyList = new ArrayList<String>();   //Keyリスト
		      ValueList = new ArrayList<String>();   //Valueリスト
		      keyList = new ArrayList<String>();;		//現在見ているKeyのインデントを返す

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

		        	  flag = 1;
		          }
		          //とりあえずデバック用
		          //System.out.println("途中結果は"+vars.toString()+"\n");  //もう1トークン事にやる必要ないので！

		      } //1文の解析がすべて終わって...
	          if(flag == 0) {	//missflagが一度も上がっていないなら,
	        	  for(int len = 0; len < ValueList.size(); len++) {
	        		  //vars.put(KeyList.get(len), ValueList.get(len));  //HashMapに格納
	        		  System.out.println("ValueList.get(" + len + ") = " + ValueList.get(len));
	        		  varslist.add(ValueList.get(len));
	        	  }
	          } //Textの文全てが終わったら...

	          /* 複数人のマッチングに対応するために...
	           * HashMapの値:ValueをList化しないといけない
	           * → ということは, 1文ずつじゃなくて,Text全体を終わってから,varslistを用いて,vars.put()しないといけない
	           */

	          if(flag == 0) {
	        	  //System.out.println("num = " + num);
	        	  System.out.println("keyList.get(0) = " + keyList.get(0));
	        	  System.out.println("indexOf = " + KeyList.indexOf(String.valueOf(keyList.get(0))));
	        	  int index = KeyList.indexOf(String.valueOf(keyList.get(0)));
	        	  System.out.println("KeyList = " + KeyList.get(index));	//いま見てるKeyの番号にしないと！

	        	  //ここは,?x,?yに対応させただけだから！

	        	  System.out.println("index = " + index);
	        	  /* 参照渡しだから,作ったリストを削除してももとの場所をささない.*/
	        	  ArrayList<String> array = new ArrayList<>();
	        	  if(vars.containsKey(KeyList.get(index))) {	//すでに作ったことがあったら,
	        		  System.out.println("削除します");
	        		  array.clear();	//消す
	        		  array = arrayCopy;//コピーを戻す
	        		  flag2 = 1;
	        	  }
	        	  arrayCopy = array;	//コピーを取っておいて,
	        	  array.add(ValueList.get(0));
	        	  if(flag2 == 0)
	        		  vars2list.add(array);
	        	  System.out.println("vars2list() = " + vars2list.toString());
	        	  System.out.println("num = " + num);
	        	  System.out.println("vars2list.get(" + index + ") = " + vars2list.get(index));
	        	  vars.put(KeyList.get(index), vars2list.get(index));  //改良HashMapに格納

	        	  flag2 = 0;
	          }
	          System.out.println("途中結果は" + vars.toString() + "\n");
	          //System.out.println("Valuesは"+vars.values()+"\n");
	          flag = 0; //falgのリセット
		      /***********************************************/
		  }
//		  in.close();  // ファイルを閉じる
//	  } catch (IOException e) {
//      e.printStackTrace(); // 例外が発生した所までのスタックトレースを表示
//	  }

	  System.out.println(num+1 + "つ目の結果は"+vars.toString()+"\n");
      } //←引数2つループ用
      // 最後まで O.K. なら成功
      System.out.println("最終結果は"+vars.toString());

      /*
       * HashMap<String,List<String>> vars これを,
       * List<String>に変更しないといけない
       */
      for(int i = 0; i < KeyList.size(); i++) {
    	  for(int j = 0; j < (vars.get(KeyList.get(i))).size(); j++) {
    		  //System.out.println("vars.get("+i+").get("+j+") = " + vars.get(KeyList.get(i)).get(j));
    		  lastMessage.add(vars.get(KeyList.get(i)).get(j));
    	  }
    	  lastMessage.add(",");
      }

      System.out.println("lastMessage = " + lastMessage.toString());
      //HashMapのメソッド↓
      //https://docs.oracle.com/javase/jp/8/docs/api/java/util/HashMap.html
      //System.out.println("Key:?xに該当する値:Value = " + vars.get("?x"));
      //System.out.println("Key:?yに該当する値:Value = " + vars.get("?y"));

      return lastMessage;
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
	  /* (注意)
	   * すでに?x = Taroという制約あがある状態で,さらに?x = Jiroを付け加えないといけない.
	   * うやむやにやっても,かぶっている候補を増やしてしまうだけである.
	   */
	  //すでにあるKeyに対して...
      if(vars.containsKey(vartoken)){	//HashMapにvartokenというキー(Not値)が存在するかどうか
    	  System.out.println("varslist.size() = " + varslist.size());

    	  //まだvarslistには入ってないけど,Maticng成功した場合,
    	  if(varslist.size() == 0) {
      		  ValueList.add(token);
      		  keyList.add(vartoken);
    	  }

    	  //普段はこっちだよね
    	  else {
    	  for(int i = 0; i < varslist.size(); i++) {
    		  //すでに登録されている関係なら...
    		  //System.out.println("ここは,"+(vars.get(vartoken)).get(0));
    		  if(token.equals((vars.get(vartoken)).get(i))){	//"HashMapにKeyであるvartokenに対応するValue値" と "token"が同じだったら...
    			  System.out.println("token.equals((vars.get(vartoken)).get(" + i + ")) = " + token.equals((vars.get(vartoken)).get(i)));
              	  //return true;
          	  } else { //別の値(TaroじゃなくてJiro)が来たら,加えます.
        	  	  //Keyも含めて再登録しないと...
          		  //KeyList.add(vartoken);
          		  ValueList.add(token);
          		  keyList.add(vartoken);
        	  	  //return true;
         	  }
    	  }
    	  }
    	  return true;
      }

      //初めてのKeyに関して...
      else {
          replaceBuffer(vartoken,token);
          //if(vars.containsValue(vartoken)){	//HashMapにvartokenという値(Notキー)が存在するかどうか
          if(varslist.contains(vartoken)) {		//HashMapの値リスト:varslistに含まれているかどうかで見る
              replaceBindings(vartoken,token);
          }
          //ここではまだしません.
          //vars.put(vartoken,token);		//キーと値を対応づけ
          //格納しておくだけ
          if(!KeyList.contains(vartoken)) {   //1回だけでっせ！
        	  KeyList.add(vartoken);
          }
          ValueList.add(token);
          keyList.add(vartoken);
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
  //  	  vars.put(key,postString);
      }
  }
  }


  boolean var(String str1){
  // 先頭が ? なら変数
	  return str1.startsWith("?");
  }

}