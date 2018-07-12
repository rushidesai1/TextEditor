package texteditor.menubar.toolsmenubar.wordsmenubar.bookmarkmenubar.enums;

/**
 * Created by desair4 on 11/29/2015.
 */
public enum EnumBookmarksMenuItems {

    NEW("New"),
    OLD("Old"),
    DELETE("Delete");

    String menuName;

    EnumBookmarksMenuItems(String name) {
        menuName = name;
    }

    public String valueOf() {
        return this.menuName;
    }

    @Override
    public String toString() {
        return "EnumBookmarksMenu{" +
                "menuName='" + menuName + '\'' +
                '}';
    }
}
