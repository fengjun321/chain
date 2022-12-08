package df.sd;

import java.util.List;
import java.util.stream.Collectors;

public class Rarity {

	int rarity;
	String rarityString;
	int availabelCount;
	List<Item> rawItems;
	List<Item> items;

	public int getRarity() {
		return rarity;
	}

	public String getRarityString() {
		return rarityString;
	}

	public int getAvailabelCount() {
		return availabelCount;
	}

	public List<Item> getRawItems() {
		return rawItems;
	}

	public Rarity(int rarity, List<Item> itemList) {

		this.rarity = rarity;
		this.rarityString = switch (rarity) {
		case 0 -> "FireworkConstants.RARITY_ORDINARY";
		case 1 -> "FireworkConstants.RARITY_RARE";
		case 2 -> "FireworkConstants.RARITY_EPIC";
		case 3 -> "FireworkConstants.RARITY_LEGENDARY";

		default -> throw new IllegalArgumentException("Unexpected value: " + rarity);
		};

		this.rawItems = itemList;
		this.items = itemList.stream().filter(e -> e.getCount() > 0 && e.getRare().intValue() == rarity).collect(Collectors.toList());
		this.availabelCount = this.items.size();
	}
	
	public List<Item> getItems() {
		return items;
	}

}
