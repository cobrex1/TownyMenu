package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NationTownListMenu extends PaginatedMenu {

	private final Player player;

	public NationTownListMenu(Player player) {
		super(
				player,
				Localization.NationMenu.NationTownMenu.MENU_TITLE,

				buildTownItems(player));
		this.player = player;
	}

	private static List<MenuItem> buildTownItems(Player player) {
		Nation nation = null;
		nation = TownyAPI.getInstance().getNation(player);

		return nation.getTowns().stream()
				.sorted(Comparator.comparing(Town::getName))
				.map(town -> {
					ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
					SkullMeta meta = (SkullMeta) skull.getItemMeta();
					if (meta != null) {

						meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MessageFormatter.formatComponent(
								Localization.NationMenu.NationTownMenu.TOWN_NAME.replace("{town}", town.getName()), player
						)));

						List<String> lore = new ArrayList<>();
						lore.add("");
						lore.add(MessageFormatter.format(
								Localization.NationMenu.NationTownMenu.MAYOR.replace("{mayor}", town.getMayor().getName()), player
						));
						lore.add("");
						lore.add(MessageFormatter.format(
								Localization.NationMenu.NationTownMenu.NUMBER_RESIDENTS.replace("{residents}", String.valueOf(town.getNumResidents())), player
						));
						meta.setLore(lore);
						skull.setItemMeta(meta);
					}

					return new MenuItem(skull, click -> {
						if (town.getMayor().getName().equalsIgnoreCase(player.getName())) {
							player.sendMessage(MessageFormatter.format(Localization.Error.CANNOT_SELECT_SELF, player));
							player.closeInventory();
							return;
						}
						MenuManager.switchMenu(player, new NationTownMenu(player, town));
					});
				})
				.toList();
	}

	@Override
	protected MenuItem getInfoButton() {
		String itemname = ConfigNodes.NATION_TOWN_LIST_INFO_BUTTON.getSub("item");
		Material mat = Material.matchMaterial(Settings.getConfig().getString(itemname));
		if (mat == null) mat = Material.BOOK;

		int modelData = Settings.getConfig().getInt(
				ConfigNodes.NATION_TOWN_LIST_INFO_BUTTON_CMD.getSub("custommodeldata"),
				ConfigNodes.NATION_TOWN_LIST_INFO_BUTTON_CMD.getDefault() instanceof Integer ?
						(Integer) ConfigNodes.NATION_TOWN_LIST_INFO_BUTTON_CMD.getDefault() : 0);

		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			if (modelData > 0) meta.setCustomModelData(modelData);
			meta.setDisplayName(
					LegacyComponentSerializer.legacySection().serialize(
							MessageFormatter.formatComponent(
									Localization.NationMenu.NationTownMenu.INFO, player
							)
					)
			);
			List<Component> lore = new ArrayList<>();
			if (Localization.TownMenu.ResidentMenu.INFO_LORE != null) {
				for (String line : Localization.NationMenu.NationTownMenu.INFO_LORE) {
					lore.add(MessageFormatter.formatComponent(line, player));
				}
			}
			meta.lore(lore);

			item.setItemMeta(meta);
		}

		return new MenuItem(item, click -> {});
	}

	@Override
	protected void onBack() {
		try {
			MenuManager.switchMenu(player, new NationMainMenu(player));
		} catch (NotRegisteredException e) {
			MessageUtils.send(player, Localization.Error.MUST_BE_IN_NATION);
//			MessageUtils.send(player, MessageFormatter.format(Localization.Error.MUST_BE_IN_NATION, player));
		}
	}
}