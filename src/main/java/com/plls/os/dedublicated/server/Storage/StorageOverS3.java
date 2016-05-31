package com.plls.os.dedublicated.server.storage;

import com.plls.os.dedublicated.server.data.Chunk;

import java.util.List;
import java.util.TreeMap;

public class StorageOverS3 implements StorageServer {
    @Override
    public void fillChunksData(TreeMap<Long, Chunk> chunks, Chunk representative) throws Exception {

    }

    @Override
    public void saveChunks(TreeMap<Long, Chunk> chunks, Chunk representative) throws Exception {

    }
}
