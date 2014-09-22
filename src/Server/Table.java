package Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import lack.LineLack;
import util.MapUtil;
import Server.praise.CVSPraise;

public class Table {
	CVSPraise parise;
	String[] fields;
	Map tabledata;

	LineLack lack ;

	public Table(String[] fields, Map tabledata) {
		this.fields = fields;
		this.tabledata = tabledata;
		lack= new LineLack();
	}

	public Table(CVSPraise praise) {
		this.parise = praise;
		tabledata = praise.praise();
		this.fields = praise.getFields();
		System.out.println("在表中获得tabledata并遍历");
		MapUtil.showMap(tabledata);
	}

	public void insert(Map tempdata) {
		Set main = tabledata.keySet();
		Iterator mit = main.iterator();
		while (mit.hasNext()) {
			String key = (String) mit.next();
			if (null != tempdata.get(key)) {
				ArrayList l = (ArrayList) tabledata.get(key);
				l.add(tempdata.get(key));
			} else {
				ArrayList l = (ArrayList) tabledata.get(key);
				l.add("NULL");
			}
		}
	}

	/***
	 * 
	 * 为了先把系统跑通，目前只支持select 不带条件的
	 * 
	 * *****/
	public Map select(String[] str) {
		Map map = new HashMap();
		for (int i = 0; i < str.length; i++) {
			map.put(str[i], (ArrayList) tabledata.get(str[i]));
		}
		return map;
	}

	/***
	 * 
	 * select 按给定的多个参数进行匹配查找 xxx，xxxx 在list中找到对应的xxx，再讲两个向比较
	 * 
	 * ***/
	public boolean select(Map conditions) {
		System.out.println("show map");
		// MapUtil.showMap(conditions);
		int count = 0;
		int num = 0;
		Set set = conditions.keySet();
		Iterator it = set.iterator();
		ArrayList list = null;
		while (it.hasNext()) {
			String key = (String) it.next();
			System.out.println("key是     " + key);
			MapUtil.showMap(tabledata);
			list = (ArrayList) tabledata.get(key);
			if (count == 0) {
				for (int i = 0; i < list.size(); i++) {

					if (list.get(i).equals(conditions.get(key))) {
						count++;
						num = i;
						break;
					}
				}
			} else {
				list = (ArrayList) tabledata.get(key);
				if (list.get(num).equals(conditions.get(key)))
					count++;
			}
		}
		if (count == set.size())
			return true;
		else
			return false;
	}

	/***
	 * 
	 * 为了简单需要 只支持一个value的匹配删除
	 * 
	 * ***/
	public void deletc(String lie, String value) {
		ArrayList list = (ArrayList) tabledata.get(lie);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == value) {
				Set main = tabledata.keySet();
				Iterator it = main.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					ArrayList l = (ArrayList) tabledata.get(key);
					l.remove(i);
				}
			}
		}
	}

	/**
	 * 
	 * 改
	 * 
	 ***/
	public void update(Map data, String lie, String value) {
		ArrayList list = (ArrayList) tabledata.get(lie);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(value)) {
				Set key = data.keySet();
				Iterator it = key.iterator();
				while (it.hasNext()) {
					String key1 = (String) it.next();
					ArrayList list1 = (ArrayList) tabledata.get(key1);
					list1.add(i, data.get(key1));
				}
			}
		}

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
