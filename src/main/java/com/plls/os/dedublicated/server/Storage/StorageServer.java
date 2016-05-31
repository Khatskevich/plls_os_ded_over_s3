package com.plls.os.dedublicated.server.storage;

import com.plls.os.dedublicated.server.data.Chunk;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.TreeMap;

public interface StorageServer {
    void fillChunksData(TreeMap<Long,Chunk> chunks, Chunk representative) throws Exception;
    void saveChunks(TreeMap<Long,Chunk> chunks, Chunk representative) throws Exception;
}
