package texteditor.menubar.toolsmenubar.enums;

/**
 * Created by desair4 on 11/23/2015.
 */
public enum EnumToolsMenuItems {

    WORDS("Words"),
    BOOKMARK("Bookmark");

    String menuName;

    EnumToolsMenuItems(String name) {
        menuName = name;
    }

    public String valueOf() {
        return this.menuName;
    }

    @Override
    public String toString() {
        return "EnumToolsMenuItems{" +
                "menuName='" + menuName + '\'' +
                '}';
    }
}
