package me.cobrex.townymenu.utils;

import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.town.ToggleSettingsMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuManager {

	private static final Map<UUID, MenuHandler> openMenus = new HashMap<>();

	public static void openMenu(Player player, MenuHandler handler) {
//		System.out.println("[DEBUG MMang17] openMenus map: " + openMenus);
//		Bukkit.getLogger().info("[MenuManager MMang18] Opening menu: " + handler.getClass().getSimpleName());
		openMenus.put(player.getUniqueId(), handler);
		Bukkit.getGlobalRegionScheduler().runDelayed(TownyMenuPlugin.instance, scheduledTask ->
		player.openInventory(handler.getInventory()), 1L);
//		Bukkit.getLogger().info("[MenuManager MMang22] openMenus now: " + openMenus);
	}

	public static void handleClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player player))
			return;

		MenuHandler handler = openMenus.get(player.getUniqueId());
		if (handler == null)
			return;

		event.setCancelled(true);

		if (event.getClickedInventory() == null)
			return;

		if (event.getClickedInventory() != event.getView().getTopInventory())
			return;

		if (event.isShiftClick() || event.getClick().isKeyboardClick())
			return;

		handler.handleClick(event);
	}

	public static void refreshInPlace(Player player, MenuHandler rebuilt) {
		MenuHandler current = openMenus.get(player.getUniqueId());
		if (current == null) {
			openMenu(player, rebuilt);
			return;
		}

		if (current.getInventory().getSize() != rebuilt.getInventory().getSize()) {
			openMenu(player, rebuilt);
			return;
		}

		current.replaceAllFrom(rebuilt);
		player.updateInventory();
	}

	public static void closeMenu(Player player) {
		openMenus.remove(player.getUniqueId());
	}

	public static MenuHandler getOpenMenu(Player player, ToggleSettingsMenu toggleSettingsMenu) {
		return openMenus.get(player.getUniqueId());
	}

	public static void switchMenu(Player player, MenuHandler newMenu) {
		player.closeInventory();
		MenuManager.openMenu(player, newMenu);
	}
}
