package com.plls.os.dedublicated.server.data;

import com.plls.os.dedublicated.server.chunking.OSDObjectChunker;

import java.util.*;

public class OSDChunkedObject {
    public long oject_id;
    public long combined_object_id;
    String representativeHash = null;
    public String name;
    public byte[] getData() {
        return null;
    }

    public TreeMap<Long, Chunk> chunkDescriptions= new TreeMap<Long, Chunk>();

    public OSDChunkedObject(String name, byte[] data, OSDObjectChunker osdObjectChunker) throws Exception {
        this.chunkDescriptions = osdObjectChunker.process(data);
        this.name = name;
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

    public String getRepresentativeHash(){
        if ( chunkDescriptions==null){
            return null;
        }
        String minimal_hash = null;
        for(Chunk ch : chunkDescriptions.values()){
            if( minimal_hash == null || minimal_hash.compareTo(ch.hash) > 0){
                minimal_hash = ch.hash;
            }
        }
        return minimal_hash;
    }
}
