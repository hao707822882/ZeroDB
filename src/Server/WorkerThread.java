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
	OperationList operation;// log��¼��
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

		// 1 ������ xxx=xxx
		// 2 �û���
		// 3 ����
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
		System.out.println("�Ƿ�������û�" + roottable.select(conditions));
	}

	@Override
	public void run() {
		while (isrun) {
			System.out.println("worker��ʼ����");
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
					
				case Flag.showtables:// ֱ���������־�����Ҫ��ʵָ�����ݿ⣬��Ҫ��ʾ�ĸı�
					handShowTable();
					break;

				case Flag.showdatabases:// �������ݿ������е��������ݿ�
					handShowDataBase();
					break;

				case Flag.choosedatabase:// �任��ǰ��ʹ�õ����ݿ�
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
					data = readData();// ���������� Ĭ�����ڵ�ǰ�����ݿ��д���
										// tablename;����1�����ԣ����ԣ�
					System.out.println(data);
					handCreateTable(data);
					operation.writeLog(praiseOperation(type, data));
					break;

				case Flag.discribe:
					data = readData();// ����
					System.out.println("չ�ֱ������" + data);
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
		System.out.println("keyString��" + keystring);
	}

	// �����ѯ��� ����ִֻ��������Ĳ�ѯ
	private void handSelect(String data) {

	}

	/***
	 * 
	 * �����Ĵ��� tablename;����1�����ԣ����ԣ�
	 * 
	 * *****/
	private void handCreateTable(String data) {
		String[] temp = data.split(";");// �з�Ϊ�����֣�������
		String tablename = temp[0];
		String[] values = temp[1].split(",");
		dbEngin.getUsingDataBase().createTable(values, tablename);
	}

	/******
	 * 
	 * ���������ݿ��ָ�� ֱ�Ӵ������ݿ������ �����Ҫ������������ոմ��������ݿ�Ҫʹ�����ݿ���л����
	 * 
	 * ******/
	private void handCreateDataBase(String data) {
		dbEngin.createDataBase(data);
	}

	/***
	 * 
	 * ת����ǰ�����ݿ� data�� ���������Q
	 * 
	 * ***/
	private void handChooseDataBase(String data) {
		try {
			DataBase db2 = null;
			DataBase db = dbEngin.getUsingDataBase();
			String dbname = db.getDBName();
			if (data.equals(dbname))
				System.out.println("��Ҫ���������ݿ�͵�ǰ�����ݿ���ͬ������Ҫ����");
			else {
				db2 = (DataBase) dbEngin.databases.get(data);
				if (db2 == null) {
					System.out.println("��ǽ�����ݿ�������û����Ҫ�ҵ����ݿ�");
				} else
					dbEngin.setUsingDataBase(db2);
				System.out.println("���ݿ��Ѿ�����");
			}
		} catch (Exception e) {
			System.out.println("��ǰ�����ݿ�������û����Ҫ�ҵ����ݿ�");
		}
	}

	/*****
	 * 
	 * չ�ֵ�ǰ�ж��ٸ����ݿ�
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
	 * չ�ֵ�ǰ��ѡ������ݿ��еı� ��Ҫ���Q��ǰ�����죬��Ҫ�@ʾ��������Q
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
