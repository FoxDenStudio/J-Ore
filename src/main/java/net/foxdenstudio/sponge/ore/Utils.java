package net.foxdenstudio.sponge.ore;

import net.foxdenstudio.sponge.ore.annotations.DBKeyType;
import net.foxdenstudio.sponge.ore.annotations.Model;
import net.foxdenstudio.sponge.ore.annotations.ModelField;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    static void createTableForObject(Class<?> aClass, List<Field> fields) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.sqlite3"); Statement statement = connection.createStatement()) {
            Model model = aClass.getAnnotation(Model.class);
            final StringBuilder sql = new StringBuilder();
            List<String> constraints = new ArrayList<>();
            List<String> uniqueIndex = new ArrayList<>();
            sql.append("CREATE TABLE IF NOT EXISTS ");
            sql.append(model.tableName());
            sql.append(" (");

            fields.forEach(field -> {
                ModelField modelField = field.getAnnotation(ModelField.class);

                if (modelField.keyType() == DBKeyType.UNIQUE) {
                    String uniqueInd = "CREATE UNIQUE INDEX IF NOT EXISTS " +
                            model.tableName() + "_" + modelField.columnName() + "_index" + " " +
                            " ON " + model.tableName() + " " +
                            "(" + modelField.columnName() + ");";
                    uniqueIndex.add(uniqueInd);
//                    uniqueIndex.add(CREATE UNIQUE INDEX User_username_uindex ON User (username););
                }

                sql.append(generateColumn(modelField));
                sql.append(", ");

                if (!modelField.linkTo().equals("")) {
                    String constraint = "CONSTRAINT " +
                            model.tableName() + "_" + modelField.linkTo().replace('.', '_') + "_fk" + " " +
                            "FOREIGN KEY " +
                            "(" + modelField.columnName() + ") " +
                            "REFERENCES " +
                            modelField.linkTo().split("\\.")[0] + " " +
                            "(" + modelField.linkTo().split("\\.")[1] + ")";
                    constraints.add(constraint);
                }
            });
//            CONSTRAINT User_Namespaces_name_fk FOREIGN KEY (username) REFERENCES Namespaces (name)

            constraints.forEach(s -> {
                sql.append(s);
                sql.append(", ");
            });

            sql.delete(sql.length() - 2, sql.length());
            sql.append(");");
            System.err.println(sql.toString());
            statement.executeUpdate(sql.toString());

            uniqueIndex.forEach(s -> {
                System.out.println("S: " + s);
                try {
                    statement.executeUpdate(s);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String generateColumn(ModelField anno) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ");
        stringBuilder.append(anno.columnName());
        stringBuilder.append(" ");
        stringBuilder.append(anno.fieldType().name());
        stringBuilder.append(" ");
        if (anno.keyType() != DBKeyType.NULL && anno.keyType() != DBKeyType.UNIQUE) {
            //FIXME: CREATE UNIQUE INDEX User_username_uindex ON User (username); //GOES AT END
            stringBuilder.append(anno.keyType().text());
            stringBuilder.append(" ");
        }
        if (anno.nullable()) {
            stringBuilder.append("NULL");
            stringBuilder.append(" ");
        } else {
            stringBuilder.append("NOT NULL");
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

}
