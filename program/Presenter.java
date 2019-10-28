
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// DaoやTextDAOなどのModelクラスとViewクラスの接合部

class Presenter {

    private Dao dao = new Dao();
    private TextCon textCon = new TextCon();
    private Unify unify = new Unify();
    //private Matcher matching = new Matcher();
    private ViewInterface view;
    Matcher matching2;

    public Presenter(ViewInterface view) {
        this.view = view;
        matching2 = new Matcher();
    }

    // GUI起動時にTextDAOに対してテキストファイルからデータベースへの読み込みを指示する
    // あおしゅー：起動時に必ずこのメソッドを呼んでください
    // のりこさん：テキストファイルからデータベースへの読み込みを行うメソッドを用意してください
    public void start() {
        try {
            textCon.readTextFile();
            view.successStart();
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
            view.successFinish();
        } catch(FileNotFoundException e) {
            view.showError(e.toString());
        }
    }

    // 新規データをデータベースに書き込むように指示する
    // あおしゅー：データ追加のアクションを受け取ったとき、このメソッドを呼んでください
    public void addData(String newData) {
        try {
            dao.addData(newData);
            view.successAddData();
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
        List<String> ValueList = new ArrayList<String>(); //【DBからlineしか取らない

        try {
            resultList = dao.FetchData();
            String[] target = targetData.split(",", 0);
            if(resultList.size() == 0) {
                view.showNoData();
            } else if (target.length == 1) {
            	System.out.println("targetData1 = " + targetData);
            	for (int i = 0; i < resultList.size(); i++) {
            		//System.out.println("☆" + (new Matcher()).matching(targetData,resultList.get(i).getTEXT()));
            		String ans = (new Matcher()).matching(targetData,resultList.get(i).getTEXT());
            		if (ans != " ") {
            			ValueList.add(ans);
            		}
            	}
            	/*
            	for (TextModel textModel : resultList) {
            		System.out.println("☆" + matching.matching(targetData,textModel.getTEXT()));
            		if (matching.matching(targetData,textModel.getTEXT()) != " ") {
            			ValueList.add(textModel.getTEXT());
            		}
            	}
            	*/

            } else {
            	// Unifyメソッドをここで呼ぶ
            	for (TextModel text : resultList) {
                    System.out.println("???" + text);
                    ValueList.add(matching2.matching(targetData, text.getTEXT()));
                    System.out.println(matching2.matching(targetData, text.getTEXT()));
               }
            }

            System.out.println("size = " + ValueList.size());
            for (int i = 0; i < ValueList.size(); i++) {
            	System.out.println(ValueList.get(i));
            }
            view.showSearchResult(ValueList);

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
            view.successDeleteData();
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
                view.showResultList(resultList);
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