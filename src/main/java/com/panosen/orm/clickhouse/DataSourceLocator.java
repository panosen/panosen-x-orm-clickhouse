package com.panosen.orm.clickhouse;

import java.io.IOException;
import java.util.Properties;

public class DataSourceLocator {

    public static DataSource getDataSource(String logicDbName) throws IOException {
        PropertiesLoader propertiesLoader = new PropertiesLoader();

        Properties properties = propertiesLoader.loadDataSourceProperties(logicDbName);

        String url = properties.getProperty("url", "http://localhost:8123");
        String user = properties.getProperty("user", "default");
        String password = properties.getProperty("password", "");

        DataSource dataSource = new DataSource();
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        return dataSource;
    }
}
