package com.plls.os.dedublicated.chunking;

import org.rabinfingerprint.fingerprint.RabinFingerprintLongWindowed;
import org.rabinfingerprint.polynomial.Polynomial;

public class ThemadRabinHashWrapper implements WindowHash {

    RabinFingerprintLongWindowed window;

    ThemadRabinHashWrapper(int window_size){
        Polynomial polynomial = Polynomial.createIrreducible(53);
        // Create a windowed fingerprint object with a window size of 48 bytes.
        RabinFingerprintLongWindowed window = new RabinFingerprintLongWindowed(polynomial, window_size);
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
