package df.sd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Camp {
	String campName;
	List<Item> items;
	// rarity list
	List<Rarity> rarities;

	public static String getCampNameByValue(int camp) {
		switch (camp) {
		case Const.CAMP_OMEN:
			return "FireworkConstants.CAMP_OMEN";
		case Const.CAMP_AMDA:
			return "FireworkConstants.CAMP_AMDA";
		case Const.CAMP_EARTHUNION:
			return "FireworkConstants.CAMP_EARTHUNION";
		case Const.CAMP_PROTOSS:
			return "FireworkConstants.CAMP_PROTOSS";

		default:
			throw new IllegalArgumentException("Unexpected value: " + camp);
		}
	}

	public Camp(String campName, List<Item> items) {

		this.campName = campName;
		this.items = items;

		Map<Integer, List<Item>> collect = items.stream().collect(Collectors.groupingBy(Item::getRare));
		List<Rarity> rarityList = new ArrayList<Rarity>();

		collect.forEach((k, v) -> {
			Rarity rarity = new Rarity(k, items);
			rarityList.add(rarity);
		});
		rarities = rarityList;
	}

	public String getCampName() {
		return campName;
	}

	public List<Item> getItems() {
		return items;
	}

	public List<Rarity> getRarities() {
		return rarities;
	}
}
