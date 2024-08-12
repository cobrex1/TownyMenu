package me.cobrex.townymenu.town;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.*;
import com.palmergames.bukkit.towny.tasks.CooldownTimerTask;
import com.palmergames.util.StringMgmt;
import lombok.SneakyThrows;
import me.cobrex.townymenu.nation.JoinNationMenu;
import me.cobrex.townymenu.nation.NationMenu;
import me.cobrex.townymenu.plot.PlotMenu;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.town.prompt.*;
import me.cobrex.townymenu.utils.HeadDatabaseUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.TimeUtil;
import org.mineacademy.fo.debug.LagCatcher;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonConversation;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.button.ButtonReturnBack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class TownMenu extends Menu {

	// TODO set up discord, spigot

	private final Button toggleMenuButton;
	private final Button residentListButton;
	private final Button townyPermButton;
	private final Button economyButton;
	private final Button generalSettingsButton;
	private final Button invitePlayerButton;
	private final ItemStack townInfoButton;
	private final Button extraInfoButton;
	private final Button plotMenuButton;
	private final Button nationMenuButton;

	private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_TOWN_MENU)), "")
			.modelData(Integer.valueOf(Settings.FILLER_TOWN_MENU_CMD)).make();

	public TownMenu(Town town, Player player) throws NotRegisteredException {

		List<Resident> residentList = town.getResidents();

		List<Resident> allOnlineResidents = new ArrayList<>();
		LagCatcher.start("load-residents-online");
		for (Player onLinePlayer : Bukkit.getOnlinePlayers()) {
			Resident res = TownyAPI.getInstance().getResident(onLinePlayer.getName());
			if (res != null && (!res.hasTown())) allOnlineResidents.add(res);
		}
		LagCatcher.end("loaded-residents-online", true);

		if (Settings.USE_FIXED_INVENTORY_SIZE) {
			setSize(9 * 6);
		} else {
			setSize(9 * 3);
		}
		ItemCreator toggleMenuItem;
		ItemCreator residentListItem;
		ItemCreator permissionsMenuItem;
		ItemCreator economyMenuItem;
		ItemCreator plotMenuItem;
		ItemCreator settingsMenuItem;
		ItemCreator inviteMenuItem;
		ItemCreator extraInfoItem;
		ItemCreator nationMenuItem;

		setTitle(Localization.TownMenu.MAIN_MENU_TITLE);

		if (Material.getMaterial(Settings.TOGGLE_MENU).equals(Material.LEATHER_BOOTS)) {
			toggleMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_MENU)))
					.name(Localization.TownMenu.TOGGLE_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.TOGGLE_MENU_CMD))
					.color(Settings.NEUTRAL_BUTTON_COLOR)
					.lore((List<String>) Localization.TownMenu.TOGGLE_MENU_BUTTON_LORE);
		} else {
			toggleMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_MENU)))
					.name(Localization.TownMenu.TOGGLE_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.TOGGLE_MENU_CMD))
					.lore((List<String>) Localization.TownMenu.TOGGLE_MENU_BUTTON_LORE);
		}


		if (Material.getMaterial(Settings.RESIDENT_LIST).equals(Material.LEATHER_BOOTS)) {
			residentListItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_LIST)))
					.name(Localization.TownMenu.RESIDENT_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.RESIDENT_LIST_CMD))
					.color(Settings.NEUTRAL_BUTTON_COLOR)
					.lore((List<String>) Localization.TownMenu.RESIDENT_MENU_BUTTON_LORE);
		} else {
			residentListItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_LIST)))
					.name(Localization.TownMenu.RESIDENT_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.RESIDENT_LIST_CMD))
					.lore((List<String>) Localization.TownMenu.RESIDENT_MENU_BUTTON_LORE);
		}


		if (Material.getMaterial(Settings.PERMISSIONS_MENU).equals(Material.LEATHER_BOOTS)) {
			permissionsMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PERMISSIONS_MENU)))
					.name(Localization.TownMenu.PERMISSIONS_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.PERMISSIONS_MENU_CMD))
					.color(Settings.NEUTRAL_BUTTON_COLOR)
					.lore((List<String>) Localization.TownMenu.PERMISSIONS_MENU_BUTTON_LORE);
		} else {
			permissionsMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PERMISSIONS_MENU)))
					.name(Localization.TownMenu.PERMISSIONS_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.PERMISSIONS_MENU_CMD))
					.lore((List<String>) Localization.TownMenu.PERMISSIONS_MENU_BUTTON_LORE);
		}


		if (Material.getMaterial(Settings.ECONOMY_MENU).equals(Material.LEATHER_BOOTS)) {
			economyMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ECONOMY_MENU)))
					.name(Localization.TownMenu.ECONOMY_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.ECONOMY_MENU_CMD))
					.color(Settings.NEUTRAL_BUTTON_COLOR)
					.lore((List<String>) Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE);
		} else {
			economyMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ECONOMY_MENU)))
					.name(Localization.TownMenu.ECONOMY_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.ECONOMY_MENU_CMD))
					.lore((List<String>) Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE);
		}


		if (Material.getMaterial(Settings.PLOT_MENU).equals(Material.LEATHER_BOOTS)) {
			plotMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_MENU)))
					.name(Localization.TownMenu.PLOT_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.PLOT_MENU_CMD))
					.color(Settings.NEUTRAL_BUTTON_COLOR)
					.lore((List<String>) Localization.TownMenu.PLOT_MENU_BUTTON_LORE);
		} else {
			plotMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_MENU)))
					.name(Localization.TownMenu.PLOT_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.PLOT_MENU_CMD))
					.lore((List<String>) Localization.TownMenu.PLOT_MENU_BUTTON_LORE);
		}


		if (Material.getMaterial(Settings.SETTINGS_MENU).equals(Material.LEATHER_BOOTS)) {
			settingsMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SETTINGS_MENU)))
					.name(Localization.TownMenu.GENERAL_SETTINGS_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.SETTINGS_MENU_CMD))
					.color(Settings.NEUTRAL_BUTTON_COLOR)
					.lore((List<String>) Localization.TownMenu.GENERAL_SETTINGS_MENU_BUTTON_LORE);
		} else {
			settingsMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SETTINGS_MENU)))
					.name(Localization.TownMenu.GENERAL_SETTINGS_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.SETTINGS_MENU_CMD))
					.lore((List<String>) Localization.TownMenu.GENERAL_SETTINGS_MENU_BUTTON_LORE);
		}


		if (Material.getMaterial(Settings.INVITE_MENU).equals(Material.LEATHER_BOOTS)) {
			inviteMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.INVITE_MENU)))
					.name(Localization.TownMenu.INVITE_PLAYER_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.INVITE_MENU_CMD))
					.color(Settings.NEUTRAL_BUTTON_COLOR)
					.lore((List<String>) Localization.TownMenu.INVITE_PLAYER_MENU_BUTTON_LORE);
		} else {
			inviteMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.INVITE_MENU)))
					.name(Localization.TownMenu.INVITE_PLAYER_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.INVITE_MENU_CMD))
					.lore((List<String>) Localization.TownMenu.INVITE_PLAYER_MENU_BUTTON_LORE);
		}


		if (Material.getMaterial(Settings.EXTRA_INFO).equals(Material.LEATHER_BOOTS)) {
			extraInfoItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.EXTRA_INFO)))
					.name(Localization.TownMenu.EXTRA_INFO_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.EXTRA_INFO_CMD))
					.color(Settings.NEUTRAL_BUTTON_COLOR)
					.lore((List<String>) Localization.TownMenu.EXTRA_INFO_MENU_BUTTON_LORE);
		} else {
			extraInfoItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.EXTRA_INFO)))
					.name(Localization.TownMenu.EXTRA_INFO_MENU_BUTTON)
					.modelData(Integer.valueOf(Settings.EXTRA_INFO_CMD))
					.lore((List<String>) Localization.TownMenu.EXTRA_INFO_MENU_BUTTON_LORE);
		}


		if (Material.getMaterial(Settings.TOGGLE_MENU).equals(Material.LEATHER_BOOTS)) {
			nationMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_MENU_BUTTON)))
					.name(Localization.TownMenu.NATION_MENU_BUTTON)
					.modelData(Settings.NATION_MENU_BUTTON_CMD)
					.color(Settings.NEUTRAL_BUTTON_COLOR)
					.lore((List<String>) Localization.TownMenu.NATION_MENU_BUTTON_LORE);
		} else {
			nationMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_MENU_BUTTON)))
					.name(Localization.TownMenu.NATION_MENU_BUTTON)
					.modelData(Settings.NATION_MENU_BUTTON_CMD)
					.lore((List<String>) Localization.TownMenu.NATION_MENU_BUTTON_LORE);
		}

		toggleMenuButton = new
				ButtonMenu(new ToggleSettingsMenu(town), toggleMenuItem);

		residentListButton = new
				ButtonMenu(new ResidentListMenu(residentList), residentListItem);

		townyPermButton = new
				ButtonMenu(new TownyPermMenu(town), permissionsMenuItem);

		if (Settings.ECONOMY_ENABLED) {
			if (TownySettings.isBankActionLimitedToBankPlots()) {
				if (TownyAPI.getInstance().getTownBlock(player.getLocation()) != null)
					if (!Objects.requireNonNull(TownyAPI.getInstance().getTownBlock(player.getLocation())).getType().equals(TownBlockType.BANK))
						economyButton = new Button() {
							@Override
							public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
								Common.tell(player, Localization.Error.MUST_BE_IN_BANK);
								player.closeInventory();
							}

							@Override
							public ItemStack getItem() {
								return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ECONOMY_MENU)))
										.name(Localization.TownMenu.ECONOMY_MENU_BUTTON)
										.modelData(Integer.valueOf(Settings.ECONOMY_MENU_CMD))
										.lore((List<String>) Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE).make();
							}
						};
					else
						economyButton = new ButtonMenu(new EconomyManagementMenu(town), economyMenuItem);
				else
					economyButton = new Button() {
						@Override
						public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
							Common.tell(player, Localization.Error.MUST_BE_IN_BANK);
							player.closeInventory();
						}

						@Override
						public ItemStack getItem() {
							return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ECONOMY_MENU)))
									.name(Localization.TownMenu.ECONOMY_MENU_BUTTON)
									.modelData(Integer.valueOf(Settings.ECONOMY_MENU_CMD))
									.lore((List<String>) Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE).make();
						}
					};
			} else if (TownySettings.isBankActionDisallowedOutsideTown()) {
				if (TownyAPI.getInstance().getTownBlock(player.getLocation()) != null)
					if (!Objects.requireNonNull(TownyAPI.getInstance().getTownBlock(player.getLocation())).getTown().equals(town)) {
						economyButton = new Button() {
							@Override
							public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
								Common.tell(player, Localization.Error.MUST_BE_IN_TOWN);
								player.closeInventory();
							}

							@Override
							public ItemStack getItem() {
								return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ECONOMY_MENU)))
										.name(Localization.TownMenu.ECONOMY_MENU_BUTTON)
										.modelData(Integer.valueOf(Settings.ECONOMY_MENU_CMD))
										.lore((List<String>) Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE).make();
							}
						};
					} else
						economyButton = new ButtonMenu(new EconomyManagementMenu(town), economyMenuItem);
				else {
					economyButton = new Button() {
						@Override
						public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
							Common.tell(player, Localization.Error.MUST_BE_IN_TOWN);
							player.closeInventory();
						}

						@Override
						public ItemStack getItem() {
							return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ECONOMY_MENU)))
									.name(Localization.TownMenu.ECONOMY_MENU_BUTTON)
									.modelData(Integer.valueOf(Settings.ECONOMY_MENU_CMD))
									.lore((List<String>) Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE).make();
						}
					};
				}

			} else
				economyButton = new ButtonMenu(new EconomyManagementMenu(town), economyMenuItem);
		} else {
			economyButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ECONOMY_MENU)))
							.name("Economy Disabled").make();
				}
			};
		}

		generalSettingsButton = new ButtonMenu(new GeneralSettingsMenu(town), settingsMenuItem);
		invitePlayerButton = new ButtonMenu(new InvitePlayerMenu(allOnlineResidents), inviteMenuItem);
		extraInfoButton = new ButtonMenu(new ExtraTownInfo(), extraInfoItem);

		if (Material.getMaterial(Settings.TOWN_INFO_BUTTON).equals(Material.LEATHER_BOOTS)) {
			if (Settings.ECONOMY_ENABLED) {

				if (town.hasNation()) {
					townInfoButton = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_INFO_BUTTON)))
							.name(Localization.TownMenu.TOWN_NAME + town.getName() + " " + "&7|" + (Localization.TownMenu.TOWN_POSTFIX + town.getPostfix()))
							.modelData(Integer.valueOf(Settings.TOWN_INFO_BUTTON_CMD))
							.color(Settings.NEUTRAL_BUTTON_COLOR)
							.lore("")
							.lore(Localization.TownMenu.RESIDENTS + (Localization.TownMenu.NUMBER_RESIDENTS + " " + town.getNumResidents()))
							.lore(Localization.TownMenu.CLAIM_BLOCKS + (Localization.TownMenu.TOTAL_CLAIMED_BLOCKS + " " + town.getNumTownBlocks()) + "" + "&7/" + (Localization.TownMenu.MAX_CLAIM_BLOCKS + "" + town.getMaxTownBlocks()))
							.lore(Localization.TownMenu.BALANCE + (Localization.TownMenu.BALANCE_AMOUNT + " " + town.getAccount().getHoldingFormattedBalance()))
							.lore(Localization.TownMenu.MAYOR + (Localization.TownMenu.MAYOR_NAME + " " + town.getMayor()))
							.lore((Localization.TownMenu.NATION + (Localization.TownMenu.NATION_NAME + " " + town.getNation()))).make();
				} else
					townInfoButton = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_INFO_BUTTON)))
							.name(Localization.TownMenu.TOWN_NAME + town.getName() + " " + "&7|" + (Localization.TownMenu.TOWN_POSTFIX + town.getPostfix()))
							.modelData(Integer.valueOf(Settings.TOWN_INFO_BUTTON_CMD))
							.color(Settings.NEUTRAL_BUTTON_COLOR)
							.lore("")
							.lore(Localization.TownMenu.RESIDENTS + (Localization.TownMenu.NUMBER_RESIDENTS + " " + town.getNumResidents()))
							.lore(Localization.TownMenu.CLAIM_BLOCKS + (Localization.TownMenu.TOTAL_CLAIMED_BLOCKS + " " + town.getNumTownBlocks()) + "" + "&7/" + (Localization.TownMenu.MAX_CLAIM_BLOCKS + "" + town.getMaxTownBlocks()))
							.lore(Localization.TownMenu.BALANCE + (Localization.TownMenu.BALANCE_AMOUNT + " " + town.getAccount().getHoldingFormattedBalance()))
							.lore(Localization.TownMenu.MAYOR + (Localization.TownMenu.MAYOR_NAME + " " + town.getMayor()))
							.lore(Localization.TownMenu.NATION + (Localization.TownMenu.NATION_NAME + "")).make();

			} else {

				if (town.hasNation()) {
					townInfoButton = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_INFO_BUTTON)))
							.name(Localization.TownMenu.TOWN_NAME + town.getName() + " " + "&7|" + (Localization.TownMenu.TOWN_POSTFIX + town.getPostfix()))
							.modelData(Integer.valueOf(Settings.TOWN_INFO_BUTTON_CMD))
							.color(Settings.NEUTRAL_BUTTON_COLOR)
							.lore("")
							.lore(Localization.TownMenu.RESIDENTS + (Localization.TownMenu.NUMBER_RESIDENTS + " " + town.getNumResidents()))
							.lore(Localization.TownMenu.CLAIM_BLOCKS + (Localization.TownMenu.TOTAL_CLAIMED_BLOCKS + " " + town.getNumTownBlocks()) + "" + "&7/" + (Localization.TownMenu.MAX_CLAIM_BLOCKS + "" + town.getMaxTownBlocks()))
