package net.tolmikarc.townymenu.plot;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.object.TownyPermissionChange;
import lombok.SneakyThrows;
import net.tolmikarc.townymenu.plot.prompt.PlotEvictPrompt;
import net.tolmikarc.townymenu.plot.prompt.PlotForSalePrompt;
import net.tolmikarc.townymenu.plot.prompt.PlotNotForSalePrompt;
import net.tolmikarc.townymenu.plot.prompt.PlotSetTypePrompt;
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
import java.util.List;

public class PlotMenu extends Menu {


	private final Button toggleSettingsMenu;
	private final Button permMenuButton;
	private final Button plotAdministrationMenuButton;
	private final Button friendButton;

	public PlotMenu(TownBlock townBlock) {

		setSize(9);
		setTitle("&2Plot Menu");

		List<Resident> onlineResidents = new ArrayList<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			try {
				onlineResidents.add(TownyAPI.getInstance().getDataSource().getResident(player.getName()));
			} catch (NotRegisteredException e) {
				e.printStackTrace();
			}
		}

		toggleSettingsMenu = new ButtonMenu(new PlotToggleSettingsMenu(townBlock), CompMaterial.LEVER, "&3&lToggle Settings Menu", "", "Turn on and off", "various plot settings");

		permMenuButton = new ButtonMenu(new PlotPermMenu(townBlock), CompMaterial.STONE_AXE, "&c&lPermission Menu", "", "Adjust plot permissions", "for friends, nation", "allies and outsiders");

		plotAdministrationMenuButton = new ButtonMenu(new PlotAdministrationMenu(townBlock), CompMaterial.BELL, "&5&lPlot Administration Menu", "", "Advanced plot management", "and settings");

		friendButton = new ButtonMenu(new FriendPlayerMenu(onlineResidents), CompMaterial.PLAYER_HEAD, "&aFriend Menu", "", "Friend management menu");


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
			setTitle("&2Friend Menu");
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

			try {
				if (item.getFriends().contains(TownyAPI.getInstance().getDataSource().getResident(player.getName()))) {
					playerResident.getFriends().remove(item);
					Common.tell(player, "&cRemoved " + item.getName() + " to your friends list!");
				} else {
					playerResident.getFriends().add(item);
					Common.tell(player, "&3Added &b" + item.getName() + " &3to your friends list!");
				}
				TownyAPI.getInstance().getDataSource().saveResidents();
				restartMenu();
			} catch (NotRegisteredException e) {
				e.printStackTrace();
			}

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

			setTitle("&2&lToggle Settings Menu");

			setInfo("Adjust toggleable settings", "for your plot!");

			fireToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().fire = !townBlock.getPermissions().fire;
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.CAMPFIRE, "&6&lToggle Firespread", "", townBlock.getPermissions().fire ? "&eClick to Turn &cOff" : "&eClick to Turn &aOn").build().make();
				}
			};
			mobsToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().mobs = !townBlock.getPermissions().mobs;
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					restartMenu();

				}

				@Override
				public ItemStack getItem() {

					return ItemCreator.of(CompMaterial.SHULKER_SPAWN_EGG, "&a&lToggle Mob Spawning", "", townBlock.getPermissions().mobs ? "&eClick to Turn &cOff" : "&eClick to Turn &aOn").build().make();

				}
			};
			explosionToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {

					townBlock.getPermissions().explosion = !townBlock.getPermissions().explosion;
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.TNT, "&c&lToggle Explosions", "", townBlock.getPermissions().explosion ? "&eClick to Turn &cOff" : "&eClick to Turn &aOn").build().make();

				}
			};
			pvpToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().pvp = !townBlock.getPermissions().pvp;
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.GOLDEN_SWORD, "&4&lToggle PVP", "", townBlock.getPermissions().pvp ? "&eClick to Turn &cOff" : "&eClick to Turn &aOn").build().make();
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

		private final ItemStack BUILD_BUTTON = ItemCreator.of(CompMaterial.BRICKS, "&3&lBuild", "", "Who has permission", "to build in", "your town.").build().make();
		private final ItemStack BREAK_BUTTON = ItemCreator.of(CompMaterial.GOLDEN_PICKAXE, "&c&lBreak", "", "Who has permission", "to break blocks in", "your town.").build().make();
		private final ItemStack ITEM_USE_BUTTON = ItemCreator.of(CompMaterial.FLINT_AND_STEEL, "&6&lItem Use", "", "Who has permission", "to use items in", "your town.", "", "E.g. take water from cauldron").build().make();
		private final ItemStack SWITCH_BUTTON = ItemCreator.of(CompMaterial.LEVER, "&2&lSwitch", "", "Who has permission", "to switch in", "your town.", "", "E.g. use a lever or door").build().make();

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
			setInfo("Set permissions for", "different groups", "in your plot.");
			setTitle("&2&lPermissions Menu");

			buildResidentButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD), TownyPermission.PermLevel.RESIDENT, TownyPermission.ActionType.BUILD);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.BELL, "&6&lBuild Resident Perm", "", "Can Residents Build: ", townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
				}
			};
			buildNationButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD), TownyPermission.PermLevel.NATION, TownyPermission.ActionType.BUILD);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, "&6&lBuild Town/Nation Perm", "", "Can Town/Nation Members Build: ", townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD) ? "&atrue" : "&cfalse", "", "&eClick to change!", "", "&cNote: If you own this plot", "&cthis permission has to do", "&cwith town members not nation.").build().make();
				}
			};
			buildAllyButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD), TownyPermission.PermLevel.ALLY, TownyPermission.ActionType.BUILD);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.CARROT, "&6&lBuild Ally Perm", "", "Can Naton Allies Build: ", townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
				}
			};
			buildOutsiderButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD), TownyPermission.PermLevel.OUTSIDER, TownyPermission.ActionType.BUILD);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.BONE, "&6&lBuild Outsider Perm", "", "Can Outsiders Build: ", townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
				}
			};

			// ------------------------------------------------------------------------------------
			breakResidentButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY), TownyPermission.PermLevel.RESIDENT, TownyPermission.ActionType.DESTROY);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.BELL, "&6&lBreak Resident Perm", "", "Can Residents Break Blocks: ", townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
				}
			};
			breakNationButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY), TownyPermission.PermLevel.NATION, TownyPermission.ActionType.DESTROY);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, "&6&lBreak Town/Nation Perm", "", "Can Town/Nation Members Break Blocks: ", townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY) ? "&atrue" : "&cfalse", "", "&eClick to change!", "", "&cNote: If you own this plot", "&cthis permission has to do", "&cwith town members not nation.").build().make();
				}
			};
			breakAllyButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY), TownyPermission.PermLevel.ALLY, TownyPermission.ActionType.DESTROY);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.CARROT, "&6&lBreak Ally Perm", "", "Can Nation Allies Break Blocks: ", townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
				}
			};
			breakOutsiderButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY), TownyPermission.PermLevel.OUTSIDER, TownyPermission.ActionType.DESTROY);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.BONE, "&6&lBreak Outsider Perm", "", "Can Outsiders Break Blocks: ", townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
				}
			};

			//------------------------------------------------------------------------------------------------------------------------------------------------------
			itemUseResidentButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE), TownyPermission.PermLevel.RESIDENT, TownyPermission.ActionType.ITEM_USE);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.BELL, "&6&lItem Use Resident Perm", "", "Can Residents Use Items: ", townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
				}
			};
			itemUseNationButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE), TownyPermission.PermLevel.NATION, TownyPermission.ActionType.ITEM_USE);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, "&6&lItem Use Town/Nation Perm", "", "Can Town/Nation Members Use Items: ", townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE) ? "&atrue" : "&cfalse", "", "&eClick to change!", "", "&cNote: If you own this plot", "&cthis permission has to do", "&cwith town members not nation.").build().make();
				}
			};
			itemUseAllyButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE), TownyPermission.PermLevel.ALLY, TownyPermission.ActionType.ITEM_USE);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.CARROT, "&6&lItem Use Ally Perm", "", "Can Nation Allies Use Items: ", townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
				}
			};
			itemUseOutsiderButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE), TownyPermission.PermLevel.OUTSIDER, TownyPermission.ActionType.ITEM_USE);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.BONE, "&6&lItem Use Outsider Perm", "", "Can Outsiders Use Items: ", townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
				}
			};

			//---------------------------------------------------------------------------------------------------------------------

			switchResidentButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH), TownyPermission.PermLevel.RESIDENT, TownyPermission.ActionType.SWITCH);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.BELL, "&6&lSwitch Resident Perm", "", "Can Residents Switch: ", townBlock.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
				}
			};
			switchNationButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH), TownyPermission.PermLevel.NATION, TownyPermission.ActionType.SWITCH);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, "&6&lSwitch Town/Nation Perm", "", "Can Town/Nation Members Switch: ", townBlock.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH) ? "&atrue" : "&cfalse", "", "&eClick to change!", "", "&cNote: If you own this plot", "&cthis permission has to do", "&cwith town members not nation.").build().make();
				}
			};
			switchAllyButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH), TownyPermission.PermLevel.ALLY, TownyPermission.ActionType.SWITCH);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.CARROT, "&6&lSwitch Ally Perm", "", "Can Nation Allies Switch: ", townBlock.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
				}
			};
			switchOutsiderButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH), TownyPermission.PermLevel.OUTSIDER, TownyPermission.ActionType.SWITCH);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.BONE, "&6&lSwitch Outsider Perm", "", "Can Outsiders Switch: ", townBlock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
				}
			};

			//----------------------------------------------------------------------------------------------------------

			resetButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.ALL_PERMS, false);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.REDSTONE_BLOCK, "&c&lReset All Fields", "", "Turn off all permissions", "for all groups.").build().make();
				}
			};
			allOnButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					townBlock.getPermissions().change(TownyPermissionChange.Action.ALL_PERMS, true);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.EMERALD_BLOCK, "&a&lTurn On All Fields", "", "Turn on all permissions", "for all groups.").build().make();
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

			plotForSaleButton = new ButtonConversation(new PlotForSalePrompt(townBlock), CompMaterial.EMERALD_BLOCK, "&a&lSet Plot For Sale", "", "Put the current plot", "up for sale with", "a specified price");

			plotNotForSaleButton = new ButtonConversation(new PlotNotForSalePrompt(townBlock), CompMaterial.REDSTONE_BLOCK, "&c&lSet Plot Not For Sale", "", "Make a plot", "not purchasable");

			plotSetTypeButton = new ButtonConversation(new PlotSetTypePrompt(townBlock), CompMaterial.DARK_OAK_DOOR, "&3&lSet Plot Type", "", "Determine what type of", "plot this is.");

			plotEvictButton = new ButtonConversation(new PlotEvictPrompt(townBlock), CompMaterial.DIAMOND_AXE, "&4&lEvict Plot Owner", "", "Evict the resident", "of this plot");


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
