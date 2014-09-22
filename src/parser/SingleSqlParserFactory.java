package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.sitinspring.common.sqlparser.single.NoSqlParserException;
/** */
/**
 * ����Sql���������칤��
 * 
 * @author �Գ���
 *
 * @since 2013-6-10
 * @version 1.00
 */
public class SingleSqlParserFactory {
	public static BaseSingleSqlParser generateParser(String sql) {
		if (contains(sql, "(insert into)(.+)(select)(.+)(from)(.+)")) {
			return new InsertSelectSqlParser(sql);
		} else if (contains(sql, "(select)(.+)(from)(.+)")) {
			return new SelectSqlParser(sql);
		} else if (contains(sql, "(delete from)(.+)")) {
			return new DeleteSqlParser(sql);
		} else if (contains(sql, "(update)(.+)(set)(.+)")) {
			return new UpdateSqlParser(sql);
		} else if (contains(sql, "(insert into)(.+)(values)(.+)")) {
			return new InsertSqlParser(sql);
		}
		// sql=sql.replaceAll("ENDSQL", "");
		else
			return new InsertSqlParser(sql);
		// throw new NoSqlParserException(sql.replaceAll("ENDOFSQL",
		// ""));//���쳣���׳�
	}

	/** */
	/**
	 * ��* ��word�Ƿ���lineText�д��ڣ�֧��������ʽ ��* @param sql:Ҫ������sql��� ��* @param
	 * regExp:������ʽ ��* @return ��
	 */
	private static boolean contains(String sql, String regExp) {
		Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sql);
		return matcher.find();
	}
}