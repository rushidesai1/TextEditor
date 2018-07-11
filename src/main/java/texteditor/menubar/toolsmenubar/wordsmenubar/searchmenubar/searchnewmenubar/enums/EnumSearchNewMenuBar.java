package main.java.texteditor.menubar.toolsmenubar.wordsmenubar.searchmenubar.searchnewmenubar.enums;

/**
 * Created by desair4 on 11/23/2015.
 */
public enum EnumSearchNewMenuBar {

    SEARCHNEW("Search New..");

    String menuName;

    EnumSearchNewMenuBar(String name) {
        menuName = name;
    }

    public String valueOf() {
        return this.menuName;
    }
}
