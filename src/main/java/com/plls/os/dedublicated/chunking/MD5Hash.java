package com.plls.os.dedublicated.chunking;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash implements CryptoHash{
    public static String getHash(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(data);
            return Hex.encodeHexString(thedigest);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
