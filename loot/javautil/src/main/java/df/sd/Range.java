package df.sd;

public class Range {
	int low;
	int high;
	String name;
 

	public Range(int low, int high, String name) {
		super();
		this.low = low;
		this.high = high;
		this.name = name;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
