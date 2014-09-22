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
		System.out.println("loghome在    " + loghome);
		IOUtil.CheckIsExistAndCreate(loghome);
	}

	// operation的格式 select;数据部分
	public void writeLog(String operation) {
		String day = DataUtil.getPersionalFormat("dd");
		String riqi = DataUtil.getPersionalFormat("yyyy-MM");
		String fileName = riqi + "-" + day;// log文件的名字 按照天进行划分
		System.out.println("日期是    " + fileName);

		File root = new File(logHome);
		System.out.println("根目录   " + root.getAbsolutePath());
		FileOutputStream outputStream;

		File logfile = new File(root.getAbsolutePath() + "/" + fileName);
		System.out.println("文件要创建的路径是   " + logfile.getAbsolutePath());
		if (logfile.exists()) {
			try {
				System.out.println("文件存在，直接写入数据");
				outputStream = new FileOutputStream(logfile, true);
				IOUtil.writeFile(outputStream, operation);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			boolean issuccess;
			try {
				issuccess = logfile.createNewFile();
				System.out.println("创建成功了吗    " + issuccess);
				outputStream = new FileOutputStream(logfile);
				IOUtil.writeFile(outputStream, operation);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
