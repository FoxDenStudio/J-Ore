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

/**
 * Created by Joshua on 2/10/2016.
 */
public class Utils {
    static void createTableForObject(Class<?> aClass, List<Field> fields) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.sqlite3"); Statement statement = connection.createStatement()) {
            Model classAnno = aClass.getAnnotation(Model.class);
            final StringBuilder sql = new StringBuilder();
            List<String> constraints = new ArrayList<>();
            List<String> uniqueIndex = new ArrayList<>();
            sql.append("CREATE TABLE IF NOT EXISTS ");
            sql.append(classAnno.tableName());
            sql.append(" (");

            fields.forEach(field -> {
                ModelField anno = field.getAnnotation(ModelField.class);

                if (anno.keyType() == DBKeyType.UNIQUE) {
                    StringBuilder uniqueInd = new StringBuilder();
                    uniqueInd.append("CREATE UNIQUE INDEX ");
                    uniqueInd.append(classAnno.tableName()).append("_").append(anno.columnName()).append("_index").append(" ");
                    uniqueInd.append(" ON ").append(classAnno.tableName()).append(" ");
                    uniqueInd.append("(").append(anno.columnName()).append(");");
                    uniqueIndex.add(uniqueInd.toString());
//                    uniqueIndex.add(CREATE UNIQUE INDEX User_username_uindex ON User (username););
                }

                sql.append(generateColumn(anno));
                sql.append(", ");

                if (!anno.linkTo().equals("")) {
                    StringBuilder constraint = new StringBuilder();
                    constraint.append("CONSTRAINT ");
                    constraint.append(classAnno.tableName()).append("_").append(anno.linkTo().replace('.', '_')).append("_fk").append(" ");
                    constraint.append("FOREIGN KEY ");
                    constraint.append("(").append(anno.columnName()).append(") ");
                    constraint.append("REFERENCES ");
                    constraint.append(anno.linkTo().split("\\.")[0]).append(" ");
                    constraint.append("(").append(anno.linkTo().split("\\.")[1]).append(")");
                    constraints.add(constraint.toString());
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

            uniqueIndex.forEach(s -> statement::executeUpdate);
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
