package uk.co.pm.holding;

public class HSJSONReference extends HSReference{
	private String link;
	public HSJSONReference(int PID, String epic, int noShares, String type, String time,
		String link) {
		super(PID, epic, noShares, type, time);
		this.link = link;
		}
	public String getLink() {
		return link;
	}

}
