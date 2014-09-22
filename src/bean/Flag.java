package bean;

public class Flag {

	public static final int register = 0;

	public static final int result = 1;

	public static final int select = 2;
	public static final int update = 3;
	public static final int delect = 4;
	public static final int insert = 5;

	public static final int choosedatabase = 6;
	public static final int showtables = 7;
	public static final int showdatabases = 8;

	public static final int createdatabase = 9;
	public static final int createtable = 10;

	public static final int discribe = 11;// 展示表的详细列信息

	public static final String DBEngin = "src/config/DataBaseEngin.properties";
	
	
	
	
	public static String flagToSstring(int num) {
		String str = "";
		switch (num) {
		case 0:
			str = "register";
			break;

		case 1:
			str = "result";
			break;

		case 2:
			str = "select";
			break;

		case 3:
			str = "update";
			break;

		case 4:
			str = "delect";
			break;

		case 5:
			str = "insert";
			break;

		case 6:
			str = "choosedatabase";
			break;

		case 7:
			str = "showtables";
			break;

		case 8:
			str = "showdatabases";
			break;

		case 9:
			str = "createdatabase";
			break;

		case 10:
			str = "createtable";
			break;

		case 11:
			str = "discribe";
			break;

		default:
			break;
		}
		return str;
	}
}
