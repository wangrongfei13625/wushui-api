package com.huaxin.member.unit;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.Security;

public class DESUtil {
    private static String strDefaultKey = "mykey";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes("UTF-8");
        int iLen = arrB.length;

        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public DESUtil() throws Exception {
        this(strDefaultKey);
    }

    public DESUtil(String strKey) throws Exception {
        if (strKey == null)
            return;
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes("UTF-8"));
        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    public String encrypt(String strIn) throws Exception {
//        System.out.println("不加UTF-8的数据:"+strIn.getBytes());
//        System.out.println("加UTF-8的数据:"+strIn.getBytes("utf-8"));
        return byteArr2HexStr(encrypt(strIn.getBytes("UTF-8")));
    }


    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)),"UTF-8");
    }

    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];

        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }

    public static String encode(String key, String str) {
        DESUtil des = null;
        try {
            des = new DESUtil(key);
            return des.encrypt(str);
        } catch (Exception ex) {
        }
        return "";
    }

    public static String decode(String key, String str) {
        DESUtil des = null;
        try {
            des = new DESUtil(key);
            return des.decrypt(str);
        } catch (Exception ex) {
        }
        return "";
    }

    public static void main(String[] args) {
        try {
            System.out.println("加密前的字符：" + "123huaxin");
            System.out.println("加密后的字符：" + DESUtil.encode("huaxin", "中华人民共和国"));
//            System.out.println("解密后的字符：" + DESUtil.decode("lisheng", DESUtil.encode("lisheng", "123456")));
            System.out.println("解密后的字符：" + DESUtil.decode("huaxin","087c6fc60ba896c0c1690b44948513d0"));
//
            System.out.println("加密前的字符：" + "123456");
            System.out.println("加密后的字符：" + DESUtil.encode("hauxin", "123456"));
//            System.out.println("解密后的字符：" + DESUtil.decode("87775236", DESUtil.encode("87775236", "1234567890")));


//            System.out.println("sha加密前的字符：" + "123huaxin");
//             com.huaxin.util.SecurityEncode my = new com.huaxin.util.SecurityEncode();
//            System.out.println("sha加密后的字符：" + my.encode( "123huaxin",com.huaxin.util.SecurityEncode.SHA1 ));

//
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
