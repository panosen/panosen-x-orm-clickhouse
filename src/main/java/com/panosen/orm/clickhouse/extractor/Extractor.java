package com.panosen.orm.clickhouse.extractor;

import com.clickhouse.data.ClickHouseColumn;
import com.clickhouse.data.ClickHouseRecord;

import java.util.List;

public interface Extractor<T> {
    T extract(List<ClickHouseColumn> columns, Iterable<ClickHouseRecord> records) throws ReflectiveOperationException;
}
