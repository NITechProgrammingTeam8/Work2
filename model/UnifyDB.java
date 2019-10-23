
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

/*
 * DB検索が行えるようにする．
 *
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class UnifyDB extends TextDAO{

	public static void main(String arg[]){


		makeTextDB("data.txt");
		System.out.println("とりあえずエラーなく終わりました");
		
		// 検索

	}

	public static void makeTextDB(String fileName) {
		StringTokenizer st;
		String buffer[];
		//String conbuffer = "";
		StringBuffer buf = new StringBuffer();
		String verb[] = new String[10];
		int j = 0;
		int newverb = 1; // 動詞が新出かどうか
		int count[] = new int[10]; // 各動詞が何回書かれているか
		int nowverb = -1; // 現在の動詞

		//textファイル処理
		try {

			// ファイル読み込みに失敗した時の例外処理のためのtry-catch構文
			//String fileName = "data.txt"; // ファイル名指定

			// 文字コードUTF-8を指定してBufferedReaderオブジェクトを作る
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

			// 変数lineに1行ずつ読み込むfor文
			for (String line = in.readLine();line != null; line = in.readLine()) {
				//System.out.println(line);  // 1行表示

				st = new StringTokenizer(line);

				int length = st.countTokens();
				buffer = new String[length];
				for(int i = 0 ; i < length; i++){
					buffer[i] = st.nextToken();
					if (i == 1) {
						for (int k = 0; k < j; k++) {
							if (buffer[i].equals(verb[k])) {
								newverb = 0;
								count[k]++;
								nowverb = k;
							}
						}
						if (newverb == 1) {
							// 新しい動詞を発見したとき
							verb[j] = buffer[i];
							//System.out.println("見つかった動詞" + buffer[i]);
							count[j]++;
							nowverb = j;
							j++;

							String pro = buffer[i] + "s";

							// tableの作成【DB】
							TextDAO.createTab(pro);
						}

					}
				}

				//System.out.println("length = " + length);
				if (length > 3) {
					for(int i = 2; i < length; i++) {
						buf.append(buffer[i]);
						if (i != length - 1) {
							buf.append(" ");
						}
					}

					String pro = buffer[1] + "s";
					// 更新【DB】
					TextDAO.insertTextTab(pro, count[nowverb], buffer[0], buf.toString());
					buf.setLength(0);
				} else if (nowverb != -1) {

					String pro = buffer[1] + "s";
					// 更新【DB】
					TextDAO.insertTextTab(pro, count[nowverb], buffer[0], buffer[2]);
					
				}

				// 値の初期化
				newverb = 1;
				nowverb = -1;

				//【DB】
				TextDAO.conCom();

			}
			in.close();  // ファイルを閉じる
		} catch (IOException e) {
			e.printStackTrace(); // 例外が発生した所までのスタックトレースを表示
		}
		//【DB】
		TextDAO.conCom();
		TextDAO.closeConn();

	}

}

/*
class Unifier {
  StringTokenizer st1;
  String buffer1[];
  StringTokenizer st2;
  String buffer2[];
  HashMap<String,String> vars;

  Unifier(){
      vars = new HashMap<String,String>();
  }

  public boolean unify(String string1,String string2,HashMap<String,String> bindings){
      this.vars = bindings;
      return unify(string1,string2);
  }

  public boolean unify(String string1,String string2){
      System.out.println(string1);
      System.out.println(string2);

      // 同じなら成功
      if(string1.equals(string2)) return true;

      // 各々トークンに分ける
      st1 = new StringTokenizer(string1);
      st2 = new StringTokenizer(string2);

      for (int i = 0 ; i < st1.countTokens(); i++) {
    	  System.out.println("☆" + st1 + "\n");
    	  st1.nextToken();
    	  //トークンはまだ配列ではない
      }

      // 数が異なったら失敗
      if(st1.countTokens() != st2.countTokens()) return false;

      // 定数同士
      int length = st1.countTokens();
      buffer1 = new String[length];
      buffer2 = new String[length];
      for(int i = 0 ; i < length; i++){
          buffer1[i] = st1.nextToken();
          buffer2[i] = st2.nextToken();
      }
      for(int i = 0 ; i < length ; i++){
          if(!tokenMatching(buffer1[i],buffer2[i])){
              return false;
          }
      }


      // 最後まで O.K. なら成功
      System.out.println(vars.toString());
      return true;
  }

  boolean tokenMatching(String token1,String token2){
      if(token1.equals(token2)) return true;
      if( var(token1) && !var(token2)) return varMatching(token1,token2);
      if(!var(token1) &&  var(token2)) return varMatching(token2,token1);
      if( var(token1) &&  var(token2)) return varMatching(token1,token2);
      return false;
  }

  boolean varMatching(String vartoken,String token){
      if(vars.containsKey(vartoken)){
          if(token.equals(vars.get(vartoken))){
              return true;
          } else {
              return false;
          }
      } else {
          replaceBuffer(vartoken,token);
          if(vars.containsValue(vartoken)){
              replaceBindings(vartoken,token);
          }
          vars.put(vartoken,token);
      }
      return true;
  }

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

  void replaceBindings(String preString,String postString){
  Iterator<String> keys;
  for(keys = vars.keySet().iterator(); keys.hasNext();){
      String key = (String)keys.next();
      if(preString.equals(vars.get(key))){
      vars.put(key,postString);
      }
  }
  }


  boolean var(String str1){
  // 先頭が ? なら変数
  return str1.startsWith("?");
  }

}
*/