package com.panosen.orm.clickhouse;

import com.clickhouse.client.ClickHouseNode;
import com.panosen.codedom.clickhouse.Parameters;
import com.panosen.orm.clickhouse.execute.Execute;
import com.panosen.orm.clickhouse.execute.ExecuteQuery;
import com.panosen.orm.clickhouse.extractor.Extractor;

public class DalClient {

    private final String connectionString;

    public DalClient(DataSource dataSource) {
        this.connectionString = buildConnectionString(dataSource);
    }

    private String buildConnectionString(DataSource dataSource) {
        return new StringBuilder()
                .append(dataSource.getUrl()).append("?")
                .append("user=").append(dataSource.getUser())
                .append("&password=").append(dataSource.getPassword())
                .toString();
    }

    public <TEntity> TEntity query(String sql, Parameters parameters, Extractor<TEntity> extractor)
            throws Exception {
        return doInConnection(new ExecuteQuery<>(sql, parameters, extractor));
    }

    private <T> T doInConnection(Execute<T> action) throws Exception {

        ClickHouseNode node = ClickHouseNode.of(this.connectionString);

        T t = action.execute(node);

        return t;
    }
}
