package net.tolmikarc.townymenu.plot;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.event.TownBlockSettingsChangedEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.*;
import lombok.SneakyThrows;
import net.tolmikarc.townymenu.plot.prompt.PlotEvictPrompt;
import net.tolmikarc.townymenu.plot.prompt.PlotForSalePrompt;
import net.tolmikarc.townymenu.plot.prompt.PlotNotForSalePrompt;
import net.tolmikarc.townymenu.plot.prompt.PlotSetTypePrompt;
import net.tolmikarc.townymenu.settings.Localization;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonConversation;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlotMenu extends Menu {


	private final Button toggleSettingsMenu;
	private final Button permMenuButton;
	private final Button plotAdministrationMenuButton;
	private final Button friendButton;
	private final Town town;

	public PlotMenu(TownBlock townBlock) throws NotRegisteredException {

		setSize(9);
		setTitle(Localization.PlotMenu.MAIN_MENU_TITLE);

		Set<Resident> onlineResidents = new HashSet<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			try {
				onlineResidents.add(TownyAPI.getInstance().getDataSource().getResident(player.getName()));
			} catch (NotRegisteredException e) {
				e.printStackTrace();
			}
		}

		toggleSettingsMenu = new ButtonMenu(new PlotToggleSettingsMenu(townBlock), CompMaterial.LEVER, Localization.PlotMenu.TOGGLE_SETTINGS_MENU_BUTTON, Localization.PlotMenu.TOGGLE_SETTINGS_MENU_BUTTON_LORE);

		permMenuButton = new ButtonMenu(new PlotPermMenu(townBlock), CompMaterial.STONE_AXE, Localization.PlotMenu.PERMISSIONS_MENU_BUTTON, Localization.PlotMenu.PERMISSIONS_MENU_BUTTON_LORE);

		plotAdministrationMenuButton = new ButtonMenu(new PlotAdministrationMenu(townBlock), CompMaterial.BELL, Localization.PlotMenu.PLOT_ADMIN_MENU_BUTTON, Localization.PlotMenu.PLOT_ADMIN_MENU_BUTTON_LORE);

		friendButton = new ButtonMenu(new FriendPlayerMenu(onlineResidents), CompMaterial.PLAYER_HEAD, Localization.PlotMenu.FRIEND_MENU_BUTTON, Localization.PlotMenu.FRIEND_MENU_BUTTON_LORE);


		town = townBlock.getTown();
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == 1)
			return toggleSettingsMenu.getItem();

		if (slot == 3)
			return permMenuButton.getItem();

		if (slot == 5)
			return plotAdministrationMenuButton.getItem();

		if (slot == 7)
			return friendButton.getItem();

		return null;
	}

	public class FriendPlayerMenu extends MenuPagged<Resident> {


		protected FriendPlayerMenu(Iterable<Resident> pages) {
			super(PlotMenu.this, pages);
			setTitle(Localization.PlotMenu.FriendMenu.MENU_TITLE);
		}

		@Override
		protected ItemStack convertToItemStack(Resident item) {
			if (item.getUUID() == null)
				return null;
			ItemStack itemSkull = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta skull = (SkullMeta) itemSkull.getItemMeta();
			skull.setDisplayName(item.getName());
			Player player = Bukkit.getPlayer(item.getUUID());
			skull.setOwningPlayer(player);
			List<String> lore = new ArrayList<>();
			lore.add("");
			try {
				lore.add(item.getFriends().contains(TownyAPI.getInstance().getDataSource().getResident(getViewer().getName())) ? ChatColor.RED + "Remove Friend" : ChatColor.YELLOW + "Add Friend");
			} catch (NotRegisteredException e) {
				e.printStackTrace();
			}
			skull.setLore(lore);
			itemSkull.setItemMeta(skull);
			return itemSkull;
		}

		@SneakyThrows
		@Override
		protected void onPageClick(Player player, Resident item, ClickType click) {

			Resident playerResident = TownyAPI.getInstance().getDataSource().getResident(player.getName());

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


		public PlotToggleSettingsMenu(TownBlock townBlock) {
			super(PlotMenu.this);

			setSize(9 * 2);

			setTitle(Localization.PlotMenu.ToggleMenu.MENU_TITLE);

			setInfo(Localization.PlotMenu.ToggleMenu.INFO);
			Button.setInfoButtonTitle(Localization.MENU_INFORMATION);

			fireToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
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
					return ItemCreator.of(CompMaterial.CAMPFIRE, Localization.PlotMenu.ToggleMenu.FIRE, "", townBlock.getPermissions().fire ? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF : Localization.PlotMenu.ToggleMenu.TOGGLE_ON).build().make();
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

					return ItemCreator.of(CompMaterial.SHULKER_SPAWN_EGG, Localization.PlotMenu.ToggleMenu.MOBS, "", townBlock.getPermissions().mobs ? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF : Localization.PlotMenu.ToggleMenu.TOGGLE_ON).build().make();

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
					return ItemCreator.of(CompMaterial.TNT, Localization.PlotMenu.ToggleMenu.EXPLODE, "", townBlock.getPermissions().explosion ? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF : Localization.PlotMenu.ToggleMenu.TOGGLE_ON).build().make();

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
					return ItemCreator.of(CompMaterial.GOLDEN_SWORD, Localization.PlotMenu.ToggleMenu.PVP, "", townBlock.getPermissions().pvp ? Localization.PlotMenu.ToggleMenu.TOGGLE_OFF : Localization.PlotMenu.ToggleMenu.TOGGLE_ON).build().make();
				}
			};

		}

		@Override
		public ItemStack getItemAt(int slot) {

			if (slot == 1)
				return fireToggle.getItem();
			if (slot == 3)
				return mobsToggle.getItem();
			if (slot == 5)
				return explosionToggle.getItem();
			if (slot == 7)
				return pvpToggle.getItem();

			return null;
		}
	}

	public class PlotPermMenu extends Menu {

		private final ItemStack BUILD_BUTTON = ItemCreator.of(CompMaterial.BRICKS, Localization.PlotMenu.PlayerPermissionsMenu.BUILD, Localization.PlotMenu.PlayerPermissionsMenu.BUILD_LORE).build().make();
		private final ItemStack BREAK_BUTTON = ItemCreator.of(CompMaterial.GOLDEN_PICKAXE, Localization.PlotMenu.PlayerPermissionsMenu.BREAK, Localization.PlotMenu.PlayerPermissionsMenu.BREAK_LORE).build().make();
		private final ItemStack ITEM_USE_BUTTON = ItemCreator.of(CompMaterial.FLINT_AND_STEEL, Localization.PlotMenu.PlayerPermissionsMenu.USE, Localization.PlotMenu.PlayerPermissionsMenu.USE_LORE).build().make();
		private final ItemStack SWITCH_BUTTON = ItemCreator.of(CompMaterial.LEVER, Localization.PlotMenu.PlayerPermissionsMenu.SWITCH, Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_LORE).build().make();

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


		protected PlotPermMenu(TownBlock townBlock) {
			super(PlotMenu.this);
			setSize(9 * 6);
			setInfo(Localization.PlotMenu.PlayerPermissionsMenu.INFO);
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
					return ItemCreator.of(CompMaterial.BELL, Localization.PlotMenu.PlayerPermissionsMenu.BUILD_RES, "", Localization.PlotMenu.PlayerPermissionsMenu.BUILD_RES2, townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, Localization.PlotMenu.PlayerPermissionsMenu.BUILD_NATION, "", Localization.PlotMenu.PlayerPermissionsMenu.BUILD_NATION2, townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CARROT, Localization.PlotMenu.PlayerPermissionsMenu.BUILD_ALLY, "", Localization.PlotMenu.PlayerPermissionsMenu.BUILD_ALLY2, townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BONE, Localization.PlotMenu.PlayerPermissionsMenu.BUILD_OUTSIDER, "", Localization.PlotMenu.PlayerPermissionsMenu.BUILD_OUTSIDER2, townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BELL, Localization.PlotMenu.PlayerPermissionsMenu.BREAK_RES, "", Localization.PlotMenu.PlayerPermissionsMenu.BREAK_RES2, townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, Localization.PlotMenu.PlayerPermissionsMenu.BREAK_NATION, "", Localization.PlotMenu.PlayerPermissionsMenu.BREAK_NATION2, townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CARROT, Localization.PlotMenu.PlayerPermissionsMenu.BREAK_ALLY, "", Localization.PlotMenu.PlayerPermissionsMenu.BREAK_ALLY2, townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BONE, Localization.PlotMenu.PlayerPermissionsMenu.BREAK_OUTSIDER, "", Localization.PlotMenu.PlayerPermissionsMenu.BREAK_OUTSIDER2, townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BELL, Localization.PlotMenu.PlayerPermissionsMenu.USE_RES, "", Localization.PlotMenu.PlayerPermissionsMenu.USE_RES2, townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, Localization.PlotMenu.PlayerPermissionsMenu.USE_NATION, "", Localization.PlotMenu.PlayerPermissionsMenu.USE_NATION2, townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CARROT, Localization.PlotMenu.PlayerPermissionsMenu.USE_ALLY, "", Localization.PlotMenu.PlayerPermissionsMenu.USE_ALLY2, townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BONE, Localization.PlotMenu.PlayerPermissionsMenu.USE_OUTSIDER, "", Localization.PlotMenu.PlayerPermissionsMenu.USE_OUTSIDER2, townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BELL, Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_RES, "", Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_RES2, townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_NATION, "", Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_NATION2, townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.CARROT, Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_ALLY, "", Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_ALLY2, townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.BONE, Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER, "", Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER2, townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH) ? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG : Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG, "", Localization.PlotMenu.PlayerPermissionsMenu.CHANGE).build().make();
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
					return ItemCreator.of(CompMaterial.REDSTONE_BLOCK, Localization.PlotMenu.PlayerPermissionsMenu.RESET, Localization.PlotMenu.PlayerPermissionsMenu.RESET_LORE).build().make();
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
					return ItemCreator.of(CompMaterial.EMERALD_BLOCK, Localization.PlotMenu.PlayerPermissionsMenu.ON, Localization.PlotMenu.PlayerPermissionsMenu.ON_LORE).build().make();
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

	public class PlotAdministrationMenu extends Menu {

		private final Button plotForSaleButton;
		private final Button plotNotForSaleButton;
		private final Button plotSetTypeButton;
		private final Button plotEvictButton;

		protected PlotAdministrationMenu(TownBlock townBlock) {
			super(PlotMenu.this);
			setSize(9);
			setTitle(Localization.PlotMenu.PlotAdminMenu.MENU_TITLE);

			plotForSaleButton = new ButtonConversation(new PlotForSalePrompt(townBlock), CompMaterial.EMERALD_BLOCK, Localization.PlotMenu.PlotAdminMenu.FOR_SALE, Localization.PlotMenu.PlotAdminMenu.FOR_SALE_LORE);

			plotNotForSaleButton = new ButtonConversation(new PlotNotForSalePrompt(townBlock), CompMaterial.REDSTONE_BLOCK, Localization.PlotMenu.PlotAdminMenu.NOT_FOR_SALE, Localization.PlotMenu.PlotAdminMenu.NOT_FOR_SALE_LORE);

			plotSetTypeButton = new ButtonConversation(new PlotSetTypePrompt(townBlock), CompMaterial.DARK_OAK_DOOR, Localization.PlotMenu.PlotAdminMenu.SET_TYPE, Localization.PlotMenu.PlotAdminMenu.SET_TYPE_LORE);

			plotEvictButton = new ButtonConversation(new PlotEvictPrompt(townBlock), CompMaterial.DIAMOND_AXE, Localization.PlotMenu.PlotAdminMenu.EVICT, Localization.PlotMenu.PlotAdminMenu.EVICT_LORE);


		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (slot == 0)
				return plotForSaleButton.getItem();
			if (slot == 2)
				return plotNotForSaleButton.getItem();
			if (slot == 4)
				return plotSetTypeButton.getItem();
			if (slot == 6)
				return plotEvictButton.getItem();


			return null;
		}
	}


}
