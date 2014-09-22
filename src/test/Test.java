package test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import parser.SqlParserUtil;

public class Test {
	/** */
	/**
	 * 单句Sql解析器制造工厂
	 * 
	 * @author 赵朝峰
	 *
	 * @since 2013-6-10
	 * @version 1.00
	 */
	public static void main1(String[] args) {
		// 程序的入口
		String testSql = "select c1,c2,c3     from    t1,t2 where condi3=3 "
				+ "\n" + "    or condi4=5 order by o1,o2";
		SqlParserUtil test = new SqlParserUtil();
		String result = test.getParsedSql(testSql);
		System.out.println(result);

	}

	public static void main2(String[] args) {
		ArrayList list = new ArrayList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(2, 3);
		System.out.println(list.get(2));
		System.out.println(list.get(3));
	}

	public static void main(String[] args) {
		java.util.Date data = new Date();
		SimpleDateFormat df = new SimpleDateFormat();
		System.out.println(df.format(data));
	}
}