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
	 * ��ȡ�������
	 * 
	 * fisΪ�����ڵ��ļ�
	 * 
	 * *
	 */
	public static String[] praisheader(FileInputStream fis) throws IOException {

		String header = readLine(fis);
		System.out.println("header��" + header);
		String[] fields = header.split(" ");
		return fields;
	}

	/**
	 * һ��һ�ж�ȡ�ļ��������ȡ�����ַ�ʱ��������
	 * 
	 * ���Ĺر�˳���ȴ򿪵ĺ�أ���򿪵��ȹأ� �����п��ܳ���java.io.IOException: Stream closed�쳣
	 * 
	 * @throws IOException
	 */
	public static String readLine(FileInputStream fis) throws IOException {
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		line = br.readLine();
		System.out.println("��ȡ��һ��" + line);
		br.close();
		isr.close();
		fis.close();
		return line;
	}

	/**
	 * һ��һ��д���ļ������д�������ַ�ʱ��������
	 * 
	 * ���Ĺر�˳���ȴ򿪵ĺ�أ���򿪵��ȹأ� �����п��ܳ���java.io.IOException: Stream closed�쳣
	 * 
	 * @throws IOException
	 */

	public static void writeFile(FileOutputStream fos, String data)
			throws IOException {
		// д�������ַ�ʱ���������������
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(data + "\r\n");
		bw.close();
		osw.close();
		fos.close();
	}

	/****
	 * 
	 * �z���ļ��Ƿ����
	 * 
	 * ***/
	public static boolean CheckIsExistAndCreate(String path) {
		File file = new File(path);
		if (file.exists()) {
			return true;
		} else {
			System.out.println("�ļ�log�����ڣ���Ҫ����");
			System.out.println("������·����    " + file.getAbsolutePath());
			file.mkdir();
			return true;
		}

	}
}
