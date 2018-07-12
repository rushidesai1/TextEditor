package texteditor;

import texteditor.contentPane.ContentPaneHelper;
import texteditor.menubar.mainmenubar.MenuBarHelper;

import javax.swing.*;
import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

/**
 * Created by desair4 on 11/21/2015.
 */
public class TextEditorFrame extends JFrame {

    //Below fields Cannot be final
    private JTextArea currentTextArea;
    private JLabel footerLabel;
    private File currentFile;
    private JLabel lblStatus;
    private JLabel lblPosition;

    public TextEditorFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        super("TextEditor Frame");

//        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        this.setPreferredSize(new Dimension(500, 500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Text Editor");

        //Text Editor area and label in bottom
        final JTextArea textArea = new JTextArea();
        this.setCurrentTextArea(textArea);

        //Setup scroll bars
        final JScrollPane scrollBar = new JScrollPane(textArea);
        scrollBar.setViewportView(textArea);
        scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(BorderLayout.CENTER, scrollBar);

        final JLabel label = new JLabel(" ");
        this.setFooterLabel(label);
        this.add(BorderLayout.SOUTH, label);

        setupBottomPanel();

        JScrollPane jScrollPane2 = new JScrollPane();
        jScrollPane2.setViewportView(currentTextArea);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        // Create Menu Bar after all setup.
        final JMenuBar mainMenuBar = new JMenuBar();
        final MenuBarHelper menuBarHelper = new MenuBarHelper(this);
        this.setJMenuBar(menuBarHelper.createMenuBar(mainMenuBar));


        ContentPaneHelper contentPaneHelper = new ContentPaneHelper(this);
        contentPaneHelper.setupContentPaneToolBar();
        /*MultiCaret c=new MultiCaret();
        c.setBlinkRate(500);
        c.setAdditionalDots(Arrays.asList(2, 4, 7));
        textArea.setCaret(c);*/

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(size.width / 2 - this.getWidth() / 2,
                size.height / 2 - this.getHeight() / 2);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.pack();
        this.setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                TextEditorFrame.this.setVisible(false);
                TextEditorFrame.this.dispose();
            }
        });
    }

    private void setupBottomPanel() {
        JPanel jPanel = new JPanel();
        lblStatus = new JLabel();
        lblPosition = new JLabel();
        lblStatus.setText("Key");
        jPanel.add(lblStatus);

        lblPosition.setText("n chars");
        jPanel.add(lblPosition);

        jPanel.setBorder(BorderFactory.createEtchedBorder());
        jPanel.setPreferredSize(new Dimension(400, 24));

        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        getContentPane().add(jPanel, BorderLayout.PAGE_END);
    }

    public JTextArea getCurrentTextArea() {
        return currentTextArea;
    }

    public void setCurrentTextArea(JTextArea currentTextArea) {
        this.currentTextArea = currentTextArea;
    }

    public JLabel getFooterLabel() {
        return footerLabel;
    }

    public void setFooterLabel(JLabel footerLabel) {
        this.footerLabel = footerLabel;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }

    public JLabel getLblStatus() {
        return lblStatus;
    }

    public void setLblStatus(JLabel lblStatus) {
        this.lblStatus = lblStatus;
    }

    public JLabel getLblPosition() {
        return lblPosition;
    }

    public void setLblPosition(JLabel lblPosition) {
        this.lblPosition = lblPosition;
    }

    class MultiCaret extends DefaultCaret {
        private List<Integer> additionalDots;

        public void setAdditionalDots(List<Integer> additionalDots) {
            this.additionalDots = additionalDots;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            try {
                TextUI mapper = getComponent().getUI();
                for (Integer addDot : additionalDots) {
                    Rectangle r = mapper.modelToView(getComponent(), addDot, getDotBias());

                    if (isVisible()) {
                        g.setColor(getComponent().getCaretColor());
                        int paintWidth = 1;
                        r.x -= paintWidth >> 1;
                        g.fillRect(r.x, r.y, paintWidth, r.height);
                    } else {
                        getComponent().repaint(r);
                    }
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

    }
}