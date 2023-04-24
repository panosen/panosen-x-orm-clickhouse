package com.panosen.orm.clickhouse.extractor;

import com.clickhouse.data.ClickHouseRecord;
import com.panosen.orm.clickhouse.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

public class EntityListExtractor<TEntity> implements Extractor<List<TEntity>> {

    private final Mapper<TEntity> mapper;

    public EntityListExtractor(Mapper<TEntity> mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<TEntity> extract(Iterable<ClickHouseRecord> records) throws ReflectiveOperationException {
        List<TEntity> result = new ArrayList<>();
        for (ClickHouseRecord record : records) {
            result.add(mapper.map(record));
        }
        return result;
    }
}
