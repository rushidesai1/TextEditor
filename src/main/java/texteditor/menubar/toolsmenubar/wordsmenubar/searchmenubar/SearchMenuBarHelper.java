package texteditor.menubar.toolsmenubar.wordsmenubar.searchmenubar;

import texteditor.TextEditorFrame;
import texteditor.menubar.toolsmenubar.wordsmenubar.enums.EnumWordsMenuBarItems;
import texteditor.menubar.toolsmenubar.wordsmenubar.searchmenubar.searchnewmenubar.SearchData;
import texteditor.menubar.toolsmenubar.wordsmenubar.searchmenubar.searchnewmenubar.enums.EnumSearchNewMenuBar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by desair4 on 11/23/2015.
 */
public class SearchMenuBarHelper {

    final private TextEditorFrame textEditorFrame;

    public SearchMenuBarHelper(TextEditorFrame textEditorFrame1) {
        textEditorFrame = textEditorFrame1;
        //Register Document listner to clear text after searching when you update document.
        RemoveHighLightingDocumentListener removeHighLightingDocumentListener = new RemoveHighLightingDocumentListener();
        Document currentDocument = textEditorFrame.getCurrentTextArea().getDocument();
        currentDocument.addDocumentListener(removeHighLightingDocumentListener);
    }

    public void setupSearchMenu(JMenuItem wordsMenuItem) {
        final JMenuItem searchMenuItem = new JMenu(EnumWordsMenuBarItems.SEARCH.valueOf());
        wordsMenuItem.add(searchMenuItem);

        setupSearchMenuDelegate(searchMenuItem);
    }

    private void setupSearchMenuDelegate(JMenuItem searchMenuItem) {
        /*SearchNewMenuBarHelper searchNewMenuBarHelper = new SearchNewMenuBarHelper(textEditorFrame) ;
        searchNewMenuBarHelper.setupSearchNewMenu(searchMenuItem);*/
        final JMenuItem searchNewItem;

        //Create child menu Items If necessary
        searchNewItem = new JMenuItem(EnumSearchNewMenuBar.SEARCHNEW.valueOf());
        searchMenuItem.add(searchNewItem);

        final ActionSearchMenuSearchOptionClick actionSearchMenuSearchOptionClick = new ActionSearchMenuSearchOptionClick(textEditorFrame, searchMenuItem);
        searchNewItem.addActionListener(actionSearchMenuSearchOptionClick);
    }

    private Highlighter removeHighLights() {
        Highlighter h = textEditorFrame.getCurrentTextArea().getHighlighter(); //get the highlighter for the JTextArea

        Highlighter.Highlight[] removeHighlights = h.getHighlights();

        //logic to remove the previous highlights when new file opens
        for (int i = 0; i < removeHighlights.length; i++) {
            Highlighter.Highlight highlight = removeHighlights[i];
            if (highlight.getPainter() instanceof DefaultHighlighter.DefaultHighlightPainter) {
                h.removeHighlight(highlight);
            }
        }
        return h;
    }

    private class ActionSearchMenuSearchOptionClick implements ActionListener {

        final private TextEditorFrame textEditorFrame;
        final private JTextArea currentTextArea;
        final private JMenuItem searchNewItemParent;
        final private Queue<JMenuItem> searchHistoryQueue;
        private int maxSearchHistory = 3;

        public ActionSearchMenuSearchOptionClick(TextEditorFrame textEditorFrame1, JMenuItem searchMenuItemParent1) {
            textEditorFrame = textEditorFrame1;
            currentTextArea = textEditorFrame.getCurrentTextArea();
            searchNewItemParent = searchMenuItemParent1;
            searchHistoryQueue = new LinkedList<JMenuItem>();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String input = (String) JOptionPane.showInputDialog(null, "Enter String to Search", "Input", JOptionPane.OK_CANCEL_OPTION, null, null, null);
            searchAndHighlight(input);
        }

        private void searchAndHighlight(String input) {
            //highlight if search is successful
            try {
                removeHighLights();
                if (!input.equals(null)) {
                    Highlighter h = currentTextArea.getHighlighter();

                    int searchPosition = currentTextArea.getText().toLowerCase().indexOf(input.toLowerCase());
                    int length = input.length();
                    int countOccurrences = 0;

                    try {
                        while (searchPosition != -1) {
                            h.addHighlight(searchPosition, searchPosition + length, DefaultHighlighter.DefaultPainter);
                            searchPosition = currentTextArea.getText().toLowerCase().indexOf(input.toLowerCase(), searchPosition + 1);
                            countOccurrences++;
                        }

                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    }

                    textEditorFrame.getFooterLabel().setText(input + ". " + countOccurrences + " occurrences.");
                    SearchData searchData = new SearchData();
                    searchData.isSearchStringPresent = countOccurrences > 0 ? true : false;
                    searchData.numberOfOccurrences = countOccurrences;

                    handleSearchHistory(input, searchNewItemParent, searchData);
                }
            } catch (NullPointerException cancel) {
                //catch the null values when user doesn't enter anything
                cancel.printStackTrace();
            }
        }

        private void handleSearchHistory(String searchString, JMenuItem searchNewItemParent, SearchData searchData) {

            if (isElementValidTobeAddedToQueue(searchString, searchData)) {
                final JMenuItem menuItem = new JMenuItem(searchString);
                final ActionSearchMenuSearchOptionClickExtend searchMenuSearchOptionClick = new ActionSearchMenuSearchOptionClickExtend();
                searchMenuSearchOptionClick.setInputSearchString(searchString);
                menuItem.addActionListener(searchMenuSearchOptionClick);

                removeAllCurrentItems(searchHistoryQueue, searchNewItemParent);

                //if queue size == maximum size of history -> then remove last element so to keep queue of mx size
                if (searchHistoryQueue.size() == maxSearchHistory) {
                /*searchHistoryQueue.clear();
                return;*/
                    searchHistoryQueue.remove();
                }
                searchHistoryQueue.add(menuItem);
                addRequiredItems(searchHistoryQueue, searchNewItemParent);

            }
        }

        private boolean isElementValidTobeAddedToQueue(String searchString, SearchData searchData) {
            if (searchData.isSearchStringPresent) {
                for (JMenuItem menuItem : searchHistoryQueue) {
                    String searchHistoryString = menuItem.getActionCommand();
                    if (searchHistoryString.equalsIgnoreCase(searchString)) {
                        return false;
                    }
                }
                return true;
            } else {
                //search string isnt present in current text area so return false;
                return false;
            }
        }

        private void removeAllCurrentItems(Queue<JMenuItem> searchHistoryQueue, JMenuItem searchNewItemParent) {
            for (JMenuItem menuItem : searchHistoryQueue) {
                searchNewItemParent.remove(menuItem);
            }
        }

        private void addRequiredItems(Queue<JMenuItem> searchHistoryQueue, JMenuItem searchNewItemParent) {
            for (JMenuItem menuItem : searchHistoryQueue) {
                searchNewItemParent.add(menuItem);
            }
        }

        class ActionSearchMenuSearchOptionClickExtend implements ActionListener {

            private String inputSearchString;

            public void setInputSearchString(String s) {
                inputSearchString = s;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                searchAndHighlight(inputSearchString);
            }
        }
    }

    class RemoveHighLightingDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            removeHighLightsAndResetFooter();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            removeHighLightsAndResetFooter();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            removeHighLightsAndResetFooter();
        }

        public void removeHighLightsAndResetFooter() {
            removeHighLights();

            final JLabel footerLabel = textEditorFrame.getFooterLabel();
            footerLabel.setText("");
        }
    }
}
