package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.*;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Comparator;
import java.util.List;

public class InviteTownMenu extends PaginatedMenu {

	private final Player player;

	public InviteTownMenu(Player player) {
		super(
				player,
				Localization.NationMenu.NationInviteTownMenu.MENU_TITLE,
				buildTownItems(player));
		this.player = player;
	}

	private static List<MenuItem> buildTownItems(Player player) {
		return TownyUniverse.getInstance().getTowns().stream()
				.filter(town -> !town.hasNation())
				.sorted(Comparator.comparing(Town::getName))
				.map(town -> {
					ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
					SkullMeta meta = (SkullMeta) skull.getItemMeta();
					if (meta != null) {

						meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MessageFormatter.formatComponent(
								Localization.NationMenu.NationTownMenu.TOWN_NAME.replace("{town}", town.getName()), player
						)));
						meta.setLore(List.of(
								MessageFormatter.format(Localization.NationMenu.NationTownMenu.MAYOR.replace("{mayor}", town.getMayor().getFormattedName()), player),
								"",
								MessageFormatter.format(Localization.NationMenu.NationInviteTownMenu.INVITE, player)
						));
						skull.setItemMeta(meta);
					}
					return new MenuItem(skull, click -> {
						player.closeInventory();
						player.performCommand("n invite " + town.getName());
					});
				})
				.toList();
	}

	@Override
	protected void onBack() {
		try {
			MenuManager.switchMenu(player, new NationMainMenu(player));
		} catch (NotRegisteredException e) {
			MessageUtils.send(player, MessageFormatter.format(Localization.Error.MUST_BE_IN_NATION, player));
		}
	}
}