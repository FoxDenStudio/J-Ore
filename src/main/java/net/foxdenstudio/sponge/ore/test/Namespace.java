package net.foxdenstudio.sponge.ore.test;

import net.foxdenstudio.sponge.ore.annotations.DBKeyType;
import net.foxdenstudio.sponge.ore.annotations.FieldType;
import net.foxdenstudio.sponge.ore.annotations.Model;
import net.foxdenstudio.sponge.ore.annotations.ModelField;

/**
 * Created by Joshua on 2/10/2016.
 * Project: J-Ore
 */
@Model(tableName = "Namespaces")
public class Namespace {

    @ModelField(fieldType = FieldType.INTEGER, maxLength = 10, columnName = "uid", autoIncrement = true)
    private
    Integer uid;

    @ModelField(fieldType = FieldType.CHARACTER, maxLength = 50, columnName = "name", keyType = DBKeyType.UNIQUE)
    private
    String name;

    public Namespace(String name) {
        this.name = name;
    }

    public Integer getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public Namespace setName(String name) {
        this.name = name;
        return this;
    }
}
