import it.davidecremonesi.superenalotto.Estrazione;
import junit.framework.TestCase;


public class TestAAAAMMDD extends TestCase {


	public void testGetDataestrazioneYYYYMMDD() {
		Estrazione eee = new Estrazione();
		eee.setDataestrazione("07/12/1971");
		String actual = eee.getDataestrazioneYYYYMMDD();
		assertEquals("19711207", actual);
	}

}
