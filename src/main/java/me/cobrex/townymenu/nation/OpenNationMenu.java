package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.*;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;

public class OpenNationMenu extends PaginatedMenu {

	private final Player player;
	private final Resident resident;

	public OpenNationMenu(Player player, List<Nation> nations) {
		super(
				player,
				Localization.JoinCreateNationMenu.JOIN_OPEN_NATION,
				buildNationItems(player, nations)
		);
		this.player = player;
		this.resident = TownyAPI.getInstance().getResident(player);
	}

	private static List<MenuItem> buildNationItems(Player player, List<Nation> nations) {
		return nations.stream()
				.sorted(Comparator.comparing(Nation::getName))
				.map(nation -> {
					OfflinePlayer king = Bukkit.getOfflinePlayer(nation.getKing().getUUID());
					List<String> lore = List.of(
							"",
							LegacyComponentSerializer.legacySection().serialize(MessageFormatter.formatComponent(Localization.JoinCreateNationMenu.KING + nation.getKing().getName(), player)),
							LegacyComponentSerializer.legacySection().serialize(MessageFormatter.formatComponent( Localization.JoinCreateNationMenu.NUMBER_OF_TOWNS + nation.getNumTowns(), player))
					);

					return MenuItemBuilder.of("join_open_nation_skull")
							.skullOwner(nation.getKing().getUUID())
							.name(Localization.JoinCreateNationMenu.NATION_NAME + nation.getName())
							.lore(lore)
							.onClick(click -> {
								player.closeInventory();
								player.performCommand("n join " + nation.getName());
							})
							.buildItem(player);
				})
				.toList();
	}

	@Override
	protected void onBack() {
		MenuManager.switchMenu(player, new JoinNationMenu(player, resident));
	}
}