package org.sparkera.nft.utils;

import java.math.BigInteger;

public class Card {
    private Integer version;
    private Integer material;

    private Integer rarity;

    private Integer camp;
    private Integer releaseVersion;

    private Integer type;

    private Integer nameId;

    private Integer level;

    private Integer identity;

    private Integer occupation;

    private Integer endurance;

    private Integer strength;

    private Integer agility;

    private Integer armor;

    private Integer crit;

    private Integer psionic;

    private Integer spirit;

    public BigInteger toBigIntegerVersion0() {
        if(version!=0) {
            throw new IllegalArgumentException("version field must be 0");
        }
        BigInteger data=BigInteger.ZERO;
        data=data.or(BigInteger.valueOf(0x3 & version));

        BigInteger mat= BigInteger.valueOf(0x3 & material).shiftLeft(2);
        data=data.or(mat);

        BigInteger rar= BigInteger.valueOf(0x7 & rarity).shiftLeft(4);
        data=data.or(rar);

        BigInteger cam= BigInteger.valueOf(0x3 & camp).shiftLeft(7);
        data=data.or(cam);

        if(type!=0) {
            throw new IllegalArgumentException("type !=0 does not implement yet");
        }
        BigInteger releasV= BigInteger.valueOf(0xF & releaseVersion).shiftLeft(9);
        data=data.or(releasV);


        BigInteger typ= BigInteger.valueOf(0x7 & type).shiftLeft(13);
        data=data.or(typ);

        BigInteger naid= BigInteger.valueOf(0xFFFF & nameId).shiftLeft(16);
        data=data.or(naid);

        BigInteger blevel= BigInteger.valueOf(0xFF & level).shiftLeft(32);
        data=data.or(blevel);

        BigInteger bident= BigInteger.valueOf(0xF & identity).shiftLeft(40);
        data=data.or(bident);

        BigInteger bOccu= BigInteger.valueOf(0xF & occupation).shiftLeft(44);
        data=data.or(bOccu);

        BigInteger bEndurance= BigInteger.valueOf(0xFF & endurance).shiftLeft(48);
        data=data.or(bEndurance);

        BigInteger bStren= BigInteger.valueOf(0xFF & strength).shiftLeft(56);
        data=data.or(bStren);

        BigInteger bAgility= BigInteger.valueOf(0xFF & agility).shiftLeft(64);
        data=data.or(bAgility);

        BigInteger bArmor= BigInteger.valueOf(0xFF & armor).shiftLeft(72);
        data=data.or(bArmor);

        BigInteger bCrit= BigInteger.valueOf(0xFF & crit).shiftLeft(80);
        data=data.or(bCrit);

        BigInteger bPsionic= BigInteger.valueOf(0xFF & psionic).shiftLeft(88);
        data=data.or(bPsionic);

        BigInteger bSpirit= BigInteger.valueOf(0xFF & spirit).shiftLeft(96);
        data=data.or(bSpirit);

        return data;
    }


    @Override
    public String toString() {
        return "Card [version=" + version + ", material=" + material + ", rarity=" + rarity + ", camp=" + camp + ", releaseVersion=" + releaseVersion + ", type=" + type + ", nameId=" + nameId + ", level=" + level + ", identity=" + identity
                + ", occupation=" + occupation + ", endurance=" + endurance + ", strength=" + strength + ", agility=" + agility + ", armor=" + armor + ", crit=" + crit + ", psionic=" + psionic + ", spirit=" + spirit + "]";
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return version;
    }

    public void setReleaseVersion(Integer releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public Integer getReleaseVersion() {
        return releaseVersion;
    }

    public Integer getMaterial() {
        return material;
    }

    public void setMaterial(Integer material) {
        this.material = material;
    }

    public Integer getRarity() {
        return rarity;
    }

    public void setRarity(Integer rarity) {
        this.rarity = rarity;
    }

    public Integer getCamp() {
        return camp;
    }

    public void setCamp(Integer camp) {
        this.camp = camp;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNameId() {
        return nameId;
    }

    public void setNameId(Integer nameId) {
        this.nameId = nameId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public Integer getOccupation() {
        return occupation;
    }

    public void setOccupation(Integer occupation) {
        this.occupation = occupation;
    }

    public Integer getEndurance() {
        return endurance;
    }

    public void setEndurance(Integer endurance) {
        this.endurance = endurance;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getAgility() {
        return agility;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

    public Integer getArmor() {
        return armor;
    }

    public void setArmor(Integer armor) {
        this.armor = armor;
    }

    public Integer getCrit() {
        return crit;
    }

    public void setCrit(Integer crit) {
        this.crit = crit;
    }

    public Integer getPsionic() {
        return psionic;
    }

    public void setPsionic(Integer psionic) {
        this.psionic = psionic;
    }

    public Integer getSpirit() {
        return spirit;
    }

    public void setSpirit(Integer spirit) {
        this.spirit = spirit;
    }

}
