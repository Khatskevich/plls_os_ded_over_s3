package com.plls.os.dedublicated.server.chunking;

public class Chunker {
    static long MAGIC_HASH = 100;
    byte[] data;
    WindowHash hash;
    int current_position = 0;

    public Chunker(byte[] data, WindowHash hash){
        this.data = data;
        this.hash = hash;
    }
    public long getNextChunkEnd(){
        if (current_position == data.length){
            return 0;
        }
        int start = current_position;
        while( current_position < data.length && hash.getHash() != MAGIC_HASH){
            hash.pushByte(data[current_position]);
            current_position++;
        }
        hash.cleanHash();
        return current_position;
    }
}
