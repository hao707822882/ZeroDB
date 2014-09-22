package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
	private final static SimpleDateFormat df = new SimpleDateFormat();

	// 获得默认的时间规格化
	public static String getFormateData() {
		java.util.Date data = new Date();
		return df.format(data);
	}

	//获得满足自定义的时间规格化
	public static String getPersionalFormat(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		java.util.Date data = new Date();
		return sdf.format(data);
	}

	
	
	public static void main(String[] args) {
		System.out.println(getPersionalFormat("yyyy:MM:dd"));
	}
}
