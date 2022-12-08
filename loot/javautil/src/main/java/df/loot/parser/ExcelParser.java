package df.loot.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import df.loot.items.Item;
import df.loot.items.OptionalItem;
import df.loot.items.RangedItem;
import df.loot.items.Trait;

public class ExcelParser {

	public static List<Trait> readTrait() throws FileNotFoundException, IOException, ParseException {
		try (InputStream inp = new FileInputStream("src/main/resources/loot20210910.xlsx")) {

			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(1);

			List<Trait> list = new ArrayList<Trait>();
			var CampItems = readStandard(sheet, 2, 4);
			Trait camp = new Trait("Camp", CampItems, Trait.TYPE_STANDARD);
			list.add(camp);

			var occuItems = readStandard(sheet, 7, 9);
			Trait occupation = new Trait("Occupation", occuItems, Trait.TYPE_STANDARD);
			list.add(occupation);

			var level = readRanged(sheet, 17, 4);
			Trait levelTrait = new Trait("Level", level, Trait.TYPE_RANGE);
			list.add(levelTrait);
			
			var specialOp=readOptional(sheet, 22, 1);
			Trait special = new Trait("Special", specialOp, Trait.TYPE_OPTIONAL);
			list.add(special);
			
			
			//Model
			var Model = readStandard(sheet, 24, 25);
			Trait modelTrait = new Trait("Model", Model, Trait.TYPE_STANDARD);
			list.add(modelTrait);
			
			//Star Ship Health
			var health = readRanged(sheet, 50, 4);
			Trait healthTrait = new Trait("Health", health, Trait.TYPE_RANGE);
			list.add(healthTrait);
			
			
			//Star Ship Power
			var power = readRanged(sheet, 55, 4);
			Trait powerTrait = new Trait("Power", power, Trait.TYPE_RANGE);
			list.add(powerTrait);
			
			//ARMOR
			var armor = readRanged(sheet, 60, 4);
			Trait ArmorTrait = new Trait("Armor", armor, Trait.TYPE_RANGE);
			list.add(ArmorTrait);
			
			//
				var SpecialWeapons=readOptional(sheet, 65, 1);
			Trait swTrait = new Trait("SpecialWeapons", SpecialWeapons, Trait.TYPE_OPTIONAL);
			list.add(swTrait);
			
			return list;

		}

	}

	public static List<Item> readStandard(Sheet sheet, int lineStart, int lines) {

		int lineEnds = lineStart + lines;

		int lo = 0;
		int hi = 0;

		int id = 1;

		ArrayList<Item> list = new ArrayList<Item>();

		for (int i = lineStart; i < lineEnds; i++) {
			Row row = sheet.getRow(i);
			var name = row.getCell(2).getStringCellValue();
			var propability = row.getCell(3).getNumericCellValue();
			var color = row.getCell(4).getStringCellValue();
			hi = lo + (int) (propability * 1000);
			Item item = new Item(id++, name, color, lo, hi);
			lo = hi;
			list.add(item);
		}
		return list;

	}

	public static List<RangedItem> readRanged(Sheet sheet, int lineStart, int lines) {

		int lineEnds = lineStart + lines;

		int lo = 0;
		int hi = 0;

		int id = 1;

		ArrayList<RangedItem> list = new ArrayList<RangedItem>();

		for (int i = lineStart; i < lineEnds; i++) {
			Row row = sheet.getRow(i);
			var nameRange = row.getCell(2).getStringCellValue();
			String[] split = nameRange.split("-");

			int rangeLow = Integer.parseInt(split[0]);
			int rangeHigh = Integer.parseInt(split[1]);

			var propability = row.getCell(3).getNumericCellValue();
			var color = row.getCell(4).getStringCellValue();
			hi = lo + (int) (propability * 1000);
			RangedItem item = new RangedItem(id++, null, color, lo, hi, rangeHigh - rangeLow, rangeLow);
			lo = hi;
			list.add(item);
		}
		return list;

	}
	public static List<OptionalItem> readOptional(Sheet sheet, int lineStart, int lines) {

		int lineEnds = lineStart + lines;

		int lo = 0;
		int hi = 0;

		int id = 1;

		ArrayList<OptionalItem> list = new ArrayList<OptionalItem>();

		for (int i = lineStart; i < lineEnds; i++) {
			Row row = sheet.getRow(i);
			var nameRange = row.getCell(2).getStringCellValue();
			String[] split = nameRange.split(",");

			var propability = row.getCell(3).getNumericCellValue();
			var color = row.getCell(4).getStringCellValue();
			hi = lo + (int) (propability * 1000);
			OptionalItem item = new OptionalItem(id++, null, color, lo, hi);
			item.setSubList( Arrays.asList(split)  );
			lo = hi;
			list.add(item);
		}
		return list;

	}
	
	

}
