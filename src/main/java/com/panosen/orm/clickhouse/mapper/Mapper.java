package com.panosen.orm.clickhouse.mapper;

import com.clickhouse.data.ClickHouseRecord;

public interface Mapper<TEntity> {
    TEntity map(ClickHouseRecord record) throws ReflectiveOperationException;
}

