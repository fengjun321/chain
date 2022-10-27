package org.sparkera.nft.utils;

import org.junit.jupiter.api.Test;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class w3test {
    public static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";

    @Test
    public void testRecoverAddressFromSignature() {

        String signature =
                "0xf86c0b85012a05f2008252089453c5d0177b91101d21e4cbfc53ceb83b32a69ddd88016345785d8a0000801ca05b0680ebe2bd3138246fdb1a8c227fc74d8c51931c410551abfe2a8cfb916cd1a011d8952784c15ed2ade862bd3d75bf15f5483241a1118d5a4ee8eb06dcb68dc1";

        String address = "0x53c5d0177b91101d21e4cbfc53ceb83b32a69ddd";
        String message = "v0G9u7huK4mJb2K1";

        String prefix = PERSONAL_MESSAGE_PREFIX + message.length();
        byte[] msgHash = Hash.sha3((prefix + message).getBytes());

        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }

        Sign.SignatureData sd =
                new Sign.SignatureData(
                        v,
                        (byte[]) Arrays.copyOfRange(signatureBytes, 0, 32),
                        (byte[]) Arrays.copyOfRange(signatureBytes, 32, 64));

        String addressRecovered = null;
        boolean match = false;

        // Iterate for each possible key to recover
        for (int i = 0; i < 4; i++) {
            BigInteger publicKey =
                    Sign.recoverFromSignature(
                            (byte) i,
                            new ECDSASignature(
                                    new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())),
                            msgHash);

            if (publicKey != null) {
                addressRecovered = "0x" + Keys.getAddress(publicKey);

                if (addressRecovered.equals(address)) {
                    match = true;
                    break;
                }
            }
        }

        assertEquals(addressRecovered, (address));
        assertTrue(match);
    }

}
