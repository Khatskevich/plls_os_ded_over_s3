package com.plls.os.dedublicated.server.data;

public class Chunk {
    public long id;
    public long size;
    public byte[] data;
    public String hash;
    public long combined_object_id;

    @Override
    public String toString() {
        return " Size = " + size + " id = " + id +" Hash = " + hash;
    }
}
