package cn.yzl.android.avd.manager.setting;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by YZL on 2017/11/9.
 */
public class Setting implements Configurable {

    public static final String KEY_PATH = "cn_yzl_android_avd_manager_path";

    private JTextField etPath;
    private JPanel myPan;
    private JButton btSelect;

    @Nls
    @Override
    public String getDisplayName() {
        return "AvdManager";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        reset();
        btSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFilePicker();
            }
        });
        return myPan;
    }

    private void showFilePicker() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(jFileChooser.DIRECTORIES_ONLY);
        jFileChooser.showOpenDialog(myPan);
        File file = jFileChooser.getSelectedFile();
        etPath.setText(file.getAbsolutePath());
    }

    /**
     * 激活apply按钮
     *
     * @return
     */
    @Override
    public boolean isModified() {
        return true;
    }

    /**
     * 点击【apply】、【OK】时，调用 2017/3/20 14:12
     *
     * @throws ConfigurationException
     */
    @Override
    public void apply() throws ConfigurationException {
        PropertiesComponent.getInstance().setValue(KEY_PATH, etPath.getText());
    }

    @Override
    public void reset() {
        String path = PropertiesComponent.getInstance().getValue(KEY_PATH, "");
        etPath.setText(path);
    }

    /**
     * 一定要实现，否则在Android Studio中会报错 2017/4/4 17:40
     */
    @Override
    public void disposeUIResources() {
        reset();
    }
}