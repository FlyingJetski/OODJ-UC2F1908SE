package model;

import model.objects.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class Authentication {
    public static User login(String username, String password)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NullPointerException, UnsupportedEncodingException {
        for (User user: User.users) {
            if ((toHashString(username, password.toCharArray())).equals(user.getPassword())) {
                return user;
            }
        }
        throw new NullPointerException();
    }

    public static String toHashString(String username, char[] password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iteration = 1000;
        int keyLength = 64 * 8;
        byte[] salt = username.getBytes();
        PBEKeySpec spec = new PBEKeySpec(password, salt, iteration, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return Arrays.toString(hash);
    }
}
