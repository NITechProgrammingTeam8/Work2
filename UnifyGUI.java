import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UnifyGUI extends JFrame implements ActionListener {
    JTextField text;
    JButton[] buttons;

    public static void main(String args[]) {
        UnifyGUI frame = new UnifyGUI("パターン照合");
        frame.setVisible(true);
    }

    UnifyGUI(String title) {
        setTitle(title);
        int appWidth = 400;
        int appHeight = 300;
        setBounds(100, 100, appWidth, appHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(appWidth, appHeight));

        JPanel inPanel = genInputPanel();
        int inHeight = 70;
        inPanel.setPreferredSize(new Dimension(appWidth, inHeight));
        JTextArea outPanel = new JTextArea(10, 20);

        mainPanel.add(inPanel);
        mainPanel.add(outPanel);

        Container contentPane = getContentPane();
        contentPane.add(mainPanel);
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
        } else if (cmd.equals("追加")) {
        } else if (cmd.equals("削除")) {
        }
    }
}