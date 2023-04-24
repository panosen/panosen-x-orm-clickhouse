package com.panosen.orm.clickhouse;

import com.panosen.orm.clickhouse.annonation.Column;

import java.lang.reflect.Field;

public class EntityManager extends EntityManagerBase {

    public EntityManager(Class<?> clazz) {
        super(clazz);
    }

    protected EntityColumn buildEntityColumn(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column == null) {
            return null;
        }

        field.setAccessible(true);

        EntityColumn entityColumn = new EntityColumn();
        entityColumn.setColumnName(column.name());
        entityColumn.setField(field);

        return entityColumn;
    }
}
