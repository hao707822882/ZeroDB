package parser;

import java.util.List;

/** */
/**
 * ����Sql���������칤��
 * 
 * @author �Գ���
 *
 * @since 2013-6-10
 * @version 1.00
 */
public class SqlParserUtil {
	/** */
	/**
	 * ��* ��������Ҫ��� ��* @param sql:Ҫ������sql��� ��* @return ���ؽ������ ��
	 */
	public String getParsedSql(String sql) {
		sql = sql.trim();
		sql = sql.toLowerCase();
		sql = sql.replaceAll("\\s{1,}", " ");
		sql = "" + sql + " ENDOFSQL";
		// System.out.println(sql);
		return SingleSqlParserFactory.generateParser(sql).getParsedSql();
	}

	/** */
	/**
	 * ��* SQL�������Ľӿ� ��* @param sql:Ҫ������sql��� ��* @return ���ؽ������ ��
	 */
	public List<SqlSegment> getParsedSqlList(String sql) {
		sql = sql.trim();
		sql = sql.toLowerCase();
		sql = sql.replaceAll("\\s{1,}", " ");
		sql = "" + sql + " ENDOFSQL";
		// System.out.println(sql);
		return SingleSqlParserFactory.generateParser(sql).RetrunSqlSegments();
	}
}