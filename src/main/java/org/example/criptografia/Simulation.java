package org.example.criptografia;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.HKDFParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

public class Simulation {

    static void main() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // --- Simula segredo bruto do ECDH ---
        byte[] ecdhSharedSecret = "segredo-bruto-irregular-ecdh-xpto".getBytes(StandardCharsets.UTF_8);
        byte[] salt = new byte[32];
        new SecureRandom().nextBytes(salt);

        KDF kdf = KDF.getInstance("HKDF-SHA256");

        // --- Deriva chave de CRIPTOGRAFIA ---
        AlgorithmParameterSpec specEnc = HKDFParameterSpec.ofExtract()
                .addIKM(ecdhSharedSecret)
                .addSalt(salt)
                .thenExpand("pix:payload:encrypt".getBytes(), 32);

        SecretKey encryptionKey = kdf.deriveKey("AES", specEnc);

        // --- Payload original ---
        String payload = """
                {
                  "txId": "pix-123456",
                  "valor": 1500.00,
                  "chavePix": "fabricio@pix.com.br"
                }
                """;

        System.out.println("ORIGINAL:\n" + payload);

        // --- CRIPTOGRAFIA (AES/GCM) ---
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, encryptionKey);

        byte[] iv          = cipher.getIV(); // GCM gera vetor automaticamente
        byte[] encrypted   = cipher.doFinal(payload.getBytes(StandardCharsets.UTF_8));

        System.out.println("ENCRIPTADO (base64): " +
                java.util.Base64.getEncoder().encodeToString(encrypted));

        // --- DECRIPTOGRAFIA ---
        // Deriva a mesma chave novamente (simula o outro lado — mesmo ikm + salt)
        SecretKey sameEncryptionKey = kdf.deriveKey("AES", HKDFParameterSpec.ofExtract()
                .addIKM(ecdhSharedSecret)
                .addSalt(salt)
                .thenExpand("pix:payload:encrypt".getBytes(), 32));

        Cipher decipher = Cipher.getInstance("AES/GCM/NoPadding");
        decipher.init(Cipher.DECRYPT_MODE, sameEncryptionKey, new GCMParameterSpec(128, iv));

        byte[] decrypted = decipher.doFinal(encrypted);

        System.out.println("DECRIPTADO:\n" + new String(decrypted, StandardCharsets.UTF_8));

        // --- Valida integridade ---
        System.out.println("INTEGRO: " +
                Arrays.equals(payload.getBytes(StandardCharsets.UTF_8), decrypted));
    }
}
