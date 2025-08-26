package me.cobrex.townymenu.plot;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.TownBlock;
import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.config.ConfigUtil;
import me.cobrex.townymenu.plot.prompt.PlotEvictPrompt;
import me.cobrex.townymenu.plot.prompt.PlotForSalePrompt;
import me.cobrex.townymenu.plot.prompt.PlotNotForSalePrompt;
import me.cobrex.townymenu.plot.prompt.PlotSetTypePrompt;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.MenuHandler;
import me.cobrex.townymenu.utils.MenuItemBuilder;
import me.cobrex.townymenu.utils.MenuManager;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.entity.Player;

public class PlotAdministrationMenu extends MenuHandler {

	public PlotAdministrationMenu(Player player, TownBlock townBlock) {
		super(
				player,
				Localization.PlotMenu.PlotAdminMenu.MENU_TITLE,
				ConfigUtil.getInt(ConfigNodes.PLOT_ADMIN_MENU_SIZE)
		);

		MenuItemBuilder.of("plot_admin_for_sale_button")
				.name(Localization.PlotMenu.PlotAdminMenu.FOR_SALE)
				.lore(Localization.PlotMenu.PlotAdminMenu.FOR_SALE_LORE)
				.onClick(click -> {
					player.closeInventory();
					MessageUtils.startConversation(TownyMenuPlugin.instance, player, new PlotForSalePrompt(player, townBlock));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_admin_not_for_sale_button")
				.name(Localization.PlotMenu.PlotAdminMenu.NOT_FOR_SALE)
				.lore(Localization.PlotMenu.PlotAdminMenu.NOT_FOR_SALE_LORE)
				.onClick(click -> {
					player.closeInventory();
					MessageUtils.startConversation(TownyMenuPlugin.instance, player, new PlotNotForSalePrompt(player, townBlock));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_admin_set_plot_type_button")
				.name(Localization.PlotMenu.PlotAdminMenu.SET_TYPE)
				.lore(Localization.PlotMenu.PlotAdminMenu.SET_TYPE_LORE)
				.onClick(click -> {
					player.closeInventory();
					MessageUtils.startConversation(TownyMenuPlugin.instance, player, new PlotSetTypePrompt(player, townBlock));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_admin_evict_res_button")
				.name(Localization.PlotMenu.PlotAdminMenu.EVICT)
				.lore(Localization.PlotMenu.PlotAdminMenu.EVICT_LORE)
				.onClick(click -> {
					player.closeInventory();
					MessageUtils.startConversation(TownyMenuPlugin.instance, player, new PlotEvictPrompt(player, townBlock));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_admin_menu_info_button")
				.name(Localization.PlotMenu.PlotAdminMenu.INFO)
				.lore(Localization.PlotMenu.PlotAdminMenu.INFO_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("plot_admin_menu_back_button")
				.name(Localization.PlotMenu.PlotAdminMenu.BACK_BUTTON)
				.lore(Localization.PlotMenu.PlotAdminMenu.BACK_BUTTON_LORE)
				.onClick(click -> {
					try {
						MenuManager.switchMenu(player, new PlotMenu(player, townBlock));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player,this);

		fillEmptySlots("filler_plot_admin_menu");
	}
}