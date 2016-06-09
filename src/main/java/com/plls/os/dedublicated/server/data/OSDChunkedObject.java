package com.plls.os.dedublicated.server.data;

import com.plls.os.dedublicated.server.chunking.OSDObjectChunker;

import java.nio.ByteBuffer;
import java.util.TreeMap;

public class OSDChunkedObject {
    public long oject_id;
    public long combined_object_id;
    String representativeHash = null;
    public String name;

    public byte[] getData() {
        return null;
    }

    public TreeMap<Long, Chunk> chunkDescriptions = new TreeMap<Long, Chunk>();

    public OSDChunkedObject(String name) {
        this.name = name;
    }

    public OSDChunkedObject(String name, byte[] data, OSDObjectChunker osdObjectChunker) throws Exception {
        this.chunkDescriptions = osdObjectChunker.process(data);
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        if (chunkDescriptions != null) {
            result.append("Chunk descriptions:\n");
            for (Chunk cd : chunkDescriptions.values()) {
                result.append(cd);
                result.append("\n");
            }
        }
        return result.toString();
    }

    public ByteBuffer toByteBuffer() {
        if (chunkDescriptions == null)
            return null;
        ByteBuffer bb = ByteBuffer.allocate((int) this.size());
        for (long start : chunkDescriptions.keySet()) {
            bb.put(chunkDescriptions.get(start).data);
        }
        bb.flip();
        return bb;
    }

    public long size() {
        if (chunkDescriptions == null)
            return 0;
        long last_start = chunkDescriptions.lastKey();
        return last_start + chunkDescriptions.get(last_start).size;
    }

    public String getRepresentativeHash() {
        Chunk representative = getRepresentativeChunk();
        if (representative == null)
            return null;
        return representative.hash;
    }

    public Chunk getRepresentativeChunk() {
        if (chunkDescriptions == null) {
            return null;
        }
        Chunk representative = null;
        for (Chunk ch : chunkDescriptions.values()) {
            if (representative == null || representative.hash.compareTo(ch.hash) > 0) {
                representative = ch;
            }
        }
        return representative;
    }
}
