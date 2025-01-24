package com.example.demo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;

@Service
public class EncriptDecript {

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGORITHM = "AES";
    private static final String secret = "CimbRcp";

    @Autowired
    private UserRepository userRepository;

    public void prepareSecreteKey(String myKey) {
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // Use only the first 128 bits
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing encryption key", e);
        }
    }

    public String encrypt(String strToEncrypt) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting", e);
        }
    }

    public String decrypt(String strToDecrypt) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting", e);
        }
    }

    public void encryptAndSavePassword(Integer userId) {
        // Fetch user by ID
        userRepository.findById(userId).ifPresent(user -> {
            String originalPassword = user.getPassword();
            String encryptedPassword = encrypt(originalPassword);
            user.setPassword(encryptedPassword);
            userRepository.save(user); // Save encrypted username
            System.out.println("Encrypted and saved user password for user ID: " + userId);
        });
    }

    public String decryptPassword(Integer userId) {
        // Fetch user by ID
        return userRepository.findById(userId).map(user -> {
            String encryptedPassword = user.getPassword();
            return decrypt(encryptedPassword); // Decrypt username
        }).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }
}