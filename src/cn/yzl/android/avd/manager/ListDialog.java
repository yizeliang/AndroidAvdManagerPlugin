package cn.yzl.android.avd.manager;

import cn.yzl.android.avd.manager.setting.Setting;
import com.intellij.ide.util.PropertiesComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ListDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList jList;

    String sdkPath = null;
    static final String readListCommond = File.separator + "emulator" + File.separator + "emulator -list-avds";

    static final String openAvdCommond = File.separator + "emulator" + File.separator + "emulator -avd ";

    public ListDialog() {
        setTitle("AvdManager");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        sdkPath = PropertiesComponent.getInstance().getValue(Setting.KEY_PATH, "");
        startCommondReadList();
    }

    private void startCommondReadList() {
        if (sdkPath == null || sdkPath.isEmpty()) {
            return;
        }

        Runtime runtime = Runtime.getRuntime();
        DefaultListModel model = new DefaultListModel();
        try {
            Process process = runtime.exec(sdkPath + readListCommond);
            BufferedReader bufferedReader = new BufferedReader
                    (new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                model.addElement(line);
            }
            if (model.isEmpty()) {
                return;
            }
            jList.setModel(model);
            jList.setSelectedIndex(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void onOK() {
        List selectedValuesList = jList.getSelectedValuesList();
        if (!selectedValuesList.isEmpty()) {
            String value = (String) selectedValuesList.get(0);
            openAvd(value);
        }
        dispose();
    }

    private void openAvd(String value) {
        if (value == null || value.isEmpty()) {
            return;
        }

        try {
            Runtime.getRuntime().exec(sdkPath + openAvdCommond + value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        ListDialog dialog = new ListDialog();
        dialog.pack();
        dialog.setSize(500, 300);
        Toolkit kit = Toolkit.getDefaultToolkit();    // 定义工具包

        Dimension screenSize = kit.getScreenSize();   // 获取屏幕的尺寸

        int screenWidth = screenSize.width / 2;         // 获取屏幕的宽

        int screenHeight = screenSize.height / 2;       // 获取屏幕的高

        int height = 500;
        int width = 300;
        dialog.setLocation(screenWidth - width / 2, screenHeight - height / 2);

        dialog.setVisible(true);
    }
}
