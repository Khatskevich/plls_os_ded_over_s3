package com.plls.os.dedublicated.chunking;

public interface WindowHash {
    void pushByte(byte b);
    long getHash();
    void cleanHash();
}
