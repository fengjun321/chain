package df.sd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Main {
	public static void main(String[] args) throws IOException, TemplateException, ParseException {

		// 1、创建个Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		 
		configuration.setNumberFormat("computer");
		String diretoryp_ath = "src/main/resources";
		// 2、设置模板文件所在的路径的目录
		configuration.setDirectoryForTemplateLoading(new File(diretoryp_ath));
		// 3、设置模板文件的字符集
		configuration.setDefaultEncoding("UTF-8");
		// 4、首先创建模板文件，再加载模板文件 模板文件的后缀官方统一的标准是.ftl 其实任何类型都行。
		Template template = configuration.getTemplate("codetpl.ftl");// 可以是<相对路径>，也可以是<绝对路径>
		// 5、创建模板文件需要展示数据的数据集对象，可以使用POJO，也可以使用map 一般是使用map
		Map<String, Object> model = new HashMap<>();
		
		 
		List<Pack> readPack = ExcelRead.readPack();
		List<Item> items=ExcelRead.readItem();
		
		Map<Integer, List<Item>> collect = items.stream().collect(Collectors.groupingBy(Item::getCamp));
		List<Camp> campList=new ArrayList();
		
		collect.forEach( (k,v)->{
			Camp camp=new Camp(Camp.getCampNameByValue(k),v);
			 
			campList.add(camp);
			
			
		});
		Counter counter=new Counter();
	 
		
		model.put("packList", readPack);
		model.put("seed", counter);
		model.put("campList", campList);
		
		
		String pre_file_path = "./src/main/resources";
		// 6、创建一个FileWriter对象 指定生成的静态文件的文件路径及文件名
		// 拼接一个前缀和后缀
		FileWriter writer = new FileWriter(new File(pre_file_path + "/result.html"));
		// 7、调用模板对象的process方法，执行输出文件。
		template.process(model, writer);
		// 8、关闭流
		writer.close();

	}
}
