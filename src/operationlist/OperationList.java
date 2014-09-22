package operationlist;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import util.DataUtil;
import util.IOUtil;
import Server.DataBaseEngin;
import bean.Flag;

public class OperationList {

	String logHome;

	public OperationList() {
		String loghome = (String) ((Properties) DataBaseEngin.config
				.get(Flag.DBEngin)).get("loghome");
		this.logHome = loghome;
		System.out.println("loghome��    " + loghome);
		IOUtil.CheckIsExistAndCreate(loghome);
	}

	// operation�ĸ�ʽ select;���ݲ���
	public void writeLog(String operation) {
		String day = DataUtil.getPersionalFormat("dd");
		String riqi = DataUtil.getPersionalFormat("yyyy-MM");
		String fileName = riqi + "-" + day;// log�ļ������� ��������л���
		System.out.println("������    " + fileName);

		File root = new File(logHome);
		System.out.println("��Ŀ¼   " + root.getAbsolutePath());
		FileOutputStream outputStream;

		File logfile = new File(root.getAbsolutePath() + "/" + fileName);
		System.out.println("�ļ�Ҫ������·����   " + logfile.getAbsolutePath());
		if (logfile.exists()) {
			try {
				System.out.println("�ļ����ڣ�ֱ��д������");
				outputStream = new FileOutputStream(logfile, true);
				IOUtil.writeFile(outputStream, operation);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			boolean issuccess;
			try {
				issuccess = logfile.createNewFile();
				System.out.println("�����ɹ�����    " + issuccess);
				outputStream = new FileOutputStream(logfile);
				IOUtil.writeFile(outputStream, operation);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
