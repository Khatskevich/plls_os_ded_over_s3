package com.plls.os.dedublicated.chunking;

import java.util.Arrays;

public class Chunker {
    static long MAGIC_HASH = 100;
    byte[] data;
    WindowHash hash;
    int current_position = 0;

    public Chunker(byte[] data, WindowHash hash){
        this.data = data;
        this.hash = hash;
    }
    public byte[] getNextChunk(){
        if (current_position == data.length){
            return null;
        }
        int start = current_position;
        while( current_position < data.length && hash.getHash() != MAGIC_HASH){
            hash.pushByte(data[current_position++]);
        }
        byte[] next_chunk = Arrays.copyOfRange(data, start,current_position);
        return next_chunk;
    }
}
