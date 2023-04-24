package com.panosen.orm.clickhouse;

import com.panosen.codedom.clickhouse.builder.SelectSqlBuilder;
import com.panosen.orm.clickhouse.tasks.SelectListTask;
import com.panosen.orm.clickhouse.tasks.SelectSingleTask;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DalTableDao<TEntity> {

    private final EntityManager entityManager;

    private final SelectListTask selectListTask;
    private final SelectSingleTask selectSingleTask;

    public DalTableDao(Class<? extends TEntity> clazz) throws IOException {
        this.entityManager = EntityManagerFactory.getOrCreateManager(clazz);

        this.selectListTask = new SelectListTask(entityManager);
        this.selectSingleTask = new SelectSingleTask(entityManager);
    }

    public List<TEntity> selectList(SelectSqlBuilder selectSqlBuilder) throws Exception {
        setDefault(selectSqlBuilder);
        return this.selectListTask.selectList(selectSqlBuilder);
    }

    public TEntity selectSingle(TEntity entity) throws Exception {
        return this.selectSingleTask.selectSingle(entity);
    }

    public TEntity selectSingle(SelectSqlBuilder selectSqlBuilder) throws Exception {
        setDefault(selectSqlBuilder);
        selectSqlBuilder.limit(1);
        return this.selectSingleTask.selectSingle(selectSqlBuilder);
    }

    private void setDefault(SelectSqlBuilder selectSqlBuilder) {
        selectSqlBuilder.from(entityManager.getTableName());
        for (Map.Entry<String, EntityColumn> entry : entityManager.getColumnMap().entrySet()) {
            selectSqlBuilder.column(entry.getKey());
        }
    }
}
