package me.cobrex.townymenu.join;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.HeadDatabaseUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonConversation;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JoinTownMenu extends Menu {

	private final Button openTownButton;
	private final Button createTownButton;

	private final static ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_JOIN_TOWN_MENU)), "").make();

	public JoinTownMenu(Resident resident, Player player) {

		ItemStack headItem = HeadDatabaseUtil.HeadDataUtil.createItem("hdb-10230");
		ItemStack materialItem = HeadDatabaseUtil.HeadDataUtil.createItem("SUNFLOWER");

		setSize(9);
		setTitle(Localization.JoinCreateMenu.MAIN_MENU_TITLE);

		List<Town> towns = new ArrayList<>(TownyUniverse.getInstance().getTowns()).stream().filter(t -> t.isOpen()).collect(Collectors.toList());

		openTownButton = new ButtonMenu(new OpenTownMenu(towns), ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.FIND_TOWN)))
				.name(Localization.JoinCreateMenu.FIND_OPEN_TOWN));

		createTownButton = new ButtonConversation(new CreateTownPrompt(player), ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.CREATE_TOWN)))
				.name(Localization.JoinCreateMenu.CLICK_CREATE_TOWN));
	}

	private void add(Town t) {
	}

	public class OpenTownMenu extends MenuPagged<Town> {

		protected OpenTownMenu(Iterable<Town> pages) {
			super(JoinTownMenu.this, pages);
			setTitle(Localization.JoinCreateMenu.JOIN_OPEN_TOWN);
		}

		@Override
		protected ItemStack convertToItemStack(Town item) {
			ItemStack itemSkull = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta skull = (SkullMeta) itemSkull.getItemMeta();
			skull.setDisplayName(ChatColor.YELLOW + "" + (item.getName()));
			Player player = Bukkit.getPlayer(item.getMayor().getUUID());
			skull.setOwningPlayer(player);
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add(ChatColor.WHITE + Localization.JoinCreateMenu.NUMBER_RESIDENTS + (item.getNumResidents()));
			lore.add("");
			lore.add(ChatColor.WHITE + Localization.JoinCreateMenu.MAYOR + (item.getMayor()));
			skull.setLore(lore);
			itemSkull.setItemMeta(skull);
			return itemSkull;
		}

		@Override
		protected void onPageClick(Player player, Town item, ClickType click) {
			player.closeInventory();
			player.performCommand("t join " + item.getName());
		}
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == 2)
			return openTownButton.getItem();

		if (slot == 5)
			return createTownButton.getItem();

		return DUMMY_BUTTON;
	}
}

