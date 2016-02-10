package net.foxdenstudio.sponge.ore;

import net.foxdenstudio.sponge.ore.annotations.Model;
import net.foxdenstudio.sponge.ore.test.TestModel;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
        models.forEach(aClass -> modelFields.put(aClass, Arrays.asList(aClass.getFields())));
//        sets.forEach(aClass -> System.out.println(aClass.getName()));


        modelFields.forEach((aClass, fields) -> {
            System.out.println(aClass.getName());
            fields.forEach(field -> System.out.println("\t" + field.getName()));
        });


        modelFields.forEach((aClass, fields) -> {
            createTableForObject(aClass, fields);
        });

        saveData(new TestModel("TestThingy"));
    }

    private void createTableForObject(Class<?> aClass, List<Field> fields) {

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
