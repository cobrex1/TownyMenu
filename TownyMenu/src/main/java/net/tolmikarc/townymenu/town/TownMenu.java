package net.tolmikarc.townymenu.town;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.*;
import net.tolmikarc.townymenu.plot.PlotMenu;
import net.tolmikarc.townymenu.town.prompt.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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


public class TownMenu extends Menu {

	// TODO
	//  create plot menu


	// TODO set up github, discord, spigot, website


	private final Button toggleMenuButton;
	private final Button residentListButton;
	private final Button townyPermButton;
	private final Button economyButton;
	private final Button generalSettingsButton;
	private final Button invitePlayerButton;
	private final Button extraInfoButton;
	private final Button plotMenuButton;

	private final static ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.GRAY_STAINED_GLASS_PANE, "").build().make();

	public TownMenu(Town town, Player player) {

		List<Resident> residentList = town.getResidents();

		List<Resident> allOnlineResidents = new ArrayList<>();
		for (Player players : Bukkit.getOnlinePlayers()) {
			try {
				allOnlineResidents.add(TownyAPI.getInstance().getDataSource().getResident(players.getName()));
			} catch (NotRegisteredException e) {
				e.printStackTrace();
			}
		}

		setSize(9 * 5);
		setTitle("&2&lTown Menu");

		toggleMenuButton = new ButtonMenu(new ToggleSettingsMenu(town), CompMaterial.LEVER, "&3&lToggle Settings Menu", "", "Turn on and off", "various town settings.");

		residentListButton = new ButtonMenu(new residentListMenu(residentList), CompMaterial.PLAYER_HEAD, "&6&lResident Menu", "", "View all resident info", "and settings.");

		townyPermButton = new ButtonMenu(new TownyPermMenu(town), CompMaterial.STONE_AXE, "&c&lPermission Menu", "", "Adjust town permissions", "for residents, nation", "allies and outsiders.");

		economyButton = new ButtonMenu(new EconomyManagementMenu(town), CompMaterial.EMERALD_BLOCK, "&a&lEconomy Management Menu", "", "Manage your town's money");

		generalSettingsButton = new ButtonMenu(new GeneralSettingsMenu(town), CompMaterial.ENDER_CHEST, "&2&lOther Settings Menu", "", "Change various", "extra town settings.");

		invitePlayerButton = new ButtonMenu(new InvitePlayerMenu(allOnlineResidents), CompMaterial.BELL, "&d&lInvite Player Menu", "", "Invite an online player", "to your town.");

		extraInfoButton = new ButtonMenu(new ExtraTownInfo(), CompMaterial.ENCHANTED_BOOK, "&e&lExtra Towny Info", "", "Learn how to claim land plus", "some useful commands");

		if (TownyAPI.getInstance().getTownBlock(player.getLocation()) != null && town.hasTownBlock(TownyAPI.getInstance().getTownBlock(player.getLocation())))
			plotMenuButton = new ButtonMenu(new PlotMenu(TownyAPI.getInstance().getTownBlock(player.getLocation())), CompMaterial.WOODEN_SHOVEL, "&9&lPlot Management Menu", "", "Manage the plot at", "your current location.");
		else
			plotMenuButton = Button.makeDummy(ItemCreator.of(CompMaterial.WOODEN_SHOVEL, "&9&lPlot Management Menu", "", "Manage the plot at", "your current location."));
	}

	@Override
	public ItemStack getItemAt(int slot) {

		if (slot == 9 + 2)
			return toggleMenuButton.getItem();
		if (slot == 9 + 4)
			return residentListButton.getItem();
		if (slot == 9 + 6)
			return townyPermButton.getItem();

		if (slot == 9 * 3 + 2)
			return economyButton.getItem();
		if (slot == 9 * 3 + 4)
			return generalSettingsButton.getItem();
		if (slot == 9 * 3 + 6)
			return invitePlayerButton.getItem();

		if (slot == 9 * 4 + 3)
			return extraInfoButton.getItem();
		if (slot == 9 * 4 + 5)
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

			setTitle("&2&lToggle Settings Menu");

			setInfo("Adjust toggleable settings", "for your town!");

			fireToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.setFire(!town.isFire());
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.CAMPFIRE, "&6&lToggle Firespread", "", town.isFire() ? "&eClick to Turn &cOff" : "&eClick to Turn &aOn").build().make();
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

					return ItemCreator.of(CompMaterial.SHULKER_SPAWN_EGG, "&a&lToggle Mob Spawning", "", town.hasMobs() ? "&eClick to Turn &cOff" : "&eClick to Turn &aOn").build().make();

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
					return ItemCreator.of(CompMaterial.TNT, "&c&lToggle Explosions", "", town.isBANG() ? "&eClick to Turn &cOff" : "&eClick to Turn &aOn").build().make();

				}
			};
			pvpToggle = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.setPVP(!town.isPVP());
					TownyAPI.getInstance().getDataSource().saveTown(town);
					restartMenu();
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.GOLDEN_SWORD, "&4&lToggle PVP", "", town.isPVP() ? "&eClick to Turn &cOff" : "&eClick to Turn &aOn").build().make();
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
					return ItemCreator.of(CompMaterial.CHEST, "&9&lToggle Public", "", town.isPublic() ? "&eClick to Turn &cOff" : "&eClick to Turn &aOn").build().make();
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

					return ItemCreator.of(CompMaterial.ACACIA_DOOR, "&3&lToggle Open", "", town.isOpen() ? "&eClick to Turn &cOff" : "&eClick to Turn &aOn").build().make();

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

					return ItemCreator.of(CompMaterial.EMERALD, "&2&lToggle Tax Percent", "", town.isTaxPercentage() ? "&eClick to Turn &cOff" : "&eClick to Turn &eOn").build().make();

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

	public class residentListMenu extends MenuPagged<Resident> {


		protected residentListMenu(Iterable<Resident> pages) {
			super(TownMenu.this, pages);
			setTitle("&2&lResident Menu");
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
			if (item.getName().equals(player.getName())) {
				Common.tell(player, "&cYou cannot select yourself to edit.");
				player.closeInventory();
				return;
			}
			new residentMenu(item).displayTo(player);
		}
	}

	public class residentMenu extends Menu {


		private final Button kickButton;
		private final Button titleButton;
		private final Button rankButton;
		private final Button mayorButton;


		protected residentMenu(Resident resident) {
			super(TownMenu.this);

			setTitle("&2&lResident Menu");


			kickButton = new ButtonConversation(new TownKickPrompt(resident), CompMaterial.REDSTONE_BLOCK, "&c&lKick Player", "", "Click to kick resident", "from your town");

			titleButton = new ButtonConversation(new TownTitlePrompt(resident), CompMaterial.NAME_TAG, "&a&lSet Resident Title", "", "Give a title to", "this resident.");

			rankButton = new ButtonConversation(new TownRankPrompt(resident), CompMaterial.IRON_SWORD, "&3&lSet Resident Rank", "", "Give a town rank", "to a a player");

			mayorButton = new ButtonConversation(new TownGiveMayorPrompt(resident), CompMaterial.GOLDEN_APPLE, "&a&lGive Resident Mayor", "", "Make this resident", "the mayor.", "&4&lIrreversible");
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


		protected TownyPermMenu(Town town) {
			super(TownMenu.this);
			setSize(9 * 6);
			setInfo("Set permissions for", "different groups", "in your town.");
			setTitle("&2&lPermissions Menu");

			buildResidentButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM, !town.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD), TownyPermission.PermLevel.RESIDENT, TownyPermission.ActionType.BUILD);
					restartMenu();
					TownyAPI.getInstance().getDataSource().saveTown(town);

				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.BELL, "&6&lBuild Resident Perm", "", "Can Residents Build: ", town.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, "&6&lBuild Nation Perm", "", "Can Nation Members Build: ", town.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.CARROT, "&6&lBuild Ally Perm", "", "Can Naton Allies Build: ", town.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.BONE, "&6&lBuild Outsider Perm", "", "Can Outsiders Build: ", town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.BELL, "&6&lBreak Resident Perm", "", "Can Residents Break Blocks: ", town.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, "&6&lBreak Nation Perm", "", "Can Nation Members Break Blocks: ", town.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.CARROT, "&6&lBreak Ally Perm", "", "Can Nation Allies Break Blocks: ", town.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.BONE, "&6&lBreak Outsider Perm", "", "Can Outsiders Break Blocks: ", town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.BELL, "&6&lItem Use Resident Perm", "", "Can Residents Use Items: ", town.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, "&6&lItem Use Nation Perm", "", "Can Nation Members Use Items: ", town.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.CARROT, "&6&lItem Use Ally Perm", "", "Can Nation Allies Use Items: ", town.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.BONE, "&6&lItem Use Outsider Perm", "", "Can Outsiders Use Items: ", town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.BELL, "&6&lSwitch Resident Perm", "", "Can Residents Switch: ", town.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.CHAINMAIL_CHESTPLATE, "&6&lSwitch Nation Perm", "", "Can Nation Members Switch: ", town.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.CARROT, "&6&lSwitch Ally Perm", "", "Can Nation Allies Switch: ", town.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.BONE, "&6&lSwitch Outsider Perm", "", "Can Outsiders Switch: ", town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH) ? "&atrue" : "&cfalse", "", "&eClick to change!").build().make();
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
					return ItemCreator.of(CompMaterial.REDSTONE_BLOCK, "&c&lReset All Fields", "", "Turn off all permissions", "for all groups.").build().make();
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

	public class EconomyManagementMenu extends Menu {

		Town town;

		private final ItemStack balanceButton;
		private final Button depositButton;
		private final Button withdrawButton;
		private final Button setTaxButton;


		protected EconomyManagementMenu(Town town) {
			super(TownMenu.this);
			setSize(9 * 2);
			setTitle("&2&lEconomy Menu");

			this.town = town;

			balanceButton = ItemCreator.of(CompMaterial.EMERALD_BLOCK, "&2&lCurrent Town Balance", "", "&a" + town.getAccount().getHoldingFormattedBalance()).build().make();

			depositButton = new ButtonConversation(new TownDepositPrompt(town), CompMaterial.CHEST, "&a&lDeposit Money", "", "Deposit money into", "your town bank.");

			withdrawButton = new ButtonConversation(new TownWithdrawPrompt(town), CompMaterial.ENDER_CHEST, "&c&lWithdraw Money", "", "Withdraw money from", "your town bank.");

			setTaxButton = new ButtonConversation(new TownSetTaxPrompt(town), CompMaterial.ARROW, "&c&lSet Tax Amount", "", town.isTaxPercentage() ? "Set tax percentage" : "Set tax amount");

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

		Town town;

		private final Button setSpawnButton;
		private final Button setHomeBlockButton;
		private final Button townBoardButton;
		private final Button townNameButton;

		protected GeneralSettingsMenu(Town town) {
			super(TownMenu.this);
			setSize(9 * 2);
			setInfo("Adjust other", "extra town settings.");
			setTitle("&2&lGeneral Settings Menu");

			this.town = town;


			setHomeBlockButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					TownBlock townBlock = TownyAPI.getInstance().getTownBlock(player.getLocation());
					try {
						if (townBlock != null && townBlock.getTown().equals(town) && town.getMayor().getName().equals(player.getName())) {
							town.setHomeBlock(townBlock);
							TownyAPI.getInstance().getDataSource().saveTown(town);
							Common.tell(player, "&aSet town block to your location!");
							Common.tell(player, "&aMake sure to also set your town spawn again!");
						} else {
							Common.tell(player, "&cCannot set townblock here.");
						}
						player.closeInventory();

					} catch (TownyException e) {
						Common.tell(player, "&cCannot set townblock here.");
					}


				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.GLOWSTONE, "&3&lSet Home Block", "", "Set the home block", "of your town.").build().make();
				}
			};

			setSpawnButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					TownBlock townBlock = TownyAPI.getInstance().getTownBlock(player.getLocation());
					try {
						if (townBlock.isHomeBlock() && townBlock.getTown().equals(town)) {
							town.setSpawn(player.getLocation());
							Common.tell(player, "&aSet town spawn to current location.");
							player.closeInventory();
							TownyAPI.getInstance().getDataSource().saveTown(town);
						} else {
							Common.tell(player, "&cCannot set town spawn here.");
						}
					} catch (TownyException e) {
						e.printStackTrace();
					}
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.LAPIS_LAZULI, "&b&lSet Town Spawn", "", "Set the location for", "teleporting with", "/t spawn", "&cMust be in the Home Block.").build().make();
				}
			};


			townNameButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {

					if (town.getMayor().getName().equals(player.getName()))
						new TownNamePrompt(town).show(player);
					else
						Common.tell(player, "&cMust be mayor to change town name.");
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.NAME_TAG, "&6&lSet Town Name", "", "Change the name", "of your town.").build().make();
				}
			};

			townBoardButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					if (town.getMayor().getName().equals(player.getName()))
						new TownBoardPrompt(town).show(player);
					else
						Common.tell(player, "&cMust be mayor to change town name.");
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.OAK_SIGN, "&2&lSet Town Board", "", "Set the town board", "of your town.").build().make();
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

		private final ItemStack claimInfo = ItemCreator.of(CompMaterial.GOLDEN_AXE, "&6&lClaiming Info", "", "&a/t claim &7- claim a chunk of land", "", "Claiming requires money in", "your town bank", "and requires you to be in a", "chunk bordering your town.", "Use &aF3+G &7to see chunks and", "&a/towny map &7to see claimed land.").build().make();
		private final ItemStack extraCommands = ItemCreator.of(CompMaterial.BOOKSHELF, "&2&lExtra Commands", "", "&a/t outlaw &7- make a player an outlaw", "&a/t buy &7- chunk purchase info", "&a/t online &7- online town members", "&a/t say &7- broadcast a town message").build().make();

		protected ExtraTownInfo() {
			super(TownMenu.this);
			setSize(9 * 2);
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
