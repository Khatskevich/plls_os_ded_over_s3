package com.plls.os.dedublicated.server.meta;

import com.plls.os.dedublicated.server.data.OSDChunkedObject;

public interface MetaServer {
    void fillFileMetaById(OSDChunkedObject obj);
    void addNewFile(OSDChunkedObject obj);
    void deleteFile(OSDChunkedObject obj);
}
