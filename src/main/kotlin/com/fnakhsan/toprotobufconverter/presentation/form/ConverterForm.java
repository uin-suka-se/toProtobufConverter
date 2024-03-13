package com.fnakhsan.toprotobufconverter.presentation.form;

import com.intellij.ui.components.JBScrollPane;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import javax.swing.*;

public class ConverterForm {
    RSyntaxTextArea kotlinTextArea;
    private JTextField fileName;
    public JButton btnConvert;
    private JLabel labelFileName;
    private JRadioButton rbKotlin;
    private JLabel labelLanguageSource;
    private JPanel panelSource;
    private JPanel panelControl;
    private JPanel panelLanguage;
    private JPanel panelGeneral;
    private JLabel labelProtobuf;
    private JPanel panelNumeric;
    private JLabel labelNumeric;
    private JRadioButton btnDefaultNum;
    private JRadioButton btnUnsignedNum;
    private JRadioButton btnSignedNum;
    private JRadioButton btnFixedNum;
    private JRadioButton btnSignedFixedNum;
    private JScrollPane panelKotlin;
    public JPanel rootPanel;


    private void createUIComponents() {
        kotlinTextArea = new RSyntaxTextArea();
        kotlinTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_KOTLIN);
        kotlinTextArea.isCodeFoldingEnabled();
        applyTheme(kotlinTextArea);
        panelKotlin = new JBScrollPane(kotlinTextArea);
    }

    private void applyTheme(RSyntaxTextArea textArea) {
        try {
            final String THEME = "/org/fife/ui/rsyntaxtextarea/themes/idea.xml";
            Theme.load(getClass().getResourceAsStream(THEME)).apply(textArea);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
}
