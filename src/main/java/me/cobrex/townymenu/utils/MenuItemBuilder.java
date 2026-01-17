package me.cobrex.townymenu.utils;

import me.cobrex.townymenu.settings.Settings;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class MenuItemBuilder {

	private final String settingsKey;
	private String name;
	private final List<String> lore = new ArrayList<>();
	private Consumer<InventoryClickEvent> clickHandler;
	private UUID skullOwnerUUID;
	private Material fallbackMaterial;

	public MenuItemBuilder(String settingsKey) {
		this.settingsKey = settingsKey;
	}

	public static MenuItemBuilder of(String settingsKey) {
		return new MenuItemBuilder(settingsKey);
	}

	public static MenuItemBuilder of(String key, Material fallback) {
		return new MenuItemBuilder(key).withFallbackMaterial(fallback);
	}

	public MenuItemBuilder name(String name) {
		this.name = name;
		return this;
	}

	public MenuItemBuilder lore(String line) {
		this.lore.add(line);
		return this;
	}

	public MenuItemBuilder lore(List<String> lines) {
		if (lines != null && !lines.isEmpty()) {
			this.lore.addAll(lines);
		}
		return this;
	}

	public MenuItemBuilder onClick(Consumer<org.bukkit.event.inventory.InventoryClickEvent> handler) {
		this.clickHandler = handler;
		return this;
	}

	public MenuItemBuilder skullOwner(UUID uuid) {
		this.skullOwnerUUID = uuid;
		return this;
	}

	public MenuItemBuilder withFallbackMaterial(Material fallback) {
		this.fallbackMaterial = fallback;
		return this;
	}

	public void buildAndSet(MenuHandler handler) {
		buildAndSet(null, handler);
	}

	public void buildAndSet(Player player, MenuHandler handler) {
		MenuItem menuItem = buildItem(player);
		int slot = getSlotFromConfig();
		handler.setMenuItem(slot, menuItem);
	}


	public MenuItem buildItem() {
		return buildItem(null);
	}

	public MenuItem buildItem(Player player) {
		FileConfiguration settings = Settings.getConfig();
		String path = settingsKey + ".";

		int slot = getSlotFromConfig();

		String materialName = settings.getString(path + "item", "STONE");
		int customModelData = settings.getInt(path + "custommodeldata", 0);

		ItemStack item;

		if (materialName.toLowerCase().startsWith("hdb-")) {
			item = HeadDatabaseUtil.HeadDataUtil.createItem(materialName);
			if (item == null && fallbackMaterial != null) {
				item = new ItemStack(fallbackMaterial);
			}

		} else if (materialName.toLowerCase().startsWith("skull-owner:")) {
			String owner = materialName.substring("skull-owner:".length());
			item = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			if (meta != null) {
				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(owner);
				meta.setOwningPlayer(offlinePlayer);
				item.setItemMeta(meta);
			}

		} else if (materialName.toLowerCase().startsWith("skull-texture:")) {
			String b64 = materialName.substring("skull-texture:".length()).trim();
			item = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

			if (skullMeta != null) {
				try {
					String json = new String(java.util.Base64.getDecoder().decode(b64), java.nio.charset.StandardCharsets.UTF_8);
					java.util.regex.Matcher m = java.util.regex.Pattern
							.compile("\"url\"\\s*:\\s*\"(http[^\"]+)\"")
							.matcher(json);

					if (m.find()) {
						String url = m.group(1);

						org.bukkit.profile.PlayerProfile profile =
								org.bukkit.Bukkit.createPlayerProfile(java.util.UUID.randomUUID(), "custom_head");
						profile.getTextures().setSkin(new java.net.URL(url));

						skullMeta.setOwnerProfile(profile);
						item.setItemMeta(skullMeta);
					} else {
						org.bukkit.Bukkit.getLogger().warning("[TownyMenu] Invalid skull-texture Base64 (no url field) for: " + settingsKey);
					}
				} catch (Exception ex) {
					org.bukkit.Bukkit.getLogger().log(java.util.logging.Level.WARNING,
							"[TownyMenu] Failed to apply skull-texture for " + settingsKey, ex);
				}
			}

		} else {
			Material material = Material.matchMaterial(materialName);
			if (material == null && fallbackMaterial != null) {
				material = fallbackMaterial;
			}
			item = new ItemStack(material != null ? material : Material.STONE);
		}

		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			if (name != null) {
					Component displayNameComp = MessageFormatter.formatComponent(name, player);
					if (meta.hasDisplayName()) meta.setDisplayName(null);
					try {
						meta.displayName(displayNameComp);
					} catch (NoSuchMethodError ignored) {
						meta.setDisplayName(MessageFormatter.format(name, player));
					}
				}
			if (!lore.isEmpty()) {
				List<Component> loreComp = lore.stream()
						.map(line -> MessageFormatter.formatComponent(line, player))
						.toList();
				try {
					meta.lore(loreComp);
				} catch (NoSuchMethodError ignored) {
					meta.setLore(MessageFormatter.formatList(lore, player));
				}
			}
			if (customModelData > 0) {
				meta.setCustomModelData(customModelData);
			}
			if (skullOwnerUUID != null && meta instanceof SkullMeta skullMeta) {
				skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(skullOwnerUUID));
			}
			item.setItemMeta(meta);
		}

		return new MenuItem(item, clickHandler);
	}

	private int getSlotFromConfig() {
		FileConfiguration settings = Settings.getConfig();
		String path = settingsKey + ".";
		return settings.getInt(path + "slot", 0);
	}
}
