package me.cobrex.townymenu.plot;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.*;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.config.ConfigUtil;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.MenuHandler;
import me.cobrex.townymenu.utils.MenuItemBuilder;
import me.cobrex.townymenu.utils.MenuManager;
import me.cobrex.townymenu.utils.MessageFormatter;
import org.bukkit.entity.Player;

import java.util.List;

public class PlotPermMenu extends MenuHandler {

	private final Player player;
	private final Resident resident;
	private final Town town;
	private final TownyPermission perm;
	private final TownBlock townblock;

	public PlotPermMenu(Player player, Resident resident) {
		super(
				player,
				MessageFormatter.format(Localization.TownMenu.PlayerPermissionsMenu.MENU_TITLE, player),
				ConfigUtil.getInt(ConfigNodes.TOWN_PERMISSIONS_MENU_SIZE)
		);
		this.player = player;
		this.resident = resident;
		this.town = resident.getTownOrNull();
		this.perm = town == null ? null : town.getPermissions();
		this.townblock = TownyAPI.getInstance().getTownBlock(player);

		setupMenu();
	}

	private void setupMenu() {
		if (town == null || perm == null) return;

		// === Info Buttons ==================================

		MenuItemBuilder.of("plot_build_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD)
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_break_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BREAK)
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_item_use_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.USE)
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.USE_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_switch_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH)
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		//===========================================================================

		//BUILD PERMS

		MenuItemBuilder.of("plot_resident_build_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_RES)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_RES2)
				.lore(List.of(
						townblock.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.PlotMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD),
							TownyPermission.PermLevel.RESIDENT,
							TownyPermission.ActionType.BUILD);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);


		MenuItemBuilder.of("ally_build_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_ALLY2)
				.lore(List.of(
						townblock.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD)
								? Localization.TownMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.TownMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.PlotMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD),
							TownyPermission.PermLevel.ALLY,
							TownyPermission.ActionType.BUILD);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("nation_build_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_NATION2)
				.lore(List.of(
						townblock.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getNationPerm(TownyPermission.ActionType.BUILD),
							TownyPermission.PermLevel.NATION,
							TownyPermission.ActionType.BUILD);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("outsider_build_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BUILD)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.BUILD_OUTSIDER2)
				.lore(List.of(
						townblock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.PlotMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD),
							TownyPermission.PermLevel.OUTSIDER,
							TownyPermission.ActionType.BUILD);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		//=======================================================================================================

		MenuItemBuilder.of("resident_break_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_RES)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_RES2)
				.lore(List.of(
						townblock.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY),
							TownyPermission.PermLevel.RESIDENT,
							TownyPermission.ActionType.DESTROY);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("nation_break_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_NATION)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_NATION2)
				.lore(List.of(
						townblock.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getNationPerm(TownyPermission.ActionType.DESTROY),
							TownyPermission.PermLevel.NATION,
							TownyPermission.ActionType.DESTROY);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("ally_break_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_ALLY)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_ALLY2)
				.lore(List.of(
						townblock.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.PlotMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY),
							TownyPermission.PermLevel.ALLY,
							TownyPermission.ActionType.DESTROY);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("outsider_break_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_OUTSIDER)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.BREAK_OUTSIDER2)
				.lore(List.of(
						townblock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.PlotMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY),
							TownyPermission.PermLevel.OUTSIDER,
							TownyPermission.ActionType.DESTROY);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		//===============================================================================================

		MenuItemBuilder.of("resident_item_use_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.USE_RES)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.USE_RES2)
				.lore(List.of(
						townblock.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.PlotMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getResidentPerm(TownyPermission.ActionType.ITEM_USE),
							TownyPermission.PermLevel.RESIDENT,
							TownyPermission.ActionType.ITEM_USE
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("nation_item_use_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.USE_NATION)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.USE_NATION2)
				.lore(List.of(
						townblock.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.PlotMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getNationPerm(TownyPermission.ActionType.ITEM_USE),
							TownyPermission.PermLevel.NATION,
							TownyPermission.ActionType.ITEM_USE
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("ally_item_use_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.USE_ALLY)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.USE_ALLY2)
				.lore(List.of(
						townblock.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.PlotMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getAllyPerm(TownyPermission.ActionType.ITEM_USE),
							TownyPermission.PermLevel.ALLY,
							TownyPermission.ActionType.ITEM_USE
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("outsider_item_use_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.USE_OUTSIDER)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.USE_OUTSIDER2)
				.lore(List.of(
						townblock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.ITEM_USE),
							TownyPermission.PermLevel.OUTSIDER,
							TownyPermission.ActionType.ITEM_USE
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		//================================================================================================


		MenuItemBuilder.of("resident_switch_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_RES)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_RES2)
				.lore(List.of(
						townblock.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getResidentPerm(TownyPermission.ActionType.SWITCH),
							TownyPermission.PermLevel.RESIDENT,
							TownyPermission.ActionType.SWITCH
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("nation_switch_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_NATION)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_NATION2)
				.lore(List.of(
						townblock.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getNationPerm(TownyPermission.ActionType.SWITCH),
							TownyPermission.PermLevel.NATION,
							TownyPermission.ActionType.SWITCH
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("ally_switch_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_ALLY)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_ALLY2)
				.lore(List.of(
						townblock.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.PlotMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getAllyPerm(TownyPermission.ActionType.SWITCH),
							TownyPermission.PermLevel.ALLY,
							TownyPermission.ActionType.SWITCH
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("outsider_switch_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.SWITCH_OUTSIDER2)
				.lore(List.of(
						townblock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH)
								? Localization.PlotMenu.PlayerPermissionsMenu.TRUE_MSG
								: Localization.PlotMenu.PlayerPermissionsMenu.FALSE_MSG,
						"",
						Localization.TownMenu.PlayerPermissionsMenu.CHANGE))
				.onClick(click -> {
					townblock.getPermissions().change(
							TownyPermissionChange.Action.SINGLE_PERM,
							!townblock.getPermissions().getOutsiderPerm(TownyPermission.ActionType.SWITCH),
							TownyPermission.PermLevel.OUTSIDER,
							TownyPermission.ActionType.SWITCH
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		//===========================================================================================

		MenuItemBuilder.of("reset_all_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.RESET)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.RESET_LORE)
				.onClick(click -> {
					townblock.getPermissions().change(TownyPermissionChange.Action.ALL_PERMS, false
					);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("all_on_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.ON)
				.lore("")
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.ON_LORE)
				.onClick(click -> {
					townblock.getPermissions().change(TownyPermissionChange.Action.ALL_PERMS, true);
					TownyAPI.getInstance().getDataSource().saveTown(town);
					MenuManager.refreshInPlace(player, new PlotPermMenu(player, resident));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_permissions_menu_info")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.INFO)
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.INFO_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_permissions_menu_back_button")
				.name(Localization.PlotMenu.PlayerPermissionsMenu.BACK_BUTTON)
				.lore(Localization.PlotMenu.PlayerPermissionsMenu.BACK_BUTTON_LORE)
				.onClick(click -> {
					try {
						MenuManager.switchMenu(player, new PlotMenu(player, townblock));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player,this);

		fillEmptySlots("filler_town_perms_menu");
	}
}

