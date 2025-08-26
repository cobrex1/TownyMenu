package me.cobrex.townymenu.town;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.town.prompt.TownGiveMayorPrompt;
import me.cobrex.townymenu.town.prompt.TownKickPrompt;
import me.cobrex.townymenu.town.prompt.TownPlayerTitlePrompt;
import me.cobrex.townymenu.town.prompt.TownRankPrompt;
import me.cobrex.townymenu.utils.MenuHandler;
import me.cobrex.townymenu.utils.MenuItemBuilder;
import me.cobrex.townymenu.utils.MenuManager;
import org.bukkit.entity.Player;

public class ResidentMenu extends MenuHandler {

	private final Player player;
	private final Resident resident;
	private final Town town;

	public ResidentMenu(Resident resident) {
		super(
				TownyAPI.getInstance().getPlayer(resident),
				Localization.TownMenu.ResidentMenu.MENU_TITLE,
				getInventorySize(ConfigNodes.RESIDENT_MENU_SIZE)
		);

		this.player = TownyAPI.getInstance().getPlayer(resident);
		this.resident = resident;
		this.town = TownyAPI.getInstance().getTown(player);

		setupMenuItems();
		fillEmptySlots("filler_resident_menu");
	}

	private void setupMenuItems() {

		MenuItemBuilder.of("resident_kick_button")
				.name(Localization.TownMenu.ResidentMenu.KICK)
				.lore(Localization.TownMenu.ResidentMenu.KICK_LORE)
				.onClick(click -> {
					player.closeInventory();
					new TownKickPrompt(resident).show(player);
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("resident_title_button")
				.name(Localization.TownMenu.ResidentMenu.TITLE)
				.lore(Localization.TownMenu.ResidentMenu.TITLE_LORE)
				.onClick(click -> {
					player.closeInventory();
					new TownPlayerTitlePrompt(resident).show(player);
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("resident_rank_button")
				.name(Localization.TownMenu.ResidentMenu.RANK)
				.lore(Localization.TownMenu.ResidentMenu.RANK_LORE)
				.onClick(click -> {
					player.closeInventory();
					new TownRankPrompt(resident).show(player);
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("resident_set_mayor_button")
				.name(Localization.TownMenu.ResidentMenu.MAYOR)
				.lore(Localization.TownMenu.ResidentMenu.MAYOR_LORE)
				.onClick(click -> {
					player.closeInventory();
					new TownGiveMayorPrompt(resident).show(player);
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("resident_menu_info_button")
				.name(Localization.TownMenu.ResidentMenu.INFO)
				.lore(Localization.TownMenu.ResidentMenu.INFO_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("Back_Button")
				.name(Localization.TownMenu.ResidentMenu.BACK_BUTTON)
				.lore(Localization.TownMenu.ResidentMenu.BACK_BUTTON_LORE)
				.onClick(click -> {
					try {
						MenuManager.switchMenu(player, new TownMenu(player, town));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player,this);
	}
}