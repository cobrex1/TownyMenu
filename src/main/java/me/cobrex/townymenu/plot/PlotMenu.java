package me.cobrex.townymenu.plot;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.event.TownBlockSettingsChangedEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.*;
import lombok.SneakyThrows;
import me.cobrex.townymenu.plot.prompt.PlotEvictPrompt;
import me.cobrex.townymenu.plot.prompt.PlotForSalePrompt;
import me.cobrex.townymenu.plot.prompt.PlotNotForSalePrompt;
import me.cobrex.townymenu.plot.prompt.PlotSetTypePrompt;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.town.TownMenu;
import me.cobrex.townymenu.utils.HeadDatabaseUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.debug.LagCatcher;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonConversation;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.List;

public class PlotMenu extends Menu {

	private final Button toggleSettingsMenu;
	private final Button permMenuButton;
	private final Button plotAdministrationMenuButton;
	private final Button friendButton;
	private final Button backToTownButton;
	private final Town town;

	private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_PLOT_MENU)), "")
			.modelData(Integer.valueOf(Settings.FILLER_PLOT_MENU_CMD)).make();

	public PlotMenu(TownBlock townBlock) throws NotRegisteredException {

		if (Settings.USE_FIXED_INVENTORY_SIZE) {
			setSize(9 * 6);
		} else {
			setSize(9);
		}

		setTitle(Localization.PlotMenu.MAIN_MENU_TITLE);

		List<Resident> allOnlineResidents = new ArrayList<>();
		LagCatcher.start("load-residents-online");
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			Resident res = TownyAPI.getInstance().getResident(onlinePlayer.getName());
			if (res != null) allOnlineResidents.add(res);
		}

		if (Material.getMaterial(Settings.PLOT_TOGGLE_MENU).equals(Material.LEATHER_BOOTS)) {
			toggleSettingsMenu = new ButtonMenu(new PlotToggleSettingsMenu(townBlock),
					ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_TOGGLE_MENU)))
							.name(Localization.PlotMenu.TOGGLE_SETTINGS_MENU_BUTTON)
							.color(Settings.NEUTRAL_BUTTON_COLOR)
							.modelData(Integer.valueOf(Settings.PLOT_TOGGLE_MENU_CMD))
							.lore(Localization.PlotMenu.TOGGLE_SETTINGS_MENU_BUTTON_LORE));
		} else {
			toggleSettingsMenu = new ButtonMenu(new PlotToggleSettingsMenu(townBlock),
					ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_TOGGLE_MENU)))
							.name(Localization.PlotMenu.TOGGLE_SETTINGS_MENU_BUTTON)
							.modelData(Integer.valueOf(Settings.PLOT_TOGGLE_MENU_CMD))
							.lore(Localization.PlotMenu.TOGGLE_SETTINGS_MENU_BUTTON_LORE));
		}


		if (Material.getMaterial(Settings.PLOT_PERMISSIONS_MENU).equals(Material.LEATHER_BOOTS)) {
			permMenuButton = new ButtonMenu(new PlotPermMenu(townBlock),
					ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_PERMISSIONS_MENU)))
							.name(Localization.PlotMenu.PERMISSIONS_MENU_BUTTON)
							.color(Settings.NEUTRAL_BUTTON_COLOR)
							.modelData(Integer.valueOf(Settings.PLOT_PERMISSIONS_MENU_CMD))
							.lore(Localization.PlotMenu.PERMISSIONS_MENU_BUTTON_LORE));
		} else {
			permMenuButton = new ButtonMenu(new PlotPermMenu(townBlock),
					ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_PERMISSIONS_MENU)))
							.name(Localization.PlotMenu.PERMISSIONS_MENU_BUTTON)
							.modelData(Integer.valueOf(Settings.PLOT_PERMISSIONS_MENU_CMD))
							.lore(Localization.PlotMenu.PERMISSIONS_MENU_BUTTON_LORE));
		}


		if (Material.getMaterial(Settings.PLOT_ADMIN_MENU).equals(Material.LEATHER_BOOTS)) {
			plotAdministrationMenuButton = new ButtonMenu(new PlotAdministrationMenu(townBlock),
					ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ADMIN_MENU)))
							.name(Localization.PlotMenu.PLOT_ADMIN_MENU_BUTTON)
							.color(Settings.NEUTRAL_BUTTON_COLOR)
							.modelData(Integer.valueOf(Settings.PLOT_ADMIN_MENU_CMD))
							.lore(Localization.PlotMenu.PLOT_ADMIN_MENU_BUTTON_LORE));
		} else {
			plotAdministrationMenuButton = new ButtonMenu(new PlotAdministrationMenu(townBlock),
					ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ADMIN_MENU)))
							.name(Localization.PlotMenu.PLOT_ADMIN_MENU_BUTTON)
							.modelData(Integer.valueOf(Settings.PLOT_ADMIN_MENU_CMD))
							.lore(Localization.PlotMenu.PLOT_ADMIN_MENU_BUTTON_LORE));
		}


		if (Material.getMaterial(Settings.PLOT_FRIEND_MENU).equals(Material.LEATHER_BOOTS)) {
			friendButton = new ButtonMenu(new FriendPlayerMenu(allOnlineResidents),
					ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_FRIEND_MENU)))
							.name(Localization.PlotMenu.FRIEND_MENU_BUTTON)
							.color(Settings.NEUTRAL_BUTTON_COLOR)
							.modelData(Integer.valueOf(Settings.PLOT_FRIEND_MENU_CMD))
							.lore(Localization.PlotMenu.FRIEND_MENU_BUTTON_LORE));

		} else {
			friendButton = new ButtonMenu(new FriendPlayerMenu(allOnlineResidents),
					ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_FRIEND_MENU)))
							.name(Localization.PlotMenu.FRIEND_MENU_BUTTON)
							.modelData(Integer.valueOf(Settings.PLOT_FRIEND_MENU_CMD))
							.lore(Localization.PlotMenu.FRIEND_MENU_BUTTON_LORE));

		}

		backToTownButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {

				try {
					new TownMenu(town, player).displayTo(player);
				} catch (NotRegisteredException e) {
					throw new RuntimeException(e);
				}

			}

			@Override
			public ItemStack getItem() {
				if (Material.getMaterial(Settings.TOWN_INFO_BUTTON).equals(Material.LEATHER_BOOTS)) {
					return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_INFO_BUTTON)))
							.name(Localization.PlotMenu.TOWN_MENU_BUTTON)
							.color(Settings.NEUTRAL_BUTTON_COLOR)
							.modelData(Settings.TOWN_INFO_BUTTON_CMD)
							.lore(Localization.PlotMenu.TOWN_MENU_BUTTON_LORE)
							.make();
				} else {
					return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.TOWN_INFO_BUTTON)))
							.name(Localization.PlotMenu.TOWN_MENU_BUTTON)
							.modelData(Settings.TOWN_INFO_BUTTON_CMD)
							.lore(Localization.PlotMenu.TOWN_MENU_BUTTON_LORE)
							.make();
				}

			}
		};

		town = townBlock.getTown();
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (!Settings.USE_FIXED_INVENTORY_SIZE) {
			if (slot == 1)
				return toggleSettingsMenu.getItem();
			if (slot == 3)
				return permMenuButton.getItem();
			if (slot == 5)
				return plotAdministrationMenuButton.getItem();
			if (slot == 7)
				return friendButton.getItem();
			if (slot == 26)
				return backToTownButton.getItem();
		} else {
			if (slot == 10)
				return toggleSettingsMenu.getItem();
			if (slot == 12)
				return permMenuButton.getItem();
			if (slot == 14)
				return plotAdministrationMenuButton.getItem();
			if (slot == 16)
				return friendButton.getItem();
			if (slot == 53)
				return backToTownButton.getItem();
		}


		return DUMMY_BUTTON;
	}

	public class FriendPlayerMenu extends MenuPagged<Resident> {

		public String[] getInfo() {
			return Localization.PlotMenu.FriendMenu.INFO;
		}

		protected FriendPlayerMenu(Iterable<Resident> pages) {
			super(PlotMenu.this, pages);
			setTitle(Localization.PlotMenu.FriendMenu.MENU_TITLE);

			if (Settings.USE_FIXED_INVENTORY_SIZE) {
				setSize(9 * 6);
			}
		}

		@Override
		protected ItemStack convertToItemStack(Resident item) {
			if (item.getUUID() == null)
				return null;
			ItemStack itemSkull = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta skull = (SkullMeta) itemSkull.getItemMeta();
			skull.setDisplayName(ChatColor.YELLOW + "" + (item.getName()));
			Player player = Bukkit.getPlayer(item.getUUID());
			skull.setOwningPlayer(player);
			skull.setCustomModelData(Settings.RESIDENT_LIST_PLAYER_HEAD_CMD);
			itemSkull.setItemMeta(skull);
			for (Player players : Bukkit.getOnlinePlayers()) {
				Resident res = TownyAPI.getInstance().getResident(players.getName());

				assert res != null;
				if (res.getFriends().contains(item)) {
					List<String> lore = new ArrayList<>();
					lore.add("");
					lore.add(ChatColor.translateAlternateColorCodes('&', Localization.PlotMenu.FriendMenu.CLICK_REMOVE_LORE));
//					lore.add(ChatColor.RED + Localization.PlotMenu.FriendMenu.CLICK_REMOVE_LORE);
					skull.setLore(lore);
					itemSkull.setItemMeta(skull);
					return itemSkull;
				} else {
					List<String> lore = new ArrayList<>();
					lore.add("");
					lore.add(ChatColor.translateAlternateColorCodes('&', Localization.PlotMenu.FriendMenu.CLICK_ADD_LORE));
//					lore.add(ChatColor.GREEN + Localization.PlotMenu.FriendMenu.CLICK_ADD_LORE);
					skull.setLore(lore);
				}
			}
			itemSkull.setItemMeta(skull);
			return itemSkull;
		}

		@SneakyThrows
		@Override
		protected void onPageClick(Player player, Resident item, ClickType click) {

			Resident playerResident = TownyAPI.getInstance().getResident(player.getName());

			if (item.equals(playerResident))
				return;
			if (playerResident.getFriends().contains(item)) {
				playerResident.removeFriend(item);
				Common.tell(player, Localization.PlotMenu.FriendMenu.REMOVE.replace("{player}", item.getName()));
			} else {
				playerResident.addFriend(item);
				Common.tell(player, Localization.PlotMenu.FriendMenu.ADD.replace("{player}", item.getName()));
			}
			TownyAPI.getInstance().getDataSource().saveResident(playerResident);
			restartMenu();
		}
	}

	public class PlotToggleSettingsMenu extends Menu {

		private final Button fireToggle;
		private final Button mobsToggle;
		private final Button explosionToggle;
		private final Button pvpToggle;

		private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_PLOT_TOGGLE_MENU)), "").make();

		@Override
		public String[] getInfo() {
			return Localization.PlotMenu.ToggleMenu.INFO;
		}

		public PlotToggleSettingsMenu(TownBlock townBlock) {
			super(PlotMenu.this);

			if (Settings.USE_FIXED_INVENTORY_SIZE) {
				setSize(9 * 6);
			} else {
				setSize(9 * 2);
			}


			setTitle(Localization.PlotMenu.ToggleMenu.MENU_TITLE);
			Button.setInfoButtonTitle(Localization.MENU_INFORMATION);

			fireToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH), TownyPermission.PermLevel.OUTSIDER, TownyPermission.ActionType.SWITCH);
					restartMenu();
					townBlock.getPermissions().fire = !townBlock.getPermissions().fire;
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_TOGGLE_FIRE).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_TOGGLE_FIRE)))
								.name(Localization.PlotMenu.ToggleMenu.FIRE)
								.modelData(Integer.valueOf(Settings.PLOT_TOGGLE_FIRE_CMD))
								.color(townBlock.getPermissions().fire ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore("" + (townBlock.getPermissions().fire ? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF : Localization.PlotMenu.ToggleMenu.TOGGLE_ON)).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_TOGGLE_FIRE)))
								.name(Localization.PlotMenu.ToggleMenu.FIRE)
								.modelData(Integer.valueOf(Settings.PLOT_TOGGLE_FIRE_CMD))
								.lore("")
								.lore("" + (townBlock.getPermissions().fire ? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF : Localization.PlotMenu.ToggleMenu.TOGGLE_ON)).make();

					}
				}
			};
			mobsToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().mobs = !townBlock.getPermissions().mobs;
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_TOGGLE_MOBS).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_TOGGLE_MOBS)))
								.name(Localization.PlotMenu.ToggleMenu.MOBS)
								.modelData(Integer.valueOf(Settings.PLOT_TOGGLE_MOBS_CMD))
								.color(townBlock.getPermissions().mobs ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore("" + (townBlock.getPermissions().mobs ? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF : Localization.PlotMenu.ToggleMenu.TOGGLE_ON)).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_TOGGLE_MOBS)))
								.name(Localization.PlotMenu.ToggleMenu.MOBS)
								.modelData(Integer.valueOf(Settings.PLOT_TOGGLE_MOBS_CMD))
								.lore("")
								.lore("" + (townBlock.getPermissions().mobs ? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF : Localization.PlotMenu.ToggleMenu.TOGGLE_ON)).make();

					}
				}
			};
			explosionToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {

					townBlock.getPermissions().explosion = !townBlock.getPermissions().explosion;
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_TOGGLE_EXPLOSIONS).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_TOGGLE_EXPLOSIONS)))
								.name(Localization.PlotMenu.ToggleMenu.EXPLODE)
								.modelData(Integer.valueOf(Settings.PLOT_TOGGLE_EXPLOSIONS_CMD))
								.color(townBlock.getPermissions().explosion ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore("" + (townBlock.getPermissions().explosion ? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF : Localization.PlotMenu.ToggleMenu.TOGGLE_ON)).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_TOGGLE_EXPLOSIONS)))
								.name(Localization.PlotMenu.ToggleMenu.EXPLODE)
								.modelData(Integer.valueOf(Settings.PLOT_TOGGLE_EXPLOSIONS_CMD))
								.lore("")
								.lore("" + (townBlock.getPermissions().explosion ? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF : Localization.PlotMenu.ToggleMenu.TOGGLE_ON)).make();

					}
				}
			};
			pvpToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().pvp = !townBlock.getPermissions().pvp;
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_TOGGLE_PVP).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_TOGGLE_PVP)))
								.name(Localization.PlotMenu.ToggleMenu.PVP)
								.modelData(Integer.valueOf(Settings.PLOT_TOGGLE_PVP_CMD))
								.color(townBlock.getPermissions().pvp ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore("" + (townBlock.getPermissions().pvp ? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF : Localization.PlotMenu.ToggleMenu.TOGGLE_ON)).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_TOGGLE_PVP)))
								.name(Localization.PlotMenu.ToggleMenu.PVP)
								.modelData(Integer.valueOf(Settings.PLOT_TOGGLE_PVP_CMD))
								.lore("")
								.lore("" + (townBlock.getPermissions().pvp ? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF : Localization.PlotMenu.ToggleMenu.TOGGLE_ON)).make();

					}
				}
			};
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (!Settings.USE_FIXED_INVENTORY_SIZE) {
				if (slot == 1)
					return fireToggle.getItem();
				if (slot == 3)
					return mobsToggle.getItem();
				if (slot == 5)
					return explosionToggle.getItem();
				if (slot == 7)
					return pvpToggle.getItem();
			} else {
				if (slot == 10)
					return fireToggle.getItem();
				if (slot == 12)
					return mobsToggle.getItem();
				if (slot == 14)
					return explosionToggle.getItem();
				if (slot == 16)
					return pvpToggle.getItem();
			}

			return DUMMY_BUTTON;
		}
	}

	public class PlotPermMenu extends Menu {

		private final ItemStack BUILD_BUTTON = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_BUILD)))
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD)
				.modelData(Integer.valueOf(Settings.PLOT_BUILD_CMD))
				.lore((List<String>) Localization.PlotMenu.PlayerPermissionsMenu.BUILD_LORE).make();

		private final ItemStack BUILD_BUTTON_COLORED = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem("LEATHER_BOOTS"))
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD)
				.modelData(Integer.valueOf(Settings.PLOT_BUILD_CMD))
				.color(Settings.NEUTRAL_BUTTON_COLOR)
				.lore((List<String>) Localization.PlotMenu.PlayerPermissionsMenu.BUILD_LORE).make();

		private final ItemStack BREAK_BUTTON = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_BREAK)))
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BREAK)
				.modelData(Integer.valueOf(Settings.PLOT_BREAK_CMD))
				.lore((List<String>) Localization.PlotMenu.PlayerPermissionsMenu.BREAK_LORE).make();

		private final ItemStack BREAK_BUTTON_COLORED = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem("LEATHER_BOOTS"))
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BREAK)
				.modelData(Integer.valueOf(Settings.PLOT_BREAK_CMD))
				.color(Settings.NEUTRAL_BUTTON_COLOR)
				.lore((List<String>) Localization.PlotMenu.PlayerPermissionsMenu.BREAK_LORE).make();

		private final ItemStack ITEM_USE_BUTTON = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ITEM_USE)))
				.name(Localization.PlotMenu.PlayerPermissionsMenu.USE)
				.modelData(Integer.valueOf(Settings.PLOT_ITEM_USE_CMD))
				.lore((List<String>) Localization.PlotMenu.PlayerPermissionsMenu.USE_LORE).make();

		private final ItemStack ITEM_USE_BUTTON_COLORED = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem("LEATHER_BOOTS"))
				.name(Localization.PlotMenu.PlayerPermissionsMenu.USE)
				.modelData(Integer.valueOf(Settings.PLOT_ITEM_USE_CMD))
				.color(Settings.NEUTRAL_BUTTON_COLOR)
				.lore((List<String>) Localization.PlotMenu.PlayerPermissionsMenu.USE_LORE).make();

		private final ItemStack SWITCH_BUTTON = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_SWITCH)))
				.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH)
				.modelData(Integer.valueOf(Settings.PLOT_SWITCH_CMD))
				.lore((List<String>) Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_LORE).make();

		private final ItemStack SWITCH_BUTTON_COLORED = ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem("LEATHER_BOOTS"))
				.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH)
				.modelData(Integer.valueOf(Settings.PLOT_SWITCH_CMD))
				.color(Settings.NEUTRAL_BUTTON_COLOR)
				.lore((List<String>) Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_LORE).make();

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

		private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_PLOT_PERMS_MENU)), "").make();

		@Override
		public String[] getInfo() {
			return Localization.PlotMenu.PlayerPermissionsMenu.INFO;
		}

		protected PlotPermMenu(TownBlock townBlock) {
			super(PlotMenu.this);
			setSize(9 * 6);
			setTitle(Localization.PlotMenu.PlayerPermissionsMenu.MENU_TITLE);
			Button.setInfoButtonTitle(Localization.MENU_INFORMATION);

			buildResidentButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD), TownyPermission.PermLevel.RESIDENT, TownyPermission.ActionType.BUILD);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_RESIDENT_BUILD).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_RESIDENT_BUILD)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_RES)
								.modelData(Integer.valueOf(Settings.PLOT_RESIDENT_BUILD_CMD))
								.color(townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_RES2)
								.lore("" + (townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", (Localization.PlotMenu.PlayerPermissionsMenu.CHANGE)).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_RESIDENT_BUILD)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_RES)
								.modelData(Integer.valueOf(Settings.PLOT_RESIDENT_BUILD_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_RES2)
								.lore("" + (townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", (Localization.PlotMenu.PlayerPermissionsMenu.CHANGE)).make();

					}
				}
			};
			buildNationButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD), TownyPermission.PermLevel.NATION, TownyPermission.ActionType.BUILD);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_NATION_BUILD).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_NATION_BUILD)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_NATION)
								.modelData(Integer.valueOf(Settings.PLOT_NATION_BUILD_CMD))
								.color(townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_NATION2)
								.lore("" + (townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_NATION_BUILD)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_NATION)
								.modelData(Integer.valueOf(Settings.PLOT_NATION_BUILD_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_NATION2)
								.lore("" + (townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			buildAllyButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD), TownyPermission.PermLevel.ALLY, TownyPermission.ActionType.BUILD);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_ALLY_BUILD).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ALLY_BUILD)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_ALLY)
								.modelData(Integer.valueOf(Settings.PLOT_ALLY_BUILD_CMD))
								.color(townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_ALLY2)
								.lore("" + (townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ALLY_BUILD)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_ALLY)
								.modelData(Integer.valueOf(Settings.PLOT_ALLY_BUILD_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_ALLY2)
								.lore("" + (townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			buildOutsiderButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD), TownyPermission.PermLevel.OUTSIDER, TownyPermission.ActionType.BUILD);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_OUTSIDER_BUILD).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_OUTSIDER_BUILD)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_OUTSIDER)
								.modelData(Integer.valueOf(Settings.PLOT_OUTSIDER_BUILD_CMD))
								.color(townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_OUTSIDER2)
								.lore("" + (townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_OUTSIDER_BUILD)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_OUTSIDER)
								.modelData(Integer.valueOf(Settings.PLOT_OUTSIDER_BUILD_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_OUTSIDER2)
								.lore("" + (townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};

			// ------------------------------------------------------------------------------------
			breakResidentButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY), TownyPermission.PermLevel.RESIDENT, TownyPermission.ActionType.DESTROY);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_RESIDENT_BREAK).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_RESIDENT_BREAK)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_RES)
								.modelData(Integer.valueOf(Settings.PLOT_RESIDENT_BREAK_CMD))
								.color(townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_RES2)
								.lore("" + (townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_RESIDENT_BREAK)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_RES)
								.modelData(Integer.valueOf(Settings.PLOT_RESIDENT_BREAK_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_RES2)
								.lore("" + (townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			breakNationButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY), TownyPermission.PermLevel.NATION, TownyPermission.ActionType.DESTROY);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_NATION_BREAK).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_NATION_BREAK)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_NATION)
								.modelData(Integer.valueOf(Settings.PLOT_NATION_BREAK_CMD))
								.color(townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_NATION2)
								.lore("" + (townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_NATION_BREAK)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_NATION)
								.modelData(Integer.valueOf(Settings.PLOT_NATION_BREAK_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_NATION2)
								.lore("" + (townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			breakAllyButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY), TownyPermission.PermLevel.ALLY, TownyPermission.ActionType.DESTROY);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_ALLY_BREAK).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ALLY_BREAK)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_ALLY)
								.modelData(Integer.valueOf(Settings.PLOT_ALLY_BREAK_CMD))
								.color(townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_ALLY2)
								.lore("" + (townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ALLY_BREAK)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_ALLY)
								.modelData(Integer.valueOf(Settings.PLOT_ALLY_BREAK_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_ALLY2)
								.lore("" + (townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			breakOutsiderButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY), TownyPermission.PermLevel.OUTSIDER, TownyPermission.ActionType.DESTROY);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_OUTSIDER_BREAK).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_OUTSIDER_BREAK)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_OUTSIDER)
								.modelData(Integer.valueOf(Settings.PLOT_OUTSIDER_BREAK_CMD))
								.color(townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_OUTSIDER2)
								.lore("" + (townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_OUTSIDER_BREAK)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_OUTSIDER)
								.modelData(Integer.valueOf(Settings.PLOT_OUTSIDER_BREAK_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_OUTSIDER2)
								.lore("" + (townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};

			//------------------------------------------------------------------------------------------------------------------------------------------------------
			itemUseResidentButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE), TownyPermission.PermLevel.RESIDENT, TownyPermission.ActionType.ITEM_USE);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_RESIDENT_ITEM_USE).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_RESIDENT_ITEM_USE)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.USE_RES)
								.modelData(Integer.valueOf(Settings.PLOT_RESIDENT_ITEM_USE_CMD))
								.color(townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.USE_RES2)
								.lore("" + (townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_RESIDENT_ITEM_USE)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.USE_RES)
								.modelData(Integer.valueOf(Settings.PLOT_RESIDENT_ITEM_USE_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.USE_RES2)
								.lore("" + (townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			itemUseNationButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE), TownyPermission.PermLevel.NATION, TownyPermission.ActionType.ITEM_USE);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_NATION_ITEM_USE).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_NATION_ITEM_USE)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.USE_NATION)
								.modelData(Integer.valueOf(Settings.PLOT_NATION_ITEM_USE_CMD))
								.color(townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.USE_NATION2)
								.lore("" + (townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_NATION_ITEM_USE)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.USE_NATION)
								.modelData(Integer.valueOf(Settings.PLOT_NATION_ITEM_USE_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.USE_NATION2)
								.lore("" + (townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			itemUseAllyButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE), TownyPermission.PermLevel.ALLY, TownyPermission.ActionType.ITEM_USE);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_ALLY_ITEM_USE).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ALLY_ITEM_USE)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.USE_ALLY)
								.modelData(Integer.valueOf(Settings.PLOT_ALLY_ITEM_USE_CMD))
								.color(townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.USE_ALLY2)
								.lore("" + (townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ALLY_ITEM_USE)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.USE_ALLY)
								.modelData(Integer.valueOf(Settings.PLOT_ALLY_ITEM_USE_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.USE_ALLY2)
								.lore("" + (townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			itemUseOutsiderButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE), TownyPermission.PermLevel.OUTSIDER, TownyPermission.ActionType.ITEM_USE);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_OUTSIDER_ITEM_USE).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_OUTSIDER_ITEM_USE)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.USE_OUTSIDER)
								.modelData(Integer.valueOf(Settings.PLOT_OUTSIDER_ITEM_USE_CMD))
								.color(townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.USE_OUTSIDER2)
								.lore("" + (townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_OUTSIDER_ITEM_USE)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.USE_OUTSIDER)
								.modelData(Integer.valueOf(Settings.PLOT_OUTSIDER_ITEM_USE_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.USE_OUTSIDER2)
								.lore("" + (townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};

			//---------------------------------------------------------------------------------------------------------------------

			switchResidentButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH), TownyPermission.PermLevel.RESIDENT, TownyPermission.ActionType.SWITCH);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_RESIDENT_SWITCH).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_RESIDENT_SWITCH)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_RES)
								.modelData(Integer.valueOf(Settings.PLOT_RESIDENT_SWITCH_CMD))
								.color(townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_RES2)
								.lore("" + (townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_RESIDENT_SWITCH)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_RES)
								.modelData(Integer.valueOf(Settings.PLOT_RESIDENT_SWITCH_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_RES2)
								.lore("" + (townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			switchNationButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH), TownyPermission.PermLevel.NATION, TownyPermission.ActionType.SWITCH);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_NATION_SWITCH).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_NATION_SWITCH)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_NATION)
								.modelData(Integer.valueOf(Settings.PLOT_NATION_SWITCH_CMD))
								.color(townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_NATION2)
								.lore("" + (townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_NATION_SWITCH)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_NATION)
								.modelData(Integer.valueOf(Settings.PLOT_NATION_SWITCH_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_NATION2)
								.lore("" + (townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			switchAllyButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH), TownyPermission.PermLevel.ALLY, TownyPermission.ActionType.SWITCH);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_ALLY_SWITCH).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ALLY_SWITCH)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_ALLY)
								.modelData(Integer.valueOf(Settings.PLOT_ALLY_SWITCH_CMD))
								.color(townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_ALLY2)
								.lore("" + (townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ALLY_SWITCH)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_ALLY)
								.modelData(Integer.valueOf(Settings.PLOT_ALLY_SWITCH_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_ALLY2)
								.lore("" + (townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};
			switchOutsiderButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH), TownyPermission.PermLevel.OUTSIDER, TownyPermission.ActionType.SWITCH);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_OUTSIDER_SWITCH).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_OUTSIDER_SWITCH)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER)
								.modelData(Integer.valueOf(Settings.PLOT_OUTSIDER_SWITCH_CMD))
								.color(townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH) ? Settings.POSITIVE_BUTTON_COLOR : Settings.NEGATIVE_BUTTON_COLOR)
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER2)
								.lore("" + (townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_OUTSIDER_SWITCH)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER)
								.modelData(Integer.valueOf(Settings.PLOT_OUTSIDER_SWITCH_CMD))
								.lore("")
								.lore(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER2)
								.lore("" + (townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG), "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).make();

					}
				}
			};

			//----------------------------------------------------------------------------------------------------------

			resetButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.ALL_PERMS, false);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_RESET_ALL).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_RESET_ALL)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.RESET)
								.modelData(Integer.valueOf(Settings.PLOT_RESET_ALL_CMD))
								.color(Settings.NEGATIVE_BUTTON_COLOR)
								.lore((List<String>) Localization.PlotMenu.PlayerPermissionsMenu.RESET_LORE).make();
					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_RESET_ALL)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.RESET)
								.modelData(Integer.valueOf(Settings.PLOT_RESET_ALL_CMD))
								.lore((List<String>) Localization.PlotMenu.PlayerPermissionsMenu.RESET_LORE).make();
					}

				}
			};
			allOnButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.ALL_PERMS, true);
					restartMenu();
					townBlock.setChanged(true);
					TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
					Bukkit.getServer().getPluginManager().callEvent(event);
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					TownyAPI.getInstance().getDataSource().saveTown(town);
				}

				@Override
				public ItemStack getItem() {
					if (Material.getMaterial(Settings.PLOT_ALL_ON).equals(Material.LEATHER_BOOTS)) {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ALL_ON)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.ON)
								.modelData(Integer.valueOf(Settings.PLOT_ALL_ON_CMD))
								.color(Settings.POSITIVE_BUTTON_COLOR)
								.lore((List<String>) Localization.PlotMenu.PlayerPermissionsMenu.ON_LORE).make();
					} else {
						return ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ALL_ON)))
								.name(Localization.PlotMenu.PlayerPermissionsMenu.ON)
								.modelData(Integer.valueOf(Settings.PLOT_ALL_ON_CMD))
								.lore((List<String>) Localization.PlotMenu.PlayerPermissionsMenu.ON_LORE).make();
					}

				}
			};
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (!Settings.USE_FIXED_INVENTORY_SIZE) {
				if (slot == 9 + 1) {
					if (Material.getMaterial(Settings.PLOT_BUILD).equals(Material.LEATHER_BOOTS)) {
						return BUILD_BUTTON_COLORED;
					} else {
						return BUILD_BUTTON;
					}
				}

				if (slot == 9 * 2 + 1) {
					if (Material.getMaterial(Settings.PLOT_BREAK).equals(Material.LEATHER_BOOTS)) {
						return BREAK_BUTTON_COLORED;
					} else {
						return BREAK_BUTTON;
					}
				}

				if (slot == 9 * 3 + 1) {
					if (Material.getMaterial(Settings.PLOT_ITEM_USE).equals(Material.LEATHER_BOOTS)) {
						return ITEM_USE_BUTTON_COLORED;
					} else {
						return ITEM_USE_BUTTON;
					}
				}

				if (slot == 9 * 4 + 1) {
					if (Material.getMaterial(Settings.PLOT_SWITCH).equals(Material.LEATHER_BOOTS)) {
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

	public class PlotAdministrationMenu extends Menu {

		public String[] getInfo() {
			return Localization.PlotMenu.PlotAdminMenu.INFO;
		}

		private final Button plotForSaleButton;
		private final Button plotNotForSaleButton;
		private final Button plotSetTypeButton;
		private final Button plotEvictButton;

		private final ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_PLOT_ADMIN_MENU)), "").make();

		protected PlotAdministrationMenu(TownBlock townBlock) {
			super(PlotMenu.this);
			setTitle(Localization.PlotMenu.PlotAdminMenu.MENU_TITLE);
			Button.setInfoButtonTitle(Localization.MENU_INFORMATION);

			if (Settings.USE_FIXED_INVENTORY_SIZE) {
				setSize(9 * 6);
			} else {
				setSize(9);
			}

			if (Material.getMaterial(Settings.PLOT_ADMIN_FOR_SALE).equals(Material.LEATHER_BOOTS)) {
				plotForSaleButton = new ButtonConversation(new PlotForSalePrompt(townBlock),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ADMIN_FOR_SALE)))
								.name(Localization.PlotMenu.PlotAdminMenu.FOR_SALE)
								.color(Settings.NEUTRAL_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.PLOT_ADMIN_FOR_SALE_CMD))
								.lore(Localization.PlotMenu.PlotAdminMenu.FOR_SALE_LORE));
			} else {
				plotForSaleButton = new ButtonConversation(new PlotForSalePrompt(townBlock),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ADMIN_FOR_SALE)))
								.name(Localization.PlotMenu.PlotAdminMenu.FOR_SALE)
								.modelData(Integer.valueOf(Settings.PLOT_ADMIN_FOR_SALE_CMD))
								.lore(Localization.PlotMenu.PlotAdminMenu.FOR_SALE_LORE));
			}


			if (Material.getMaterial(Settings.PLOT_ADMIN_NOT_FOR_SALE).equals(Material.LEATHER_BOOTS)) {
				plotNotForSaleButton = new ButtonConversation(new PlotNotForSalePrompt(townBlock),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ADMIN_NOT_FOR_SALE)))
								.name(Localization.PlotMenu.PlotAdminMenu.NOT_FOR_SALE)
								.color(Settings.NEUTRAL_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.PLOT_ADMIN_NOT_FOR_SALE_CMD))
								.lore(Localization.PlotMenu.PlotAdminMenu.NOT_FOR_SALE_LORE));
			} else {
				plotNotForSaleButton = new ButtonConversation(new PlotNotForSalePrompt(townBlock),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ADMIN_NOT_FOR_SALE)))
								.name(Localization.PlotMenu.PlotAdminMenu.NOT_FOR_SALE)
								.modelData(Integer.valueOf(Settings.PLOT_ADMIN_NOT_FOR_SALE_CMD))
								.lore(Localization.PlotMenu.PlotAdminMenu.NOT_FOR_SALE_LORE));
			}


			if (Material.getMaterial(Settings.PLOT_ADMIN_SET_PLOT_TYPE).equals(Material.LEATHER_BOOTS)) {
				plotSetTypeButton = new ButtonConversation(new PlotSetTypePrompt(townBlock),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ADMIN_SET_PLOT_TYPE)))
								.name(Localization.PlotMenu.PlotAdminMenu.SET_TYPE)
								.color(Settings.NEUTRAL_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.PLOT_ADMIN_SET_PLOT_TYPE_CMD))
								.lore(Localization.PlotMenu.PlotAdminMenu.SET_TYPE_LORE));
			} else {
				plotSetTypeButton = new ButtonConversation(new PlotSetTypePrompt(townBlock),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ADMIN_SET_PLOT_TYPE)))
								.name(Localization.PlotMenu.PlotAdminMenu.SET_TYPE)
								.modelData(Integer.valueOf(Settings.PLOT_ADMIN_SET_PLOT_TYPE_CMD))
								.lore(Localization.PlotMenu.PlotAdminMenu.SET_TYPE_LORE));
			}


			if (Material.getMaterial(Settings.PLOT_ADMIN_EVICT_RES).equals(Material.LEATHER_BOOTS)) {
				plotEvictButton = new ButtonConversation(new PlotEvictPrompt(townBlock),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ADMIN_EVICT_RES)))
								.name(Localization.PlotMenu.PlotAdminMenu.EVICT)
								.color(Settings.NEGATIVE_BUTTON_COLOR)
								.modelData(Integer.valueOf(Settings.PLOT_ADMIN_EVICT_RES_CMD))
								.lore(Localization.PlotMenu.PlotAdminMenu.EVICT_LORE));
			} else {
				plotEvictButton = new ButtonConversation(new PlotEvictPrompt(townBlock),
						ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.PLOT_ADMIN_EVICT_RES)))
								.name(Localization.PlotMenu.PlotAdminMenu.EVICT)
								.modelData(Integer.valueOf(Settings.PLOT_ADMIN_EVICT_RES_CMD))
								.lore(Localization.PlotMenu.PlotAdminMenu.EVICT_LORE));
			}

		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (!Settings.USE_FIXED_INVENTORY_SIZE) {
				if (slot == 0)
					return plotForSaleButton.getItem();
				if (slot == 2)
					return plotNotForSaleButton.getItem();
				if (slot == 4)
					return plotSetTypeButton.getItem();
				if (slot == 6)
					return plotEvictButton.getItem();
			} else {
				if (slot == 10)
					return plotForSaleButton.getItem();
				if (slot == 12)
					return plotNotForSaleButton.getItem();
				if (slot == 14)
					return plotSetTypeButton.getItem();
				if (slot == 16)
					return plotEvictButton.getItem();
			}


			return DUMMY_BUTTON;
		}
	}
}
