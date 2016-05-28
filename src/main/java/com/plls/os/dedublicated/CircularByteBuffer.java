package com.plls.os.dedublicated;

public class CircularByteBuffer <T> {
    private byte[] data;
    private int position;
    private int size;
    public CircularByteBuffer(int size){
        this.size = size;
        data = new byte[size];
    }
    public void push(byte b){
        set(b,position);
        position++;
    }
    public void set(byte b, int position){
        data[position%size] = b;
    }
    public byte get(int position){
        return data[position%size];
    }
    public void clean(){
        position = 0;
    }

}
