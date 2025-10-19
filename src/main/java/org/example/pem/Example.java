package org.example.pem;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.spec.DSAPrivateKeySpec;

public class Example {

    static void main() {
        PrivateKey privateKey = null;
        PublicKey publicKey = null;

        // let's encode a cryptographic object!
        PEMEncoder pemEncoder = PEMEncoder.of();

        // this returns PEM text in a byte array
        byte[] privateKeyPem = pemEncoder.encode(privateKey);

        // this returns PEM text in a String
        String keyPairPem = pemEncoder.encodeToString(new KeyPair(publicKey, privateKey));

        // this returns encrypted PEM text
        String password = "java-first-java-always";
        String pem = pemEncoder.withEncryption(password.toCharArray()).encodeToString(privateKey);

        // let's decode a cryptographic object!
        PEMDecoder pemDecoder = PEMDecoder.of();

        // this returns a DEREncodable, so we need to pattern-match
//        switch (pemDecoder.decode(pem)) {
//            case PublicKey publicKey -> null;
//            case PrivateKey privateKey -> null;
//            default -> throw new IllegalArgumentException("Unsupported cryptographic object");
//        }

        // alternatively, if you know the type of the encoded cryptographic object in advance:
        PrivateKey key = pemDecoder.decode(pem, PrivateKey.class);

        // this decodes an encrypted cryptographic object
        PrivateKey decryptedkey = pemDecoder.withDecryption(password.toCharArray()).decode(pem, PrivateKey.class);
    }
}
