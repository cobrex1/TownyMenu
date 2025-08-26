package me.cobrex.townymenu.town;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.MenuItem;
import me.cobrex.townymenu.utils.MenuManager;
import me.cobrex.townymenu.utils.MessageFormatter;
import me.cobrex.townymenu.utils.PaginatedMenu;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InvitePlayerMenu extends PaginatedMenu {

	private final Player player;
	private final Town town;

	public InvitePlayerMenu(Player player) {
		super(
				player,
				Localization.TownMenu.ResidentMenu.MENU_TITLE,
				buildInviteItems(player));
		this.player = player;
		this.town = TownyAPI.getInstance().getTown(player);
	}

	private static List<MenuItem> buildInviteItems(Player player) {
		return Bukkit.getOnlinePlayers().stream()
				.filter(online -> {
					Resident res = TownyAPI.getInstance().getResident(online);
					return res != null && !res.hasTown();
				})
				.sorted(Comparator.comparing(Player::getName))
				.map(online -> {
					Resident res = TownyAPI.getInstance().getResident(online);
					ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
					SkullMeta meta = (SkullMeta) skull.getItemMeta();

					if (meta != null) {
						meta.setDisplayName(
								LegacyComponentSerializer.legacySection().serialize(
										MessageFormatter.formatComponent("&a&l" + res.getFormattedName(), player)
								)
						);

						List<String> lore = new ArrayList<>();
						lore.add("");
						lore.add(MessageFormatter.format(Localization.TownMenu.ResidentMenu.INVITE, player));
						meta.setLore(lore);

						meta.setOwningPlayer(online);
						skull.setItemMeta(meta);
					}

					return new MenuItem(skull, click -> {
						player.closeInventory();
						player.performCommand("t invite " + res.getName());
					});
				})
				.toList();
	}

	@Override
	protected void onBack() throws NotRegisteredException {
		MenuManager.switchMenu(player, new TownMenu(player, town));
	}
}
