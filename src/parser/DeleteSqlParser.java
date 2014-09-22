package parser;

/** */
/**
 *
 * ����ɾ����������
 * 
 * @author �Գ���
 *
 * @since 2013-6-10
 * @version 1.00
 */
public class DeleteSqlParser extends BaseSingleSqlParser {
	public DeleteSqlParser(String originalSql) {
		super(originalSql);
	}

	@Override
	protected void initializeSegments() {
		segments.add(new SqlSegment("(delete from)(.+)( where | ENDOFSQL)",
				"[,]"));
		segments.add(new SqlSegment("(where)(.+)( ENDOFSQL)", "(and|or)"));
	}
}