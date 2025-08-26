package me.cobrex.townymenu.listeners;

import me.cobrex.townymenu.town.ToggleSettingsMenu;
import me.cobrex.townymenu.utils.MenuHandler;
import me.cobrex.townymenu.utils.MenuManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;


public class MenuListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player player)) return;

		try {
			MenuHandler menu = MenuManager.getOpenMenu(player, new ToggleSettingsMenu(player));
			if (menu != null) {

				MenuManager.handleClick(event);
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getPlayer() instanceof Player player) {
			MenuManager.closeMenu(player);
		}
	}
}
