package Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import bean.Flag;

/***
 * 
 * 使用两个map分别存储实例化的database和config
 * 
 * 默认数据库启动的时候使用的是root数据库
 * 
 * ***/
@SuppressWarnings("all")
public class DataBaseEngin {
	public static Map databases = new HashMap();
	public static Map config = new HashMap();
	private DataBase usingDataBase;

	public DataBaseEngin() {

		try {
			initDataBasesEngin();
			setUsingDataBase((DataBase) databases.get("root"));// 默认使用root数据库

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 从设置的数据库的更目录，遍历获得这个数据引擎可以使用的数据库
	private void initDataBasesEngin() throws URISyntaxException {
		loadProperty(Flag.DBEngin);
		Properties p = (Properties) config.get(Flag.DBEngin);
		String dbRootPath = (String) p.get("root");
		File databases = new File(dbRootPath);
		// 正常
		/*
		 * System.out.println("databases现在存在吗" + databases.exists());
		 * System.out.println("数据库引擎所在的根目录    " + databases.getAbsolutePath());
		 */
		File[] temp = databases.listFiles();
		for (File file : temp) {
			// 可以读取到数据
			DataBase database = new DataBase(file.getAbsolutePath(),file.getName(), false);
			// 正常
			/*
			 * System.out.println("路径" + file.getAbsolutePath());
			 * System.out.println("添加了一个数据库，名字是"+file.getName());
			 */this.databases.put(file.getName(), database);
		}

	}

	/***
	 * 
	 * load properties属性文件 并存储到config map中 name 从Flag常量类中获得
	 * 
	 * @throws URISyntaxException
	 * 
	 * ***/
	private void loadProperty(String name) throws URISyntaxException {
		try {
			System.out.println(name);
			File pFile = new File(name);
			FileInputStream pInStream = new FileInputStream(pFile);
			Properties p = new Properties();
			p.load(pInStream);
			config.put(name, p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createDataBase(String dataBasesName) {
		Properties p = (Properties) config.get(Flag.DBEngin);
		String path = (String) p.get("root");
		File file = new File(path, dataBasesName);
		// 正常
		/*
		 * System.out.println("路径是" + file); System.out.println("存在吗" +
		 * file.exists());
		 */
		if (!file.exists()) {
			file.mkdirs();
			databases.put(dataBasesName,
					new DataBase(path, dataBasesName, true));
		} else
			System.out.println("数据库已经存在了");
	}

	public void changeDB(String name) {
		DataBase db = (DataBase) databases.get(name);
		this.usingDataBase = db;
	}

	public DataBase getDataBase(String name) {
		return (DataBase) databases.get(name);
	}

	public DataBase getUsingDataBase() {
		return usingDataBase;
	}

	public void setUsingDataBase(DataBase usingDataBase) {
		this.usingDataBase = usingDataBase;
	}

}
