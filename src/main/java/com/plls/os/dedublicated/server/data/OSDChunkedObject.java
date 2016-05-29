package com.plls.os.dedublicated.server.data;

import com.plls.os.dedublicated.server.chunking.OSDObjectChunker;

import java.util.*;

public class OSDChunkedObject {
    long id;
    long representativeId = 0;
    String representativeHash = null;

    public byte[] getData() {
        return null;
    }

    private TreeMap<Long, Chunk> chunkDescriptions= new TreeMap<Long, Chunk>();

    public OSDChunkedObject(byte[] data, OSDObjectChunker osdObjectChunker) throws Exception {
        this.chunkDescriptions = osdObjectChunker.process(data);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        if( chunkDescriptions!=null){
            result.append("Chunk descriptions:\n");
            for ( Chunk cd : chunkDescriptions.values()){
                result.append(cd);
                result.append("\n");
            }
        }
        return result.toString();
    }
}
