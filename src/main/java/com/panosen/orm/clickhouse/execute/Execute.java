package com.panosen.orm.clickhouse.execute;

import com.clickhouse.client.ClickHouseNode;

public abstract class Execute<T> {

    public abstract T execute(ClickHouseNode node) throws Exception;
}

