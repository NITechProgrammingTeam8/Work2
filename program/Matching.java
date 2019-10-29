/***
Matching Program written



変数:前に？をつける．

Examle:
% Matching "Takayuki" "Takayuki"
true

% Matching "Takayuki" "Takoyuki"
false

% Matching "?x am Takayuki" "I am Takayuki"
?x = I .

% Matching "?x is ?x" "a is b"
false

% Matching "?x is ?x" "a is a"
?x = a .


Mating は，パターン表現と通常表現とを比較して，通常表現が
パターン表現の例であるかどうかを調べる．
Unify は，ユニフィケーション照合アルゴリズムを実現し，
パターン表現を比較して矛盾のない代入によって同一と判断
できるかどうかを調べる．

***/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
* マッチングのプログラム
*
*/
class Matching {

  public static void main(String arg[]){
	if(arg.length != 1){
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

	//System.out.println((new Matcher()).matching(arg[0],arg[1]));	//ここでarg[0]引数1, arg[1]に引数2
  }
}

/**
* 実際にマッチングを行うクラス
*
*/
	class Matcher {
	  StringTokenizer st1;
	  StringTokenizer st2;
	  HashMap<String,String> vars;
	  ArrayList<String> KeyList;  //Keyをとり,HashMapのValueを取るためのリスト
	  int flag;	//ミスフラグ

	  Matcher(){
		vars = new HashMap<String,String>();
		KeyList = new ArrayList<String>();
		flag = 0;
	  }

/***
	  public boolean matching(String string1,String string2,HashMap<String,String> bindings){
		this.vars = bindings;
		if(matching(string1,string2)){
		    return true;
		} else {
		    return false;
		}
	  }

***/

	  public String matching(String string1,String string2){
		//System.out.println("tagetData = " + string1);
		//System.out.println("resultList = " + string2);

		// 同じなら成功
		if(string1.equals(string2)) return " ";

		// 各々トークンに分ける
		st1 = new StringTokenizer(string1);
		st2 = new StringTokenizer(string2);

		//System.out.println("トークンの数" + st2.countTokens());

		// 数が異なったら失敗
		if(st1.countTokens() != st2.countTokens()) return " ";

		// 定数同士
		for(int i = 0 ; i < st1.countTokens();){
		    if(!tokenMatching(st1.nextToken(),st2.nextToken())){
			// トークンが一つでもマッチングに失敗したら失敗
			return " ";
		    }
		}


		// 最後まで O.K. なら成功
		//System.out.println("成功！" + vars.get(KeyList.get(0)));
		return vars.get(KeyList.get(0));
	  }

	  public boolean tokenMatching(String token1,String token2){
		//System.out.println(token1+"<->"+token2);
		if(token1.equals(token2)) return true;
		if( var(token1) && !var(token2)) return varMatching(token1,token2);
		if(!var(token1) &&  var(token2)) return varMatching(token2,token1);
		return false;
	  }

	  public boolean varMatching(String vartoken,String token){
		if(vars.containsKey(vartoken)){
		    if(token.equals(vars.get(vartoken))){
		    	//System.out.println(token);
			return true;
		    }
		    else {
			return false;
		    }
		} else {
		    vars.put(vartoken,token);
		    KeyList.add(vartoken);
		    //System.out.println(vars.get(vartoken));
		}
		return true;
	  }

	  public boolean var(String str1){
		// 先頭が ? なら変数
		return str1.startsWith("?");
	  }

	}