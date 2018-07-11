package main.java.texteditor.menubar.toolsmenubar.wordsmenubar.bookmarkmenubar;

import main.java.texteditor.TextEditorFrame;
import main.java.texteditor.menubar.toolsmenubar.enums.EnumToolsMenuItems;
import main.java.texteditor.menubar.toolsmenubar.wordsmenubar.bookmarkmenubar.enums.EnumBookmarksMenuItems;
import main.java.texteditor.menubar.toolsmenubar.wordsmenubar.bookmarkmenubar.oldmenubar.OldMenuBarHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by desair4 on 11/29/2015.
 */
public class BookmarkMenuBarHelper {
    final private TextEditorFrame textEditorFrame;
    BookmarkManager bookmarkManagerSingleton;

    public BookmarkMenuBarHelper(TextEditorFrame textEditorFrame1) {
        textEditorFrame = textEditorFrame1;
    }

    //Tool Menu
    public void setupBookmarksMenu(JMenuItem toolsBarItem) {
        final JMenuItem bookmarkMenuItem = new JMenu(EnumToolsMenuItems.BOOKMARK.valueOf());
        toolsBarItem.add(bookmarkMenuItem);

        setupBookmarksMenuDelegate(bookmarkMenuItem);
    }

    public void setupBookmarksMenuDelegate(JMenuItem bookmarkMenuItem) {
        final JMenuItem deleteMenuItem, newMenuItem;

        //Create child menu Items If necessary
        newMenuItem = new JMenuItem(EnumBookmarksMenuItems.NEW.valueOf());
        deleteMenuItem = new JMenuItem(EnumBookmarksMenuItems.DELETE.valueOf());

        bookmarkMenuItem.add(newMenuItem);
        bookmarkMenuItem.add(deleteMenuItem);

        final OldMenuBarHelper oldMenuBarHelper = new OldMenuBarHelper(textEditorFrame);
        oldMenuBarHelper.setupBookmarksMenu(bookmarkMenuItem);

        //Get reference. Instace already created and initialized in setupBookmarksMenu() initialized later
        bookmarkManagerSingleton = BookmarkManager.getInstance();

        //Bind actionListener's
        final ActionNewMenuOptionClick actionNewMenuCountOptionClick = new ActionNewMenuOptionClick(textEditorFrame);
        newMenuItem.addActionListener(actionNewMenuCountOptionClick);

        final ActionDeleteMenuOptionClick actionDeleteMenuOptionClick = new ActionDeleteMenuOptionClick(textEditorFrame);
        deleteMenuItem.addActionListener(actionDeleteMenuOptionClick);

    }

    private class ActionNewMenuOptionClick implements ActionListener {
        final private TextEditorFrame textEditorFrame;

        public ActionNewMenuOptionClick(TextEditorFrame textEditorFrame1) {
            textEditorFrame = textEditorFrame1;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Integer caretPoint = textEditorFrame.getCurrentTextArea().getCaret().getDot();
            String input = (String) JOptionPane.showInputDialog(null, "Enter tag name", "Input", JOptionPane.OK_CANCEL_OPTION, null, null, null);
            bookmarkManagerSingleton.addBookmarkItem(input, caretPoint);
        }
    }

    private class ActionDeleteMenuOptionClick implements ActionListener {
        final private TextEditorFrame textEditorFrame;

        public ActionDeleteMenuOptionClick(TextEditorFrame textEditorFrame1) {
            textEditorFrame = textEditorFrame1;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> bookMarksList = new ArrayList<String>(bookmarkManagerSingleton.getCurrentBookmarksMap().keySet());
            String input = (String) JOptionPane.showInputDialog(null, "Enter tag name", "Input", JOptionPane.OK_CANCEL_OPTION, null, bookMarksList.toArray(), bookMarksList.size() != 0 ? bookMarksList.get(0) : null);

            bookmarkManagerSingleton.removeBookmarkItem(input);
        }
    }
}
