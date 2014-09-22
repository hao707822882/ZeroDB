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
 * Cache CacheListener �������������ʽ���ڵģ�ͨ�^set��ӹ��ܣ�
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
		System.out.println("���������ݿ���" + databasepath);
		// �������ݿ�
	}

	public String getDBName() {
		return this.DBName;
	}

	// ��ִ����updata����ʱ��Ҫ����һ���Եļ�飬���»���
	private void daUpdate() {
		for (int i = 0; i < cacheListener.size(); i++) {
			CacheListener listener = (CacheListener) cacheListener.get(i);
			listener.upData(queryCache);
		}
	}

	public void addCacheListener(CacheListener listener)
			throws IllegalAccessException {
		if (queryCache == null)
			throw new IllegalAccessException("û�����û��棬�޷����listener");
		else
			this.cacheListener.add(listener);
	}

	// ���������ݿ�զ�����Ѿ����� �����������췽��
	public DataBase(String databasepath, String dataBaseName, boolean isNewDb) {
		this.isNewDB = isNewDb;
		this.databasepath = databasepath;
		this.DBName = dataBaseName;
		System.out.println("databasepath    " + databasepath);
		if (isNewDB == false) {
			System.out.println("�����ϵ����ݿ�");
			initDataBase(databasepath);
		} else {
			System.out.println("�����µ����ݿ�");
		}
	}

	/***
	 * 
	 * attr ���е��� table �������
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
			System.out.println("�������·���ǣ�" + file.getAbsolutePath());
			System.out.println("���ļ������ɹ�����" + issuccsee);
			FileOutputStream stream = new FileOutputStream(file);
			util.IOUtil.writeFile(stream, write);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initDataBase(String databaseFile) {
		System.out.println("initDataBase�е�·��    " + databaseFile);
		File[] data = new File(databaseFile).listFiles();
		if (data.length == 0) {
			return;
		} else {
			for (File file : data) {
				System.out.println("�����ڵ��ļ�·��      " + file.getAbsolutePath());
				System.out.println("������� &&&&&     " + file.getName());

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
