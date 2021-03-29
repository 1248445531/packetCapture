package packetCapture;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * 流式布局
 */
public class JpCapFrame extends JFrame {
    private static DefaultTableModel model;
    private static JTextField filterField;
    private JTextArea showArea;
    private JButton startBtn;
    private JButton checkBtn;
    private JButton exitBtn;
    private JButton clearBtn;

    public JpCapFrame() {
        super();
        initGUI();
    }

    public static DefaultTableModel getModel() {
        return model;
    }

    public JTextArea getShowArea() {
        return showArea;
    }

    public JButton getStartBtn() {
        return startBtn;
    }

    public JButton getCheckBtn() {
        return checkBtn;
    }

    public JButton getExitBtn() {
        return exitBtn;
    }

    public JButton getClearBtn() {
        return clearBtn;
    }

    public static JTextField getFilterField() {
        return filterField;
    }

    private void initGUI() {
        Font font1 = new Font("宋体", Font.BOLD, 15);
        Font font4 = new Font("宋体", Font.BOLD, 14);
        Font font2 = new Font("宋体", Font.PLAIN, 16);
        Font font3 = new Font("微软雅黑", Font.PLAIN, 16);

        //界面
        setSize(1550, 1000);
        setVisible(true);
        setTitle("Captor");
        Container container = this.getContentPane();

        //顶部
        JPanel pane = new JPanel();
        pane.setBounds(0, 0, 775, 150);
        pane.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pane.setPreferredSize(new Dimension(775, 27));

        checkBtn = new JButton("查看网卡信息");
        checkBtn.setFont(font4);
        checkBtn.setBounds(0, 0, 50, 0);
        pane.add(checkBtn);

        startBtn = new JButton("开始");
        startBtn.setFont(font4);
        startBtn.setBounds(0, 0, 50, 0);
        pane.add(startBtn);

        clearBtn = new JButton("清空");
        clearBtn.setFont(font4);
        clearBtn.setBounds(0, 0, 50, 0);
        pane.add(clearBtn);

        exitBtn = new JButton("退出");
        exitBtn.setFont(font4);
        exitBtn.setBounds(0, 0, 50, 0);
        pane.add(exitBtn);

        JPanel panelTest = new JPanel();
        panelTest.setBounds(775, 0, 775, 150);
        panelTest.setPreferredSize(new Dimension(775, 27));
        panelTest.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 0));

        JLabel filter = new JLabel("Filter:");
        filter.setFont(font1);
        filter.setBounds(0, 0, 500, 0);
        filterField = new JTextField(50);
        filterField.setBounds(200, 0, 500, 0);
        panelTest.add(filter);
        panelTest.add(filterField);

        //中部主体内容显示区
        String[] name = {"No.", "Time", "Source", "Destination", "Protocol", "Length", "Info"};
        //model = new DefaultTableModel();
        //model.setColumnIdentifiers(name);
        JTable table = new JTable(model);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(font1);
        table.setFont(font2);
        table.setRowHeight(20);
        model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(name);
        table.setEnabled(false);
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(0, 300, 1550, 600);

        //底部
        JPanel pane2 = new JPanel();
        pane2.setLayout(new BorderLayout());
        pane2.setPreferredSize(new Dimension(1550, 300));

        showArea = new JTextArea(5, 5);
        //showArea.setBounds(0,0,1200,300);
        //showArea.setText("Test");
        showArea.setEditable(false);
        showArea.setLineWrap(false);
        showArea.setFont(font3);
        //showArea.setBackground(Color.GRAY);
        //pane2.add(showArea);
        pane2.setSize(10, 10);
        pane2.setBounds(0, 0, 1, 1);
        //给textArea添加滚动条
        JScrollPane scrollPane = new JScrollPane(showArea);
        scrollPane.setBounds(0, 0, 1, 1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        pane2.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(showArea);

        container.add(jScrollPane, BorderLayout.CENTER);
        container.add(pane, BorderLayout.NORTH);
        container.add(panelTest, BorderLayout.NORTH);
        container.add(pane2, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
