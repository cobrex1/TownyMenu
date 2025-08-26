package me.cobrex.townymenu.town;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.town.prompt.TownCreatePrompt;
import me.cobrex.townymenu.utils.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class JoinTownMenu extends MenuHandler {

	private final Player player;

	public JoinTownMenu(Player player) {
		super(
				player,
				MessageFormatter.format(Localization.JoinCreateMenu.MAIN_MENU_TITLE, player),
				getInventorySize(ConfigNodes.FIND_TOWN_MENU_SIZE)
		);

		this.player = player;

		buildmenu();
		fillEmptySlots("filler_join_town_menu");
	}

	private void buildmenu() {

		MenuItemBuilder.of("find_town_button")
				.name(Localization.JoinCreateMenu.FIND_OPEN_TOWN)
				.lore("")
				.onClick(click -> {
					List<Town> town = TownyAPI.getInstance().getTowns().stream()
							.filter(Town::isOpen)
							.collect(Collectors.toList());
					player.closeInventory();
					MenuManager.switchMenu(player, new OpenTownMenu(player));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("create_town_button")
				.name(Localization.JoinCreateMenu.CLICK_CREATE_TOWN)
				.lore("")
				.onClick(click -> {
					player.closeInventory();
					new TownCreatePrompt(player).show(player);
				})
				.buildAndSet(player,this);
	}
}
