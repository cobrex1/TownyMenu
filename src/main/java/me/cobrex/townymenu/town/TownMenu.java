package me.cobrex.townymenu.town;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownBlockType;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.plot.PlotMenu;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.MenuHandler;
import me.cobrex.townymenu.utils.MenuItemBuilder;
import me.cobrex.townymenu.utils.MenuManager;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.entity.Player;


public class TownMenu extends MenuHandler {

	private final Player player;
	private final Town town;
	private final Resident resident;
	private final TownBlock townblock;

	public TownMenu(Player player, Town town) throws NotRegisteredException {
		super(
				player,
				Localization.TownMenu.MAIN_MENU_TITLE,
				getInventorySize(ConfigNodes.TOWN_MAIN_MENU_SIZE)
		);
//		Bukkit.getLogger().info("[DEBUG] Raw title string: " + Localization.TownMenu.MAIN_MENU_TITLE);
		this.player = player;
		this.town = town;
		this.resident = TownyAPI.getInstance().getResident(player);
		this.townblock = TownyAPI.getInstance().getTownBlock(player);

		setMenuItems();
	}

	private void setMenuItems() throws NotRegisteredException {

		MenuItemBuilder.of("town_toggle_button")
				.name(Localization.TownMenu.TOGGLE_MENU_BUTTON)
				.lore(Localization.TownMenu.TOGGLE_MENU_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new ToggleSettingsMenu(player)))
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_resident_list_button")
				.name(Localization.TownMenu.RESIDENT_MENU_BUTTON)
				.lore(Localization.TownMenu.RESIDENT_MENU_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new ResidentListMenu(player, town)))
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_permissions_menu_button")
				.name(Localization.TownMenu.PERMISSIONS_MENU_BUTTON)
				.lore(Localization.TownMenu.PERMISSIONS_MENU_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new TownyPermMenu(player, resident)))
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_plot_menu_button")
				.name(Localization.TownMenu.PLOT_MENU_BUTTON)
				.lore(Localization.TownMenu.PLOT_MENU_BUTTON_LORE)
				.onClick(click -> {
					player.closeInventory();
					try {
						MenuManager.switchMenu(player, new PlotMenu(player, townblock));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}

				})
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_settings_menu_button")
				.name(Localization.TownMenu.GENERAL_SETTINGS_MENU_BUTTON)
				.lore(Localization.TownMenu.GENERAL_SETTINGS_MENU_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new GeneralSettingsMenu(player, town)))
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_invite_menu_button")
				.name(Localization.TownMenu.INVITE_PLAYER_MENU_BUTTON)
				.lore(Localization.TownMenu.INVITE_PLAYER_MENU_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new InvitePlayerMenu(player)))
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_extra_info_button")
				.name(Localization.TownMenu.EXTRA_INFO_MENU_BUTTON)
				.lore(Localization.TownMenu.EXTRA_INFO_MENU_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new ExtraTownInfo(player)))
				.buildAndSet(player, this);

		String balanceDisplay = Settings.ECONOMY_ENABLED
				? Localization.TownMenu.BALANCE + Localization.TownMenu.BALANCE_AMOUNT + " " + town.getAccount().getHoldingFormattedBalance()
				: Localization.TownMenu.BALANCE + Localization.TownMenu.BALANCE_AMOUNT + " &cDisabled";

		if (town.hasNation()) {
		MenuItemBuilder.of("town_info_button")
				.name(Localization.TownMenu.TOWN_NAME + town.getName() +  " &7|" + Localization.TownMenu.TOWN_POSTFIX + town.getPostfix())
				.lore("")
				.lore(Localization.TownMenu.RESIDENTS + Localization.TownMenu.NUMBER_RESIDENTS + " " + town.getNumResidents())
				.lore(Localization.TownMenu.CLAIM_BLOCKS + Localization.TownMenu.TOTAL_CLAIMED_BLOCKS + town.getNumTownBlocks() + "&7/" + Localization.TownMenu.MAX_CLAIM_BLOCKS + town.getMaxTownBlocks())
				.lore(balanceDisplay)
				.lore(Localization.TownMenu.MAYOR + Localization.TownMenu.MAYOR_NAME + " " + town.getMayor())
				.lore(Localization.TownMenu.NATION + Localization.TownMenu.NATION_NAME + " " + town.getNation())
				.onClick(click -> {})
				.buildAndSet(player, this);

		} else
			MenuItemBuilder.of("town_info_button")
					.name(Localization.TownMenu.TOWN_NAME + town.getName() + " &7|" + Localization.TownMenu.TOWN_POSTFIX + town.getPostfix())
					.lore("")
					.lore(Localization.TownMenu.RESIDENTS + Localization.TownMenu.NUMBER_RESIDENTS + " " + town.getNumResidents())
					.lore(Localization.TownMenu.CLAIM_BLOCKS + Localization.TownMenu.TOTAL_CLAIMED_BLOCKS + town.getNumTownBlocks() + "&7/" + Localization.TownMenu.MAX_CLAIM_BLOCKS + town.getMaxTownBlocks())
					.lore(balanceDisplay)
					.lore(Localization.TownMenu.MAYOR + Localization.TownMenu.MAYOR_NAME + " " + town.getMayor())
					.lore(Localization.TownMenu.NATION)
					.onClick(click -> {})
					.buildAndSet(player, this);

//		System.out.println("[DEBUG] ECONOMY_ENABLED: " + Settings.ECONOMY_ENABLED);
		handleTownEconomyMenu();

		fillEmptySlots("filler_town_menu");
	}

	private void handleTownEconomyMenu() throws NotRegisteredException {

		if (Settings.ECONOMY_ENABLED) {
//			System.out.println("[DEBUG] ECONOMY_ENABLED: " + Settings.ECONOMY_ENABLED);
			TownBlock block = TownyAPI.getInstance().getTownBlock(player.getLocation());

			if (TownySettings.isBankActionLimitedToBankPlots()) {
				if (block == null || !block.getType().equals(TownBlockType.BANK)) {
					MenuItemBuilder.of("town_economy_menu_button")
							.name(Localization.TownMenu.ECONOMY_MENU_BUTTON)
							.lore(Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE)
							.onClick(click -> {
								MenuManager.closeMenu(player);
								MessageUtils.send(player, Localization.Error.MUST_BE_IN_BANK);
							})
							.buildAndSet(player, this);
					return;
				}
			} else if (TownySettings.isBankActionDisallowedOutsideTown()) {
				if (block == null || !block.getTown().equals(town)) {
					MenuItemBuilder.of("town_economy_menu_button")
							.name(Localization.TownMenu.ECONOMY_MENU_BUTTON)
							.lore(Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE)
							.onClick(click -> {
								MenuManager.closeMenu(player);
								MessageUtils.send(player, Localization.Error.MUST_BE_IN_TOWN);
							})
							.buildAndSet(player, this);
					return;
				}
			}

			MenuItemBuilder.of("town_economy_menu_button")
					.name(Localization.TownMenu.ECONOMY_MENU_BUTTON)
					.lore(Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE)
					.onClick(click -> MenuManager.switchMenu(player, new EconomyManagementMenu(player, town)))
					.buildAndSet(player, this);
		} else {
			MenuItemBuilder.of("town_economy_menu_button")
					.name(Localization.TownMenu.ECONOMY_DISABLED_MENU_BUTTON)
					.lore(Localization.TownMenu.ECONOMY_DISABLED_MENU_BUTTON_LORE)
					.onClick(click -> {})
					.buildAndSet(player, this);
		}
	}
}
