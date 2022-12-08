package df.sd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pack {
	String packName;
	String methodName;
	Double pGold;
	Double pSilver;
	Double pCopper;

	Double pLegendary;
	Double pEpic;
	Double pRare;
	Double pOrdinary;
	
	public List<Range> getMaterialRange(){
		List<Range> rangeList=new ArrayList<Range>();
		int low=0;
		int hi=low+(int)(pGold*1000);
		rangeList.add(new Range(low, hi, "FireworkConstants.MAT_GOLD"));
		
		low=hi;
		hi=low+(int)(pSilver*1000);
		rangeList.add(new Range(low, hi,"FireworkConstants.MAT_SILVER"));
		
		low=hi;
		hi=low+(int)(pCopper*1000);
		rangeList.add(new Range(low, hi,"FireworkConstants.MAT_COPPER"));
		return rangeList;
	}

	
	public List<Range> getRarityRange(){
		List<Range> rangeList=new ArrayList<Range>();
		int low=0;
		int hi=low+(int)(pLegendary*1000);
		rangeList.add(new Range(low, hi,"FireworkConstants.RARITY_LEGENDARY"));
		
		low=hi;
		hi=low+(int)(pEpic*1000);
		rangeList.add(new Range(low, hi,"FireworkConstants.RARITY_EPIC"));
		
		low=hi;
		hi=low+(int)(pRare*1000);
		rangeList.add(new Range(low, hi,"FireworkConstants.RARITY_RARE"));
		
		low=hi;
		hi=low+(int)(pOrdinary*1000);
		rangeList.add(new Range(low, hi,"FireworkConstants.RARITY_ORDINARY"));
		return rangeList;
	}
	
	
	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public Double getpGold() {
		return pGold;
	}

	public void setpGold(Double pGold) {
		this.pGold = pGold;
	}

	public Double getpSilver() {
		return pSilver;
	}

	public void setpSilver(Double pSilver) {
		this.pSilver = pSilver;
	}

	public Double getpCopper() {
		return pCopper;
	}

	public void setpCopper(Double pCopper) {
		this.pCopper = pCopper;
	}

	public Double getpLegendary() {
		return pLegendary;
	}

	public void setpLegendary(Double pLegendary) {
		this.pLegendary = pLegendary;
	}

	public Double getpEpic() {
		return pEpic;
	}

	public void setpEpic(Double pEpic) {
		this.pEpic = pEpic;
	}

	public Double getpRare() {
		return pRare;
	}

	public void setpRare(Double pRare) {
		this.pRare = pRare;
	}

	public Double getpOrdinary() {
		return pOrdinary;
	}

	public void setpOrdinary(Double pOrdinary) {
		this.pOrdinary = pOrdinary;
	}

	public String getMethodName() {
		return methodName;
	}


	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	@Override
	public String toString() {
		return "Pack [packName=" + packName + ", pGold=" + pGold + ", pSilver=" + pSilver + ", pCopper=" + pCopper
				+ ", pLegendary=" + pLegendary + ", pEpic=" + pEpic + ", pRare=" + pRare + ", pOrdinary=" + pOrdinary
				+ "]";
	}

}
