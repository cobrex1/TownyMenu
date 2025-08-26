package me.cobrex.townymenu.plot;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownBlock;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.*;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class FriendPlayerMenu extends PaginatedMenu {

	private final Player player;
	private final Resident playerResident;

	public FriendPlayerMenu(Player player) {
		super(
				player,
				Localization.PlotMenu.FriendMenu.MENU_TITLE,
				buildFriendItems(player));
		this.player = player;
		this.playerResident = TownyAPI.getInstance().getResident(player.getName());
	}

	private static List<MenuItem> buildFriendItems(Player player) {
		Resident playerResident = TownyAPI.getInstance().getResident(player.getName());

		List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());

		return onlinePlayers.stream()
				.filter(p -> !p.getUniqueId().equals(player.getUniqueId())) // exclude self
				.sorted(Comparator.comparing(Player::getName, String.CASE_INSENSITIVE_ORDER))
				.map(online -> {
					Resident targetResident = TownyAPI.getInstance().getResident(online.getName());
					if (targetResident == null) return null;

					ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
					SkullMeta meta = (SkullMeta) skull.getItemMeta();
					if (meta != null) {
						meta.setOwningPlayer(online);

						meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(
								MessageFormatter.formatComponent("&a" + online.getName(), player)));

						boolean isFriend = playerResident.getFriends().contains(targetResident);
						meta.setLore(List.of(
								"",
								MessageFormatter.format(isFriend
										? Localization.PlotMenu.FriendMenu.CLICK_REMOVE_LORE
										: Localization.PlotMenu.FriendMenu.CLICK_ADD_LORE, player)
						));
						skull.setItemMeta(meta);
					}

					return new MenuItem(skull, click -> {
						if (playerResident.getFriends().contains(targetResident)) {
							playerResident.removeFriend(targetResident);
							MessageUtils.send(player, Localization.PlotMenu.FriendMenu.REMOVE.replace("{player}", online.getName()));
						} else {
							playerResident.addFriend(targetResident);
							MessageUtils.send(player, Localization.PlotMenu.FriendMenu.ADD.replace("{player}", online.getName()));
						}
						TownyAPI.getInstance().getDataSource().saveResident(playerResident);
						player.closeInventory();
					});
				})
				.filter(Objects::nonNull)
				.toList();
	}

	@Override
	protected void onBack() throws NotRegisteredException {
		TownBlock townBlock = TownyAPI.getInstance().getTownBlock(player.getLocation());
		MenuManager.switchMenu(player, new PlotMenu(player, townBlock));
	}
}