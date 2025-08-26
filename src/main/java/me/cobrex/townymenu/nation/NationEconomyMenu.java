package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.nation.prompt.NationDepositPrompt;
import me.cobrex.townymenu.nation.prompt.NationSetTaxPrompt;
import me.cobrex.townymenu.nation.prompt.NationWithdrawPrompt;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NationEconomyMenu extends MenuHandler {

	private final Nation nation;
	private final Economy economy;
	private final Player player;

	public NationEconomyMenu(Player player) {
		super(
				player,
				MessageFormatter.format(Localization.NationMenu.NationEconomyMenu.MENU_TITLE, player),
				getInventorySize(ConfigNodes.NATION_ECONOMY_MENU_SIZE)
		);

		this.nation = TownyAPI.getInstance().getNation(player);
		this.economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
		this.player = player;
		buildMenu();
		fillEmptySlots("filler_nation_economy_menu");
	}

	private void buildMenu() {

		MenuItemBuilder.of("nation_balance_button")
				.name(Localization.NationMenu.NationEconomyMenu.BALANCE)
				.lore(Localization.NationMenu.NationEconomyMenu.NATION_BALANCE + nation.getAccount().getHoldingFormattedBalance())
				.lore("")
				.lore(Localization.NationMenu.NationEconomyMenu.UPKEEP + Settings.MONEY_SYMBOL +
					TownySettings.getNationUpkeepCost(nation))
				.onClick( click -> {})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_deposit_button")
				.name(Localization.NationMenu.NationEconomyMenu.DEPOSIT)
				.lore(Localization.NationMenu.NationEconomyMenu.DEPOSIT_LORE)
				.onClick(click -> {
					player.closeInventory();
					MessageUtils.startConversation(TownyMenuPlugin.instance, player, new NationDepositPrompt(nation, economy));
				})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_withdraw_button")
				.name(Localization.NationMenu.NationEconomyMenu.WITHDRAW)
				.lore(Localization.NationMenu.NationEconomyMenu.WITHDRAW_LORE)
				.onClick(click -> {
					player.closeInventory();
					MessageUtils.startConversation(TownyMenuPlugin.instance, player, new NationWithdrawPrompt(nation));
				})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_set_tax_button")
				.name(Localization.NationMenu.NationEconomyMenu.TAX)
				.lore("")
				.lore(nation.isTaxPercentage()
					? Localization.NationMenu.NationEconomyMenu.TAX_AMOUNT
					: Localization.NationMenu.NationEconomyMenu.TAX_PERCENTAGE
				)
				.onClick(click -> {
					player.closeInventory();
					MessageUtils.startConversation(TownyMenuPlugin.instance, player, new NationSetTaxPrompt(nation));
				})
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_economy_menu_info_button")
				.name(Localization.NationMenu.NationEconomyMenu.NATION_ECONOMY_MENU_INFO)
				.lore("")
				.lore(Localization.NationMenu.NationEconomyMenu.NATION_ECONOMY_MENU_INFO_LORE)
				.onClick(click -> {})
				.buildAndSet(player, this);

		MenuItemBuilder.of("nation_economy_back_button")
				.name(Localization.NationMenu.NationEconomyMenu.BACK_BUTTON)
				.lore(Localization.NationMenu.NationEconomyMenu.BACK_BUTTON_LORE)
				.onClick(click -> {
					player.closeInventory();
					try {
						MenuManager.switchMenu(player, new NationMainMenu(player));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player, this);
	}
}