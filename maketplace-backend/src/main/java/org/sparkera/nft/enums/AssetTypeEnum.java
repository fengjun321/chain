package org.sparkera.nft.enums;

public enum AssetTypeEnum {
    ERC20(0, "ERC20"),
    ERC721(1, "ERC721"),
    ERC1155(2, "ERC1155");

    private Integer code;
    private String message;

    AssetTypeEnum(Integer code, String message) {
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
