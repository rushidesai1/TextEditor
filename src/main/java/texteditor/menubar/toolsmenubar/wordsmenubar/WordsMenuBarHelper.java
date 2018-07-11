package main.java.texteditor.menubar.toolsmenubar.wordsmenubar;

import main.java.texteditor.TextEditorFrame;
import main.java.texteditor.menubar.toolsmenubar.enums.EnumToolsMenuItems;
import main.java.texteditor.menubar.toolsmenubar.wordsmenubar.enums.EnumWordsMenuBarItems;
import main.java.texteditor.menubar.toolsmenubar.wordsmenubar.searchmenubar.SearchMenuBarHelper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by desair4 on 11/23/2015.
 */
public class WordsMenuBarHelper {
    final List<DocumentListener> conflictingDocumentListeners = new ArrayList<DocumentListener>();
    final Document document;
    final JLabel footerLabel;
    final private TextEditorFrame textEditorFrame;

    public WordsMenuBarHelper(TextEditorFrame textEditorFrame1) {
        textEditorFrame = textEditorFrame1;
        document = textEditorFrame.getCurrentTextArea().getDocument();
        footerLabel = new JLabel();//textEditorFrame.getFooterLabel() ;
        textEditorFrame.getContentPane().add(footerLabel, BorderLayout.PAGE_END);

    }

    //Tool Menu
    public void setupWordsMenu(JMenuItem toolsBarItem) {
        final JMenuItem wordsMenuItem = new JMenu(EnumToolsMenuItems.WORDS.valueOf());
        toolsBarItem.add(wordsMenuItem);

        //Different file menu items - create and add them to file menu
        setupWordsMenuDelegate(wordsMenuItem);
    }

    private void setupWordsMenuDelegate(JMenuItem wordsMenuItem) {
        final JMenuItem countMenuItem, uniqueMenuItem, searchMenuItem;

        //Create child menu Items If necessary
        countMenuItem = new JMenuItem(EnumWordsMenuBarItems.WORDSCOUNT.valueOf());
        uniqueMenuItem = new JMenuItem(EnumWordsMenuBarItems.UNIQUE.valueOf());
        //searchMenuItem  = new JMenuItem(EnumWordsMenuBarItems.SEARCH.valueOf());

        //Add child menuItems
        wordsMenuItem.add(countMenuItem);
        wordsMenuItem.add(uniqueMenuItem);
        //    wordsMenuItem.add(searchMenuItem) ;
        //Creation and adding delegated to Helper
        final SearchMenuBarHelper searchMenuBarHelper = new SearchMenuBarHelper(textEditorFrame);
        searchMenuBarHelper.setupSearchMenu(wordsMenuItem);

        //Bind actionListener's
        final ActionWordsMenuCountOptionClick actionWordsMenuCountOptionClickListener = new ActionWordsMenuCountOptionClick(textEditorFrame);
        countMenuItem.addActionListener(actionWordsMenuCountOptionClickListener);

        final ActionWordsMenuUniqueCountOptionClick actionWordsMenuUniqueCountOptionClickListener = new ActionWordsMenuUniqueCountOptionClick(textEditorFrame);
        uniqueMenuItem.addActionListener(actionWordsMenuUniqueCountOptionClickListener);
    }

    private void removeOtherConflictingListeners(DocumentListener actionListenerToBeAdded) {
        for (DocumentListener currentListener : conflictingDocumentListeners) {
            if (currentListener != actionListenerToBeAdded) {
                document.removeDocumentListener(currentListener);
            }
        }
    }

    private int countWordsInString(String contentAsString) {
        StringTokenizer st = new StringTokenizer(contentAsString);
        return st.countTokens();
    }

    private String getStringFromTextArea(JTextArea currentTextArea) {
        //Using StringBuilder instead of StringBuffer since no synchronization is needed here.
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : currentTextArea.getText().split("\\n")) {
            stringBuilder.append(line).append("\n");
        }

        return stringBuilder.toString();
    }

    private class ActionWordsMenuCountOptionClick implements ActionListener {

        final TextEditorFrame textEditorFrame;
        final WordCountDocumentListener wordCountDocumentListener;

        public ActionWordsMenuCountOptionClick(TextEditorFrame textEditorFrame1) {
            textEditorFrame = textEditorFrame1;
            wordCountDocumentListener = new WordCountDocumentListener(textEditorFrame.getCurrentTextArea());
            conflictingDocumentListeners.add(wordCountDocumentListener);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            wordCountDocumentListener.updateWordCount(textEditorFrame.getCurrentTextArea());
            document.addDocumentListener(wordCountDocumentListener);
            removeOtherConflictingListeners(wordCountDocumentListener);
        }
    }

    private class ActionWordsMenuUniqueCountOptionClick implements ActionListener {

        final TextEditorFrame textEditorFrame;
        final UniqueWordCountDocumentListener uniqueWordCountDocumentListener;

        public ActionWordsMenuUniqueCountOptionClick(TextEditorFrame textEditorFrame1) {
            textEditorFrame = textEditorFrame1;
            uniqueWordCountDocumentListener = new UniqueWordCountDocumentListener(textEditorFrame.getCurrentTextArea());
            conflictingDocumentListeners.add(uniqueWordCountDocumentListener);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            uniqueWordCountDocumentListener.updateUniqueWordCount(textEditorFrame.getCurrentTextArea());
            document.addDocumentListener(uniqueWordCountDocumentListener);
            removeOtherConflictingListeners(uniqueWordCountDocumentListener);
        }
    }

    class WordCountDocumentListener implements DocumentListener {

        final JTextArea currentTextArea;

        WordCountDocumentListener(JTextArea currentTextArea1) {
            currentTextArea = currentTextArea1;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateWordCount(currentTextArea);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateWordCount(currentTextArea);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }

        public void updateWordCount(JTextArea currentTextArea) {
            final String contentAsString = getStringFromTextArea(currentTextArea);

            final int totalWords = countWordsInString(contentAsString);

            footerLabel.setText("Word Count: " + totalWords);
            System.out.println("count");
            //footerLabel.setText("Word Count: "+totalWords);
        }
    }

    class UniqueWordCountDocumentListener implements DocumentListener {

        final JTextArea currentTextArea;

        UniqueWordCountDocumentListener(JTextArea currentTextArea1) {
            currentTextArea = currentTextArea1;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateUniqueWordCount(currentTextArea);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateUniqueWordCount(currentTextArea);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }

        public void updateUniqueWordCount(JTextArea currentTextArea) {
            final String contentAsString = getStringFromTextArea(currentTextArea);
            int uniqueWords = getUniqueWordCounts(contentAsString);

            //textEditorFrame.getFooterLabel() ;
            footerLabel.setText("Unique Words: " + uniqueWords);
        }

        private int getUniqueWordCounts(String contentAsString) {
            final StringTokenizer stringTokenizer = new StringTokenizer(contentAsString);
            int uniqueWords = 0;

            final Map<String, Integer> map = new HashMap<String, Integer>();
            while (stringTokenizer.hasMoreTokens()) {
                String currentToken = stringTokenizer.nextToken();
                if (!map.containsKey(currentToken)) {
                    map.put(currentToken, 1);
                    uniqueWords += 1;
                } else {
                    map.put(currentToken, map.get(currentToken) + 1);
                }
            }
            return uniqueWords;
        }
    }
}
