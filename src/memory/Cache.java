package memory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******
 * 
 * �Ի�����ԣ�ֻ�в�ѯ���� ���ݴ洢��ʽ��
 * map<select��䣬list<����>>
 * ����ͬ��select���л���
 * 
 * *****/

@SuppressWarnings("all")
public interface Cache {
	Map cache = new HashMap<String, List>();

	public void update();

	public void insert();

	public void getCache();
}
