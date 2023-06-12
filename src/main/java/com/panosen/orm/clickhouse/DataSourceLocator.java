package com.panosen.orm.clickhouse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceLocator {

    public static DataSource getDataSource(String logicDbName, Properties extraProperties) throws IOException {
        PropertiesLoader propertiesLoader = new PropertiesLoader();

        Properties builtInProperties = propertiesLoader.loadDataSourceProperties(logicDbName);

        Properties properties = combine(builtInProperties, extraProperties);

        String url = properties.getProperty("url", "http://localhost:8123");
        String user = properties.getProperty("user", "default");
        String password = properties.getProperty("password", "");

        DataSource dataSource = new DataSource();
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        return dataSource;
    }

    private static Properties combine(Properties... args) {
        Properties properties = new Properties();
        for (Properties arg : args) {
            properties.putAll(arg);
        }
        return properties;
    }
}
