package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
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

public class NationResidentListMenu extends PaginatedMenu {

	private final Player player;

	public NationResidentListMenu(Nation nation, Player player) throws NotRegisteredException {
		super(
				player,
				Localization.NationMenu.NationResidentMenu.MENU_TITLE,
				buildResidentItems(nation, player)
		);

		this.player = player;
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

	private static List<MenuItem> buildResidentItems(Nation nation, Player player) {
		return nation.getTowns().stream()
				.flatMap(town -> town.getResidents().stream())
				.sorted(Comparator.comparing(Resident::getName))
				.map(resident -> {
					ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
					SkullMeta meta = (SkullMeta) skull.getItemMeta();
					if (meta != null && resident.getUUID() != null) {
						meta.setOwnerProfile(Bukkit.createPlayerProfile(resident.getUUID(), resident.getName()));

						meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MessageFormatter.formatComponent(
								Localization.TownMenu.ResidentMenu.RESIDENT_NAME.replace("{player}", resident.getFormattedTitleName()), player
						)));

						List<Component> lore = new ArrayList<>();
						Town town = TownyAPI.getInstance().getTown(player);
						if (town != null) {
							lore.add(parseFormatted(MessageFormatter.format(
									Localization.NationMenu.NationResidentMenu.TOWN.replace(
											"{town}", town.getName()), player
							)));
						} else {
							lore.add(parseFormatted(MessageFormatter.format(
									Localization.NationMenu.NationResidentMenu.TOWN.replace(
											"{town}", "No Town"), player
							)));
						}

						lore.add(Component.empty());
						lore.add(parseFormatted(MessageFormatter.format(
								Localization.NationMenu.NationResidentMenu.ONLINE.replace("{online}",
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
							MenuManager.switchMenu(player, new NationResidentMenu(resident));
						}
					});
				})
				.toList();
	}

	@Override
	protected MenuItem getInfoButton() {
		String itemname = ConfigNodes.NATION_RESIDENT_LIST_INFO_BUTTON.getSub("item");
		Material mat = Material.matchMaterial(Settings.getConfig().getString(itemname));
		if (mat == null) mat = Material.BOOK;

		int modelData = Settings.getConfig().getInt(
				ConfigNodes.NATION_RESIDENT_LIST_INFO_BUTTON_CMD.getSub("custommodeldata"),
				ConfigNodes.NATION_RESIDENT_LIST_INFO_BUTTON_CMD.getDefault() instanceof Integer ?
						(Integer) ConfigNodes.NATION_RESIDENT_LIST_INFO_BUTTON_CMD.getDefault() : 0);

		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			if (modelData > 0) meta.setCustomModelData(modelData);
			meta.setDisplayName(
					LegacyComponentSerializer.legacySection().serialize(
							MessageFormatter.formatComponent(
									Localization.NationMenu.NationResidentMenu.RESIDENT_INFO, player
							)
					)
			);

			List<Component> lore = new ArrayList<>();
			if (Localization.TownMenu.ResidentMenu.INFO_LORE != null) {
				for (String line : Localization.NationMenu.NationResidentMenu.RESIDENT_INFO_LORE) {
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
		MenuManager.switchMenu(player, new NationMainMenu(player));
	}
}
