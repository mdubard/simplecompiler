//package us.sc.k12;

public class SymbolRow {
	// Mary and Tia
	// September 23, 2013

	private String type;
	private String value;
	private int sml;

	public SymbolRow(String kind, String worth, int SML) {
		type = kind;
		value = worth;
		sml = SML;
	}

	public String type() {
		return type;
	}

	public String value() {
		return value;
	}

	public int sml() {
		return sml;
	}
}
