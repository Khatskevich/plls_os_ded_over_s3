package com.plls.os.dedublicated.server.storage;

import com.plls.os.dedublicated.server.data.Chunk;

import java.util.List;

public interface StorageServer {
    List<Chunk> getChunksByIds(List<Long> ids);
    void saveChunks(List<Chunk> chunks);
}
