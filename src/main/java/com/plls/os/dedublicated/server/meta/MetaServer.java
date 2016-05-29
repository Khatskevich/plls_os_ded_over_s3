package com.plls.os.dedublicated.server.meta;

import com.plls.os.dedublicated.server.data.OSDChunkedObject;

import java.sql.SQLException;

public interface MetaServer {
    void fillFileMetaById(OSDChunkedObject obj);
    void addNewFile(OSDChunkedObject obj) throws SQLException, Exception;
    void deleteFile(OSDChunkedObject obj);
}
