package net.foxdenstudio.sponge.ore.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Joshua on 2/10/2016.
 * Project: J-Ore
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ModelField {

    String columnName();

    int maxLength() default Integer.MAX_VALUE;

    FieldType fieldType();

    boolean autoIncrement() default false;

    DBKeyType keyType() default DBKeyType.NULL;

    boolean nullable() default false;

    String linkTo() default "";

    RelationshipType modifyRelType() default RelationshipType.NA;

    RelationshipType deleteRelType() default RelationshipType.NA;
}
