package texteditor.menubar.toolsmenubar.wordsmenubar.bookmarkmenubar;

import texteditor.TextEditorFrame;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by desair4 on 11/29/2015.
 */
public class BookmarkManager {
    static private BookmarkManager singletonBookmarkInstance;
    private JMenuItem oldMenuItem;
    private Map<String, BookmarkData> currentBookmarksMap;
    private TextEditorFrame textEditorFrame;

    private BookmarkManager() {
    }

    public static BookmarkManager getInstance() {
        if (singletonBookmarkInstance == null) {
            singletonBookmarkInstance = new BookmarkManager();
        }
        return singletonBookmarkInstance;
    }

    public void init(TextEditorFrame textEditorFrame1, JMenuItem oldMenuItem1) {
        oldMenuItem = oldMenuItem1;
        textEditorFrame = textEditorFrame1;
        currentBookmarksMap = new HashMap<String, BookmarkData>();
    }

    public void removeBookmarkItem(String bookmarkItemCaseSensitiveName) {
        removeBookmarkFromMenu(bookmarkItemCaseSensitiveName);
        currentBookmarksMap.remove(bookmarkItemCaseSensitiveName);
    }

    private void removeBookmarkFromMenu(String bookmarkItemCaseSensitiveName) {
        BookmarkData bookmarkData = currentBookmarksMap.get(bookmarkItemCaseSensitiveName);
        JMenuItem menuItemTobeRemoved = bookmarkData.menuItem;
        oldMenuItem.remove(menuItemTobeRemoved);
    }

    public void addBookmarkItem(String bookmarkItemCaseSensitiveName, Integer caretPoint) {

        removeAllBookMarkItems();

        ActionBookmarkClick actionBookmarkClick = new ActionBookmarkClick();
        JMenuItem menuItemTobeAdded = new JMenuItem(bookmarkItemCaseSensitiveName);
        menuItemTobeAdded.addActionListener(actionBookmarkClick);
        BookmarkData bookmarkData = new BookmarkData(menuItemTobeAdded, caretPoint);
        currentBookmarksMap.put(bookmarkItemCaseSensitiveName, bookmarkData);

        Map<String, BookmarkData> sortedMap = new TreeMap<String, BookmarkData>(currentBookmarksMap);

        addAllBookMarkItems(sortedMap);
    }

    private void addAllBookMarkItems(Map<String, BookmarkData> sortedMap) {
        for (BookmarkData bookmarkData : sortedMap.values()) {
            JMenuItem menuItem = bookmarkData.menuItem;
            oldMenuItem.add(menuItem);
        }
    }

    private void removeAllBookMarkItems() {
        for (String bookmarkStringKey : currentBookmarksMap.keySet()) {
            removeBookmarkFromMenu(bookmarkStringKey);
        }
    }

    public Map<String, BookmarkData> getCurrentBookmarksMap() {
        return currentBookmarksMap;
    }

    private class ActionBookmarkClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //get name of clicked bookmark
            String bookMarkKey = ((JMenuItem) e.getSource()).getActionCommand();
            //get bookmark data for current clicked bookmark
            BookmarkData bookmarkData = currentBookmarksMap.get(bookMarkKey);

            final JTextArea currentTextArea = textEditorFrame.getCurrentTextArea();
            //set caret to bookmarked position
            currentTextArea.setCaretPosition(bookmarkData.bookmarkPoint);

            //Highlight first character
            Highlighter h = currentTextArea.getHighlighter();
            h.removeAllHighlights();
            final int currentTextAreaCaretPosition = currentTextArea.getCaretPosition();
            try {
                h.addHighlight(currentTextAreaCaretPosition, currentTextAreaCaretPosition + 1, DefaultHighlighter.DefaultPainter);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class BookmarkData {
        public JMenuItem menuItem;
        public Integer bookmarkPoint;

        BookmarkData(JMenuItem menuItem1, Integer caretPoint) {
            bookmarkPoint = caretPoint;
            menuItem = menuItem1;
        }
    }
}
