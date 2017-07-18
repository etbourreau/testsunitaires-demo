package dev.console;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.exception.CalculException;
import dev.service.CalculService;

public class AppTest {

	private static final Logger LOG = LoggerFactory.getLogger(AppTest.class);

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	@Rule
	public final TextFromStandardInputStream systemIn = emptyStandardInputStream();
	private App app;
	private CalculService calculService;

	@Before
	public void setUp() throws Exception {
		this.calculService = new CalculService();
		this.calculService = mock(CalculService.class);
		this.app = new App(new Scanner(System.in), calculService);

	}

	@Test
	public void testAfficherTitre() throws Exception {
		this.app.afficherTitre();
		String logConsole = systemOutRule.getLog();
		assertThat(logConsole).contains("**** Application Calculatrice ****");
	}

	@Test
	public void testEvaluer() throws CalculException {
		String expression = "1+34";
		when(calculService.additionner(expression)).thenReturn(35);
		this.app.evaluer(expression);
		verify(calculService).additionner(expression);
		assertThat(systemOutRule.getLog()).contains("1+34=35");
	}

	@Test
	public void testEvaluerExpressionInvalide() throws CalculException {
		String expression = "b+34";
		when(calculService.additionner(expression)).thenThrow(CalculException.class);
		this.app.evaluer(expression);
		verify(calculService).additionner(expression);
	}

	@Test
	public void testEvaluerExpressionVide() throws CalculException {
		String expression = "";
		when(calculService.additionner(expression)).thenThrow(CalculException.class);
		this.app.evaluer(expression);
		verify(calculService).additionner(expression);
	}

	@Test
	public void testEtape1() {
		systemIn.provideLines("fin");
		this.app.demarrer();
		assertThat(systemOutRule.getLog()).contains("Aurevoir :-(");
	}

	@Test
	public void testEtape2() throws CalculException {
		systemIn.provideLines("1+2", "fin");
		when(calculService.additionner("1+2")).thenReturn(3);
		this.app.demarrer();
		assertThat(systemOutRule.getLog()).contains("1+2=3");
		assertThat(systemOutRule.getLog()).contains("Aurevoir :-(");
	}

	@Test
	public void testEtape3() throws CalculException {
		systemIn.provideLines("AAAA", "fin");
		when(calculService.additionner("AAAA")).thenThrow(CalculException.class);
		this.app.demarrer();
		assertThat(systemOutRule.getLog()).contains("L'expression AAAA est invalide.");
		assertThat(systemOutRule.getLog()).contains("Aurevoir :-(");
	}

	@Test
	public void testEtape4() throws CalculException {
		systemIn.provideLines("1+2", "30+2", "fin");
		when(calculService.additionner("1+2")).thenReturn(3);
		when(calculService.additionner("30+2")).thenReturn(32);
		this.app.demarrer();
		assertThat(systemOutRule.getLog()).contains("1+2=3");
		assertThat(systemOutRule.getLog()).contains("Aurevoir :-(");
	}
}
