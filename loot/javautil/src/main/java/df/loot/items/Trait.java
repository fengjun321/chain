package df.loot.items;

import java.util.List;

public class Trait {
	private String name;
	List<? extends Item> items;
	
	public static final int TYPE_STANDARD=1;
	public static final int TYPE_OPTIONAL=2;
	public static final int TYPE_RANGE=3;
	
	private int type;
	
	
	public Trait(String name, List<? extends Item> items,int typ) {
		super();
		this.name = name;
		this.items = items;
		this.type=typ;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<? extends Item> getItems() {
		return items;
	}
	public void setItems(List<? extends Item> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "Trait [name=" + name + ", items=" + items + "]";
	}
	
	public boolean isStandard() {
		return type==TYPE_STANDARD;
		
	}
	
	public boolean isOptional() {
		return type==TYPE_OPTIONAL;
		
	}
	
	public boolean isRange() {
		return type==TYPE_RANGE;
		
	}
	
	
}
