package com.example.store.security;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {
  private static final SecureRandom RNG = new SecureRandom();
  private static final int ITERATIONS = 120_000;
  private static final int SALT_BYTES = 16;
  private static final int KEY_BITS = 256;

  public static String hash(String password) {
    try {
      byte[] salt = new byte[SALT_BYTES];
      RNG.nextBytes(salt);

      byte[] dk = pbkdf2(password.toCharArray(), salt, ITERATIONS, KEY_BITS);
      return ITERATIONS + ":" + b64(salt) + ":" + b64(dk);
    } catch (Exception e) {
      throw new RuntimeException("Password hashing failed", e);
    }
  }

  public static boolean verify(String password, String stored) {
    try {
      String[] parts = stored.split(":");
      int iterations = Integer.parseInt(parts[0]);
      byte[] salt = b64d(parts[1]);
      byte[] expected = b64d(parts[2]);

      byte[] actual = pbkdf2(password.toCharArray(), salt, iterations, expected.length * 8);
      return constantTimeEquals(expected, actual);
    } catch (Exception e) {
      return false;
    }
  }

  private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyBits) throws Exception {
    PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyBits);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    return skf.generateSecret(spec).getEncoded();
  }

  private static String b64(byte[] b) { return Base64.getEncoder().encodeToString(b); }
  private static byte[] b64d(String s) { return Base64.getDecoder().decode(s); }

  private static boolean constantTimeEquals(byte[] a, byte[] b) {
    if (a.length != b.length) return false;
    int r = 0;
    for (int i = 0; i < a.length; i++) r |= (a[i] ^ b[i]);
    return r == 0;
  }
}
