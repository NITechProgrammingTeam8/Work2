import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

// DaoやTextDAOなどのModelクラスとViewクラスの接合部

class Presenter {

	// コンストラクタ
	Presenter() {
	}

    private Dao dao = new Dao();
    private TextCon textCon = new TextCon();
    private Unify unify = new Unify();
    private ViewInterface view;

    public Presenter(ViewInterface view) {
        this.view = view;
    }

    // GUI起動時にTextDAOに対してテキストファイルからデータベースへの読み込みを指示する
    // あおしゅー：起動時に必ずこのメソッドを呼んでください
    // のりこさん：テキストファイルからデータベースへの読み込みを行うメソッドを用意してください
    public void start() {
        try {
            textCon.readTextFile();
            //view.successStart();
        } catch(FileNotFoundException e) {
            view.showError(e.toString());
        }
    }

    // GUI終了時にTextDAOに対してデータベースからテキストファイルへの書き込みを指示する
    // あおしゅー：終了時に必ずこのメソッドを呼んでください
    // のりこさん：データベースからテキストファイルへの書き込みを行うメソッドを用意してください
    public void finish() {
        try {
            textCon.writeTextFile();
            //view.successFinish();
        } catch(FileNotFoundException e) {
            view.showError(e.toString());
        }
    }

    // 新規データをデータベースに書き込むように指示する
    // あおしゅー：データ追加のアクションを受け取ったとき、このメソッドを呼んでください
    public void addData(String newData) {
        try {
            dao.addData(newData);
            //view.successAddData();
        } catch(SQLException e) {
            view.showError(e.toString());
        } catch(ClassNotFoundException e) {
            view.showError(e.toString());
        } catch (Exception e) {
			view.showError(e.toString());
		} finally {
            try {
				dao.conn.close();
			} catch (SQLException e) {
				view.showError(e.toString());
			}
        }
    }

    // 検索データをデータベースから取得するように指示する
    // あおしゅー：データ検索のアクションを受け取ったとき、このメソッドを呼んでください
    public void searchData(String targetData) {
        List<TextModel> resultList;
        //List<String> resultList; //【DBからlineしか取得しない場合】
        try {
            resultList = dao.FetchData();

            /*
            // 取得したリストの表示
            for (TextModel key : resultList) {
                System.out.println(key.getUUID() + " => " + key.getTEXT());
            }
			/*

            /*
            // 取得したリストの表示【DBからlineしか取得しない場合】
            for (int i = 0 ; i < resultList.size() ; i++){
				System.out.println(resultList.get(i));
			}
			*/

            // Unifyメソッドをここで呼ぶ
            if(resultList.size() == 0) {
                view.showNoData();
            } else {
                //view.showSearchResult(resultList);
                //view.showSearchResult(); // ViewInterfaceとの整合性のため
            }
        } catch(SQLException e) {
            view.showError(e.toString());
        } catch(ClassNotFoundException e) {
            view.showError(e.toString());
        } catch (Exception e) {
        	view.showError(e.toString());
		} finally {
            try {
				dao.conn.close();
			} catch (SQLException e) {
				view.showError(e.toString());
			}
        }
    }

    // 削除データをデータベースに削除するように支持する
    // あおしゅー：データ削除のアクションを受け取ったとき、このメソッドを呼んでください
    public void deleteData(int targetData) {
        try {
            dao.deleteData(targetData);
            //view.successDeleteData();
        } catch(SQLException e) {
            view.showError(e.toString());
        } catch(ClassNotFoundException e) {
            view.showError(e.toString());
        } catch (Exception e) {
        	view.showError(e.toString());
		} finally {
            try {
				dao.conn.close();
			} catch (SQLException e) {
				view.showError(e.toString());
			}
        }
    }

    public void fetchData() {
        List<TextModel> resultList;
    	//List<String> resultList; // 【DBからlineしか取得しない場合】
        try {
            resultList = dao.FetchData();
            if(resultList.size() == 0) {
                view.showNoData();
            } else {
                //view.showResultList(resultList);
            }
        } catch(SQLException e) {
            view.showError(e.toString());
        } catch(ClassNotFoundException e) {
            view.showError(e.toString());
        } catch (Exception e) {
        	view.showError(e.toString());
		} finally {
            try {
				dao.conn.close();
			} catch (SQLException e) {
				view.showError(e.toString());
			}
        }
    }
}