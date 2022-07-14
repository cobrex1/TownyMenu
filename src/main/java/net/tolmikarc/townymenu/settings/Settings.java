package net.tolmikarc.townymenu.settings;


import org.mineacademy.fo.settings.SimpleSettings;

public final class Settings extends SimpleSettings {
	@Override
	protected int getConfigVersion() {
		return 1;
	}

	public static String MONEY_SYMBOL;
	public static Boolean ECONOMY_ENABLED;
 	public static String LOCALE;

	private static void init() {
		setPathPrefix(null);

		MONEY_SYMBOL = getString("Money_Symbol");
		ECONOMY_ENABLED = getBoolean("Economy");
		LOCALE = getString("Locale");

	}

}
