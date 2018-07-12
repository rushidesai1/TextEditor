package texteditor.menubar.filemenubar.enums;

/**
 * Created by desair4 on 11/21/2015.
 */
public enum EnumFileMenuItems {

    OPEN("Open.."),
    SAVE("Save "),
    SAVEAS("Save as.."),
    CLOSE("Close"),
    EXIT("Exit");

    String menuName;

    EnumFileMenuItems(String name) {
        menuName = name;
    }

    public String valueOf() {
        return this.menuName;
    }

    @Override
    public String toString() {
        return "EnumFileMenuItems{" +
                "menuName='" + menuName + '\'' +
                '}';
    }
}
