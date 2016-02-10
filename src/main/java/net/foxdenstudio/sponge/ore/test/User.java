package net.foxdenstudio.sponge.ore.test;

import net.foxdenstudio.sponge.ore.annotations.DBKeyType;
import net.foxdenstudio.sponge.ore.annotations.FieldType;
import net.foxdenstudio.sponge.ore.annotations.Model;
import net.foxdenstudio.sponge.ore.annotations.ModelField;

/**
 * Created by Joshua on 2/10/2016.
 */
@Model(tableName = "User")
public class User {

    @ModelField(columnName = "uid", fieldType = FieldType.INTEGER, maxLength = 10)
    Integer uid;

    @ModelField(columnName = "username", fieldType = FieldType.CHARACTER, maxLength = 50, keyType = DBKeyType.UNIQUE, linkTo = "Namespaces.name")
    String userName;

    @ModelField(columnName = "firstname", fieldType = FieldType.CHARACTER, maxLength = 50)
    String firstName;

    @ModelField(columnName = "lastname", fieldType = FieldType.CHARACTER, maxLength = 50)
    String lastName;

    @ModelField(columnName = "email", fieldType = FieldType.CHARACTER, maxLength = 50)
    String email;

    String hello;

    public User(Integer uid, String userName, String firstName, String lastName, String email) {
        this.uid = uid;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
//    CONSTRAINT User_Namespaces_name_fk FOREIGN KEY (username) REFERENCES Namespaces (name)
