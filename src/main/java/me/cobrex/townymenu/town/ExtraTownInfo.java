package me.cobrex.townymenu.town;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.MenuHandler;
import me.cobrex.townymenu.utils.MenuItemBuilder;
import me.cobrex.townymenu.utils.MenuManager;
import me.cobrex.townymenu.utils.MessageFormatter;
import org.bukkit.entity.Player;

public class ExtraTownInfo extends MenuHandler {

	private final Town town;

	public ExtraTownInfo(Player player) {
		super(
				player,
				MessageFormatter.format(Localization.TownMenu.ExtraInfoMenu.MENU_TITLE, player),
				getInventorySize(ConfigNodes.TOWN_EXTRA_INFO_MENU_SIZE)
		);

		this.town = TownyAPI.getInstance().getTown(player);

		setCancelClicks(true);

		MenuItemBuilder.of("town_claim_info")
				.name(Localization.TownMenu.ExtraInfoMenu.CLAIMING)
				.lore(Localization.TownMenu.ExtraInfoMenu.CLAIMING_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_extra_commands_info")
				.name(Localization.TownMenu.ExtraInfoMenu.COMMANDS)
				.lore(Localization.TownMenu.ExtraInfoMenu.COMMANDS_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_extra_info_menu_back_button")
				.name(Localization.TownMenu.ExtraInfoMenu.BACK_BUTTON)
				.lore(Localization.TownMenu.ExtraInfoMenu.BACK_BUTTON_LORE)
				.onClick(click -> {
					try {
						MenuManager.switchMenu(player, new TownMenu(player, town));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player,this);

		fillEmptySlots("filler_town_extra_info_menu");
	}
}