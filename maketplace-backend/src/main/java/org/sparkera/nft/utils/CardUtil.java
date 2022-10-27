package org.sparkera.nft.utils;

import lombok.Data;

import java.math.BigInteger;
@Data
public class CardUtil {
	public static Card fromBigIntVersion0(BigInteger rawData) {
		int version=rawData.and(BigInteger.valueOf(0x3)).intValue();
		if(version!=0) {
			throw new IllegalArgumentException("version field must be 0");
		}
		rawData=rawData.shiftRight(2);
		int mat = rawData.and(BigInteger.valueOf(0x3)).intValue();
		rawData = rawData.shiftRight(2);

		int rarity = rawData.and(BigInteger.valueOf(0x7)).intValue();
		rawData = rawData.shiftRight(3);

		int camp = rawData.and(BigInteger.valueOf(0x3)).intValue();
		rawData = rawData.shiftRight(2);
		
		int releaseVer=rawData.and(BigInteger.valueOf(0xF)).intValue();
		rawData=rawData.shiftRight(4);

		int typ = rawData.and(BigInteger.valueOf(0x7)).intValue();
		if(typ==0) {
			rawData = rawData.shiftRight(3);

			int nameId = rawData.and(BigInteger.valueOf(0xFFFF)).intValue();
			rawData = rawData.shiftRight(16);

			int level = rawData.and(BigInteger.valueOf(0xFF)).intValue();
			rawData = rawData.shiftRight(8);

			int identity = rawData.and(BigInteger.valueOf(0xF)).intValue();
			rawData = rawData.shiftRight(4);
			int occu = rawData.and(BigInteger.valueOf(0xF)).intValue();
			rawData = rawData.shiftRight(4);

			int endurance = rawData.and(BigInteger.valueOf(0xFF)).intValue();
			rawData = rawData.shiftRight(8);

			int strength = rawData.and(BigInteger.valueOf(0xFF)).intValue();
			rawData = rawData.shiftRight(8);

			int agility = rawData.and(BigInteger.valueOf(0xFF)).intValue();
			rawData = rawData.shiftRight(8);

			int armor = rawData.and(BigInteger.valueOf(0xFF)).intValue();
			rawData = rawData.shiftRight(8);

			int crit = rawData.and(BigInteger.valueOf(0xFF)).intValue();
			rawData = rawData.shiftRight(8);

			int psionic = rawData.and(BigInteger.valueOf(0xFF)).intValue();
			rawData = rawData.shiftRight(8);

			int spirit = rawData.and(BigInteger.valueOf(0xFF)).intValue();
			rawData = rawData.shiftRight(8);

			Card card=new Card();
			card.setVersion(version);	
			card.setMaterial(mat);
			card.setRarity(rarity);
			card.setCamp(camp);
			card.setReleaseVersion(releaseVer);
			card.setType(typ);
			card.setNameId(nameId);
			card.setLevel(level);	
			card.setIdentity(identity);
			card.setOccupation(occu);
			card.setEndurance(endurance);
			card.setStrength(strength);
			card.setAgility(agility);
			card.setArmor(armor);
			card.setCrit(crit);
			card.setPsionic(psionic);
			card.setSpirit(spirit);
			return card;
		}else {
			throw new IllegalArgumentException("type !=0  does not implement yet!");
		}
	
	}



	
	
	
	 
	
	


}
