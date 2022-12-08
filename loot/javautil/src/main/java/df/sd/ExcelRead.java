package df.sd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import static df.sd.Const.*;

public class ExcelRead {

	public static List<Pack> readPack() throws FileNotFoundException, IOException, ParseException {
		try (InputStream inp = new FileInputStream("src/main/resources/预售池概率.xlsx")) {

			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);

			var readSheet0 = readSheet0(sheet);
			return readSheet0;

		}
	}
	
	
	public static  List<Item> readItem() throws FileNotFoundException, IOException, ParseException {
		try (InputStream inp = new FileInputStream("src/main/resources/预售池概率.xlsx")) {

			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(1);
			var readSheet1 = readSheet1(sheet);
			return readSheet1;

		}
	}

	

	//
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		try (InputStream inp = new FileInputStream("src/main/resources/预售池概率.xlsx")) {

			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);

			var readSheet0 = readSheet0(sheet);
			readSheet0.forEach(System.out::println);

			sheet = wb.getSheetAt(1);
			var readSheet1 = readSheet1(sheet);

			System.out.println(readSheet1);
		}

	}

	private static List<Item> readSheet1(Sheet sheet) throws ParseException {

		List<Item> r = new ArrayList<Item>();
		// omen
		r.addAll(readItemRow(CAMP_OMEN, RARITY_ORDINARY, 2, sheet));
		r.addAll(readItemRow(CAMP_OMEN, RARITY_RARE, 3, sheet));
		r.addAll(readItemRow(CAMP_OMEN, RARITY_EPIC, 4, sheet));
		r.addAll(readItemRow(CAMP_OMEN, RARITY_LEGENDARY, 5, sheet));

		// adam
		r.addAll(readItemRow(CAMP_AMDA, RARITY_ORDINARY, 12, sheet));
		r.addAll(readItemRow(CAMP_AMDA, RARITY_RARE, 13, sheet));
		r.addAll(readItemRow(CAMP_AMDA, RARITY_EPIC, 14, sheet));
		r.addAll(readItemRow(CAMP_AMDA, RARITY_LEGENDARY, 15, sheet));

		// earth
		r.addAll(readItemRow(CAMP_EARTHUNION, RARITY_ORDINARY, 23, sheet));
		r.addAll(readItemRow(CAMP_EARTHUNION, RARITY_RARE, 24, sheet));
		r.addAll(readItemRow(CAMP_EARTHUNION, RARITY_EPIC, 25, sheet));
		r.addAll(readItemRow(CAMP_EARTHUNION, RARITY_LEGENDARY, 26, sheet));

		// protoss
		r.addAll(readItemRow(CAMP_PROTOSS, RARITY_ORDINARY, 35, sheet));
		r.addAll(readItemRow(CAMP_PROTOSS, RARITY_RARE, 36, sheet));
		r.addAll(readItemRow(CAMP_PROTOSS, RARITY_EPIC, 37, sheet));
		r.addAll(readItemRow(CAMP_PROTOSS, RARITY_LEGENDARY, 38, sheet));

		return r;
	}

	private static List<Item> readItemRow(int camp, int rare, int rowIdx, Sheet s) throws ParseException {
		List<Item> r = new ArrayList<Item>();
		Row row = s.getRow(rowIdx);
		int commanderCount = getCellValueAsInt(row.getCell(1));
		r.add(Item.of(camp, ITEM_TYPE_COMMANDER, rare, commanderCount));

		int scienst = getCellValueAsInt(row.getCell(2));
		r.add(Item.of(camp, ITEM_TYPE_SCIENTIST, rare, scienst));

		int warror = getCellValueAsInt(row.getCell(3));
		r.add(Item.of(camp, ITEM_TYPE_WARRIOR, rare, warror));

		int trooper = getCellValueAsInt(row.getCell(4));
		r.add(Item.of(camp, ITEM_TYPE_TROOPER, rare, trooper));

		int head = getCellValueAsInt(row.getCell(5));
		r.add(Item.of(camp, ITEM_TYPE_HELMAT, rare, head));

		int chest = getCellValueAsInt(row.getCell(6));
		r.add(Item.of(camp, ITEM_TYPE_BREASTPLATE, rare, chest));

		int leg = getCellValueAsInt(row.getCell(7));
		r.add(Item.of(camp, ITEM_TYPE_GREAVE, rare, leg));

		int mainWeapon = getCellValueAsInt(row.getCell(8));
		r.add(Item.of(camp, ITEM_TYPE_MAINWEAPON, rare, mainWeapon));

		int secWeapon = getCellValueAsInt(row.getCell(9));
		r.add(Item.of(camp, ITEM_TYPE_SECONDARYWEAPON, rare, secWeapon));

		int skill = getCellValueAsInt(row.getCell(10));
		r.add(Item.of(camp, ITEM_TYPE_skill, rare, skill));

		return r;

	}

	private static List<Pack> readSheet0(Sheet sheet) throws ParseException {
		// TODO Auto-generated method stub
		List<Pack> r = new ArrayList<Pack>();

		for (int i = 1; i < 5; i++) {
			Row row = sheet.getRow(i);
			var gold = row.getCell(2);
			var silver = row.getCell(3);
			var copper = row.getCell(4);

			var le = row.getCell(5);
			var ep = row.getCell(6);
			var rare = row.getCell(7);
			var od = row.getCell(8);

			Pack pack = new Pack();
			pack.setPackName(row.getCell(0).getStringCellValue());
			pack.setpGold(getCellValueAsDouble(gold));
			pack.setpSilver(getCellValueAsDouble(silver));
			pack.setpCopper(getCellValueAsDouble(copper));

			pack.setpLegendary(getCellValueAsDouble(le));
			pack.setpEpic(getCellValueAsDouble(ep));
			pack.setpRare(getCellValueAsDouble(rare));
			pack.setpOrdinary(getCellValueAsDouble(od));

			r.add(pack);
		}

		return r;

	}

	public static double getCellValueAsDouble(Cell cell) throws ParseException {
		if (cell.getCellType() == CellType.STRING) {
			DecimalFormat fmt = new DecimalFormat("0.0#%");
			return fmt.parse(cell.getStringCellValue()).doubleValue();
		}
		if (cell.getCellType() == CellType.NUMERIC) {
			return cell.getNumericCellValue();
		}
		throw new RuntimeException("Wrong type of cell");
	}

	public static int getCellValueAsInt(Cell cell) throws ParseException {
		if (cell.getCellType() == CellType.STRING) {
			if (StringUtils.isEmpty(cell.getStringCellValue())) {
				return 0;
			}
			return Integer.parseInt(cell.getStringCellValue());
		}
		if (cell.getCellType() == CellType.NUMERIC) {
			return (int) cell.getNumericCellValue();
		}
		if (cell.getCellType() == CellType.BLANK) {
			return 0;
		}

		throw new RuntimeException("Wrong type of cell");
	}
}
