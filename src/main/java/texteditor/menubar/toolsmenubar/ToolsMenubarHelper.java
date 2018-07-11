package main.java.texteditor.menubar.toolsmenubar;

import main.java.texteditor.TextEditorFrame;
import main.java.texteditor.menubar.toolsmenubar.wordsmenubar.WordsMenuBarHelper;
import main.java.texteditor.menubar.toolsmenubar.wordsmenubar.bookmarkmenubar.BookmarkMenuBarHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by desair4 on 11/23/2015.
 */
public class ToolsMenubarHelper {

    final private TextEditorFrame textEditorFrame;

    public ToolsMenubarHelper(TextEditorFrame textEditorFrame1) {
        textEditorFrame = textEditorFrame1;
        // fileChooser = MenuBarHelper.getJFileChooser() ;
    }

    //Tool Menu - need to implement in next HW
    public void toolsMenu(JMenuBar menuBar) {
        final JMenuItem tools = new JMenu("Tools");
        menuBar.add(tools);

        //Different file menu items - create and add them to file menu
        setupToolsMenuDelegate(tools);
    }

    private void setupToolsMenuDelegate(JMenuItem toolsMenuItem) {
        //JMenuItem wordsMenuItem,bookMarkMenuItem;

        //Create child menuItem's if necessary

        //Add child menuItems if any

        //Creation and adding delegated to Helper
        final WordsMenuBarHelper wordsMenuBarHelper = new WordsMenuBarHelper(textEditorFrame);
        wordsMenuBarHelper.setupWordsMenu(toolsMenuItem);

        final BookmarkMenuBarHelper bookmarkMenuBarHelper = new BookmarkMenuBarHelper(textEditorFrame);
        bookmarkMenuBarHelper.setupBookmarksMenu(toolsMenuItem);
        //bookMarkMenuItem = new JMenuItem(EnumToolsMenuItems.BOOKMARK.valueOf());

       /* toolsMenuItem.add(wordsMenuItem);
        toolsMenuItem.add(bookMarkMenuItem);
*/
        //Bind actionListeners
        //ActionToolsMenuWordCountOptionClick actionToolsMenuWordCountOptionClickListener = new ActionToolsMenuWordCountOptionClick();
        //wordsMenuItem.addActionListener(actionToolsMenuWordCountOptionClickListener);

    }

    /*private class ActionToolsMenuWordCountOptionClick implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }*/

    private class ActionToolsMenuBookMarkOptionClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
