package me.cobrex.townymenu.town;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.*;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;

public class OpenTownMenu extends PaginatedMenu {

	private final Player player;

	public OpenTownMenu(Player player) {
		super(
				player,
				Localization.JoinCreateMenu.JOIN_OPEN_TOWN,
				buildTownItems(player)
		);

		this.player = player;
	}

	private static List<MenuItem> buildTownItems(Player player) {
		return TownyUniverse.getInstance().getTowns().stream()
				.filter(Town::isOpen)
				.sorted(Comparator.comparing(Town::getName))
				.map(town -> {
					OfflinePlayer mayor = Bukkit.getOfflinePlayer(town.getMayor().getUUID());

					List<String> lore = List.of(
							LegacyComponentSerializer.legacySection().serialize(MessageFormatter.formatComponent(Localization.JoinCreateMenu.MAYOR + town.getMayor().getName(), player)),
							LegacyComponentSerializer.legacySection().serialize(MessageFormatter.formatComponent(Localization.JoinCreateMenu.NUMBER_RESIDENTS + town.getNumResidents(), player))
					);

					return MenuItemBuilder.of("join_open_town_skull")
							.skullOwner(town.getMayor().getUUID())
							.name(Localization.JoinCreateMenu.TOWN_NAME + town.getName())
							.lore(lore)
							.onClick(click -> {
								player.closeInventory();
								player.performCommand("t join " + town.getName());
							})
							.buildItem(player);
				})
				.toList();
	}

	@Override
	protected void onBack() {
		MenuManager.switchMenu(player, new JoinTownMenu(player));
	}
}
