package org.sparkera.nft.utils;

import org.web3j.utils.Convert;

public class W3ToWeiUtil {
    public static Convert.Unit getUnit(int decimals){
        Convert.Unit rs =null;
        switch (decimals){
            case 0:
                rs = Convert.Unit.WEI;
                break;
            case 3:
                rs = Convert.Unit.KWEI;
                break;
            case 6:
                rs = Convert.Unit.MWEI;
                break;
            case 9:
                rs = Convert.Unit.GWEI;
                break;
            case 12:
                rs = Convert.Unit.SZABO;
                break;
            case 15:
                rs = Convert.Unit.FINNEY;
                break;
            case 18:
                rs = Convert.Unit.ETHER;
                break;
            case 21:
                rs = Convert.Unit.KETHER;
                break;
            case 24:
                rs = Convert.Unit.METHER;
                break;
            case 27:
                rs = Convert.Unit.GETHER;
                break;
            default:
                break;
        }
        return rs;
    }
}
