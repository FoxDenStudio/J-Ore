package net.foxdenstudio.sponge.ore.test;

import net.foxdenstudio.sponge.ore.annotations.FieldType;
import net.foxdenstudio.sponge.ore.annotations.Model;
import net.foxdenstudio.sponge.ore.annotations.ModelField;

/**
 * Created by Joshua on 2/10/2016.
 */
@Model(tableName = "TestModel")
public class TestModel {

    @ModelField(fieldType = FieldType.CHARACTER, maxLength = 50, columnName = "name")
    public String name;

    public TestModel(String name) {
        this.name = name;
    }
}
