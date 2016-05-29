package com.plls.os.dedublicated.server.chunking;

import org.rabinfingerprint.fingerprint.RabinFingerprintLongWindowed;
import org.rabinfingerprint.polynomial.Polynomial;

public class ThemadRabinHashWrapper implements WindowHash {

    RabinFingerprintLongWindowed window;

    public ThemadRabinHashWrapper(){
        int window_size = 256;
        Polynomial polynomial = Polynomial.createIrreducible(10);
        // Create a windowed fingerprint object with a window size of 48 bytes.
        window = new RabinFingerprintLongWindowed(polynomial, window_size);
    }

    public void pushByte(byte b) {
        window.pushByte(b);
    }

    public long getHash() {
        return window.getFingerprintLong();
    }

    public void cleanHash() {
        window.reset();
    }
}
