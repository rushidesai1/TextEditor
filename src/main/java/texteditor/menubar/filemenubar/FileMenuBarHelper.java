package texteditor.menubar.filemenubar;

import texteditor.TextEditorFrame;
import texteditor.menubar.filemenubar.enums.EnumFileMenuItems;
import texteditor.menubar.mainmenubar.MenuBarHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

/**
 * Created by desair4 on 11/23/2015.
 */
public class FileMenuBarHelper {

    final private TextEditorFrame textEditorFrame;
    final private JFileChooser fileChooser;

    public FileMenuBarHelper(TextEditorFrame textEditorFrame1) {
        textEditorFrame = textEditorFrame1;
        fileChooser = MenuBarHelper.getJFileChooser();
    }

    //File menu
    public void setupFileMenu(JMenuBar menuBar) {
        //Create and add new file menu to menu bar
        final JMenuItem file;
        file = new JMenu("File");
        menuBar.add(file);

        //Different file menu items - create and add them to file menu
        setupFileMenu(file);
    }

    private void setupFileMenu(JMenuItem file) {
        final JMenuItem openItem, saveItem, saveAsItem, closeItem, exitItem;

        openItem = new JMenuItem(EnumFileMenuItems.OPEN.valueOf());
        openItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/copy.png"))); // NOI18N

        saveItem = new JMenuItem(EnumFileMenuItems.SAVE.valueOf());
        saveItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N

        saveAsItem = new JMenuItem(EnumFileMenuItems.SAVEAS.valueOf());
        saveAsItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N


        closeItem = new JMenuItem(EnumFileMenuItems.CLOSE.valueOf());

        exitItem = new JMenuItem(EnumFileMenuItems.EXIT.valueOf());
        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        exitItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N

        File currentFile = null;

        file.add(openItem);
        file.add(saveItem);
        file.add(saveAsItem);
        file.add(closeItem);
        file.add(exitItem);

        //New class to be called on click of any menu item from file menu
        // ActionOnFileMenuClick actionFile = new ActionOnFileMenuClick();

        final ActionFileMenuOpenOptionClick actionFileMenuOpenOptionClickListener = new ActionFileMenuOpenOptionClick();
        openItem.addActionListener(actionFileMenuOpenOptionClickListener);

        final ActionFileMenuSaveOptionClick actionFileMenuSaveOptionClickListener = new ActionFileMenuSaveOptionClick();
        saveItem.addActionListener(actionFileMenuSaveOptionClickListener);

        final ActionFileMenuSaveAsOptionClick actionFileMenuSaveAsOptionClickListener = new ActionFileMenuSaveAsOptionClick();
        saveAsItem.addActionListener(actionFileMenuSaveAsOptionClickListener);

        final ActionFileMenuCloseOptionClick actionFileMenuCloseOptionClickListener = new ActionFileMenuCloseOptionClick();
        closeItem.addActionListener(actionFileMenuCloseOptionClickListener);

        final ActionFileMenuExitOptionClick actionFileMenuExitOptionClickListener = new ActionFileMenuExitOptionClick();
        exitItem.addActionListener(actionFileMenuExitOptionClickListener);
    }

    public class ActionFileMenuOpenOptionClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            final File currentFile;

            int result = fileChooser.showOpenDialog(null);
            //if user doesn't cancel then continue
            if (result == JFileChooser.APPROVE_OPTION) {
                //clear previous file contents,if any
                final JTextArea currentTextArea = textEditorFrame.getCurrentTextArea();
                currentTextArea.setText("");
                Scanner s;

                currentFile = fileChooser.getSelectedFile();
                textEditorFrame.setCurrentFile(fileChooser.getSelectedFile());
                //read the file and print on text area
                try {
                    s = new Scanner(currentFile);
                    while (s.hasNextLine()) {
                        currentTextArea.append(s.nextLine() + "\n");
                    }
                    final JLabel footerLabel = textEditorFrame.getFooterLabel();
                    footerLabel.setText(currentFile.getName().toString());
                } catch (FileNotFoundException exc) {
                    JOptionPane.showMessageDialog(null, "File not found", "File error", JOptionPane.ERROR_MESSAGE);
                }

            } else if (result == JFileChooser.CANCEL_OPTION) {
                // cancel options
            }
        }
    }

    public class ActionFileMenuSaveOptionClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            File currentFile = textEditorFrame.getCurrentFile();

            String fileN = "";
            if (currentFile == null) {  // get filename from user
                final JTextArea currentTextArea = textEditorFrame.getCurrentTextArea();
                if (fileChooser.showSaveDialog(currentTextArea) != JFileChooser.CANCEL_OPTION) {
                    currentFile = fileChooser.getSelectedFile();
                    textEditorFrame.setCurrentFile(currentFile);
                }
            }
            if (currentFile != null) {  // else user cancelled
                final JLabel footerLabel = textEditorFrame.getFooterLabel();
                try {
                    if (currentFile.getName().lastIndexOf('.') > 0) {
                        fileN = currentFile.getName().toString();
                    } else {
                        currentFile = new File(currentFile.getAbsolutePath() + ".txt");
                        textEditorFrame.setCurrentFile(currentFile);
                        fileN = currentFile.getName().toString();
                    }
                    final PrintWriter output = new PrintWriter(new FileWriter(currentFile));
                    for (String line : textEditorFrame.getCurrentTextArea().getText().split("\\n")) {
                        output.println(line);
                    }
                    output.close();
                    //filename = name;
                    footerLabel.setText(fileN + " successfully saved");
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "Cannot write to file: " + currentFile,
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e) {
                    footerLabel.setText("Error saving file");
                }
            }
        }
    }

    public class ActionFileMenuSaveAsOptionClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            selectedSaveAs();
        }

        //Method for Save as click
        private void selectedSaveAs() {
            File currentFile = textEditorFrame.getCurrentFile();
            //String to be printed on bottom label - initialize
            String fileN = "";
            //if user presses cancel do nothing and close dialog but if not cancel then get the selected file
            final JTextArea currentTextArea = textEditorFrame.getCurrentTextArea();
            int result = fileChooser.showDialog(currentTextArea, "Save As");
            if (result != JFileChooser.CANCEL_OPTION) {
                currentFile = fileChooser.getSelectedFile();
                textEditorFrame.setCurrentFile(currentFile);
            }

            if (currentFile != null) {
                final JLabel footerLabel = textEditorFrame.getFooterLabel();
                //format the name to be displayed on bottom label
                try {
                    if (currentFile.getName().lastIndexOf('.') > 0) {
                        fileN = currentFile.getName().toString();
                    } else {
                        currentFile = new File(currentFile.getAbsolutePath() + ".txt");
                        textEditorFrame.setCurrentFile(currentFile);
                        fileN = currentFile.getName().toString();
                    }
                    //Write the text area's text into a file
                    final PrintWriter output = new PrintWriter(new FileWriter(currentFile));
                    for (String line : currentTextArea.getText().split("\\n")) {
                        output.println(line);
                    }
                    output.close();
                    //change the filename
                    //currentFile = file;
                    //set text for display on bottom
                    footerLabel.setText(fileN + " successfully saved");
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "Cannot write to file: " + currentFile,
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e) {
                    footerLabel.setText("Error saving file");
                }
            }
        }
    }

    public class ActionFileMenuCloseOptionClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            selectedClose();
        }

        private void selectedClose() {
            textEditorFrame.getCurrentTextArea().setText("");
            textEditorFrame.setCurrentFile(null);
        }
    }

    public class ActionFileMenuExitOptionClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedExit();
        }

        private void selectedExit() {
            System.exit(0);
        }
    }
}
