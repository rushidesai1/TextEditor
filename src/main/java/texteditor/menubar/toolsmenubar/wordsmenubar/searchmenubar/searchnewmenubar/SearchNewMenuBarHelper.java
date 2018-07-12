package texteditor.menubar.toolsmenubar.wordsmenubar.searchmenubar.searchnewmenubar;

import texteditor.TextEditorFrame;
import texteditor.menubar.toolsmenubar.wordsmenubar.searchmenubar.searchnewmenubar.enums.EnumSearchNewMenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by desair4 on 11/23/2015.
 */
public class SearchNewMenuBarHelper {
    final private TextEditorFrame textEditorFrame;

    public SearchNewMenuBarHelper(TextEditorFrame textEditorFrame1) {
        textEditorFrame = textEditorFrame1;
    }

    public void setupSearchNewMenu(JMenuItem searchMenuItem) {
        final JMenuItem searchNewMenuItem = new JMenuItem(EnumSearchNewMenuBar.SEARCHNEW.valueOf());
        searchMenuItem.add(searchNewMenuItem);

        //Bind Listeners
        ActionSearchMenuSearchOptionClick actionSearchMenuSearchOptionClickListener = new ActionSearchMenuSearchOptionClick(textEditorFrame);
        searchNewMenuItem.addActionListener(actionSearchMenuSearchOptionClickListener);
    }


    private class ActionSearchMenuSearchOptionClick implements ActionListener {

        final JButton searchButton;
        final JFrame textReader;
        final private TextEditorFrame textEditorFrame;
        JTextArea textArea;

        public ActionSearchMenuSearchOptionClick(TextEditorFrame textEditorFrame1) {
            textEditorFrame = textEditorFrame1;
            textArea = textEditorFrame.getCurrentTextArea();
            textReader = new JFrame();
            searchButton = new JButton("Search");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }


        //take passed in value as an input to know the buttons and textreader status
        public void actionOnButtonClick(JFrame textReader, JButton search, JButton close, JTextArea ta) {

            String input = (String) JOptionPane.showInputDialog(null, "Enter String to Search", "Input", JOptionPane.OK_CANCEL_OPTION, null, null, null);
            // setupView();

           /* Highlighter h = ta.getHighlighter(); //get the highlighter for the JTextArea

            Highlighter.Highlight[] removeHighlights = h.getHighlights();

            //logic to remove the previous highlights when new file opens
            for (int i = 0; i < removeHighlights.length; i++) {
                Highlighter.Highlight highlight = removeHighlights[i];
                if (highlight.getPainter() instanceof DefaultHighlighter.DefaultHighlightPainter) {
                    h.removeHighlight(highlight);
                }

            }

            String input = (String) JOptionPane.showInputDialog(null, "Enter String to Search", "Input", JOptionPane.OK_CANCEL_OPTION, null, null, null);

            //highlight if search is successful
            try{
                if(!input.equals(null)){

                    int searchPosition = ta.getText().toLowerCase().indexOf(input.toLowerCase());
                    int length = input.length();
                    int countOccurences = 0;

                    try {
                        while(searchPosition != -1){
                            h.addHighlight(searchPosition, searchPosition + length, DefaultHighlighter.DefaultPainter);
                            searchPosition = ta.getText().toLowerCase().indexOf(input.toLowerCase(), searchPosition+1);
                            countOccurences++;
                        }

                    } catch (BadLocationException e1) {

                        System.out.println(e1);
                    }

                    textEditorFrame.getFooterLabel().setText("File: " + textEditorFrame.getCurrentFile().getName() + ". Search string: "+ input + ". " + countOccurences + " occurences." );

                }
            }
            catch(NullPointerException cancel){
                //catch the null values when user doesn't enter anything
            }
            // }*/

        }

        //method to setting up view for the first frame
        public void setupView() {
            JFrame nameFrame = new JFrame();
            JTextField nameText;
            JButton button1;
            JButton button2;
            // set up the view
            nameFrame.setTitle("Input");
            nameFrame.setPreferredSize(new Dimension(300, 125));
            nameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            nameFrame.setLayout(new FlowLayout());
            // create and add components
            nameFrame.add(new JLabel("Enter file name"));
            nameText = new JTextField(20);
            button1 = new JButton("OK");
            button2 = new JButton("Cancel");
            nameFrame.add(nameText);
            nameFrame.add(button1);
            nameFrame.add(button2);

            // Perform some action based on the button click (event)
            button1.addActionListener(this);
            button2.addActionListener(this);
            //display the frame on screen
            nameFrame.pack();
            nameFrame.setVisible(true);
        }

        //Overrides interface's method


    }

}
