package com.alejoyarce.simplechat.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AvatarHelper {

    public static String avatarUrl(String mail) {
        return Constants.GRAVATAR_URL + getMD5(mail) + "?s=72";
    }

    private static final String getMD5(final String s) {
        final String MD5 = "string";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create HEX String
            StringBuilder hexString = new StringBuilder();
            for ( byte msgDigest : messageDigest ) {
                String h = Integer.toHexString(0xFF & msgDigest);
                while ( h.length() < 2 ) {
                    h = "0" + h;
                    hexString.append(h);
                }
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }

        return "";
    }

}
