import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class UnifyGUI extends JFrame implements ActionListener {
    View view;
    Presenter presenter;
    JTextField text;
    JButton[] buttons;
    List<TextModel> textList;
    DefaultListModel lModel;
    JList listPanel;
    List<TextModel> searchList;
    DefaultListModel sModel;
    JList searchPanel;

    public static void main(String args[]) {
        UnifyGUI frame = new UnifyGUI("パターン照合");
        frame.setVisible(true);
    }

    UnifyGUI(String title) {

        setTitle(title);
        int appWidth = 900;
        int appHeight = 500;
        setBounds(100, 100, appWidth, appHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new myListener());
        setLayout(new FlowLayout());  // 既定はBorderLayout．設定しないとmainPanelがmaximumSizeに関わらず画面いっぱいに表示される．
        
        JPanel mainPanel = new JPanel();
        int mainWidth = 300;
        int mainHeight = 400;
        mainPanel.setMaximumSize(new Dimension(mainWidth, mainHeight));
        mainPanel.setPreferredSize(new Dimension(mainWidth, mainHeight));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel inPanel = genInputPanel();
        int inWidth = 300;
        int inHeight = 70;
        inPanel.setMaximumSize(new Dimension(inWidth, inHeight));
        inPanel.setPreferredSize(new Dimension(inWidth, inHeight));
        
        sModel = new DefaultListModel();
        searchPanel = new JList(sModel);
        JScrollPane searchSp = new JScrollPane();
        searchSp.getViewport().setView(listPanel);
        // searchSp.setMaximumSize(new Dimension(inWidth, mainHeight - inHeight));
        // searchSp.setPreferredSize(new Dimension(inWidth, mainHeight - inHeight));
        
        lModel = new DefaultListModel();
        listPanel = new JList(lModel);
        JScrollPane listSp = new JScrollPane();
        listSp.getViewport().setView(listPanel);
        // listSp.setMaximumSize(new Dimension(appWidth - mainWidth, appHeight));
        // inPanel.setPreferredSize(new Dimension(appWidth - mainWidth, appHeight));

        mainPanel.add(inPanel);
        mainPanel.add(searchSp);

        Container contentPane = getContentPane();
        contentPane.add(mainPanel);
        contentPane.add(listSp);
    }

    JPanel genInputPanel() {
        text = new JTextField(20);
        JPanel p = new JPanel();

        JPanel btnPanel = new JPanel();
        buttons = new JButton[3];
        buttons[0] = new JButton("検索");
        buttons[1] = new JButton("追加");
        buttons[2] = new JButton("削除");
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(this);
            btnPanel.add(buttons[i]);
        }
        p.add(text);
        p.add(btnPanel);
        return p;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String arg = text.getText();

        if (cmd.equals("検索")) {
            presenter.searchData(arg);
            searchList = view.getList();
            for (TextModel text : searchList) {
                sModel.addElement(text);
            }

        } else if (cmd.equals("追加")) {
            presenter.addData(arg);
            presenter.fetchData();
            textList = view.getList();
            for (TextModel text : textList) {
                lModel.addElement(text);
            }

        } else if (cmd.equals("削除")) {
            if (!listPanel.isSelectionEmpty()) {
                int index = listPanel.getSelectedIndex();
                TextModel val = (TextModel)listPanel.getSelectedValue();
                presenter.deleteData(val.getUUID());
                lModel.remove(index);
            } else {
                System.out.println("削除失敗（未選択のため）");
            }
        }
    }

    public class myListener extends WindowAdapter {
        public void windowOpened(WindowEvent e) {
            view = new View();
            presenter = new Presenter(view);
            presenter.start();
            presenter.fetchData();
            textList = view.getList();
            for (TextModel text : textList) {
                lModel.addElement(text);
            }
        }

        public void windowClosing(WindowEvent e) {
            presenter.finish();
        }
    }
}

class View implements ViewInterface {
    List<TextModel> list;

    View() {
        list = new ArrayList<>();
    }

    List<TextModel> getList() {
        return list;
    }

    // データベース初期化完了メソッド
    public void successStart() {
        System.out.println("Successfully started");
    }

    // テキストファイル記録完了メソッド
    public void successFinish() {
        System.out.println("Successfully finished");
    }

    // データ追加完了メソッド
    public void successAddData() {
        System.out.println("Successfully added data");
    }

    // 検索結果反映メソッド
    public void showSearchResult(List<TextModel> resultList) {
        list = resultList;
        System.out.println("検索結果を取得");
    }

    // データ削除メソッド
    public void successDeleteData() {
        System.out.println("Successfully deleted data");
    }

    // 一覧表示メソッド
    public void showResultList(List<TextModel> resultList) {
        list = resultList;
        System.out.println("一覧を取得");
    }

    // 例外処理表示メソッド
    public void showError(String errorText) {
        System.out.println("errorText");
    }

    // データ無し表示メソッド
    public void showNoData() {
        System.out.println("No matching data");
    }
}