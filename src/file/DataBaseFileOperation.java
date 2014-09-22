package file;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class DataBaseFileOperation {

	/***
	 * 
	 * false :文件夹已经存在 true :文件夹可以创建
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
	 * true :文件存在 false :文件不存在
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
	 * 根据给出的path创建相应的文件
	 * 
	 * ***/
	public static void createFile(String path) throws IOException,
			IllegalAccessException {
		if (isDirExists(path)) {
			throw new IllegalAccessException("文件已经存在");
		} else {
			File file = new File(path);
			file.createNewFile();
		}
	}

	
	
	public static void main(String[] args) {
		try {
			createFile("c://1.txt");
		} catch (IllegalAccessException | IOException e) {
			System.out.println("错误");
			e.printStackTrace();
		}
	}
}