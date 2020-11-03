package uk.co.pm.dao.csv.reader;

//This exception is used to indicate that the file was found
//but there was some issue in reading it
public class FileReadErrorException extends Exception {

	public FileReadErrorException(String msg) {
		super(msg);
	}
	
}
