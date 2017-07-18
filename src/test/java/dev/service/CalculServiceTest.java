package dev.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.exception.CalculException;

public class CalculServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(CalculServiceTest.class);

	@Test
	public void testAdditionner() throws CalculException {
		LOG.info("Etant donné, une instance de la classe CalculService");
		CalculService cs = new CalculService();
		LOG.info("Lorsque j'évalue l'addition de l'expression 121+346+481");
		int somme = cs.additionner("121+346+481");
		LOG.info("Alors j'obtiens le résultat 948");
		assertEquals("Le resultat n'est pas correct!", somme, 948);
	}

	@Test(expected = CalculException.class)
	public void testAdditionnerCalculException() throws CalculException {
		LOG.info("Etant donné, une instance de la classe CalculService");
		CalculService cs = new CalculService();
		LOG.info("Lorsque j'évalue l'addition de l'expression b+cab");
		cs.additionner("b+cab");
		LOG.info("Alors j'obtiens une exception CalculException");
	}

	@Test(expected = CalculException.class)
	public void testAdditionnerExpressionVide() throws CalculException {
		LOG.info("Etant donné, une instance de la classe CalculService");
		CalculService cs = new CalculService();
		LOG.info("Lorsque j'évalue l'addition de l'expression vide");
		cs.additionner("");
	}
}
