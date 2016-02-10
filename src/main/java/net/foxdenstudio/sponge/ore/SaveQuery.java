package net.foxdenstudio.sponge.ore;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by Joshua on 2/10/2016.
 */
public class SaveQuery extends Query {
    HashMap<Field, Object> data;

    public SaveQuery(Class<?> aClass) {
        super(aClass);

        data = new HashMap<>();
    }

    void addSaveData(Field field, Object o) {
        data.put(field, o);
    }

    public void save() {
        data.forEach((field, o) -> System.out.println(field + " | " + o));
    }
}
