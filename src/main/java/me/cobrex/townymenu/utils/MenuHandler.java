package me.cobrex.townymenu.utils;

import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.config.ConfigUtil;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class MenuHandler implements InventoryHolder {

	private boolean cancelClicks = true;

	protected Inventory inventory;
	protected final Player player;
	private final Map<Integer, MenuItem> items = new HashMap<>();

	public MenuHandler(Player player, String title, int size) {
		this.player = player;

		try {
			this.inventory = Bukkit.createInventory(this, size, formatTitle(title, player));
		} catch (NoSuchMethodError e) {
			this.inventory = Bukkit.createInventory(this, size, formatTitleLegacy(title, player));
		}
	}

	protected static String serializeForDebug(Component component) {
		if (MessageFormatter.COLOR_MODE == Settings.ColorMode.MINIMESSAGE) {
			return MiniMessage.miniMessage().serialize(component);
		} else {
			return LegacyComponentSerializer.legacyAmpersand().serialize(component);
		}
	}

	public static Component formatTitle(String title, Player player) {
		Component titleComp = MessageFormatter.formatComponent(title, player)
				.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);

		return titleComp;
	}

	public static String formatTitleLegacy(String title, Player player) {
		return MessageFormatter.format(title, player);
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	public void setMenuItem(int slot, MenuItem item) {
		if (item == null) {
			items.remove(slot);
			inventory.setItem(slot, null);
			return;
		}
		items.put(slot, item);
		ItemStack stack = item.getItemStack();
		inventory.setItem(slot, stack);
	}

	public void handleClick(InventoryClickEvent event) {
		if (shouldCancelClicks()) {
			event.setCancelled(true);
		}

		int rawSlot = event.getRawSlot();

		if (rawSlot < inventory.getSize()) {
			MenuItem item = items.get(rawSlot);
			if (item != null) {
				item.onClick(event);
			}
		}
	}

	protected void clearMenuItems() {
		inventory.clear();
		items.clear();
	}

	protected void addMenuItem(int slot, ItemStack item, Consumer<InventoryClickEvent> handler) {
		setMenuItem(slot, new MenuItem(item, handler));
	}

	protected void addMenuItem(String settingsKey, String name, List<String> lore, Consumer<InventoryClickEvent> handler) {
		FileConfiguration settings = Settings.getConfig();
		String path = settingsKey + ".";

		int slot = settings.getInt(path + "slot", settings.getInt(path + "Slot", 0));
		String materialName = settings.getString(path + "item", settings.getString(path + "Item", "STONE"));
		int customModelData = settings.getInt(path + "custommodeldata", settings.getInt(path + "CustomModelData", 0));

		ItemStack item;
		if (materialName != null && materialName.toLowerCase().startsWith("hdb-")) {
			try {
				item = HeadDatabaseUtil.HeadDataUtil.createItem(materialName);
			} catch (Throwable ignored) {
				item = null;
			}
			if (item == null) {
				item = new ItemStack(Material.STONE);
			}
		} else {
			Material material = Material.matchMaterial(materialName);
			if (material == null) material = Material.STONE;
			item = new ItemStack(material);
		}

		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			if (name != null && !name.isBlank()) {
				Component comp = MessageFormatter.formatComponent(name, player);
				comp = comp.decoration(TextDecoration.ITALIC, false);
				try {
					meta.displayName(comp);
				} catch (NoSuchMethodError ignore) {
					meta.setDisplayName(MessageFormatter.format(name, player));
				}
			}

			if (lore != null && !lore.isEmpty()) {
				List<Component> compLore = lore.stream()
						.map(line -> MessageFormatter.formatComponent(line, player)
								.decoration(TextDecoration.ITALIC, false))
						.collect(Collectors.toList());

				try {
					meta.lore(compLore);
				} catch (NoSuchMethodError ignore) {
					meta.setLore(MessageFormatter.formatList(lore, player));
				}
			}

			if (customModelData > 0) {
				try {
					meta.setCustomModelData(customModelData);
				} catch (Throwable ignored) {
				}
			}
			item.setItemMeta(meta);
		}
	}

	protected void addNavMenuItem(String settingsKey, int slot, Consumer<InventoryClickEvent> handler) {

		String title;
		List<String> lore;

		switch (settingsKey) {
			case "menu_previous_page_button" -> {
				title = Localization.Previous_Page_Button.PREVIOUS_PAGE_BUTTON_TITLE;
				lore = Localization.Previous_Page_Button.PREVIOUS_PAGE_BUTTON_LORE;
			}
			case "menu_next_page_button" -> {
				title = Localization.Next_Page_Button.NEXT_PAGE_BUTTON_TITLE;
				lore = Localization.Next_Page_Button.NEXT_PAGE_BUTTON_LORE;
			}
			default -> {
				title = Localization.Back_Button.BACK_BUTTON_TITLE;
				lore = Localization.Back_Button.BACK_BUTTON_LORE;
			}
		}

		MenuItem item = Settings.loadMenuItem(
				player,
				settingsKey,
				Material.STONE,
				title,
				lore,
				handler
		);

		setMenuItem(slot, item);
	}

	protected static int getInventorySize(ConfigNodes sizeNode) {
		int size = ConfigUtil.getInt(sizeNode);
		return (size % 9 == 0 && size >= 9) ? size : 27;
	}

	public void fillEmptySlots(String fillerSectionKey) {
		FileConfiguration settings = Settings.getConfig();

		String itemKey = fillerSectionKey + ".item";
		String modelDataKey = fillerSectionKey + ".custommodeldata";

		String materialName = settings.getString(itemKey, "GRAY_STAINED_GLASS_PANE");
		int modelData = settings.getInt(modelDataKey, 0);

		ItemStack filler;
		if (materialName != null && materialName.toLowerCase().startsWith("hdb-")) {
			try {
				filler = HeadDatabaseUtil.HeadDataUtil.createItem(materialName);
			} catch (Throwable t) {
				filler = null;
			}
			if (filler == null) {
				Bukkit.getLogger().warning("[MenuHandler] Failed to load HDB head for filler: " + materialName + ", using fallback.");
				filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
			}
		} else {
			Material material = Material.matchMaterial(materialName);
			filler = (material != null) ? new ItemStack(material) : new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		}

		ItemMeta meta = filler.getItemMeta();
		if (meta != null) {
			try {
				meta.displayName(Component.text(" "));
			} catch (NoSuchMethodError e) {
				meta.setDisplayName(" ");
			}
			if (modelData > 0) {
				try {
					meta.setCustomModelData(modelData);
				} catch (Throwable ignored) {
				}
			}
			filler.setItemMeta(meta);
		}

		for (int i = 0; i < inventory.getSize(); i++) {
			if (inventory.getItem(i) == null) {
				setMenuItem(i, new MenuItem(filler, e -> { }));
			}
		}
	}

	protected Map<Integer, MenuItem> getItemsView() {
		return items;
	}

	public void replaceAllFrom(MenuHandler other) {
		this.clearMenuItems();

		for (Map.Entry<Integer, MenuItem> e : other.getItemsView().entrySet()) {
			this.setMenuItem(e.getKey(), e.getValue());
		}
	}

	public void setCancelClicks(boolean cancel) {
		this.cancelClicks = cancel;
	}

	public boolean shouldCancelClicks() {
		return cancelClicks;
	}
}
