package com.plls.os.dedublicated;

import com.plls.os.dedublicated.server.data.OSDChunkedObject;
import com.plls.os.dedublicated.server.chunking.OSDObjectChunker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        String filename = "/Users/alexeykhatskevich/Downloads/q.pdf";
        Path path = null;
        path = Paths.get(filename);
        byte[] data =null;
        if (path != null)
            data = Files.readAllBytes(path);
            OSDChunkedObject osdObject = new OSDChunkedObject(data);
        try {
            OSDObjectChunker.process(osdObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(osdObject);

        //UploadObjectSingleOperation.main(null);
    }
}
