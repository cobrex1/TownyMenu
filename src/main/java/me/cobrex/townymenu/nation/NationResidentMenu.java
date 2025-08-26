package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.nation.prompt.NationGiveKingPrompt;
import me.cobrex.townymenu.nation.prompt.NationPlayerTitlePrompt;
import me.cobrex.townymenu.nation.prompt.NationRankPrompt;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.MenuHandler;
import me.cobrex.townymenu.utils.MenuItemBuilder;
import me.cobrex.townymenu.utils.MenuManager;
import org.bukkit.entity.Player;


public class NationResidentMenu extends MenuHandler {

	private final Player player;
	private final Town town;
	private final Resident resident;

	public NationResidentMenu(Resident resident) {
		super(
				TownyAPI.getInstance().getPlayer(resident),
				Localization.TownMenu.ResidentMenu.MENU_TITLE,
				getInventorySize(ConfigNodes.RESIDENT_MENU_SIZE)
		);

		this.player = TownyAPI.getInstance().getPlayer(resident);
		this.resident = resident;
		this.town = resident.hasTown() ? resident.getTownOrNull() : null;

		setupMenuItems();
		fillEmptySlots("filler_nation_resident_list_menu");
	}

	private void setupMenuItems() {

		MenuItemBuilder.of("nation_resident_title_button")
				.name(Localization.NationMenu.NationResidentMenu.NATION_TITLE)
				.lore(Localization.NationMenu.NationResidentMenu.NATION_TITLE_LORE)
				.onClick(click -> {
					player.closeInventory();
					new NationPlayerTitlePrompt(resident).show(player);
				})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_resident_rank_button")
				.name(Localization.NationMenu.NationResidentMenu.NATION_RANK)
				.lore(Localization.NationMenu.NationResidentMenu.NATION_RANK_LORE)
				.onClick(click -> {
					player.closeInventory();
					new NationRankPrompt(resident).show(player);
				})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_resident_set_king_button")
				.name(Localization.NationMenu.NationResidentMenu.NATION_KING)
				.lore(Localization.NationMenu.NationResidentMenu.NATION_KING_LORE)
				.onClick(click -> {
					player.closeInventory();
					new NationGiveKingPrompt(resident).show(player);
				})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_resident_menu_info_button")
				.name(Localization.NationMenu.NationResidentMenu.RESIDENT_INFO)
				.lore(Localization.NationMenu.NationResidentMenu.RESIDENT_INFO_LORE)
				.onClick(click -> {})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_resident_menu_back_button")
				.name(Localization.NationMenu.NationResidentMenu.BACK_BUTTON)
				.lore(Localization.NationMenu.NationResidentMenu.BACK_BUTTON_LORE)
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
