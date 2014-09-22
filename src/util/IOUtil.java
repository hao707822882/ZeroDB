package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class IOUtil {

	/*
	 * 获取表的列名
	 * 
	 * fis为表所在的文件
	 * 
	 * *
	 */
	public static String[] praisheader(FileInputStream fis) throws IOException {

		String header = readLine(fis);
		System.out.println("header是" + header);
		String[] fields = header.split(" ");
		return fields;
	}

	/**
	 * 一行一行读取文件，解决读取中文字符时出现乱码
	 * 
	 * 流的关闭顺序：先打开的后关，后打开的先关， 否则有可能出现java.io.IOException: Stream closed异常
	 * 
	 * @throws IOException
	 */
	public static String readLine(FileInputStream fis) throws IOException {
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		line = br.readLine();
		System.out.println("读取的一行" + line);
		br.close();
		isr.close();
		fis.close();
		return line;
	}

	/**
	 * 一行一行写入文件，解决写入中文字符时出现乱码
	 * 
	 * 流的关闭顺序：先打开的后关，后打开的先关， 否则有可能出现java.io.IOException: Stream closed异常
	 * 
	 * @throws IOException
	 */

	public static void writeFile(FileOutputStream fos, String data)
			throws IOException {
		// 写入中文字符时解决中文乱码问题
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(data + "\r\n");
		bw.close();
		osw.close();
		fos.close();
	}

	/****
	 * 
	 * 檢查文件是否存在
	 * 
	 * ***/
	public static boolean CheckIsExistAndCreate(String path) {
		File file = new File(path);
		if (file.exists()) {
			return true;
		} else {
			System.out.println("文件log不存在，需要创建");
			System.out.println("创建的路径是    " + file.getAbsolutePath());
			file.mkdir();
			return true;
		}

	}
}
