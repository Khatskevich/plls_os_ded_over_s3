package com.plls.os.dedublicated.server.chunking;

public interface WindowHash {
    void pushByte(byte b);
    long getHash();
    void cleanHash();
}
