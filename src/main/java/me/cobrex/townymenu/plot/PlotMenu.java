package me.cobrex.townymenu.plot;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.town.TownMenu;
import me.cobrex.townymenu.utils.MenuHandler;
import me.cobrex.townymenu.utils.MenuItemBuilder;
import me.cobrex.townymenu.utils.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class PlotMenu extends MenuHandler {

	private final Player player;
	private final TownBlock townBlock;
	private final Town town;
	private final Resident resident;

	public PlotMenu(Player player, TownBlock townBlock) throws NotRegisteredException {
		super(
				player,
				Localization.PlotMenu.MAIN_MENU_TITLE,
				getInventorySize(ConfigNodes.PLOT_MENU_SIZE));
		this.player = player;
		this.townBlock = townBlock;
		this.town = townBlock.getTown();
		this.resident = TownyAPI.getInstance().getResident(player);

		setMenuItems();
	}

	private void setMenuItems() {
		MenuItemBuilder.of("plot_toggle_menu_button")
				.name(Localization.PlotMenu.TOGGLE_SETTINGS_MENU_BUTTON)
				.lore(Localization.PlotMenu.TOGGLE_SETTINGS_MENU_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new PlotToggleSettingsMenu(player, townBlock)))
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_permissions_menu_button")
				.name(Localization.PlotMenu.PERMISSIONS_MENU_BUTTON)
				.lore(Localization.PlotMenu.PERMISSIONS_MENU_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new PlotPermMenu(player, resident)))
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_admin_menu_button")
				.name(Localization.PlotMenu.PLOT_ADMIN_MENU_BUTTON)
				.lore(Localization.PlotMenu.PLOT_ADMIN_MENU_BUTTON_LORE)
				.onClick(click -> MenuManager.switchMenu(player, new PlotAdministrationMenu(player, townBlock)))
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_friend_menu_button")
				.name(Localization.PlotMenu.FRIEND_MENU_BUTTON)
				.lore(Localization.PlotMenu.FRIEND_MENU_BUTTON_LORE)
				.onClick(click -> {
					List<Resident> allOnlineResidents = new ArrayList<>();
					for (Player online : Bukkit.getOnlinePlayers()) {
						Resident res = TownyAPI.getInstance().getResident(online);
						if (res != null)
							allOnlineResidents.add(res);
					}
					MenuManager.switchMenu(player, new FriendPlayerMenu(player));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_toggle_menu_info_button")
				.name(Localization.PlotMenu.ToggleMenu.INFO)
				.lore(Localization.PlotMenu.ToggleMenu.INFO_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_menu_back_button")
				.name(Localization.PlotMenu.BACK_BUTTON)
				.lore(Localization.PlotMenu.BACK_BUTTON_LORE)
				.onClick(click -> {
					MenuManager.closeMenu(player);
					try {
						MenuManager.switchMenu(player, new TownMenu(player, town));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player,this);

		fillEmptySlots("filler_plot_menu");
	}
}
