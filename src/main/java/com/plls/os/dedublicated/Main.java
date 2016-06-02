package com.plls.os.dedublicated;

import com.plls.os.dedublicated.server.Server;
import com.plls.os.dedublicated.server.data.OSDChunkedObject;
import com.plls.os.dedublicated.server.chunking.OSDObjectChunker;
import com.plls.os.dedublicated.server.meta.SingleDatabaseMetaServer;
import com.plls.os.dedublicated.server.storage.StorageOverLocalDiskSimplest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        String filename = "/Users/alexeykhatskevich/Downloads/ojdbc7.jar";
        Path path = null;
        path = Paths.get(filename);
        byte[] data =null;
        if (path != null)
            data = Files.readAllBytes(path);
        OSDChunkedObject osdObject = null;
        try {
            //osdObject = new OSDChunkedObject(filename, data, new OSDObjectChunker());
            //System.out.print(osdObject);
            Server server = new Server(new SingleDatabaseMetaServer(), new StorageOverLocalDiskSimplest());
            //server.saveFile(osdObject);
            OSDChunkedObject osdChunkedObject2 = server.getFileByName(filename);
            System.out.println(osdChunkedObject2);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            SingleDatabaseMetaServer singleDatabaseMetaServer = new SingleDatabaseMetaServer();
//            singleDatabaseMetaServer.addNewFile(null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //UploadObjectSingleOperation.main(null);
    }
}
