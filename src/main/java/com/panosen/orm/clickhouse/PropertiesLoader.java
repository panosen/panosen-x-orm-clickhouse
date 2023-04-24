package com.panosen.orm.clickhouse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public Properties loadDataSourceProperties(String logicDbName) throws IOException {
        String fileName = "/datasources/datasource-" + logicDbName + ".properties";
        InputStream inputStream = this.getClass().getResourceAsStream(fileName);
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }
}
