package it.davidecremonesi.superenalotto.persistence;

import junit.framework.TestCase;

public class EstrazioneTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFallsBetween1() {
		Estrazione e = new Estrazione();
		e.setDataEstrazione("01/01/2011");
		e.setNestrazione("11");
		
		assertTrue(e.fallsBetween(2010, 1, 2012, 100));
		assertTrue(e.fallsBetween(2010, 111, 2012, 1));
		assertTrue(e.fallsBetween(2011, 11, 2011, 11));
		assertTrue(e.fallsBetween(2011, 11, null, null));
		assertTrue(e.fallsBetween(null, null, 2011, 11));

		assertFalse(e.fallsBetween(2009, 1, 2010, 100));
		assertFalse(e.fallsBetween(null, null, 2010, 100));
	}

}
