package Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import sun.nio.ch.IOUtil;
import memory.Cache;
import memory.CacheListener;
import Server.praise.CVSPraise;
import bean.Flag;

/*****
 * 
 * Cache CacheListener 都是以组件的形式存在的，通過set添加功能，
 * 
 * 
 * *******/
@SuppressWarnings("all")
public class DataBase {

	public String databasepath;
	private Cache queryCache;
	private List cacheListener;
	private Map tables = new HashMap<String, Table>();
	public static String DBName;
	public boolean isNewDB = false;

	public DataBase(String[] table, String dbname) {
		this.databasepath = ((Properties) (DataBaseEngin.config
				.get(Flag.DBEngin))).get("root") + "/" + dbname;
		System.out.println("创建的数据库是" + databasepath);
		// 创建数据库
	}

	public String getDBName() {
		return this.DBName;
	}

	// 当执行了updata语句的时候，要进行一致性的检查，更新缓存
	private void daUpdate() {
		for (int i = 0; i < cacheListener.size(); i++) {
			CacheListener listener = (CacheListener) cacheListener.get(i);
			listener.upData(queryCache);
		}
	}

	public void addCacheListener(CacheListener listener)
			throws IllegalAccessException {
		if (queryCache == null)
			throw new IllegalAccessException("没有设置缓存，无法添加listener");
		else
			this.cacheListener.add(listener);
	}

	// 如果这个数据库咋本地已经有了 则调用这个构造方法
	public DataBase(String databasepath, String dataBaseName, boolean isNewDb) {
		this.isNewDB = isNewDb;
		this.databasepath = databasepath;
		this.DBName = dataBaseName;
		System.out.println("databasepath    " + databasepath);
		if (isNewDB == false) {
			System.out.println("我是老的数据库");
			initDataBase(databasepath);
		} else {
			System.out.println("我是新的数据库");
		}
	}

	/***
	 * 
	 * attr 表中的列 table 表的名字
	 * 
	 * **/
	public void createTable(String[] attr, String tablename) {
		Map data = new HashMap();
		String write = "";
		for (int i = 0; i < attr.length; i++) {
			if (i == attr.length - 1)
				write = write + attr[i];
			else
				write = write + attr[i] + " ";

			data.put(attr[i], new ArrayList());
		}
		Table table = new Table(attr, data);
		tables.put(tablename, data);
		try {
			String str = databasepath + "/" + tablename;
			File file = new File(str);

			boolean issuccsee = file.createNewFile();
			System.out.println("创建表的路径是：" + file.getAbsolutePath());
			System.out.println("表文件创建成功了吗：" + issuccsee);
			FileOutputStream stream = new FileOutputStream(file);
			util.IOUtil.writeFile(stream, write);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initDataBase(String databaseFile) {
		System.out.println("initDataBase中的路径    " + databaseFile);
		File[] data = new File(databaseFile).listFiles();
		if (data.length == 0) {
			return;
		} else {
			for (File file : data) {
				System.out.println("表所在的文件路勁      " + file.getAbsolutePath());
				System.out.println("表的名字 &&&&&     " + file.getName());

				tables.put(file.getName(), new Table(new CVSPraise(file)));
			}
		}
	}

	public Cache getQueryCache() {
		return queryCache;
	}

	public List getCacheListener() {
		return cacheListener;
	}

	public Map getTables() {
		return tables;
	}

	public void setQueryCache(Cache queryCache) {
		this.queryCache = queryCache;
	}

	public void setCacheListener(List cacheListener) {
		this.cacheListener = cacheListener;
	}

	public void setTables(Map tables) {
		this.tables = tables;
	}

}
