package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.MenuHandler;
import me.cobrex.townymenu.utils.MenuItemBuilder;
import me.cobrex.townymenu.utils.MenuManager;
import me.cobrex.townymenu.utils.MessageFormatter;
import org.bukkit.entity.Player;


public class NationToggleMenu extends MenuHandler {

	private final Player player;
	private final Nation nation;

	public NationToggleMenu(Player player) {
		super(
				player,
				MessageFormatter.format(Localization.NationMenu.NationToggleMenu.MENU_TITLE, player),
				getInventorySize(ConfigNodes.NATION_TOGGLE_MENU_SIZE)
		);

		this.player = player;
		this.nation = TownyAPI.getInstance().getNation(player);

		fillEmptySlots("filler_nation_toggle_menu");

		MenuItemBuilder.of("nation_toggle_open_button")
				.name(Localization.NationMenu.NationToggleMenu.OPEN)
				.lore(nation.isOpen()
						? Localization.NationMenu.NationToggleMenu.TOGGLE_OFF
						: Localization.NationMenu.NationToggleMenu.TOGGLE_ON)
				.onClick(click -> toggleNationSetting("open"))
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_toggle_public_button")
				.name(Localization.NationMenu.NationToggleMenu.PUBLIC)
				.lore(nation.isPublic()
						? Localization.NationMenu.NationToggleMenu.TOGGLE_OFF
						: Localization.NationMenu.NationToggleMenu.TOGGLE_ON)
				.onClick(click -> toggleNationSetting("public"))
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_toggle_tax_percentage_button")
				.name(Localization.NationMenu.NationToggleMenu.TAX_PERCENT)
				.lore(nation.isTaxPercentage()
						? Localization.NationMenu.NationToggleMenu.TOGGLE_OFF
						: Localization.NationMenu.NationToggleMenu.TOGGLE_ON)
				.onClick(click -> toggleNationSetting("tax"))
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_toggle_neutral_button")
				.name(Localization.NationMenu.NationToggleMenu.NEUTRAL)
				.lore(nation.isNeutral()
						? Localization.NationMenu.NationToggleMenu.TOGGLE_OFF
						: Localization.NationMenu.NationToggleMenu.TOGGLE_ON)
				.onClick(click -> toggleNationSetting("neutral"))
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_toggle_peaceful_button")
				.name(Localization.NationMenu.NationToggleMenu.PEACEFUL)
				.lore(nation.isNeutral()
						? Localization.NationMenu.NationToggleMenu.TOGGLE_OFF
						: Localization.NationMenu.NationToggleMenu.TOGGLE_ON)
				.onClick(click -> {
					player.performCommand("n toggle peaceful");
					saveNation();
					MenuManager.refreshInPlace(player, new NationToggleMenu(player));
				})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_toggle_menu_info_button")
				.name(Localization.NationMenu.NationToggleMenu.NATION_TOGGLE_MENU_INFO)
				.lore(Localization.NationMenu.NationToggleMenu.NATION_TOGGLE_MENU_INFO_LORE)
				.onClick(click -> {})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_toggle_menu_back_button")
				.name(Localization.Back_Button.BACK_BUTTON_TITLE)
				.onClick(click -> {
					try {
						MenuManager.switchMenu(player, new NationMainMenu(player));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player, this);
	}

	private void toggleNationSetting(String type) {
		switch (type.toLowerCase()) {
			case "open" -> nation.setOpen(!nation.isOpen());
			case "public" -> nation.setPublic(!nation.isPublic());
			case "tax" -> nation.setTaxPercentage(!nation.isTaxPercentage());
			case "neutral" -> nation.setNeutral(!nation.isNeutral());
		}

		saveNation();
		MenuManager.refreshInPlace(player, new NationToggleMenu(player));
	}

	private void saveNation() {
		TownyAPI.getInstance().getDataSource().saveNation(nation);
	}
}
