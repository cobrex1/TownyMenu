package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.*;
import lombok.SneakyThrows;
import me.cobrex.townymenu.nation.prompt.*;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.HeadDatabaseUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.TimeUtil;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonConversation;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NationMenu extends Menu {

	private final Button nationToggleButton;
	private final Button nationTownListButton;
	private final Button nationEconomyButton;
	private final Button nationSettingsButton;
	private final Button inviteTownButton;
	private final Button nationExtraInfoButton;
	private final Button nationResidentListButton;

	private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_JOIN_NATION_MENU)), "").make();

	public NationMenu(Nation nation, Player player) throws NotRegisteredException {

		List<Town> nationTownList = nation.getTowns();

		List<Town> townList = TownyAPI.getInstance().getTownsWithoutNation();

		List<Resident> nationResidentList = nation.getResidents();

		setSize(9 * 3);

		setTitle(Localization.NationMenu.MAIN_MENU_TITLE);

		ItemCreator toggleNationItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_TOGGLE_MENU)))
				.name(Localization.NationMenu.NATION_TOGGLE_MENU_BUTTON);
		ItemCreator nationTownListItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_TOWN_LIST)))
				.name(Localization.NationMenu.NATION_TOWN_LIST_BUTTON)
				.lore((List<String>) Localization.NationMenu.NATION_TOWN_LIST_BUTTON_LORE);
		ItemCreator nationEconomyMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_ECONOMY_MENU)))
				.name(Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON)
				.lore((List<String>) Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON_LORE);
		ItemCreator nationSettingsMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_SETTINGS_MENU)))
				.name(Localization.NationMenu.NATION_SETTINGS_MENU_BUTTON)
				.lore((List<String>) Localization.NationMenu.NATION_SETTINGS_MENU_BUTTON_LORE);
		ItemCreator townInviteMenuItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_INVITE_TOWN_MENU)))
				.name(Localization.NationMenu.NATION_INVITE_TOWN_MENU_BUTTON)
				.lore((List<String>) Localization.NationMenu.NATION_INVITE_TOWN_MENU_BUTTON_LORE);
		ItemCreator nationExtraInfoItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_EXTRA_INFO)))
				.name(Localization.NationMenu.NATION_EXTRA_INFO_MENU_BUTTON)
				.lore((List<String>) Localization.NationMenu.NATION_EXTRA_INFO_MENU_BUTTON_LORE);
		ItemCreator nationResidentListItem = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_RESIDENT_MENU)))
				.name(Localization.NationMenu.NATION_RESIDENT_MENU_BUTTON)
				.lore((List<String>) Localization.NationMenu.NATION_RESIDENT_MENU_BUTTON_LORE);

		nationToggleButton = new ButtonMenu(new NationToggleMenu(nation), toggleNationItem);
		nationTownListButton = new ButtonMenu(new NationTownListMenu(nationTownList), nationTownListItem);
		nationSettingsButton = new ButtonMenu(new NationSettingsMenu(nation), nationSettingsMenuItem);
		inviteTownButton = new ButtonMenu(new InviteTownMenu(townList), townInviteMenuItem);
		nationExtraInfoButton = new ButtonMenu(new NationExtraInfo(), nationExtraInfoItem);
		nationResidentListButton = new ButtonMenu(new NationResidentListMenu(nationResidentList), nationResidentListItem);

		if (Settings.ECONOMY_ENABLED) {
			if (TownySettings.isBankActionLimitedToBankPlots()) {
				if (TownyAPI.getInstance().getTownBlock(player.getLocation()) != null)
					if (!Objects.requireNonNull(TownyAPI.getInstance().getTownBlock(player.getLocation())).getType().equals(TownBlockType.BANK))
						nationEconomyButton = new Button() {
							@Override
							public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
								Common.tell(player, Localization.Error.MUST_BE_IN_BANK);
								player.closeInventory();
							}

							@Override
							public ItemStack getItem() {
								return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_ECONOMY_MENU)))
										.name(Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON)
										.lore(String.valueOf(Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON_LORE)).make();
							}
						};
					else
						nationEconomyButton = new ButtonMenu(new NationEconomyManagementMenu(nation), nationEconomyMenuItem);
				else
					nationEconomyButton = new Button() {
						@Override
						public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
							Common.tell(player, Localization.Error.MUST_BE_IN_BANK);
							player.closeInventory();
						}

						@Override
						public ItemStack getItem() {
							return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_ECONOMY_MENU)))
									.name(Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON)
									.lore(String.valueOf(Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON_LORE)).make();
						}
					};
			} else if (TownySettings.isBankActionDisallowedOutsideTown()) {
				if (TownyAPI.getInstance().getTownBlock(player.getLocation()) != null)
					if (!Objects.requireNonNull(TownyAPI.getInstance().getTownBlock(player.getLocation())).getTown().equals(nation)) {
						nationEconomyButton = new Button() {
							@Override
							public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
								Common.tell(player, Localization.Error.MUST_BE_IN_TOWN);
								player.closeInventory();
							}

							@Override
							public ItemStack getItem() {
								return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_ECONOMY_MENU)))
										.name(Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON)
										.lore((List<String>) Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON_LORE).make();
							}
						};
					} else
						nationEconomyButton = new ButtonMenu(new NationEconomyManagementMenu(nation), nationEconomyMenuItem);
				else {
					nationEconomyButton = new Button() {
						@Override
						public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
							Common.tell(player, Localization.Error.MUST_BE_IN_TOWN);
							player.closeInventory();
						}

						@Override
						public ItemStack getItem() {
							return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_ECONOMY_MENU)))
									.name(Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON)
									.lore((List<String>) Localization.NationMenu.NATION_ECONOMY_MENU_BUTTON_LORE).make();
						}
					};
				}

			} else
				nationEconomyButton = new ButtonMenu(new NationEconomyManagementMenu(nation), nationEconomyMenuItem);
		} else {
			nationEconomyButton = new Button() {
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
	}

	@Override
	public ItemStack getItemAt(int slot) {

		if (slot == 2)
			return nationToggleButton.getItem();
		if (slot == 4)
			return nationTownListButton.getItem();
		if (slot == 6)
			return nationResidentListButton.getItem();
		if (slot == 9 + 4 && Settings.ECONOMY_ENABLED)
			return nationEconomyButton.getItem();
		if (slot == 9 * 2 + 2)
			return nationSettingsButton.getItem();
		if (slot == 9 * 2 + 4)
			return inviteTownButton.getItem();
		if (slot == 9 * 2 + 6)
			return nationExtraInfoButton.getItem();
		return DUMMY_BUTTON;
	}

	@Override
	public String[] getInfo() {
		return Localization.NationMenu.INFO;
	}

	public class NationToggleMenu extends Menu {
		private final Button nationOpenToggle;
		private final Button nationPublicToggle;
		private final Button nationTaxPercentToggle;

		private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_TOWN_TOGGLE)), "").make();

		public NationToggleMenu(Nation nation) {
			super(NationMenu.this);

			setSize(9 * 2);

			setTitle(Localization.NationMenu.NationToggleMenu.MENU_TITLE);
			Button.setInfoButtonTitle(Localization.MENU_INFORMATION);

			nationOpenToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					nation.setOpen(!nation.isOpen());
					TownyAPI.getInstance().getDataSource().saveNation(nation);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					if (nation.isOpen())
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_TOGGLE_OPEN)))
								.name(Localization.NationMenu.NationToggleMenu.OPEN)
								.lore("")
								.lore(Localization.NationMenu.NationToggleMenu.TOGGLE_OFF).make();
					else
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_TOGGLE_OPEN)))
								.name(Localization.NationMenu.NationToggleMenu.OPEN)
								.lore("")
								.lore(Localization.NationMenu.NationToggleMenu.TOGGLE_ON).make();
				}
			};

			nationPublicToggle = new

					Button() {
						@Override
						public void onClickedInMenu(Player player, Menu menu, ClickType click) {
							nation.setPublic(!nation.isPublic());
							TownyAPI.getInstance().getDataSource().saveNation(nation);
							restartMenu();
						}

						@Override
						public ItemStack getItem() {
							if (nation.isPublic())
								return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_TOGGLE_PUBLIC)))
										.name(Localization.NationMenu.NationToggleMenu.PUBLIC)
										.lore("")
										.lore(Localization.NationMenu.NationToggleMenu.TOGGLE_OFF).make();
							else
								return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_TOGGLE_PUBLIC)))
										.name(Localization.NationMenu.NationToggleMenu.PUBLIC)
										.lore("")
										.lore(Localization.NationMenu.NationToggleMenu.TOGGLE_ON).make();
						}
					};

			nationTaxPercentToggle = new Button() {

				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					nation.setTaxPercentage(!nation.isTaxPercentage());
					TownyAPI.getInstance().getDataSource().saveNation(nation);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					if (nation.isTaxPercentage())
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_TOGGLE_TAX_PERCENTAGE)))
								.name(Localization.NationMenu.NationToggleMenu.TAX_PERCENT)
								.lore("")
								.lore(Localization.NationMenu.NationToggleMenu.TOGGLE_OFF).make();
					else
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_TOGGLE_TAX_PERCENTAGE)))
								.name(Localization.NationMenu.NationToggleMenu.TAX_PERCENT)
								.lore("")
								.lore(Localization.NationMenu.NationToggleMenu.TOGGLE_ON).make();
				}
			};
		}

		@Override
		public ItemStack getItemAt(int slot) {

			if (slot == 2)
				return nationOpenToggle.getItem();
			if (slot == 4)
				return nationPublicToggle.getItem();
			if (slot == 6)
				return nationTaxPercentToggle.getItem();

			return DUMMY_BUTTON;
		}
	}

	public class NationTownListMenu extends MenuPagged<Town> {

		@Override
		public String[] getInfo() {
			return Localization.NationMenu.NationTownMenu.INFO;
		}

		protected NationTownListMenu(Iterable<Town> pages) {
			super(NationMenu.this, pages);
			setTitle(Localization.NationMenu.NationTownMenu.MENU_TITLE);
			Button.setInfoButtonTitle(Localization.MENU_INFORMATION);
		}

		@Override
		protected ItemStack convertToItemStack(Town item) {
			ItemStack itemSkull = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta skull = (SkullMeta) itemSkull.getItemMeta();
			skull.setDisplayName(ChatColor.translateAlternateColorCodes('&',
					Localization.NationMenu.NationTownMenu.TOWN_NAME + item.getName()));
//			skull.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + item.getName());
			if (item.getName() == null)
				return DUMMY_BUTTON;
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add(ChatColor.translateAlternateColorCodes('&', Localization.NationMenu.NationTownMenu.MAYOR + (item.getMayor())));
//			lore.add(ChatColor.WHITE + Localization.NationMenu.NationTownMenu.MAYOR + (item.getMayor()));
			lore.add("");
			lore.add(ChatColor.translateAlternateColorCodes('&', Localization.NationMenu.NationTownMenu.NUMBER_RESIDENTS + (item.getNumResidents())));
//			lore.add(ChatColor.WHITE + Localization.NationMenu.NationTownMenu.NUMBER_RESIDENTS + (item.getNumResidents()));
			skull.setLore(lore);
			itemSkull.setItemMeta(skull);
			return itemSkull;
		}

		@Override
		protected void onPageClick(Player player, Town item, ClickType click) {
			if (item.getName().equals(player.getName())) {
				Common.tell(player, Localization.Error.CANNOT_SELECT_SELF);
				player.closeInventory();
				return;
			}
			new NationTownMenu(item).displayTo(player);
		}
	}

	public class NationTownMenu extends Menu {

		private final Button townKickButton;

		protected NationTownMenu(Town town) {
			super(NationMenu.this);

			setSize(9);

			setTitle(Localization.NationMenu.NationTownMenu.MENU_TITLE);


			townKickButton = new ButtonConversation(new NationKickPrompt(town),
					ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_TOWN_KICK)))
							.name(Localization.NationMenu.NationTownMenu.KICK)
							.lore((List<String>) Localization.NationMenu.NationTownMenu.KICK_LORE));
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (slot == 4)
				return townKickButton.getItem();
			return DUMMY_BUTTON;
		}
	}

	public class NationEconomyManagementMenu extends Menu {

		private ItemStack balanceButton;
		private final Button depositButton;
		private final Button withdrawButton;
		private final Button setTaxButton;

		private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_TOWN_ECONOMY_MENU)), "").make();

		protected NationEconomyManagementMenu(Nation nation) {
			super(NationMenu.this);
			setSize(9 * 2);
			setTitle(Localization.NationMenu.NationEconomyMenu.MENU_TITLE);

			try {
				balanceButton = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_BALANCE)))
						.name(Localization.NationMenu.NationEconomyMenu.BALANCE)
						.lore("")
						.lore(Localization.NationMenu.NationEconomyMenu.NATION_BALANCE + nation.getAccount().getHoldingFormattedBalance())
