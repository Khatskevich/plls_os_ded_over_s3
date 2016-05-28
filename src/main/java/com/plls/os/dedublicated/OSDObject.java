package com.plls.os.dedublicated;

import java.util.*;

public class OSDObject {
    static public class ChunkDescription{
        public ChunkDescription() {
        };
        public long start;
        public long length;
        public String hash;

        @Override
        public String toString() {
            return "Start : " + start + " Length = " + length + " hash = " + hash;
        }
    }
    long representativeId = 0;
    String representativeHash = null;

    public byte[] getData() {
        return data;
    }

    byte[] data = null;
    private TreeMap<Long, ChunkDescription> chunkDescriptions= new TreeMap<Long, ChunkDescription>();

    public void setNewChunkDescription(ChunkDescription cd){
        chunkDescriptions.put(cd.start,cd);
    }

    public OSDObject(byte[] data){
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        if (data != null){
            result.append("Length = ").append(data.length).append("\n");
        }
        if( chunkDescriptions!=null){
            result.append("Chunk descriptions:\n");
            for ( ChunkDescription cd : chunkDescriptions.values()){
                result.append(cd);
                result.append("\n");
            }
        }
        return result.toString();
    }
}