//						.lore(Localization.TownMenu.BALANCE + (Localization.TownMenu.BALANCE_AMOUNT + " " + town.getAccount().getHoldingFormattedBalance()))
							.lore(Localization.TownMenu.MAYOR + (Localization.TownMenu.MAYOR_NAME + " " + town.getMayor()))
							.lore((Localization.TownMenu.NATION + (Localization.TownMenu.NATION_NAME + " " + town.getNation()))).make();
				} else
					townInfoButton = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_INFO_BUTTON)))
							.name(Localization.TownMenu.TOWN_NAME + town.getName() + " " + "&7|" + (Localization.TownMenu.TOWN_POSTFIX + town.getPostfix()))
							.modelData(Integer.valueOf(Settings.TOWN_INFO_BUTTON_CMD))
							.color(Settings.NEUTRAL_BUTTON_COLOR)
							.lore("")
							.lore(Localization.TownMenu.RESIDENTS + (Localization.TownMenu.NUMBER_RESIDENTS + " " + town.getNumResidents()))
							.lore(Localization.TownMenu.CLAIM_BLOCKS + (Localization.TownMenu.TOTAL_CLAIMED_BLOCKS + " " + town.getNumTownBlocks()) + "" + "&7/" + (Localization.TownMenu.MAX_CLAIM_BLOCKS + "" + town.getMaxTownBlocks()))
