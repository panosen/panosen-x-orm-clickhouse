package com.panosen.orm.clickhouse.extractor;

import com.clickhouse.data.ClickHouseRecord;

public interface Extractor<T> {
    T extract(Iterable<ClickHouseRecord> records) throws ReflectiveOperationException;
}
