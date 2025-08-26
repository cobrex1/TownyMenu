package me.cobrex.townymenu.town;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ResidentListMenu extends PaginatedMenu {

	private final Player player;
	private final Town town;

	public ResidentListMenu(Player player, Town town) {
		super(
				player,
				Localization.TownMenu.ResidentMenu.MENU_TITLE,           // raw key/string, not preformatted
				buildResidentItems(town, player)
		);
		this.player = player;
		this.town = town;
	}

	private static final MiniMessage MM = MiniMessage.miniMessage();
	private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

	private static Component parseFormatted(String input) {
		if (input == null) return Component.empty();
		if (input.contains("<")) {

			return MM.deserialize(input);
		} else {

			return LEGACY.deserialize(input);
		}
	}

	private static List<MenuItem> buildResidentItems(Town town, Player player) {
		return  town.getResidents().stream()
				.sorted(Comparator.comparing(Resident::getName))
				.map(resident -> {
					ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
					SkullMeta meta = (SkullMeta) skull.getItemMeta();
					if (meta != null && resident.getUUID() != null) {
						meta.setOwnerProfile(Bukkit.createPlayerProfile(resident.getUUID(), resident.getName()));

						meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MessageFormatter.formatComponent(
								Localization.TownMenu.ResidentMenu.RESIDENT_NAME.replace("{player}", resident.getFormattedName()), player
						)));

						List<Component> lore = new ArrayList<>();
						lore.add(Component.empty());
						lore.add(parseFormatted(MessageFormatter.format(
								Localization.TownMenu.ResidentMenu.TOWN_RANK.replace("{rank}", resident.getNamePrefix()), player
						)));
//						Bukkit.getLogger().info("[ResidentListMenu] Lore1 (raw): " + lore);
//						Bukkit.getLogger().info("[ResidentListMenu] Color mode: " + MessageFormatter.COLOR_MODE);
						lore.add(Component.empty());
						lore.add(parseFormatted(MessageFormatter.format(
								Localization.TownMenu.ResidentMenu.ONLINE.replace("{online}",
										MessageUtils.getFormattedDateShort(resident.getLastOnline())), player
						)));
						meta.lore(lore);

						skull.setItemMeta(meta);
					}

					return new MenuItem(skull, click -> {
						if (resident.getName().equalsIgnoreCase(player.getName())) {
							player.sendMessage(MessageFormatter.format(Localization.Error.CANNOT_SELECT_SELF, player));
							player.closeInventory();
						} else {
							MenuManager.switchMenu(player, new ResidentMenu(resident));
						}
					});
				})
				.toList();
	}

	@Override
	protected MenuItem getInfoButton() {
		String itemname = ConfigNodes.RESIDENT_MENU_INFO_BUTTON.getSub("item");
		Material mat = Material.matchMaterial(Settings.getConfig().getString(itemname));
		if (mat == null) mat = Material.BOOK;

		int modelData = Settings.getConfig().getInt(
				ConfigNodes.RESIDENT_MENU_INFO_BUTTON_CMD.getSub("custommodeldata"),
				ConfigNodes.RESIDENT_MENU_INFO_BUTTON_CMD.getDefault() instanceof Integer ?
						(Integer) ConfigNodes.RESIDENT_MENU_INFO_BUTTON_CMD.getDefault() : 0);

		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			if (modelData > 0) meta.setCustomModelData(modelData);
			meta.setDisplayName(
					LegacyComponentSerializer.legacySection().serialize(
							MessageFormatter.formatComponent(
									Localization.TownMenu.ResidentMenu.INFO, player
							)
					)
			);

			List<Component> lore = new ArrayList<>();
			if (Localization.TownMenu.ResidentMenu.INFO_LORE != null) {
				for (String line : Localization.TownMenu.ResidentMenu.INFO_LORE) {
					lore.add(MessageFormatter.formatComponent(line, player));
				}
			}
			meta.lore(lore);

			item.setItemMeta(meta);
		}

		return new MenuItem(item, click -> {});
	}

	@Override
	protected void onBack() throws NotRegisteredException {
		MenuManager.switchMenu(player, new TownMenu(player, town));
	}
}
