import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UnifyGUI extends JFrame implements ActionListener {
    View view;
    Presenter presenter;
    JTextField text;
    JButton[] buttons;
    JButton delButton;
    List<TextModel> textList;
    DefaultListModel lModel;
    JList listPanel;
    List<String> searchList;
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
        // setLayout(new FlowLayout()); //
        // 既定はBorderLayout．設定しないとmainPanelがmaximumSizeに関わらず画面いっぱいに表示される．

        JPanel mainPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        mainPanel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        text = new JTextField(20);

        JPanel btnPanel = new JPanel();
        buttons = new JButton[2];
        buttons[0] = new JButton("検索");
        buttons[1] = new JButton("追加");
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(this);
            btnPanel.add(buttons[i]);
        }

        sModel = new DefaultListModel();
        searchPanel = new JList(sModel);
        JScrollPane searchSp = new JScrollPane();
        searchSp.getViewport().setView(searchPanel);
        searchSp.setPreferredSize(new Dimension(200, 300));

        gbc.gridx = 0;
        gbc.gridy = 0;
        layout.setConstraints(text, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        layout.setConstraints(btnPanel, gbc);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        layout.setConstraints(searchSp, gbc);

        delButton = new JButton("削除");
        delButton.addActionListener(this);

        lModel = new DefaultListModel();
        listPanel = new JList(lModel);
        JScrollPane listSp = new JScrollPane();
        listSp.getViewport().setView(listPanel);
        listSp.setPreferredSize(new Dimension(400, 350));

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        layout.setConstraints(listSp, gbc);

        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        layout.setConstraints(delButton, gbc);

        mainPanel.add(text);
        mainPanel.add(btnPanel);
        mainPanel.add(searchSp);
        mainPanel.add(listSp);
        mainPanel.add(delButton);

        Container contentPane = getContentPane();
        contentPane.add(mainPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String arg = text.getText();

        if (cmd.equals("検索")) {
            presenter.searchData(arg);
            searchList = view.getSr();
            for (String text : searchList) {
                sModel.addElement(text);
            }

        } else if (cmd.equals("追加")) {
            presenter.addData(arg);
            lModel.clear();
            presenter.fetchData();
            textList = view.getRl();
            for (TextModel text : textList) {
                lModel.addElement(text);
            }

        } else if (cmd.equals("削除")) {
            if (!listPanel.isSelectionEmpty()) {
                int index = listPanel.getSelectedIndex();
                TextModel val = (TextModel) listPanel.getSelectedValue();
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
            textList = view.getRl();
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
    List<String> sr;
    List<TextModel> rl;

    View() {
        sr = new ArrayList<>();
        rl = new ArrayList<>();
    }

    List<String> getSr() {
        return sr;
    }

    List<TextModel> getRl() {
        return rl;
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
    public void showSearchResult(List<String> resultList) {
        sr = resultList;
        System.out.println("検索結果を取得");
    }

    // データ削除メソッド
    public void successDeleteData() {
        System.out.println("Successfully deleted data");
    }

    // 一覧表示メソッド
    public void showResultList(List<TextModel> resultList) {
        rl = resultList;
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