//						.lore(Localization.TownMenu.BALANCE + (Localization.TownMenu.BALANCE_AMOUNT + " " + town.getAccount().getHoldingFormattedBalance()))
							.lore(Localization.TownMenu.MAYOR + (Localization.TownMenu.MAYOR_NAME + " " + town.getMayor()))
							.lore(Localization.TownMenu.NATION + (Localization.TownMenu.NATION_NAME + "")).make();


			}
		} else {
			if (Settings.ECONOMY_ENABLED) {

				if (town.hasNation()) {
					townInfoButton = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_INFO_BUTTON)))
							.name(Localization.TownMenu.TOWN_NAME + town.getName() + " " + "&7|" + (Localization.TownMenu.TOWN_POSTFIX + town.getPostfix()))
							.modelData(Integer.valueOf(Settings.TOWN_INFO_BUTTON_CMD))
							.lore("")
							.lore(Localization.TownMenu.RESIDENTS + (Localization.TownMenu.NUMBER_RESIDENTS + " " + town.getNumResidents()))
							.lore(Localization.TownMenu.CLAIM_BLOCKS + (Localization.TownMenu.TOTAL_CLAIMED_BLOCKS + " " + town.getNumTownBlocks()) + "" + "&7/" + (Localization.TownMenu.MAX_CLAIM_BLOCKS + "" + town.getMaxTownBlocks()))
							.lore(Localization.TownMenu.BALANCE + (Localization.TownMenu.BALANCE_AMOUNT + " " + town.getAccount().getHoldingFormattedBalance()))
							.lore(Localization.TownMenu.MAYOR + (Localization.TownMenu.MAYOR_NAME + " " + town.getMayor()))
							.lore((Localization.TownMenu.NATION + (Localization.TownMenu.NATION_NAME + " " + town.getNation()))).make();
				} else
					townInfoButton = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_INFO_BUTTON)))
							.name(Localization.TownMenu.TOWN_NAME + town.getName() + " " + "&7|" + (Localization.TownMenu.TOWN_POSTFIX + town.getPostfix()))
							.modelData(Integer.valueOf(Settings.TOWN_INFO_BUTTON_CMD))
							.lore("")
							.lore(Localization.TownMenu.RESIDENTS + (Localization.TownMenu.NUMBER_RESIDENTS + " " + town.getNumResidents()))
							.lore(Localization.TownMenu.CLAIM_BLOCKS + (Localization.TownMenu.TOTAL_CLAIMED_BLOCKS + " " + town.getNumTownBlocks()) + "" + "&7/" + (Localization.TownMenu.MAX_CLAIM_BLOCKS + "" + town.getMaxTownBlocks()))
							.lore(Localization.TownMenu.BALANCE + (Localization.TownMenu.BALANCE_AMOUNT + " " + town.getAccount().getHoldingFormattedBalance()))
							.lore(Localization.TownMenu.MAYOR + (Localization.TownMenu.MAYOR_NAME + " " + town.getMayor()))
							.lore(Localization.TownMenu.NATION + (Localization.TownMenu.NATION_NAME + "")).make();

			} else {

				if (town.hasNation()) {
					townInfoButton = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_INFO_BUTTON)))
							.name(Localization.TownMenu.TOWN_NAME + town.getName() + " " + "&7|" + (Localization.TownMenu.TOWN_POSTFIX + town.getPostfix()))
							.modelData(Integer.valueOf(Settings.TOWN_INFO_BUTTON_CMD))
							.lore("")
							.lore(Localization.TownMenu.RESIDENTS + (Localization.TownMenu.NUMBER_RESIDENTS + " " + town.getNumResidents()))
							.lore(Localization.TownMenu.CLAIM_BLOCKS + (Localization.TownMenu.TOTAL_CLAIMED_BLOCKS + " " + town.getNumTownBlocks()) + "" + "&7/" + (Localization.TownMenu.MAX_CLAIM_BLOCKS + "" + town.getMaxTownBlocks()))
//						.lore(Localization.TownMenu.BALANCE + (Localization.TownMenu.BALANCE_AMOUNT + " " + town.getAccount().getHoldingFormattedBalance()))
							.lore(Localization.TownMenu.MAYOR + (Localization.TownMenu.MAYOR_NAME + " " + town.getMayor()))
							.lore((Localization.TownMenu.NATION + (Localization.TownMenu.NATION_NAME + " " + town.getNation()))).make();
				} else
					townInfoButton = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_INFO_BUTTON)))
							.name(Localization.TownMenu.TOWN_NAME + town.getName() + " " + "&7|" + (Localization.TownMenu.TOWN_POSTFIX + town.getPostfix()))
							.modelData(Integer.valueOf(Settings.TOWN_INFO_BUTTON_CMD))
							.lore("")
							.lore(Localization.TownMenu.RESIDENTS + (Localization.TownMenu.NUMBER_RESIDENTS + " " + town.getNumResidents()))
							.lore(Localization.TownMenu.CLAIM_BLOCKS + (Localization.TownMenu.TOTAL_CLAIMED_BLOCKS + " " + town.getNumTownBlocks()) + "" + "&7/" + (Localization.TownMenu.MAX_CLAIM_BLOCKS + "" + town.getMaxTownBlocks()))
