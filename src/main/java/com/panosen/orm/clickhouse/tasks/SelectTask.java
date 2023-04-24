package com.panosen.orm.clickhouse.tasks;

import com.panosen.codedom.clickhouse.builder.ConditionsBuilder;
import com.panosen.codedom.clickhouse.builder.SelectSqlBuilder;
import com.panosen.orm.clickhouse.EntityColumn;
import com.panosen.orm.clickhouse.EntityManager;

import java.io.IOException;
import java.util.Map;

public abstract class SelectTask extends SingleTask {

    public SelectTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    protected <TEntity> SelectSqlBuilder buildSelectSqlBuilder(TEntity entity) throws IllegalAccessException {
        SelectSqlBuilder selectSqlBuilder = new SelectSqlBuilder()
                .from(entityManager.getTableName());

        if (entity == null) {
            return selectSqlBuilder;
        }

        ConditionsBuilder whereBuilder = selectSqlBuilder.where();
        for (Map.Entry<String, EntityColumn> entry : entityManager.getColumnMap().entrySet()) {
            Object value = entry.getValue().getField().get(entity);
            if (value == null) {
                continue;
            }
            whereBuilder.equal(entry.getKey(), value);
        }
        return selectSqlBuilder;
    }
}
