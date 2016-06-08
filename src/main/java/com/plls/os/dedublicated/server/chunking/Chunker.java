package com.plls.os.dedublicated.server.chunking;

public class Chunker {
    private static final int MIN_CHUNK_SIZE = 2048;
    private static final int MAX_CHUNK_SIZE = 5*2048;
    static long MAGIC_HASH = 100;
    byte[] data;
    WindowHash hash;
    int last_chunk_end = 0;
    int new_chunk_size = 0;

    public Chunker(byte[] data, WindowHash hash){
        this.data = data;
        this.hash = hash;
    }
    public long getNextChunkEnd(){
        if (last_chunk_end == data.length){
            return 0;
        }
        new_chunk_size = 0;
        hash.cleanHash();
        while( last_chunk_end + new_chunk_size < data.length &&
                !(new_chunk_size > MIN_CHUNK_SIZE && hash.getHash() == MAGIC_HASH) &&
                new_chunk_size < MAX_CHUNK_SIZE){
            hash.pushByte(data[last_chunk_end + new_chunk_size]);
            new_chunk_size++;
        }
        last_chunk_end += new_chunk_size;
        return last_chunk_end;
    }
}
