package com.seu.IDE;
/*
 *以下工程为MinisysSoC2018
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;

public class Frames extends JFrame implements ActionListener {//主类是继承了JFrame
    String[][] menuNameMnemonics = {{"File", "f"}, {"Edit", "e"},
            {"Source", "s"}, {"Refactor", "t"}, {"Navigate", "n"},
            {"Search", "a"}, {"Project", "p"}, {"Run", "r"}, {"Window", "w"}, {"Help", "h"}};//此处为Menu题头
    private File file = null;
    JMenuBar menuBar;
    JPanel mainpanel;//这是主的界面（每次new都是生成一个新界面来添加组件）
    //JPanel jpforEdit;
    Font textfont = new Font("宋体", Font.BOLD, 30);
    ImageIcon icon_close = new ImageIcon("C:/Users/qqq/Desktop/fgo/close2.png");//这个是图片要重新设置位置
    Box box = null;//用于存储打开的page的切换button
    ArrayList<JButton> listforbuttons = new ArrayList();//切换按钮的数组
    ArrayList<JButton> listforcloses = new ArrayList();//关闭按钮的数组
    ArrayList<MyTextPane> listforEdits = new ArrayList();//输入框数组
    ArrayList<JScrollPane> listforRolls = new ArrayList();//输入框滚动条数组
    ArrayList<MyTextPane> listforOutputs = new ArrayList();//输出框数组
    ArrayList<JScrollPane> listforOutputRolls = new ArrayList();//输出框滚动条数组

    int on_window = -1;//当前界面在数组中的下标

    public Frames() {
        /*
         * 以下为设计一个menu的部分
         */
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        for (String[] menuNameMnemonic : menuNameMnemonics) {
            menuBar.add(createMenu(menuNameMnemonic[0], menuNameMnemonic[1]));
        }
        // 为File添加子菜单和菜单项

        JMenu fileMenu = getMenu("File");
        fileMenu.add(createMenuNamedNew());

        JMenuItem open_file = createMenuItem("Open File", null, null, null);
        fileMenu.add(open_file);
        open_file.addActionListener(this);

        fileMenu.addSeparator();

        fileMenu.add(createMenuItem("Close", "c", null, KeyStroke.getKeyStroke('W', InputEvent.CTRL_MASK)));
        fileMenu.add(createMenuItem("Close All", "l", null, KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK)));
        fileMenu.addSeparator();
        JMenuItem save_file = createMenuItem("Save", "s", null, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        fileMenu.add(save_file);
        save_file.addActionListener(this);

        JMenuItem save_as = createMenuItem("Save As", "a", null, null);
        fileMenu.add(save_as);
        save_as.addActionListener(this);

        fileMenu.add(createMenuItem("Save All", "e", null, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK)));
        // fileMenu.add(createMenuItem("Revert", "t", null, null));
        //fileMenu.addSeparator();

        // JMenuItem menuNamedMove = createMenuItem("Move", "v", null, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        //  menuNamedMove.setEnabled(false);
        //fileMenu.add(menuNamedMove);

        // JMenuItem menuNamedRename = createMenuItem("Rename", "m", null, KeyStroke.getKeyStroke("F2"));
        // menuNamedRename.setEnabled(false);
        //fileMenu.add(menuNamedRename);


        // fileMenu.add(createMenuItem("Refresh", "f", null, KeyStroke.getKeyStroke("F5")));

        // JMenu menuNamedConvert = createMenuNamedConvert();
        // fileMenu.add(menuNamedConvert);//���New�˵�

        //  fileMenu.addSeparator();
        //fileMenu.add(createMenuItem("Print", "p", null, KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK)));
        //fileMenu.addSeparator();

        // JMenu menuNamedSwitch = createMenu("Switch WorkSpsace", "w");
        // menuNamedSwitch.add(createMenuItem("Other", "o", null, null));
        //fileMenu.add(menuNamedSwitch);

        //fileMenu.add(createMenuItem("Restart", null, null, null));
        //fileMenu.addSeparator();
        //fileMenu.add(createMenuItem("Import", "i", null, null));
        //fileMenu.add(createMenuItem("Export", "o", null, null));
        //fileMenu.addSeparator();
        //fileMenu.add(createMenuItem("Properties", "r", null, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.ALT_MASK)));
        //fileMenu.addSeparator();
        JMenuItem menuExit = createMenuItem("Exit", "x", null, null);
        fileMenu.add(menuExit);
        menuExit.addActionListener(this);

        JMenu runMenu = getMenu("Run");

        JMenuItem compile = createMenuItem("Compile", null, null, null);
        runMenu.add(compile);
        compile.addActionListener(this);

        runMenu.addSeparator();

        JMenuItem assemble = createMenuItem("Assemble", null, null, null);
        runMenu.add(assemble);
        compile.addActionListener(this);

        /*
         * 以下为mainpanel的设计
         */
        mainpanel = new JPanel();
        //jpforEdit.setLayout(new BorderLayout());

        /*
         * 以下为整个面板JFrame的整体设计
         */
        box = Box.createHorizontalBox();//横结构

        this.setTitle("MinisysSoC2018IDE");       // 标题
        this.setSize(getPreferredSize().width, 200);      // 大小
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);       // 关闭方式
        this.setLocationRelativeTo(null);
        Container container = this.getContentPane();         // 获取一个容器
        mainpanel.setBackground(Color.BLACK);
        mainpanel.setLayout(new BorderLayout());
        //mainpanel.add(Edit);

        mainpanel.updateUI();
        this.setContentPane(mainpanel);
        this.setVisible(true);        // 可视化

    }


    private JMenu createMenuNamedNew() {
        /*
         * * 创建名称为New的menu
         */
        JMenu menu = createMenu("New", "n");
        menu.add(createMenuItem("JavaProject", null, null, null));
        menu.add(createMenuItem("Project", null, null, null));
        JMenuItem Package = createMenuItem("Package", null, null, null);
        menu.add(Package);
        Package.addActionListener(this);
        JMenuItem Class = createMenuItem("Class", null, null, null);
        menu.add(Class);
        Class.addActionListener(this);
        JMenuItem Interface = createMenuItem("Interface", null, null, null);
        menu.add(Interface);
        Interface.addActionListener(this);
        // menu.add(createMenuItem("Enum", null, null, null));
        // menu.add(createMenuItem("Annotation", null, null, null));
        // menu.add(createMenuItem("SourceFolder", null, null, null));
        // menu.add(createMenuItem("JavaWorkingSet", null, null, null));
        //menu.add(createMenuItem("Folder", null, null, null));
        // menu.add(createMenuItem("File", null, null, null));
        //menu.add(createMenuItem("UntitledTextFile", null, null, null));
        //menu.add(createMenuItem("JunitTestCase", null, null, null));
        //menu.add(createMenuItem("Task", null, null, null));
        //menu.add(createMenuItem("Example", null, null, null));
        //menu.add(createMenuItem("Other", null, null, null));
        return menu;
    }

    private JMenu createMenuNamedConvert() {
        /*
         * 创建名称为Convert的menu
         */
        JMenu menu = createMenu("Convert Line Delimiters to", "v");
        menu.add(createMenuItem("Windows (CRLF, \\r\\n, 0D0A, xx)[default]", "w", null, null));
        menu.add(createMenuItem("Unix (LF, \\n, 0A, xx)", "n", null, null));
        return menu;
    }

    private JMenu getMenu(String menuName) {
        /*
         * 根据名称从menuBar中查找menu并返回
         */
        JMenu menu = null;
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            menu = menuBar.getMenu(i);
            if (menu.getText().equals(menuName))
                return menu;
        }
        return null;
    }

    private JMenu createMenu(String name, String mnemonic) {
        /*
         * 根据名称和快捷键创建menu并添加到menuBar
         */
        JMenu menu = new JMenu(name);
        if (mnemonic != null)
            menu.setMnemonic(mnemonic.toCharArray()[0]);
        return menu;
    }

    private JMenuItem createMenuItem(String name, String mnemonic, Icon icon, KeyStroke keyStroke) {
        /*
         * 根据名称和快捷键创建menu并添加到menuBar
         */
        JMenuItem menuItem = new JMenuItem(name, icon);
        if (mnemonic != null)
            menuItem.setMnemonic(mnemonic.toCharArray()[0]);
        if (keyStroke != null)
            menuItem.setAccelerator(keyStroke);
        return menuItem;
    }

    public String readToString(File file) {
        /*
         *		读文件的函数
         */
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(filecontent);
    }

    public void read() {
        /*
         *这里是Open file调用的用于打开文件并创建新界面的函数
         */

        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(jfc);
        file = jfc.getSelectedFile();

        if (file != null) {


            String str = readToString(file);

            if (listforEdits.size() > 0) {
                listforRolls.get(on_window).setVisible(false);
                listforEdits.get(on_window).setVisible(false);
                listforOutputRolls.get(on_window).setVisible(false);
                listforOutputs.get(on_window).setVisible(false);
            }


            MyTextPane Edit;
            Edit = new MyTextPane();
            JScrollPane scrollpane = new JScrollPane(Edit);
            listforRolls.add(scrollpane);

            listforEdits.add(Edit);


            MyTextPane output = new MyTextPane();
            output.setEnabled(false);
            JScrollPane scrollpane_out = new JScrollPane(output);
            listforOutputs.add(output);
            listforOutputRolls.add(scrollpane_out);


            on_window = (listforEdits.size() - 1);
            //int temp=on_window+1;
            JButton switchpage = new JButton(file.getName());
            switchpage.setBorder(BorderFactory.createRaisedBevelBorder());
            switchpage.setMaximumSize(new Dimension(110, 25));
            switchpage.setPreferredSize(new Dimension(110, 25));
            switchpage.setLayout(new BorderLayout());//改变按钮的布局使它们之间可以重叠
            listforbuttons.add(switchpage);


            switchpage.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //同理，先关闭现在打开的window
                            if (listforEdits.size() > 0) {
                                listforRolls.get(on_window).setVisible(false);
                                listforEdits.get(on_window).setVisible(false);
                                listforOutputRolls.get(on_window).setVisible(false);
                                listforOutputs.get(on_window).setVisible(false);
                                //System.out.println(on_window);//用于检测关闭是否正确
                            }

                            for (int j = 0; j < listforbuttons.size(); j++) {
                                if (e.getSource() == listforbuttons.get(j)) {
                                    on_window = j;
                                    listforRolls.get(on_window).setVisible(true);
                                    listforEdits.get(on_window).setVisible(true);
                                    listforOutputRolls.get(on_window).setVisible(true);
                                    listforOutputs.get(on_window).setVisible(true);

                                }
                            }
                            //System.out.println(on_window);//用于检测Button是否相应
                            mainpanel.revalidate();
                        }

                    }
            );
            //on_window.set(i, 0);
            //mainpanel.add(listforEdits.get(i));此条用于演示原Edit的数据能否缓存在ArrayList的Edit中

            JButton close = new JButton();
            close.setBorder(BorderFactory.createRaisedBevelBorder());
            close.setMaximumSize(new Dimension(25, 25));
            close.setPreferredSize(new Dimension(25, 25));
            close.setIcon(icon_close);
            close.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                            for (int j = 0; j < listforcloses.size(); j++) {
                                if (e.getSource() == listforcloses.get(j)) {
                                    on_window = j;
                                }
                            }

                            //先删除该删除的组件
                            mainpanel.remove(listforRolls.get(on_window));
                            mainpanel.remove(listforEdits.get(on_window));
                            mainpanel.remove(listforOutputRolls.get(on_window));
                            mainpanel.remove(listforOutputs.get(on_window));
                            box.remove(on_window);
                            //System.out.println("close1  "+on_window);
                            listforEdits.remove(on_window);
                            listforRolls.remove(on_window);
                            listforbuttons.remove(on_window);
                            listforcloses.remove(on_window);
                            listforOutputRolls.remove(on_window);
                            listforOutputs.remove(on_window);
                            if (listforbuttons.size() == 0) {
                                on_window = -1;
                            }

                            if (on_window == listforbuttons.size()) {
                                on_window--;
                            }
                            //System.out.println("close2  "+on_window);
                            if (on_window != -1) {
                                listforRolls.get(on_window).setVisible(true);
                                listforEdits.get(on_window).setVisible(true);
                                listforOutputRolls.get(on_window).setVisible(true);
                                listforOutputs.get(on_window).setVisible(true);
                            }

                            mainpanel.updateUI();
                            mainpanel.repaint();
                            mainpanel.revalidate();
                        }
                    }
            );

            listforcloses.add(close);

            switchpage.add(close, BorderLayout.EAST);
            box.add(switchpage);

            Edit.setForeground(Color.WHITE);
            Edit.setBackground(Color.BLACK);
            Edit.setFont(textfont);
            Edit.setCaretColor(Color.WHITE);

            output.setForeground(Color.BLACK);
            output.setBackground(Color.WHITE);
            output.setCaretColor(Color.BLACK);

            mainpanel.setLayout(new BorderLayout());
            mainpanel.add(scrollpane, BorderLayout.CENTER);
            mainpanel.add(scrollpane_out, BorderLayout.SOUTH);

            mainpanel.add(box, BorderLayout.NORTH);


            listforEdits.get(on_window).setText(str);
            try {
                if (file != null) {
                    FileInputStream in = new FileInputStream(file);
                    InputStreamReader ipr = new InputStreamReader(in);
                    BufferedReader bf = new BufferedReader(ipr);
                    int i = 0;


                    while ((str = bf.readLine()) != null) {
                        listforEdits.get(on_window).ScanText(i);
                        i++;
                    }
                }

            } catch (FileNotFoundException e) {
                System.out.println("打开文件失败");
            } catch (IOException e) {
                System.out.println("打开文件失败");
            }


            mainpanel.revalidate();

        }

    }

    public void save() {
        /*
         *保存文件调用的函数
         */
        if (file == null) {
            JFileChooser jfc = new JFileChooser();
            jfc.showSaveDialog(jfc);
            file = jfc.getSelectedFile();
            if (file != null) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    //TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        OutputStreamWriter out = null;
        if (file != null) {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                out = new OutputStreamWriter(fos);

                out.write(listforEdits.get(on_window).getText());
                out.flush();
                out.close();
            } catch (IOException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public void resave() {
        /*
         *另存为文件调用的函数，实质上和save()一样
         */
        JFileChooser jfc = new JFileChooser();
        jfc.showSaveDialog(jfc);
        file = jfc.getSelectedFile();
        if (file != null) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        OutputStreamWriter out = null;
        if (file != null) {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                out = new OutputStreamWriter(fos);

                out.write(listforEdits.get(on_window).getText());
                out.flush();
                out.close();
            } catch (IOException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public void New() {
        /*
         *每次New任何新的文件都要调用的函数，生成一个新的JPanel然后添加好各种组件和排版布局
         */
        //同理，先关闭现在打开的window
        if (listforEdits.size() > 0) {
            listforRolls.get(on_window).setVisible(false);
            listforEdits.get(on_window).setVisible(false);
            listforOutputRolls.get(on_window).setVisible(false);
            listforOutputs.get(on_window).setVisible(false);
        }

        MyTextPane Edit;
        Edit = new MyTextPane();
        JScrollPane scrollpane = new JScrollPane(Edit);
        MyTextPane output = new MyTextPane();
        output.setEnabled(false);
        JScrollPane scrollpane_out = new JScrollPane(output);

        listforEdits.add(Edit);
        listforRolls.add(scrollpane);
        listforOutputs.add(output);
        listforOutputRolls.add(scrollpane_out);
        on_window = (listforEdits.size() - 1);

        //int temp=on_window+1;
        JButton switchpage = new JButton("Untitled*");
        switchpage.setBorder(BorderFactory.createRaisedBevelBorder());
        switchpage.setMaximumSize(new Dimension(110, 25));
        switchpage.setPreferredSize(new Dimension(110, 25));
        switchpage.setLayout(new BorderLayout());//设置为边界布局方便放置关闭按钮
        listforbuttons.add(switchpage);


        switchpage.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //ͬ���ȹر����ڴ򿪵�window
                        if (listforEdits.size() > 0) {
                            listforRolls.get(on_window).setVisible(false);
                            listforEdits.get(on_window).setVisible(false);
                            listforOutputRolls.get(on_window).setVisible(false);
                            listforOutputs.get(on_window).setVisible(false);
                            //System.out.println("button1  "+on_window);//用于检测关闭是否正确
                        }

                        for (int j = 0; j < listforbuttons.size(); j++) {
                            if (e.getSource() == listforbuttons.get(j)) {
                                on_window = j;
                                listforRolls.get(on_window).setVisible(true);
                                listforEdits.get(on_window).setVisible(true);
                                listforOutputRolls.get(on_window).setVisible(true);
                                listforOutputs.get(on_window).setVisible(true);
                            }
                        }
                        //System.out.println("button2  "+on_window);//用于检测Button是否相应
                        mainpanel.revalidate();
                    }

                }
        );
        //on_window.set(i, 0);
        //mainpanel.add(listforEdits.get(i));此条用于演示原Edit的数据能否缓存在ArrayList的Edit中

        //接着是相应的关闭按钮
        JButton close = new JButton();
        close.setBorder(BorderFactory.createRaisedBevelBorder());
        close.setMaximumSize(new Dimension(25, 25));
        close.setPreferredSize(new Dimension(25, 25));
        close.setIcon(icon_close);
        close.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        for (int j = 0; j < listforcloses.size(); j++) {
                            if (e.getSource() == listforcloses.get(j)) {
                                on_window = j;
                            }
                        }

                        //先删除该删除的组件
                        mainpanel.remove(listforRolls.get(on_window));
                        mainpanel.remove(listforEdits.get(on_window));
                        mainpanel.remove(listforOutputRolls.get(on_window));
                        mainpanel.remove(listforOutputs.get(on_window));
                        box.remove(on_window);
                        //System.out.println("close1  "+on_window);
                        listforEdits.remove(on_window);
                        listforbuttons.remove(on_window);
                        listforcloses.remove(on_window);
                        listforRolls.remove(on_window);
                        listforOutputRolls.remove(on_window);
                        listforOutputs.remove(on_window);

                        if (listforbuttons.size() == 0) {
                            on_window = -1;
                        }

                        if (on_window == listforbuttons.size()) {
                            on_window--;
                        }
                        //System.out.println("close2  "+on_window);
                        if (on_window != -1) {
                            listforRolls.get(on_window).setVisible(true);
                            listforEdits.get(on_window).setVisible(true);
                            listforOutputRolls.get(on_window).setVisible(true);
                            listforOutputs.get(on_window).setVisible(true);
                        }

                        mainpanel.updateUI();
                        mainpanel.repaint();
                        mainpanel.revalidate();
                    }
                }
        );

        listforcloses.add(close);

        switchpage.add(close, BorderLayout.EAST);

        box.add(switchpage);

        Edit.setForeground(Color.WHITE);
        Edit.setBackground(Color.BLACK);
        Edit.setFont(textfont);
        Edit.setCaretColor(Color.WHITE);

        output.setForeground(Color.BLACK);
        output.setBackground(Color.WHITE);
        Edit.setCaretColor(Color.WHITE);
        output.setCaretColor(Color.BLACK);

        mainpanel.setLayout(new BorderLayout());
        mainpanel.add(scrollpane, BorderLayout.CENTER);
        mainpanel.add(scrollpane_out, BorderLayout.SOUTH);
        mainpanel.add(box, BorderLayout.NORTH);
        mainpanel.revalidate();
    }

    public void actionPerformed(ActionEvent e)

    {
        /*
         *各种消息用名字和if来进行判断和处理
         */
        if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
        if (e.getActionCommand().equals("Class")) {
            New();
        }
        if (e.getActionCommand().equals("Interface")) {
            New();
        }
        if (e.getActionCommand().equals("Open File")) {
            read();
        }
        if (e.getActionCommand().equals("Save")) {
            save();
        }
        if (e.getActionCommand().equals("Save As")) {
            resave();
        }
        if (e.getActionCommand().equals("Compile")) {
            //这里调用编译函数
        }
        if (e.getActionCommand().equals("Assemble")) {
            //这里调用汇编函数
        }
    }


    public static void main(String[] args) {
        Frames frame = new Frames();
    }


}
