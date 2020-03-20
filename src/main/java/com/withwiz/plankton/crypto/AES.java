package com.withwiz.plankton.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Crypto library: AES supported.<BR>
 * Created by uni4love on 2012. 09. 16..
 */
public class AES {
    /**
     * mode: AES/ECB/PKCS5Padding
     */
    private static final String MODE_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";

    /**
     * mode: AES/CBC/NoPadding
     */
    private static final String MODE_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";

    /**
     * mode
     */
    private static String transfomation = MODE_CBC_PKCS5PADDING;

    /**
     * transfomation을 설정한다.
     *
     * @param transfomation transfomation string
     */
    public static void setTransfomation(String transfomation) {
        AES.transfomation = transfomation;
    }


    /**
     * 암호화
     *
     * @param plainText 원본 문자열 데이터
     * @param key       암호화 key
     * @param iv        initial vector
     * @return 암호화 문자열 데이터
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] encrypt(byte[] plainText, byte[] key, byte[] iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        if (plainText == null)
            return null;

        // 128bit AES 키 생성기
        // KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        // keyGen.init(128);
        // keySpac 생성
        // MessageDigest md = MessageDigest.getInstance("SHA-256");
        // md.update(key);
        // byte[] rawKey = md.digest();
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        // 암호화 모듈 인스턴스 생성 및 초기화
        Cipher cipher = Cipher.getInstance(transfomation);
        if (transfomation.equals(AES.MODE_CBC_PKCS5PADDING)) {
            if (iv == null) {
                iv = new byte[cipher.getBlockSize()];
            }
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParamSpec);
        } else if (transfomation.equals(AES.MODE_ECB_PKCS5PADDING)) {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        }
        // 암호화
        return cipher.doFinal(plainText);

    }


    /**
     * 암호화
     *
     * @param plainText 원본 문자열 데이터
     * @param key       암호화 key
     * @param iv        initial vector
     * @return 암호화 문자열 데이터
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public static byte[] encrypt(byte[] plainText, String key, byte[] iv)
            throws NoSuchPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {
        return encrypt(plainText, key.getBytes(), iv);
    }

    /**
     * 암호화
     *
     * @param plainText 원본 문자열 데이터
     * @param key       암호화 key
     * @return 암호화 문자열 데이터
     * @throws Exception
     */
    public static byte[] encrypt(byte[] plainText, byte[] key) throws Exception {
        return encrypt(plainText, key, null);
    }

    /**
     *
     * @param cipherText 암호화된 문자열 데이터
     * @param key       암호화 key
     * @param iv        initial vector
     * @return 복호화된 문자열 데이터
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] decrypt(byte[] cipherText, byte[] key, byte[] iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        if (cipherText == null)
            return null;

        // KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        // // 128bit 키 생성기
        // keyGen.init(128);
        // // keySpac 생성
        // MessageDigest md = MessageDigest.getInstance("MD5");
        // md.update(key.getBytes());
        // byte[] rawKey = md.digest();
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        // 암호화 모듈 인스턴스 생성 및 초기화
        Cipher cipher = Cipher.getInstance(transfomation);
        if (transfomation.equals(AES.MODE_CBC_PKCS5PADDING)) {
            if (iv == null) {
                iv = new byte[cipher.getBlockSize()];
            }
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParamSpec);
        } else if (transfomation.equals(AES.MODE_ECB_PKCS5PADDING)) {
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        }
        // 복호화
        return cipher.doFinal(cipherText);
    }

    /**
     * 복호화
     *
     * @param cipherText 암호화된 문자열 데이터
     * @return 복호화된 문자열 데이터
     * @throws Exception
     */
    public static byte[] decrypt(byte[] cipherText, byte[] key)
            throws Exception {
        return decrypt(cipherText, key, null);
    }

    /**
     * test main
     *
     * @param args
     */
    public static void main(String[] args) {
        AES.setTransfomation(AES.MODE_CBC_PKCS5PADDING);
        String testString = "test";
        String key = "1234567890123456";
        try {
            // encrypt
            byte[] encrypted = AES.encrypt(testString.getBytes(),
                    key.getBytes());
            System.out.println("encrypted string = " + new String(encrypted));
            // decrypt
            byte[] decrypted = AES.decrypt(encrypted, key.getBytes());
            System.out.println("decrypted string = " + new String(decrypted));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