//						.lore(Localization.TownMenu.BALANCE + (Localization.TownMenu.BALANCE_AMOUNT + " " + town.getAccount().getHoldingFormattedBalance()))
							.lore(Localization.TownMenu.MAYOR + (Localization.TownMenu.MAYOR_NAME + " " + town.getMayor()))
							.lore(Localization.TownMenu.NATION + (Localization.TownMenu.NATION_NAME + "")).make();


			}
		}


		if (TownyAPI.getInstance().getTownBlock(player.getLocation()) != null && town.hasTownBlock(TownyAPI.getInstance().getTownBlock(player.getLocation()))) {
			plotMenuButton = new ButtonMenu(new PlotMenu(TownyAPI.getInstance().getTownBlock(player.getLocation())), plotMenuItem);

		} else {
			plotMenuButton = Button.makeDummy(plotMenuItem);

		}

		if (town.hasNation()) {
			nationMenuButton = new ButtonMenu(new NationMenu(town.getNation(), player), nationMenuItem);

		} else {
			//nationMenuButton = Button.makeDummy(nationMenuItem);
			nationMenuButton = new ButtonMenu(new JoinNationMenu(null, player), nationMenuItem);

		}
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (!Settings.USE_FIXED_INVENTORY_SIZE) {
			if (slot == 2)
				return toggleMenuButton.getItem();
			if (slot == 4)
				return residentListButton.getItem();
			if (slot == 6)
				return townyPermButton.getItem();
			if (slot == 11)
				return generalSettingsButton.getItem();
			if (slot == 13 && Settings.ECONOMY_ENABLED)
				return economyButton.getItem();
			if (slot == 15)
				return invitePlayerButton.getItem();
			if (slot == 9 * 2)
				return townInfoButton;
			if (slot == 9 * 2 + 2)
				return extraInfoButton.getItem();
			if (slot == 9 * 2 + 5)
				return plotMenuButton.getItem();
			if (slot == 9 * 2 + 6)
				return nationMenuButton.getItem();

		} else {
			if (slot == 45)
				return toggleMenuButton.getItem();
			if (slot == 51)
				return residentListButton.getItem();
			if (slot == 53)
				return townyPermButton.getItem();
			if (slot == 46)
				return generalSettingsButton.getItem();
			if (slot == 49 && Settings.ECONOMY_ENABLED)
				return economyButton.getItem();
			if (slot == 52)
				return invitePlayerButton.getItem();
			if (slot == 13)
				return townInfoButton;
			if (slot == 47)
				return extraInfoButton.getItem();
			if (slot == 16)
				return plotMenuButton.getItem();
			if (slot == 10)
				return nationMenuButton.getItem();
		}


		return DUMMY_BUTTON;
	}

	public class ToggleSettingsMenu extends Menu {
		private final Button fireToggle;
		private final Button mobsToggle;
		private final Button explosionToggle;
		private final Button pvpToggle;
		private final Button publicToggle;
		private final Button openToggle;
		private final Button taxPercentToggle;

		private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_TOWN_TOGGLE)), "")
				.modelData(Integer.valueOf(Settings.FILLER_TOWN_TOGGLE_CMD)).make();

		@Override
		public String[] getInfo() {
			return Localization.TownMenu.ToggleMenu.INFO;
		}

		public ToggleSettingsMenu(Town town) {
			super(TownMenu.this);

			if (Settings.USE_FIXED_INVENTORY_SIZE) {
				setSize(9 * 6);
			} else {
				setSize(9 * 2);
			}

			if (Settings.BACK_BUTTON.getMaterial().equals(Material.LEATHER_BOOTS)) {
				ButtonReturnBack.setItemStack(ItemCreator
						.of(Settings.BACK_BUTTON)
						.name(Localization.Back_Button.BACK_BUTTON_TITLE)
						.lore((List<String>) Localization.Back_Button.BACK_BUTTON_LORE)
								.color(Settings.NEUTRAL_BUTTON_COLOR)
						.modelData(Settings.BACK_BUTTON_CMD)
						.make());
			} else {
				ButtonReturnBack.setItemStack(ItemCreator
						.of(Settings.BACK_BUTTON)
						.name(Localization.Back_Button.BACK_BUTTON_TITLE)
						.lore((List<String>) Localization.Back_Button.BACK_BUTTON_LORE)
						.modelData(Settings.BACK_BUTTON_CMD)
						.make());
			}


			setTitle(Localization.TownMenu.ToggleMenu.MENU_TITLE);

			fireToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.setFire(!town.isFire());
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.TOGGLE_PVP).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_FIRE)))
								.name(Localization.TownMenu.ToggleMenu.FIRE)
								.color(town.isFire() ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.TOGGLE_FIRE_CMD))
								.lore("")
								.lore("" + (town.isFire() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_FIRE)))
								.name(Localization.TownMenu.ToggleMenu.FIRE)
								.modelData(Integer.valueOf(Settings.TOGGLE_FIRE_CMD))
								.lore("")
								.lore("" + (town.isFire() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					}
				}
			};
			mobsToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.setHasMobs(!town.hasMobs());
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.TOGGLE_PVP).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_MOBS)))
								.name(Localization.TownMenu.ToggleMenu.MOBS)
								.color(town.hasMobs() ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.TOGGLE_MOBS_CMD))
								.lore("")
								.lore("" + (town.hasMobs() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_MOBS)))
								.name(Localization.TownMenu.ToggleMenu.MOBS)
								.modelData(Integer.valueOf(Settings.TOGGLE_MOBS_CMD))
								.lore("")
								.lore("" + (town.hasMobs() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					}
				}
			};
			explosionToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.setExplosion(!town.isExplosion());
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.TOGGLE_PVP).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_EXPLOSIONS)))
								.name(Localization.TownMenu.ToggleMenu.EXPLODE)
								.color(town.isExplosion() ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.TOGGLE_EXPLOSIONS_CMD))
								.lore("")
								.lore("" + (town.isExplosion() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_EXPLOSIONS)))
								.name(Localization.TownMenu.ToggleMenu.EXPLODE)
								.modelData(Integer.valueOf(Settings.TOGGLE_EXPLOSIONS_CMD))
								.lore("")
								.lore("" + (town.isExplosion() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					}
				}
			};
			pvpToggle = new Button() {
				@SneakyThrows
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					if (TownySettings.getOutsidersPreventPVPToggle()) {
						Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
						for (Player onlinePlayer : onlinePlayers) {
							Resident onlinePlayerAsRes = TownyAPI.getInstance().getResident(onlinePlayer.getName());
							if (onlinePlayerAsRes.hasTown()) {
								if (!onlinePlayerAsRes.getTown().equals(town))
									if (TownyAPI.getInstance().getTownBlock(onlinePlayer.getLocation()) != null)
										if (Objects.requireNonNull(TownyAPI.getInstance().getTownBlock(onlinePlayer.getLocation())).getTown().equals(town)) {
											Common.tell(player, Localization.Error.TOGGLE_PVP_OUTSIDERS);
											player.closeInventory();
											return;
										}
							} else {
								if (TownyAPI.getInstance().getTownBlock(onlinePlayer.getLocation()) != null)
									if (Objects.requireNonNull(TownyAPI.getInstance().getTownBlock(onlinePlayer.getLocation())).getTown().equals(town)) {
										Common.tell(player, Localization.Error.TOGGLE_PVP_OUTSIDERS);
										player.closeInventory();
										return;
									}
							}
						}
					}
					if (TownySettings.getPVPCoolDownTime() > 0) {
						if (CooldownTimerTask.hasCooldown(town.getName(), CooldownTimerTask.CooldownType.PVP)) {
							Common.tell(player, Localization.Error.TOGGLE_PVP_COOLDOWN);
							player.closeInventory();
							return;
						}
					}
					town.setPVP(!town.isPVP());
					CooldownTimerTask.addCooldownTimer(town.getName(), CooldownTimerTask.CooldownType.PVP);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.TOGGLE_PVP).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_PVP)))
								.name(Localization.TownMenu.ToggleMenu.PVP)
								.color(town.isPVP() ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.TOGGLE_PVP_CMD))
								.lore("")
								.lore("" + (town.isPVP() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_PVP)))
								.name(Localization.TownMenu.ToggleMenu.PVP)
								.modelData(Integer.valueOf(Settings.TOGGLE_PVP_CMD))
								.lore("")
								.lore("" + (town.isPVP() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					}
				}
			};
			publicToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.setPublic(!town.isPublic());
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.TOGGLE_PVP).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_PUBLIC)))
								.name(Localization.TownMenu.ToggleMenu.PUBLIC)
								.color(town.isPublic() ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.TOGGLE_PUBLIC_CMD))
								.lore("")
								.lore("" + (town.isPublic() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_PUBLIC)))
								.name(Localization.TownMenu.ToggleMenu.PUBLIC)
								.modelData(Integer.valueOf(Settings.TOGGLE_PUBLIC_CMD))
								.lore("")
								.lore("" + (town.isPublic() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					}

				}
			};
			openToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.setOpen(!town.isOpen());
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.TOGGLE_PVP).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_OPEN)))
								.name(Localization.TownMenu.ToggleMenu.OPEN)
								.color(town.isOpen() ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.TOGGLE_OPEN_CMD))
								.lore("")
								.lore("" + (town.isOpen() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_OPEN)))
								.name(Localization.TownMenu.ToggleMenu.OPEN)
								.modelData(Integer.valueOf(Settings.TOGGLE_OPEN_CMD))
								.lore("")
								.lore("" + (town.isOpen() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					}

				}
			};
			taxPercentToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {

					town.setTaxPercentage(!town.isTaxPercentage());
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.TOGGLE_PVP).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_TAX_PERCENTAGE)))
								.name(Localization.TownMenu.ToggleMenu.TAX_PERCENT)
								.color(town.isTaxPercentage() ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.TOGGLE_TAX_PERCENTAGE_CMD))
								.lore("")
								.lore("" + (town.isTaxPercentage() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOGGLE_TAX_PERCENTAGE)))
								.name(Localization.TownMenu.ToggleMenu.TAX_PERCENT)
								.modelData(Integer.valueOf(Settings.TOGGLE_TAX_PERCENTAGE_CMD))
								.lore("")
								.lore("" + (town.isTaxPercentage() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON)).make();
					}

				}
			};
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (!Settings.USE_FIXED_INVENTORY_SIZE) {
				if (slot == 1)
					return fireToggle.getItem();
				if (slot == 2)
					return mobsToggle.getItem();
				if (slot == 3)
					return explosionToggle.getItem();
				if (slot == 4)
					return pvpToggle.getItem();
				if (slot == 5)
					return publicToggle.getItem();
				if (slot == 6)
					return openToggle.getItem();
				if (slot == 7)
					return taxPercentToggle.getItem();
			} else {
				if (slot == 10)
					return fireToggle.getItem();
				if (slot == 11)
					return mobsToggle.getItem();
				if (slot == 12)
					return explosionToggle.getItem();
				if (slot == 13)
					return pvpToggle.getItem();
				if (slot == 14)
					return publicToggle.getItem();
				if (slot == 15)
					return openToggle.getItem();
				if (slot == 16)
					return taxPercentToggle.getItem();
			}
			return DUMMY_BUTTON;
		}
	}

	public class ResidentListMenu extends MenuPagged<Resident> {

		@Override
		public String[] getInfo() {
			return Localization.TownMenu.ResidentMenu.INFO;
		}

		protected ResidentListMenu(Iterable<Resident> pages) {
			super(TownMenu.this, pages);

			if (Settings.USE_FIXED_INVENTORY_SIZE) {
				setSize(9 * 6);
			}

			setTitle(Localization.TownMenu.ResidentMenu.MENU_LIST_TITLE);
			Button.setInfoButtonTitle(Localization.MENU_INFORMATION);
		}

		@Override
		protected ItemStack convertToItemStack(Resident resident) {

//			LagCatcher.start("load-player-skulls");
			ItemStack itemSkull = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta skull = (SkullMeta) itemSkull.getItemMeta();
			if (resident.getUUID() == null)
				return DUMMY_BUTTON;
//			skull.setOwningPlayer(item.getPlayer());
			PlayerProfile profile = Bukkit.createPlayerProfile(resident.getUUID(), resident.getName());
			skull.setOwnerProfile(profile);
			skull.setCustomModelData(Settings.RESIDENT_LIST_PLAYER_HEAD_CMD);
			skull.setDisplayName(ChatColor.translateAlternateColorCodes('&',
					Localization.TownMenu.ResidentMenu.RESIDENT_NAME + resident.getFormattedName()));
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add(ChatColor.translateAlternateColorCodes('&', Localization.TownMenu.ResidentMenu.TOWN_RANK +
					ChatColor.translateAlternateColorCodes('&', StringMgmt.join(resident.getTownRanks(), ""))));
			lore.add("");
			lore.add(ChatColor.translateAlternateColorCodes('&', Localization.TownMenu.ResidentMenu.ONLINE +
					TimeUtil.getFormattedDateShort(resident.getLastOnline())));
			skull.setLore(lore);
			itemSkull.setItemMeta(skull);
//			LagCatcher.end("load-player-skulls", true);
			return itemSkull;
		}

		@Override
		protected void onPageClick(Player player, Resident resident, ClickType click) {
			if (resident.getName().equals(player.getName())) {
				Common.tell(player, Localization.Error.CANNOT_SELECT_SELF);
				player.closeInventory();
				return;
			}
			new ResidentMenu(resident).displayTo(player);
		}
	}

	public class ResidentMenu extends Menu {
		public String[] getInfo() {
			return Localization.TownMenu.ResidentMenu.INFO_EDIT;
		}
		private final Button playerButton;
		private final Button kickButton;
		private final Button titleButton;
		private final Button rankButton;
		private final Button mayorButton;

		protected ResidentMenu(Resident resident) {
			super(TownMenu.this);

			if (Settings.USE_FIXED_INVENTORY_SIZE) {
				setSize(9 * 6);
			}

			setTitle(Localization.TownMenu.ResidentMenu.MENU_TITLE);

			playerButton = new ButtonConversation(new TownPlayerTitlePrompt(resident),
					ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(Material.PLAYER_HEAD.toString()))
							.skullOwner(resident.getName())
							.name(ChatColor.translateAlternateColorCodes('&',
									Localization.TownMenu.ResidentMenu.RESIDENT_NAME + resident.getFormattedName()))
							.modelData(Settings.RESIDENT_PLAYER_HEAD_CMD)
							.lore("")
							.lore(ChatColor.translateAlternateColorCodes('&', Localization.TownMenu.ResidentMenu.TOWN_RANK +
									ChatColor.translateAlternateColorCodes('&', StringMgmt.join(resident.getTownRanks(), ""))))
							.lore("")
							.lore(ChatColor.translateAlternateColorCodes('&', Localization.TownMenu.ResidentMenu.ONLINE +
									TimeUtil.getFormattedDateShort(resident.getLastOnline())))
			);

			if (Material.getMaterial(Settings.RESIDENT_KICK).equals(Material.LEATHER_BOOTS)) {
				kickButton = new ButtonConversation(new TownKickPrompt(resident),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_KICK)))
								.name(Localization.TownMenu.ResidentMenu.KICK)
								.color(Settings.NEGATIVE_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.RESIDENT_KICK_CMD))
								.lore((List<String>) Localization.TownMenu.ResidentMenu.KICK_LORE));
			} else {
				kickButton = new ButtonConversation(new TownKickPrompt(resident),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_KICK)))
								.name(Localization.TownMenu.ResidentMenu.KICK)
								.modelData(Integer.valueOf(Settings.RESIDENT_KICK_CMD))
								.lore((List<String>) Localization.TownMenu.ResidentMenu.KICK_LORE));
			}

			if (Material.getMaterial(Settings.RESIDENT_TITLE).equals(Material.LEATHER_BOOTS)) {
				titleButton = new ButtonConversation(new TownPlayerTitlePrompt(resident),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_TITLE)))
								.name(Localization.TownMenu.ResidentMenu.TITLE)
								.color(Settings.NEUTRAL_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.RESIDENT_TITLE_CMD))
								.lore((List<String>) Localization.TownMenu.ResidentMenu.TITLE_LORE));
			} else {
				titleButton = new ButtonConversation(new TownPlayerTitlePrompt(resident),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_TITLE)))
								.name(Localization.TownMenu.ResidentMenu.TITLE)
								.modelData(Integer.valueOf(Settings.RESIDENT_TITLE_CMD))
								.lore((List<String>) Localization.TownMenu.ResidentMenu.TITLE_LORE));
			}

			if (Material.getMaterial(Settings.RESIDENT_RANK).equals(Material.LEATHER_BOOTS)) {
				rankButton = new ButtonConversation(new TownRankPrompt(resident),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_RANK)))
								.name(Localization.TownMenu.ResidentMenu.RANK)
								.color(Settings.NEUTRAL_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.RESIDENT_RANK_CMD))
								.lore((List<String>) Localization.TownMenu.ResidentMenu.RANK_LORE));
			} else {
				rankButton = new ButtonConversation(new TownRankPrompt(resident),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_RANK)))
								.name(Localization.TownMenu.ResidentMenu.RANK)
								.modelData(Integer.valueOf(Settings.RESIDENT_RANK_CMD))
								.lore((List<String>) Localization.TownMenu.ResidentMenu.RANK_LORE));
			}

			if (Material.getMaterial(Settings.RESIDENT_MAYOR).equals(Material.LEATHER_BOOTS)) {
				mayorButton = new ButtonConversation(new TownGiveMayorPrompt(resident),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_MAYOR)))
								.name(Localization.TownMenu.ResidentMenu.MAYOR)
								.color(Settings.NEGATIVE_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.RESIDENT_MAYOR_CMD))
								.lore((List<String>) Localization.TownMenu.ResidentMenu.MAYOR_LORE));
			} else {
				mayorButton = new ButtonConversation(new TownGiveMayorPrompt(resident),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_MAYOR)))
								.name(Localization.TownMenu.ResidentMenu.MAYOR)
								.modelData(Integer.valueOf(Settings.RESIDENT_MAYOR_CMD))
								.lore((List<String>) Localization.TownMenu.ResidentMenu.MAYOR_LORE));
			}

		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (!Settings.USE_FIXED_INVENTORY_SIZE) {
				if (slot == 4 )
					return playerButton.getItem();
				if (slot == 9 + 1)
					return kickButton.getItem();
				if (slot == 9 + 3)
					return titleButton.getItem();
				if (slot == 9 + 5)
					return rankButton.getItem();
				if (slot == 9 + 7)
					return mayorButton.getItem();
			} else {
				if (slot == 13 )
					return playerButton.getItem();
				if (slot == 0)
					return kickButton.getItem();
				if (slot == 8)
					return titleButton.getItem();
				if (slot == 17)
					return rankButton.getItem();
				if (slot == 9)
					return mayorButton.getItem();
			}


			return DUMMY_BUTTON;
		}
	}

	public class TownyPermMenu extends Menu {

		private final ItemStack BUILD_BUTTON = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.BUILD)))
				.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD)
				.modelData(Integer.valueOf(Settings.BUILD_CMD))
				.lore((List<String>) Localization.TownMenu.PlayerPermissionsMenu.BUILD_LORE).make();
		private final ItemStack BREAK_BUTTON = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.BREAK)))
				.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK)
				.modelData(Integer.valueOf(Settings.BREAK_CMD))
				.lore((List<String>) Localization.TownMenu.PlayerPermissionsMenu.BREAK_LORE).make();
		private final ItemStack ITEM_USE_BUTTON = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ITEM_USE)))
				.name(Localization.TownMenu.PlayerPermissionsMenu.USE)
				.modelData(Integer.valueOf(Settings.ITEM_USE_CMD))
				.lore((List<String>) Localization.TownMenu.PlayerPermissionsMenu.USE_LORE).make();
		private final ItemStack SWITCH_BUTTON = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SWITCH)))
				.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH)
				.modelData(Integer.valueOf(Settings.SWITCH_CMD))
				.lore((List<String>) Localization.TownMenu.PlayerPermissionsMenu.SWITCH_LORE).make();

		private final ItemStack BUILD_BUTTON_COLORED = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem("LEATHER_BOOTS"))
				.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD)
				.modelData(Integer.valueOf(Settings.BUILD_CMD))
				.color(Settings.NEUTRAL_BUTTON_COLOR)
				.lore((List<String>) Localization.TownMenu.PlayerPermissionsMenu.BUILD_LORE).make();
		private final ItemStack BREAK_BUTTON_COLORED = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem("LEATHER_BOOTS"))
				.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK)
				.modelData(Integer.valueOf(Settings.BREAK_CMD))
				.color(Settings.NEUTRAL_BUTTON_COLOR)
				.lore((List<String>) Localization.TownMenu.PlayerPermissionsMenu.BREAK_LORE).make();
		private final ItemStack ITEM_USE_BUTTON_COLORED = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem("LEATHER_BOOTS"))
				.name(Localization.TownMenu.PlayerPermissionsMenu.USE)
				.modelData(Integer.valueOf(Settings.ITEM_USE_CMD))
				.color(Settings.NEUTRAL_BUTTON_COLOR)
				.lore((List<String>) Localization.TownMenu.PlayerPermissionsMenu.USE_LORE).make();
		private final ItemStack SWITCH_BUTTON_COLORED = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem("LEATHER_BOOTS"))
				.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH)
				.modelData(Integer.valueOf(Settings.SWITCH_CMD))
				.color(Settings.NEUTRAL_BUTTON_COLOR)
				.lore((List<String>) Localization.TownMenu.PlayerPermissionsMenu.SWITCH_LORE).make();



		private final Button buildResidentButton;
		private final Button buildNationButton;
		private final Button buildAllyButton;
		private final Button buildOutsiderButton;

		private final Button breakResidentButton;
		private final Button breakNationButton;
		private final Button breakAllyButton;
		private final Button breakOutsiderButton;

		private final Button itemUseResidentButton;
		private final Button itemUseNationButton;
		private final Button itemUseAllyButton;
		private final Button itemUseOutsiderButton;

		private final Button switchResidentButton;
		private final Button switchNationButton;
		private final Button switchAllyButton;
		private final Button switchOutsiderButton;

		private final Button resetButton;
		private final Button allOnButton;

		private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_TOWN_PERMS_MENU)), "")
				.modelData(Integer.valueOf(Settings.FILLER_TOWN_PERMS_MENU_CMD)).make();


		@Override
		public String[] getInfo() {
			return Localization.TownMenu.PlayerPermissionsMenu.INFO;
		}

		protected TownyPermMenu(Town town) {
			super(TownMenu.this);
			setSize(9 * 6);
			setTitle(Localization.TownMenu.PlayerPermissionsMenu.MENU_TITLE);
			Button.setInfoButtonTitle(Localization.MENU_INFORMATION);

			buildResidentButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD), TownyPermission.PermLevel.RESIDENT, TownyPermission.ActionType.BUILD);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.RESIDENT_BUILD).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_BUILD)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD_RES)
								.modelData(Integer.valueOf(Settings.RESIDENT_BUILD_CMD))
								.color(town.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BUILD_RES2, town.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_BUILD)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD_RES)
								.modelData(Integer.valueOf(Settings.RESIDENT_BUILD_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BUILD_RES2, town.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			buildNationButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD), TownyPermission.PermLevel.NATION, TownyPermission.ActionType.BUILD);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.NATION_BUILD).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_BUILD)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD_NATION)
								.modelData(Integer.valueOf(Settings.NATION_BUILD_CMD))
								.color(town.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BUILD_NATION2, town.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_BUILD)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD_NATION)
								.modelData(Integer.valueOf(Settings.NATION_BUILD_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BUILD_NATION2, town.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			buildAllyButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD), TownyPermission.PermLevel.ALLY, TownyPermission.ActionType.BUILD);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.ALLY_BUILD).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ALLY_BUILD)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD_ALLY)
								.modelData(Integer.valueOf(Settings.ALLY_BUILD_CMD))
								.color(town.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BUILD_ALLY2, town.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ALLY_BUILD)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD_ALLY)
								.modelData(Integer.valueOf(Settings.ALLY_BUILD_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BUILD_ALLY2, town.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			buildOutsiderButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD), TownyPermission.PermLevel.OUTSIDER, TownyPermission.ActionType.BUILD);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.OUTSIDER_BUILD).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.OUTSIDER_BUILD)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD_OUTSIDER)
								.modelData(Integer.valueOf(Settings.OUTSIDER_BUILD_CMD))
								.color(town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BUILD_OUTSIDER2, town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.OUTSIDER_BUILD)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD_OUTSIDER)
								.modelData(Integer.valueOf(Settings.OUTSIDER_BUILD_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BUILD_OUTSIDER2, town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};

			// ------------------------------------------------------------------------------------
			breakResidentButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY), TownyPermission.PermLevel.RESIDENT, TownyPermission.ActionType.DESTROY);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.RESIDENT_BREAK).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_BREAK)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK_RES)
								.modelData(Integer.valueOf(Settings.RESIDENT_BREAK_CMD))
								.color(town.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_RES2, town.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_BREAK)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK_RES)
								.modelData(Integer.valueOf(Settings.RESIDENT_BREAK_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_RES2, town.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			breakNationButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY), TownyPermission.PermLevel.NATION, TownyPermission.ActionType.DESTROY);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.NATION_BREAK).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_BREAK)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK_NATION)
								.modelData(Integer.valueOf(Settings.NATION_BREAK_CMD))
								.color(town.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_NATION2, town.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_BREAK)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK_NATION)
								.modelData(Integer.valueOf(Settings.NATION_BREAK_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_NATION2, town.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			breakAllyButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY), TownyPermission.PermLevel.ALLY, TownyPermission.ActionType.DESTROY);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.ALLY_BREAK).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ALLY_BREAK)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK_ALLY)
								.modelData(Integer.valueOf(Settings.ALLY_BREAK_CMD))
								.color(town.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_ALLY2, town.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ALLY_BREAK)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK_ALLY)
								.modelData(Integer.valueOf(Settings.ALLY_BREAK_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_ALLY2, town.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			breakOutsiderButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY), TownyPermission.PermLevel.OUTSIDER, TownyPermission.ActionType.DESTROY);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.OUTSIDER_BREAK).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.OUTSIDER_BREAK)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK_OUTSIDER)
								.modelData(Integer.valueOf(Settings.OUTSIDER_BREAK_CMD))
								.color(town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_OUTSIDER2, town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.OUTSIDER_BREAK)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK_OUTSIDER)
								.modelData(Integer.valueOf(Settings.OUTSIDER_BREAK_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_OUTSIDER2, town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};

			//------------------------------------------------------------------------------------------------------------------------------------------------------
			itemUseResidentButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE), TownyPermission.PermLevel.RESIDENT, TownyPermission.ActionType.ITEM_USE);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.RESIDENT_ITEM_USE).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_ITEM_USE)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.USE_RES)
								.modelData(Integer.valueOf(Settings.RESIDENT_ITEM_USE_CMD))
								.color(town.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.USE_RES2, town.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_ITEM_USE)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.USE_RES)
								.modelData(Integer.valueOf(Settings.RESIDENT_ITEM_USE_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.USE_RES2, town.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			itemUseNationButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE), TownyPermission.PermLevel.NATION, TownyPermission.ActionType.ITEM_USE);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.NATION_ITEM_USE).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_ITEM_USE)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.USE_NATION)
								.modelData(Integer.valueOf(Settings.NATION_ITEM_USE_CMD))
								.color(town.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.USE_NATION2, town.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_ITEM_USE)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.USE_NATION)
								.modelData(Integer.valueOf(Settings.NATION_ITEM_USE_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.USE_NATION2, town.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			itemUseAllyButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE), TownyPermission.PermLevel.ALLY, TownyPermission.ActionType.ITEM_USE);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.ALLY_ITEM_USE).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ALLY_ITEM_USE)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.USE_ALLY)
								.modelData(Integer.valueOf(Settings.ALLY_ITEM_USE_CMD))
								.color(town.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.USE_ALLY2, town.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ALLY_ITEM_USE)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.USE_ALLY)
								.modelData(Integer.valueOf(Settings.ALLY_ITEM_USE_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.USE_ALLY2, town.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			itemUseOutsiderButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE), TownyPermission.PermLevel.OUTSIDER, TownyPermission.ActionType.ITEM_USE);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.OUTSIDER_ITEM_USE).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.OUTSIDER_ITEM_USE)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.USE_OUTSIDER)
								.modelData(Integer.valueOf(Settings.OUTSIDER_ITEM_USE_CMD))
								.color(town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.USE_OUTSIDER2, town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.OUTSIDER_ITEM_USE)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.USE_OUTSIDER)
								.modelData(Integer.valueOf(Settings.OUTSIDER_ITEM_USE_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.USE_OUTSIDER2, town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};

			//---------------------------------------------------------------------------------------------------------------------

			switchResidentButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH), TownyPermission.PermLevel.RESIDENT, TownyPermission.ActionType.SWITCH);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.RESIDENT_SWITCH).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_SWITCH)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_RES)
								.modelData(Integer.valueOf(Settings.RESIDENT_SWITCH_CMD))
								.color(town.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_RES2, town.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_SWITCH)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_RES)
								.modelData(Integer.valueOf(Settings.RESIDENT_SWITCH_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_RES2, town.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			switchNationButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH), TownyPermission.PermLevel.NATION, TownyPermission.ActionType.SWITCH);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.NATION_SWITCH).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_SWITCH)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_NATION)
								.color(town.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.NATION_SWITCH_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_NATION2, town.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_SWITCH)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_NATION)
								.modelData(Integer.valueOf(Settings.NATION_SWITCH_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_NATION2, town.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			switchAllyButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH), TownyPermission.PermLevel.ALLY, TownyPermission.ActionType.SWITCH);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.ALLY_SWITCH).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ALLY_SWITCH)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_ALLY)
								.modelData(Integer.valueOf(Settings.ALLY_SWITCH_CMD))
								.color(town.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("", Localization.TownMenu.PlayerPermissionsMenu.SWITCH_ALLY2, town.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ALLY_SWITCH)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_ALLY)
								.modelData(Integer.valueOf(Settings.ALLY_SWITCH_CMD))
								.lore("", Localization.TownMenu.PlayerPermissionsMenu.SWITCH_ALLY2, town.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			switchOutsiderButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH), TownyPermission.PermLevel.OUTSIDER, TownyPermission.ActionType.SWITCH);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.OUTSIDER_SWITCH).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.OUTSIDER_SWITCH)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER)
								.modelData(Integer.valueOf(Settings.OUTSIDER_SWITCH_CMD))
								.color(town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER2, town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.OUTSIDER_SWITCH)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER)
								.modelData(Integer.valueOf(Settings.OUTSIDER_SWITCH_CMD))
								.lore("")
								.lore(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER2, town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};

			//----------------------------------------------------------------------------------------------------------

			resetButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.ALL_PERMS, false);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.RESET_ALL).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESET_ALL)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.RESET)
								.modelData(Integer.valueOf(Settings.RESET_ALL_CMD))
								.color(Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore((List<String>) Localization.TownMenu.PlayerPermissionsMenu.RESET_LORE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESET_ALL)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.RESET)
								.modelData(Integer.valueOf(Settings.RESET_ALL_CMD))
								.lore("")
								.lore((List<String>) Localization.TownMenu.PlayerPermissionsMenu.RESET_LORE).make();

					}
				}
			};
			allOnButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.ALL_PERMS, true);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.ALL_ON).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ALL_ON)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.ON)
								.color(Settings.POSITIVE_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.ALL_ON_CMD))
								.lore((List<String>) Localization.TownMenu.PlayerPermissionsMenu.ON_LORE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.ALL_ON)))
								.name(Localization.TownMenu.PlayerPermissionsMenu.ON)
								.modelData(Integer.valueOf(Settings.ALL_ON_CMD))
								.lore((List<String>) Localization.TownMenu.PlayerPermissionsMenu.ON_LORE).make();

					}
				}
			};
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (!Settings.USE_FIXED_INVENTORY_SIZE) {
				if (slot == 9 + 1) {
					if (Material.getMaterial(Settings.BUILD).equals(Material.LEATHER_BOOTS)) {
						return BUILD_BUTTON_COLORED;
					} else {
						return BUILD_BUTTON;
					}
				}

				if (slot == 9 * 2 + 1) {
					if (Material.getMaterial(Settings.BREAK).equals(Material.LEATHER_BOOTS)) {
						return BREAK_BUTTON_COLORED;
					} else {
						return BREAK_BUTTON;
					}
				}

				if (slot == 9 * 3 + 1) {
					if (Material.getMaterial(Settings.ITEM_USE).equals(Material.LEATHER_BOOTS)) {
						return ITEM_USE_BUTTON_COLORED;
					} else {
						return ITEM_USE_BUTTON;
					}
				}

				if (slot == 9 * 4 + 1) {
					if (Material.getMaterial(Settings.SWITCH).equals(Material.LEATHER_BOOTS)) {
						return SWITCH_BUTTON_COLORED;
					} else {
						return SWITCH_BUTTON;
					}
				}

				if (slot == 9 + 3)
					return buildResidentButton.getItem();
				if (slot == 9 + 4)
					return buildNationButton.getItem();
				if (slot == 9 + 5)
					return buildAllyButton.getItem();
				if (slot == 9 + 6)
					return buildOutsiderButton.getItem();

				if (slot == 9 * 2 + 3)
					return breakResidentButton.getItem();
				if (slot == 9 * 2 + 4)
					return breakNationButton.getItem();
				if (slot == 9 * 2 + 5)
					return breakAllyButton.getItem();
				if (slot == 9 * 2 + 6)
					return breakOutsiderButton.getItem();

				if (slot == 9 * 3 + 3)
					return itemUseResidentButton.getItem();
				if (slot == 9 * 3 + 4)
					return itemUseNationButton.getItem();
				if (slot == 9 * 3 + 5)
					return itemUseAllyButton.getItem();
				if (slot == 9 * 3 + 6)
					return itemUseOutsiderButton.getItem();

				if (slot == 9 * 4 + 3)
					return switchResidentButton.getItem();
				if (slot == 9 * 4 + 4)
					return switchNationButton.getItem();
				if (slot == 9 * 4 + 5)
					return switchAllyButton.getItem();
				if (slot == 9 * 4 + 6)
					return switchOutsiderButton.getItem();

				if (slot == 9 * 2 + 8)
					return resetButton.getItem();
				if (slot == 9 * 3 + 8)
					return allOnButton.getItem();
			} else {
				if (slot == 1) {
					if (Material.getMaterial(Settings.BUILD).equals(Material.LEATHER_BOOTS)) {
						return BUILD_BUTTON_COLORED;
					} else {
						return BUILD_BUTTON;
					}
				}

				if (slot == 9 + 1) {
					if (Material.getMaterial(Settings.BREAK).equals(Material.LEATHER_BOOTS)) {
						return BREAK_BUTTON_COLORED;
					} else {
						return BREAK_BUTTON;
					}
				}

				if (slot == 9 * 2 + 1) {
					if (Material.getMaterial(Settings.ITEM_USE).equals(Material.LEATHER_BOOTS)) {
						return ITEM_USE_BUTTON_COLORED;
					} else {
						return ITEM_USE_BUTTON;
					}
				}

				if (slot == 9 * 3 + 1) {
					if (Material.getMaterial(Settings.SWITCH).equals(Material.LEATHER_BOOTS)) {
						return SWITCH_BUTTON_COLORED;
					} else {
						return SWITCH_BUTTON;
					}
				}

				if (slot == 3)
					return buildResidentButton.getItem();
				if (slot == 4)
					return buildNationButton.getItem();
				if (slot == 5)
					return buildAllyButton.getItem();
				if (slot == 6)
					return buildOutsiderButton.getItem();

				if (slot == 9 + 3)
					return breakResidentButton.getItem();
				if (slot == 9 + 4)
					return breakNationButton.getItem();
				if (slot == 9 + 5)
					return breakAllyButton.getItem();
				if (slot == 9 + 6)
					return breakOutsiderButton.getItem();

				if (slot == 9 * 2 + 3)
					return itemUseResidentButton.getItem();
				if (slot == 9 * 2 + 4)
					return itemUseNationButton.getItem();
				if (slot == 9 * 2 + 5)
					return itemUseAllyButton.getItem();
				if (slot == 9 * 2 + 6)
					return itemUseOutsiderButton.getItem();

				if (slot == 9 * 3 + 3)
					return switchResidentButton.getItem();
				if (slot == 9 * 3 + 4)
					return switchNationButton.getItem();
				if (slot == 9 * 3 + 5)
					return switchAllyButton.getItem();
				if (slot == 9 * 3 + 6)
					return switchOutsiderButton.getItem();

				if (slot == 9 + 8)
					return resetButton.getItem();
				if (slot == 9 * 2 + 8)
					return allOnButton.getItem();
			}

			return DUMMY_BUTTON;
		}
	}

	public class EconomyManagementMenu extends Menu {

		private ItemStack balanceButton;
		private final Button depositButton;
		private final Button withdrawButton;
		private final Button setTaxButton;

		private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_TOWN_ECONOMY_MENU)), "")
				.modelData(Integer.valueOf(Settings.FILLER_TOWN_ECONOMY_MENU_CMD)).make();

		protected EconomyManagementMenu(Town town) {
			super(TownMenu.this);

			if (Settings.USE_FIXED_INVENTORY_SIZE) {
				setSize(9 * 6);
			} else {
				setSize(9 * 2);
			}

			setTitle(Localization.TownMenu.EconomyMenu.MENU_TITLE);

			try {
				if (Material.getMaterial(Settings.TOWN_BALANCE).equals(Material.LEATHER_BOOTS)) {
					balanceButton = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_BALANCE)))
							.name(Localization.TownMenu.EconomyMenu.BALANCE)
							.modelData(Integer.valueOf(Settings.TOWN_BALANCE_CMD))
							.color(Settings.NEUTRAL_BUTTON_COLOR)
							.lore("")
							.lore(Localization.TownMenu.EconomyMenu.TOWN_BALANCE + town.getAccount().getHoldingFormattedBalance(), "",
									Localization.TownMenu.EconomyMenu.UPKEEP + Settings.MONEY_SYMBOL + TownySettings.getTownUpkeepCost(town)).make();
				} else {
					balanceButton = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_BALANCE)))
							.name(Localization.TownMenu.EconomyMenu.BALANCE)
							.modelData(Integer.valueOf(Settings.TOWN_BALANCE_CMD))
							.lore("")
							.lore(Localization.TownMenu.EconomyMenu.TOWN_BALANCE + town.getAccount().getHoldingFormattedBalance(), "",
									Localization.TownMenu.EconomyMenu.UPKEEP + Settings.MONEY_SYMBOL + TownySettings.getTownUpkeepCost(town)).make();
				}

			} catch (Throwable t) {
				balanceButton = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_BALANCE)))
						.name("Economy Disabled").make();
			}

			if (Material.getMaterial(Settings.DEPOSIT).equals(Material.LEATHER_BOOTS)) {
				depositButton = new ButtonConversation(new TownDepositPrompt(town),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.DEPOSIT)))
								.name(Localization.TownMenu.EconomyMenu.DEPOSIT)
								.modelData(Integer.valueOf(Settings.DEPOSIT_CMD))
								.color(Settings.NEUTRAL_BUTTON_COLOR)
								.lore((List<String>) Localization.TownMenu.EconomyMenu.DEPOSIT_LORE));
			} else {
				depositButton = new ButtonConversation(new TownDepositPrompt(town),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.DEPOSIT)))
								.name(Localization.TownMenu.EconomyMenu.DEPOSIT)
								.modelData(Integer.valueOf(Settings.DEPOSIT_CMD))
								.lore((List<String>) Localization.TownMenu.EconomyMenu.DEPOSIT_LORE));
			}

			if (Material.getMaterial(Settings.WITHDRAW).equals(Material.LEATHER_BOOTS)) {
				withdrawButton = new ButtonConversation(new TownWithdrawPrompt(town),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.WITHDRAW)))
								.name(Localization.TownMenu.EconomyMenu.WITHDRAW)
								.modelData(Integer.valueOf(Settings.WITHDRAW_CMD))
								.color(Settings.NEUTRAL_BUTTON_COLOR)
								.lore((List<String>) Localization.TownMenu.EconomyMenu.WITHDRAW_LORE));
			} else {
				withdrawButton = new ButtonConversation(new TownWithdrawPrompt(town),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.WITHDRAW)))
								.name(Localization.TownMenu.EconomyMenu.WITHDRAW)
								.modelData(Integer.valueOf(Settings.WITHDRAW_CMD))
								.lore((List<String>) Localization.TownMenu.EconomyMenu.WITHDRAW_LORE));
			}

			if (Material.getMaterial(Settings.SET_TAX).equals(Material.LEATHER_BOOTS)) {
				setTaxButton = new ButtonConversation(new TownSetTaxPrompt(town),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SET_TAX)))
								.name(Localization.TownMenu.EconomyMenu.TAX)
								.modelData(Integer.valueOf(Settings.SET_TAX_CMD))
								.color(Settings.NEUTRAL_BUTTON_COLOR)
								.lore("")
								.lore("" + (town.isTaxPercentage() ? Localization.TownMenu.EconomyMenu.TAX_PERCENTAGE : Localization.TownMenu.EconomyMenu.TAX_AMOUNT)));
			} else {
				setTaxButton = new ButtonConversation(new TownSetTaxPrompt(town),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SET_TAX)))
								.name(Localization.TownMenu.EconomyMenu.TAX)
								.modelData(Integer.valueOf(Settings.SET_TAX_CMD))
								.lore("")
								.lore("" + (town.isTaxPercentage() ? Localization.TownMenu.EconomyMenu.TAX_PERCENTAGE : Localization.TownMenu.EconomyMenu.TAX_AMOUNT)));
			}

		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (!Settings.USE_FIXED_INVENTORY_SIZE) {
				if (slot == 1)
					return balanceButton;
				if (slot == 3)
					return depositButton.getItem();
				if (slot == 5)
					return withdrawButton.getItem();
				if (slot == 7)
					return setTaxButton.getItem();
			} else {
				if (slot == 13)
					return balanceButton;
				if (slot == 47)
					return depositButton.getItem();
				if (slot == 49)
					return withdrawButton.getItem();
				if (slot == 51)
					return setTaxButton.getItem();
			}


			return DUMMY_BUTTON;
		}
	}

	public class GeneralSettingsMenu extends Menu {

		private final Button setSpawnButton;
		private final Button setHomeBlockButton;
		private final Button townBoardButton;
		private final Button townNameButton;

		private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_TOWN_GENERAL_SETTINGS_MENU)), "")
				.modelData(Integer.valueOf(Settings.FILLER_TOWN_GENERAL_SETTINGS_MENU_CMD)).make();

		@Override
		public String[] getInfo() {
			return Localization.TownMenu.GeneralSettingsMenu.INFO;
		}

		protected GeneralSettingsMenu(Town town) {
			super(TownMenu.this);

			if (Settings.USE_FIXED_INVENTORY_SIZE) {
				setSize(9 * 6);
			} else {
				setSize(9 * 2);
			}

			setTitle(Localization.TownMenu.GeneralSettingsMenu.MENU_TITLE);
			Button.setInfoButtonTitle(Localization.MENU_INFORMATION);

			setHomeBlockButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					TownBlock townBlock = TownyAPI.getInstance().getTownBlock(player.getLocation());
					try {
						if (townBlock != null && townBlock.getTown().equals(town) && town.getMayor().getName().equals(player.getName())) {
							town.setHomeBlock(townBlock);
							TownyAPI.getInstance().getDataSource().saveTown(town);
							Common.tell(player, Localization.TownMenu.GeneralSettingsMenu.SET_HOME_BLOCK_MSG);
							Common.tell(player, Localization.TownMenu.GeneralSettingsMenu.SPAWN_REMINDER);
						} else {
							Common.tell(player, Localization.Error.CANNOT_SET_HOMEBLOCK);
						}
						player.closeInventory();

					} catch (TownyException e) {
						Common.tell(player, Localization.Error.CANNOT_SET_HOMEBLOCK);
					}
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.SET_HOME_BLOCK).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SET_HOME_BLOCK)))
								.name(Localization.TownMenu.GeneralSettingsMenu.SET_HOME_BLOCK)
								.color(Settings.NEUTRAL_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.SET_HOME_BLOCK_CMD))
								.lore((List<String>) Localization.TownMenu.GeneralSettingsMenu.SET_HOME_BLOCK_LORE).make();
					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SET_HOME_BLOCK)))
								.name(Localization.TownMenu.GeneralSettingsMenu.SET_HOME_BLOCK)
								.modelData(Integer.valueOf(Settings.SET_HOME_BLOCK_CMD))
								.lore((List<String>) Localization.TownMenu.GeneralSettingsMenu.SET_HOME_BLOCK_LORE).make();
					}
				}
			};

			setSpawnButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					TownBlock townBlock = TownyAPI.getInstance().getTownBlock(player.getLocation());
					try {
						if (townBlock.isHomeBlock() && townBlock.getTown().equals(town)) {
							town.setSpawn(player.getLocation());
							Common.tell(player, Localization.TownMenu.GeneralSettingsMenu.SET_SPAWN_MSG);
							player.closeInventory();
							TownyAPI.getInstance().getDataSource().saveTown(town);
						} else {
							Common.tell(player, Localization.Error.CANNOT_SET_SPAWN);
						}
					} catch (TownyException e) {
						e.printStackTrace();
					}
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.SET_TOWN_SPAWN).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SET_TOWN_SPAWN)))
								.name(Localization.TownMenu.GeneralSettingsMenu.SET_SPAWN)
								.color(Settings.NEUTRAL_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.SET_TOWN_SPAWN_CMD))
								.lore((List<String>) Localization.TownMenu.GeneralSettingsMenu.SET_SPAWN_LORE).make();
					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SET_TOWN_SPAWN)))
								.name(Localization.TownMenu.GeneralSettingsMenu.SET_SPAWN)
								.modelData(Integer.valueOf(Settings.SET_TOWN_SPAWN_CMD))
								.lore((List<String>) Localization.TownMenu.GeneralSettingsMenu.SET_SPAWN_LORE).make();
					}

				}
			};


			townNameButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {

					if (town.getMayor().getName().equals(player.getName()))
						new TownNamePrompt(town).show(player);
					else
						Common.tell(player, Localization.Error.CANNOT_CHANGE_NAME);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.SET_TOWN_NAME).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SET_TOWN_NAME)))
								.name(Localization.TownMenu.GeneralSettingsMenu.SET_NAME)
								.color(Settings.NEUTRAL_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.SET_TOWN_NAME_CMD))
								.lore((List<String>) Localization.TownMenu.GeneralSettingsMenu.SET_NAME_LORE).make();
					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SET_TOWN_NAME)))
								.name(Localization.TownMenu.GeneralSettingsMenu.SET_NAME)
								.modelData(Integer.valueOf(Settings.SET_TOWN_NAME_CMD))
								.lore((List<String>) Localization.TownMenu.GeneralSettingsMenu.SET_NAME_LORE).make();
					}
				}
			};

			townBoardButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					if (town.getMayor().getName().equals(player.getName()))
						new TownBoardPrompt(town).show(player);
					else
						Common.tell(player, Localization.Error.CANNOT_CHANGE_BOARD);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.SET_TOWN_BOARD).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SET_TOWN_BOARD)))
								.name(Localization.TownMenu.GeneralSettingsMenu.SET_BOARD)
								.color(Settings.NEUTRAL_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.SET_TOWN_BOARD_CMD))
								.lore((List<String>) Localization.TownMenu.GeneralSettingsMenu.SET_BOARD_LORE).make();
					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SET_TOWN_BOARD)))
								.name(Localization.TownMenu.GeneralSettingsMenu.SET_BOARD)
								.modelData(Integer.valueOf(Settings.SET_TOWN_BOARD_CMD))
								.lore((List<String>) Localization.TownMenu.GeneralSettingsMenu.SET_BOARD_LORE).make();
					}
				}
			};
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (!Settings.USE_FIXED_INVENTORY_SIZE) {
				if (slot == 1)
					return setHomeBlockButton.getItem();
				if (slot == 3)
					return setSpawnButton.getItem();
				if (slot == 5)
					return townNameButton.getItem();
				if (slot == 7)
					return townBoardButton.getItem();
			} else {
				if (slot == 10)
					return setHomeBlockButton.getItem();
				if (slot == 12)
					return setSpawnButton.getItem();
				if (slot == 14)
					return townNameButton.getItem();
				if (slot == 16)
					return townBoardButton.getItem();
			}
			return DUMMY_BUTTON;
		}
	}

	public class InvitePlayerMenu extends MenuPagged<Resident> {
		public String[] getInfo() {
			return Localization.TownMenu.InvitePlayerMenu.INFO;
		}
		protected InvitePlayerMenu(Iterable<Resident> pages) {
			super(TownMenu.this, pages);

			if (Settings.USE_FIXED_INVENTORY_SIZE) {
				setSize(9 * 6);
			}

			setTitle(Localization.TownMenu.ResidentMenu.MENU_LIST_TITLE);

		}

		@Override
		protected ItemStack convertToItemStack(Resident item) {
			ItemStack itemSkull = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta skull = (SkullMeta) itemSkull.getItemMeta();
			skull.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + item.getFormattedTitleName());
			skull.setCustomModelData(Settings.RESIDENT_PLAYER_HEAD_CMD);
			OfflinePlayer player = Bukkit.getOfflinePlayer(item.getUUID());
			skull.setOwningPlayer(player);
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add((ChatColor.translateAlternateColorCodes('&', Localization.TownMenu.ResidentMenu.INVITE)));
			skull.setLore(lore);
			itemSkull.setItemMeta(skull);
			return itemSkull;
		}

		@Override
		protected void onPageClick(Player player, Resident item, ClickType click) {
			player.closeInventory();
			player.performCommand("t invite " + item.getName());
		}
	}

	public class ExtraTownInfo extends Menu {
		public String[] getInfo() {
			return Localization.TownMenu.ExtraInfoMenu.INFO;
		}
		private final ItemStack claimInfo = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_CLAIM_INFO)))
				.name(Localization.TownMenu.ExtraInfoMenu.CLAIMING)
				.modelData(Integer.valueOf(Settings.TOWN_CLAIM_INFO_CMD))
				.lore((List<String>) Localization.TownMenu.ExtraInfoMenu.CLAIMING_LORE).make();
		private final ItemStack claimInfoColored = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem("LEATHER_BOOTS"))
				.name(Localization.TownMenu.ExtraInfoMenu.CLAIMING)
				.color(Settings.NEUTRAL_BUTTON_COLOR)
				.modelData(Integer.valueOf(Settings.TOWN_CLAIM_INFO_CMD))
				.lore((List<String>) Localization.TownMenu.ExtraInfoMenu.CLAIMING_LORE).make();
		private final ItemStack extraCommands = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.EXTRA_COMMANDS)))
				.name(Localization.TownMenu.ExtraInfoMenu.COMMANDS)
				.modelData(Integer.valueOf(Settings.EXTRA_INFO_CMD))
				.lore((List<String>) Localization.TownMenu.ExtraInfoMenu.COMMANDS_LORE).make();
		private final ItemStack extraCommandsColored = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem("LEATHER_BOOTS"))
				.name(Localization.TownMenu.ExtraInfoMenu.COMMANDS)
				.color(Settings.NEUTRAL_BUTTON_COLOR)
				.modelData(Integer.valueOf(Settings.EXTRA_INFO_CMD))
				.lore((List<String>) Localization.TownMenu.ExtraInfoMenu.COMMANDS_LORE).make();

		private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_TOWN_EXTRA_INFO_MENU)), "")
				.modelData(Integer.valueOf(Settings.FILLER_TOWN_EXTRA_INFO_MENU_CMD)).make();

		protected ExtraTownInfo() {
			super(TownMenu.this);

			if (Settings.USE_FIXED_INVENTORY_SIZE) {
				setSize(9 * 6);
			} else {
				setSize(9 * 2);
			}

			setTitle(Localization.TownMenu.ExtraInfoMenu.MENU_TITLE);
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (!Settings.USE_FIXED_INVENTORY_SIZE) {
				if (slot == 3)
					if (Material.getMaterial(Settings.TOWN_CLAIM_INFO).equals(Material.LEATHER_BOOTS)) {
						return claimInfoColored;
					} else {
						return claimInfo;
					}
				if (slot == 5)
					if (Material.getMaterial(Settings.TOWN_CLAIM_INFO).equals(Material.LEATHER_BOOTS)) {
						return extraCommandsColored;
					} else {
						return extraCommands;
					}
			} else {
				if (slot == 12)
					if (Material.getMaterial(Settings.TOWN_CLAIM_INFO).equals(Material.LEATHER_BOOTS)) {
						return claimInfoColored;
					} else {
						return claimInfo;
					}
				if (slot == 14)
					if (Material.getMaterial(Settings.TOWN_CLAIM_INFO).equals(Material.LEATHER_BOOTS)) {
						return extraCommandsColored;
					} else {
						return extraCommands;
					}
			}
			return DUMMY_BUTTON;
		}
	}
}