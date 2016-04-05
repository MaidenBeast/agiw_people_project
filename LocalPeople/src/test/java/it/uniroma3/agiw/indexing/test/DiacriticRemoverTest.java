package it.uniroma3.agiw.indexing.test;

import static org.junit.Assert.*;
import it.uniroma3.agiw.indexing.services.parsing.DiacriticRemover;

import org.junit.Before;
import org.junit.Test;

public class DiacriticRemoverTest {
	
	private String noDiacr = "Felicita e tenersi per mano andare lontano";
	private String diacr1_singolo = "Felicità e tenersi per mano andare lontano";
	private String diacr2_singolo = "La sincerità é la virtù più desiderabile";
	private String diacr1_multiplo = "Il tennista Bjorn Børg";
	private String diacr2_multiplo = "Il tennista Bjorn Børg che cammina per la Straße";
	private String vuota = "";
	
	private DiacriticRemover remover;

	@Before
	public void setUp() throws Exception {
		this.remover = new DiacriticRemover();
	}

	@Test
	public void testClean_nessunDiacritico() {
		String correct = "Felicita e tenersi per mano andare lontano";
		String clean = this.remover.clean(this.noDiacr);
		this.cleanCheck(correct, clean);
	}
	
	@Test
	public void testClean_unDiacriticoSostituzioneSingola() {
		String correct = "Felicita e tenersi per mano andare lontano";
		String clean = this.remover.clean(this.diacr1_singolo);
		this.cleanCheck(correct, clean);
	}
	
	@Test
	public void testClean_piuDiacriticiSostituzioneSingola() {
		String correct = "La sincerita e la virtu piu desiderabile";
		String clean = this.remover.clean(this.diacr2_singolo);
		this.cleanCheck(correct, clean);
	}
	
	@Test
	public void testClean_unDiacriticoSostituzioneMultipla() {
		String correct = "Il tennista Bjorn Boerg";
		String clean = this.remover.clean(this.diacr1_multiplo);
		this.cleanCheck(correct, clean);
	}
	
	@Test
	public void testClean_piuDiacriticiSostituzioneMultipla() {
		String correct = "Il tennista Bjorn Boerg che cammina per la Strasse";
		String clean = this.remover.clean(this.diacr2_multiplo);
		this.cleanCheck(correct, clean);
	}
	
	@Test
	public void testClean_stringaVuota() {
		String correct = "";
		String clean = this.remover.clean(this.vuota);
		this.cleanCheck(correct, clean);
	}
	
	private void cleanCheck(String correct, String clean) {
		assertNotNull(clean);
		assertEquals(correct, clean);
	}
}
