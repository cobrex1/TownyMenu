package net.tolmikarc.townymenu.settings;

import org.mineacademy.fo.settings.SimpleSettings;


public class Settings extends SimpleSettings {
	@Override
	protected int getConfigVersion() {
		return 1;
	}

	public static String MONEY_SYMBOL;

	private static void init() {
		pathPrefix(null);

		MONEY_SYMBOL = getString("Money_Symbol");


	}

}
