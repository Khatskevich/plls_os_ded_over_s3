package com.plls.os.dedublicated.server.storage;

import com.plls.os.dedublicated.server.data.Chunk;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class StorageOverLocalDiskBinned implements StorageServer {
    private static String base_dir = "valera/";

    static class SimilarSizeChunksBlock{
        private ByteBuffer tempBuffer;
        private int chunk_size;
        private FileChannel file_meta;
        private FileChannel file_data;
        private Map<Integer,Integer> numberOffsetMap = new HashMap();
        public SimilarSizeChunksBlock(Chunk representative, int chunk_size, String base_dir) throws Exception{
            tempBuffer = ByteBuffer.allocate(chunk_size);
            this.chunk_size = SimilarSizeChunksBlock.roundSize(chunk_size);
            Path metaPath = ( new File( base_dir + chunk_size + "meta" ) ).toPath();
            Path dataPath = ( new File( base_dir + chunk_size ) ).toPath();

            file_meta = FileChannel.open( metaPath, StandardOpenOption.READ,
                    StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            file_data = FileChannel.open( dataPath, StandardOpenOption.READ,
                    StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            ByteBuffer bb = ByteBuffer.allocate((int)file_meta.size());
            file_meta.read(bb);
            bb.flip();
            for( int i = 0; i<bb.capacity()/8; i ++) {
                numberOffsetMap.put(bb.getInt(),bb.getInt());
            }
        }

        @Override
        protected void finalize() throws Throwable {
            file_meta.close();
            file_data.close();
            super.finalize();
        }

        public void fillChunkData(Chunk chunk) throws Exception {
            if (chunk==null) throw new Exception();
            Integer chunk_offset = numberOffsetMap.get(chunk.id);
            if ( chunk_offset == null) throw new Exception();
            tempBuffer.clear();
            file_data.read(tempBuffer,chunk_offset);
            chunk.data = new byte[(int) chunk.size];
            tempBuffer.get(chunk.data, 0, (int) chunk.size);
        }

        public void saveChunk(Chunk chunk) throws Exception {
            if (chunk == null) throw new Exception();
            Integer chunk_offset = numberOffsetMap.get(chunk.id);
            if (chunk_offset != null) return;
            chunk_offset = numberOffsetMap.size() * chunk_size;
            file_data.position(chunk_offset);
            tempBuffer.clear();
            tempBuffer.put(chunk.data);
            tempBuffer.flip();
            file_data.write(tempBuffer);
            tempBuffer.clear();
            tempBuffer.putInt((int) chunk.id);
            tempBuffer.putInt(chunk_offset);
            tempBuffer.flip();
            file_meta.position( numberOffsetMap.size()*8);
            file_meta.write(tempBuffer);
        }

        public static int roundSize(int size){
            int pice = 256;
            return (size/pice + 1)*pice;
        }
    }
    @Override
    public void fillChunksData(TreeMap<Long,Chunk> chunks, Chunk representative) throws Exception {
        createDirectoryForRepresentative(representative);
        Map<Integer,SimilarSizeChunksBlock> similarSizeChunksBlocks = new HashMap<>();
        for( Chunk c : chunks.values() ){
            int rounded_size = SimilarSizeChunksBlock.roundSize((int) c.size);
            SimilarSizeChunksBlock similarSizeChunksBlock = similarSizeChunksBlocks.get(rounded_size);
            if (similarSizeChunksBlock == null){
                similarSizeChunksBlock = new SimilarSizeChunksBlock(representative,rounded_size,getPathForRepresentative(representative));
                similarSizeChunksBlocks.put(rounded_size,similarSizeChunksBlock);
            }
            similarSizeChunksBlock.fillChunkData(c);
        }
    }

    @Override
    public void saveChunks(TreeMap<Long,Chunk> chunks, Chunk representative) throws Exception {
        createDirectoryForRepresentative(representative);
        Map<Integer,SimilarSizeChunksBlock> similarSizeChunksBlocks = new HashMap<>();
        for( Chunk c : chunks.values() ){
            int rounded_size = SimilarSizeChunksBlock.roundSize((int) c.size);
            SimilarSizeChunksBlock similarSizeChunksBlock = similarSizeChunksBlocks.get(rounded_size);
            if (similarSizeChunksBlock == null){
                similarSizeChunksBlock = new SimilarSizeChunksBlock(representative,rounded_size,getPathForRepresentative(representative));
                similarSizeChunksBlocks.put(rounded_size,similarSizeChunksBlock);
            }
            similarSizeChunksBlock.saveChunk(c);
        }
    }
    private void createDirectoryForRepresentative(Chunk representative){
        File theDir = new File(getPathForRepresentative(representative));
        theDir.mkdir();
    }
    private String getPathForRepresentative(Chunk representative){
        return base_dir + representative.id + "/";
    }
}
