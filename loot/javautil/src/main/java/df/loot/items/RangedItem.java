package df.loot.items;

public class RangedItem extends Item {

	
	private int range;
	private int rangeLow;
	
	public RangedItem(int id, String name, String color, int low, int high,int range,int rangeLow) {
		super(id, name, color, low, high);
		this.range=range;
		this.rangeLow=rangeLow;
	
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getRangeLow() {
		return rangeLow;
	}

	public void setRangeLow(int rangeLow) {
		this.rangeLow = rangeLow;
	}
	
	
	

}
