package file;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class DataBaseFileOperation {

	/***
	 * 
	 * false :�ļ����Ѿ����� true :�ļ��п��Դ���
	 * 
	 * ****/
	public static boolean createDataBaseDir(String rootpath)
			throws URISyntaxException {
		File path = new File(rootpath);
		if (path.exists())
			return false;
		else {
			path.mkdirs();
			return true;
		}
	}

	/****
	 * 
	 * true :�ļ����� false :�ļ�������
	 * 
	 * ****/
	public static boolean isDirExists(String path) {
		File file = new File(path);
		if (file.exists())
			return true;
		else
			return false;
	}

	/***
	 * 
	 * ���ݸ�����path������Ӧ���ļ�
	 * 
	 * ***/
	public static void createFile(String path) throws IOException,
			IllegalAccessException {
		if (isDirExists(path)) {
			throw new IllegalAccessException("�ļ��Ѿ�����");
		} else {
			File file = new File(path);
			file.createNewFile();
		}
	}

	
	
	public static void main(String[] args) {
		try {
			createFile("c://1.txt");
		} catch (IllegalAccessException | IOException e) {
			System.out.println("����");
			e.printStackTrace();
		}
	}
}