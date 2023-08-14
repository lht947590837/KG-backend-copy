package com.example.kgsystem.util;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;


public class DruidDataSourceFactory implements DataSourceFactory {
    private Properties prop;
    @Override
    public void setProperties(Properties properties) {
        this.prop =properties;
    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource druid = new DruidDataSource();
        druid.setDriverClassName(this.prop.getProperty("driver"));
        druid.setUrl(this.prop.getProperty("url"));
        druid.setUsername(this.prop.getProperty("username"));
        druid.setPassword(this.prop.getProperty("password"));
        try {
            druid.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druid;
    }
}