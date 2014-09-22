package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapUtil {

	public static void showMap(Map map) {
		Set set = map.keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			System.out.println("key «" + key);
			/*
			 * ArrayList list = (ArrayList) map.get(key); for (int i = 0; i <
			 * list.size(); i++) { System.out.print(list.get(i) + "    "); }
			 */

		}
	}

	public static void ishave(Map map) {
		ArrayList list = (ArrayList) map.get("name");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	public static String mapKeyToString(Map map) {
		Set set = map.keySet();
		Iterator it = set.iterator();
		String keys = "";
		while (it.hasNext()) {
			String key = (String) it.next();
			keys = keys + key + ",";
		}
		return keys;
	}
}
