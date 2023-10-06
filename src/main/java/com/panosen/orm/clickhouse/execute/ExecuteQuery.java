package com.panosen.orm.clickhouse.execute;

import com.clickhouse.client.*;
import com.clickhouse.data.ClickHouseFormat;
import com.panosen.codedom.clickhouse.Parameters;
import com.panosen.orm.clickhouse.extractor.Extractor;

public class ExecuteQuery<T> extends Execute<T> {

    private final Extractor<T> extractor;
    private final String sql;
    private final Parameters parameters;

    public ExecuteQuery(String sql, Parameters parameters, Extractor<T> extractor) {
        this.sql = sql;
        this.parameters = parameters;
        this.extractor = extractor;
    }

    @Override
    public T execute(ClickHouseNode node) throws Exception {

        try (ClickHouseClient client = ClickHouseClient.newInstance(ClickHouseProtocol.HTTP)) {
            ClickHouseRequest<?> clickHouseRequest = client.read(node).format(ClickHouseFormat.TabSeparatedWithNamesAndTypes);
            try (ClickHouseResponse response = clickHouseRequest.query(sql).params(parameters.getValues()).executeAndWait()) {
                T result = extractor.extract(response.getColumns(), response.records());
                return result;
            }
        }
    }
}
