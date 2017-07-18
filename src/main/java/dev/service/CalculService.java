package dev.service;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.exception.CalculException;

public class CalculService {

	private static final Logger LOG = LoggerFactory.getLogger(CalculService.class);

	public int additionner(String expression) throws CalculException {
		LOG.debug("Evaluation de l'expression {}", expression);
		try {
			return Stream.of(expression.split("\\+")).mapToInt(Integer::parseInt).sum();
		} catch (NumberFormatException exc) {
			throw new CalculException("Paramètre(s) invalide(s) : " + expression);
		}
	}
}