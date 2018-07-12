package texteditor.menubar.toolsmenubar.wordsmenubar.bookmarkmenubar.oldmenubar;

import texteditor.TextEditorFrame;
import texteditor.menubar.toolsmenubar.wordsmenubar.bookmarkmenubar.BookmarkManager;
import texteditor.menubar.toolsmenubar.wordsmenubar.bookmarkmenubar.enums.EnumBookmarksMenuItems;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by desair4 on 11/29/2015.
 */
public class OldMenuBarHelper {
    final private TextEditorFrame textEditorFrame;
    BookmarkManager bookmarkManagerSingleton;
    JMenuItem oldMenuItemParent;

    public OldMenuBarHelper(TextEditorFrame textEditorFrame1) {
        textEditorFrame = textEditorFrame1;
    }

    public void setupBookmarksMenu(JMenuItem bookmarksMenu) {
        JMenuItem oldMenuItem = new JMenu(EnumBookmarksMenuItems.OLD.valueOf());
        bookmarksMenu.add(oldMenuItem);

        bookmarkManagerSingleton = BookmarkManager.getInstance();
        bookmarkManagerSingleton.init(textEditorFrame, oldMenuItem);

        final ActionOldMenuOptionClick actionOldMenuOptionClick = new ActionOldMenuOptionClick();
        oldMenuItem.addActionListener(actionOldMenuOptionClick);
    }

    private class ActionOldMenuOptionClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Map<String,JMenuItem> currentBookmarksMap = bookmarkManagerSingleton.getCurrentBookmarksMap();

        }
    }
}
