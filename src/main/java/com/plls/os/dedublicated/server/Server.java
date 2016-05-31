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
        OSDChunkedObject osdChunkedObject = new OSDChunkedObject("/Users/alexeykhatskevich/Downloads/q.pdf");
        ms.fillFileMetaByName(osdChunkedObject);
        ss.fillChunksData(osdChunkedObject.chunkDescriptions, null);
      return null;
    }

    public void saveFile(OSDChunkedObject osdChunkedObject) throws Exception {
        ms.addNewFile(osdChunkedObject);
        ss.saveChunks(osdChunkedObject.chunkDescriptions,null);
    }
}
