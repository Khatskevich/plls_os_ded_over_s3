package com.plls.os.dedublicated.server;

import com.plls.os.dedublicated.server.data.OSDChunkedObject;
import com.plls.os.dedublicated.server.meta.MetaServer;
import com.plls.os.dedublicated.server.storage.StorageServer;

public class Server {
    MetaServer ms;
    StorageServer ss;
    Server(MetaServer ms, StorageServer ss){
    }
    OSDChunkedObject getFileByName(String name){
      return null;
    }
}
