package org.sparkera.nft.utils;

import java.math.BigInteger;

public class EggUtil {
    public static Egg fromBigIntVersion0(BigInteger rawData) {

        int type = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        Egg egg = new Egg();
        egg.setTypeId(type);
        egg.setVersion(0);
        return egg;
    }
}
