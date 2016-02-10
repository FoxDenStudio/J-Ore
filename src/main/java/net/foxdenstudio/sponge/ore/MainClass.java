package net.foxdenstudio.sponge.ore;

import net.foxdenstudio.sponge.ore.annotations.DBKeyType;
import net.foxdenstudio.sponge.ore.annotations.Model;
import net.foxdenstudio.sponge.ore.annotations.ModelField;
import net.foxdenstudio.sponge.ore.test.Namespace;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Joshua on 2/10/2016.
 */
public class MainClass {

    Set<Class<?>> models;

    HashMap<Class<?>, List<Field>> modelFields;

    public void initialize() {
        modelFields = new HashMap<>();
        Reflections reflections = new Reflections();
        models = reflections.getTypesAnnotatedWith(Model.class);
        models.forEach(aClass -> modelFields.put(aClass, Arrays.asList(aClass.getDeclaredFields()).stream().filter(field1 -> field1.isAnnotationPresent(ModelField.class)).collect(Collectors.toList())));
//        sets.forEach(aClass -> System.out.println(aClass.getName()));


        modelFields.forEach((aClass, fields) -> {
            System.out.println(aClass.getName());
            fields.forEach(field -> System.out.println("\t" + field.getName()));
        });


        modelFields.forEach(Utils::createTableForObject);

        saveData(new Namespace("TestThingy"));
    }

    public <T> T saveData(T object) {
        if (modelFields.containsKey(object.getClass())) {
            SaveQuery query = new SaveQuery(object.getClass());
            modelFields.get(object.getClass()).forEach(field -> {
                try {
                    query.addSaveData(field, field.get(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            query.save();
        }
        return object;
    }

    public static void main(String[] args) {
        new MainClass().initialize();
    }
}
