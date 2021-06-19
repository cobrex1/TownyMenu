package net.tolmikarc.townymenu.town;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.*;
import com.palmergames.bukkit.towny.tasks.CooldownTimerTask;
import lombok.SneakyThrows;
import net.tolmikarc.townymenu.plot.PlotMenu;
import net.tolmikarc.townymenu.settings.Localization;
import net.tolmikarc.townymenu.settings.Settings;
import net.tolmikarc.townymenu.town.prompt.*;
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
import org.mineacademy.fo.debug.LagCatcher;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonConversation;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class TownMenu extends Menu {


	// TODO set up discord, spigot


	private final Button toggleMenuButton;
	private final Button residentListButton;
	private final Button townyPermButton;
	private final Button economyButton;
	private final Button generalSettingsButton;
	private final Button invitePlayerButton;
	private final Button extraInfoButton;
	private final Button plotMenuButton;

	private final static ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.GRAY_STAINED_GLASS_PANE, "").build().make();

	public TownMenu(Town town, Player player) throws NotRegisteredException {

		List<Resident> residentList = town.getResidents();

		List<Resident> allOnlineResidents = new ArrayList<>();
		LagCatcher.start("load-residents-online");
		for (Player players : Bukkit.getOnlinePlayers()) {
			try {
				allOnlineResidents.add(TownyAPI.getInstance().getDataSource().getResident(players.getName()));
			} catch (NotRegisteredException e) {
				e.printStackTrace();
			}
		}
		LagCatcher.end("load-residents-online");

		setSize(9 * 4);
		setTitle(Localization.TownMenu.MAIN_MENU_TITLE);

		ItemCreator.ItemCreatorBuilder toggleMenuItem = ItemCreator.of(CompMaterial.LEVER, Localization.TownMenu.TOGGLE_MENU_BUTTON, Localization.TownMenu.TOGGLE_MENU_BUTTON_LORE);
		ItemCreator.ItemCreatorBuilder residentListItem = ItemCreator.of(CompMaterial.PLAYER_HEAD, Localization.TownMenu.RESIDENT_MENU_BUTTON, Localization.TownMenu.RESIDENT_MENU_BUTTON_LORE);
		ItemCreator.ItemCreatorBuilder permissionsMenuItem = ItemCreator.of(CompMaterial.STONE_AXE, Localization.TownMenu.PERMISSIONS_MENU_BUTTON, Localization.TownMenu.PERMISSIONS_MENU_BUTTON_LORE);
		ItemCreator.ItemCreatorBuilder economyMenuItem = ItemCreator.of(CompMaterial.EMERALD_BLOCK, Localization.TownMenu.ECONOMY_MENU_BUTTON, Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE);
		ItemCreator.ItemCreatorBuilder plotMenuItem = ItemCreator.of(CompMaterial.WOODEN_SHOVEL, Localization.TownMenu.PLOT_MENU_BUTTON, Localization.TownMenu.PLOT_MENU_BUTTON_LORE);
		ItemCreator.ItemCreatorBuilder settingsMenuItem = ItemCreator.of(CompMaterial.ENDER_CHEST, Localization.TownMenu.GENERAL_SETTINGS_MENU_BUTTON, Localization.TownMenu.GENERAL_SETTINGS_MENU_BUTTON_LORE);
		ItemCreator.ItemCreatorBuilder inviteMenuItem = ItemCreator.of(CompMaterial.BELL, Localization.TownMenu.INVITE_PLAYER_MENU_BUTTON, Localization.TownMenu.INVITE_PLAYER_MENU_BUTTON_LORE);
		ItemCreator.ItemCreatorBuilder extraInfoItem = ItemCreator.of(CompMaterial.ENCHANTED_BOOK, Localization.TownMenu.EXTRA_INFO_MENU_BUTTON, Localization.TownMenu.EXTRA_INFO_MENU_BUTTON_LORE);

		toggleMenuButton = new ButtonMenu(new ToggleSettingsMenu(town), toggleMenuItem);
		residentListButton = new ButtonMenu(new ResidentListMenu(residentList), residentListItem);
		townyPermButton = new ButtonMenu(new TownyPermMenu(town), permissionsMenuItem);

		if (Settings.ECONOMY_ENABLED) {
			if (TownySettings.isBankActionLimitedToBankPlots()) {
				if (TownyAPI.getInstance().getTownBlock(player.getLocation()) != null)
					if (!TownyAPI.getInstance().getTownBlock(player.getLocation()).getType().equals(TownBlockType.BANK))
						economyButton = new Button() {
							@Override
							public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
								Common.tell(player, Localization.Error.MUST_BE_IN_BANK);
								player.closeInventory();
							}

							@Override
							public ItemStack getItem() {
								return ItemCreator.of(CompMaterial.EMERALD_BLOCK, Localization.TownMenu.ECONOMY_MENU_BUTTON, Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE).build().make();
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
							return ItemCreator.of(CompMaterial.EMERALD_BLOCK, Localization.TownMenu.ECONOMY_MENU_BUTTON, Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE).build().make();
						}
					};
			} else if (TownySettings.isBankActionDisallowedOutsideTown()) {
				if (TownyAPI.getInstance().getTownBlock(player.getLocation()) != null)
					if (!TownyAPI.getInstance().getTownBlock(player.getLocation()).getTown().equals(town)) {
						economyButton = new Button() {
							@Override
							public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
								Common.tell(player, Localization.Error.MUST_BE_IN_TOWN);
								player.closeInventory();
							}

							@Override
							public ItemStack getItem() {
								return ItemCreator.of(CompMaterial.EMERALD_BLOCK, Localization.TownMenu.ECONOMY_MENU_BUTTON, Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE).build().make();
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
							return ItemCreator.of(CompMaterial.EMERALD_BLOCK, Localization.TownMenu.ECONOMY_MENU_BUTTON, Localization.TownMenu.ECONOMY_MENU_BUTTON_LORE).build().make();
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
					return null;
				}
			};
		}

		generalSettingsButton = new ButtonMenu(new GeneralSettingsMenu(town), settingsMenuItem);

		invitePlayerButton = new ButtonMenu(new InvitePlayerMenu(allOnlineResidents), inviteMenuItem);

		extraInfoButton = new ButtonMenu(new ExtraTownInfo(), extraInfoItem);

		if (TownyAPI.getInstance().getTownBlock(player.getLocation()) != null && town.hasTownBlock(TownyAPI.getInstance().getTownBlock(player.getLocation())))
			plotMenuButton = new ButtonMenu(new PlotMenu(TownyAPI.getInstance().getTownBlock(player.getLocation())), plotMenuItem);
		else
			plotMenuButton = Button.makeDummy(plotMenuItem);

	}


	@Override
	public ItemStack getItemAt(int slot) {

		if (slot == 2)
			return toggleMenuButton.getItem();
		if (slot == 4)
			return residentListButton.getItem();
		if (slot == 6)
			return townyPermButton.getItem();

		if (slot == 9 * 2 + 2)
			return generalSettingsButton.getItem();
		if (slot == 9 * 2 + 4 && Settings.ECONOMY_ENABLED)
			return economyButton.getItem();
		if (slot == 9 * 2 + 6)
			return invitePlayerButton.getItem();

		if (slot == 9 * 3 + 3)
			return extraInfoButton.getItem();
		if (slot == 9 * 3 + 5)
			return plotMenuButton.getItem();


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


		public ToggleSettingsMenu(Town town) {
			super(TownMenu.this);

			setSize(9 * 2);

			setTitle(Localization.TownMenu.ToggleMenu.MENU_TITLE);

			setInfo(Localization.TownMenu.ToggleMenu.INFO);
			Button.setInfoButtonTitle(Localization.MENU_INFORMATION);

			fireToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.setFire(!town.isFire());
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.CAMPFIRE, Localization.TownMenu.ToggleMenu.FIRE, "", town.isFire() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON).build().make();
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

					return ItemCreator.of(CompMaterial.SHULKER_SPAWN_EGG, Localization.TownMenu.ToggleMenu.MOBS, "", town.hasMobs() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON).build().make();

				}
			};
			explosionToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {

					town.setBANG(!town.isBANG());
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.TNT, Localization.TownMenu.ToggleMenu.EXPLODE, "", town.isBANG() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_OFF).build().make();

				}
			};
			pvpToggle = new Button() {
				@SneakyThrows
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					if (TownySettings.getOutsidersPreventPVPToggle()) {
						Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
						for (Player onlinePlayer : onlinePlayers) {
							Resident onlinePlayerAsRes = TownyAPI.getInstance().getDataSource().getResident(onlinePlayer.getName());
							if (onlinePlayerAsRes.hasTown()) {
								if (!onlinePlayerAsRes.getTown().equals(town))
									if (TownyAPI.getInstance().getTownBlock(onlinePlayer.getLocation()) != null)
										if (TownyAPI.getInstance().getTownBlock(onlinePlayer.getLocation()).getTown().equals(town)) {
											Common.tell(player, Localization.Error.TOGGLE_PVP_OUTSIDERS);
											player.closeInventory();
											return;
										}
							} else {
								if (TownyAPI.getInstance().getTownBlock(onlinePlayer.getLocation()) != null)
									if (TownyAPI.getInstance().getTownBlock(onlinePlayer.getLocation()).getTown().equals(town)) {
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
					return ItemCreator.of(CompMaterial.GOLDEN_SWORD, Localization.TownMenu.ToggleMenu.PVP, "", town.isPVP() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON).build().make();
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
					return ItemCreator.of(CompMaterial.CHEST, Localization.TownMenu.ToggleMenu.PUBLIC, "", town.isPublic() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON).build().make();
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

					return ItemCreator.of(CompMaterial.ACACIA_DOOR, Localization.TownMenu.ToggleMenu.OPEN, "", town.isOpen() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON).build().make();

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

					return ItemCreator.of(CompMaterial.EMERALD, Localization.TownMenu.ToggleMenu.TAX_PERCENT, "", town.isTaxPercentage() ? Localization.TownMenu.ToggleMenu.TOGGLE_OFF : Localization.TownMenu.ToggleMenu.TOGGLE_ON).build().make();

				}
			};

		}

		@Override
		public ItemStack getItemAt(int slot) {

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

			return null;
		}
	}

	public class ResidentListMenu extends MenuPagged<Resident> {


		protected ResidentListMenu(Iterable<Resident> pages) {
			super(TownMenu.this, pages);
			setTitle(Localization.TownMenu.ResidentMenu.MENU_TITLE);
			setInfo(Localization.TownMenu.ResidentMenu.INFO);
			Button.setInfoButtonTitle(Localization.MENU_INFORMATION);
		}

		@Override
		protected ItemStack convertToItemStack(Resident item) {
			ItemStack itemSkull = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta skull = (SkullMeta) itemSkull.getItemMeta();
			skull.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + item.getFormattedTitleName());
			if (item.getUUID() == null)
				return null;
			OfflinePlayer player = Bukkit.getOfflinePlayer(item.getUUID());
			skull.setOwningPlayer(player);
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add(ChatColor.GRAY + Localization.TownMenu.ResidentMenu.ONLINE + TimeUtil.getFormattedDateShort(item.getLastOnline()));
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
			new ResidentMenu(item).displayTo(player);
		}
	}

	public class ResidentMenu extends Menu {


		private final Button kickButton;
		private final Button titleButton;
		private final Button rankButton;
		private final Button mayorButton;


		protected ResidentMenu(Resident resident) {
			super(TownMenu.this);

			setTitle(Localization.TownMenu.ResidentMenu.MENU_TITLE);


			kickButton = new ButtonConversation(new TownKickPrompt(resident), ItemCreator.of(CompMaterial.REDSTONE_BLOCK, Localization.TownMenu.ResidentMenu.KICK, Localization.TownMenu.ResidentMenu.KICK_LORE));

			titleButton = new ButtonConversation(new TownTitlePrompt(resident), ItemCreator.of(CompMaterial.NAME_TAG, Localization.TownMenu.ResidentMenu.TITLE, Localization.TownMenu.ResidentMenu.TITLE_LORE));

			rankButton = new ButtonConversation(new TownRankPrompt(resident), ItemCreator.of(CompMaterial.IRON_SWORD, Localization.TownMenu.ResidentMenu.RANK, Localization.TownMenu.ResidentMenu.RANK_LORE));

			mayorButton = new ButtonConversation(new TownGiveMayorPrompt(resident), ItemCreator.of(CompMaterial.GOLDEN_APPLE, Localization.TownMenu.ResidentMenu.MAYOR, Localization.TownMenu.ResidentMenu.MAYOR_LORE));
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (slot == 9 + 1)
				return kickButton.getItem();
			if (slot == 9 + 3)
				return titleButton.getItem();
			if (slot == 9 + 5)
				return rankButton.getItem();
			if (slot == 9 + 7)
				return mayorButton.getItem();


			return null;
		}
	}

	public class TownyPermMenu extends Menu {

		private final ItemStack BUILD_BUTTON = ItemCreator.of(CompMaterial.BRICKS, Localization.TownMenu.PlayerPermissionsMenu.BUILD, Localization.TownMenu.PlayerPermissionsMenu.BUILD_LORE).build().make();
		private final ItemStack BREAK_BUTTON = ItemCreator.of(CompMaterial.GOLDEN_PICKAXE, Localization.TownMenu.PlayerPermissionsMenu.BREAK, Localization.TownMenu.PlayerPermissionsMenu.BREAK_LORE).build().make();
		private final ItemStack ITEM_USE_BUTTON = ItemCreator.of(CompMaterial.FLINT_AND_STEEL, Localization.TownMenu.PlayerPermissionsMenu.USE, Localization.TownMenu.PlayerPermissionsMenu.USE_LORE).build().make();
		private final ItemStack SWITCH_BUTTON = ItemCreator.of(CompMaterial.LEVER, Localization.TownMenu.PlayerPermissionsMenu.SWITCH, Localization.TownMenu.PlayerPermissionsMenu.SWITCH_LORE).build().make();

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


		protected TownyPermMenu(Town town) {
			super(TownMenu.this);
			setSize(9 * 6);
			setInfo(Localization.TownMenu.PlayerPermissionsMenu.INFO);
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
					return ItemCreator.of(CompMaterial.BELL, Localization.TownMenu.PlayerPermissionsMenu.BUILD_RES, "", Localization.TownMenu.PlayerPermissionsMenu.BUILD_RES2, town.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, Localization.TownMenu.PlayerPermissionsMenu.BUILD_NATION, "", Localization.TownMenu.PlayerPermissionsMenu.BUILD_NATION2, town.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CARROT, Localization.TownMenu.PlayerPermissionsMenu.BUILD_ALLY, "", Localization.TownMenu.PlayerPermissionsMenu.BUILD_ALLY2, town.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BONE, Localization.TownMenu.PlayerPermissionsMenu.BUILD_OUTSIDER, "", Localization.TownMenu.PlayerPermissionsMenu.BUILD_OUTSIDER2, town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BELL, Localization.TownMenu.PlayerPermissionsMenu.BREAK_RES, "", Localization.TownMenu.PlayerPermissionsMenu.BREAK_RES2, town.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, Localization.TownMenu.PlayerPermissionsMenu.BREAK_NATION, "", Localization.TownMenu.PlayerPermissionsMenu.BREAK_NATION2, town.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CARROT, Localization.TownMenu.PlayerPermissionsMenu.BREAK_ALLY, "", Localization.TownMenu.PlayerPermissionsMenu.BREAK_ALLY2, town.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BONE, Localization.TownMenu.PlayerPermissionsMenu.BREAK_OUTSIDER, "", Localization.TownMenu.PlayerPermissionsMenu.BREAK_OUTSIDER2, town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BELL, Localization.TownMenu.PlayerPermissionsMenu.USE_RES, "", Localization.TownMenu.PlayerPermissionsMenu.USE_RES2, town.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, Localization.TownMenu.PlayerPermissionsMenu.USE_NATION, "", Localization.TownMenu.PlayerPermissionsMenu.USE_NATION2, town.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CARROT, Localization.TownMenu.PlayerPermissionsMenu.USE_ALLY, "", Localization.TownMenu.PlayerPermissionsMenu.USE_ALLY2, town.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BONE, Localization.TownMenu.PlayerPermissionsMenu.USE_OUTSIDER, "", Localization.TownMenu.PlayerPermissionsMenu.USE_OUTSIDER2, town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BELL, Localization.TownMenu.PlayerPermissionsMenu.SWITCH_RES, "", Localization.TownMenu.PlayerPermissionsMenu.SWITCH_RES2, town.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, Localization.TownMenu.PlayerPermissionsMenu.SWITCH_NATION, "", Localization.TownMenu.PlayerPermissionsMenu.SWITCH_NATION2, town.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CARROT, Localization.TownMenu.PlayerPermissionsMenu.SWITCH_ALLY, "", Localization.TownMenu.PlayerPermissionsMenu.SWITCH_ALLY2, town.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BONE, Localization.TownMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER, "", Localization.TownMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER2, town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH) ? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.TownMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.REDSTONE_BLOCK, Localization.TownMenu.PlayerPermissionsMenu.RESET, Localization.TownMenu.PlayerPermissionsMenu.RESET_LORE).build().make();
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
					return ItemCreator.of(CompMaterial.EMERALD_BLOCK, Localization.TownMenu.PlayerPermissionsMenu.ON, Localization.TownMenu.PlayerPermissionsMenu.ON_LORE).build().make();
				}
			};


		}

		@Override
		public ItemStack getItemAt(int slot) {

			if (slot == 9 + 1)
				return BUILD_BUTTON;
			if (slot == 9 * 2 + 1)
				return BREAK_BUTTON;
			if (slot == 9 * 3 + 1)
				return ITEM_USE_BUTTON;
			if (slot == 9 * 4 + 1)
				return SWITCH_BUTTON;

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


			return null;
		}
	}

	public class EconomyManagementMenu extends Menu {

		private final ItemStack balanceButton;
		private final Button depositButton;
		private final Button withdrawButton;
		private final Button setTaxButton;


		protected EconomyManagementMenu(Town town) {
			super(TownMenu.this);
			setSize(9 * 2);
			setTitle(Localization.TownMenu.EconomyMenu.MENU_TITLE);

			balanceButton = ItemCreator.of(CompMaterial.EMERALD_BLOCK, Localization.TownMenu.EconomyMenu.BALANCE, "", "&a" + town.getAccount().getHoldingFormattedBalance(), "", Localization.TownMenu.EconomyMenu.UPKEEP + Settings.MONEY_SYMBOL + TownySettings.getTownUpkeepCost(town)).build().make();

			depositButton = new ButtonConversation(new TownDepositPrompt(town), ItemCreator.of(CompMaterial.CHEST, Localization.TownMenu.EconomyMenu.DEPOSIT, Localization.TownMenu.EconomyMenu.DEPOSIT_LORE));

			withdrawButton = new ButtonConversation(new TownWithdrawPrompt(town), ItemCreator.of(CompMaterial.ENDER_CHEST, Localization.TownMenu.EconomyMenu.WITHDRAW, Localization.TownMenu.EconomyMenu.WITHDRAW_LORE));

			setTaxButton = new ButtonConversation(new TownSetTaxPrompt(town), CompMaterial.ARROW, Localization.TownMenu.EconomyMenu.TAX, "", town.isTaxPercentage() ? Localization.TownMenu.EconomyMenu.TAX_PERCENTAGE : Localization.TownMenu.EconomyMenu.TAX_AMOUNT);

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

			return null;
		}
	}


	public class GeneralSettingsMenu extends Menu {

		private final Button setSpawnButton;
		private final Button setHomeBlockButton;
		private final Button townBoardButton;
		private final Button townNameButton;

		protected GeneralSettingsMenu(Town town) {
			super(TownMenu.this);
			setSize(9 * 2);
			setInfo(Localization.TownMenu.GeneralSettingsMenu.INFO);
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
					return ItemCreator.of(CompMaterial.GLOWSTONE, Localization.TownMenu.GeneralSettingsMenu.SET_HOME_BLOCK, Localization.TownMenu.GeneralSettingsMenu.SET_HOME_BLOCK_LORE).build().make();
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
					return ItemCreator.of(CompMaterial.LAPIS_LAZULI, Localization.TownMenu.GeneralSettingsMenu.SET_SPAWN, Localization.TownMenu.GeneralSettingsMenu.SET_SPAWN_LORE).build().make();
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
					return ItemCreator.of(CompMaterial.NAME_TAG, Localization.TownMenu.GeneralSettingsMenu.SET_NAME, Localization.TownMenu.GeneralSettingsMenu.SET_NAME_LORE).build().make();
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
					return ItemCreator.of(CompMaterial.OAK_SIGN, Localization.TownMenu.GeneralSettingsMenu.SET_BOARD, Localization.TownMenu.GeneralSettingsMenu.SET_BOARD_LORE).build().make();
				}
			};


		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (slot == 1)
				return setHomeBlockButton.getItem();
			if (slot == 3)
				return setSpawnButton.getItem();
			if (slot == 5)
				return townNameButton.getItem();
			if (slot == 7)
				return townBoardButton.getItem();

			return null;
		}
	}

	public class InvitePlayerMenu extends MenuPagged<Resident> {


		protected InvitePlayerMenu(Iterable<Resident> pages) {
			super(TownMenu.this, pages);
		}

		@Override
		protected ItemStack convertToItemStack(Resident item) {
			ItemStack itemSkull = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta skull = (SkullMeta) itemSkull.getItemMeta();
			skull.setDisplayName(item.getName());
			OfflinePlayer player = Bukkit.getOfflinePlayer(item.getUUID());
			skull.setOwningPlayer(player);
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

		private final ItemStack claimInfo = ItemCreator.of(CompMaterial.GOLDEN_AXE, Localization.TownMenu.ExtraInfoMenu.CLAIMING, Localization.TownMenu.ExtraInfoMenu.CLAIMING_LORE).build().make();
		private final ItemStack extraCommands = ItemCreator.of(CompMaterial.BOOKSHELF, Localization.TownMenu.ExtraInfoMenu.COMMANDS, Localization.TownMenu.ExtraInfoMenu.COMMANDS_LORE).build().make();

		protected ExtraTownInfo() {
			super(TownMenu.this);
			setSize(9);
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (slot == 2)
				return claimInfo;
			if (slot == 6)
				return extraCommands;

			return null;
		}
	}

}
