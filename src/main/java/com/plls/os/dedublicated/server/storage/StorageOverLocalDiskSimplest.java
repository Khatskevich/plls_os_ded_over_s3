package com.plls.os.dedublicated.server.storage;

import com.plls.os.dedublicated.server.data.Chunk;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.TreeMap;

public class StorageOverLocalDiskSimplest implements StorageServer {
    @Override
    public void fillChunksData(TreeMap<Long,Chunk> chunks, Chunk representative) throws Exception {
        for(Long start : chunks.keySet() ){
            Chunk c = chunks.get(start);
            FileInputStream fis = new FileInputStream("valera"+c.id);
            byte[] b = new byte[(int) c.size];
            fis.read(b,0, (int) c.size);
            c.data = b;
            fis.close();
        }
    }

    @Override
    public void saveChunks(TreeMap<Long,Chunk> chunks, Chunk representative) throws Exception {
        for( Chunk c : chunks.values() ){
            FileOutputStream fos = new FileOutputStream("valera"+c.id);
            try {

                fos.write(c.data);
            }
            finally {
                fos.close();
            }
        }
    }
}
