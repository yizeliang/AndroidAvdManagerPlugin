package cn.yzl.android.avd.manager;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.awt.*;

public class Action extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        ListDialog dialog = new ListDialog();
        dialog.pack();
        dialog.setSize(500, 300);
        Toolkit kit = Toolkit.getDefaultToolkit();    // 定义工具包

        Dimension screenSize = kit.getScreenSize();   // 获取屏幕的尺寸

        int screenWidth = screenSize.width / 2;         // 获取屏幕的宽

        int screenHeight = screenSize.height / 2;       // 获取屏幕的高

        int height = 300;

        int width = 500;
        dialog.setLocation(screenWidth - width / 2, screenHeight - height / 2);

        dialog.setVisible(true);
    }
}
