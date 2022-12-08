package df.sd;

import java.util.HashMap;
import java.util.Map;

public class Item {

	Integer rare;
	Integer type;
	String typeString;

	Integer camp;
	Integer count;

	public Integer getRare() {
		return rare;
	}

	public void setRare(Integer rare) {
		this.rare = rare;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;

		this.typeString = switch (type) {
		case 0 -> "FireworkConstants.ITEM_TYPE_COMMANDER";
		case 1 -> "FireworkConstants.ITEM_TYPE_SCIENTIST";
		case 2 -> "FireworkConstants.ITEM_TYPE_WARRIOR";
		case 3 -> "FireworkConstants.ITEM_TYPE_TROOPER";
		case 4 -> "FireworkConstants.ITEM_TYPE_MAINWEAPON";
		case 5 -> "FireworkConstants.ITEM_TYPE_SECONDARYWEAPON";
		case 6 -> "FireworkConstants.ITEM_TYPE_HELMAT";
		case 7 -> "FireworkConstants.ITEM_TYPE_BREASTPLATE";
		case 8 -> "FireworkConstants.ITEM_TYPE_GREAVE";
		case 9 -> "FireworkConstants.ITEM_TYPE_SKILL";
		default -> throw new IllegalArgumentException("Unexpected value: " + type);
		};
	}

	public Integer getCamp() {
		return camp;
	}

	public void setCamp(Integer camp) {
		this.camp = camp;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getTypeString() {
		return typeString;
	}

	@Override
	public String toString() {
		return "Item [rare=" + rare + ", type=" + type + ", camp=" + camp + ", count=" + count + "]";
	}

	public static Item of(int camp, int type, int rare, int cnt) {
		Item item = new Item();
		item.setCamp(camp);
		item.setType(type);
		item.setRare(rare);
		item.setCount(cnt);
		return item;
	}

}
