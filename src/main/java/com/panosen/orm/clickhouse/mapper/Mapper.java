package com.panosen.orm.clickhouse.mapper;

import com.clickhouse.data.ClickHouseColumn;
import com.clickhouse.data.ClickHouseRecord;

import java.util.List;

public interface Mapper<TEntity> {
    TEntity map(List<ClickHouseColumn> columns, ClickHouseRecord record) throws ReflectiveOperationException;
}

