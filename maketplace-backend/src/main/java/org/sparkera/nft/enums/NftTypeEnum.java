package org.sparkera.nft.enums;

public enum NftTypeEnum {
    FHC(0, "FHC"),
    HSE(1, "HSE"),
    PSE(2, "PSE"),
    EGSE(3, "EGSE"),
    SHIP(4,"SHIP"),
    EGG(5,"EGG"),
    MBSE(6,"MBSE"),
    ASE(7,"ASE"),
    ISE(8, "ISE");

    private Integer code;
    private String message;

    NftTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static NftTypeEnum getNftTypeEnumByCode(Integer code) {
        for (NftTypeEnum nftType : NftTypeEnum.values()) {
            if (nftType.getCode().equals(code)) {
                return nftType;
            }
        }
        return null;
    }

    public static NftTypeEnum getNftTypeEnumByMessage(String message) {
        for (NftTypeEnum nftType : NftTypeEnum.values()) {
            if (nftType.getMessage().equals(message)) {
                return nftType;
            }
        }
        return null;
    }
}
