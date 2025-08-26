package me.cobrex.townymenu.town;

import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.town.prompt.TownDepositPrompt;
import me.cobrex.townymenu.town.prompt.TownSetTaxPrompt;
import me.cobrex.townymenu.town.prompt.TownWithdrawPrompt;
import me.cobrex.townymenu.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class EconomyManagementMenu extends MenuHandler {

	private final Town town;
	private final Economy economy;

	public EconomyManagementMenu(Player player, Town town) {
		super(
				player,
				MessageFormatter.format(Localization.TownMenu.EconomyMenu.MENU_TITLE, player),
				getInventorySize(ConfigNodes.TOWN_ECONOMY_MENU_SIZE));
		this.economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
		this.town = town;

		try {
			MenuItemBuilder.of("town_balance_button")
					.name(Localization.TownMenu.EconomyMenu.BALANCE)
					.lore(List.of(
							Localization.TownMenu.EconomyMenu.TOWN_BALANCE + town.getAccount().getHoldingFormattedBalance(),
							"",
							Localization.TownMenu.EconomyMenu.UPKEEP + Settings.MONEY_SYMBOL + TownySettings.getTownUpkeepCost(town)
					))
					.onClick(click -> {})
					.buildAndSet(player,this);
		} catch (Throwable t) {
			MenuItemBuilder.of("town_balance_button")
					.name("Economy Disabled")
					.lore(Collections.emptyList())
					.onClick(click -> {})
					.buildAndSet(player,this);
		}

		MenuItemBuilder.of("town_deposit_button")
				.name(Localization.TownMenu.EconomyMenu.DEPOSIT)
				.lore(Localization.TownMenu.EconomyMenu.DEPOSIT_LORE)
				.onClick(click -> {
					player.closeInventory();
					MessageUtils.startConversation(TownyMenuPlugin.instance, player, new TownDepositPrompt(player, economy));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_withdraw_button")
				.name(Localization.TownMenu.EconomyMenu.WITHDRAW)
				.lore(Localization.TownMenu.EconomyMenu.WITHDRAW_LORE)
				.onClick(click -> {
					player.closeInventory();
					MessageUtils.startConversation(TownyMenuPlugin.instance, player, new TownWithdrawPrompt(town));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_set_tax_button")
				.name(Localization.TownMenu.EconomyMenu.TAX)
				.lore("")
				.lore(List.of(
						town.isTaxPercentage()
								? Localization.TownMenu.EconomyMenu.TAX_PERCENTAGE
								: Localization.TownMenu.EconomyMenu.TAX_AMOUNT
				))
				.onClick(click -> {
					player.closeInventory();
					MessageUtils.startConversation(TownyMenuPlugin.instance, player, new TownSetTaxPrompt(town, player));
				})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_economy_menu_info_button")
				.name(Localization.TownMenu.EconomyMenu.INFO)
				.lore("")
				.lore(Localization.TownMenu.EconomyMenu.INFO_LORE)
				.onClick(click -> {})
				.buildAndSet(player,this);

		MenuItemBuilder.of("town_economy_menu_back_button")
				.name(Localization.TownMenu.EconomyMenu.BACK_BUTTON)
				.lore(Localization.TownMenu.EconomyMenu.BACK_BUTTON_LORE)
				.onClick(click -> {
					try {
						MenuManager.switchMenu(player, new TownMenu(player, town));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player,this);

		fillEmptySlots("filler_town_economy_menu");
	}
}