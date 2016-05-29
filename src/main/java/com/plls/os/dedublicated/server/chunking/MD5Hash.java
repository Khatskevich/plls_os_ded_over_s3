package com.plls.os.dedublicated.server.chunking;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash implements CryptoHash{
    public static String getHash(byte[] data, long offset, long len) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data,(int)offset,(int)len);
            byte[] thedigest = md.digest();
            return Hex.encodeHexString(thedigest);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
