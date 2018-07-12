package texteditor.menubar.mainmenubar;

import texteditor.TextEditorFrame;
import texteditor.menubar.filemenubar.FileMenuBarHelper;
import texteditor.menubar.filemenubar.enums.EnumFileMenuItems;
import texteditor.menubar.toolsmenubar.ToolsMenubarHelper;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by desair4 on 11/21/2015.
 */
public class MenuBarHelper {

    //Currently they are not exposed outside class. In future they can be exposed via (if necessary) synchronized getter's
    final private JMenuItem openItem = new JMenuItem(EnumFileMenuItems.OPEN.valueOf());
    final private JMenuItem saveItem = new JMenuItem(EnumFileMenuItems.SAVE.valueOf());
    final private JMenuItem saveAsItem = new JMenuItem(EnumFileMenuItems.SAVEAS.valueOf());
    final private JMenuItem closeItem = new JMenuItem(EnumFileMenuItems.CLOSE.valueOf());
    final private JMenuItem exitItem = new JMenuItem(EnumFileMenuItems.EXIT.valueOf());
    final private TextEditorFrame textEditorFrame;
    final private JFileChooser fileChooser;

    public MenuBarHelper(TextEditorFrame textEditorFrame1) {
        textEditorFrame = textEditorFrame1;
        fileChooser = getJFileChooser();
    }

    static public JFileChooser getJFileChooser() {
        final JFileChooser fileChooser = new JFileChooser();
        //set a default path that the save/open/saveAs dialog boxes should open
        final String DEFAULT_PATH = System.getProperty("user.home");
        fileChooser.setCurrentDirectory(new File(DEFAULT_PATH));
        FileNameExtensionFilter f = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(f);
        return fileChooser;
    }

    public JMenuItem getOpenItem() {
        return openItem;
    }

    //Define menu bar's menues and create them
    public JMenuBar createMenuBar(JMenuBar menuBar) {

        FileMenuBarHelper fileMenuBarHelper = new FileMenuBarHelper(textEditorFrame);
        fileMenuBarHelper.setupFileMenu(menuBar);

        ToolsMenubarHelper toolsMenubarHelper = new ToolsMenubarHelper(textEditorFrame);
        toolsMenubarHelper.toolsMenu(menuBar);

        helpMenu(menuBar);
        return menuBar;
    }

    //Help Menu - About us sections
    private void helpMenu(JMenuBar menuBar) {
        JMenu help = new JMenu("Help");
        menuBar.add(help);
        JMenuItem aboutItem = new JMenuItem("About");
        help.add(aboutItem);

        ActionOnHelpMenuClick actionHelp = new ActionOnHelpMenuClick();
        aboutItem.addActionListener(actionHelp);
    }

    //Listen to help menu events and display new frame with required contents - AboutUs click
    private class ActionOnHelpMenuClick implements ActionListener {
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
    }

    /**
     * @author Priyanka Shah
     *         Actionlistener  Class for menu item click
     */
  /*  class ActionOnFileMenuClick implements ActionListener {
        //initialize filename and file chooser (dialog box to choose filename)
        File filename = null;
        JFileChooser fileExplorer = new JFileChooser();

        //check what item is clicked and call appropriate method
        public void actionPerformed(ActionEvent e) {
            //default path and text file type
            fileExplorer.setCurrentDirectory(new File(java.TextEditor.DEFAULT_PATH));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            fileExplorer.setFileFilter(filter);

            Object option = e.getSource();

            if (option == openItem) {
                selectedOpen();
            } else if (option == saveItem) {
                selectedSave(filename);
            } else if (option == saveAsItem) {
                selectedSaveAs(filename);
            } else if (option == closeItem) {
                label.setText(" ");
                selectedClose();
            } else if (option == exitItem) {
                selectedExit();
            }
        }

        //Method for Save item click - similar to save as but doesn't open new choose object on each item click (only first time opens)
        private void selectedSave(File name) {
            String fileN = "";
            if (name == null) {  // get filename from user
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(java.TextEditor.DEFAULT_PATH));
                FileNameExtensionFilter f = new FileNameExtensionFilter("Text files", "txt");
                fc.setFileFilter(f);
                if (fc.showSaveDialog(ta) != JFileChooser.CANCEL_OPTION) {
                    name = fc.getSelectedFile();
                }
            }
            if (name != null) {  // else user cancelled
                try {
                    if (name.getName().lastIndexOf('.') > 0) {
                        fileN = name.getName().toString();
                    } else {
                        name = new File(name.getAbsolutePath() + ".txt");
                        fileN = name.getName().toString();
                    }
                    PrintWriter output = new PrintWriter(new FileWriter(name));
                    for (String line : ta.getText().split("\\n")) {
                        output.println(line);
                    }
                    output.close();
                    filename = name;
                    label.setText(fileN + " succesfully saved");
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "Cannot write to file: " + name,
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e) {
                    label.setText("Error saving file");
                }
            }
        }

        //Opens the selected file
        private void selectedOpen() {
            int result = fileExplorer.showOpenDialog(null);
            //if user doesn't cancel then continue
            if (result == JFileChooser.APPROVE_OPTION) {
                //clear previous file contents,if any
                ta.setText("");
                filename = fileExplorer.getSelectedFile();
                Scanner s;
                //read the file and print on text area
                try {
                    s = new Scanner(filename);
                    while (s.hasNextLine()) {
                        ta.append(s.nextLine() + "\n");
                    }
                    label.setText(filename.getName().toString());
                } catch (FileNotFoundException exc) {
                    JOptionPane.showMessageDialog(null, "File not found", "File error", JOptionPane.ERROR_MESSAGE);
                }

            } else if (result == JFileChooser.CANCEL_OPTION) {
                // cancel options
            }

        }
    }
*/
}
