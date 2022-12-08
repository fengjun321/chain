package df.loot.items;

import java.util.List;

public class OptionalItem extends Item {

	private List<String> subList;
	
	public OptionalItem(int id, String name, String color, int low, int high) {
		super(id, name, color, low, high);
	}

	public List<String> getSubList() {
		return subList;
	}

	public void setSubList(List<String> subList) {
		this.subList = subList;
	}
	
	
	
	

}
