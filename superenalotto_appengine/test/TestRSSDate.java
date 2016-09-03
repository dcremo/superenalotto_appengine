import java.util.Date;

import it.davidecremonesi.atom.RSSFeed;
import junit.framework.TestCase;


public class TestRSSDate extends TestCase {

	public void testFormatDateCalendar() {
		String fff = RSSFeed.formatDate(new Date());
		System.out.println(fff);
		assertEquals(12, fff.length());
	}

	public void testFormatDateString() {
		fail("Not yet implemented");
	}

}
