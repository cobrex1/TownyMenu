package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.nation.prompt.CreateNationPrompt;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class JoinNationMenu extends MenuHandler {

	private final Player player;
	private final Resident resident;

	public JoinNationMenu(Player player, Resident resident) {
		super(
				player,
				Localization.JoinCreateNationMenu.MAIN_MENU_TITLE,
				getInventorySize(ConfigNodes.FIND_NATION_MENU_SIZE)
		);

		this.player = player;
		this.resident = resident;
		buildmenu();
		fillEmptySlots("filler_join_nation_menu");
	}

	private void buildmenu() {

		MenuItemBuilder.of("find_nation_button")
				.name(Localization.JoinCreateNationMenu.FIND_NATION_BUTTON)
				.lore("")
				.onClick(click -> {
					List<Nation> nations = TownyUniverse.getInstance().getNations().stream()
							.filter(Nation::isOpen)
							.collect(Collectors.toList());
					player.closeInventory();
					MenuManager.switchMenu(player, new OpenNationMenu(player, nations));
				})
				.buildAndSet(player, this);

		MenuItemBuilder.of("create_nation_button")
				.name(Localization.JoinCreateNationMenu.CLICK_CREATE_NATION_BUTTON)
				.onClick(click -> {
					player.closeInventory();
					new CreateNationPrompt(player).show(player);
				})
				.buildAndSet(player, this);
	}
}