//						.lore("&a" + nation.getAccount().getHoldingFormattedBalance())
						.lore("")
						.lore(Localization.NationMenu.NationEconomyMenu.UPKEEP + Settings.MONEY_SYMBOL + TownySettings.getNationUpkeepCost(nation)).make();
			} catch (Throwable t) {
				balanceButton = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_BALANCE)))
						.lore("Economy Disabled").make();
			}

			depositButton = new ButtonConversation(new NationDepositPrompt(nation),
					ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_DEPOSIT)))
							.name(Localization.NationMenu.NationEconomyMenu.DEPOSIT)
							.lore((List<String>) Localization.NationMenu.NationEconomyMenu.DEPOSIT_LORE));
			withdrawButton = new ButtonConversation(new NationWithdrawPrompt(nation),
					ItemCreator.of(ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_WITHDRAW)))
							.name(Localization.NationMenu.NationEconomyMenu.WITHDRAW)
							.lore((List<String>) Localization.NationMenu.NationEconomyMenu.WITHDRAW_LORE).make()));
			setTaxButton = new ButtonConversation(new NationSetTaxPrompt(nation),
					ItemCreator.of(ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_SET_TAX)))
							.name(Localization.NationMenu.NationEconomyMenu.TAX)
							.lore("")
							.lore("" + (nation.isTaxPercentage() ? Localization.NationMenu.NationEconomyMenu.TAX_AMOUNT : Localization.NationMenu.NationEconomyMenu.TAX_AMOUNT)).make()));
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (slot == 1)
				return balanceButton;
			if (slot == 3)
				return depositButton.getItem();
			if (slot == 5)
				return withdrawButton.getItem();
			if (slot == 7)
				return setTaxButton.getItem();

			return DUMMY_BUTTON;
		}
	}

	public class NationResidentListMenu extends MenuPagged<Resident> {

		@Override
		public String[] getInfo() {
			return Localization.NationMenu.NationResidentMenu.INFO;
		}

		protected NationResidentListMenu(Iterable<Resident> pages) {
			super(NationMenu.this, pages);
			setTitle(Localization.NationMenu.NationResidentMenu.MENU_TITLE);
			Button.setInfoButtonTitle(Localization.MENU_INFORMATION);
		}

		@SneakyThrows
		@Override
		protected ItemStack convertToItemStack(Resident item) {
			ItemStack itemSkull = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta skull = (SkullMeta) itemSkull.getItemMeta();
			skull.setDisplayName(ChatColor.translateAlternateColorCodes('&',
					Localization.NationMenu.NationResidentMenu.RES_NAME + item.getFormattedTitleName()));
//			skull.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + item.getFormattedTitleName());
			if (item.getUUID() == null)
				return DUMMY_BUTTON;
			OfflinePlayer player = Bukkit.getOfflinePlayer(item.getUUID());
			skull.setOwningPlayer(player);
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add(ChatColor.translateAlternateColorCodes('&', Localization.NationMenu.NationResidentMenu.TOWN + item.getTown()));
//			lore.add(ChatColor.GRAY + Localization.NationMenu.NationResidentMenu.TOWN + item.getTown());
			lore.add("");
			lore.add(ChatColor.translateAlternateColorCodes('&', Localization.NationMenu.NationResidentMenu.ONLINE + TimeUtil.getFormattedDateShort(item.getLastOnline())));
//			lore.add(ChatColor.GRAY + Localization.NationMenu.NationResidentMenu.ONLINE + TimeUtil.getFormattedDateShort(item.getLastOnline()));
			skull.setLore(lore);
			itemSkull.setItemMeta(skull);
			return itemSkull;
		}

		@Override
		protected void onPageClick(Player player, Resident item, ClickType click) {
			if (item.getName().equals(player.getName())) {
				Common.tell(player, Localization.Error.CANNOT_SELECT_SELF);
				player.closeInventory();
				return;
			}
			new NationResidentMenu(item).displayTo(player);
		}
	}

	public class NationResidentMenu extends Menu {

		private final Button rankButton;
		private final Button mayorButton;

		protected NationResidentMenu(Resident resident) {
			super(NationMenu.this);

			setTitle(Localization.NationMenu.NationResidentMenu.MENU_TITLE);

			rankButton = new ButtonConversation(new NationRankPrompt(resident),
					ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_RESIDENT_RANK)))
							.name(Localization.NationMenu.NationResidentMenu.NATION_RANK)
							.lore((List<String>) Localization.NationMenu.NationResidentMenu.NATION_RANK_LORE));
			mayorButton = new ButtonConversation(new NationGiveKingPrompt(resident),
					ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.RESIDENT_MAYOR)))
							.name(Localization.NationMenu.NationResidentMenu.NATION_KING)
							.lore((List<String>) Localization.NationMenu.NationResidentMenu.NATION_KING_LORE));
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (slot == 9 + 3)
				return rankButton.getItem();
			if (slot == 9 + 6)
				return mayorButton.getItem();

			return DUMMY_BUTTON;
		}
	}

	public class NationSettingsMenu extends Menu {

		private final Button setNationSpawnButton;
		private final Button nationBoardButton;
		private final Button nationNameButton;

		private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_TOWN_GENERAL_SETTINGS_MENU)), "").make();

		@Override
		public String[] getInfo() {
			return Localization.NationMenu.NationSettingsMenu.INFO;
		}

		protected NationSettingsMenu(Nation nation) {
			super(NationMenu.this);
			setSize(9 * 2);
			setTitle(Localization.NationMenu.NationSettingsMenu.MENU_TITLE);
			Button.setInfoButtonTitle(Localization.MENU_INFORMATION);

			setNationSpawnButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					TownBlock townBlock = TownyAPI.getInstance().getTownBlock(player.getLocation());
					try {
						if (!TownySettings.isNationSpawnOnlyAllowedInCapital() && nation.hasTown(townBlock.getTown())
								|| nation.hasTown(townBlock.getTown()) && townBlock.getTown().isCapital()) {
							Common.tell(player, Localization.NationMenu.NationSettingsMenu.SET_SPAWN_MSG);
							player.closeInventory();
							TownyAPI.getInstance().getDataSource().saveNation(nation);
						} else {
							Common.tell(player, Localization.Error.CANNOT_SET_SPAWN);
						}
					} catch (TownyException e) {
						e.printStackTrace();
					}
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SET_NATION_SPAWN)))
							.name(Localization.NationMenu.NationSettingsMenu.SET_SPAWN)
							.lore((List<String>) Localization.NationMenu.NationSettingsMenu.SET_SPAWN_LORE).make();
				}
			};

			nationNameButton = new

					Button() {
						@Override
						public void onClickedInMenu(Player player, Menu menu, ClickType click) {

							if (nation.getKing().getName().equals(player.getName()))
								new NationNamePrompt(nation).show(player);
							else
								Common.tell(player, Localization.Error.CANNOT_CHANGE_NAME);
						}

						@Override
						public ItemStack getItem() {
							return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SET_NATION_NAME)))
									.name(Localization.NationMenu.NationSettingsMenu.SET_NAME)
									.lore((List<String>) Localization.NationMenu.NationSettingsMenu.SET_NAME_LORE).make();
						}
					}

			;

			nationBoardButton = new

					Button() {
						@Override
						public void onClickedInMenu(Player player, Menu menu, ClickType click) {
							if (nation.getKing().getName().equals(player.getName()))
								new NationBoardPrompt(nation).show(player);
							else
								Common.tell(player, Localization.Error.CANNOT_CHANGE_BOARD);
						}

						@Override
						public ItemStack getItem() {
							return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.SET_NATION_BOARD)))
									.name(Localization.NationMenu.NationSettingsMenu.SET_BOARD)
									.lore((List<String>) Localization.NationMenu.NationSettingsMenu.SET_BOARD_LORE).make();
						}
					}

			;
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (slot == 2)
				return setNationSpawnButton.getItem();
			if (slot == 4)
				return nationNameButton.getItem();
			if (slot == 6)
				return nationBoardButton.getItem();

			return DUMMY_BUTTON;
		}
	}

	public class InviteTownMenu extends MenuPagged<Town> {

		protected InviteTownMenu(Iterable<Town> pages) {
			super(NationMenu.this, pages);
			setTitle(Localization.NationMenu.NationInviteTownMenu.MENU_TITLE);
		}

		@Override
		protected ItemStack convertToItemStack(Town item) {
			ItemStack itemSkull = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta skull = (SkullMeta) itemSkull.getItemMeta();
			assert skull != null;
			skull.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + item.getName());
			OfflinePlayer player = Bukkit.getOfflinePlayer(item.getUUID());
			skull.setOwningPlayer(player);
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add(ChatColor.translateAlternateColorCodes('&', Localization.NationMenu.NationInviteTownMenu.INVITE));
//			lore.add(ChatColor.GRAY + Localization.NationMenu.NationInviteTownMenu.INVITE);
			skull.setLore(lore);
			itemSkull.setItemMeta(skull);
			return itemSkull;
		}

		@Override
		protected void onPageClick(Player player, Town item, ClickType click) {

		}

		protected void onPageClick(Player player, Resident item, ClickType click) {
			player.closeInventory();
			player.performCommand("t invite " + item.getName());
		}
	}


	public class NationExtraInfo extends Menu {

		private final ItemStack extraCommands1 =
				ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_EXTRA_COMMANDS_1)))
						.name(Localization.NationMenu.NationExtraInfoMenu.COMMANDS_1)
						.lore((List<String>) Localization.NationMenu.NationExtraInfoMenu.COMMANDS_1_LORE).make();
		private final ItemStack extraCommands2 =
				ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.NATION_EXTRA_COMMANDS_2)))
						.name(Localization.NationMenu.NationExtraInfoMenu.COMMANDS_2)
						.lore((List<String>) Localization.NationMenu.NationExtraInfoMenu.COMMANDS_2_LORE).make();

		private final ItemStack DUMMY_BUTTON =
				ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_TOWN_EXTRA_INFO_MENU)), "").make();

		protected NationExtraInfo() {
			super(NationMenu.this);
			setSize(9 * 2);
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (slot == 3)
				return extraCommands1;
			if (slot == 5)
				return extraCommands2;

			return DUMMY_BUTTON;
		}
	}
}

