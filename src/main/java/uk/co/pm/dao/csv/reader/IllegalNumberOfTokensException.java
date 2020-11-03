package uk.co.pm.dao.csv.reader;

public class IllegalNumberOfTokensException extends Exception {

	public IllegalNumberOfTokensException(String line, int expectedTokenAmount, int actualTokenAmount) {
		super("Number of tokens on line " + line + " should be " + expectedTokenAmount + " but was actually " + actualTokenAmount);
	}
}
