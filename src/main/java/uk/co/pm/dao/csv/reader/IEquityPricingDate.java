package uk.co.pm.dao.csv.reader;

public interface IEquityPricingDate {

	/***
	 * modifies this
	 * @effect Sets this equity pricing date to the argument passed in
	 *         If the date is not of the format <YEAR>-Q<QUARTER>
	 *         then throws an IllegalEquityDateFormatException
	 */
	public void setDate(String date) throws IllegalEquityDateFormatException;
	
	/***
	 * @effects Returns a string representation of this date,
	 * which is in the format:
	 *                 <YEAR>-Q<QUARTER>
	 */
	@Override
	public String toString();
}
