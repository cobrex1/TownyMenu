package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.*;
import org.bukkit.entity.Player;

public class NationExtraInfoMenu extends MenuHandler {

	private final Player player;

	public NationExtraInfoMenu(Player player) {
		super(
				player,
				Localization.NationMenu.NationExtraInfoMenu.MENU_TITLE,
				getInventorySize(ConfigNodes.NATION_EXTRA_INFO_MENU_SIZE)
		);

		this.player = player;
		buildMenu();
		fillEmptySlots("filler_nation_extra_info_menu");
	}

	private void buildMenu() {

		MenuItemBuilder.of("nation_extra_commands_1_button")
				.name(Localization.NationMenu.NationExtraInfoMenu.COMMANDS_1)
				.lore(Localization.NationMenu.NationExtraInfoMenu.COMMANDS_1_LORE)
				.onClick(click -> {})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_extra_commands_2_button")
				.name(Localization.NationMenu.NationExtraInfoMenu.COMMANDS_2)
				.lore(Localization.NationMenu.NationExtraInfoMenu.COMMANDS_2_LORE)
				.onClick(click -> {})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_extra_info_menu_back_button")
				.name(Localization.NationMenu.NationExtraInfoMenu.BACK_BUTTON)
				.lore(Localization.NationMenu.NationExtraInfoMenu.BACK_BUTTON_LORE)
				.onClick(click -> {
					try {
						MenuManager.switchMenu(player, new NationMainMenu(player));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player, this);
	}
}