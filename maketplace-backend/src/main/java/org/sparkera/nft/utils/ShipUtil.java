package org.sparkera.nft.utils;

import java.math.BigInteger;

public class ShipUtil {
    public static Ship fromBigIntVersion0(BigInteger rawData) {

        int type = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int nameId = rawData.and(BigInteger.valueOf(0xFFFF)).intValue();
        rawData = rawData.shiftRight(16);

        int level = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int duration = rawData.and(BigInteger.valueOf(0xFFFF)).intValue();
        rawData = rawData.shiftRight(16);

        int version = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int basicSlotLimit = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int basicSlotCurrent = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int leaderSlotLimit = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int leaderSlotCurrent = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int SciSlotLimit = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int SciSlotCurrent = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int CommanderSlotLimit = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int CommanderSlotCurre = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int SsoldierSlotLimit = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int SsoldierCurrent = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int CsoldierSlotLimit = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        int CsoldierSlotCurre = rawData.and(BigInteger.valueOf(0xFF)).intValue();
        rawData = rawData.shiftRight(8);

        Ship ship = new Ship();
        ship.setType(type);
        ship.setNameId(nameId);
        ship.setLevel(level);
        ship.setDuration(duration);
        ship.setReleaseVersion(version);
        ship.setBasicLimit(basicSlotLimit);
        ship.setBasicCurrent(basicSlotCurrent);
        ship.setLeaderLimit(leaderSlotLimit);
        ship.setLeaderCurrent(leaderSlotCurrent);
        ship.setScientistLimit(SciSlotLimit);
        ship.setScientistCurrent(SciSlotCurrent);
        ship.setCommanderLimit(CommanderSlotLimit);
        ship.setCommanderCurrent(CommanderSlotCurre);
        ship.setStartSoldierLimit(SsoldierSlotLimit);
        ship.setStartSoldierCurrent(SsoldierCurrent);
        ship.setCommonSoldierLimit(CsoldierSlotLimit);
        ship.setCommonSoldierCurrent(CsoldierSlotCurre);

        ship.setVersion(0);
        return ship;
    }
}
