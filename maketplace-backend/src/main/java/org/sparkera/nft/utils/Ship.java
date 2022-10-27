package org.sparkera.nft.utils;

import lombok.Data;

@Data
public class Ship {
    private Integer type;
    private Integer nameId;
    private Integer level;
    private Integer duration;
    private Integer releaseVersion;
    private Integer basicLimit;
    private Integer basicCurrent;
    private Integer leaderLimit;
    private Integer leaderCurrent;
    private Integer scientistLimit;
    private Integer scientistCurrent;
    private Integer commanderLimit;
    private Integer commanderCurrent;
    private Integer startSoldierLimit;
    private Integer startSoldierCurrent;
    private Integer commonSoldierLimit;
    private Integer commonSoldierCurrent;

    //这字段不知道什么来的，先放着
    private Integer version;
}
