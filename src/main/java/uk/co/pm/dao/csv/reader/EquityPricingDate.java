package uk.co.pm.dao.csv.reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquityPricingDate implements IEquityPricingDate {

	String dateString = "";
	Pattern equityDatePattern;
	
	public EquityPricingDate() {
		this.equityDatePattern = Pattern.compile("\\d\\d\\d-Q[1-4]");
	}
	
	@Override
	public void setDate(String date) throws IllegalEquityDateFormatException {
		if(isInvalidDateFormat(date)) {
			throw new IllegalEquityDateFormatException("The format of the date should be of type YYYY-Q[1-4]> but it was " + date);
		}
		else {
			this.dateString = date;
		}
	}
	
	private boolean isInvalidDateFormat(String dateString) {
		Matcher m = this.equityDatePattern.matcher(dateString);
		return m.matches();
	}

	@Override
	public String toString() {
		return this.dateString;
	}
}