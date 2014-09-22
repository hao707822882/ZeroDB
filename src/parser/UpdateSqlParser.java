package parser;

/** */
/**
 *
 * µ¥¾ä¸üĞÂÓï¾ä½âÎöÆ÷
 * 
 * @author ÕÔ³¯·å
 *
 * @since 2013-6-10
 * @version 1.00
 */
public class UpdateSqlParser extends BaseSingleSqlParser {
	public UpdateSqlParser(String originalSql) {
		super(originalSql);
	}

	@Override
	protected void initializeSegments() {
		segments.add(new SqlSegment("(update)(.+)(set)", "[,]"));
		segments.add(new SqlSegment("(set)(.+)( where | ENDOFSQL)", "[,]"));
		segments.add(new SqlSegment("(where)(.+)( ENDOFSQL)", "(and|or)"));
	}
}