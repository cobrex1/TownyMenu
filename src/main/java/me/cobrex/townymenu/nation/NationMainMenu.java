package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownBlockType;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.*;
import org.bukkit.entity.Player;

public class NationMainMenu extends MenuHandler {

	private final Player player;
	private final Nation nation;
	private final Town town;

	public NationMainMenu(Player player) throws NotRegisteredException {
		super(
				player,
				MessageFormatter.format(Localization.NationMenu.MAIN_MENU_TITLE, player),
				getInventorySize(ConfigNodes.NATION_MAIN_MENU_SIZE)
		);

		this.player = player;
		this.nation = TownyAPI.getInstance().getNation(player);
		this.town = TownyAPI.getInstance().getTown(player);

		MenuItemBuilder.of("nation_toggle_button")
				.name(Localization.NationMenu.NATION_TOGGLE_MENU_BUTTON)
				.lore(Localization.NationMenu.NATION_TOGGLE_MENU_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new NationToggleMenu(player)))
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_town_list_button")
				.name(Localization.NationMenu.NATION_TOWN_LIST_BUTTON)
				.lore(Localization.NationMenu.NATION_TOWN_LIST_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new NationTownListMenu(player)))
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_resident_menu_button")
				.name(Localization.NationMenu.NATION_RESIDENT_MENU_BUTTON)
				.lore(Localization.NationMenu.NATION_RESIDENT_MENU_BUTTON_LORE)
				.onClick(click -> {
					try {
						MenuManager.switchMenu(player, new NationResidentListMenu(nation, player));
					} catch (NotRegisteredException ex) {
						throw new RuntimeException(ex);
					}
				})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_settings_menu_button")
				.name(Localization.NationMenu.NATION_SETTINGS_MENU_BUTTON)
				.lore(Localization.NationMenu.NATION_SETTINGS_MENU_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new NationSettingsMenu(player)))
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_invite_town_button")
				.name(Localization.NationMenu.NATION_INVITE_TOWN_MENU_BUTTON)
				.lore(Localization.NationMenu.NATION_INVITE_TOWN_MENU_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new InviteTownMenu(player)))
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_extra_info_button")
				.name(Localization.NationMenu.NATION_EXTRA_INFO_MENU_BUTTON)
				.lore(Localization.NationMenu.NATION_EXTRA_INFO_MENU_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new NationExtraInfoMenu(player)))
				.buildAndSet(player, this);

		boolean economyActive = Settings.ECONOMY_ENABLED && TownySettings.isUsingEconomy();

		String balanceDisplay = economyActive
				? Localization.NationMenu.BALANCE + Localization.NationMenu.BALANCE_AMOUNT + " " + nation.getAccount().getHoldingFormattedBalance()
				: Localization.NationMenu.BALANCE + Localization.NationMenu.BALANCE_AMOUNT + " &cDisabled";

		MenuItemBuilder.of("nation_menu_info_button")
				.name(Localization.NationMenu.NATION_NAME + nation.getName())
				.lore("")
				.lore(Localization.NationMenu.TOWNS + Localization.NationMenu.NUMBER_TOWNS + " " + nation.getNumTowns())
				.lore(Localization.NationMenu.RESIDENTS + Localization.NationMenu.NUMBER_RESIDENTS + " " + nation.getNumResidents())
				.lore(balanceDisplay)
				.lore(Localization.NationMenu.KING + Localization.NationMenu.KING_NAME + " " + nation.getKing())
				.onClick(click -> {})
				.buildAndSet(player, this);

		handleNationEconomyMenu();

		fillEmptySlots("filler_nation_main_menu");
	}

	private void handleNationEconomyMenu() throws NotRegisteredException {

		if (Settings.ECONOMY_ENABLED && TownySettings.isUsingEconomy()) {
//			System.out.println("[DEBUG] ECONOMY_ENABLED: " + Settings.ECONOMY_ENABLED);

			TownBlock block = TownyAPI.getInstance().getTownBlock(player.getLocation());

			if (TownySettings.isBankActionLimitedToBankPlots()) {
				if (block == null || !block.getType().equals(TownBlockType.BANK)) {
					MenuItemBuilder.of("nation_economy_menu_button")
							.name(Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON)
							.lore(Localization.NationMenu.NATION_ECONOMY_DISABLED_MENU_BUTTON_LORE)
							.onClick(click -> {
								MenuManager.closeMenu(player);
								MessageUtils.send(player, Localization.Error.MUST_BE_IN_BANK);
							})
							.buildAndSet(player, this);
					return;
				}
			} else if (TownySettings.isBankActionDisallowedOutsideTown()) {
				if (block == null || !block.getTown().equals(town)) {
					MenuItemBuilder.of("nation_economy_menu_button")
							.name(Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON)
							.lore(Localization.NationMenu.NATION_ECONOMY_DISABLED_MENU_BUTTON_LORE)
							.onClick(click -> {
								MenuManager.closeMenu(player);
								MessageUtils.send(player, Localization.Error.MUST_BE_IN_TOWN);
							})
							.buildAndSet(player, this);
					return;
				}
			}

			MenuItemBuilder.of("nation_economy_menu_button")
					.name(Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON)
					.lore(Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON_LORE)
					.onClick(click -> MenuManager.switchMenu(player, new NationEconomyMenu(player)))
					.buildAndSet(player, this);

		} else {
			MenuItemBuilder.of("nation_economy_menu_button")
					.name(Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON)
					.lore(Localization.NationMenu.NATION_ECONOMY_DISABLED_MENU_BUTTON_LORE)
					.onClick(click -> {})
					.buildAndSet(player, this);
		}
	}
}
