package texteditor.menubar.toolsmenubar.wordsmenubar.enums;

/**
 * Created by desair4 on 11/23/2015.
 */
public enum EnumWordsMenuBarItems {
    WORDSCOUNT("Count"),
    UNIQUE("Unique"),
    SEARCH("Search");

    String menuName;

    EnumWordsMenuBarItems(String name) {
        menuName = name;
    }

    public String valueOf() {
        return this.menuName;
    }
}
