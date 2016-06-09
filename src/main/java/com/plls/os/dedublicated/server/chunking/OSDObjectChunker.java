package com.plls.os.dedublicated.server.chunking;

import com.plls.os.dedublicated.server.data.Chunk;

import java.util.TreeMap;

public class OSDObjectChunker {
    public TreeMap<Long, Chunk> process(byte[] data) throws Exception{
        if ( data == null ){
            throw new Exception();
        }
        TreeMap<Long, Chunk> chunkDescriptions= new TreeMap<Long, Chunk>();
        //Chunker chunker = new Chunker(data,new ThemadRabinHashWrapper());
        Chunker chunker = new Chunker(data,new MyRabin(4094,3,50));
        long start_of_next_chunk;
        long end_of_previous_chunk = 0;
        while ( (start_of_next_chunk = chunker.getNextChunkEnd()) > 0){
            Chunk chunk = new Chunk();
            chunk.size = start_of_next_chunk - end_of_previous_chunk;
            chunk.hash = MD5Hash.getHash(data, end_of_previous_chunk, chunk.size);
            byte[] d = new byte[(int) chunk.size];
            System.arraycopy(data,(int)end_of_previous_chunk, d,0,(int)chunk.size);
            chunk.data = d;
            chunkDescriptions.put(end_of_previous_chunk,chunk);
            end_of_previous_chunk = start_of_next_chunk;
        }
        return chunkDescriptions;
    }
}
