package com.panosen.orm.clickhouse.extractor;

import com.clickhouse.data.ClickHouseRecord;
import com.panosen.orm.clickhouse.mapper.Mapper;

import java.util.Iterator;

public class EntityExtractor<TEntity> implements Extractor<TEntity> {

    private final Mapper<TEntity> mapper;

    public EntityExtractor(Mapper<TEntity> mapper) {
        this.mapper = mapper;
    }

    @Override
    public TEntity extract(Iterable<ClickHouseRecord> records) throws ReflectiveOperationException {
        if (records == null) {
            return null;
        }
        Iterator<ClickHouseRecord> iterator = records.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        return mapper.map(iterator.next());
    }
}
