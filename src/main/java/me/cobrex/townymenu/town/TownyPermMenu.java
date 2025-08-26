package me.cobrex.townymenu.town;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.object.TownyPermissionChange;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.MenuHandler;
import me.cobrex.townymenu.utils.MenuItemBuilder;
import me.cobrex.townymenu.utils.MenuManager;
import me.cobrex.townymenu.utils.MessageFormatter;
import org.bukkit.entity.Player;

import java.util.List;

public class TownyPermMenu extends MenuHandler {

	private final Player player;
	private final Resident resident;
	private final Town town;
	private final TownyPermission permData;

	public TownyPermMenu(Player player, Resident resident) {
		super(
				player,
				MessageFormatter.format(Localization.TownMenu.PlayerPermissionsMenu.MENU_TITLE, player),
				getInventorySize(ConfigNodes.TOWN_PERMISSIONS_MENU_SIZE)
		);
		this.player = player;
		this.resident = resident;
		this.town = resident.getTownOrNull();
		this.permData = town.getPermissions();

		setupMenu();
	}

	private void setupMenu() {
		if (town == null) return;

		MenuItemBuilder.of("build_info")
				.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD)
				.lore(Localization.TownMenu.PlayerPermissionsMenu.BUILD_LORE)
				.onClick(click -> {
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("break_info")
				.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK)
				.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_LORE)
				.onClick(click -> {
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("item_use_info")
				.name(Localization.TownMenu.PlayerPermissionsMenu.USE)
				.lore(Localization.TownMenu.PlayerPermissionsMenu.USE_LORE)
				.onClick(click -> {
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("switch_info")
				.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH)
				.lore(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_LORE)
				.onClick(click -> {
				})
				.buildAndSet(player,this);

		//===========================================================================

		MenuItemBuilder.of("resident_build_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD_RES)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_RES2)
				.lore(List.of(
						town.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD),
							TownyPermission.PermLevel.RESIDENT,
							TownyPermission.ActionType.BUILD);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
//					MenuManager.switchMenu(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("ally_build_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.BUILD_ALLY2)
				.lore(List.of(
						town.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD),
							TownyPermission.PermLevel.ALLY,
							TownyPermission.ActionType.BUILD);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("nation_build_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.BUILD_NATION2)
				.lore(List.of(
						town.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD),
							TownyPermission.PermLevel.NATION,
							TownyPermission.ActionType.BUILD);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("outsider_build_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.BUILD)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.BUILD_OUTSIDER2)
				.lore(List.of(
						town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD),
							TownyPermission.PermLevel.OUTSIDER,
							TownyPermission.ActionType.BUILD);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		//=======================================================================================================

		MenuItemBuilder.of("resident_break_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK_RES)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_RES2)
				.lore(List.of(
						town.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY),
							TownyPermission.PermLevel.RESIDENT,
							TownyPermission.ActionType.DESTROY);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("nation_break_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK_NATION)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_NATION2)
				.lore(List.of(
						town.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY),
							TownyPermission.PermLevel.NATION,
							TownyPermission.ActionType.DESTROY);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("ally_break_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK_ALLY)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_ALLY2)
				.lore(List.of(
						town.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY),
							TownyPermission.PermLevel.ALLY,
							TownyPermission.ActionType.DESTROY);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("outsider_break_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.BREAK_OUTSIDER)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.BREAK_OUTSIDER2)
				.lore(List.of(
						town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY),
							TownyPermission.PermLevel.OUTSIDER,
							TownyPermission.ActionType.DESTROY);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		//===============================================================================================

		MenuItemBuilder.of("resident_item_use_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.USE_RES)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.USE_RES2)
				.lore(List.of(
						town.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE),
							TownyPermission.PermLevel.RESIDENT,
							TownyPermission.ActionType.ITEM_USE
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("nation_item_use_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.USE_NATION)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.USE_NATION2)
				.lore(List.of(
						town.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE),
							TownyPermission.PermLevel.NATION,
							TownyPermission.ActionType.ITEM_USE
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("ally_item_use_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.USE_ALLY)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.USE_ALLY2)
				.lore(List.of(
						town.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE),
							TownyPermission.PermLevel.ALLY,
							TownyPermission.ActionType.ITEM_USE
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("outsider_item_use_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.USE_OUTSIDER)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.USE_OUTSIDER2)
				.lore(List.of(
						town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE),
							TownyPermission.PermLevel.OUTSIDER,
							TownyPermission.ActionType.ITEM_USE
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		//================================================================================================

		MenuItemBuilder.of("resident_switch_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_RES)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_RES2)
				.lore(List.of(
						town.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH),
							TownyPermission.PermLevel.RESIDENT,
							TownyPermission.ActionType.SWITCH
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("nation_switch_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_NATION)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_NATION2)
				.lore(List.of(
						town.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH),
							TownyPermission.PermLevel.NATION,
							TownyPermission.ActionType.SWITCH
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("ally_switch_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_ALLY)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_ALLY2)
				.lore(List.of(
						town.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH),
							TownyPermission.PermLevel.ALLY,
							TownyPermission.ActionType.SWITCH
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("outsider_switch_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER2)
				.lore(List.of(
						town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					town.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH),
							TownyPermission.PermLevel.OUTSIDER,
							TownyPermission.ActionType.SWITCH
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		//===========================================================================================

		MenuItemBuilder.of("reset_all_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.RESET)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.RESET_LORE)
				.onClick(click -> {
					town.getPermissions().change(TownyPermissionChange.Action.ALL_PERMS, false
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("all_on_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.ON)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.ON_LORE)
				.onClick(click -> {
					town.getPermissions().change(TownyPermissionChange.Action.ALL_PERMS, true);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("reset_to_town_perms_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.RESET_TO_TOWN_PERM)
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.RESET_TO_TOWN_PERM_LORE)
				.onClick(click -> {
					player.closeInventory();
					player.performCommand("t set perm reset");
					MenuManager.refreshInPlace(player, new TownyPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_permissions_menu_info_button")
				.name(Localization.menuInformation(player))
				.lore("")
				.lore(Localization.TownMenu.PlayerPermissionsMenu.INFO_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_permissions_menu_back_button")
				.name(Localization.TownMenu.PlayerPermissionsMenu.BACK_BUTTON)
				.lore(Localization.TownMenu.PlayerPermissionsMenu.BACK_BUTTON_LORE)
				.onClick(click -> {
					try {
						MenuManager.switchMenu(player, new TownMenu(player, town));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player,this);

		fillEmptySlots("filler_town_perms_menu");
	}
}