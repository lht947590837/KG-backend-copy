package com.example.kgsystem.util;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
public class DatabaseUtils {
    private static final String configpath = "databaseConfig.xml";

    public static SqlSession getSession(String db){
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(configpath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is,db.equals("mysql")?"mysql":"neo4j");
        SqlSession session = factory.openSession();
        return session;
    }

    public static void closeSession(SqlSession session){
        session.close();
    }
}