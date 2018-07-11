package main.java.texteditor.contentPane;

import main.java.texteditor.TextEditorFrame;
import main.java.texteditor.menubar.filemenubar.FileMenuBarHelper;
import main.java.texteditor.menubar.filemenubar.FileMenuBarHelper.ActionFileMenuOpenOptionClick;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * Created by desair4 on 12/8/2015.
 */
public class ContentPaneHelper {
    final TextEditorFrame textEditorFrame;
    final FileMenuBarHelper fileMenuBarHelper;

    public ContentPaneHelper(TextEditorFrame textEditorFrame) {
        this.textEditorFrame = textEditorFrame;
        fileMenuBarHelper = new FileMenuBarHelper(textEditorFrame);
    }

    public void setupContentPaneToolBar() {
        final JToolBar jToolBar1 = new JToolBar();

        final JLabel fontLabel = new JLabel();

        final JComboBox fontComboBox = new JComboBox();

        loadFont(fontComboBox);

        final JComboBox selectSizeComboBox = new JComboBox();

        final JLabel sizeLabel = new JLabel();

        jToolBar1.setRollover(true);

        setupButtons(jToolBar1);

        fontLabel.setText("Font");
        jToolBar1.add(fontLabel);

        //    fontComboBox.setModel(new DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        fontComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jcbFontActionPerformed(evt, fontComboBox, selectSizeComboBox);
            }
        });
        jToolBar1.add(fontComboBox);

        sizeLabel.setText("Size");
        jToolBar1.add(sizeLabel);

        selectSizeComboBox.setModel(new DefaultComboBoxModel(new String[]{"10", "14", "18", "22", "26", "30", "34", "36", "40", "44", "48", "52"}));
        selectSizeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jcbSelectSizeActionPerformed(evt, selectSizeComboBox);
            }
        });
        jToolBar1.add(selectSizeComboBox);

        final JTextArea currentTextArea = textEditorFrame.getCurrentTextArea();
        currentTextArea.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextPane1CaretUpdate(evt);
            }
        });
        currentTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                jTextPane1MouseReleased(evt, textEditorFrame);
            }
        });
        currentTextArea.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }

            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTextPane1AncestorAdded(evt);
            }

            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        currentTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextPane1KeyTyped(evt);
            }
        });

        textEditorFrame.getContentPane().add(jToolBar1, BorderLayout.PAGE_START);
    }

    private void loadFont(JComboBox fontComboBox) {
        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // get all font name
        String[] fontNames = gEnv.getAvailableFontFamilyNames();
        // load to combobox
        ComboBoxModel model = new DefaultComboBoxModel(fontNames);
        fontComboBox.setModel(model);
    }


    private void jTextPane1AncestorAdded(AncestorEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextPane1KeyTyped(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        textEditorFrame.getLblStatus().setText("Typing: " + evt.getKeyChar());
    }

    private void jTextPane1CaretUpdate(javax.swing.event.CaretEvent evt) {
        int pos = textEditorFrame.getCurrentTextArea().getCaretPosition();
        Element map = textEditorFrame.getCurrentTextArea().getDocument().getDefaultRootElement();
        // get position of cursor on TextPane
        int row = map.getElementIndex(pos);
        Element lineElem = map.getElement(row);
        int col = pos - lineElem.getStartOffset();
        // show row and col
        textEditorFrame.getLblPosition().setText("At: row " + row + " col " + col);
    }

    private void jTextPane1MouseReleased(MouseEvent evt, TextEditorFrame textEditorFrame) {
        // TODO add your handling code here:
        JPopupMenu jPopupMenu1 = new JPopupMenu();

        JMenuItem jMenuItem2 = new JMenuItem();
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/images/copy.png")));
        jMenuItem2.setText("Copy");
        jPopupMenu1.add(jMenuItem2);

        JMenuItem jMenuItem3 = new JMenuItem();
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/images/paste.png")));
        jMenuItem3.setText("Paste");
        jPopupMenu1.add(jMenuItem3);

        if (evt.getButton() == MouseEvent.BUTTON3) {
            jPopupMenu1.show(textEditorFrame, evt.getX(), evt.getY());
        }
    }

    private void setupButtons(JToolBar jToolBar1) {
        final JButton btnNew = new JButton();
        final JButton btnOpen = new JButton();
        final JButton btnSave = new JButton();
        final JButton btnSelectColor = new JButton();


        btnNew.setIcon(new ImageIcon(getClass().getResource("/main/resources/images/new.png")));
        btnNew.setFocusable(false);
        btnNew.setHorizontalTextPosition(SwingConstants.CENTER);
        btnNew.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNew);

        btnOpen.setIcon(new ImageIcon(getClass().getResource("/main/resources/images/open.png")));
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(SwingConstants.CENTER);
        btnOpen.setVerticalTextPosition(SwingConstants.BOTTOM);

        final ActionFileMenuOpenOptionClick openActionListener = fileMenuBarHelper.new ActionFileMenuOpenOptionClick();
        btnOpen.addActionListener(openActionListener);
        jToolBar1.add(btnOpen);

        btnSave.setIcon(new ImageIcon(getClass().getResource("/main/resources/images/save.png")));
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSave.addActionListener(fileMenuBarHelper.new ActionFileMenuSaveOptionClick());
        jToolBar1.add(btnSave);

        btnSelectColor.setIcon(new ImageIcon(getClass().getResource("/main/resources/images/color.png")));
        btnSelectColor.setFocusable(false);
        btnSelectColor.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSelectColor.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSelectColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSelectColorActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSelectColor);
    }

    private void btnNewActionPerformed(ActionEvent evt) {
        textEditorFrame.getCurrentTextArea().setText("");
    }

    private void btnSelectColorActionPerformed(ActionEvent evt) {
        Color jColor = null;
        // open color dialog and select Color
        if ((jColor = JColorChooser.showDialog(textEditorFrame, "Select color", jColor)) != null) {
            //selectColor = jColor;
            // set text color
            textEditorFrame.getCurrentTextArea().setForeground(jColor);
        }
    }

    private void jcbFontActionPerformed(ActionEvent evt, JComboBox fontComboBox, JComboBox selectSizeComboBox) {
        // Change font of text
        textEditorFrame.getCurrentTextArea().setFont(new Font(fontComboBox.getSelectedItem().toString(),
                Font.PLAIN, Integer.parseInt(selectSizeComboBox.getSelectedItem().toString())));
    }

    private void jcbSelectSizeActionPerformed(ActionEvent evt, JComboBox selectSizeComboBox) {
        // Select size of text
        String getSize = selectSizeComboBox.getSelectedItem().toString();
        Font f = textEditorFrame.getFont();
        textEditorFrame.getCurrentTextArea().setFont(new Font(f.getFontName(),
                f.getStyle(), Integer.parseInt(getSize)));
    }
}
