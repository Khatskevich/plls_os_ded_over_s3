package com.plls.os.dedublicated.server.storage;

import com.plls.os.dedublicated.server.data.Chunk;

import java.util.List;

public class StorageOverS3 implements StorageServer {
    @Override
    public List<Chunk> getChunksByIds(List<Long> ids) {
        return null;
    }

    @Override
    public void saveChunks(List<Chunk> chunks) {
    }
}
