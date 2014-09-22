package Server.praise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.MapUtil;

public class CVSPraise implements FilePraise {

	String[] fields;
	Map tabledata;
	File file;

	public CVSPraise(File file) {
		this.file = file;
	}

	@Override
	public Map praise() {
		try {
			tabledata = new HashMap();
			// 正常
			/*
			 * System.out.println("表的名字是      " + file.getName());
			 * System.out.println("表所在的路径      " + file.getAbsolutePath());
			 */
			fields = util.IOUtil.praisheader(new FileInputStream(file));// 有序的
			for (int i = 0; i < fields.length; i++) {
				System.out.println("表的字段是      " + fields[i]);
				tabledata.put(fields[i], new ArrayList());
			}
			praiseBody();
		} catch (IOException e) {
			System.out.println("数据读取错误");
			e.printStackTrace();
		}
		return tabledata;
	}

	private void praiseBody() throws IOException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		String[] arrs = null;
		while ((line = br.readLine()) != null) {
			arrs = line.split(" ");
			for (int i = 0; i < arrs.length; i++) {
				ArrayList temp = (ArrayList) tabledata.get(fields[i]);
				temp.add(arrs[i]);
			}
		}
		System.out.println("答应tabledata中的数据");
	}

	public String[] getFields() {
		return fields;
	}

	public Map getTabledata() {
		return tabledata;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public void setTabledata(Map tabledata) {
		this.tabledata = tabledata;
	}
}
