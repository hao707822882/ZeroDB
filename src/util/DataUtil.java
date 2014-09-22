package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
	private final static SimpleDateFormat df = new SimpleDateFormat();

	// ���Ĭ�ϵ�ʱ����
	public static String getFormateData() {
		java.util.Date data = new Date();
		return df.format(data);
	}

	//��������Զ����ʱ����
	public static String getPersionalFormat(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		java.util.Date data = new Date();
		return sdf.format(data);
	}

	
	
	public static void main(String[] args) {
		System.out.println(getPersionalFormat("yyyy:MM:dd"));
	}
}
