package com.panosen.orm.clickhouse.tasks;

import com.panosen.codedom.clickhouse.builder.SelectSqlBuilder;
import com.panosen.orm.clickhouse.DalClientExtension;
import com.panosen.orm.clickhouse.EntityManager;
import com.panosen.orm.clickhouse.mapper.EntityMapper;
import com.panosen.orm.clickhouse.mapper.Mapper;

import java.io.IOException;
import java.util.List;

public class SelectListTask extends SelectTask {

    public SelectListTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <TEntity> List<TEntity> selectList(SelectSqlBuilder selectSqlBuilder) throws Exception {
        Mapper<TEntity> mapper = new EntityMapper<>(entityManager);
        return DalClientExtension.selectList(dalClient, selectSqlBuilder, mapper);
    }
}
