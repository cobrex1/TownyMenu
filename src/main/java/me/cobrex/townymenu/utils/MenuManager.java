package me.cobrex.townymenu.utils;

import me.cobrex.townymenu.town.ToggleSettingsMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuManager {

	private static final Map<UUID, MenuHandler> openMenus = new HashMap<>();

	public static void openMenu(Player player, MenuHandler handler) {
//		System.out.println("[DEBUG MMang17] openMenus map: " + openMenus);
//		Bukkit.getLogger().info("[MenuManager MMang18] Opening menu: " + handler.getClass().getSimpleName());
		openMenus.put(player.getUniqueId(), handler);
		SchedulerUtil.runLater(player, () -> {
			player.openInventory(handler.getInventory());
		}, 1L);
	}

	public static void handleClick(InventoryClickEvent event) {
		Inventory top = event.getView().getTopInventory();
		if (!(top.getHolder() instanceof MenuHandler handler)) return;

		// Protect the actual view before any permission check or button callback.
		boolean alreadyCancelled = event.isCancelled();
		event.setCancelled(true);
		if (alreadyCancelled || !(event.getWhoClicked() instanceof Player)) return;
		if (event.getClickedInventory() != top) return;
		if (event.getClick() != ClickType.LEFT && event.getClick() != ClickType.RIGHT) return;

		try {
			handler.handleClick(event);
		} finally {
			// An exception or callback must never unlock decorative menu items.
			event.setCancelled(true);
		}
	}

	public static void handleDrag(InventoryDragEvent event) {
		if (event.getView().getTopInventory().getHolder() instanceof MenuHandler) {
			event.setCancelled(true);
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
