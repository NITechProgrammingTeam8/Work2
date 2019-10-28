/*
 * DB検索が行えるようにする．
 * TextDAOにアクセス
 */


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TextCon{

	public void readTextFile() throws FileNotFoundException{
		String empty = "";

		//textファイル処理
		try {

			// tableの作成【DB】
			TextDAO.createTab();

			// ファイル読み込みに失敗した時の例外処理のためのtry-catch構文
			String fileName = "dataset_example.txt"; // ファイル名指定
			//String fileName = "rugby.txt"; // ファイル名指定

			// 文字コードUTF-8を指定してBufferedReaderオブジェクトを作る
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

			// 変数lineに1行ずつ読み込むfor文
			for (String line = in.readLine();line != null; line = in.readLine()) {

				if (!line.equals(empty)) {
					// 更新【DB】
					TextDAO.insertTextTab(line);
				}

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


	public void writeTextFile() throws FileNotFoundException{

		try {    // ファイル読み込みに失敗した時の例外処理のためのtry-catch構文
			ArrayList<String> TextData = new ArrayList<String>();

			String fileName = "dataset_example.txt"; // ファイル名指定
			//String fileName = "rugby.txt"; // ファイル名指定

			// 文字コードUTF-8を指定してPrintWriterオブジェクトを作る。
			PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));

			// 取得【DB】
			TextData = TextDAO.getDBData();
			TextDAO.conCom();

			for (int i = 0 ; i < TextData.size() ; i++){
				out.println(TextData.get(i));
			}

			out.close();        // ファイルを閉じる
		} catch (IOException e) {
			e.printStackTrace(); // 例外が発生した所までのスタックトレースを表示
		}

		//削除【DB】
		TextDAO.deleteDB();

		//【DB】
		TextDAO.closeConn();
	}
}
