package com.plls.os.dedublicated.chunking;

import com.amazonaws.services.dynamodbv2.xspec.M;
import com.plls.os.dedublicated.CircularByteBuffer;

public class MyRabin implements WindowHash {
    private final long window_size;
    long mod;
    long current_hash = 0;
    long mult;
    long mult_pow_window;
    long current_len = 0;
    CircularByteBuffer circularByteBuffer;
    public MyRabin(long mod, long mult, long window_sie){
        this.mod = mod;
        this.mult = mult;
        this.window_size = window_sie;
        this.mult_pow_window = powMult(mult, mod, window_sie);
        this.circularByteBuffer = new CircularByteBuffer((int) window_sie);
    }

    private long powMult(long a,long mod, long n){
        if (n == 0) {
            return 1;
        }
        long temp = a;
        while (n > 1) {
            temp = (temp * a) % mod;
            n--;
        }
        return temp;
    }

    @Override
    public void pushByte(byte b) {
        current_hash = (current_hash*mult + b) % mod;
        if ( current_len >= window_size){
            byte byte_to_remove = circularByteBuffer.get((int) (current_len-window_size));
            current_hash = current_hash - byte_to_remove*mult_pow_window;
            current_hash = (current_hash%mod + mod)%mod;
        }
        circularByteBuffer.push(b);
        current_len++;
    }

    @Override
    public long getHash() {
        return current_hash;
    }

    @Override
    public void cleanHash() {
        current_len = 0;
        current_hash = 0;
        circularByteBuffer.clean();
    }
}
