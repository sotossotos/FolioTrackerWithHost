package uk.co.pm.dao.csv.reader;

public class SyntaxErrorException extends Exception {

	public SyntaxErrorException(String line, String reason) {
		super("Illegal syntax at line: " + line + ". " + reason);
	}
	
}
