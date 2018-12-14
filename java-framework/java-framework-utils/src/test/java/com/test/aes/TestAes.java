package com.test.aes;


import com.java.utils.code.AES_CBC_PKCS5;
import com.java.utils.code.AES_X_923;
import com.java.utils.code.AesEncoding;
import com.java.utils.code.BytesUtility;

public class TestAes {


    private static String str ="F2 E9 6B FA BC 4E A4 47 82 A9 A6 7E 87 E2 58 50";
    private static String secret_key="65 FD ED EF 89 CD D5 B1 A5 EC 58 69 47 51 12 32";
    private static String iv="58 65 54 85 12 0F 79 89 FF A2 C5 B2 E6 66 D5 A9";


    public static void main(String[] args) throws Exception {

        checkAESPKCS5();

    }

    private static void checkAESPKCS5()throws Exception{
        String keys  = "abcdefghijklmnop";
        System.out.println(keys);
        String viStr = "abcdefghijkl1234";;
        System.out.println(viStr);
        String str = "hello12345123454312321512d";
        String encrypt = AES_CBC_PKCS5.encrypt(str,keys,viStr,AesEncoding.hex);
        System.out.println(encrypt);

        String decrypt = AES_CBC_PKCS5.decrypt(encrypt,keys,viStr,AesEncoding.hex);
        System.out.println(decrypt.equals(str));

    }

    private static void checkAES1()throws Exception{
        String keys = "abcdefghij123456";
        byte[] keybytes = keys.getBytes();
        byte[] ivsbytes = keys.getBytes();

        String str = "hello1234512345";
        System.out.println(str.length());
        byte[] desStr =  AES_X_923.encryptX923(keybytes,ivsbytes,str);
        System.out.println(BytesUtility.bytesToHexString(desStr));
        System.out.println(desStr.length);

        byte[] result = AES_X_923.descryptX923(keybytes,ivsbytes,desStr);

        String res = new String(result);

        System.out.println(res);

    }

    private static void checkAES()throws Exception{
        String[] keys = secret_key.split(" ");
        byte[] keybytes = BytesUtility.hexStringToHexbytes(keys);

        String[] ivs = iv.split(" ");
        byte[] ivsbytes = BytesUtility.hexStringToHexbytes(ivs);

        String str = "hello1234";
        System.out.println(str.length());
        byte[] desStr =  AES_X_923.encryptX923(keybytes,ivsbytes,str);
        System.out.println(BytesUtility.bytesToHexString(desStr));
        System.out.println(desStr.length);

        //String[] strs = str.split(" ");
        //byte[] desStr = AESUtil.hexStringToHexbytes(strs);

        byte[] result = AES_X_923.descryptX923(keybytes,ivsbytes,desStr);

        String res = new String(result);

        System.out.println(res);
    }





}
