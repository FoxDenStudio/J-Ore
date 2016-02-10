package net.foxdenstudio.sponge.ore.annotations;

/**
 * Created by Joshua on 2/10/2016.
 * Project: J-Ore
 */
public enum DBKeyType {
    PRIMARY("PRIMARY KEY"), INDEX("INDEX"), UNIQUE("UNIQUE"), FOREIGN("FOREIGN KEY"), NULL("NUL");

    private final String keyText;

    DBKeyType(String keyText) {
        this.keyText = keyText;
    }

    public String text() {
        return this.keyText;
    }
}
