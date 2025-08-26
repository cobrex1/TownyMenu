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
		Bukkit.getScheduler().runTaskLater(TownyMenuPlugin.instance, () ->
		player.openInventory(handler.getInventory()), 1L);
//		Bukkit.getLogger().info("[MenuManager MMang22] openMenus now: " + openMenus);
	}

	public static void handleClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		MenuHandler handler = openMenus.get(player.getUniqueId());

		if (handler != null) {
//			Bukkit.getLogger().info("[DEBUG] TopInventory: " + event.getView().getTopInventory());
//			Bukkit.getLogger().info("[DEBUG] Handler Inventory: " + handler.getInventory());
//			System.out.println("[DEBUG] Equal? " + event.getView().getTopInventory().equals(handler.getInventory()));

			if (event.getView().getTopInventory().equals(handler.getInventory())) {
				handler.handleClick(event);
			} else {
//				Bukkit.getLogger().warning("[DEBUG] Inventory mismatch for player: " + player.getName());
			}
		}
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
