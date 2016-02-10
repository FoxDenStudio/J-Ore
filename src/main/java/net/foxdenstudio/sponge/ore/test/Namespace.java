package net.foxdenstudio.sponge.ore.test;

import net.foxdenstudio.sponge.ore.annotations.DBKeyType;
import net.foxdenstudio.sponge.ore.annotations.FieldType;
import net.foxdenstudio.sponge.ore.annotations.Model;
import net.foxdenstudio.sponge.ore.annotations.ModelField;

/**
 * Created by Joshua on 2/10/2016.
 */
@Model(tableName = "Namespaces")
public class Namespace {

    @ModelField(fieldType = FieldType.INT, maxLength = 9, columnName = "uid", autoIncrement = true, keyType = DBKeyType.UNIQUE)
    public Integer uid;

    @ModelField(fieldType = FieldType.CHARACTER, maxLength = 50, columnName = "name")
    public String name;

    public Namespace(String name) {
        this.name = name;
    }
}
