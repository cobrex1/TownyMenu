package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.nation.prompt.NationKickPrompt;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.town.prompt.TownKickPrompt;
import me.cobrex.townymenu.utils.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class NationTownMenu extends MenuHandler {

	private final Town town;
	private final Plugin plugin;
	private final Resident resident;

	public NationTownMenu(Player player, Town town) {
		super(
				player,
				MessageFormatter.format(Localization.NationMenu.NationTownMenu.MENU_TITLE, player),
				getInventorySize(ConfigNodes.NATION_TOWN_MENU_SIZE));
		this.town = town;
		this.resident = TownyAPI.getInstance().getResident(player);
		this.plugin = JavaPlugin.getPlugin(TownyMenuPlugin.class);

		MenuItemBuilder.of("nation_town_kick_button")
				.name(Localization.NationMenu.NationTownMenu.KICK)
				.lore(Localization.NationMenu.NationTownMenu.KICK_LORE)
				.onClick(click -> {
					player.closeInventory();
					MessageUtils.startConversation(plugin, player, new NationKickPrompt(town));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("nation_town_sanction_button")
				.name(Localization.TownMenu.ResidentMenu.KICK)
				.lore(Localization.TownMenu.ResidentMenu.KICK_LORE)
				.onClick(click -> {
					player.closeInventory();
					new TownKickPrompt(resident).show(player);
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("back_button")
				.name(Localization.NationMenu.NationTownMenu.BACK_BUTTON)
				.lore(Localization.NationMenu.NationTownMenu.BACK_BUTTON_LORE)
				.lore("")
				.onClick(click -> {
					try {
						MenuManager.switchMenu(player, new NationMainMenu(player));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player,this);

		fillEmptySlots("filler_nation_town_menu");
	}
}