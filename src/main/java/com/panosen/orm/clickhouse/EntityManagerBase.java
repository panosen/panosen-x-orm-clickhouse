package com.panosen.orm.clickhouse;

import com.google.common.collect.Maps;
import com.panosen.orm.clickhouse.EntityColumn;
import com.panosen.orm.clickhouse.annonation.DataSource;
import com.panosen.orm.clickhouse.annonation.Table;

import java.lang.reflect.Field;
import java.util.Map;

public abstract class EntityManagerBase {

    private final Class<?> clazz;
    private final String dataSourceName;
    private final String tableName;
    private final Map<String, EntityColumn> columnMap;

    public EntityManagerBase(Class<?> clazz) {
        this.clazz = clazz;

        DataSource dataSource = clazz.getAnnotation(DataSource.class);
        this.dataSourceName = dataSource != null ? dataSource.name() : null;

        Table table = clazz.getAnnotation(Table.class);
        this.tableName = table != null ? table.name() : null;

        this.columnMap = buildFieldMap(clazz);
    }

    private <TEntity> Map<String, EntityColumn> buildFieldMap(Class<TEntity> clazz) {
        Map<String, EntityColumn> columnNames = Maps.newLinkedHashMap();
        Class<?> currentClass = clazz;
        while (currentClass != null) {

            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                EntityColumn entityColumn = buildEntityColumn(field);
                if (entityColumn == null) {
                    continue;
                }
                columnNames.putIfAbsent(entityColumn.getColumnName(), entityColumn);
            }

            currentClass = currentClass.getSuperclass();
        }

        return columnNames;
    }

    protected abstract EntityColumn buildEntityColumn(Field field);

    // region getters

    public String getDataSourceName() {
        return dataSourceName;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, EntityColumn> getColumnMap() {
        return columnMap;
    }

    // endregion

    public Object createInstance() throws IllegalAccessException, InstantiationException {
        return this.clazz.newInstance();
    }
}
