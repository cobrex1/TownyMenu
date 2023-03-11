package me.cobrex.townymenu.hooks;

import me.arcaniax.hdb.api.HeadDatabaseAPI;

public final class HeadDatabaseHook {


	private HeadDatabaseAPI hdbApi = null;

	public HeadDatabaseAPI getHdbApi() {
		return hdbApi;
	}

	public void setHdbApi(HeadDatabaseAPI hdbApi) {
		this.hdbApi = hdbApi;
	}

}