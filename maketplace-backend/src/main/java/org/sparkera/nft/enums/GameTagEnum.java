package org.sparkera.nft.enums;

public enum GameTagEnum {
    WALLET(0, "WALLET"),
    SPARKERA_v3(1, "SPARKERA_v3");

    private Integer code;
    private String message;

    GameTagEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
}
