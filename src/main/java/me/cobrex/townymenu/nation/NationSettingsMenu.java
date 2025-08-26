package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.TownBlock;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.nation.prompt.NationBoardPrompt;
import me.cobrex.townymenu.nation.prompt.NationNamePrompt;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.*;
import org.bukkit.entity.Player;

public class NationSettingsMenu extends MenuHandler {

	private final Player player;
	private final Nation nation;

	public NationSettingsMenu(Player player) {
		super(
				player,
				MessageFormatter.format(Localization.NationMenu.NationSettingsMenu.MENU_TITLE, player),
				getInventorySize(ConfigNodes.NATION_SETTINGS_MENU_SIZE)
		);

		this.player = player;
		this.nation = TownyAPI.getInstance().getNation(player);

		MenuItemBuilder.of("nation_set_spawn_button")
				.name(Localization.NationMenu.NationSettingsMenu.SET_SPAWN)
				.lore(Localization.NationMenu.NationSettingsMenu.SET_SPAWN_LORE)
				.onClick(click -> {
					player.closeInventory();
					TownBlock townBlock = TownyAPI.getInstance().getTownBlock(player.getLocation());
					try {
						if ((!TownySettings.isNationSpawnOnlyAllowedInCapital() && nation.hasTown(townBlock.getTown())) ||
								(nation.hasTown(townBlock.getTown()) && townBlock.getTown().isCapital())) {
							MessageUtils.send(player, Localization.NationMenu.NationSettingsMenu.SET_SPAWN_MSG);
							TownyAPI.getInstance().getDataSource().saveNation(nation);
						} else {
							MessageUtils.send(player, Localization.Error.CANNOT_SET_SPAWN);
						}
					} catch (TownyException e) {
						player.sendMessage(Localization.Error.CANNOT_SET_HOMEBLOCK);
					}
					player.closeInventory();
				})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_set_name_button")
				.name(Localization.NationMenu.NationSettingsMenu.SET_NAME)
				.lore(Localization.NationMenu.NationSettingsMenu.SET_NAME_LORE)
				.onClick(click -> {
					player.closeInventory();
					if (nation.getKing().getName().equals(player.getName())) {
						new NationNamePrompt(player, nation).show(player);
					} else {
						MessageUtils.send(player, Localization.Error.CANNOT_CHANGE_NAME);
					}
				})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_set_board_button")
				.name(Localization.NationMenu.NationSettingsMenu.SET_BOARD)
				.lore(Localization.NationMenu.NationSettingsMenu.SET_BOARD_LORE)
				.onClick(click -> {
					player.closeInventory();
					if (nation.getKing().getName().equals(player.getName())) {
						new NationBoardPrompt(player).show(player);
					} else {
						MessageUtils.send(player, Localization.Error.CANNOT_CHANGE_BOARD);
					}
				})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_settings_info_button")
				.name(Localization.NationMenu.NationSettingsMenu.NATION_SETTINGS_MENU_INFO)
				.lore(Localization.NationMenu.NationSettingsMenu.NATION_SETTINGS_MENU_INFO_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("nation_settings_menu_back_button")
				.name(Localization.Back_Button.BACK_BUTTON_TITLE)
				.onClick(click -> {
					try {
						MenuManager.switchMenu(player, new NationMainMenu(player));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player,this);

		fillEmptySlots("filler_nation_settings_menu");
	}
}
