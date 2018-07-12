package texteditor;

import javax.swing.*;

/**
 * Author: Priyanka Shah
 * Date: 11/28/2015
 * java.TextEditor: Application to open and save text files
 */

public class TextEditor {

    /* //Instantiate new jframe,jpanel,textarea and menubar for the application
     JFrame textEditor = new JFrame();
     JPanel jpanel = new JPanel();
     JTextArea ta = new JTextArea();

     JMenuBar menuBar = new JMenuBar();
     JMenuItem openItem, saveItem, saveAsItem, closeItem, exitItem;
 */
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        //Start application
        TextEditorFrame tr = new TextEditorFrame();
    }

  /*  //View of screen when it opens first
    public void setupView() {
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setCaretPosition(0);
        JScrollPane scroller = new JScrollPane(ta);
        scroller.setViewportView(ta);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //set size and defaults for text editor
        textEditor.setPreferredSize(new Dimension(500, 500));
        textEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textEditor.setTitle("Text Editor");

        // Create Menu Bar
        textEditor.setJMenuBar(createMenuBar());
        //Text Editor area and label in bottom
        textEditor.add(BorderLayout.CENTER, scroller);
        textEditor.add(BorderLayout.SOUTH, label);

        textEditor.pack();
        textEditor.setVisible(true);
    }*/

    //Define menu bar's menues and create them
  /*  private JMenuBar createMenuBar() {
        fileMenu();
        toolsMenu();
        helpMenu();
        return menuBar;
    }
*/
    //File menu
  /*  private void fileMenu() {
        //Create and add new file menu to menu bar
        JMenu file = new JMenu("File");
        menuBar.add(file);
        //Different file menu items - create and add them to file menu
        openItem = new JMenuItem("Open...");
        saveItem = new JMenuItem("Save");
        saveAsItem = new JMenuItem("Save as..");
        closeItem = new JMenuItem("Close");
        exitItem = new JMenuItem("Exit");

        file.add(openItem);
        file.add(saveItem);
        file.add(saveAsItem);
        file.add(closeItem);
        file.add(exitItem);

        //New class to be called on click of any menu item from file menu
        ActionOnFileMenuClick actionFile = new ActionOnFileMenuClick();

        openItem.addActionListener(actionFile);
        saveItem.addActionListener(actionFile);
        saveAsItem.addActionListener(actionFile);
        closeItem.addActionListener(actionFile);
        exitItem.addActionListener(actionFile);
    }*/

   /* //Tool Menu - need to implement in next HW
    private void toolsMenu() {
        JMenu tools = new JMenu("Tools");
        menuBar.add(tools);
    }

    //Help Menu - About us sections
    private void helpMenu() {
        JMenu help = new JMenu("Help");
        menuBar.add(help);
        JMenuItem aboutItem = new JMenuItem("About");
        help.add(aboutItem);

        ActionOnHelpMenuClick actionHelp = new ActionOnHelpMenuClick();
        aboutItem.addActionListener(actionHelp);
    }
*/

    //Listen to help menu events and display new frame with required contents - AboutUs click
 /*   private class ActionOnHelpMenuClick implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            Font f = new Font(Font.SANS_SERIF, 15, 15);
            JFrame aboutUsFrame = new JFrame();
            JTextPane pane = new JTextPane();
            pane.setFont(f);
            aboutUsFrame.setPreferredSize(new Dimension(500, 300));
            aboutUsFrame.setTitle("About Text Editor");
            aboutUsFrame.add(pane);
            pane.setText("\n" + "\t\t\t" + "Text Editor" + "\n" + "\t\t\t" + "Created by: Priyanka Shah" + "\n" + "\t\t\t" + "Email: pashah@andrew.cmu.edu" + "\n\n");
            pane.insertIcon(new ImageIcon("image.png"));

            pane.setEditable(false);
            aboutUsFrame.pack();
            aboutUsFrame.setVisible(true);

        }
    }*/
}