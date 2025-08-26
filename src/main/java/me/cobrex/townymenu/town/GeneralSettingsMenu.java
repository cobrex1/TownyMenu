package me.cobrex.townymenu.town;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.town.prompt.TownBoardPrompt;
import me.cobrex.townymenu.town.prompt.TownNamePrompt;
import me.cobrex.townymenu.utils.*;
import org.bukkit.entity.Player;

public class GeneralSettingsMenu extends MenuHandler {

	private final Town town;

	public GeneralSettingsMenu(Player player, Town town) {
		super(
				player,
				MessageFormatter.format(Localization.TownMenu.GeneralSettingsMenu.MENU_TITLE, player),
				getInventorySize(ConfigNodes.TOWN_SETTINGS_MENU_SIZE)
		);

		this.town = town;

		MenuItemBuilder.of("town_set_home_block_button")
				.name(Localization.TownMenu.GeneralSettingsMenu.SET_HOME_BLOCK)
				.lore(Localization.TownMenu.GeneralSettingsMenu.SET_HOME_BLOCK_LORE)
				.onClick(click -> {
					TownBlock townBlock = TownyAPI.getInstance().getTownBlock(player.getLocation());
					try {
						if (townBlock != null && townBlock.getTown().equals(town) && town.getMayor().getName().equals(player.getName())) {
							town.setHomeBlock(townBlock);
							TownyAPI.getInstance().getDataSource().saveTown(town);
							player.sendMessage(Localization.TownMenu.GeneralSettingsMenu.SET_HOME_BLOCK_MSG);
							player.sendMessage(Localization.TownMenu.GeneralSettingsMenu.SPAWN_REMINDER);
						} else {
							player.sendMessage(Localization.Error.CANNOT_SET_HOMEBLOCK);
						}
					} catch (TownyException e) {
						MessageUtils.send(player, Localization.Error.CANNOT_SET_HOMEBLOCK);
					}
					player.closeInventory();
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_set_spawn_button")
				.name(Localization.TownMenu.GeneralSettingsMenu.SET_SPAWN)
				.lore(Localization.TownMenu.GeneralSettingsMenu.SET_SPAWN_LORE)
				.onClick(click -> {
					player.closeInventory();
					TownBlock townBlock = TownyAPI.getInstance().getTownBlock(player.getLocation());
					try {
						if (townBlock.isHomeBlock() && townBlock.getTown().equals(town)) {
							town.setSpawn(player.getLocation());
							TownyAPI.getInstance().getDataSource().saveTown(town);
							MessageUtils.send(player, Localization.TownMenu.GeneralSettingsMenu.SET_SPAWN_MSG);
						} else {
							MessageUtils.send(player, Localization.Error.CANNOT_SET_SPAWN);
						}
					} catch (TownyException e) {
						MessageUtils.send(player, Localization.Error.CANNOT_SET_SPAWN);
					}
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_set_name_button")
				.name(Localization.TownMenu.GeneralSettingsMenu.SET_NAME)
				.lore(Localization.TownMenu.GeneralSettingsMenu.SET_NAME_LORE)
				.onClick(click -> {
					player.closeInventory();
					if (town.getMayor().getName().equals(player.getName())) {
						new TownNamePrompt(town).show(player);
					} else {
						MessageUtils.send(player, Localization.Error.CANNOT_CHANGE_NAME);
					}
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_set_board_button")
				.name(Localization.TownMenu.GeneralSettingsMenu.SET_BOARD)
				.lore(Localization.TownMenu.GeneralSettingsMenu.SET_BOARD_LORE)
				.onClick(click -> {
					System.out.println("[DEBUG] Town board button clicked by " + player.getName());
					player.closeInventory();
					if (town.getMayor().getName().equals(player.getName())) {
						new TownBoardPrompt(player, town).show(player);
					} else {
						MessageUtils.send(player, Localization.Error.CANNOT_CHANGE_BOARD);
					}
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_settings_menu_info_button")
				.name(Localization.TownMenu.GeneralSettingsMenu.INFO)
				.lore(Localization.TownMenu.GeneralSettingsMenu.INFO_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_settings_menu_back_button")
				.name(Localization.TownMenu.GeneralSettingsMenu.BACK_BUTTON)
				.lore(Localization.TownMenu.GeneralSettingsMenu.BACK_BUTTON_LORE)
				.onClick(click -> {
					try {
						MenuManager.switchMenu(player, new TownMenu(player, town));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player,this);

		fillEmptySlots("filler_nation_settings_menu");
	}
}