package com.plls.os.dedublicated.server.meta;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.plls.os.dedublicated.server.data.OSDChunkedObject;

public class SingleDatabaseMetaServer implements MetaServer {

    private ComboPooledDataSource connectionPool;

    SingleDatabaseMetaServer() throws Exception {
        Class.forName("org.postgresql.Driver");
        connectionPool = new ComboPooledDataSource();
        connectionPool.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver
        connectionPool.setJdbcUrl("jdbc:postgresql://groozin.ru:5432/snowboarders");
        connectionPool.setUser("grooz");
        connectionPool.setPassword("russia66");
        // the settings below are optional -- c3p0 can work with defaults
        connectionPool.setMinPoolSize(5);
        connectionPool.setAcquireIncrement(5);
        connectionPool.setMaxPoolSize(20);
    }

    @Override
    public void fillFileMetaById(OSDChunkedObject obj) {

    }

    @Override
    public void addNewFile(OSDChunkedObject obj) {
        
    }

    @Override
    public void deleteFile(OSDChunkedObject obj) {

    }
}
