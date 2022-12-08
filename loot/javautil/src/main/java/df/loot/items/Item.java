package df.loot.items;

public class Item {

	private int id;
	private String name;
	private String color;
	private int low;
	private int high;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color.toUpperCase();
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
	public Item(int id,String name, String color, int low, int high) {
		super();
		this.id=id;
		this.name = name;
		this.color = color.toUpperCase();
		this.low = low;
		this.high = high;
	}
	@Override
	public String toString() {
		return "Item [id="+id+" name=" + name + ", color=" + color + ", low=" + low + ", high=" + high + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
	
}
