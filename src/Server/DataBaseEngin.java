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
 * ʹ������map�ֱ�洢ʵ������database��config
 * 
 * Ĭ�����ݿ�������ʱ��ʹ�õ���root���ݿ�
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
			setUsingDataBase((DataBase) databases.get("root"));// Ĭ��ʹ��root���ݿ�

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �����õ����ݿ�ĸ�Ŀ¼�����������������������ʹ�õ����ݿ�
	private void initDataBasesEngin() throws URISyntaxException {
		loadProperty(Flag.DBEngin);
		Properties p = (Properties) config.get(Flag.DBEngin);
		String dbRootPath = (String) p.get("root");
		File databases = new File(dbRootPath);
		// ����
		/*
		 * System.out.println("databases���ڴ�����" + databases.exists());
		 * System.out.println("���ݿ��������ڵĸ�Ŀ¼    " + databases.getAbsolutePath());
		 */
		File[] temp = databases.listFiles();
		for (File file : temp) {
			// ���Զ�ȡ������
			DataBase database = new DataBase(file.getAbsolutePath(),file.getName(), false);
			// ����
			/*
			 * System.out.println("·��" + file.getAbsolutePath());
			 * System.out.println("�����һ�����ݿ⣬������"+file.getName());
			 */this.databases.put(file.getName(), database);
		}

	}

	/***
	 * 
	 * load properties�����ļ� ���洢��config map�� name ��Flag�������л��
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
		// ����
		/*
		 * System.out.println("·����" + file); System.out.println("������" +
		 * file.exists());
		 */
		if (!file.exists()) {
			file.mkdirs();
			databases.put(dataBasesName,
					new DataBase(path, dataBasesName, true));
		} else
			System.out.println("���ݿ��Ѿ�������");
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
