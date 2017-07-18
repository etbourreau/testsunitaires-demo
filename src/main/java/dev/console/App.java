package dev.console;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.exception.CalculException;
import dev.service.CalculService;

public class App {

	private Scanner scanner;
	private CalculService calculatrice;
	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	public App(Scanner scanner, CalculService calculatrice) {
		this.scanner = scanner;
		this.calculatrice = calculatrice;
	}

	protected void afficherTitre() {
		LOG.info("**** Application Calculatrice ****");
	}

	public void demarrer() {
		afficherTitre();
		String answer;
		do{
			LOG.info("Veuillez saisir une expression :");
			answer = scanner.next();
			evaluer(answer);
		} while (!answer.equalsIgnoreCase("fin"));
	}

	protected void evaluer(String expression) {
		if (expression.equalsIgnoreCase("fin")) {
			LOG.info("Aurevoir :-(");
		} else {
			try {
				LOG.info(expression + "=" + this.calculatrice.additionner(expression));
			} catch (CalculException e) {
				LOG.info("L'expression " + expression + " est invalide.");
			}
		}

	}
}
