package memory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******
 * 
 * 对缓存而言，只有查询缓存 数据存储方式：
 * map<select语句，list<数据>>
 * 对相同的select进行缓存
 * 
 * *****/

@SuppressWarnings("all")
public interface Cache {
	Map cache = new HashMap<String, List>();

	public void update();

	public void insert();

	public void getCache();
}
