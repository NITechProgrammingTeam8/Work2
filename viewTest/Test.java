
/*
 * DB関連のプログラムのみを動作確認するためのプログラム
 * 具体的には、
 * Presenter.java
 * Dao.java
 * TextCon.java
 * TextDAO.java
 * の動作確認用です
 *
 * 動作させるためPresenterクラスにコンストラクタを追加しました
 * Listの表示のためにTextModel.javaにゲッタを追加しました
 */

class Test {
	public static void main(String arg[]){

		Presenter presenter = new Presenter(new View());
		presenter.start();

		presenter.addData("Mike is a boy");
		presenter.deleteData(1);
		presenter.addData("Mike loves Hanako");
		presenter.deleteData(5);
		presenter.addData("Mike has a hobby of playing tennis");
		presenter.deleteData(20);
		presenter.addData("Mike is a boy");

		presenter.searchData("Taro studies ?x");

		presenter.fetchData();
		
		presenter.finish();

	}

}