package com.plls.os.dedublicated.server.meta;

import com.plls.os.dedublicated.server.data.OSDChunkedObject;

import java.sql.SQLException;

public interface MetaServer {
    public void fillFileMetaByName(OSDChunkedObject obj) throws Exception;
    void addNewFile(OSDChunkedObject obj) throws Exception;
    void deleteFile(OSDChunkedObject obj);
}
