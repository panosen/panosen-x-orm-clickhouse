package com.panosen.orm.clickhouse.mapper;

import com.clickhouse.data.ClickHouseColumn;
import com.clickhouse.data.ClickHouseRecord;
import com.clickhouse.data.ClickHouseSimpleRecord;
import com.clickhouse.data.ClickHouseValue;
import com.panosen.orm.clickhouse.EntityColumn;
import com.panosen.orm.clickhouse.EntityManager;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class EntityMapper<TEntity> implements Mapper<TEntity> {

    private final EntityManager entityManager;

    public EntityMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TEntity map(List<ClickHouseColumn> columns, ClickHouseRecord record) throws ReflectiveOperationException {
        Object entity = entityManager.createInstance();
        for (Map.Entry<String, EntityColumn> entry : entityManager.getColumnMap().entrySet()) {

            Field field = entry.getValue().getField();

            if (columns.stream().noneMatch(v -> v.getColumnName().equals(entry.getKey()))) {
                continue;
            }

            setValue(field, entity, record, entry.getKey());
        }

        return (TEntity) entity;
    }

    private void setValue(Field field, Object entity, ClickHouseRecord record, String columnName)
            throws ReflectiveOperationException {
        ClickHouseValue clickHouseValue = record.getValue(columnName);

        if (clickHouseValue == null) {
            return;
        }

        Class<?> clazz = field.getType();

        if (clazz.equals(String.class)) {
            field.set(entity, clickHouseValue.asString());
        } else if (clazz.equals(BigDecimal.class)) {
            field.set(entity, clickHouseValue.isNullOrEmpty() ? null : new BigDecimal(clickHouseValue.asString()));
        } else if (clazz.equals(LocalDateTime.class)) {
            field.set(entity, clickHouseValue.asDateTime());
        } else if (clazz.equals(Integer.class)) {
            field.set(entity, clickHouseValue.asInteger());
        } else if (clazz.equals(Long.class)) {
            field.set(entity, clickHouseValue.asLong());
        }
    }
}
