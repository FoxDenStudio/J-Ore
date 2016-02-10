package net.foxdenstudio.sponge.ore;

import net.foxdenstudio.sponge.ore.annotations.DBKeyType;
import net.foxdenstudio.sponge.ore.annotations.Model;
import net.foxdenstudio.sponge.ore.annotations.ModelField;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by Joshua on 2/10/2016.
 */
public class SaveQuery extends Query {
    HashMap<Field, Object> data;
    Class<?> aClass;

    public SaveQuery(Class<?> aClass) {
        this.aClass = aClass;
        data = new HashMap<>();
    }

    void addSaveData(Field field, Object o) {
        data.put(field, o);
    }

    public void save() {
        query.append("begin tran");
        query.append("\n\r");
        query.append("if exsists (select * from `");
        query.append(aClass.getAnnotation(Model.class).tableName());
        query.append("` (updlock,serializable) where ");
        final boolean[] done = {false};
        data.forEach((field2, o2) -> {
            if (!done[0]) {
                ModelField anno = field2.getAnnotation(ModelField.class);
                if (anno.keyType().equals(DBKeyType.UNIQUE)) {
                    query.append(anno.columnName());
                    query.append(" = ");
                    try {
                        query.append((o2 == null || field2.get(o2) == null || field2.get(o2).toString().isEmpty()) ? "NULL" : field2.get(o2).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    done[0] = true;
                }
            }
        });
        query.append(")");
        query.append("\n\r");
        query.append("begin");
        query.append("\n\r");
        query.append("\t");
        query.append("UPDATE TABLE SET ");
        //TODO UPDATE DATA
        query.append("\n\r");
        query.append("end");
        query.append("\n\r");
        query.append("else");
        query.append("\n\r");
        query.append("begin");
        query.append("\n\r");
        query.append("\t");
        query.append("INSERT INTO TABLE ");
        //TODO INSERT DATA
        query.append("\n\r");
        query.append("end");
        query.append("\n\r");
        query.append("commit tran");
        query.append("\n\r");
        System.out.println(query);
        data.forEach((field1, o1) -> {
            ModelField mf = field1.getAnnotation(ModelField.class);
            if (!(mf.autoIncrement() && o1 == null)) {

            }
        });
        data.forEach((field, o) -> System.out.println(field + " | " + o));
    }
}
