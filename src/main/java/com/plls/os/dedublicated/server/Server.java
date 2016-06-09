package com.plls.os.dedublicated.server;

import com.plls.os.dedublicated.server.data.OSDChunkedObject;
import com.plls.os.dedublicated.server.meta.MetaServer;
import com.plls.os.dedublicated.server.storage.StorageServer;

public class Server {
    MetaServer ms;
    StorageServer ss;
    public Server(MetaServer ms, StorageServer ss){
        this.ms = ms;
        this.ss = ss;
    }
    public OSDChunkedObject getFileByName(String name) throws Exception {
        OSDChunkedObject osdChunkedObject = new OSDChunkedObject(name);
        ms.fillFileMetaByName(osdChunkedObject);
        ss.fillChunksData(osdChunkedObject.chunkDescriptions, osdChunkedObject.getRepresentativeChunk());
      return osdChunkedObject;
    }

    public void saveFile(OSDChunkedObject osdChunkedObject) throws Exception {
        ms.addNewFile(osdChunkedObject);
        ss.saveChunks(osdChunkedObject.chunkDescriptions,osdChunkedObject.getRepresentativeChunk());
    }
}
