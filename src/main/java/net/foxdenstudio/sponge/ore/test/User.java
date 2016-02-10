package net.foxdenstudio.sponge.ore.test;

import net.foxdenstudio.sponge.ore.annotations.DBKeyType;
import net.foxdenstudio.sponge.ore.annotations.FieldType;
import net.foxdenstudio.sponge.ore.annotations.Model;
import net.foxdenstudio.sponge.ore.annotations.ModelField;

/**
 * Created by Joshua on 2/10/2016.
 * Project: J-Ore
 */
@Model(tableName = "User")
class User {

    @ModelField(columnName = "uid", fieldType = FieldType.INTEGER, maxLength = 10)
    private
    Integer uid;

    @ModelField(columnName = "username", fieldType = FieldType.CHARACTER, maxLength = 50, keyType = DBKeyType.UNIQUE, linkTo = "Namespaces.name")
    private
    String userName;

    @ModelField(columnName = "first_name", fieldType = FieldType.CHARACTER, maxLength = 50)
    private
    String firstName;

    @ModelField(columnName = "last_name", fieldType = FieldType.CHARACTER, maxLength = 50)
    private
    String lastName;

    @ModelField(columnName = "email", fieldType = FieldType.CHARACTER, maxLength = 50)
    private
    String email;

    public User(Integer uid, String userName, String firstName, String lastName, String email) {
        this.uid = uid;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getUid() {
        return uid;
    }

    public User setUid(Integer uid) {
        this.uid = uid;
        return this;
    }
}
//    CONSTRAINT User_Namespaces_name_fk FOREIGN KEY (username) REFERENCES Namespaces (name)
