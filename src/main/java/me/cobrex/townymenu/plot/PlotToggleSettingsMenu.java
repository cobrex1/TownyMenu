package me.cobrex.townymenu.plot;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.event.TownBlockSettingsChangedEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.MenuHandler;
import me.cobrex.townymenu.utils.MenuItemBuilder;
import me.cobrex.townymenu.utils.MenuManager;
import me.cobrex.townymenu.utils.MessageFormatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class PlotToggleSettingsMenu extends MenuHandler {

	private final TownBlock townBlock;
	private final Player player;

	public PlotToggleSettingsMenu(Player player, TownBlock townBlock) {
		super(
				player,
				MessageFormatter.format(Localization.PlotMenu.ToggleMenu.MENU_TITLE, player),
				getInventorySize(ConfigNodes.PLOT_TOGGLE_MENU_SIZE)
		);

		this.player = player;
		this.townBlock = townBlock;

		fillEmptySlots("filler_plot_toggle_menu");

		MenuItemBuilder.of("plot_toggle_fire_button")
				.name(Localization.PlotMenu.ToggleMenu.FIRE)
				.lore(List.of(
						"",
						townBlock.getPermissions().fire
								? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF
								: Localization.PlotMenu.ToggleMenu.TOGGLE_ON
				))
				.onClick(click -> {
					togglePerm("fire");
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_toggle_mobs_button")
				.name(Localization.PlotMenu.ToggleMenu.MOBS)
				.lore(List.of(
						"",
						townBlock.getPermissions().mobs
								? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF
								: Localization.PlotMenu.ToggleMenu.TOGGLE_ON
				))
				.onClick(click -> {
					togglePerm("mobs");
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_toggle_explosions_button")
				.name(Localization.PlotMenu.ToggleMenu.EXPLODE)
				.lore(List.of(
						"",
						townBlock.getPermissions().explosion
								? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF
								: Localization.PlotMenu.ToggleMenu.TOGGLE_ON
				))
				.onClick(click -> {
					togglePerm("explosion");
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_toggle_pvp_button")
				.name(Localization.PlotMenu.ToggleMenu.PVP)
				.lore(List.of(
						"",
						townBlock.getPermissions().pvp
								? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF
								: Localization.PlotMenu.ToggleMenu.TOGGLE_ON
				))
				.onClick(click -> {
					togglePerm("pvp");
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_toggle_menu_info_button")
				.name(Localization.PlotMenu.ToggleMenu.INFO)
				.lore(Localization.PlotMenu.ToggleMenu.INFO_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_toggle_menu_back_button")
				.name(Localization.Back_Button.BACK_BUTTON_TITLE)
				.lore("")
				.lore(Localization.TownMenu.ToggleMenu.BACK_BUTTON_LORE)
				.onClick(click -> {
					MenuManager.closeMenu(player);
					try {
						MenuManager.switchMenu(player, new PlotMenu(player, townBlock));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player,this);
	}

	private void togglePerm(String type) {
		switch (type.toLowerCase()) {
			case "fire" -> townBlock.getPermissions().fire = !townBlock.getPermissions().fire;
			case "mobs" -> townBlock.getPermissions().mobs = !townBlock.getPermissions().mobs;
			case "explosion" -> townBlock.getPermissions().explosion = !townBlock.getPermissions().explosion;
			case "pvp" -> townBlock.getPermissions().pvp = !townBlock.getPermissions().pvp;
		}

		townBlock.setChanged(true);
		Bukkit.getPluginManager().callEvent(new TownBlockSettingsChangedEvent(townBlock));

		Town town = townBlock.getTownOrNull();
		if (town != null) {
			TownyAPI.getInstance().getDataSource().saveTown(town);
		}
		TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

		MenuManager.refreshInPlace(player, new PlotToggleSettingsMenu(player, townBlock));
	}
}