package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import operationlist.OperationList;
import util.DataUtil;
import util.MapUtil;
import bean.Flag;

public class WorkerThread implements Runnable {

	Socket socket;
	DataInputStream din;
	DataOutputStream dout;
	DataBaseEngin dbEngin;
	OperationList operation;// log记录器
	boolean isrun = true;

	public WorkerThread(Socket socket, DataBaseEngin dbEngin) {
		this.socket = socket;
		this.dbEngin = dbEngin;
		try {
			din = new DataInputStream(socket.getInputStream());
			dout = new DataOutputStream(socket.getOutputStream());
			operation = new OperationList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readData() throws IOException {
		byte[] data = null;
		int length = din.readByte();
		data = new byte[length];
		din.read(data, 0, length);
		return new String(data);
	}

	public void handRegister(String str) {

		// 1 服务名 xxx=xxx
		// 2 用户名
		// 3 密码
		String[] tp = null;
		Map conditions = new HashMap();
		String[] temp = str.split("&");
		System.out.println(str);
		tp = temp[1].split("=");
		conditions.put(tp[0], tp[1]);
		tp = temp[2].split("=");
		conditions.put(tp[0], tp[1]);
		Map tables = dbEngin.getUsingDataBase().getTables();
		Table roottable = (Table) tables.get("user");
		MapUtil.showMap(roottable.getTabledata());
		System.out.println("****************");
		System.out.println("是否查找用用户" + roottable.select(conditions));
	}

	@Override
	public void run() {
		while (isrun) {
			System.out.println("worker开始工作");
			String data;
			try {
				int type = din.readByte();
				switch (type) {
				case Flag.register:
					data = readData();
					handRegister(data);
					operation.writeLog(data);
					operation.writeLog(praiseOperation(type, data));
					break;
					
				case Flag.showtables:// 直接是命令标志，如果要现实指定数据库，需要显示的改变
					handShowTable();
					break;

				case Flag.showdatabases:// 返回数据库引擎中的所有数据库
					handShowDataBase();
					break;

				case Flag.choosedatabase:// 变换当前所使用的数据库
					data = readData();
					System.out.println(data);
					String[] da = data.split("=");
					handChooseDataBase(da[1]);
					operation.writeLog(praiseOperation(type, data));
					break;

				case Flag.select:
					data = readData();
					System.out.println(data);
					handSelect(data);
					operation.writeLog(praiseOperation(type, data));
					break;

				case Flag.delect:

					break;

				case Flag.update:

					break;

				case Flag.insert:

					break;
				case Flag.createdatabase:
					data = readData();
					System.out.println(data);
					handCreateDataBase(data);
					operation.writeLog(praiseOperation(type, data));

					break;

				case Flag.createtable:
					data = readData();// 创建表的语句 默认是在当前的数据库中创建
										// tablename;属性1，属性，属性，
					System.out.println(data);
					handCreateTable(data);
					operation.writeLog(praiseOperation(type, data));
					break;

				case Flag.discribe:
					data = readData();// 表名
					System.out.println("展现表的描述" + data);
					handDescribe(data);
					operation.writeLog(praiseOperation(type, data));
					break;

				default:
					break;
				}
			} catch (IOException e) {
				if (socket != null)
					try {
						isrun = false;
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				e.printStackTrace();
			}

		}
	}

	private void handDescribe(String data) {
		String keystring = MapUtil.mapKeyToString(((Table) dbEngin
				.getUsingDataBase().getTables().get(data)).getTabledata());
		System.out.println("keyString是" + keystring);
	}

	// 处理查询语句 现在只执行最基本的查询
	private void handSelect(String data) {

	}

	/***
	 * 
	 * 处理表的创建 tablename;属性1，属性，属性，
	 * 
	 * *****/
	private void handCreateTable(String data) {
		String[] temp = data.split(";");// 切分为表名字，表属性
		String tablename = temp[0];
		String[] values = temp[1].split(",");
		dbEngin.getUsingDataBase().createTable(values, tablename);
	}

	/******
	 * 
	 * 处理创建数据库的指令 直接传入数据库的名字 单如果要想接下来操作刚刚创建的数据库要使用数据库的切换语句
	 * 
	 * ******/
	private void handCreateDataBase(String data) {
		dbEngin.createDataBase(data);
	}

	/***
	 * 
	 * 转换当前的数据库 data： 數據庫名稱
	 * 
	 * ***/
	private void handChooseDataBase(String data) {
		try {
			DataBase db2 = null;
			DataBase db = dbEngin.getUsingDataBase();
			String dbname = db.getDBName();
			if (data.equals(dbname))
				System.out.println("你要跟换的数据库和当前的数据库相同，不需要更换");
			else {
				db2 = (DataBase) dbEngin.databases.get(data);
				if (db2 == null) {
					System.out.println("挡墙的数据库引擎中没有你要找的数据库");
				} else
					dbEngin.setUsingDataBase(db2);
				System.out.println("数据库已经跟换");
			}
		} catch (Exception e) {
			System.out.println("当前的数据库引擎中没有你要找的数据库");
		}
	}

	/*****
	 * 
	 * 展现当前有多少个数据库
	 * 
	 * ****/
	private void handShowDataBase() {
		Map databases = dbEngin.databases;
		Set set = databases.keySet();
		int i = 0;
		System.out.println(set.size());
		Iterator it = set.iterator();
		String strtables = "";
		while (it.hasNext()) {
			i++;
			if (i == set.size()) {
				String key = (String) it.next();
				strtables += key;
			} else {
				String key = (String) it.next();
				strtables = strtables + key + ",";
			}
		}
		System.out.println(strtables);

	}

	/***
	 * 
	 * 展现当前所选择的数据库中的表 想要跟換當前數據庫，需要顯示的命令跟換
	 * 
	 * ****/
	private void handShowTable() {
		Map tables = dbEngin.getUsingDataBase().getTables();
		Set set = tables.keySet();
		int i = 0;
		System.out.println(set.size());
		Iterator it = set.iterator();
		String strtables = "";
		while (it.hasNext()) {
			i++;
			if (i == set.size()) {
				String key = (String) it.next();
				strtables += key;
			} else {
				String key = (String) it.next();
				strtables = strtables + key + ",";
			}
		}
		System.out.println(strtables);
	}

	private String praiseOperation(int type, String data) {
		return DataUtil.getFormateData() + "    " + Flag.flagToSstring(type)
				+ "    " + data;
	}

	public OperationList getOperation() {
		return operation;
	}

	public void setOperation(OperationList operation) {
		this.operation = operation;
	}

}
