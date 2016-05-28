package com.plls.os.dedublicated.chunking;

import com.plls.os.dedublicated.OSDObject;

import java.util.ArrayList;

public class OSDObjectChunker {
    static public void process(OSDObject obj) throws Exception{
        byte[] data = obj.getData();
        if ( data == null ){
            throw new Exception();
        }
        //Chunker chunker = new Chunker(data,new ThemadRabinHashWrapper());
        Chunker chunker = new Chunker(data,new MyRabin(1333,7,20));
        long start_of_next_chunk;
        long end_of_previous_chunk = 0;
        while ( (start_of_next_chunk = chunker.getNextChunkEnd()) > 0){
            OSDObject.ChunkDescription chunkDescription = new OSDObject.ChunkDescription();
            chunkDescription.start = end_of_previous_chunk;
            chunkDescription.length = start_of_next_chunk - end_of_previous_chunk;
            chunkDescription.hash = MD5Hash.getHash(data, chunkDescription.start, chunkDescription.length);
            obj.setNewChunkDescription(chunkDescription);

            end_of_previous_chunk = start_of_next_chunk;
        }
    }
}
