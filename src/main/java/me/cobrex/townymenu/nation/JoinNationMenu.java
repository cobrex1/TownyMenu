package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import me.cobrex.townymenu.nation.prompt.CreateNationPrompt;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
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

public class JoinNationMenu extends Menu {

	private final Button openNationButton;
	private final Button createNationButton;


	//	private final static ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.GRAY_STAINED_GLASS_PANE, "").make();
	private final static ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_JOIN_NATION_MENU)), "").make();

	public JoinNationMenu(Resident resident, Player player) {

		setSize(9);
		setTitle(Localization.JoinCreateNationMenu.MAIN_MENU_TITLE);

		List<Nation> nations = new ArrayList<>(TownyUniverse.getInstance().getNations()).stream().filter(n -> n.isOpen()).collect(Collectors.toList());

		openNationButton = new ButtonMenu(new OpenNationMenu(nations), CompMaterial.fromString(String.valueOf(Settings.FIND_NATION_BUTTON)), Localization.JoinCreateNationMenu.FIND_NATION_BUTTON);

		createNationButton = new ButtonConversation(new CreateNationPrompt(player), ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.CREATE_NATION_BUTTON)), Localization.JoinCreateNationMenu.CLICK_CREATE_NATION_BUTTON));

	}

	private void add(Nation n) {
	}

	public class OpenNationMenu extends MenuPagged<Nation> {

		protected OpenNationMenu(Iterable<Nation> pages) {
			super(JoinNationMenu.this, pages);
			setTitle(Localization.JoinCreateNationMenu.JOIN_OPEN_NATION);
		}

		@Override
		protected ItemStack convertToItemStack(Nation item) {
			ItemStack itemSkull = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta skull = (SkullMeta) itemSkull.getItemMeta();
			assert skull != null;
			skull.setDisplayName(ChatColor.YELLOW + "" + (item.getName()));
			Player player = Bukkit.getPlayer(item.getKing().getUUID());
			skull.setOwningPlayer(player);
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add(ChatColor.WHITE + Localization.JoinCreateNationMenu.KING + (item.getKing()));
			lore.add(ChatColor.WHITE + Localization.JoinCreateNationMenu.NUMBER_OF_TOWNS + (item.getNumTowns()));
			skull.setLore(lore);
			itemSkull.setItemMeta(skull);
			return itemSkull;

		}

		@Override
		protected void onPageClick(Player player, Nation item, ClickType click) {
			player.closeInventory();
			player.performCommand("n join " + item.getName());

		}

	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == 2)
			return openNationButton.getItem();

		if (slot == 4)
			return createNationButton.getItem();

		return DUMMY_BUTTON;
	}

}
