package net.foxdenstudio.sponge.ore.annotations;

/**
 * Created by Joshua on 2/10/2016.
 */
public enum DBKeyType {
    PRIMARY("PRIMARY KEY"), INDEX("INDEX"), UNIQUE("UNIQUE"), FOREIGN("FOREIGN KEY"), NULL("NUL");

    private String keyText;

    DBKeyType(String keyText) {
        this.keyText = keyText;
    }

    public String text() {
        return this.keyText;
    }
}
